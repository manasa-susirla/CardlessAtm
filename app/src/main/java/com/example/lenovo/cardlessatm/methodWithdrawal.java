package com.example.lenovo.cardlessatm;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
class TransactionDetails
{
    float amount;
    int acc_no;
    String acc_type;
    String atm_no;
    String code;
    String date_of_Transaction;
    boolean isComplete;
    String method_used;
    void setValues(float amount,int acc_no,String acc_type,String atm_no,String code,String date_of_Transaction,boolean isComplete,String method_used)
    {this.acc_no=acc_no;
        this.acc_type=acc_type;
        this.amount=amount;
        this.atm_no=atm_no;
        this.code=code;
        this.date_of_Transaction=date_of_Transaction;
        this.isComplete=isComplete;
        this.method_used=method_used;}
}

public class methodWithdrawal extends AppCompatActivity implements View.OnClickListener{

    Button qr,sms,nfc;
    private TextView textViewName;
    private IntentIntegrator qrScan;
    SimpleDateFormat formatter;
    Date date;int acc;
    float amt;int pin;
    String acc_type;
    String method_used;
    String ATMTransactionIdList;
    String TransactionIdList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_method_withdrawal);
        qr=(Button)findViewById(R.id.buttonQR);
        sms=(Button)findViewById(R.id.buttonSMS);
        nfc=(Button)findViewById(R.id.buttonNFC);
        textViewName = (TextView) findViewById(R.id.textViewName);
        qrScan = new IntentIntegrator(this);
        qr.setOnClickListener(this);
        sms.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                amt =Float.parseFloat(getIntent().getExtras().getString("amount"));
                acc_type=getIntent().getExtras().getString("acc_type");
                pin=Integer.parseInt(getIntent().getExtras().getString("pin"));
                acc=Integer.parseInt(getIntent().getExtras().getString("acc"));
                method_used="access";

                Intent activityChangeIntent = new Intent(methodWithdrawal.this, AccessCode.class);
                startActivity(activityChangeIntent);
            }
            }

        );
        nfc.setOnClickListener(this);

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
                    textViewName.setText(obj.getString("ATMid"));


                    //following
                    //setting current transactionID in the ATM node
                    FirebaseDatabase.getInstance().getReference().child("ATM").child(obj.getString("ATMid")).child("current_transaction").setValue(acc_type+String.valueOf(acc)+(String)formatter.format(date));

                    //setting transactionIDList in the atm node
                    FirebaseDatabase.getInstance().getReference().child("ATM").child(obj.getString("ATMid")).child("transaction_list").addListenerForSingleValueEvent( new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            ATMTransactionIdList= (String) snapshot.getValue();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    //setting the "transaction_initiated" attribute of the atm node to true
                    FirebaseDatabase.getInstance().getReference().child("ATM").child(obj.getString("ATMid")).child("transaction_initiated").setValue(true);
                    System.out.println("created node############");

                    //setting the ATM id in the transaction Details node
                    FirebaseDatabase.getInstance().getReference().child("TransactionDetails").child(acc_type+String.valueOf(acc)+(String)formatter.format(date)).child("atm_no").setValue(obj.getString("ATMid"));

                    FirebaseDatabase.getInstance().getReference().child("accounts").child(String.valueOf(acc)).child("transactionIDList").setValue(ATMTransactionIdList+"*"+acc_type+String.valueOf(acc)+(String)formatter.format(date));


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
    }
    public void onClick(View v)
    {
        if(v.getId()==R.id.buttonQR){
            amt =Float.parseFloat(getIntent().getExtras().getString("amount"));
            acc_type=getIntent().getExtras().getString("acc_type");
            pin=Integer.parseInt(getIntent().getExtras().getString("pin"));
            acc=Integer.parseInt(getIntent().getExtras().getString("acc"));
            method_used="qr";
            //intent.putExtra("amount",amt);
            //intent.putExtra("pin",pin);
            //intent.putExtra("acc_type",acc_type);
            //intent.putExtra("acc",acc);
            System.out.println("pin="+pin);
            System.out.println("amt="+amt);
            System.out.println("acc="+acc);
            System.out.println("acc_type="+acc_type);
            formatter = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
            date = new Date();

            //creating transaction details node
            TransactionDetails td= new TransactionDetails();
            td.setValues(amt,acc,acc_type,"","",formatter.format(date),false,method_used);
            DatabaseReference pathReference = FirebaseDatabase.getInstance().getReference().child("TransactionDetails");
            final Task<Void> voidTask = pathReference.child(acc_type+String.valueOf(acc)+(String) formatter.format(date)).setValue(td).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
            //updating current TransactionId in the account Node
            FirebaseDatabase.getInstance().getReference().child("accounts").child(String.valueOf(acc)).child("currentTransactionID").setValue(acc_type+String.valueOf(acc)+(String)formatter.format(date));;

            //updating current TransactionIdList in the account Node

            FirebaseDatabase.getInstance().getReference().child("accounts").child(String.valueOf(acc)).child("transactionIDList").addListenerForSingleValueEvent( new ValueEventListener() {
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
            FirebaseDatabase.getInstance().getReference().child("accounts").child(String.valueOf(acc)).child("transactionIDList").setValue(TransactionIdList+"*"+acc_type+String.valueOf(acc)+(String)formatter.format(date));


            qrScan.initiateScan();
        }
    }

    }



