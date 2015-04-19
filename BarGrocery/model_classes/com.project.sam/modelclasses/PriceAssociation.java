package modelclasses;

/**
 * Model class to store price information for any brand/product association
 * each location had on price associated to it
 * Created by sam on 3/16/2015.
 */
public class PriceAssociation {
    private int priceInfoId;
    private int itemId;
    private double price;
    private String location;
    
    public PriceAssociation(){
    	
    }

   public PriceAssociation(int itemId, double price, String location){
        this.itemId = itemId;
        this.price = price;
        this.location = location;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setItemId(int itemId) {
            this.itemId = itemId;
    }

    public void setPriceInfoId(int priceInfoId){
        this.priceInfoId = priceInfoId;
    }

    public int getItemId(){
        return itemId;
    }

    public int getPriceInfoId(){
        return priceInfoId;
    }

    public String getLocation(){
        return location;
    }

    public double getPrice(){
        return price;
    }
}
