package com.wasey.android.firebaseapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

class customadapter extends BaseAdapter {
    private User user;
    private Context context;
    public customadapter(Context context, User obj){
        this.user=obj;
        this.context=context;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return user.mCart.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub

        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        convertView = LayoutInflater.from(context).
                inflate(R.layout.custom, parent, false);

        TextView mobilename = convertView.findViewById(R.id.textView1);
        TextView mobilequan = convertView.findViewById(R.id.textView2);
        ImageView image = convertView.findViewById(R.id.imageView1);

        mobilename.setText(user.mCart.get(position).prod.getName());
        mobilequan.setText(Integer.toString(user.mCart.get(position).quantity));

        Picasso.get()
                .load(user.mCart.get(position).prod.getImg())
                .fit()
                .centerCrop()
                .into(image);

        return convertView;
    }

}
