package com.wasey.android.firebaseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class All_Items extends AppCompatActivity {
    private ArrayList<Mobile> items;
    EditText searchtxt;

    GridView itemsListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__items);
//
//        Intent intent=new Intent(this,RetrieveDataService.class);
//        startService(intent);

        items=new ArrayList<>();
        searchtxt=(EditText)findViewById(R.id.search_text);
        Button searchbtn=(Button)findViewById(R.id.Search_btn);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = FirebaseDatabase.getInstance().getReference("Mobile")
                        .orderByChild("brand")
                        .equalTo(searchtxt.getText().toString());
                query.addListenerForSingleValueEvent(valueEventListener);
            }
        });


        //final CustomListAdapter adapter = new CustomListAdapter(this, items);

// get the ListView and attach the adapter



        itemsListView  = (GridView) findViewById(R.id.mobilegrid);
        Query query = FirebaseDatabase.getInstance().getReference("Mobile")
                .orderByChild("pId");
                //.equalTo("2");
        query.addListenerForSingleValueEvent(valueEventListener);



       // itemsListView.setAdapter(adapter);



        itemsListView.setOnItemClickListener (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                Intent intent = new Intent(All_Items.this, Mobile_Description.class);
                //Get the value of the item you clicked
                Mobile itemClicked = items.get(position);
                intent.putExtra("Phone", itemClicked);
                startActivity(intent);

            }
        });

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            items.clear();

            //artistList.clear(); product list
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Mobile prods = snapshot.getValue(Mobile.class);
                    items.add(prods);
                }
                CustomListAdapter adapter=new CustomListAdapter(All_Items.this,items);
                itemsListView.setAdapter(adapter);
                //adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}
