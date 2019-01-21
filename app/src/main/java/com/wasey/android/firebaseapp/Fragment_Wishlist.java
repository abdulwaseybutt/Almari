package com.wasey.android.firebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Fragment_Wishlist extends Fragment {
    View view;
    ListView WishlistListView;
    User myuser;
    DatabaseReference databaseUsers;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userid = user.getUid();


    public Fragment_Wishlist() {
       // Mobile mob1 = new Mobile(1, "Iphone", "Iphone X", "Black", "5inch", 3500, 4, 120000, 3.2, 5, "https://switch.com.my/wp-content/uploads/2017/11/iphonex_spacegray.png");
       // Mobile mob2 = new Mobile(2, "Samsung", "Samsung S9", "Black", "6inch", 4000, 6, 98000, 3.1, 4.5, "https://switch.com.my/wp-content/uploads/2017/11/iphonex_spacegray-600x600.png");
       // Mobile mob3 = new Mobile(3, "Nokia", "Nokia 3310", "Black", "4inch", 3500, 1, 5000, 1.5, 4.8, "https://cdn2.gsmarena.com/vv/bigpic/nokia-3310-2017-1.jpg");
//        myuser = new User("1", "Bazaid", "User", "Baxaid", "1234", "bazaidkhan@gmail.com", "03311043276");
//        myuser.addToWishList(mob1);
//        myuser.addToWishList(mob2);
//        myuser.addToWishList(mob3);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.wishlist_fragment, container, false);

        //final customwishlistadapter adapter = new customwishlistadapter(getActivity(), myuser);

        //WishlistListView.setAdapter(adapter);
        databaseUsers = FirebaseDatabase.getInstance().getReference("User");
        Query query = FirebaseDatabase.getInstance().getReference("User")
                .orderByChild("userId")
                .equalTo(userid);
        query.addListenerForSingleValueEvent(getAndDisplayWishList);

        return view;
    }
    ValueEventListener getAndDisplayWishList = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            //us.clear();
            //artistList.clear(); product list
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    myuser = snapshot.getValue(User.class);
                    //us.add(user);
                }
                WishlistListView=(ListView) view.findViewById(R.id.wishlist_list);
                customadapterWishList ca = new customadapterWishList();
                WishlistListView.setAdapter(ca);
                WishlistListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Mobile item = (Mobile) WishlistListView.getItemAtPosition(position);

                        Intent intent = new Intent(getActivity(),Mobile_Description.class);
                        intent.putExtra("Phone",item);
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

    class customadapterWishList extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return myuser.wishList.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub

            return myuser.wishList.get(arg0);
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
            //TextView tv1 = convertview.findViewById(R.id.textView2);
            ImageView image = convertview.findViewById(R.id.imageView1);

            ImageView btn=(ImageView) convertview.findViewById(R.id.remove_btn);
            btn.setTag(position);
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Integer index = (Integer) v.getTag();
                    //items.remove(index.intValue());
                    myuser.wishList.remove(position);
                    databaseUsers.child(myuser.userId).setValue(myuser);//it stores a new productTest in the newly generated id
                    customadapterWishList.this.notifyDataSetChanged();

                }
            });

            tv.setText(myuser.wishList.get(position).getName());
            //tv1.setText(Integer.toString(us.get(0).mCart.get(position).quantity));

            Picasso.get()
                    .load(myuser.wishList.get(position).img)
                    .fit()
                    .centerCrop()
                    .into(image);


            return convertview;
        }

    }
}