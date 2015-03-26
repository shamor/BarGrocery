package com.project.sam.bargrocery.backend.Controllers;

import com.project.sam.bargrocery.backend.DatabasePersistance.Database;
import com.project.sam.bargrocery.backend.DatabasePersistance.IDatabase;
import com.project.sam.modelclases.Items;
import com.project.sam.modelclases.PriceAssociation;

import java.util.List;

/**
 * Created by Samantha Hamor on 3/23/2015.
 */
public class getLowPricesController {
    public List<PriceAssociation> getlowPrice(Items[] i){
        IDatabase db = Database.getInstance();
        return db.cheapestAll(i);
    }
}
