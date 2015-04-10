package Controllers;

import Persistance.Database;
import Persistance.IDatabase;
import modelclasses.PriceAssociation;

/**
 * Created by Samantha Hamor on 3/22/2015.
 */
public class addPriceController {
    public void addItem(PriceAssociation p) {
        IDatabase db = Database.getInstance();
        db.addPriceInfo(p);
    }
}
