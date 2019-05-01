package com.example.collegeassistant.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

//local package
import com.example.collegeassistant.AuthHelper.LogInHelper;
import com.example.collegeassistant.Home.HomeActivity;
import com.example.collegeassistant.R;

//Firbase libs
import com.example.collegeassistant.UserHelper.User;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.BuildConfig;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "LoginActivity";
    private String UID;


    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private LogInHelper logInHelper = new LogInHelper();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //------------------------------------------------------------
        //---------------Auth Flow Configurations---------------------
        //------------------------------------------------------------
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        AuthMethodPickerLayout customLayout = new AuthMethodPickerLayout
                .Builder(R.layout.activity_auth_ui)
                .setEmailButtonId(R.id.logbutton)
                .setGoogleButtonId(R.id.googleSignIn)
                .build();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        //---------------------------------------------------------
        //----------------Start Auth Flow--------------------------
        //---------------------------------------------------------
        if(mUser!=null){
            startActivity(new Intent(this, HomeActivity.class));
        }else{
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setIsSmartLockEnabled(!BuildConfig.DEBUG /* credentials */, true /* hints */)
                            .setAuthMethodPickerLayout(customLayout)
                            .build(),
                    RC_SIGN_IN);
        }//end if
    }//end onCreate

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                mAuth = FirebaseAuth.getInstance();
                mUser = mAuth.getCurrentUser();
                UID = mUser.getUid();
                status(UID);
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    setContentView(R.layout.activity_login);
                    return;
                }
                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {

                    return;
                }


                Log.e(TAG, "Sign-in error: ", response.getError());
            }
        }
    }

    //check if the user is recent or newly logged in
    public boolean isNewSignUp(){
        FirebaseUserMetadata metadata = mAuth.getCurrentUser().getMetadata();
        return metadata.getCreationTimestamp() == metadata.getLastSignInTimestamp();
    }

    //handle the result if the user is new
    private void status(String uid){
        if(isNewSignUp()){//creates new enTry to be checked later if the user is new
            logInHelper.createUser(UID,null,false,true);
        }else{
            registered(UID);
        }
    }

    private void registered(String uid){//if user is not registered

        if(logInHelper.getUser(uid).getIsNew()){
            startActivity(new Intent(this,Signup2Activity.class));
        }else{
            startActivity(new Intent(this, HomeActivity.class));
        }
    }

    public void toUp(View view){
        startActivity(new Intent(this, SignupActivity.class));
    }
}
