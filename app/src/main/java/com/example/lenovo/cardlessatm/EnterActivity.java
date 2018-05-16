package com.example.lenovo.cardlessatm;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterActivity extends AppCompatActivity {
    public static final String ph="mobile no";
    EditText editText;
    Button button;
    Button reset;
    String password;
    Button forgot;

    protected void oncCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        editText=(EditText)findViewById(R.id.editText);
        button=(Button)findViewById(R.id.button);
        reset=(Button)findViewById(R.id.button_reset);
        forgot=(Button)findViewById(R.id.button_forgot);
        SharedPreferences settings=getSharedPreferences("PREFS",0);
        password=settings.getString("Password","");

        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          String text=editText.getText().toString();
                                          if(text.equals(password)){
                                              System.out.println("hello");
                                              final Intent in=getIntent();
                                              String x=in.getStringExtra(MainActivity.ph);
                                              Intent intent= new Intent(getApplicationContext(),AccountsActivity.class);
                                            intent.putExtra(ph,String.valueOf(x));
                                            startActivity(intent);
                                          finish();
                                      }else{
                                             Toast.makeText(EnterActivity.this,"Wrong Password!",Toast.LENGTH_SHORT).show();
                                             } }
                                }
        );
        reset.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {

                                         //  String text=editText.getText().toString();
                                         // if(text.equals(password)){
                                         Intent intent= new Intent(getApplicationContext(),EnterToResetActivity.class);
                                         startActivity(intent);
                                         finish();
                                     }

                                 }
        );
        forgot.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          //  String text=editText.getText().toString();
                                          // if(text.equals(password)){
                                          Intent intent= new Intent(getApplicationContext(),SignOutActivity.class);
                                          startActivity(intent);
                                          finish();
                                      }

                                  }
        );

    }
}

