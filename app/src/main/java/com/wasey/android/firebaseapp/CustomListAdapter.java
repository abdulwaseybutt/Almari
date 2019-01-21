package com.wasey.android.firebaseapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
    private Context context; //context
    private ArrayList<Mobile> mobilelist;
    private ArrayList<Mobile> items; //data source of the list adapter

    //public constructor
    public CustomListAdapter(Context context, ArrayList<Mobile> items) {
        this.context = context;
        this.items = items;
        this.mobilelist=new ArrayList<Mobile>();
        this.mobilelist.addAll(items);
    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.mobile_list_row, parent, false);
        }

        // get current item to be displayed
        Mobile currentItem = (Mobile) getItem(position);

        // get the TextView for item name and item description
        ImageView Imageviewitem=(ImageView) convertView.findViewById(R.id.Phone_pic);
        TextView textViewItemName = (TextView)
                convertView.findViewById(R.id.Phone_name);
        TextView textViewItemPrice = (TextView)
                convertView.findViewById(R.id.Phone_Price);

        //sets the text for item name and item description from the current item object
        Picasso.get()
                .load(currentItem.getImg())
                .fit()
                .centerCrop()
                .into(Imageviewitem);
        textViewItemName.setText(currentItem.getName());
        textViewItemPrice.setText(currentItem.getPrice()+"$");

        // returns the view for the current row
        return convertView;
    }




    /*public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(conslist);
        } else {
            for (Contacts wp : conslist) {
                if (wp.getItemName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    items.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }*/


}