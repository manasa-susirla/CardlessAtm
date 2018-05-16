package com.example.lenovo.cardlessatm;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignOutActivity extends AppCompatActivity {

    public String temporary;
    public String userid;
    public static int cx,cy ;
    private FirebaseDatabase mFirebaseDatabase;
    private static DatabaseReference mUserDatabase;
    private FirebaseAuth mAuth;
    private static DatabaseReference myRef;
    private int MODE_PRIVATE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        startauthentication();


        setContentView(R.layout.activity_sign_out);


        SharedPreferences mPreferences;
        mPreferences = SignOutActivity.this.getSharedPreferences("User", MODE_PRIVATE);

        temporary = mPreferences.getString("saveuserid", "");

        if(temporary!= null && !temporary.isEmpty()) {

            mAuth = FirebaseAuth.getInstance();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            myRef = mFirebaseDatabase.getReference();
             FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
             userid = currentFirebaseUser.getUid();
            //finish();
            //startActivity(new Intent(getApplicationContext(),EnterActivity.class));


        } else
        {


        }


    }

    public void startauthentication(){

        SharedPreferences mPreferences;
        mPreferences = getSharedPreferences("User", MODE_PRIVATE);

        temporary = mPreferences.getString("saveuserid", "");

        if(temporary!= null && !temporary.isEmpty()){


        }

        else{


            Intent y = new Intent(SignOutActivity.this,LoginActivity.class);
            y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(y);

        }
    }


    public void signoutbutton(View s) {
        if (s.getId() == R.id.sign_out) {



            AlertDialog.Builder builder = new AlertDialog.Builder(SignOutActivity.this);
            builder.setMessage("Do you really want to Log Out ?").setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            SharedPreferences mPreferences;

                            mPreferences = getSharedPreferences("User", MODE_PRIVATE);
                            SharedPreferences.Editor editor = mPreferences.edit();
                            editor.clear();
                            editor.apply();
                            mAuth.signOut();

                            Intent y = new Intent(SignOutActivity.this, LoginActivity.class);
                            y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(y);



                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.setTitle("Confirm");
            dialog.show();


        }
    }
}