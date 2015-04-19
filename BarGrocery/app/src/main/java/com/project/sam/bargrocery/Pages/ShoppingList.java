package com.project.sam.bargrocery.Pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.project.sam.bargrocery.R;
import com.project.sam.bargrocery.Utilities.MyListAdapter;
import com.project.sam.bargrocery.controllers.*;
import java.util.ArrayList;
import java.util.List;
import modelclasses.*;



public class ShoppingList extends Activity {

    public static PriceAssociation[] result;
    public static List<Item> items = new ArrayList<Item>();
    public static PriceAssociation[] tester = new PriceAssociation[3];
    public static List<Integer> quantities = new ArrayList<Integer>();
    private MyListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shopping_list);
        ListView lv = (ListView) findViewById(R.id.listView);

        //associate an adapter with the listview
        adapter = new MyListAdapter(this, items,quantities);
        lv.setAdapter(adapter);

        Button addButton = (Button) findViewById(R.id.addBtn);

        ///Submit the shopping list button
        addButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                //associate the edittexts from the shopping list xml to values
                EditText b = (EditText) findViewById(R.id.brandET);
                EditText p = (EditText) findViewById(R.id.productET);
                EditText n = (EditText) findViewById(R.id.numET);

                //check to make sure all fields are filled out
                if(TextUtils.isEmpty(b.getText().toString())){
                    Toast.makeText(ShoppingList.this, "Please choose a brand. Use 'Generic' if brand does not matter.", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(p.getText().toString())){
                    Toast.makeText(ShoppingList.this, "Please enter a product name.", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(n.getText().toString())){
                    Toast.makeText(ShoppingList.this, "Please enter desired amount of product", Toast.LENGTH_SHORT).show();
                }else {
                    addRowhandler(b,p,n);
                }
            }
        });

        Button submitButton = (Button) findViewById(R.id.submitBtn);

        //Submit the shopping list button
        submitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
               if(items.size() == 0){
                   Toast.makeText(ShoppingList.this,"Your list must contain at least one item.", Toast.LENGTH_SHORT).show();
                }else {

                  Item[] itemL = items.toArray(new Item[items.size()]);

                  //execute post
                  new MyTask().execute(itemL);

                   Intent intent = new Intent(getApplicationContext(), Results.class);
                   startActivity(intent);
               }
            }
        });
    }//onCreate()

    /**
     * Add a shopping list item to the listview whenever the add button is pressed
     */
    public void addRowhandler(EditText b,EditText p,EditText n) {

            //Add them to items and quantities list for the postrequest and the List adapter
            Item newItem = new Item(b.getText().toString(), p.getText().toString());
            items.add(newItem);
            quantities.add(Integer.parseInt(n.getText().toString()));

            //reset Edittexts
            b.setText("");
            p.setText("");
            n.setText("");

            //update the adapter
            adapter.add(items);


    }//addRowhandler()


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping_list, menu);
        return true;
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

    //My AsyncTask implementation
    private class MyTask extends AsyncTask<Item[],Integer,PriceAssociation[]> {

        @Override
        protected PriceAssociation[] doInBackground(Item[]... items) {
            sendList sl = new sendList();
            try {
                result = sl.postList(items[0]);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}


