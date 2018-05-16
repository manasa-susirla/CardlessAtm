package com.example.lenovo.cardlessatm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;

public class ScreenLock extends AppCompatActivity {
    PinLockView mPinLockView;
    IndicatorDots mIndicatorDots;
    private String TAG="pinlock";
    public static String mPin="0000";
    public String lock;
    TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_lock);
        display=(TextView) findViewById(R.id.display);

        mPinLockView=(PinLockView) findViewById(R.id.pin_lock_view);
        mPinLockView.setPinLockListener(mPinLockListener);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);
        mPinLockView.attachIndicatorDots(mIndicatorDots);

    }
    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            Log.d(TAG, "Pin complete: " + pin);
            SharedPreferences settings=getSharedPreferences("Pin",0);
            lock=settings.getString("mPin","");
            if(lock.equals(pin)) {


                Intent intent = new Intent(ScreenLock.this, AccActivity.class);
                startActivity(intent);
            }
            else{

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
