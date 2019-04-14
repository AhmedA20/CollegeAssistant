package com.example.collegeassistant;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    OAuthProvider.Builder provider = OAuthProvider.newBuilder("microsoft.com");

    Button submit;
    EditText nameView, yearView, depView, titleView;
    EditText mail, pass, rePass;

    static final String TAG = "SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        submit    = findViewById(R.id.submit);
        nameView  = findViewById(R.id.Name);
        mail      = findViewById(R.id.e);
        pass      = findViewById(R.id.pass);
        rePass    =  findViewById(R.id.repass);
        yearView  = findViewById(R.id.Year);
        depView   = findViewById(R.id.dep);
        titleView = findViewById(R.id.title);
        permitted();

        mAuth = FirebaseAuth.getInstance();
        checkForRespond();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valid()){
                    //checkForRespond();
                    //todo: cloud signing is disabled
                    Toast.makeText(SignupActivity.this,"Cloud signing disabled switch to logIn",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void toIn(View view){
        startActivity(new Intent(SignupActivity.this, MainActivity.class));
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



    //if there is a pending request the calls will be here
    private void checkForRespond() {
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
                                        Log.w(TAG,"user is new!user is new!<------------------------------------------");
                                        Log.d(TAG,"user is loggedIn!user is new!<------------------------------------------");
                                        authResult.getAdditionalUserInfo().getProfile();
                                        // The OAuth access token can also be retrieved:
                                        //authResult.getCredential().getAccessToken();
                                        mAuth.getAccessToken(true);
                                        startActivity(new Intent(SignupActivity.this, HomeActivity.class));
                                        finish();
                                    }else {
                                        FirebaseAuth.getInstance().signOut();
                                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    }
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure.
                                    Toast.makeText(SignupActivity.this,"LoginFailed!",Toast.LENGTH_LONG).show();
                                    Log.d(TAG,"user request failed!------------------------------------------");
                                }
                            });
        } else {
            // There's no pending result so you need to start the sign-in flow.
            // See below.
            signIn();

        }
    }//ending pending result

    private void signIn() {
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
                                    Log.e(TAG,"user is new!<------------------------------------------");
                                    Log.e(TAG,"user is loggedIn!<-------------------------------------");
                                    authResult.getAdditionalUserInfo().getProfile();
                                    mAuth.getAccessToken(true);
                                    // The OAuth access token can also be retrieved:
                                    //authResult.getCredential().getAccessToken();
                                    startActivity(new Intent(SignupActivity.this, HomeActivity.class));
                                    finish();
                                }else {
                                    FirebaseAuth.getInstance().signOut();
                                    Toast.makeText(SignupActivity.this,"This account already exists, LogIn instead!",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                }

                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle failure.
                                Log.d(TAG,"user request failed!<------------------------------------------");

                            }
                        });
    }//ending signIn

    //validate input fields
    private boolean valid(){
        boolean valid =true;

        String name = nameView.getText().toString();
        String mail = this.mail.getText().toString();
        String pass = this.pass.getText().toString();
        String repass = rePass.getText().toString();
        String year = yearView.getText().toString();
        String dep = depView.getText().toString();
        String title = titleView.getText().toString();


        if (name.isEmpty() || name.length() < 3) {
            nameView.setError("at least 3 characters");
            valid = false;
        } else {
            nameView.setError(null);
        }

        if (mail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            this.mail.setError("enter a valid email address");
            valid = false;
        } else {
            this.mail.setError(null);
        }


        if (pass.isEmpty() || pass.length() < 4 || pass.length() > 10) {
            this.pass.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            this.pass.setError(null);
        }

        if (repass.isEmpty() || repass.length() < 4 || repass.length() > 10 || !(repass.equals(pass))) {
            rePass.setError("Password Do not match");
            valid = false;
        } else {
            rePass.setError(null);
        }

        if (year.isEmpty()) {
            yearView.setError("Enter Enrollment Year");
            valid = false;
        } else {
            yearView.setError(null);
        }


        if (dep.isEmpty()) {
            depView.setError("enter a valid department name");
            valid = false;
        } else {
            depView.setError(null);
        }

        if (title.isEmpty()) {
            titleView.setError("Enter Sub Department name");
            valid = false;
        } else {
            titleView.setError(null);
        }


        return valid;
    }
}
