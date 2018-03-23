package com.example.lenovo.cardlessatm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PinDetails extends AppCompatActivity implements View.OnClickListener{

    Button proceed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_details);
        proceed=(Button)findViewById(R.id.buttonProceed2);
        proceed.setOnClickListener(this);
    }
    public void onClick(View v)
    {
        if(v.getId()==R.id.buttonProceed2)
        {
            Intent intent = new Intent(this, methodWithdrawal.class);
            startActivity(intent);
        }
    }
}
