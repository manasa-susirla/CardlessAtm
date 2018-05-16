package com.example.lenovo.cardlessatm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class AmtPin extends AppCompatActivity {

    TextView textview,textview2;
    EditText edittext,edittext2;
    String acc,bal,AMT,PIN;
    String pin;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amt_pin);


        textview = (TextView) findViewById(R.id.acc_no);
        textview2 = (TextView) findViewById(R.id.balance);
        edittext = (EditText) findViewById(R.id.enter_amt);
        edittext2 = (EditText) findViewById(R.id.enter_pin);
        Intent intent = getIntent();
        acc = intent.getExtras().getString("acc_no");
        bal = intent.getExtras().getString("balance");
        pin = intent.getExtras().getString("pin");
        textview.setText("xxxx xxxx xxxx "+acc);
        textview2.setText("Balance: "+bal);
        button=(Button)findViewById(R.id.button_proceed);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AMT=edittext.getText().toString().trim();
                        PIN=edittext2.getText().toString().trim();
                        System.out.println(AMT);
                        if(pin.equals(PIN)){
                            System.out.println(acc);
                            Intent intent=new Intent(getApplicationContext(),Selectmethod.class);
                            intent.putExtra("acc_no",acc);
                            intent.putExtra("balance",bal);
                            intent.putExtra("pin", pin);
                            intent.putExtra("amount",AMT);
                            startActivity(intent);
                        }
                    }
                });

    }

}

