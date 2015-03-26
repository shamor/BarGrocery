package com.project.sam.bargrocery.backend.DatabasePersistance;

import com.project.sam.modelclases.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sam on 3/16/2015.
 */
public class FakeDatabase implements IDatabase {

    private List<Items> items;
    private List<PriceAssociation> prices;

    private int Id;
    private int PId;

    public FakeDatabase() {
        //FakeDatabase info holders
        items = new ArrayList<Items>();
        prices = new ArrayList<PriceAssociation>();
        Items item = new Items("brandtest", "producttest");
        item.setId(0);
        PriceAssociation pa = new PriceAssociation(0, 1.00, "The Market");
        pa.setPriceInfoId(0);
        Id = 1;
        PId = 1;
    }

    public PriceAssociation cheapestPrice(Items item){
       int id = getItem(item.getBrand(), item.getProduct()).getId();
       PriceAssociation cheapest = new PriceAssociation(-1, 10000000.00,"");
        for(PriceAssociation pa : prices){
            if(pa.getItemId() == id && (pa.getPrice() < cheapest.getPrice())){
                cheapest = pa;
            }
        }
        return cheapest;
    }

    public List<PriceAssociation> cheapestAll(Items[] itemsList){
        List<PriceAssociation> allPrices = new ArrayList<PriceAssociation>();
        for(Items i : itemsList){
            allPrices.add(cheapestPrice(i));
        }
        return allPrices;

    }
    public void addItem(Items item){
            item.setId(Id++);
            items.add(item);
    }

    public Items getItem(String brand, String product){
        for(Items i : items){
            if(i.getProduct().equals(product) && i.getBrand().equals(brand)){
                return i;
            }
        }
        return null;
    }

    public void addPriceInfo(PriceAssociation pa){
        pa.setPriceInfoId(PId++);
        prices.add(pa);
    }

    public PriceAssociation getPriceInfo(int itemId){
        for(PriceAssociation pa : prices){
            if(pa.getItemId() == itemId){
                return pa;
            }
        }
        return null;
    }



}
