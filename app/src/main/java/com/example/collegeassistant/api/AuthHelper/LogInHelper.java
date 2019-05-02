package com.example.collegeassistant.api.AuthHelper;

import com.example.collegeassistant.Models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogInHelper {


    private static final String Collection = "new_users_journal";


    // --- COLLECTION REFERENCE ---
    public static CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance()
                .collection(Collection);
    }

    // --- create journal entry "User"---"rolled to student later"
    public static Task<Void> createUser(String uid, String year,String department,boolean isProfessor,boolean isNew) {
        User abstractSTD = new User(uid,year,department,isProfessor,isNew);
        return LogInHelper.getUsersCollection().document(uid).set(abstractSTD);
    }
    // --- create journal entry "User"---"rolled to professor later"
    public static Task<Void> createUser(String uid, String department,boolean isProfessor,boolean isNew) {
        User abstractSTD = new User(uid,department,isProfessor,isNew);
        return LogInHelper.getUsersCollection().document(uid).set(abstractSTD);
    }

    // --- GET ---
    public static Task<DocumentSnapshot> getUserFromDB(String uid){
        return LogInHelper.getUsersCollection().document(uid).get();
    }
    public static User getUser(String uid){
        DocumentSnapshot snapshot = getUserFromDB(uid).getResult();
        return snapshot.toObject(User.class);
    }

    // --- DELETE ---
    public static void deleteUser(String uid) {
        LogInHelper.getUsersCollection().document(uid).delete();
    }
}
