package Controllers;

import Persistance.Database;
import Persistance.IDatabase;
import modelclasses.PriceAssociation;

/**
 * Created by sam on 3/22/2015.
 */
public class getPriceAssociationController {
    public PriceAssociation getPriceInfo(int id){
        IDatabase db = Database.getInstance();
        return db.getPriceInfo(id);
    }
}
