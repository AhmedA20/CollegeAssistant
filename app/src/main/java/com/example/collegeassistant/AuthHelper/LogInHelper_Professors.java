package com.example.collegeassistant.AuthHelper;

import com.example.collegeassistant.UserHelper.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogInHelper_Professors {

    LogInHelper_Professors helper = new LogInHelper_Professors();


    private final String Collection_P = "professor_journal";


    // --- COLLECTION REFERENCE ---
    public CollectionReference getUsersCollection(){
            return FirebaseFirestore.getInstance()
                    .collection(Collection_P);
    }



    // --- create journal entry "Professor"---
    public Task<Void> createProfessor(String uid,  String department, boolean isNew) {
        User abstractPFS = new User(uid, department, isNew);
        return helper.getUsersCollection().document(uid).set(abstractPFS);
    }

    // --- update journal entry "Student"---
    public Task<Void> updateStudent(String uid, String department, boolean isNew) {
        User abstractPFS = new User(uid, department, isNew);
        helper.deleteUser(uid);
        return helper.getUsersCollection().document(uid).set(abstractPFS);
    }

    // --- DELETE ---
    public void deleteUser(String uid) {
        helper.getUsersCollection().document(uid).delete();
    }


}
