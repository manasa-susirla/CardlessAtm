package com.example.lenovo.cardlessatm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AccActivity extends AppCompatActivity {

    private RecyclerView mPeopleRV;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Accs, AccActivity.AccountsViewHolder> mPeopleRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc);

        setTitle("Accs");

        //"Accs" here will reflect what you have called your database in Firebase.
        mDatabase = FirebaseDatabase.getInstance().getReference().child("accounts");
        mDatabase.keepSynced(true);

        mPeopleRV = (RecyclerView) findViewById(R.id.myRecycleView);

        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://cardlessatm-1a9ff.firebaseio.com/");
        //Query the database
        //Query personsQuery = personsRef.orderByKey()
        SharedPreferences settings=getSharedPreferences("PREFS",0);
        String password=settings.getString("Password","");
        Query personsQuery = personsRef.child("accounts").orderByChild("mobile").equalTo(8142335362L);

        mPeopleRV.hasFixedSize();
        mPeopleRV.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<Accs>().setQuery(personsQuery,Accs.class).build();

        mPeopleRVAdapter = new FirebaseRecyclerAdapter<Accs, AccActivity.AccountsViewHolder>(personsOptions) {

            @Override
            protected void onBindViewHolder(AccActivity.AccountsViewHolder holder, final int position, final Accs model) {
                holder.setTitle("xxxx xxxx xxxx " +String.valueOf(model.getAcc_no()));
                //holder.setDesc("Current Balance"  +String.valueOf(model.getBalance()));
                //holder.setImage(getBaseContext(),"account"  );
                System.out.println(" out onClick");
                final int pin = model.getPin();
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(model.getIsLinked().equals("yes")){

                            System.out.println("In onClick islinked true");
                            Intent intent = new Intent(getApplicationContext(), AmtPin.class);
                            intent.putExtra("acc_no",String.valueOf(model.getAcc_no()));
                            intent.putExtra("balance",String.valueOf(model.getBalance()));
                            intent.putExtra("pin", String.valueOf(pin));
                            startActivity(intent);}
                        else{
                            System.out.println(" out onClick islinked false");
                            /*Intent intent = new Intent(getApplicationContext(), RegisterAccounts.class);
                            intent.putExtra("acc_no",String.valueOf(model.getAcc_no()));
                            intent.putExtra("balance",String.valueOf(model.getBalance()));
                            intent.putExtra("pin", String.valueOf(pin));
                            startActivity(intent);*/
                        }
                    }
                });
            }

            @Override
            public AccActivity.AccountsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.accs_row, parent, false);

                return new AccActivity.AccountsViewHolder(view);
            }
        };

        mPeopleRV.setAdapter(mPeopleRVAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPeopleRVAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPeopleRVAdapter.stopListening();


    }

    public static class AccountsViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public AccountsViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }
        public void setTitle(String title){
            TextView post_title = (TextView)mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }

        public void setImage(Context ctx, String image){
            ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            post_image.setImageResource(R.drawable.plus_three);
            //Picasso.with(ctx).load(image).into(post_image);
        }

    }
}