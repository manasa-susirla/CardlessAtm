package com.example.lenovo.cardlessatm;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatePasswordActivity extends AppCompatActivity {
    public static final String ph="mobile no";


    EditText editText1,editText2,editText3;
    Button button;
    String question,answer;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        //String ph=String.valueOf(x);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.questions_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);



       /* Toast.makeText(this,"User added",Toast.LENGTH_LONG).show();*/


        editText1=(EditText)findViewById(R.id.editText1);
        editText2=(EditText)findViewById(R.id.editText2);
        editText3=(EditText)findViewById(R.id.editText3);
        button=(Button)findViewById(R.id.button);
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



        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String text1=editText1.getText().toString();
                String text2=editText2.getText().toString();
                answer=editText3.getText().toString();
                if(text1.equals("")||text2.equals("")){
                    Toast.makeText(CreatePasswordActivity.this,"No password entered!",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(text1.equals(text2)){
                        SharedPreferences settings=getSharedPreferences("PREFS",0);
                        SharedPreferences.Editor editor= settings.edit();
                        editor.putString("Password",text1);
                        editor.apply();
                        String id= ref.push().getKey();
                        System.out.print(id);
                        Security qa=new Security(question, answer);
                        ref.child(id).setValue(qa);
                        final Intent in=getIntent();
                        String x=in.getStringExtra(LoginActivity.ph);
                        Intent intent= new Intent(getApplicationContext(),AccActivity.class);
                      intent.putExtra(ph,String.valueOf(x));
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(CreatePasswordActivity.this,"Password doesn't match!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

}

