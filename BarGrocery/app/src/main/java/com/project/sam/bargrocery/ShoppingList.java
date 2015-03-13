package com.project.sam.bargrocery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;


public class ShoppingList extends Activity {

    List<stringHolder> rows = new ArrayList<stringHolder>();
    private  MyListAdapter adapter;
    stringHolder sh = new stringHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        ListView lv = (ListView) findViewById(R.id.listView);

        //associate an adapter with the listview
        adapter = new MyListAdapter(this,rows);
        lv.setAdapter(adapter);


        Button submitButton = (Button) findViewById(R.id.submitBtn);

        //Submit the shopping list button
        submitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Results.class);
                startActivity(intent);
            }
        });

    }//onCreate()

    /**
     * Add a shopping list item to the listview whenever the add button is pressed
     * @param v
     */
    public void addRowhandler(View v){

        //associate the edittexts from the shopping list xml to values
        EditText b = (EditText) findViewById(R.id.brandET);
        EditText p = (EditText) findViewById(R.id.productET);
        EditText n = (EditText) findViewById(R.id.numET);

        //pull the values from the edittexts and add to the rows array
        stringHolder sh = new stringHolder();
        sh.brand = b.getText().toString();
        sh.product = p.getText().toString();
        sh.quantity = n.getText().toString();

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
     * May be changed later inorder to create a groceryitem class
     */
    public class stringHolder{
        String brand;
        String product;
        String quantity;
    }
}
