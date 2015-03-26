package com.project.sam.bargrocery.backend.Controllers;

import com.project.sam.bargrocery.backend.DatabasePersistance.Database;
import com.project.sam.bargrocery.backend.DatabasePersistance.IDatabase;
import com.project.sam.modelclases.PriceAssociation;

/**
 * Created by sam on 3/22/2015.
 */
public class getPriceAssociationController {
    public PriceAssociation getPriceInfo(int id){
        IDatabase db = Database.getInstance();
        return db.getPriceInfo(id);
    }
}
