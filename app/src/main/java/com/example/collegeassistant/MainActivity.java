package com.example.collegeassistant;

import android.content.Intent;
import android.os.Handler;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.auth.OAuthProvider;
import  com.google.firebase.auth.AuthCredential;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RelativeLayout relative1, relative2;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    OAuthProvider.Builder provider = OAuthProvider.newBuilder("microsoft.com");

    Button log;
    static final String TAG = "LogInActivity";

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            relative1.setVisibility(View.VISIBLE);
            relative2.setVisibility(View.VISIBLE);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relative1 = findViewById(R.id.relative1);
        relative2 = findViewById(R.id.relative2);
        log       = findViewById(R.id.logbutton);
        long delayMillis = 2000;
        handler.postDelayed(runnable, delayMillis); // 2000 is time  out for splash

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForResult();
            }
        });

    }//ending onCreate

    public void logIn(){//signIn
        mAuth
                .startActivityForSignInWithProvider(/* activity= */ this, provider.build())
                .addOnSuccessListener(
                        new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                // User is signed in.
                                // IdP data available in
                                // authResult.getAdditionalUserInfo().getProfile().
                                // The OAuth access token can also be retrieved:
                                // authResult.getCredential().getAccessToken().
                                //is the user new or not
                                if(authResult.getAdditionalUserInfo().isNewUser()){
                                    Log.w(TAG,"user is new!");
                                    Log.d(TAG,"user is loggedIn!");
                                    //authResult.getAdditionalUserInfo().getProfile();
                                    try{
                                        mAuth.getCurrentUser().delete();
                                        startActivity(new Intent(MainActivity.this, SignupActivity.class));
                                    }//delete profile and prompt user to signUp first
                                    catch(NullPointerException e){
                                        Log.w(TAG,"deleting unsuccessful");
                                        setContentView(R.layout.activity_main);
                                    }
                                    // The OAuth access token can also be retrieved:
                                     //authResult.getCredential().getAccessToken();
                                    finish();
                                }else {
                                    //FirebaseAuth.getInstance().signOut();
                                    Toast.makeText(MainActivity.this,"This account already exists, LogIn instead!",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                    finish();
                                }

                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle failure.
                                Log.d(TAG,"user request failed!");
                            }
                        });
    }

    public void checkForResult(){//ResultListener
        permitted();
        Task<AuthResult> pendingResultTask = mAuth.getPendingAuthResult();
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    // User is signed in.
                                    // IdP data available in
                                    if(authResult.getAdditionalUserInfo().isNewUser()){
                                        Log.w(TAG,"user is new!");
                                        Log.d(TAG,"user is loggedIn!");
                                        //authResult.getAdditionalUserInfo().getProfile();
                                        try{
                                            mAuth.getCurrentUser().delete();
                                            startActivity(new Intent(MainActivity.this, SignupActivity.class));
                                        }//delete profile and prompt user to signUp first
                                       catch(NullPointerException e){
                                            Log.w(TAG,"deleting unsuccessful");
                                            setContentView(R.layout.activity_main);
                                        }
                                        // The OAuth access token can also be retrieved:
                                        mAuth.getAccessToken(true);
                                        //authResult.getCredential().getAccessToken();

                                        finish();
                                    }else {
                                        //FirebaseAuth.getInstance().signOut();
                                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                        finish();
                                    }
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure.
                                    Toast.makeText(MainActivity.this,"LoginFailed!",Toast.LENGTH_LONG).show();
                                }
                            });
        } else {
            // There's no pending result so you need to start the sign-in flow.
            // See below.
            logIn();
        }
    }

    //login request permissions
    private void permitted() {
        // Force re-consent.
        provider.addCustomParameter("prompt", "consent");
        // Target specific email with login hint.
        provider.addCustomParameter("domain_hint", "user@science.helwan.edu.eg");
        //permissions
        List<String> scopes =
                new ArrayList<String>() {
                    {
                        add("calendars.read");
                    }
                };
        provider.setScopes(scopes);
    }//ending permissions

    public void toUp(View view) {
        startActivity(new Intent(MainActivity.this, SignupActivity.class));
    }

}
