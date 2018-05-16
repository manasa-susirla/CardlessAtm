package com.example.lenovo.cardlessatm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Withdraw extends AppCompatActivity implements  View.OnClickListener{
    Button withdraw;String acc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        acc=getIntent().getExtras().getString("acc");
        withdraw=(Button)findViewById(R.id.buttonWithdraw);
        withdraw.setOnClickListener(this);
    }
    public void onClick(View v)
    {
        if(v.getId()==R.id.buttonWithdraw) {
            Log.d("button", "BUTTON1 WAS PRESSED");
            Intent intent = new Intent(this, Accounts.class);
            intent.putExtra("acc",acc);
            startActivity(intent);
            setContentView(R.layout.activity_accounts);
        }

    }
}
