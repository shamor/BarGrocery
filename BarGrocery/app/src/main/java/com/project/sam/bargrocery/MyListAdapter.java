package com.project.sam.bargrocery;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

/**
 * Created by sam on 2/24/2015.
 */
public class MyListAdapter extends ArrayAdapter {

    private int layoutResourceId;
    private Context context;

    public MyListAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MyHolder holder = null;
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        holder = new MyHolder();
        holder.brand = (EditText)row.findViewById(R.id.brandET);
        holder.product = (EditText)row.findViewById(R.id.productET);
        holder.quant = (EditText)row.findViewById(R.id.numET);
        holder.addbtn = (Button)row.findViewById(R.id.addBtn);

        row.setTag(holder);
        return row;
    }

    public static class MyHolder {
        EditText brand;
        EditText product;
        EditText quant;
        Button addbtn;
    }
}