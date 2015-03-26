package com.project.sam.bargrocery.backend.Controllers;

import com.project.sam.bargrocery.backend.DatabasePersistance.Database;
import com.project.sam.bargrocery.backend.DatabasePersistance.IDatabase;
import com.project.sam.modelclases.PriceAssociation;

/**
 * Created by sam on 3/22/2015.
 */
public class addPriceController {
    public void addItem(PriceAssociation p) {
        IDatabase db = Database.getInstance();
        db.addPriceInfo(p);
    }
}
