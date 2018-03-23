package com.example.lenovo.cardlessatm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class Amount extends AppCompatActivity implements View.OnClickListener{

    Button proceed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);
        proceed=(Button)findViewById(R.id.buttonProceed);
    }
    public void onClick(View v)
    {
        if(v.getId()==R.id.buttonProceed)
        {
            Intent intent = new Intent(this, PinDetails.class);
            startActivity(intent);
        }
    }
}
