package com.example.collegeassistant.AuthHelper;

import com.example.collegeassistant.UserHelper.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogInHelper {
    LogInHelper checker = new LogInHelper();

    private final String Collection = "new_users_journal";


    // --- COLLECTION REFERENCE ---
    public CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance()
                .collection(Collection);
    }

    // --- create journal entry "User"---"rolled to student later"
    public Task<Void> createUser(String uid, String year,String department,boolean isProfessor,boolean isNew) {
        User abstractSTD = new User(uid,year,department,isProfessor,isNew);
        return checker.getUsersCollection().document(uid).set(abstractSTD);
    }
    // --- create journal entry "User"---"rolled to professor later"
    public Task<Void> createUser(String uid, String department,boolean isProfessor,boolean isNew) {
        User abstractSTD = new User(uid,department,isProfessor,isNew);
        return checker.getUsersCollection().document(uid).set(abstractSTD);
    }

    // --- GET ---
    public Task<DocumentSnapshot> getUserFromDB(String uid){
        return checker.getUsersCollection().document(uid).get();
    }
    public User getUser(String uid){
        DocumentSnapshot snapshot = getUserFromDB(uid).getResult();
        return snapshot.toObject(User.class);
    }

    // --- DELETE ---
    public void deleteUser(String uid) {
        checker.getUsersCollection().document(uid).delete();
    }
}
