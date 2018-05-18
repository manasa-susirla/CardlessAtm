package com.example.lenovo.cardlessatm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AmtPin extends AppCompatActivity {

    TextView textview,textview2;
    EditText edittext,edittext2;
    Button tick;
    String acc;
    String bal;
    String AMT;
    String PIN;
    String pin;
    Button button;
    String TAG="LOCK";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amt_pin);
        Intent intent = getIntent();
        acc = intent.getExtras().getString("acc_no");
        bal = intent.getExtras().getString("balance");
        pin = intent.getExtras().getString("pin");
       final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref1 = database.getReferenceFromUrl("https://cardlessatm-1a9ff.firebaseio.com/accounts/").child(acc);


        textview = (TextView) findViewById(R.id.acc_no);
        textview2 = (TextView) findViewById(R.id.balance);
        edittext = (EditText) findViewById(R.id.enter_amt);
        edittext2 = (EditText) findViewById(R.id.enter_pin);
        button=(Button)findViewById(R.id.button_proceed);






        Log.d("Intent PIN", "Pin intent: " + pin);
        textview.setText("xxxx xxxx xxxx "+acc);

        tick=(Button)findViewById(R.id.tick);

        tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PIN=edittext2.getText().toString().trim();
                if(pin.equals(PIN)){
                   ref1.child("isLinked").setValue("yes");
                    textview2.setText("Balance: "+bal);
                    textview2.setVisibility(View.VISIBLE);
                    edittext.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    tick.setVisibility(View.GONE);

                }
                else{
                    textview2.setVisibility(View.VISIBLE);
                    textview2.setText("Wrong Pin!");
                    Toast.makeText(getApplicationContext(), "Wrong Pin!", Toast.LENGTH_LONG).show();
                }

            }
        });
        edittext2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);



        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AMT=edittext.getText().toString().trim();
                        if(AMT.equals("")||AMT==null||AMT.equals("0")){
                            Toast.makeText(getApplicationContext(), "Enter Amount to proceed", Toast.LENGTH_LONG).show();

                        }
                  else {
                            System.out.println(AMT);

                            System.out.println(acc);
                            Intent intent = new Intent(getApplicationContext(), Selectmethod.class);
                            intent.putExtra("acc_no", acc);
                            intent.putExtra("balance", bal);
                            intent.putExtra("pin", pin);
                            intent.putExtra("amount", AMT);
                            startActivity(intent);
                        }
                    }
                });

    }

}

