package com.example.collegeassistant;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override//initiated AuthState Listener not attached yet
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //listener attached at onResume detached at onPause
                mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {
                    // User is signed in

                } else {
                    //noUser present

                }
            }
        };
    }

    @Override
    protected void onResume() {//attach the authentication listener
        super.onResume();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {//detach the authentication listener
        super.onPause();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.subject:
                startActivity(new Intent(this,SubjectActivity.class));
                break;
            case R.id.table:
                startActivity(new Intent(this,TableActivity.class));
                break;
            case R.id.grade:
                startActivity(new Intent(this,GradesActivity.class));
                break;
            case R.id.chat:
                startActivity(new Intent(this,ChatActivity.class));
                break;
            case R.id.att:
                startActivity(new Intent(this,AttendanceActivity.class));
                break;
        }
    }
}
