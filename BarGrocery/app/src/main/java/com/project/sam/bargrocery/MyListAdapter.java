package com.project.sam.bargrocery;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sam on 2/24/2015.
 */
public class MyListAdapter extends ArrayAdapter {

    private Context context;
    private List<MyHolder> rowArray = new ArrayList<MyHolder>();
    private int position;

    public MyListAdapter(Context context,  List<MyHolder> rowArray, int position) {
        super(context, R.layout.custom_list_activity, rowArray);
        this.context = context;
        this.rowArray = rowArray;
        this.position = position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row =  convertView;//inflater.inflate(R.layout.custom_list_activity, parent, false);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//((Activity) context).getLayoutInflater();

        if(row == null) {

            row = inflater.inflate(R.layout.custom_list_activity, parent, false);

            rowArray.get(position).brand = (EditText) row.findViewById(R.id.brandET);
            rowArray.get(position).product = (EditText) row.findViewById(R.id.productET);
            rowArray.get(position).quant = (EditText) row.findViewById(R.id.numET);
            rowArray.get(position).addbtn = (Button) row.findViewById(R.id.addBtn);

            row.setTag(rowArray.get(position));
        }
        return row;
    }

    public static class MyHolder {
        EditText brand;
        EditText product;
        EditText quant;
        Button addbtn;
    }
}