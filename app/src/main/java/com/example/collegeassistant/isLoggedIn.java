package com.example.collegeassistant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IsLoggedIn extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    RelativeLayout rSplash ;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rSplash.setVisibility(View.VISIBLE);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Make sure this is before calling super.onCreate
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        rSplash = findViewById(R.id.rSplash);
        long delayMillis = 2000;
        handler.postDelayed(runnable, delayMillis); // 2000 is time  out for splash

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if(mUser!=null){
            //user is present
            startActivity(new Intent(IsLoggedIn.this,HomeActivity.class));
            finish();
        }else{
            //user isn't loggedIn or token is invalid
            startActivity(new Intent(IsLoggedIn.this,MainActivity.class));
            finish();
        }

    }
}
