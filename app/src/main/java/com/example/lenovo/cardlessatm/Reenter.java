package com.example.lenovo.cardlessatm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;

public class Reenter extends AppCompatActivity {
    PinLockView mPinLockView;
    IndicatorDots mIndicatorDots;
    private String TAG="pinlock";
    public String p;
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reenter);
        mPinLockView=(PinLockView) findViewById(R.id.pin_lock_view);
        mPinLockView.setPinLockListener(mPinLockListener);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);
        mPinLockView.attachIndicatorDots(mIndicatorDots);
        Intent in=getIntent();
        p=in.getStringExtra(PinActivity.mPin);

         display=(TextView) findViewById(R.id.profile_name);
    }
    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            Log.d(TAG, "Pin complete: " + pin);
            System.out.print(p);
            if (p.equals(pin)) {

                display.setText("Confirmed!");
                SharedPreferences settings=getSharedPreferences("Pin",0);
                SharedPreferences.Editor editor= settings.edit();
                editor.putString("mPin",pin);
                editor.apply();
                Intent intent = new Intent(Reenter.this, AccActivity.class);
                startActivity(intent);

            } else {
                display.setText("Wrong Pin");
                Toast.makeText(getApplicationContext(), "Pin Not Matched. Re-enter", Toast.LENGTH_SHORT).show();


            }
        }
        @Override
        public void onEmpty() {
            Log.d(TAG, "Pin empty");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
        }
    };


}
