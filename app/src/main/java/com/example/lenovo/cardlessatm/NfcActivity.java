package com.example.lenovo.cardlessatm;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.tech.NfcA;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;
import android.widget.Toast;
import java.nio.charset.Charset;

import org.w3c.dom.Text;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.nfc.NdefRecord.createMime;
//import static com.example.android.nfc.MainActivity.createNewTextRecord;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.Charset;






    class TransactionDetail
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
    public class NfcActivity extends Activity implements CreateNdefMessageCallback {
        NfcAdapter mNfcAdapter;
        TextView textView;
        float amt;
        int pin,acc;
        String acc_type,method_used,TransactionIdList;
        SimpleDateFormat formatter;
        Date date;
        FirebaseDatabase fd;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_nfc);
            TextView textView = (TextView) findViewById(R.id.textView);
            amt =Float.parseFloat(getIntent().getExtras().getString("amount"));
            acc_type=getIntent().getExtras().getString("acc_type");
            pin=Integer.parseInt(getIntent().getExtras().getString("pin"));
            acc=Integer.parseInt(getIntent().getExtras().getString("acc"));
            method_used=getIntent().getExtras().getString("method_used");
            // Check for available NFC Adapter
            mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
            if (mNfcAdapter == null) {
                Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
                finish();
                return;
            }
            formatter = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
            date = new Date();

            //creating transaction details node
            TransactionDetail td= new TransactionDetail();
            td.setValues(amt,acc,acc_type,"","",formatter.format(date),false,method_used);
            DatabaseReference pathReference = FirebaseDatabase.getInstance().getReference().child("TransactionDetails");
            final Task<Void> voidTask = pathReference.child(acc_type+String.valueOf(acc)+(String) formatter.format(date)).setValue(td).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
            //updating current TransactionId in the account Node
            FirebaseDatabase.getInstance().getReference().child("accounts").child(String.valueOf(acc)).child("currentTransactionID").setValue(acc_type+String.valueOf(acc)+(String)formatter.format(date));

            //updating current TransactionIdList in the account Node

            FirebaseDatabase.getInstance().getReference().child("accounts").child(String.valueOf(acc)).child("transactionIDList").addListenerForSingleValueEvent(new ValueEventListener() {
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

            // Register callback
            mNfcAdapter.setNdefPushMessageCallback(this, this);
        }

        @Override
        public NdefMessage createNdefMessage(NfcEvent event) {
            String text = (acc_type+String.valueOf(acc)+(String)formatter.format(date));
            NdefMessage msg = new NdefMessage(
                    new NdefRecord[] { createMime(
                            "application/vnd.com.example.hp.atm", text.getBytes())
                            /**
                             * The Android Application Record (AAR) is commented out. When a device
                             * receives a push with an AAR in it, the application specified in the AAR
                             * is guaranteed to run. The AAR overrides the tag dispatch system.
                             * You can add it back in to guarantee that this
                             * activity starts when receiving a beamed message. For now, this code
                             * uses the tag dispatch system.
                            */
                            ,NdefRecord.createApplicationRecord("com.example.hp.atm")
                    });
            return msg;
        }

        @Override
        public void onResume() {
            super.onResume();
            // Check to see that the Activity started due to an Android Beam
            if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
                processIntent(getIntent());
            }
        }

        @Override
        public void onNewIntent(Intent intent) {
            // onResume gets called after this to handle the intent
            setIntent(intent);
        }

        /**
         * Parses the NDEF Message from the intent and prints to the TextView
         */
        void processIntent(Intent intent) {
            textView = (TextView) findViewById(R.id.textView);
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);
            // only one message sent during the beam
            NdefMessage msg = (NdefMessage) rawMsgs[0];
            // record 0 contains the MIME type, record 1 is the AAR, if present
            textView.setText(new String(msg.getRecords()[0].getPayload()));
        }
    }



