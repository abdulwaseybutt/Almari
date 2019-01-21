package com.wasey.android.firebaseapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class Fragment_Support extends Fragment {
    View view;
    Button submit;
    EditText getemail,getcomplain;
    public Fragment_Support() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.support_fragment,container,false);

        submit=view.findViewById(R.id.submit_button);
        getemail=view.findViewById(R.id.email_edittext);
        getcomplain=view.findViewById(R.id.query_edittext);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> toEmailList = Arrays.asList(getemail.getText().toString().split("\\s*,\\s*"));
                new SendMailTask(getActivity()).execute("adevelopers07@gmail.com",
                        "bazaid07", toEmailList, "Feedback Details", "We have recieved your query :)\n"+getcomplain.getText().toString());
                Toast.makeText(getActivity(), "Feedback Recieved :)", Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }
}
