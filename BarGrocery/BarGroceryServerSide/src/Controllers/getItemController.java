package Controllers;

import Persistance.Database;
import Persistance.IDatabase;
import modelclasses.Item;


/**
 * Created by sam on 3/22/2015.
 */
public class getItemController {
    public Item getItem(String brand, String product){
        IDatabase db = Database.getInstance();
        return db.getItem(brand, product);
    }
}
