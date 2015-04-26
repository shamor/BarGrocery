package Controllers;

import Persistance.Database;
import Persistance.IDatabase;
import modelclasses.Item;


/**
 * Created by Samantha Hamor on 3/22/2015.
 */
public class AddItemController {
    public Item addItem(Item item){
        IDatabase db = Database.getInstance();
        return db.addItem(item);
    }
}
