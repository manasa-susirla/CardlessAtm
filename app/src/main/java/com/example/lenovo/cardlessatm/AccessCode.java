package com.example.lenovo.cardlessatm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.*;
import org.w3c.dom.Text;
import static java.lang.Thread.sleep;

class TransactionDet
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

public class AccessCode extends AppCompatActivity {
TextView code;
float amt;
int pin,acc;
String acc_type,method_used,TransactionIdList;
    SimpleDateFormat formatter;
    String otp;
    Date date;
    FirebaseDatabase fd;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_code);
        amt =Float.parseFloat(getIntent().getExtras().getString("amount"));
        System.out.println("amt="+amt);
        acc_type=getIntent().getExtras().getString("acc_type");
        pin=Integer.parseInt(getIntent().getExtras().getString("pin"));
        acc=Integer.parseInt(getIntent().getExtras().getString("acc"));
        method_used=getIntent().getExtras().getString("acc_type");
        code=(TextView)findViewById(R.id.textView10);

        // Java code to explain how to generate OTP

// Here we are using random() method of util
// class in Java
otp="";
        try {
            otp=OTP();
            code.setText(otp);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
        }


        /*
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
            System.out.println("acc_type="+acc_type);*/
            formatter = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
            date = new Date();

            //creating transaction details node
            TransactionDet td= new TransactionDet();
            td.setValues(amt,acc,acc_type,"",otp,formatter.format(date),false,method_used);
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
        new Timer().schedule(new TimerTask(){
            public void run() {
                AccessCode.this.runOnUiThread(new Runnable() {
                    public void run() {

                        Intent intent=new Intent(AccessCode.this,TransactionReceipts.class);
                        intent.putExtra("acc",String.valueOf(acc));
                        intent.putExtra("TransID",acc_type+String.valueOf(acc)+(String) formatter.format(date));
                        startActivity(intent);
                    }
                });
            }
        }, 60000);
/*
        for(int i=0;i<60;i++)
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

    }
    static String OTP() throws Exception
    {
        System.out.println("Generating OTP using random() : ");
        System.out.print("You OTP is : ");

        // Using numeric values
        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*_=+-/.?<>)";
        Random r=new Random();
        int len=6;
        // int len=r.nextInt(10);
    System.out.println("len="+len);
        String values = Capital_chars + Small_chars +
                numbers + symbols;
        // Using random method
        System.out.println("values-length"+values+" "+values.length());
        Random rndm_method = new Random();

        String otp="";

        for (int i = 0; i < len; i++)
        {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            otp+=values.charAt(rndm_method.nextInt(values.length()));
        }
        System.out.println("otp-"+otp);
        return otp;
    }
}
