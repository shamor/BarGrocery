package com.project.sam.bargrocery.Pages;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.project.sam.bargrocery.R;
import com.project.sam.bargrocery.Utilities.MyListAdapter2;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import modelclasses.Item;
import modelclasses.PriceAssociation;


public class Results extends Activity {
    List<String> headers;
    List<headerHolder> stringAssoications = new ArrayList<headerHolder>();
    Map<String, List<String>> itemMap;
    ExpandableListView expListView;
    private ProgressBar pb;

    PriceAssociation[] resultsArr = ShoppingList.result;
    List<Item> items = ShoppingList.items;
    List<Integer> quants = ShoppingList.quantities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        pb =( ProgressBar) findViewById(R.id.progressBar);



       expListView = (ExpandableListView) findViewById(R.id.expandableListView);

       //wait for information to be returned from shoppinglist
       int counter = 0;
       while(resultsArr==null ){
            resultsArr = ShoppingList.result;
            pb.setProgress(counter);
            counter++;
       }
       counter = 0;
       pb.setVisibility(View.GONE);

       createHeaders();

       createChildren();

      ExpandableListAdapter expListAdapter = new MyListAdapter2(this, headers,itemMap);

       expListView.setAdapter(expListAdapter);

    }
    private void createHeaders() {
        headers = new ArrayList<String>();

        //add items to the resultsArr/ store name area
        if(resultsArr == null) {
            headers = null;
            Toast.makeText(Results.this, "Your list must contain at least one item.", Toast.LENGTH_SHORT).show();
        }else if (resultsArr.length == 1) {
            headers.add(resultsArr[0].getLocation() + "          $" + resultsArr[0].getPrice());
        } else {

            int CurrentPriceId = -1;
            int currentItem = -1;


            for (PriceAssociation p : resultsArr) {
                if (p.getItemId() != CurrentPriceId) {//make sure to keep the results lined up with the items
                    CurrentPriceId = p.getItemId();
                    currentItem++;
                } else if (p.getItemId() == -1 && CurrentPriceId == -1) {
                    currentItem++;
                }

                //check to see if the item is already in the list
                boolean add = true;
                for (int i = 0; i < stringAssoications.size(); i++) {
                    if (stringAssoications.get(i).Store.equals(p.getLocation())) {
                       add = false;
                       stringAssoications.get(i).total += (p.getPrice() * quants.get(currentItem));
                        if(!p.getLocation().equals("Unavailable")) {
                            stringAssoications.get(i).itemsAndPrices.add(items.get(currentItem).getProduct() + " " + toDollar(p.getPrice()));
                        }else{
                            stringAssoications.get(i).itemsAndPrices.add(items.get(currentItem).getProduct() + "      $--.--");
                        }
                        break;
                    }
                }

                //If a store does not exist in the list then add it
                if (add == true) {
                    headerHolder holder = new headerHolder();
                    holder.Store = p.getLocation();
                    if(!p.getLocation().equals("Unavailable")) {
                        holder.total += p.getPrice() * quants.get(currentItem);
                        holder.itemsAndPrices.add(items.get(currentItem).getProduct() + "      " + toDollar(p.getPrice()));
                   }else{
                       holder.itemsAndPrices.add(items.get(currentItem).getProduct() + "      $--.--");
                   }
                    stringAssoications.add(holder);
                }
            }
            for(headerHolder h : stringAssoications){
                headers.add(h.Store + "      " + toDollar(h.total));
            }

        }
    }

    private void createChildren() {

        itemMap = new LinkedHashMap<String, List<String>>();
            int i = 0;
            for(String g : headers){
                  itemMap.put(g,stringAssoications.get(i).itemsAndPrices);
                i++;
            }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static class headerHolder{
        public String Store;
        public double total;
        public List<String> itemsAndPrices = new ArrayList<String>();
    }

    private String toDollar(double val){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String temp = formatter.format(val);
        return temp;
    }

    private  List<headerHolder> sortResuts(){
        List<headerHolder> temp = stringAssoications;
        return temp;
    }
}