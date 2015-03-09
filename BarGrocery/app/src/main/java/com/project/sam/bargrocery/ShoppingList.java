package com.project.sam.bargrocery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ShoppingList extends Activity {
    MyListAdapter.MyHolder holder = new MyListAdapter.MyHolder();
    List<MyListAdapter.MyHolder> rows = new ArrayList<MyListAdapter.MyHolder>();
    private  MyListAdapter adapter;
    private int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        ListView lv = (ListView) findViewById(R.id.listView);

        //add a holder object to the listview


        //View Variables
        adapter = new MyListAdapter(this,rows,pos);
        lv.setAdapter(adapter);
        adapter.add(holder);


        Button submitButton = (Button) findViewById(R.id.submitBtn);

        //Submit the shopping list button
        submitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Results.class);
                startActivity(intent);
            }
        });

        addRow(1);


    }//onCreate()
    public void addRowhandler(View v){

        pos++;
        adapter.add(holder);

        MyListAdapter.MyHolder  h = ( MyListAdapter.MyHolder) adapter.getItem(adapter.getCount()-1);
        String d = "n:" + adapter.getCount() + ".";

        Toast.makeText(this, h.brand.getText().toString(), Toast.LENGTH_SHORT).show();
       // adapter.notifyDataSetChanged();
    }//addRowhandler(View v)

    public void addRow(int id) {
/*
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.relativeLayout);
        RelativeLayout.LayoutParams newParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        EditText brand = new EditText(this);
        brand.setEms(5);
        brand.setHeight(96);
        brand.setHint("Brand");
        newParams.setMargins(0,0,6,12);
        brand.setBackgroundColor(Color.parseColor("#fffffce0"));
      if(id!=1) {
            newParams.addRule(RelativeLayout.BELOW, id-4);
        }
        brand.setLayoutParams(newParams);
        brand.setId(id);
        rl.addView(brand);

        RelativeLayout.LayoutParams newParams0 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        EditText product = new EditText(this);
        product.setHint("Product");
        product.setEms(7);
        product.setHeight(96);
        product.setBackgroundColor(Color.parseColor("#fffff00f"));
        newParams0.setMargins(0,0,6,12);
        newParams0.addRule(RelativeLayout.RIGHT_OF,id);
       if(id!=1) {
            newParams0.addRule(RelativeLayout.BELOW, id-3);
        }
        product.setLayoutParams(newParams0);
        product.setId(id+1);//editor error, can be ignored
        rl.addView(product);


        RelativeLayout.LayoutParams newParams1 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        EditText quant = new EditText(this);
        quant.setHint("#");
        quant.setEms(2);
        quant.setHeight(96);
        quant.setBackgroundColor(Color.parseColor("#fffffce0"));
        newParams1.setMargins(0,0,6,12);
        newParams1.addRule(RelativeLayout.RIGHT_OF,id+1);
        if(id!=1) {
            newParams1.addRule(RelativeLayout.BELOW, id-2);
        }
        quant.setLayoutParams(newParams1);
        quant.setId(id+2);//editor error, can be ignored
        rl.addView(quant);

        RelativeLayout.LayoutParams newParams2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        Button addButton = new Button(this);
        addButton.setText("+");
        addButton.setTextSize(20);
        addButton.setBackgroundColor(Color.parseColor("#fffdaa15"));
        newParams2.setMargins(0,0,6,12);
        newParams2.addRule(RelativeLayout.RIGHT_OF,id+2);
        if(id!=1) {
            newParams2.addRule(RelativeLayout.BELOW, id-1);
        }
        addButton.setLayoutParams(newParams2);
        addButton.setId(id+3);
        rl.addView(addButton);

        final int id2 = id;
        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){
                    addRow(id2+4);
            }
        });*/
    }//addRow(int id)

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
}
