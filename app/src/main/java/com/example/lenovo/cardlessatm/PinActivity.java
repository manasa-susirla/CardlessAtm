package com.example.lenovo.cardlessatm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import android.support.v7.widget.RecyclerView;
import com.andrognito.pinlockview.PinLockView;

public class PinActivity extends AppCompatActivity {
    PinLockView mPinLockView;
    IndicatorDots mIndicatorDots;
    private String TAG="pinlock";
    public static String mPin="0000";
    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            Log.d(TAG, "Pin complete: " + pin);

           Intent intent=new Intent(PinActivity.this,Reenter.class);
            intent.putExtra(mPin,pin);
            startActivity(intent);


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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinlock);

        mPinLockView=(PinLockView) findViewById(R.id.pin_lock_view);
        mPinLockView.setPinLockListener(mPinLockListener);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);
        mPinLockView.attachIndicatorDots(mIndicatorDots);



    }


}
