package com.wasey.android.firebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import java.util.ArrayList;

public class Fragment_Home extends Fragment {
    ArrayList<Mobile> allmobiles;
    View view;
    GridLayout mainGrid;
    public Fragment_Home() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.home_fragment,container,false);
        mainGrid=(GridLayout) view.findViewById(R.id.mainGrid);


        setSingleEvent(mainGrid);


        return view;
    }

    private void setSingleEvent(GridLayout mainGrid) {
        for (int i=0;i<mainGrid.getChildCount();i++){

            CardView cardview= (CardView)mainGrid.getChildAt(i);
            final int FinalI=i;
            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String page = " ";

                    if (FinalI==0){
                        page="Shirts";
                    }
                    else if(FinalI==1){
                        page="Jeans";
                    }
                    else if(FinalI==2){
                        page= "Shoes";
                    }
                    else if(FinalI==3){
                        page="Accessories";
                    }
                    else if(FinalI==4){
                        page="Mobile";
                        Intent intent = new Intent(getActivity(),All_Items.class);


                        startActivity(intent);

                    }
                    else if(FinalI==5){
                        page="Computer";
                    }

                }
            });
        }
    }

}
