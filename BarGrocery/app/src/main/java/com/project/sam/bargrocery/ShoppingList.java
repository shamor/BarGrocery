package com.project.sam.bargrocery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.project.sam.bargrocery.controllers.*;
import java.util.ArrayList;
import java.util.List;
import modelclasses.*;



public class ShoppingList extends Activity {

    PriceAssociation[] result = null;
    List<stringHolder> rows = new ArrayList<stringHolder>();
    List<Item> items = new ArrayList<Item>();

    List<Integer> quantities = new ArrayList<Integer>();
    private MyListAdapter adapter;
    stringHolder sh = new stringHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shopping_list);
        ListView lv = (ListView) findViewById(R.id.listView);

        //associate an adapter with the listview
        adapter = new MyListAdapter(this, rows);
        lv.setAdapter(adapter);
        Button submitButton = (Button) findViewById(R.id.submitBtn);

        //Submit the shopping list button
        submitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

               Item[] itemL = items.toArray(new Item[items.size()]);

               new MyTask().execute(itemL);

                if(result!=null) {
                    Toast.makeText(ShoppingList.this, result[0].getLocation().toString(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ShoppingList.this, "Could not find any items matches.", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), Results.class);
                startActivity(intent);
            }
        });


    }//onCreate()

    /**
     * Add a shopping list item to the listview whenever the add button is pressed
     * @param v
     */
    public void addRowhandler(View v) {

        //associate the edittexts from the shopping list xml to values
        EditText b = (EditText) findViewById(R.id.brandET);
        EditText p = (EditText) findViewById(R.id.productET);
        EditText n = (EditText) findViewById(R.id.numET);

        //pull the values from the edittexts and add to the rows array for the adpater
        stringHolder sh = new stringHolder();
        sh.brand = b.getText().toString();
        sh.product = p.getText().toString();
        sh.quantity = Integer.parseInt(n.getText().toString());

        //Now add them to items and quantities list for the postrequest
        Item newItem = new Item(sh.brand,sh.product);
        items.add(newItem);
        quantities.add(sh.quantity);

        //reset Edittexts
        b.setText("");
        p.setText("");
        n.setText("");

        //update the adapter
        rows.add(sh);
        adapter.add(rows);


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

    /**
     * simple class to hold information about each row in a shopping list
     * May be changed later in order to create a groceryitem class
     */
    public class stringHolder {
        String brand;
        String product;
        Integer quantity;
    }

   // public PriceAssociation[] postList(List<Item> itemList, List<Integer> quants) throws URISyntaxException,  IOException {
    class MyTask extends AsyncTask<Item[],Integer,PriceAssociation[]> {

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


