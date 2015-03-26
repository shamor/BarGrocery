package com.project.sam.bargrocery.backend.DatabasePersistance;

import com.project.sam.modelclases.Items;
import com.project.sam.modelclases.PriceAssociation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samamantha Hamor on 3/16/2015.
 * Database persistence methods
 */
public interface IDatabase {
    //function headers for database operations

    public void addItem(Items item);
    public Items getItem(String brand, String product);
    public void addPriceInfo(PriceAssociation pa);
    public PriceAssociation getPriceInfo(int itemId);
    public PriceAssociation cheapestPrice(Items item);
    public List<PriceAssociation> cheapestAll(Items[] itemsList);

}
