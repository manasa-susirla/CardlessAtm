package com.example.lenovo.cardlessatm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener {
    public static final String ph="mobile no";
    private static final String TAG = "PhoneAuthActivity";
    private int FLAG=0;
    Userdetails profile=new Userdetails();

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    int back = Color.parseColor("#70e2aa23");
    int pink = Color.parseColor("#70dd2476");
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;


    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private ViewGroup mPhoneNumberViews;
    private ViewGroup mSignedInViews;

    private TextView mStatusText;
    private TextView mDetailText;
    private TextView mtext1;
    private TextView mtext2;
    private TextView mtext3;
    private TextView mtext4;
    private TextView mtext5;

    private TextView mcode;

    private EditText mPhoneNumberField;
    private EditText mVerificationField;

    public static String userID;
    private Button mStartButton;
    private Button mVerifyButton;
    private Button mResendButton;
    private Button mSignOutButton;
    private String contactno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Restore instance state
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        // Assign views

        mtext1=(TextView)findViewById(R.id.text1);
        mtext2=(TextView)findViewById(R.id.text2);
        mcode=(TextView) findViewById(R.id.code);
        mtext4=(TextView)findViewById(R.id.text4);
        mtext5=(TextView)findViewById(R.id.text5);
        mtext3=(TextView) findViewById(R.id.text3);

        mStatusText = (TextView) findViewById(R.id.status);
        mDetailText = (TextView) findViewById(R.id.detail);

        mPhoneNumberField = (EditText) findViewById(R.id.field_phone_number);



       // Typeface custom_font = Typeface.createFromAsset(getAssets(),  "assets/fonts/Roboto-Medium.ttf");

        //mPhoneNumberField.setTypeface(custom_font);


        mVerificationField = (EditText) findViewById(R.id.field_verification_code);


        mStartButton = (Button) findViewById(R.id.button_start_verification);
        mVerifyButton = (Button) findViewById(R.id.button_verify_phone);
        mResendButton = (Button) findViewById(R.id.button_resend);
        mSignOutButton = (Button) findViewById(R.id.sign_out_button);




        // Assign click listeners
        mStartButton.setOnClickListener(this);
        mVerifyButton.setOnClickListener(this);
        mResendButton.setOnClickListener(this);
        mSignOutButton.setOnClickListener(this);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                updateUI(STATE_VERIFY_SUCCESS, credential);
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    mPhoneNumberField.setError("Invalid phone number.");
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    //  Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                    Toast.makeText(getApplicationContext(), "SMS Quota Exceeded", Toast.LENGTH_LONG).show();
                    //    Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]
                updateUI(STATE_VERIFY_FAILED);
                // [END_EXCLUDE]
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // [START_EXCLUDE]
                // Update UI
                updateUI(STATE_CODE_SENT);
                // [END_EXCLUDE]
            }
        };
        // [END phone_auth_callbacks]
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        // [START_EXCLUDE]
        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(mPhoneNumberField.getText().toString());
        }
        // [END_EXCLUDE]
    }
    // [END on_start_check_user]

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }


    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    // [END resend_verification]

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();


                            //check if user exist or not in Database


                            userID = user.getUid();

                            myRef.child("users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    if (snapshot.getValue() != null) {
                                        SharedPreferences s=getSharedPreferences("Mobile",0);
                                        SharedPreferences.Editor editor1= s.edit();
                                        editor1.putString("Phone",mPhoneNumberField.getText().toString());
                                        editor1.apply();
                                        //myRef.child("Accounts").child("SBI").child(userdetails.getAccount()).child("isPhoneLinked").setValue("Yes");
                                        Intent d=new Intent(getApplicationContext(),MainActivity.class);
                                        d.putExtra(ph,mPhoneNumberField.getText().toString());
                                        Intent y = new Intent(getApplicationContext(), PinActivity.class);
                                        //Intent d=new Intent(getApplicationContext(),Accounts.class);
                                        y.putExtra(ph,mPhoneNumberField.getText().toString());
                                        y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(y);

                                        SharedPreferences mPreferences;

                                        mPreferences = getSharedPreferences("User", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = mPreferences.edit();
                                        editor.putString("saveuserid", userID);
                                        editor.commit();

                                        //user exists, do something
                                    } else {

                                        SharedPreferences mPreferences;

                                        mPreferences = getSharedPreferences("User", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = mPreferences.edit();
                                        editor.putString("saveuserid", userID);
                                        editor.commit();

                                        contactno = mPhoneNumberField.getText().toString();
                                        //user does not exist, do something else
                                        myRef.child("users").child(userID).setValue("true");
                                        //    myRef.child("users").child(userID).child("Name").setValue("true");
                                        myRef.child("users").child(userID).child("contact").setValue(contactno);
                                        //myRef.child("Accounts").child("SBI").child(userdetails.getAccount()).child("isPhoneLinked").setValue("Yes");

                                        //myRef.child("contacts").setValue(mPhoneNumberField.getText().toString());
                                        //Intent d=new Intent(getApplicationContext(),MainActivity.class);
                                        //d.putExtra(ph,mPhoneNumberField.getText().toString());
                                        Intent y = new Intent(getApplicationContext(),PinActivity.class);
                                        //Intent d=new Intent(getApplicationContext(),Accounts.class);
                                        SharedPreferences s=getSharedPreferences("Mobile",0);
                                        SharedPreferences.Editor editor1= s.edit();
                                        editor1.putString("Phone",mPhoneNumberField.getText().toString());
                                        editor1.apply();


                                        y.putExtra(ph,mPhoneNumberField.getText().toString());
                                        y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(y);

                                    }
                                }

                                public void onCancelled(DatabaseError arg0) {
                                }
                            });


                            //  myRef.child(userID).child("title").setValue("true");
                            //    myRef.child(userID).child("description").setValue("true");
                            // myRef.child(userID).child("imageurl").setValue("true");
                            //  myRef.child(userID).child("url").setValue("true");
                            // [START_EXCLUDE]
                            updateUI(STATE_SIGNIN_SUCCESS, user);
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                mVerificationField.setError("Invalid code.");
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }
    // [END sign_in_with_phone]

    private void signOut() {
        mAuth.signOut();
        updateUI(STATE_INITIALIZED);
    }

    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user);
        } else {
            updateUI(STATE_INITIALIZED);
        }
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:
                // Initialized state, show only the phone number field and start button
                enableViews(mStartButton, mPhoneNumberField,mtext2,mtext1,mcode);
                disableViews(mVerifyButton, mResendButton, mVerificationField);
                mDetailText.setText(null);
                break;
            case STATE_CODE_SENT:
                // Code sent state, show the verification field, the
                enableViews(mVerifyButton, mResendButton, mVerificationField);
                disableViews(mStartButton,mtext1,mtext2,mcode);
                //  mDetailText.setText(R.string.status_code_sent);
                Toast.makeText(getApplicationContext(), "code sent", Toast.LENGTH_LONG).show();
                break;
            case STATE_VERIFY_FAILED:
                // Verification has failed, show all options
                enableViews(mStartButton, mVerifyButton, mResendButton, mPhoneNumberField,
                        mVerificationField);
                //  mDetailText.setText(R.string.status_verification_failed);
                Toast.makeText(getApplicationContext(), "Unable to send SMS. Check Network", Toast.LENGTH_LONG).show();
                break;
            case STATE_VERIFY_SUCCESS:
                // Verification has succeeded, proceed to firebase sign in
                disableViews(mStartButton, mVerifyButton, mResendButton, mPhoneNumberField,
                        mVerificationField);
                //  mDetailText.setText(R.string.status_verification_succeeded);

                Toast.makeText(getApplicationContext(), "verification success", Toast.LENGTH_LONG).show();

                // Set the verification text based on the credential
                if (cred != null) {
                    if (cred.getSmsCode() != null) {
                        mVerificationField.setText(cred.getSmsCode());
                    } else {
                        //         mVerificationField.setText(R.string.instant_validation);
                    }
                }

                break;
            case STATE_SIGNIN_FAILED:
                // No-op, handled by sign-in check
                //  mDetailText.setText(R.string.status_sign_in_failed);
                break;
            case STATE_SIGNIN_SUCCESS:
                // Np-op, handled by sign-in check
                break;
        }

        if (user == null) {
            // Signed out
            //  mPhoneNumberViews.setVisibility(View.VISIBLE);
            //    mSignedInViews.setVisibility(View.GONE);

            //  mStatusText.setText(R.string.signed_out);
        } else {
            // Signed in


            //  mStatusText.setText(R.string.signed_in);
            //  mDetailText.setText(getString(R.string.firebase_status_fmt, user.getUid()));
        }
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = mPhoneNumberField.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumberField.setError("Invalid phone number.");
            return false;
        }
        return true;
    }

    private void enableViews(View... views) {
        for (View v : views) {
            v.setEnabled(true);
        }
    }

    private void disableViews(View... views) {
        for (View v : views) {
            v.setEnabled(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_start_verification:
                if (!validatePhoneNumber()) {
                    return;
                }
                myRef.child("accounts").orderByChild("mobile").equalTo(Long.valueOf(mPhoneNumberField.getText().toString())).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot trackSnapshot :dataSnapshot.getChildren()){
                            profile = trackSnapshot.getValue(Userdetails.class);


                            if(profile!=null){
                                System.out.println("hello");
                                if (!validatePhoneNumber()) {
                                    return;
                                }

                                mStartButton.setBackgroundColor(pink);

                                startPhoneNumberVerification(mPhoneNumberField.getText().toString());

                                mResendButton.setVisibility(View.VISIBLE);
                                mVerificationField.setVisibility(View.VISIBLE);
                                mVerifyButton.setVisibility(View.VISIBLE);
                                mtext3.setVisibility(View.VISIBLE);
                                mtext4.setVisibility(View.VISIBLE);
                                mtext5.setVisibility(View.VISIBLE);
                                mtext1.setVisibility(View.INVISIBLE);
                                mtext2.setVisibility(View.INVISIBLE);
                                mcode.setVisibility(View.INVISIBLE);
                                mStartButton.setVisibility(View.INVISIBLE);
                                mPhoneNumberField.setVisibility(View.INVISIBLE);
                                FLAG=1;
                                break;


                                //Toast.makeText(getApplicationContext(), "enter registered number", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(LoginActivity.this, "enter registered number", Toast.LENGTH_SHORT).show();

                            }}
                        if(FLAG==0){
                            // System.out.println");
                            Toast.makeText(LoginActivity.this, "Enter Registered Mobile Number", Toast.LENGTH_SHORT).show();
                            // break;
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });
                break;

            case R.id.button_verify_phone:
                String code = mVerificationField.getText().toString();
                mVerifyButton.setTextColor(Color.BLUE);
                mResendButton.setTextColor(Color.GRAY);
                if (TextUtils.isEmpty(code)) {
                    mVerificationField.setError("Cannot be empty.");
                    return;
                }

                verifyPhoneNumberWithCode(mVerificationId, code);
                break;
            case R.id.button_resend:
                mResendButton.setTextColor(Color.BLUE);
                mVerifyButton.setTextColor(Color.GRAY);
                resendVerificationCode(mPhoneNumberField.getText().toString(), mResendToken);
                break;
            case R.id.sign_out_button:
                signOut();
                break;
        }
    }
}