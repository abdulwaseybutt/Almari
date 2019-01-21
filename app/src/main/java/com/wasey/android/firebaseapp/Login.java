package com.wasey.android.firebaseapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
 Button buttonregister;
 EditText getemail,getpass;
    private EditText emailadd;
    private EditText password;
    Button buttonlogin;
    AdView madview;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();
        getemail=findViewById(R.id.emailtext);

        getpass=findViewById(R.id.passtext);
        buttonregister=(Button)findViewById(R.id.registerbutton);

        emailadd=(EditText)findViewById(R.id.emailtext2);
        password=(EditText)findViewById(R.id.passtext2);
        buttonlogin=(Button)findViewById(R.id.loggin);



        MobileAds.initialize(this,"ca-app-pub-1077284254654502/2708358004");
        madview=(AdView)findViewById(R.id.adView);
        AdRequest adRequest=new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        madview.loadAd(adRequest);

        progressDialog=new ProgressDialog(this);

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailadd.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if(pass.matches("")||email.matches(""))
                {
                    Toast.makeText(Login.this, "Can't login :)", Toast.LENGTH_SHORT).show();
                }
                else {
                    validate(email, pass);
                }
            }
        });


        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = getemail.getText().toString().trim();
                String pass = getpass.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Login.this, "Please enter email :(", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(Login.this, "Please enter passsword :(", Toast.LENGTH_LONG).show();
                    return;
                }
                progressDialog.setMessage("Registering you :)");
               progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                  progressDialog.dismiss();
                                    sendemailverification();
                                    User temp= new User(task.getResult().getUser().getUid(),"name","account-holder","username","12341234",task.getResult().getUser().getEmail(),
                                    "0900-788601");
                                    DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("User");
                                    databaseUsers.child(temp.userId).setValue(temp);//it stores a new productTest in the newly generated id

                                    firebaseAuth.signOut();
                                    //Toast.makeText(MainActivity.this, "Registered Successfully :)", Toast.LENGTH_SHORT).show();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(Login.this, "Unsucck :)", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

    }

    private void sendemailverification()
    {
        final FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(Login.this, "Registered Successfully :)", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(Login.this, "Verification mail not sent :)", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                    }
                }
            });
        }
    }
    private void validate(String username, String password)
    {
        progressDialog.setMessage("Please wait :)");
        progressDialog.show();
        if(username!=null&&password!=null)

            firebaseAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        //Toast.makeText(MainActivity.this, "Logged in :)", Toast.LENGTH_SHORT).show();
                        checkemailverification();
                        //take to new activity
                    }
                    else
                    {
                        progressDialog.dismiss();
                        //checkemailverification();
                        Toast.makeText(Login.this, "Login failed :(", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private void checkemailverification()
    {
        FirebaseUser firebaseUser=firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag=firebaseUser.isEmailVerified();

        if(emailflag)
        {
            //have to go to new activity if email is verified :)
            Toast.makeText(Login.this, "Email is verified btw. Logged in xdd :o", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(Login.this, "Email is not verified :o", Toast.LENGTH_LONG).show();
        }
    }

    public void logout()
    {
        firebaseAuth.signOut();
        Toast.makeText(Login.this, "Signed out :o", Toast.LENGTH_LONG).show();
    }
}
