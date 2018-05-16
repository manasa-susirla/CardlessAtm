package com.example.lenovo.cardlessatm;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EnterToResetActivity extends AppCompatActivity {

    Button next;
    EditText editText5;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_to_reset);
        editText5 = (EditText) findViewById(R.id.editText5);
        next = (Button) findViewById(R.id.button_next);
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        password = settings.getString("Password", "");
        next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        String text = editText5.getText().toString();

                                        if (text.equals(password)) {
                                            Intent intent = new Intent(getApplicationContext(), CreatePasswordActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                    }

                                }
        );
    }
}