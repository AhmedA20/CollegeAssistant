package com.example.collegeassistant.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.collegeassistant.R;
import com.example.collegeassistant.announcement.AnnouncementActivity;
import com.example.collegeassistant.api.user_helper.UserHelper;
import com.example.collegeassistant.attendance.AttendanceActivity;
import com.example.collegeassistant.authentication.LoginActivity;
import com.example.collegeassistant.chat.ChatActivity;
import com.example.collegeassistant.grades.GradesActivity;
import com.example.collegeassistant.models.User;
import com.example.collegeassistant.subject.SubjectActivity;
import com.example.collegeassistant.table.TableActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    Intent intent;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        getUser();

        if(mUser!=null){
        }else{
            startActivity(new Intent(this, LoginActivity.class));
        }

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.subject:
                intent = new Intent(this, SubjectActivity.class);
                intent.putExtra("User", user);
                startActivity(intent);
                break;
            case R.id.table:
                intent = new Intent(this, TableActivity.class);
                intent.putExtra("User", user);
                startActivity(intent);
                break;
            case R.id.grade:
                intent = new Intent(this, GradesActivity.class);
                intent.putExtra("User", user);
                startActivity(intent);
                break;
            case R.id.chat:
                intent = new Intent(this, ChatActivity.class);
                intent.putExtra("User", user);
                startActivity(intent);
                break;
            case R.id.att:
                intent  = new Intent(this, AttendanceActivity.class);
                intent.putExtra("User", user);
                startActivity(intent);
                break;
            case R.id.announcement:
                intent = new Intent(this, AnnouncementActivity.class);
                intent.putExtra("User", user);
                startActivity(intent);
                break;
            default:
                break;

        }

    }

    private void getUser(){
        user = (User) getIntent().getSerializableExtra("User");

        if(user == null){
            final ProgressDialog progressDialog =new ProgressDialog(HomeActivity.this, R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();


            UserHelper.getUser(mUser.getUid()).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        user = task.getResult().toObject(User.class);
                        progressDialog.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(HomeActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        //your code when back button pressed

    }

}
