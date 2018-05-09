package com.example.lenovo.cardlessatm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{

    Button login,signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=(Button)findViewById(R.id.buttonLogin);
        signup=(Button)findViewById(R.id.buttonSignup);
        login.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        if(v.getId()==R.id.buttonLogin) {
            Log.d("button", "BUTTON1 WAS PRESSED");
            Intent intent = new Intent(this, Withdraw.class);
            startActivity(intent);
        }
            else if(v.getId()==R.id.buttonSignup)
            Log.d("button","BUTTON2 WAS PRESSED");
            //Intent intent = new Intent(this, Signup.class);
            //startActivity(intent);
    }
}
