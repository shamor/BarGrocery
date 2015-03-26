package com.project.sam.bargrocery.backend.Controllers;

import com.project.sam.bargrocery.backend.DatabasePersistance.Database;
import com.project.sam.bargrocery.backend.DatabasePersistance.IDatabase;
import com.project.sam.modelclases.Items;

/**
 * Created by sam on 3/22/2015.
 */
public class AddItemController {
    public void addItem(Items item){
        IDatabase db = Database.getInstance();
        db.addItem(item);
    }
}
