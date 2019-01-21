package com.wasey.android.firebaseapp;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RetrieveDataService extends Service {
    GridView itemsListView;

    private ArrayList<Mobile> items;
    public RetrieveDataService() {
    }
    public void onCreate() {
        //Toast.makeText(this,"Service starting",Toast.LENGTH_LONG).show();

    }
    public int onStartCommand(Intent intent,int flags,int startId){

        Toast.makeText(this,"Service starting",Toast.LENGTH_LONG).show();

//        List<String> toEmailList = Arrays.asList(intent.getExtras().get("email").toString()
//                .split("\\s*,\\s*"));

//        new SendMailTask().execute("adevelopers07@gmail.com",
//                "bazaid07", toEmailList, "Reciept", "Your order has been placed Successfully :)");
        // itemsListView.setAdapter(adapter);


        return START_NOT_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;



    }

}
