package com.example.collegeassistant.Auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.collegeassistant.MainActivity;
import com.example.collegeassistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
public class Signup2Activity extends AppCompatActivity {
    @BindView(R.id.submit_google) Button submit;
    @BindView(R.id.Name_google) EditText nameView;
    @BindView(R.id.department_google) EditText department;
    @BindView(R.id.Year_google) EditText year;
    @BindView(R.id.title_google) EditText title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_2);
        ButterKnife.bind(this);
    }

    public void toIn(View view){
        startActivity(new Intent(Signup2Activity.this, MainActivity.class));
    }
}
