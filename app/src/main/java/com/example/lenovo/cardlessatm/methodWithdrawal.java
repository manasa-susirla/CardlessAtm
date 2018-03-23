package com.example.lenovo.cardlessatm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class methodWithdrawal extends AppCompatActivity implements View.OnClickListener{

    Button qr,sms,nfc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_method_withdrawal);
        qr=(Button)findViewById(R.id.buttonQR);
        sms=(Button)findViewById(R.id.buttonSMS);
        nfc=(Button)findViewById(R.id.buttonNFC);
        qr.setOnClickListener(this);
        sms.setOnClickListener(this);
        nfc.setOnClickListener(this);
    }
    public void onClick(View v)
    {
        if(v.getId()==R.id.buttonQR)
        {

        }
    }
}
