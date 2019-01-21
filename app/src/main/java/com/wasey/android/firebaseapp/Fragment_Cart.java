package com.wasey.android.firebaseapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class Fragment_Cart extends Fragment {
    View view;
    User myuser,user1,user2;
    ListView CartListView;
    DatabaseReference databaseUsers,databaseProducts;
    Mobile mobile;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userid = user.getUid();

    Button checkout;
    ValueEventListener getAndDisplayCart = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            //us.clear();
            //artistList.clear(); product list
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    myuser = snapshot.getValue(User.class);
                    //us.add(user);
                }

                CartListView = (ListView) view.findViewById(R.id.cart_list);
                customadapterCart ca = new customadapterCart();
                CartListView.setAdapter(ca);
                CartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Cart item = (Cart) CartListView.getItemAtPosition(position);

                        Intent intent = new Intent(getActivity(), EditCart.class);
                        intent.putExtra("EditPhone", item);
                        //based on item add info to intent
                        startActivity(intent);
                    }


                });
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public Fragment_Cart() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cart_fragment, container, false);

        databaseUsers = FirebaseDatabase.getInstance().getReference("User");
        databaseProducts = FirebaseDatabase.getInstance().getReference("Mobile");
        Query query = FirebaseDatabase.getInstance().getReference("User")
                .orderByChild("userId")
                .equalTo(userid);
        query.addListenerForSingleValueEvent(getAndDisplayCart);

        checkout = view.findViewById(R.id.checkout_id);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (testNet()){
                    for (int i = 0; i < myuser.mCart.size(); i++) {
                        checkout();
                    }
                    String em=myuser.emailId;
                    Intent intent=new Intent(getActivity(),RetrieveDataService.class);
                    intent.putExtra("email",em);
                    getActivity().startService(intent);

                    List<String> toEmailList = Arrays.asList(myuser.emailId
                            .split("\\s*,\\s*"));
                    new SendMailTask(getActivity()).execute("adevelopers07@gmail.com",
                            "bazaid07", toEmailList, "Reciept", "Your order has been placed Successfully :)");
                }

            }
        });

//        CartListView  = (ListView) view.findViewById(R.id.cart_list);
//
//
//        final customadapter adapter = new customadapter(getActivity(), myuser);
//
//        CartListView.setAdapter(adapter);

        return view;
    }

    public void checkout(){
                if (myuser != null)//checking if the data is loaded or not
                {
                    Query query = FirebaseDatabase.getInstance().getReference("Mobile")
                            .orderByChild("pId")
                            .equalTo(myuser.mCart.get(0).prod.pId);
                    query.addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //artistList.clear(); product list
                                    if (dataSnapshot.exists()) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            mobile = snapshot.getValue(Mobile.class);
                                        }
                                        if (mobile != null)//checking if the data is loaded or not
                                        {
                                            if (mobile.stock - myuser.mCart.get(0).quantity >= 0) {
                                                mobile.stock = mobile.stock - myuser.mCart.get(0).quantity;
                                                databaseProducts.child(Integer.toString(mobile.pId)).setValue(mobile);

                                                myuser.mCart.remove(myuser.mCart.get(0));
                                                databaseUsers.child(myuser.userId).setValue(myuser);

                                                CartListView=null;
                                                CartListView = (ListView) view.findViewById(R.id.cart_list);
                                                customadapterCart ca = new customadapterCart();
                                                CartListView.setAdapter(ca);
                                                CartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                        Cart item = (Cart) CartListView.getItemAtPosition(position);

                                                        Intent intent = new Intent(getActivity(), EditCart.class);
                                                        intent.putExtra("EditPhone", item);
                                                        //based on item add info to intent
                                                        startActivity(intent);
                                                    }

                                            });
                                            }
                                            else {
                                                Toast.makeText(getActivity(), "not enough stock available", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            }
                    );




            }
    }

    class customadapterCart extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return myuser.mCart.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub

            return myuser.mCart.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int position, View convertview, ViewGroup arg2) {
            // TODO Auto-generated method stub
            LayoutInflater inflater = getLayoutInflater();
            convertview = inflater.inflate(R.layout.custom, null);

            TextView tv = convertview.findViewById(R.id.textView1);
            TextView tv1 = convertview.findViewById(R.id.textView2);
            ImageView image = convertview.findViewById(R.id.imageView1);
            ImageView btn = (ImageView) convertview.findViewById(R.id.remove_btn);
            btn.setTag(position);
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Integer index = (Integer) v.getTag();
                    //items.remove(index.intValue());
                    myuser.mCart.remove(position);
                    databaseUsers.child(myuser.userId).setValue(myuser);//it stores a new productTest in the newly generated id
                    customadapterCart.this.notifyDataSetChanged();


                }
            });

            tv.setText(myuser.mCart.get(position).prod.getName());
            tv1.setText(Integer.toString(myuser.mCart.get(position).quantity));

            Picasso.get()
                    .load(myuser.mCart.get(position).prod.img)
                    .fit()
                    .centerCrop()
                    .into(image);


            return convertview;
        }

    }


    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else
                return false;
        }
        else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to be connected to the Internet to continue. \n Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });

        return builder;
    }

    public boolean testNet() {
        if(!isConnected(getActivity())){
            Toast.makeText(getActivity(),"Sorry No internet connection found!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            Toast.makeText(getActivity(),"Connected",Toast.LENGTH_SHORT).show();
            return true;
        }

    }
}
