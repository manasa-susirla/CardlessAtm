package com.example.lenovo.cardlessatm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PhoneMainActivity extends AppCompatActivity {
    String verified="No";
    Userdetails profile1=new Userdetails();
    private EditText editTextAccNum;
    private EditText editTextPinNo;
    private Button buttonNext;
    public String temporary;
    public String userid;
    public static int cx,cy ;
    private FirebaseDatabase mFirebaseDatabase;
    private static DatabaseReference mUserDatabase;
    private FirebaseAuth mAuth;
    private static DatabaseReference myRef;
    private int MODE_PRIVATE;

    String phone;
    DatabaseReference ref2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent=getIntent();
     /*   String no=intent.getStringExtra(PhoneAuthActivity.phone);
        String Mobile=String.valueOf(no);
        String a=intent.getStringExtra(String.valueOf(PhoneAuthActivity.class));
        final String Account=String.valueOf(a);
*/

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
       /* final DatabaseReference ref = database.getReferenceFromUrl("https://cardlessatm-1a9ff.firebaseio.com/accounts").child(Account); */

        startauthentication();


        setContentView(R.layout.activity_phone_main);
        editTextPinNo=(EditText)findViewById(R.id.editTextPinNo);
        buttonNext=(Button)findViewById(R.id.buttonNext);

        SharedPreferences mPreferences;
        mPreferences = PhoneMainActivity.this.getSharedPreferences("UserAccounts", MODE_PRIVATE);

        temporary = mPreferences.getString("userid", "");

        if(temporary!= null && !temporary.isEmpty()) {
            System.out.println("heyy");
            mAuth = FirebaseAuth.getInstance();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            myRef = mFirebaseDatabase.getReference();
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            userid = currentFirebaseUser.getUid();
            buttonNext.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    final String pin = editTextPinNo.getText().toString().trim();
                    final Long pin1=Long.valueOf(pin);
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference ref1 = database.getReferenceFromUrl("https://cardlessatm-1a9ff.firebaseio.com/accounts/").child("555");

// Attach a listener to read the data at your profile reference
                    ref1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            profile1 = dataSnapshot.getValue(Userdetails.class);
                            if (profile1 == null) {
                                Toast.makeText(getApplicationContext(), "enter pin number", Toast.LENGTH_SHORT).show();
                            } else {

                                Long realpin = profile1.getPin();
                                if (pin1.equals(realpin)) {

                                    ref1.child("isLinked").setValue("yes");
                                    Intent i=new Intent(getApplicationContext(),LeftNavigationActivity.class);
                                    startActivity(i);
                                    Toast.makeText(getApplicationContext(), "account added", Toast.LENGTH_SHORT).show();
                                } else {
                                    System.out.println("failed");
                                }
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });


                }});

        }



        else
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


            Intent y = new Intent(getApplicationContext(), PhoneAuthActivity.class);
            y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(y);

        }
    }

}
