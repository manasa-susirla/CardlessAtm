package com.example.lenovo.cardlessatm;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Selectmethod extends AppCompatActivity {
    Button nfc,qrcode,accesscode;
    String method_used;
    float amt;String acc_type;
    int pin,acc;
    SimpleDateFormat formatter;
    Date date;
    private IntentIntegrator qrScan;
    String ATMTransactionIdList;
    String TransactionIdList;
    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectmethod);
        nfc=(Button)findViewById(R.id.buttonnfc);
        accesscode=(Button)findViewById(R.id.buttonAccessCode);
        qrcode=(Button)findViewById(R.id.buttonqr);
        qrScan = new IntentIntegrator(this);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        nfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                method_used="NFC";
                Intent intent = new Intent(Selectmethod.this,NfcActivity.class);
                amt =Float.parseFloat(getIntent().getExtras().getString("amount"));
                acc_type=getIntent().getExtras().getString("acc_type");
                pin=Integer.parseInt(getIntent().getExtras().getString("pin"));
                acc=Integer.parseInt(getIntent().getExtras().getString("acc_no"));

                intent.putExtra("amount",String.valueOf(amt));
                intent.putExtra("pin",String.valueOf(pin));
                intent.putExtra("acc_type",acc_type);
                intent.putExtra("acc",String.valueOf(acc));
                intent.putExtra("method_used",method_used);
                startActivity(intent);


            }
        });

        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                method_used="qrcode";
                amt =Float.parseFloat(getIntent().getExtras().getString("amount"));
                acc_type=getIntent().getExtras().getString("acc_type");
                pin=Integer.parseInt(getIntent().getExtras().getString("pin"));
                acc=Integer.parseInt(getIntent().getExtras().getString("acc_no"));
                method_used="qr";
                //intent.putExtra("amount",amt);
                //intent.putExtra("pin",pin);
                //intent.putExtra("acc_type",acc_type);
                //intent.putExtra("acc",acc);
                System.out.println("pin="+pin);
                System.out.println("amt="+amt);
                System.out.println("acc="+acc);
                System.out.println("acc_type="+acc_type);
                formatter = new SimpleDateFormat(" dd:MM:yyyy HH:mm:ss");
                date = new Date();

                //creating transaction details node
                TransactionDetails td= new TransactionDetails();
                td.setValues(amt,acc,acc_type,"","",formatter.format(date),false,method_used);
                DatabaseReference pathReference = myRef.child("TransactionDetails");
                final Task<Void> voidTask = pathReference.child(acc_type+String.valueOf(acc)+(String) formatter.format(date)).setValue(td).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
                //updating current TransactionId in the account Node
                myRef.child("accounts").child(String.valueOf(acc)).child("currentTransactionID").setValue(acc_type+String.valueOf(acc)+(String)formatter.format(date));;

                //updating current TransactionIdList in the account Node

                myRef.child("accounts").child(String.valueOf(acc)).child("transactionIDList").addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        TransactionIdList= (String) snapshot.getValue();
                        System.out.println("transactionidlist="+TransactionIdList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //System.out.println(TransactionIdList+"*"+acc_type+String.valueOf(acc)+(String)formatter.format(date));
                myRef.child("accounts").child(String.valueOf(acc)).child("transactionIDList").setValue(TransactionIdList+"*"+acc_type+String.valueOf(acc)+(String)formatter.format(date));


                qrScan.initiateScan();
            }
        });

        accesscode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                method_used="accesscode";
                Intent intent = new Intent(Selectmethod.this, AccessCode.class);
                amt =Float.parseFloat(getIntent().getExtras().getString("amount"));

                pin=Integer.parseInt(getIntent().getExtras().getString("pin"));
                acc=Integer.parseInt(getIntent().getExtras().getString("acc_no"));

                intent.putExtra("amount",String.valueOf(amt));
                intent.putExtra("pin",String.valueOf(pin));
                intent.putExtra("acc_type",acc_type);
                intent.putExtra("acc",String.valueOf(acc));
                intent.putExtra("method_used",method_used);
                startActivity(intent);
            }
        });
        nfc.setEnabled(false);nfc.setBackgroundColor(Color.parseColor("#D5DBDB"));
        accesscode.setEnabled(false);accesscode.setBackgroundColor(Color.parseColor("#D5DBDB"));
        qrcode.setEnabled(false);qrcode.setBackgroundColor(Color.parseColor("#D5DBDB"));

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    System.out.println(obj.getString("ATMid"));

                    //following
                    //setting current transactionID in the ATM node
                    myRef.child("ATM").child(obj.getString("ATMid")).child("current_transaction").setValue(acc_type+String.valueOf(acc)+(String)formatter.format(date));

                    //setting transactionIDList in the atm node
                    myRef.child("ATM").child(obj.getString("ATMid")).child("transaction_list").addListenerForSingleValueEvent( new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            ATMTransactionIdList= (String) snapshot.getValue();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    //setting the "transaction_initiated" attribute of the atm node to true
                    myRef.child("ATM").child(obj.getString("ATMid")).child("transaction_initiated").setValue(true);
                    System.out.println("created node############");

                    //setting the ATM id in the transaction Details node
                    myRef.child("TransactionDetails").child(acc_type+String.valueOf(acc)+(String)formatter.format(date)).child("atm_no").setValue(obj.getString("ATMid"));

                    myRef.child("accounts").child(String.valueOf(acc)).child("transactionIDList").setValue(ATMTransactionIdList+"*"+acc_type+String.valueOf(acc)+(String)formatter.format(date));


                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

        ///display transcation details after this
    }

    public void onRadioButtonClicked(View view) {
        nfc.setEnabled(true); nfc.setBackgroundColor(Color.parseColor("#2196F3"));
        accesscode.setEnabled(true);accesscode.setBackgroundColor(Color.parseColor("#2196F3"));
        qrcode.setEnabled(true);qrcode.setBackgroundColor(Color.parseColor("#2196F3"));

        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radiosavings:
                if (checked){
                    acc_type="savings";
                    break;}
            case R.id.radiocurrent:
                if (checked) {
                    acc_type = "current";
                    break;
                }
        }
    }



}

