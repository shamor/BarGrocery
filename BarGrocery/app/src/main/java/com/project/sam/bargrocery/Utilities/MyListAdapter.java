package com.project.sam.bargrocery.Utilities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.sam.bargrocery.R;

import java.util.ArrayList;
import java.util.List;
import modelclasses.Item;

/**
 * Created by sam on 2/24/2015.
 */
public class MyListAdapter extends ArrayAdapter {

    private Context context;
    List<Item> rowlist = new ArrayList<Item>();
    List<Integer> quants = new ArrayList<Integer>();


    public MyListAdapter(Context context, List<Item> rowlist, List<Integer> quants) {
        super(context, R.layout.custom_list_activity2);
        this.context = context;
        this.rowlist = rowlist;
        this.quants = quants;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row =  convertView;
        MyHolder holder = null;

        //associate adapter with the context's view widgets
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
        if(rowlist.get(position).getBrand() != null) {
            holder.brand.setText(rowlist.get(position).getBrand().toString());
            holder.product.setText(rowlist.get(position).getProduct().toString());
            holder.quant.setText(quants.get(position).toString());
        }

        return row;
    }

    //Simple class to hold textview widgets
    public static class MyHolder {
       TextView brand;
       TextView product;
       TextView quant;
    }



}