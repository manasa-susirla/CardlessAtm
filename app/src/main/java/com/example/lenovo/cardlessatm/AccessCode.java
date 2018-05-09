package com.example.lenovo.cardlessatm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.database.FirebaseDatabase;
import java.util.*;
import org.w3c.dom.Text;
import static java.lang.Thread.sleep;

public class AccessCode extends AppCompatActivity {
TextView code;
FirebaseDatabase fd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_code);
        code=(TextView)findViewById(R.id.textView10);

        // Java code to explain how to generate OTP

// Here we are using random() method of util
// class in Java

        code.setText(OTP());

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

*/
        for(int i=0;i<60;i++)
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }
    static String OTP()
    {
        System.out.println("Generating OTP using random() : ");
        System.out.print("You OTP is : ");

        // Using numeric values
        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*_=+-/.?<>)";
        Random r=new Random();
        int len=r.nextInt(10);

        String values = Capital_chars + Small_chars +
                numbers + symbols;
        // Using random method
        Random rndm_method = new Random();

        String otp="";

        for (int i = 0; i < len; i++)
        {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            otp+=numbers.charAt(rndm_method.nextInt(values.length()));
        }
        return otp;
    }
}
