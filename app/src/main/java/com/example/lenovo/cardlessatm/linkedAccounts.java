package com.example.lenovo.cardlessatm;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class linkedAccounts extends AppCompatActivity {

    String islinked,x;
    ListView listView;
    public static final String acc="acc num";
    private ArrayList<String> mUserdetails=new ArrayList<>();

    List<Userdetails> accounts;
    Userdetails profile2=new Userdetails();
    Userdetails userdetails=new Userdetails();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkedaccounts);
        accounts=new ArrayList<>();
        listView=(ListView)findViewById(R.id.listView);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        SharedPreferences s=getSharedPreferences("Mobile",0);
        String password=s.getString("Phone","");

        String p=String.valueOf(password);
        final DatabaseReference ref = database.getReferenceFromUrl("https://cardlessatm-1a9ff.firebaseio.com/accounts/");

// Attach a listener to read the data at your profile reference
        ref.orderByChild("isLinked").equalTo("yes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                accounts.clear();
                for(DataSnapshot trackSnapshot :dataSnapshot.getChildren()){
                    profile2 = trackSnapshot.getValue(Userdetails.class);

                    if(profile2==null){
                        Toast.makeText(getApplicationContext(), "enter registered number in accounts tab", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        accounts.add(profile2);

                    }

                }
                UserAccountsList adapter=new UserAccountsList(linkedAccounts.this,accounts);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        userdetails=accounts.get(i);
                        Toast.makeText(linkedAccounts.this, "perform action", Toast.LENGTH_SHORT).show();
                        islinked=userdetails.getIsLinked();
                        if(islinked.equals("No")) {
                            Intent intent = new Intent(getApplicationContext(), PinVerifyActivity.class);
                            intent.putExtra(acc, userdetails.getAcc_no());
                            startActivity(intent);
                        }
                    else{
                            Toast.makeText(linkedAccounts.this, "perform action", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(linkedAccounts.this, Withdraw.class);
                            intent.putExtra("acc",String.valueOf(userdetails.getAcc_no()));
                            startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });




    }
}

