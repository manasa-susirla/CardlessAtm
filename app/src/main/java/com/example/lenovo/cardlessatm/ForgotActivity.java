package com.example.lenovo.cardlessatm;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ForgotActivity extends AppCompatActivity {

    String question;
    Button reset;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        reset=(Button)findViewById(R.id.change);
        spinner = (Spinner) findViewById(R.id.spinner2);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.questions_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        question =(String) parent.getItemAtPosition(position);

                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        // Another interface callback
                    }
                }
        );

        reset.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {

                                         //  String text=editText.getText().toString();
                                         // if(text.equals(password)){
                                         Intent intent= new Intent(getApplicationContext(),CreatePasswordActivity.class);
                                         startActivity(intent);
                                         finish();
                                     }

                                 }
        );
    }
}

