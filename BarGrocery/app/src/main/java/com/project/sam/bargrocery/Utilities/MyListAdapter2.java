package com.project.sam.bargrocery.Utilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.project.sam.bargrocery.R;

import java.util.List;
import java.util.Map;

/**
 * Created by sam on 4/11/2015.
 */
public class MyListAdapter2 extends BaseExpandableListAdapter {

       private Activity context;
        private Map<String, List<String>> childItems;
        private List<String> headerStores;

        public MyListAdapter2(Activity context, List<String> headerStores, Map<String, List<String>> childItems) {

            this.context = context;
            this.childItems = childItems;
            this.headerStores = headerStores;
        }

        public Object getChild(int groupPosition, int childPosition) {
            return this.childItems.get(this.headerStores.get(groupPosition)).get(childPosition);
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }


        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            String itemPrice = (String) getChild(groupPosition, childPosition);


            if (convertView == null) {
                LayoutInflater inflater = this.context.getLayoutInflater();
                convertView = inflater.inflate(R.layout.custom_list_activity0, null);
            }

            TextView itemText = (TextView) convertView.findViewById(R.id.itemTV);
            TextView priceText = (TextView) convertView.findViewById(R.id.priceTV);
            String Istring = itemPrice.substring(0,itemPrice.indexOf("$")-1);
            String Pstring = itemPrice.substring(itemPrice.indexOf("$"));
            itemText.setText(Istring);
            priceText.setText(Pstring);
            return convertView;
        }

        public int getChildrenCount(int groupPosition) {
            return childItems.get(headerStores.get(groupPosition)).size();
        }

        public Object getGroup(int groupPosition) {
            return headerStores.get(groupPosition);
        }

        public int getGroupCount() {
            return headerStores.size();
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String StorenameTotal = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.custom_list_activity1,
                        null);
            }

            TextView total = (TextView) convertView.findViewById(R.id.totalTV);
            TextView store = (TextView) convertView.findViewById(R.id.storeTV);
            total.setFocusable(false);
            store.setFocusable(false);
            String Sstring = StorenameTotal.substring(0,StorenameTotal.indexOf("$")-1);
            String Tstring;
            if(StorenameTotal.contains("Unavailable")) {
                Tstring = "No Prices Listed";
            }else{
               Tstring = StorenameTotal.substring(StorenameTotal.indexOf("$"));
            }
            store.setText(Sstring);
            total.setText(Tstring);
            return convertView;
        }

        public boolean hasStableIds() {
           return true;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
   }