package Persistance;

import modelclasses.Item;
import modelclasses.PriceAssociation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sam on 3/16/2015.
 */
public class FakeDatabase implements IDatabase {

    private List<Item> Item;
    private List<PriceAssociation> prices;

    private int Id;
    private int PId;

    public FakeDatabase() {
        //FakeDatabase info holders
        Item = new ArrayList<Item>();
        prices = new ArrayList<PriceAssociation>();
        Item item = new Item("brandtest", "producttest");
        item.setId(0);
        PriceAssociation pa = new PriceAssociation(0, 1.00, "The Market");
        pa.setPriceInfoId(0);
        Item.add(item);
        prices.add(pa);
        Id = 1;
        PId = 1;
    }
    
    @Override
    public PriceAssociation cheapestPrice(Item item){
    	System.out.printf("TEST3\n");
    	PriceAssociation cheapest = new PriceAssociation(-1, 10000000.00,"");
    	if(item != null){
	       int id = getItem(item.getBrand(), item.getProduct()).getId();
	        for(PriceAssociation pa : prices){
	            if(pa.getItemId() == id && (pa.getPrice() < cheapest.getPrice())){
	                cheapest = pa;
	            }
	        }
    	}
        return cheapest;
    }
    
    @Override
    public List<PriceAssociation> cheapestAll(Item[] ItemList){
    	System.out.printf("TEST2\n");
        List<PriceAssociation> allPrices = new ArrayList<PriceAssociation>();
        if(ItemList != null){
	        for(Item i : ItemList){
	            allPrices.add(cheapestPrice(i));
	        }
        }
        
        return allPrices;

    }
    
    @Override
    public void addItem(Item item){
            item.setId(Id++);
            Item.add(item);
    }
    
    @Override
    public Item getItem(String brand, String product){
    	System.out.printf("TEST4\n");
        for(Item i : Item){
        	System.out.printf("Item is: " + i.getProduct().toString() + ", " + i.getBrand().toString());
            if(i.getProduct().equals(product) && i.getBrand().equals(brand)){
                return i;
            }
        }
        
        return null;
    }

    @Override
    public void addPriceInfo(PriceAssociation pa){
        pa.setPriceInfoId(PId++);
        prices.add(pa);
    }

    @Override
    public PriceAssociation getPriceInfo(int itemId){
        for(PriceAssociation pa : prices){
            if(pa.getItemId() == itemId){
                return pa;
            }
        }
        return null;
    }



}