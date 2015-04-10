package Persistance;

import modelclasses.Item;
import modelclasses.PriceAssociation;

import java.util.List;

/**
 * Created by Samamantha Hamor on 3/16/2015.
 * Database persistence methods
 */
public interface IDatabase {
    //function headers for database operations

    public void addItem(Item item);
    public Item getItem(String brand, String product);
    public void addPriceInfo(PriceAssociation pa);
    public PriceAssociation getPriceInfo(int itemId);
    public PriceAssociation cheapestPrice(Item item);
    public List<PriceAssociation> cheapestAll(Item[] itemsList);

}
