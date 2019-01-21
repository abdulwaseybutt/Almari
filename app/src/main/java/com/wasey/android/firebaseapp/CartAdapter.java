package com.wasey.android.firebaseapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CartAdapter extends ArrayAdapter<Cart> {
    private ArrayList<Cart> cart;
    public CartAdapter(Context context, ArrayList<Cart> c){
        super(context,0,c);
        this.cart= c;
    }

    @SuppressLint("ResourceType")
    public View getView(int position, View convertView, ViewGroup parent) {
        Cart temp = getItem(position);
        if (convertView == null) {

            LinearLayout layout = new LinearLayout(getContext());
            layout.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
            layout.setOrientation(LinearLayout.HORIZONTAL);

//            ImageView image= new ImageView(getContext());
//            image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//            image.getLayoutParams().height = 100;
//            image.getLayoutParams().width = 100;
//            image.setId(1);
//            layout.addView(image);
            LinearLayout layout2= new LinearLayout(getContext());
            layout2.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
            layout2.setOrientation(LinearLayout.VERTICAL);

            TextView name = new TextView(getContext());
            name.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            name.setTextSize(20);
            name.setTextColor(Color.parseColor("#000000"));
            name.setId(1);
            layout2.addView(name);

            TextView desc= new TextView(getContext());
            desc.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            desc.setId(2);
            layout2.addView(desc);

            layout.addView(layout2);
            convertView = layout;
        }

        TextView name = (TextView) convertView.findViewById(1);
        name.setText(cart.get(position).prod.brand);

        TextView desc = (TextView) convertView.findViewById(2);
        desc.setText(cart.get(position).quantity);

//        ImageView image= convertView.findViewById(1);
//        image.setBackgroundResource(contact.getImage());

        return convertView;
    }
}
