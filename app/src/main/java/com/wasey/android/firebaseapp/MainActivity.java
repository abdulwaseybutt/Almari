package com.wasey.android.firebaseapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseUsers;
    DatabaseReference databaseProducts;

    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    String userid=user.getUid();
   private FirebaseAuth firebaseAuth;

    public ArrayList<Mobile> allmobiles=new ArrayList<>();
    private TabLayout tablayout;
    private AppBarLayout appbarlayout;
    private ViewPager viewpager;
    private int[] tabIcons = {
            R.drawable.home,
            R.drawable.cart,
            R.drawable.wishlist,
            R.drawable.support
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestSmsPermission();
        SimpleSmsReciever mReceiver = new SimpleSmsReciever();
        IntentFilter i = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mReceiver,  i);


        firebaseAuth=FirebaseAuth.getInstance();


        if(!isConnected(MainActivity.this))
            buildDialog(MainActivity.this).show();
        else {
            Toast.makeText(MainActivity.this,"Welcome", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_main);

            databaseUsers = FirebaseDatabase.getInstance().getReference("User");
            databaseProducts = FirebaseDatabase.getInstance().getReference("Mobile");

//            User wasey = new User("1", "wasey", "admin", "abdulwaseybutt", "123123", "abdulwasey@outlook.com", "0900-78601");
//            databaseUsers.child(wasey.userId).setValue(wasey);//it stores a new productTest in the newly generated id
//            Toast.makeText(MainActivity.this, wasey.username+" added to database", Toast.LENGTH_LONG).show();
//
//
//            User taha = new User("2", "taha", "admin", "tahakb", "123qwe", "taha@gmail.com", "0300-1231123");
//            databaseUsers.child(taha.userId).setValue(taha);//it stores a new productTest in the newly generated id
//            Toast.makeText(MainActivity.this, taha.username+" added to database", Toast.LENGTH_LONG).show();
//
//
//            Mobile mob1=new Mobile(1,"Iphone","Iphone X","Black","5inch",3500,4,120000,3.2,5,"https://switch.com.my/wp-content/uploads/2017/11/iphonex_spacegray.png",
//                    10);
//            databaseProducts.child(Integer.toString(mob1.pId)).setValue(mob1);//it stores a new productTest in the newly generated id
//            Toast.makeText(MainActivity.this, mob1.brand+" added to database", Toast.LENGTH_LONG).show();
//
//
//            Mobile mob2=new Mobile(2,"Samsung","Samsung S9","Black","6inch",4000,6,98000,3.1,4.5,"https://switch.com.my/wp-content/uploads/2017/11/iphonex_spacegray-600x600.png",
//                    10);
//
//            Mobile mob3=new Mobile(3,"Nokia","Nokia 3310","Black","4inch",3500,1,5000,1.5,4.8,"https://cdn2.gsmarena.com/vv/bigpic/nokia-3310-2017-1.jpg"
//            ,10);
//
//
//            User moeed = new User("4", "moeed", "account-holder", "mrk", "pewpew", "moeed@gmail.com", "0423-0220212");
//
//
//            TV superTv4=new TV(4,"Changongh-Ruba","Ruba star",6000,5,"brown",20,480,
//                    "https://brain-images-ssl.cdn.dixons.com/5/0/10158805/u_10158805.jpg");
//            User bazaid = new User("3", "bazaid", "account-holder", "bazaidkhan", "sexybitch", "bazaid@ymail.com", "0331-0220212");
//
//            bazaid.addToCart(mob2);
//            bazaid.addToWishList(mob1);
//
//            databaseUsers.child(bazaid.userId).setValue(bazaid);//it stores a new productTest in the newly generated id
//            Toast.makeText(MainActivity.this, bazaid.username+" added to database", Toast.LENGTH_LONG).show();
//
//            moeed.addToCart(mob3);
//            moeed.addToCart(mob2);
//            moeed.addToWishList(mob2);
//
//
//            databaseUsers.child(moeed.userId).setValue(moeed);//it stores a new productTest in the newly generated id
//            Toast.makeText(MainActivity.this, moeed.username+" added to database", Toast.LENGTH_LONG).show();
//
//            databaseProducts.child(Integer.toString(mob2.pId)).setValue(mob2);//it stores a new productTest in the newly generated id
//            Toast.makeText(MainActivity.this, mob2.brand+" added to database", Toast.LENGTH_LONG).show();
//
//
//            databaseProducts.child(Integer.toString(mob3.pId)).setValue(mob3);//it stores a new productTest in the newly generated id
//            Toast.makeText(MainActivity.this, mob3.brand+" added to database", Toast.LENGTH_LONG).show();


            tablayout=(TabLayout) findViewById(R.id.tab_id);
            appbarlayout=(AppBarLayout) findViewById(R.id.appbarid);
            viewpager=(ViewPager) findViewById(R.id.viewpager_id);

//            allmobiles.add(mob1);
//            allmobiles.add(mob2);
//            allmobiles.add(mob3);

       ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
//            Bundle bundle=new Bundle();
//            bundle.putSerializable("mobiles", allmobiles);
//            Fragment frag1= new Fragment_Home();
//            frag1.setArguments(bundle);

            adapter.AddFragment(new Fragment_Home(),"Home");
            adapter.AddFragment(new Fragment_Cart(),"Cart");
            adapter.AddFragment(new Fragment_Wishlist(),"Wishlist");
            adapter.AddFragment(new Fragment_Support(),"Support");

            viewpager.setAdapter(adapter);
            tablayout.setupWithViewPager(viewpager);
            setupTabIcons();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if (id==R.id.View_Profile){
            Toast.makeText(this,"Profile Selected",Toast.LENGTH_LONG).show();
        }
        else if(id==R.id.Sign_Out){
            firebaseAuth.signOut();
            Intent intent=new Intent(this,Login.class);
            startActivity(intent);
            Toast.makeText(this,"Signed Out",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupTabIcons() {
        tablayout.getTabAt(0).setIcon(tabIcons[0]);
        tablayout.getTabAt(1).setIcon(tabIcons[1]);
        tablayout.getTabAt(2).setIcon(tabIcons[2]);
        tablayout.getTabAt(3).setIcon(tabIcons[3]);
    }
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }

    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);

            switch (wifiStateExtra) {
                case WifiManager.WIFI_STATE_ENABLED:
                   Toast.makeText(getApplicationContext(),"Wifi Enabled",Toast.LENGTH_SHORT).show();
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    Toast.makeText(getApplicationContext(),"Wifi Disabled",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private void requestSmsPermission() {
        String permission = android.Manifest.permission.RECEIVE_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if ( grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
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
                finish();
            }
        });

        return builder;
    }

    public void testNet(View view) {
        if(!isConnected(getApplicationContext())){
            Toast.makeText(getApplicationContext(),"Sorry No internet connection found!",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_SHORT).show();
        }

    }


}