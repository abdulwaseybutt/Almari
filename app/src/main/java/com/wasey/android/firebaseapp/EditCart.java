package com.wasey.android.firebaseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.ArrayList;

public class EditCart extends AppCompatActivity {
    public User myuser;
    public Cart target;
    public int Quantity;
    TextView quan;
    int position;
    int hint;
    ArrayList<Mobile> targetList=new ArrayList<>();

    String userid;
    DatabaseReference databaseUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cart);


        target=(Cart) getIntent().getSerializableExtra("EditPhone");

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        userid=user.getUid();


        databaseUsers = FirebaseDatabase.getInstance().getReference("User");
        Query query = FirebaseDatabase.getInstance().getReference("User")
                .orderByChild("userId")
                .equalTo(userid);
        query.addListenerForSingleValueEvent(GetUser);


    }
    public void Increase(View view){

        Query query = FirebaseDatabase.getInstance().getReference("User")
                .orderByChild("userId")
                .equalTo(userid);
        query.addListenerForSingleValueEvent(increaseQuan);

    }


    public void Decrease(View view){

        Query query = FirebaseDatabase.getInstance().getReference("User")
                .orderByChild("userId")
                .equalTo(userid);
        query.addListenerForSingleValueEvent(decreaseQuan);
    }


    ValueEventListener GetUser = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    myuser = snapshot.getValue(User.class);
                    //us.add(user);
                }
                position=0;
                for (int i=0;i<myuser.mCart.size();i++)
                {
                    if (myuser.mCart.get(i).equals(target)){
                        position=i;
                        hint=i;
                        break;
                    }
                }

                Quantity=myuser.mCart.get(position).quantity;


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
                quan=(TextView)findViewById(R.id.show_quan) ;


                Brand.setText("Brand   :  "+target.prod.getBrand());
                Model.setText("Model   :  "+target.prod.getName());
                Color.setText("Color   :  "+target.prod.getColor());
                Disp_Size.setText("Display :  "+target.prod.getDisplay_Size());
                Battery.setText("Battery :  "+target.prod.getBattery());
                Ram.setText("Ram     :  "+target.prod.getRam());
                Price.setText("Price   :  "+target.prod.getPrice()+"$");
                OS.setText("OS      :  "+target.prod.getOS());
                Rate.setText("Rate    :  "+target.prod.getRate());
                Picasso.get()
                        .load(target.prod.getImg())
                        .fit()
                        .centerCrop()
                        .into(Imageviewitem);
                if (position!=-1){
                    quan.setText(Integer.toString(myuser.mCart.get(position).quantity));
                }

            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    ValueEventListener increaseQuan = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    myuser = snapshot.getValue(User.class);
                    //us.add(user);
                }



                        myuser.mCart.get(hint).quantity++;
                        databaseUsers.child(myuser.userId).setValue(myuser);//it stores a new productTest in the newly generated id
                        quan.setText(Integer.toString(myuser.mCart.get(hint).quantity));



            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    ValueEventListener decreaseQuan = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    myuser = snapshot.getValue(User.class);
                    //us.add(user);
                }


                        myuser.mCart.get(hint).quantity--;
                        databaseUsers.child(myuser.userId).setValue(myuser);//it stores a new productTest in the newly generated id
                        quan.setText(Integer.toString(myuser.mCart.get(hint).quantity));



            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
