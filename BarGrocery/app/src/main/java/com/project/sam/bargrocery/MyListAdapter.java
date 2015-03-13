package com.project.sam.bargrocery;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sam on 2/24/2015.
 */
public class MyListAdapter extends ArrayAdapter {

    private Context context;
    MyHolder passedHolder = new MyHolder();
    List<ShoppingList.stringHolder> rowlist = new ArrayList<ShoppingList.stringHolder>();


    public MyListAdapter(Context context, List<ShoppingList.stringHolder> rowlist  ) {//String[] values
        super(context, R.layout.custom_list_activity2);
        this.context = context;
        this.rowlist = rowlist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row =  convertView;
        MyHolder holder = null;

            if (row == null) {
                LayoutInflater inflater =((Activity) context).getLayoutInflater();
                holder = new MyHolder();
                row = inflater.inflate(R.layout.custom_list_activity2, parent, false);

                holder.brand = (TextView) row.findViewById(R.id.brandtv);
                holder.product = (TextView) row.findViewById(R.id.producttv);
                holder.quant = (TextView) row.findViewById(R.id.quanttv);

                row.setTag(holder);

            } else {
                holder = (MyHolder) row.getTag();
            }

        //set the textview values to the data collected from the edittexts
        if(rowlist.get(position).brand != null) {
            holder.brand.setText(rowlist.get(position).brand);
            holder.product.setText(rowlist.get(position).product);
            holder.quant.setText(rowlist.get(position).quantity);
        }

        return row;
    }

    public static class MyHolder {
       TextView brand;
       TextView product;
       TextView quant;
    }



}