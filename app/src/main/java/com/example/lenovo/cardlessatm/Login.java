package com.example.lenovo.cardlessatm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity implements  View.OnClickListener{

    Button login2;
    EditText Password,Emailid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login2=(Button)findViewById(R.id.buttonLogin2);
        Password=(EditText)findViewById(R.id.editTextPassword);
        Emailid=(EditText)findViewById(R.id.editTextEmailid);
        login2.setOnClickListener(this);
    }
    public void onClick(View v)
    {
        if(v.getId()==R.id.buttonLogin2) {

            Intent intent = new Intent(this, Withdraw.class);
            startActivity(intent);
            setContentView(R.layout.activity_withdraw);
        }

    }
}
