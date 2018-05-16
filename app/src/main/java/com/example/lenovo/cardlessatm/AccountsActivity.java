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

public class AccountsActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_accountsactivity);
        accounts=new ArrayList<>();
        listView=(ListView)findViewById(R.id.listView);
        final Intent intent=getIntent();
        System.out.println("hello");
        /*String previousActivity= intent.getStringExtra("FROM_ACTIVITY");
        System.out.println("hello");
        System.out.println(previousActivity);
        if(previousActivity=="CreatePasswordActivity.class"){
            x=intent.getStringExtra(CreatePasswordActivity.ph);

        }
        else if(previousActivity=="EnterActivity"){*/
            x=intent.getStringExtra(CreatePasswordActivity.ph);
            if(x==null){
                x=intent.getStringExtra(EnterActivity.ph);
            }

        SharedPreferences s=getSharedPreferences("Mobile",0);
        String password=s.getString("Phone","");

        //System.out.println(p);
        System.out.println(String.valueOf(password));
        String p=String.valueOf(password);
        //final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mUserdetails);
        //listView.setAdapter(arrayAdapter);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReferenceFromUrl("https://cardlessatm-1a9ff.firebaseio.com/accounts/");

// Attach a listener to read the data at your profile reference
        ref.orderByChild("mobile").equalTo(Long.valueOf(p)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                accounts.clear();
                for(DataSnapshot trackSnapshot :dataSnapshot.getChildren()){
                    profile2 = trackSnapshot.getValue(Userdetails.class);

                    if(profile2==null){
                        Toast.makeText(getApplicationContext(), "enter registered number", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        accounts.add(profile2);
                        System.out.println(profile2.getAcc_no());

                    }

                }
                UserAccountsList adapter=new UserAccountsList(AccountsActivity.this,accounts);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        userdetails=accounts.get(i);
                        islinked=userdetails.getIsLinked();
                        if(islinked.equals("No")){
                            Intent intent=new Intent(getApplicationContext(),PinVerifyActivity.class);
                            //intent.putExtra(acc,Long.valueOf()userdetails.getAcc_no());
                            //intent.putExtra(acc,userdetails.getAcc_no());
                            intent.putExtra(acc,String.valueOf(userdetails.getAcc_no()));
                            startActivity(intent);
                        }else{
                            Intent intent1=new Intent(getApplicationContext(),LeftNavigationActivity.class);
                            startActivity(intent1);
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
