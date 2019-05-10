package com.example.collegeassistant.home;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.example.collegeassistant.R;
import com.example.collegeassistant.attendance.AttendanceActivity;
import com.example.collegeassistant.authentication.LoginActivity;
import com.example.collegeassistant.chat.ChatActivity;
import com.example.collegeassistant.grades.GradesActivity;
import com.example.collegeassistant.subject.SubjectActivity;
import com.example.collegeassistant.table.TableActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if(mUser!=null){//attention : NULL POINTER EXCEPTION MAYBE PRESENT
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
