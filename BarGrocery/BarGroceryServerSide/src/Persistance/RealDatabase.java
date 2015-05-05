package Persistance;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelclasses.Item;
import modelclasses.PriceAssociation;

/**
 * Created by Samantha Hamor on 3/16/2015.
 * Creates my BarGrocery Database with a few preloaded values
 */

public class RealDatabase implements IDatabase {
    static {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (Exception e) {
            throw new IllegalStateException("Could not load sqlite driver");
        	
        }
    }

    private interface Transaction<ResultType> {
        public ResultType execute(Connection conn) throws SQLException;
    }

    private static final int MAX_ATTEMPTS = 10;

    private Connection connect() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:derby:barGrocerydb.db;create=true");

        // Set autocommit to false to allow the execution of multiple 
        //queries/statements as part of the same transaction.
        conn.setAutoCommit(false);

        return conn;
    }

    public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
        try {
            return doExecuteTransaction(txn);
        } catch (SQLException e) { 
            throw new PersistenceException("Transaction failed", e);
           
        }
    }

    public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
        Connection conn = connect();

        try {
            int numAttempts = 0;
            boolean success = false;
            ResultType result = null;

            while (!success && numAttempts < MAX_ATTEMPTS) {
                try {
                    result = txn.execute(conn);
                    conn.commit();
                    success = true;
                } catch (SQLException e) {
                    if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
                        // Deadlock: retry (unless max retry count has been reached)
                        numAttempts++;
                    } else {
                        // Some other kind of SQLException
                        throw e;
                    }
                }
            }

            if (!success) {
                throw new SQLException("Transaction failed (too many retries)");
            }

            // Success!
            return result;
        } finally {
            DBUtil.closeQuietly(conn);
        }
    }

    public void createItemTables() {
        executeTransaction(new Transaction<Boolean>() {
            @Override
            public Boolean execute(Connection conn) throws SQLException {
                PreparedStatement stmt = null;

                try {
                   // Note that the 'id' column is an autoincrement primary key,
                    // so SQLite will automatically assign an id when rows are inserted. 
                    stmt = conn.prepareStatement(
                            "CREATE TABLE  items ( "
                            + "  id INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY, " 
                            + "  brand VARCHAR(30) NOT NULL, " 
                            + "  product VARCHAR(30) NOT NULL " 
                            + "  )"
                    );

                    stmt.executeUpdate();

                    return true;
                } finally {
                    DBUtil.closeQuietly(stmt);
                }
            }
        });
    }

    public void createPriceAssociationTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					// Note that the 'id' column is an autoincrement primary key,
					// so SQLite will automatically assign an id when rows
					// are inserted.				
					stmt = conn.prepareStatement(
							"CREATE TABLE priceAssociations (" +
							"  id INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY," +
							"  itemid INTEGER," +
							"  price FLOAT(10) NOT NULL," +
							"  storename varchar(20) NOT NULL" +
							")"
					);
					
					stmt.executeUpdate();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}	
		
    protected void storePriceAssoicationNoId(PriceAssociation pa, PreparedStatement stmt, int i) throws SQLException {
		        // Note that we are assuming that the Item does not have a valid id,
		        // and so are not attempting to store the (invalid) id.
		        //a unique id is automatically generated.
		        stmt.setInt(i++, pa.getItemId());
		        stmt.setDouble(i++, pa.getPrice());
		        stmt.setString(i++, pa.getLocation());
				
	}
    
    protected void loadPriceAssoication(PriceAssociation pa, ResultSet resultSet, int index) throws SQLException {
		pa.setPriceInfoId(resultSet.getInt(index++));
		pa.setItemId(resultSet.getInt(index++));
		pa.setPrice(resultSet.getDouble(index++));
		pa.setLocation(resultSet.getString(index++));
	}
    
    public void loadInitialItemData() {
        executeTransaction(new Transaction<Boolean>() {
            @Override
            public Boolean execute(Connection conn) throws SQLException {
                PreparedStatement stmt = null;

                try {
                    stmt = conn.prepareStatement("insert into items (brand, product) values (?, ?)");
                    storeItemNoId(new Item("b","p"), stmt, 1);
                    stmt.addBatch();
                    storeItemNoId(new Item("bt","pt"), stmt, 1);
                    stmt.addBatch();
                    storeItemNoId(new Item("brandtest","producttest"), stmt, 1);
                    stmt.addBatch();

                    stmt.executeBatch();

                    return true;
                } finally {
                    DBUtil.closeQuietly(stmt);
                }
            }
        });
    }
    
    public void loadInitialPriceAssociationData() {
        executeTransaction(new Transaction<Boolean>() {
            @Override
            public Boolean execute(Connection conn) throws SQLException {
                PreparedStatement stmt = null;

                try {
                    stmt = conn.prepareStatement("insert into priceAssociations (itemid, price, storename) values (?, ?, ?)");
                    storePriceAssoicationNoId(new PriceAssociation(3, 1.25,"The Market"), stmt, 1);
                    stmt.addBatch();
                    storePriceAssoicationNoId(new PriceAssociation(1, 1.30, "The Shop"), stmt, 1);
                    stmt.addBatch();
                    storePriceAssoicationNoId(new PriceAssociation(2, 2.68, "The Market"), stmt, 1);
                    stmt.addBatch();
                    storePriceAssoicationNoId(new PriceAssociation(1, 2.00, "The Market"), stmt, 1);
                    stmt.addBatch();
                    storePriceAssoicationNoId(new PriceAssociation(3, 1.75, "The Shop"), stmt, 1);
                    stmt.addBatch();

                    stmt.executeBatch();

                    return true;
                } finally {
                    DBUtil.closeQuietly(stmt);
                } 
            }       
        });
    }

    protected void storeItemNoId(Item item, PreparedStatement stmt, int index) throws SQLException {

        stmt.setString(index++, item.getBrand());
        stmt.setString(index++, item.getProduct());
 
    }
   
    protected void loadItem(Item item, ResultSet resultSet, int index) throws SQLException {
		item.setId(resultSet.getInt(index++));
		item.setBrand(resultSet.getString(index++));
		item.setProduct(resultSet.getString(index++));
	} /**/
   
    public static void main(String[] args) throws IOException {
        RealDatabase db = new RealDatabase();
        System.out.println("Creating tables...");
        db.createItemTables();
        db.createPriceAssociationTables();
        System.out.println("Loading initial data...");
        db.loadInitialItemData();
        db.loadInitialPriceAssociationData();
        System.out.println("Done!");
       
    }

	@Override
	public void addItem(final Item item) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet generatedKeys = null;
				
				try {
					Item temp = getItem(item.getBrand(), item.getProduct());
					if(temp == null){
						//check to see if item already exists so there is not duplicate data
						stmt = conn.prepareStatement(
								"insert into items (brand, product) values (?, ?)",
								PreparedStatement.RETURN_GENERATED_KEYS
						);
						
						storeItemNoId(item, stmt, 1);
	
						// Attempt to insert the item
						stmt.executeUpdate();
	
						// Determine the auto-generated id
						generatedKeys = stmt.getGeneratedKeys();
						if (!generatedKeys.next()) {
							throw new SQLException("Could not get auto-generated key for inserted Users");
						}
						
						item.setId(generatedKeys.getInt(1));
					}
					return true;
				} finally {
					DBUtil.closeQuietly(generatedKeys);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public Item getItem(final String brand, final String product) {
		return executeTransaction(new Transaction<Item>() {
			@Override
			public Item execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement("select items.* from items where items.brand = ? and items.product = ?");
					stmt.setString(1, brand);
					stmt.setString(2, product);
					
					resultSet = stmt.executeQuery();
					
					if (!resultSet.next()) {
						// No such item
						return null;
					}
					
					Item i = new Item();
					loadItem(i, resultSet, 1);
					return i;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public void addPriceInfo(final PriceAssociation pa) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet generatedKeys = null;
				
				try {
					stmt = conn.prepareStatement(
							"insert into priceAssociations (itemId, price, storename) values (?, ?, ?)",
							PreparedStatement.RETURN_GENERATED_KEYS
					);
					
					storePriceAssoicationNoId(pa, stmt, 1);

					// Attempt to insert the item
					stmt.executeUpdate();

					// Determine the auto-generated id
					generatedKeys = stmt.getGeneratedKeys();
					if (!generatedKeys.next()) {
						throw new SQLException("Could not get auto-generated key for inserted Users");
					}
					
					pa.setPriceInfoId(generatedKeys.getInt(1));
					
					return true;
				} finally {
					DBUtil.closeQuietly(generatedKeys);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
		
	}

	@Override
	public PriceAssociation getPriceInfo(final int itemId) {
		return executeTransaction(new Transaction<PriceAssociation>() {
			@Override
			public PriceAssociation execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement("select priceAssociations.* from priceAssociations where priceAssociations.itemid = ?");
					stmt.setInt(1, itemId);
					
					resultSet = stmt.executeQuery();
					
					if (!resultSet.next()) {
						//Item not Found
						return null;
					}
					
					PriceAssociation pa = new PriceAssociation();
					loadPriceAssoication(pa, resultSet, 1);
					return pa;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	public PriceAssociation[] getAllPriceAssociations(final int itemId){
		return executeTransaction(new Transaction<PriceAssociation[]>() {
			@Override
			public PriceAssociation[] execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement("select priceAssociations.* from priceAssociations where priceAssociations.itemid = ?");
					stmt.setInt(1, itemId);
					resultSet = stmt.executeQuery();
					
					List<PriceAssociation> result  = new ArrayList<PriceAssociation>();
					while (resultSet.next()) {
						PriceAssociation pa = new PriceAssociation();
						loadPriceAssoication(pa, resultSet, 1);

						result.add(pa);
					}
					
					
					//Sort the array before it is added so that the lowest prices
					//are near the beginning of the array
					QuickSort q = new QuickSort();
					q.sort(result);
					
					return result.toArray(new PriceAssociation[result.size()]);
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public PriceAssociation[] cheapestPrice(Item item) {
		PriceAssociation cheapest = new PriceAssociation(-1, 1000000,"Unavailable");
    	PriceAssociation[] plist = new PriceAssociation[5];
    	plist[0] = cheapest; //have a default value so that the item is still in the plist
    	
    	if(getItem(item.getBrand(), item.getProduct()) != null){
	       int id = getItem(item.getBrand(), item.getProduct()).getId();
	       PriceAssociation[] temp =  getAllPriceAssociations(id);
	       
	       for(int i = 0; i < 5; i++){
	    	   if(i< temp.length){
		    	   if(temp[i] != null){
		    		   plist[i] = temp[i];
		    	   }
	    	   }
	       }

    	}
        return plist;
	}

	@Override
	public List<PriceAssociation> cheapestAll(Item[] ItemList) {
		List<PriceAssociation> allPrices = new ArrayList<PriceAssociation>();
        if(ItemList != null){
	        for(Item i : ItemList){
	        	
	        	PriceAssociation[] pL = cheapestPrice(i);
	        	for(int j = 0; j<5; j++){
	        		if(pL[j] != null){
	        			allPrices.add(pL[j]);
	        		}
	        	}
	        }
        }else{
        	System.out.println("List was null.");
        }
        
        return allPrices;
	}

	public class QuickSort {
	    private List<PriceAssociation>  arr;
	    private int size;
	
	    public void sort(List<PriceAssociation> pL) {
	        if (pL == null || pL.size() == 0) {
	            return;
	        }
	        this.arr = pL;
	        size = pL.size();
	        quickSort(0, size - 1);
	    }
	
	    private void quickSort(int lowerIndex, int higherIndex) {
	        int i = lowerIndex;
	        int j = higherIndex;
	        // calculate pivot number, I am taking pivot as middle index number
	        double pivot = arr.get(lowerIndex+(higherIndex-lowerIndex)/2).getPrice();
	        // Divide array in half and check pivot value
	        while (i <= j) {
	            while (arr.get(i).getPrice() < pivot) {
	                i++;
	            }
	            while (arr.get(j).getPrice() > pivot) {
	                j--;
	            }
	            if (i <= j) {
	                exchangeTotals(i, j);
	                i++;
	                j--;
	            }
	        }
	        // call quickSort() method recursively
	        if (lowerIndex < j){
	            quickSort(lowerIndex, j);
	        }if (i < higherIndex){
	            quickSort(i, higherIndex);
	        }
	    }
	
	    private void exchangeTotals(int i, int j) {
	    	PriceAssociation temp = arr.get(i);
	        arr.set(i, arr.get(j));
	        arr.set(j, temp);
	    }
	
	}
}
