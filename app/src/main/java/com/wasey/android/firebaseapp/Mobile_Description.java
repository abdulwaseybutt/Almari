package com.wasey.android.firebaseapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

import java.util.ArrayList;

public class Mobile_Description extends AppCompatActivity {
    public User myuser;
    public Mobile target;

    ArrayList<Mobile>targetList=new ArrayList<>();

    String userid;
    DatabaseReference databaseUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile__description);


        target=(Mobile) getIntent().getSerializableExtra("Phone");


        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        userid=user.getUid();


        databaseUsers = FirebaseDatabase.getInstance().getReference("User");


        TextView Brand=(TextView)findViewById(R.id.brandid);
        TextView Model=(TextView)findViewById(R.id.modelid);
        TextView Color=(TextView)findViewById(R.id.colorid);
        TextView Disp_Size=(TextView)findViewById(R.id.displayid);
        TextView Battery=(TextView)findViewById(R.id.batteryid);
        TextView Ram=(TextView)findViewById(R.id.ramid);
        TextView Price=(TextView)findViewById(R.id.priceid);
        TextView OS=(TextView)findViewById(R.id.osid);
        TextView Rate=(TextView)findViewById(R.id.rateid);
        ImageView Imageviewitem=(ImageView) findViewById(R.id.desc_imageid);



        Brand.setText("Brand   :  "+target.getBrand());
        Model.setText("Model   :  "+target.getName());
        Color.setText("Color   :  "+target.getColor());
        Disp_Size.setText("Display :  "+target.getDisplay_Size());
        Battery.setText("Battery :  "+target.getBattery());
        Ram.setText("Ram     :  "+target.getRam());
        Price.setText("Price   :  "+target.getPrice()+"$");
        OS.setText("OS      :  "+target.getOS());
        Rate.setText("Rate    :  "+target.getRate());
        Picasso.get()
                .load(target.getImg())
                .fit()
                .centerCrop()
                .into(Imageviewitem);

    }
    public void Add_To_Wishlist(View view){
        Query query = FirebaseDatabase.getInstance().getReference("User")
                .orderByChild("userId")
                .equalTo(userid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        myuser = snapshot.getValue(User.class);
                        //us.add(user);
                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final Handler handler = new Handler();
        final int delay=1000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (myuser!=null){
                    myuser.addToWishList(target);
                    databaseUsers.child(myuser.userId).setValue(myuser);//it stores a new productTest in the newly generated id
                    Toast.makeText(getBaseContext(), target.getName()+" added to WishList :)", Toast.LENGTH_LONG).show();
                }
                else
                    handler.postDelayed(this,delay);

            }
        }, delay);

    }


    public void Add_To_Cart(View view) {
        Query query = FirebaseDatabase.getInstance().getReference("User")
                .orderByChild("userId")
                .equalTo(userid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        myuser = snapshot.getValue(User.class);
                        //us.add(user);
                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final Handler handler = new Handler();
        final int delay=1000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (myuser!=null){
                    boolean ans=true;
                    for (int i = 0; i < myuser.mCart.size(); i++) {
                        if (myuser.mCart.get(i).equals(target)) {
                            Toast.makeText(Mobile_Description.this, "Already in your cart", Toast.LENGTH_LONG).show();
                            ans=false;
                        }
                    }
                    if (ans==true){
                        myuser.addToCart(target);
                        databaseUsers.child(myuser.userId).setValue(myuser);//it stores a new productTest in the newly generated id
                        Toast.makeText(getApplicationContext(), target.getName()+" added to Cart :)", Toast.LENGTH_LONG).show();
                    }


                }
                else
                    handler.postDelayed(this,delay);

            }
        }, delay);

    }
}
