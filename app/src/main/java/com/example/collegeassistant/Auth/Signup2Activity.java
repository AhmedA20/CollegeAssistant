package com.example.collegeassistant.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;

import com.example.collegeassistant.AuthHelper.LogInHelper;
import com.example.collegeassistant.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Signup2Activity extends AppCompatActivity {
    //student data container
    @BindView(R.id.scroll) ScrollView studentDataContainer;
    @BindView(R.id.form2_name) EditText stdName;
    @BindView(R.id.form2_year) EditText stdEnrolmentYear;
    @BindView(R.id.form2_department) EditText stdEnrolmentDepartment;

    //professors data container
    @BindView(R.id.form2_linear_container) LinearLayout professorDataContainer;
    @BindView(R.id.form2_name_professor) EditText pfsName;
    @BindView(R.id.form2_department_professor) EditText pfsEnrolmentDepartment;

    //button submission
    @BindView(R.id.submit_form) Button submit;

    //enables the respective container
    boolean isProfessor;

    //firbase related object
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    //local package fields
    LogInHelper newUser = new LogInHelper();
    //data entry fields
    String name ;
    String year ;
    String dep ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_2);
        ButterKnife.bind(this);
        submit.setEnabled(false);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if(mUser!=null){//creates the basic entry path to the user root document
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(validPFS()&&isProfessor){//professor data entry
                        newUser.deleteUser(mUser.getUid());
                        newUser.createUser(mUser.getUid(),dep,isProfessor,false);
                    }else if(validSTD()&&!isProfessor){//student data entry
                        newUser.deleteUser(mUser.getUid());
                        newUser.createUser(mUser.getUid(),year,dep,isProfessor,false);
                    }
                }
            });
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_professor:
                if (checked){
                    //enables professor container and disables student
                    professorDataContainer.setVisibility(View.VISIBLE);
                    studentDataContainer.setVisibility(View.INVISIBLE);
                    isProfessor = true;
                    submit.setEnabled(true);
                }
                    break;
            case R.id.radio_student:
                if (checked){
                    //disables professor container and enables student
                    professorDataContainer.setVisibility(View.INVISIBLE);
                    studentDataContainer.setVisibility(View.VISIBLE);
                    isProfessor = false;
                    submit.setEnabled(true);
                }
                    break;
        }//end switch
    }//end onRadioButtonClicked

    public void toIn(View view){
        startActivity(new Intent(Signup2Activity.this, LoginActivity.class));
    }

    //validate input fields
    private boolean validSTD(){
        boolean valid =true;

        name     = stdName.getText().toString();
        year   = stdEnrolmentYear.getText().toString();
        dep    = stdEnrolmentDepartment.getText().toString();


        if (name.isEmpty() || name.length() < 3) {
            stdName.setError("at least 3 characters");
            valid = false;
        } else {
            stdName.setError(null);
        }



        if (year.isEmpty()) {
            stdEnrolmentYear.setError("Enter Enrollment Year");
            valid = false;
        } else {
            stdEnrolmentYear.setError(null);
        }

        if (dep.isEmpty()) {
            stdEnrolmentDepartment.setError("enter a valid department name");
            valid = false;
        } else {
            stdEnrolmentDepartment.setError(null);
        }

        return valid;
    }//input for student data validated

    //validate input fields
    private boolean validPFS(){
        boolean valid =true;

        name   = pfsName.getText().toString();
        dep    = pfsEnrolmentDepartment.getText().toString();


        if (name.isEmpty() || name.length() < 3) {
            pfsName.setError("at least 3 characters");
            valid = false;
        } else {
            pfsName.setError(null);
        }

        if (dep.isEmpty()) {
            pfsEnrolmentDepartment.setError("enter a valid department name");
            valid = false;
        } else {
            pfsEnrolmentDepartment.setError(null);
        }

        return valid;
    }//input for student data validated


}
