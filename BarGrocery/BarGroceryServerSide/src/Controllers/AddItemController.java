package Controllers;

import Persistance.Database;
import Persistance.IDatabase;
import modelclasses.Item;


/**
 * Created by Samantha Hamor on 3/22/2015.
 */
public class AddItemController {
    public void addItem(Item item){
        IDatabase db = Database.getInstance();
        db.addItem(item);
    }
}
