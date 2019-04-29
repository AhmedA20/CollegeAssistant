package com.example.collegeassistant.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.collegeassistant.Attendance.AttendanceActivity;
import com.example.collegeassistant.Auth.LoginActivity;
import com.example.collegeassistant.Chat.ChatActivity;
import com.example.collegeassistant.Grades.GradesActivity;
import com.example.collegeassistant.R;
import com.example.collegeassistant.Subject.SubjectActivity;
import com.example.collegeassistant.Table.TableActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if(mUser!=null){//attention : NULL POINTER EXCEPTION MAYBE PRESENT
            setContentView(R.layout.activity_home);
        }else{
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.subject:
                startActivity(new Intent(this, SubjectActivity.class));
                break;
            case R.id.table:
                startActivity(new Intent(this, TableActivity.class));
                break;
            case R.id.grade:
                startActivity(new Intent(this, GradesActivity.class));
                break;
            case R.id.chat:
                startActivity(new Intent(this, ChatActivity.class));
                break;
            case R.id.att:
                startActivity(new Intent(this, AttendanceActivity.class));
                break;
        }

    }
}
