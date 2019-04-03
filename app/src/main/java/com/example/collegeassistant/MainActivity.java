package com.example.collegeassistant;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;

public class MainActivity extends AppCompatActivity {
    //firebase handling objects
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private OAuthProvider.Builder provider = OAuthProvider.newBuilder("microsoft.com");
    ;
    RelativeLayout relative1, relative2;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            relative1.setVisibility(View.VISIBLE);
            relative2.setVisibility(View.VISIBLE);
        }
    };//switch between the splash and main activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //todo if encountered @nullPointerException enable this
        //setContentView(R.layout.activity_main);

        relative1 = findViewById(R.id.relative1);
        relative2 = findViewById(R.id.relative2);
        long delayMillis = 2000;
        handler.postDelayed(runnable, delayMillis); // 2000 is time out for splash
        //instantiate FB objects
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        //check if there is current user
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mUser != null) {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                } else {
                    setContentView(R.layout.activity_main);
                }
            }
        };
    }

    public void logIn(View view) {//triger the signIn flow
        checkResult();
    }


    public void toUp(View view) {//intent for signing up
        startActivity(new Intent(MainActivity.this, SignupActivity.class));
    }

    //todo implement all signIn related code below
    private void checkResult() {
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
                                    // authResult.getAdditionalUserInfo().getProfile().
                                    // The OAuth access token can also be retrieved:
                                    // authResult.getCredential().getAccessToken().
                                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure.
                                }
                            });
        } else {
            // There's no pending result so you need to start the sign-in flow.
            // See below.
            signIn();
        }
    }

    private void signIn() {//if there is no logged in user
        //adds custom parameters request
        customParam();
        mAuth.startActivityForSignInWithProvider(/* activity= */ this, provider.build())
                .addOnSuccessListener(
                        new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                // User is signed in.
                                // IdP data available in
                                // authResult.getAdditionalUserInfo().getProfile().
                                // The OAuth access token can also be retrieved:
                                // authResult.getCredential().getAccessToken().
                                //checks if the user is logged in or not
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            }
                        })//if request succeeded
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle failure.
                            }
                        })//if request failed
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {//User new?
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                        if (isNewUser) {//user data invalidated
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                //do nothing
                                                Toast.makeText(getBaseContext(), "Signup to be able to login!", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(MainActivity.this, SignupActivity.class));
                                            }
                                        }
                                    });
                        } //end if
                    }//end onComplete
                }/*endListener*/);//end signIn
    }//end signing in

    private void customParam() {
        // Target specific email with login hint.
        provider.addCustomParameter("domain_hint", "user@science.helwan.edu.eg");
    }


}
