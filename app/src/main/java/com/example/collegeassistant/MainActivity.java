package com.example.collegeassistant;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    RelativeLayout relative1, relative2 ;

    Handler handler = new Handler();
    Runnable runnable= new Runnable() {
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

        relative1=(RelativeLayout) findViewById(R.id.relative1);
        relative2=(RelativeLayout) findViewById(R.id.relative2);
        long delayMillis =2000 ;
        handler.postDelayed(runnable, delayMillis); // 2000 is time  out for spleash
    }


    public void toUp(View view){
        startActivity(new Intent(MainActivity.this, SignupActivity.class));
    }
}
