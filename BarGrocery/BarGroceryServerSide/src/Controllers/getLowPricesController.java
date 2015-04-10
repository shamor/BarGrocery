package Controllers;

import Persistance.Database;
import Persistance.IDatabase;
import modelclasses.Item;
import modelclasses.PriceAssociation;

import java.util.List;

/**
 * Created by Samantha Hamor on 3/23/2015.
 */
public class getLowPricesController {
    public List<PriceAssociation> getlowPrice(Item[] i){
        IDatabase db = Database.getInstance();
        return db.cheapestAll(i);
    }
}
