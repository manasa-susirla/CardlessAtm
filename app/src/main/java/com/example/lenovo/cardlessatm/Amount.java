package com.example.lenovo.cardlessatm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Amount extends AppCompatActivity implements View.OnClickListener {
    EditText Amount;
    Button proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);
        Amount = (EditText) findViewById(R.id.editTextAmount);
        proceed = (Button) findViewById(R.id.buttonProceed);
        proceed.setOnClickListener(this);
    }

    public void onClick(View v) {
        try {
            if (v.getId() == R.id.buttonProceed) {
                Intent intent = new Intent(this, PinDetails.class);
                String acc_type=getIntent().getExtras().getString("acc_type");
                System.out.println("account type="+acc_type);

                String amt = Amount.getText().toString();
                //Create the bundle
                //Bundle bundle = new Bundle();
                //Add your data to bundle
                //bundle.putString("amount", amt);
                //Add the bundle to the intent
                intent.putExtra("amount",amt);
                intent.putExtra("acc_type",acc_type);
                //Fire that second activity
                startActivity(intent);
                setContentView(R.layout.activity_pin_details);
            }
        } catch (Exception e) {

        }
    }
}
