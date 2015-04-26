package com.project.sam.bargrocery.Pages;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.project.sam.bargrocery.controllers.postItem;
import com.project.sam.bargrocery.controllers.postPrice;
import com.project.sam.bargrocery.R;
import com.project.sam.bargrocery.controllers.sendList;

import modelclasses.*;

/**
 * Created by sam on 4/10/2015.
 */
public class ReportPrice extends Activity {
    Integer pInt = 0;
    Item returnedItem = null;
    Item lastItem = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(ReportPrice.this, "Not implemted.", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.submit_activity);

        final EditText storetext = (EditText) findViewById(R.id.storeET);
        final EditText loctext = (EditText) findViewById(R.id.locationET);
        final EditText brandtext = (EditText) findViewById(R.id.rbrandET);
        final EditText producttext = (EditText) findViewById(R.id.rproductET);
        final EditText pricetext = (EditText) findViewById(R.id.priceET);

        Button reportButton = (Button) findViewById(R.id.reporttBtn);
        reportButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                if(TextUtils.isEmpty(storetext.getText().toString()) || TextUtils.isEmpty(loctext.getText().toString()) || TextUtils.isEmpty(brandtext.getText().toString()) ||
                        TextUtils.isEmpty(producttext.getText().toString()) || TextUtils.isEmpty(pricetext.getText().toString())){
                    Toast.makeText(ReportPrice.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                }else{
                   Item newItem = new Item(brandtext.getText().toString(), producttext.getText().toString());
                    //execute Item post
                    new MyItemTask().execute(newItem);

                    while(returnedItem == null || returnedItem.equals(lastItem)){
                        ;//do nothing until item comes back
                    }

                    //USe the new Item Id to associate it with a price
                    if(returnedItem!=null) {
                        lastItem = returnedItem;
                        PriceAssociation newPrice = new PriceAssociation(returnedItem.getId(), Double.parseDouble(pricetext.getText().toString()), storetext.getText().toString());
                        //execute Price post
                        new MyPriceTask().execute(newPrice);
                    }else{
                      Toast.makeText(ReportPrice.this, "Error: Price not posted.", Toast.LENGTH_SHORT).show();
                    }

                    storetext.setText("");
                    pricetext.setText("");
                    producttext.setText("");
                    brandtext.setText("");
                    loctext.setText("");

                }

            }
        });

        Button listButton = (Button) findViewById(R.id.shopListBtn);
        listButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShoppingList.class);
                startActivity(intent);
            }
        });

    }
    //My AsyncTask implementation
    private class MyItemTask extends AsyncTask<Item,Integer,Item> {

        @Override
        protected Item doInBackground(Item... items) {
            postItem pI = new postItem();
            try {
                returnedItem = pI.postItem(items[0]);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return returnedItem;
        }
    }
    //My AsyncTask implementation
    private class MyPriceTask extends AsyncTask<PriceAssociation,Integer,Integer> {

        @Override
        protected Integer doInBackground(PriceAssociation... price) {
            postPrice pP = new postPrice();
            try {
                pInt = pP.postPrice(price[0]);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return pInt;
        }
    }

}
