package com.example.lenovo.cardlessatm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AccActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView mPeopleRV;
    private DatabaseReference mDatabase;
    Button settings;
    private FirebaseRecyclerAdapter<Accs, AccActivity.AccountsViewHolder> mPeopleRVAdapter;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc);
        // settings=(Button)findViewById(R.id.settings);
/*        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(AccActivity.this,SettingsActivity.class);
                AccActivity.this.startActivity(in);
            }
        });*/


        setTitle("");

        //"Accs" here will reflect what you have called your database in Firebase.
        mDatabase = FirebaseDatabase.getInstance().getReference().child("accounts");
        mDatabase.keepSynced(true);

        mPeopleRV = (RecyclerView) findViewById(R.id.myRecycleView);

        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://cardlessatm-1a9ff.firebaseio.com/");
        //Query the database
        //Query personsQuery = personsRef.orderByKey()
        SharedPreferences settings=getSharedPreferences("Mobile",0);
        String password=settings.getString("Phone","");
        Query personsQuery = personsRef.child("accounts").orderByChild("mobile").equalTo(Long.valueOf(password));

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
                Log.d("Database PIN", "Pin complete: " + pin);
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        System.out.println("In onClick islinked true");
                        Log.d("Database PIN", "Pin inside: " + pin);
                        Intent intent = new Intent(getApplicationContext(), AmtPin.class);
                        intent.putExtra("acc_no", String.valueOf(model.getAcc_no()));
                        intent.putExtra("balance", String.valueOf(model.getBalance()));
                        intent.putExtra("pin", String.valueOf(pin));
                        startActivity(intent);

                        System.out.println(" out onClick islinked false");
                            /*Intent intent = new Intent(getApplicationContext(), RegisterAccounts.class);
                            intent.putExtra("acc_no",String.valueOf(model.getAcc_no()));
                            intent.putExtra("balance",String.valueOf(model.getBalance()));
                            intent.putExtra("pin", String.valueOf(pin));
                            startActivity(intent);*/
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








    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.acc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {


        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            startActivity(new Intent(getApplicationContext(),SignOutActivity.class));
            // Handle the camera action

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
