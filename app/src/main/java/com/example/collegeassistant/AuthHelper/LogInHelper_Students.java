package com.example.collegeassistant.AuthHelper;

import com.example.collegeassistant.UserHelper.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogInHelper_Students {
    private final String Collection_S = "students_journal";

    LogInHelper_Students helper = new LogInHelper_Students();

    // --- COLLECTION REFERENCE ---
    public CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance()
                .collection(Collection_S);
    }

    // --- create journal entry "Student"---
    public Task<Void> createStudent(String uid, String year, String department, boolean isNew) {
        User abstractSTD = new User(uid, year, department, isNew);
        return helper.getUsersCollection().document(uid).set(abstractSTD);
    }

    // --- update journal entry "Student"---
    public Task<Void> updateStudent(String uid, String year, String department, boolean isNew) {
        User abstractSTD = new User(uid, year, department, isNew);
        helper.deleteUser(uid);
        return helper.getUsersCollection().document(uid).set(abstractSTD);
    }

    // --- DELETE ---
    public void deleteUser(String uid) {
        helper.getUsersCollection().document(uid).delete();
    }
}
