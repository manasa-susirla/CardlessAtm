package com.example.lenovo.cardlessatm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TransactionReceipts extends AppCompatActivity {
    Userdetails ud=new Userdetails();
    int acc;
    public String d="hello";
    String TID;
    Transdetails td=new Transdetails();
    private TextView tRs,tPh,tAc,tAmt,tDt;
    Transdetails transdetails=new Transdetails();
    Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_receipts);
        tRs=(TextView)findViewById(R.id.textViewRs);
        tPh=(TextView)findViewById(R.id.textViewDPhone);
        tAc=(TextView)findViewById(R.id.textViewDAcc);
        tAmt=(TextView)findViewById(R.id.textViewDAmt);
        tDt=(TextView)findViewById(R.id.textViewDT);
        go=(Button)findViewById(R.id.buttonGo);
        acc=Integer.parseInt(getIntent().getExtras().getString("acc"));
        TID=getIntent().getExtras().getString("TransID");
        final DatabaseReference mref=FirebaseDatabase.getInstance().getReferenceFromUrl("https://cardlessatm-1a9ff.firebaseio.com/");
        mref.child("accounts").child(String.valueOf(acc)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ud=dataSnapshot.getValue(Userdetails.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mref.child("TransactionDetails").child(TID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                td=dataSnapshot.getValue(Transdetails.class);
                //System.out.println(td.getAmount());
                String R= "Rs"+String.valueOf(td.getAmount());

                if(td.isComplete()){
                    //tRs.setText(dataSnapshot.getValue());
                    tRs.setText(R);
                    tPh.setText(String.valueOf(ud.getMobile()));
                    tAc.setText(String.valueOf(td.getAcc_no()));
                    tAmt.setText(String.valueOf(td.getAmount()));
                    tDt.setText(String.valueOf(td.getDate_of_Transaction()));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent in=new Intent(getApplicationContext(),AccActivity.class);
                startActivity(in);
            }
        });
    }
}

