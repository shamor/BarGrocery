package com.project.sam.bargrocery;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import java.util.ArrayList;
import java.util.List;


public class ShoppingList extends ActionBarActivity {
List<EditText> editTexts = new ArrayList<EditText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        //View Variables
        //ScrollView sv = (ScrollView) findViewById(R.id.scrollView);

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

    public void addRow(int id) {

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.relativeLayout);
        RelativeLayout.LayoutParams newParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        EditText brand = new EditText(this);
        brand.setHint("Brand");
        brand.setEms(5);
        brand.setHeight(85);
        brand.setBackgroundColor(Color.parseColor("#fffffce0"));
       /**/ if(id!=1) {
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
        product.setHeight(85);
        product.setBackgroundColor(Color.parseColor("#fffff00f"));
        newParams0.addRule(RelativeLayout.RIGHT_OF,id);
       /* */if(id!=1) {
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
        quant.setEms(3);
        quant.setHeight(85);
        quant.setBackgroundColor(Color.parseColor("#fffffce0"));
        newParams1.addRule(RelativeLayout.RIGHT_OF,id+1);
       /**/ if(id!=1) {
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
        addButton.setTextSize(12);
        //addButton.setMaxHeight(50);
       // addButton.setMaxWidth(50);
        addButton.setBackgroundColor(Color.parseColor("#fffdaa15"));
        newParams2.addRule(RelativeLayout.RIGHT_OF,id+2);
       /**/ if(id!=1) {
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
        });
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
