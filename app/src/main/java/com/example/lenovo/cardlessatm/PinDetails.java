package com.example.lenovo.cardlessatm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

class AccountDetails
{
    int acc_no;
    float balance;
    int mobile;
    int pin;
    String currentTransactionID;
    String transactionIDList;
    int accesscode;
}
public class PinDetails extends AppCompatActivity implements View.OnClickListener{

    Button proceed;
    EditText editTextPIN;
    FirebaseDatabase database;
    DatabaseReference myRef;

    protected void onStart(View v)
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_details);
        proceed=(Button)findViewById(R.id.buttonProceed2);
        proceed.setOnClickListener(this);
        editTextPIN=(EditText)findViewById(R.id.editTextPIN);
        //Bundle bundle = getIntent().getExtras();
        //Extract the data…
        String stuff = getIntent().getExtras().getString("amount");
        Log.i("amount",stuff);

    }
    public void onClick(View v)
    {
        if(v.getId()==R.id.buttonProceed2)
        {
            final Intent intent = new Intent(this, methodWithdrawal.class);
            String amt = getIntent().getExtras().getString("amount");
            String acc_type=getIntent().getExtras().getString("acc_type");
            final String pin=editTextPIN.getText().toString();
            final int acc=444;

            intent.putExtra("amount",amt);
            intent.putExtra("pin",pin);
            intent.putExtra("acc_type",acc_type);
            intent.putExtra("acc",String.valueOf(acc));

            database = FirebaseDatabase.getInstance();
            database.getReference().child("accounts")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren())
                            {

                                AccountDetails user = snapshot.getValue(AccountDetails.class);
                                System.out.println("user acc-"+user.acc_no);
                                if(user.acc_no==acc && user.pin==Integer.parseInt(pin))
                                {
                                    Toast.makeText(getApplicationContext(),"PIN matched", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    setContentView(R.layout.activity_method_withdrawal);
                                }

                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });






        }
    }
}
