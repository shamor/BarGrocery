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
        Item item0 = new Item("brandtest", "producttest");
        Item item1 = new Item("bt", "pt");
        Item item2 = new Item("b", "p");
        item0.setId(0);
        item1.setId(1);
        item2.setId(2);
        Item.add(item0);
        Item.add(item1);
        Item.add(item2);
        Id = 3;
        PriceAssociation pa0 = new PriceAssociation(0, 1.00, "The Market");
        PriceAssociation pa1 = new PriceAssociation(1, 1.20, "The Shop");
        PriceAssociation pa2 = new PriceAssociation(2, 1.23, "The Shop");
        pa0.setPriceInfoId(0);
        pa1.setPriceInfoId(1);
        pa2.setPriceInfoId(2);
        prices.add(pa0);
        prices.add(pa1);
        prices.add(pa2);
        PId = 3;
    }
    
    @Override
    public PriceAssociation cheapestPrice(Item item){
    	PriceAssociation cheapest = new PriceAssociation(-1, 10000000.00,"NONE");
    	if(getItem(item.getBrand(), item.getProduct()) != null){
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
        for(Item i : Item){
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
