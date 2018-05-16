package com.example.lenovo.cardlessatm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PinVerifyActivity extends AppCompatActivity {
    private EditText editTextPin;
    private Button buttonAdd;
    Userdetails profile1=new Userdetails();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_verify);
        editTextPin=(EditText)findViewById(R.id.editTextPIN);
        buttonAdd=(Button)findViewById(R.id.buttonAdd);
        final Intent intent=getIntent();
        final String a=intent.getStringExtra(AccountsActivity.acc);
        final String Account=String.valueOf(a);
        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //final String accnum = editTextAccNum.getText().toString().trim();
                final String pin = editTextPin.getText().toString().trim();
                final Long pin1= Long.valueOf(pin);
                //String no=intent.getStringExtra(PhoneAuthActivity.phone);
                //String Mobile=String.valueOf(no);

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference ref1 = database.getReferenceFromUrl("https://cardlessatm-1a9ff.firebaseio.com/accounts/").child(Account);

// Attach a listener to read the data at your profile reference
                ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        profile1 = dataSnapshot.getValue(Userdetails.class);
                        if (profile1 == null) {
                            Toast.makeText(getApplicationContext(), "enter pin number", Toast.LENGTH_SHORT).show();
                        } else {
                            // String realaccnum = profile1.getAccount();
                            Long realpin = profile1.getPin();
                            if (pin1.equals(realpin)) {
                                //System.out.println("success");
                                //profile1.setIsLinked("yes");
                                ref1.child("isLinked").setValue("yes");
                               // ref1.child("isPhoneLinked").setValue("yes");
                                Toast.makeText(getApplicationContext(), "account added", Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),LeftNavigationActivity.class);
                                startActivity(i);

                            } else {
                                System.out.println("failed");
                            }
                        }

                       /* System.out.println(profile.getName());
                        System.out.println(profile.getAccount());
                        System.out.println(profile.getIFSC());
                        System.out.println(profile.getPIN());*/
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });


            }});


    }
}
