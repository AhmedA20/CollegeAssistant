package com.example.collegeassistant.api.user_helper;

import android.support.annotation.NonNull;

import com.example.collegeassistant.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserHelper {
    private static final String COLLECTION_NAME = "Users";

    // --- COLLECTION REFERENCE ---
    public static CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance()
                .collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static void createNew(String uid, User user){
        StudentHelper.getUsersCollection().document(uid).set(user);
    }

    public static Task<Void> createUser(String uid, User user) {
        return StudentHelper.getUsersCollection().document(uid).set(user);
    }

    // --- GET ---
    public static Task<DocumentSnapshot> getUser(String uid){
        return StudentHelper.getUsersCollection().document(uid).get();
    }

    // --- UPDATE ---
    // --- Create more update methods to update more data related t the user
    public static Task<Void> updateUserName(String username, String uid) {
        return StudentHelper.getUsersCollection().document(uid).update("username", username);
    }


    // --- DELETE ---
    public Task<Void> deleteUser(String uid) {
        return StudentHelper.getUsersCollection().document(uid).delete();
    }

}
