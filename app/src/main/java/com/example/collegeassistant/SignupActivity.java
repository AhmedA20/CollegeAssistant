package com.example.collegeassistant;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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
    //firebase handling objects
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private OAuthProvider.Builder provider = OAuthProvider.newBuilder("microsoft.com");
    private EditText name, year, dep, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name       = findViewById(R.id.Name);
        year       = findViewById(R.id.Year);
        dep        = findViewById(R.id.dep);
        title    = findViewById(R.id.title);

        //instantiate FB objects
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        //check if there is current user

    }

    public void signUp(View view) {//triger the signIn flow
        if(valid()){
            checkResult();
        }
    }

    public void toIn(View view){
        startActivity(new Intent(SignupActivity.this, MainActivity.class));
    }

    public boolean valid(){
        boolean valid = true;
        String name            = this.name.getText().toString();
        String year            = this.year.getText().toString();
        String department      = dep.getText().toString();
        String subDepartment   = title.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            this.name.setError("at least 3 characters");
            valid = false;
        } else {
            this.name.setError(null);
        }

        if (year.isEmpty()) {
            this.year.setError("Enter Valid Address");
            valid = false;
        } else {
            this.year.setError(null);
        }

        if (department.isEmpty()) {
            dep.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            dep.setError(null);
        }

        if (subDepartment.isEmpty()) {
           title.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            title.setError(null);
        }

        return valid;
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
                                    startActivity(new Intent(SignupActivity.this, HomeActivity.class));
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
    }//end checker

    private void signIn() {//if there is no logged in user
        //adds custom parameters request
        customParam();
        permissions();

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
                                startActivity(new Intent(SignupActivity.this, HomeActivity.class));
                            }
                        })//if request succeeded
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle failure.
                            }
                        })
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {//User new?

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                        if (!isNewUser) {//user data invalidated
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Toast.makeText(getBaseContext(), "You are already sigedup login instead!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                        }
            }//end onComplete
        })/*endListener*/;
    }//end signing in

    private void customParam(){//todo add your custom parameters here
        // Force re-consent.
        provider.addCustomParameter("prompt", "consent");
        // Target specific email with login hint.
        provider.addCustomParameter("domain_hint", "user@science.helwan.edu.eg");
    }

    private void permissions(){//todo add permissions here
        List<String> scopes =
                new ArrayList<String>() {
                    {
                        add("mail.read");
                        add("calendars.read");
                    }
                };
        provider.setScopes(scopes);
    }
}
