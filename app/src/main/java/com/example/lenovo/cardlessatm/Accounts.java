package com.example.lenovo.cardlessatm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Accounts extends AppCompatActivity implements View.OnClickListener{
Button savings,current;String acc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        acc=getIntent().getExtras().getString("acc");
        savings = (Button)findViewById(R.id.buttonSavings);
        savings.setOnClickListener(this);
    }
    public void onClick(View v)
    {
        if(v.getId()==R.id.buttonSavings)
        {
            Intent intent = new Intent(this, Amount.class);
            intent.putExtra("acc_type","savings");
            intent.putExtra("acc",acc);
            startActivity(intent);
            setContentView(R.layout.activity_amount);
        }
    }
}
