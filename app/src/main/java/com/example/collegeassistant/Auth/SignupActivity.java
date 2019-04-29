package com.example.collegeassistant.Auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.collegeassistant.MainActivity;
import com.example.collegeassistant.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.submit) Button submit;
    @BindView(R.id.Name) EditText nameView;
    @BindView(R.id.e) EditText mail;
    @BindView(R.id.pass) EditText pass;
    @BindView(R.id.repass) EditText rePass;
    @BindView(R.id.Year) EditText yearView;
    @BindView(R.id.dep) EditText depView;
    @BindView(R.id.title) EditText titleView;

    String name ;
    String e_mail ;
    String e_pass ;
    String repass ;
    String year ;
    String dep ;
    String title ;


    FirebaseAuth mAuth;
    FirebaseUser mUser;

    static final String TAG = "SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);


        mAuth = FirebaseAuth.getInstance();



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valid()){//todo: cloud signing is disabled
                    Toast.makeText(SignupActivity.this,"Cloud signing disabled switch to logIn",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void toIn(View view){
        startActivity(new Intent(SignupActivity.this, MainActivity.class));
    }


    //validate input fields
    private boolean valid(){
        boolean valid =true;

        name     = nameView.getText().toString();
        e_mail   = mail.getText().toString();
        e_pass   = pass.getText().toString();
        repass = rePass.getText().toString();
        year   = yearView.getText().toString();
        dep    = depView.getText().toString();
        title  = titleView.getText().toString();


        if (name.isEmpty() || name.length() < 3) {
            nameView.setError("at least 3 characters");
            valid = false;
        } else {
            nameView.setError(null);
        }

        if (e_mail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(e_mail).matches()) {
            this.mail.setError("enter a valid email address");
            valid = false;
        } else {
            this.mail.setError(null);
        }


        if (e_pass.isEmpty() || pass.length() < 4 || pass.length() > 10) {
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
