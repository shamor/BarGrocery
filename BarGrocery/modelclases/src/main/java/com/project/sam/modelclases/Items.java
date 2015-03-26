package com.project.sam.modelclases;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samantha on 3/16/2015.
 * Basic class to hold information about a single item
 * brand - the brandname of a particular item
        *(Each item may have one brand or many)
        *(To handle this each product/ brand combination is considered to be unique)
 * product - the name of the product
 */
public class Items {

    private String brand;
    private String product;
    private int Id;

    public Items(String brand, String product){
        this.brand = brand;
        this.product = product;
    }

    public void setId(int Id){
        this.Id = Id;
    }

    public String getBrand(){
        return brand;
    }

    public String getProduct(){
        return product;
    }

    public void setBrand(String brand){
        this.brand = brand;
    }

    public void setProduct(String product){
        this.product = product;
    }

    public int getId(){ return Id;}

}
