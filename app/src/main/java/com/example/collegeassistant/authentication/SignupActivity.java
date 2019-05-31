package com.example.collegeassistant.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.collegeassistant.home.HomeActivity;
import com.example.collegeassistant.R;
import com.example.collegeassistant.api.UserHelper.ProfessorHelper;
import com.example.collegeassistant.api.UserHelper.StudentHelper;

import com.example.collegeassistant.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    //TODO:VIEWS RELATED THE ORIGINAL SIGNUP FORM ENABLE IF NEEDED
    @BindView(R.id.submit) Button submit;
    @BindView(R.id.Name) EditText nameView;
    @BindView(R.id.e) EditText mail;
    @BindView(R.id.pass) EditText pass;
    @BindView(R.id.repass) EditText rePass;
    @BindView(R.id.Year) EditText yearView;
    @BindView(R.id.department) EditText depView;

    ProgressDialog progressDialog;

    //firbase instances
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    //data entry vars
    String name ;
    String e_mail ;
    String e_pass ;
    String repass ;
    String year ;
    String dep ;
    boolean isProfessor;
    String UID;
    //constants
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
    }//end onCreate

    public void signup(View view){//TODO:assign to the onClick in xml for submit button
        if(valid()){
            //---------------ProgressBar-----------------------------
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating Account...");
            progressDialog.show();
            //-----------------Authentication-----------------------------------
            mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(e_mail, e_pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG, "createUserWithEmail:success");
                                mUser = mAuth.getCurrentUser();
                                UID = mUser.getUid();
                                if(isProfessor){
                                    createProfessor();
                                }else{
                                    createStudent();
                                }
                                Log.d(TAG, "createUserWithEmail:success--->Data");
                                progressDialog.dismiss();
                                startActivity(new Intent(SignupActivity.this,HomeActivity.class));
                                SignupActivity.this.finish();
                            }
                        }
                    })
                    .addOnFailureListener(this,new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            handleFail(e);
                        }
                    });
        }
    }

    public void toIn(View view){
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        finish();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_professor:
                if (checked){
                    //enables professor container and disables student
                    yearView.setVisibility(View.INVISIBLE);
                    isProfessor = true;
                }
                break;
            case R.id.radio_student:
                if (checked){
                    yearView.setVisibility(View.VISIBLE);
                    isProfessor = false;
                }
                break;
        }//end switch
    }//end onRadioButtonClicked

    //validate input fields
    private boolean valid(){
        boolean valid =true;

        name     = nameView.getText().toString();
        e_mail   = mail.getText().toString();
        e_pass   = pass.getText().toString();
        repass = rePass.getText().toString();
        year   = yearView.getText().toString();
        dep    = depView.getText().toString();


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

        if (e_pass.isEmpty() || pass.length() < 4) {
            this.pass.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            this.pass.setError(null);
        }

        if (repass.isEmpty() || repass.length() < 4 || !(repass.equals(e_pass))) {
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

        return valid;
    }

    //todo:memory leak found further testing will show why
    private void createProfessor(){
        User user = new User(UID,name,null,dep);
        ProfessorHelper.createProfessor(UID,user);

    }//end professor

    private void createStudent(){
        User user = new User(UID,name,null,year,dep);
        StudentHelper.createStudent(UID,user);
    }//end Student


    //handle logging failure
    private void handleFail(@NonNull Exception e){
        // If sign in fails, display a message to the user.
        progressDialog.dismiss();
        Toast.makeText(SignupActivity.this, e.getLocalizedMessage(),
                Toast.LENGTH_LONG).show();
        if (e instanceof FirebaseAuthInvalidCredentialsException) {
            Toast.makeText(SignupActivity.this, "Invalid password",
                    Toast.LENGTH_LONG).show();
        } else if (e instanceof FirebaseAuthInvalidUserException) {

            String errorCode =
                    ((FirebaseAuthInvalidUserException) e).getErrorCode();

            if (errorCode.equals("ERROR_EMAIL_ALREADY_IN_USE")) {
                Toast.makeText(SignupActivity.this, "E-MAIL ALREADY IN USE!",
                        Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(SignupActivity.this, e.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }//end handler
}

