package com.example.collegeassistant.api.AuthHelper;


import com.example.collegeassistant.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogInHelper {
    private static final String TAG = "LogInHelper";
    private static final String Collection = "new_users_journal";

    // --- COLLECTION REFERENCE ---
    public static CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance()
                .collection(Collection);
    }

    // --- create journal entry "User"---"rolled to student later"
    public static Task<Void> createUser(String uid, String year,String department,boolean isProfessor) {
        User abstractSTD = new User(uid,year,department,isProfessor);
        return LogInHelper.getUsersCollection().document(uid).set(abstractSTD);
    }
    // --- create journal entry "User"---"rolled to professor later"
    public static Task<Void> createUser(String uid, String department,boolean isProfessor) {
        User abstractSTD = new User(uid,department,isProfessor);
        return LogInHelper.getUsersCollection().document(uid).set(abstractSTD);
    }

    // --- GET ---
   public static Task<DocumentSnapshot> getUserFromDB(String uid){
        return LogInHelper.getUsersCollection().document(uid).get();
    }


    // ---UPDATE---
    public static Task<Void> updateIsNew(String uid,boolean isNew){
        return LogInHelper.getUsersCollection().document(uid).update("isNew",isNew);
    }

    // --- DELETE ---
    public static Task<Void> deleteUser(String uid) {
        return LogInHelper.getUsersCollection().document(uid).delete();
    }
}
