package com.wasey.android.firebaseapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserList extends ArrayAdapter<Cart> {
    private Activity context;
    private List<Cart> cartList;

    public UserList(Activity context, List<Cart> userList) {
        super(context, R.layout.cart_adapter, userList);
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View listviewitem = layoutInflater.inflate(R.layout.cart_adapter, null, true);
        TextView prodname = (TextView) listviewitem.findViewById(R.id.name);
        TextView prodtype = (TextView) listviewitem.findViewById(R.id.qt);

        Cart cart = cartList.get(position);
        prodname.setText(cart.prod.brand);
        prodtype.setText(cart.quantity);


        return listviewitem;
    }
}
