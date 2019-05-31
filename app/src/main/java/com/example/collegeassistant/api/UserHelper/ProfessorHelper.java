package com.example.collegeassistant.api.UserHelper;


import com.example.collegeassistant.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfessorHelper {

    private static final String COLLECTION_NAME = "science_staff";



    // --- COLLECTION REFERENCE ---
    private static CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance()
                .collection(COLLECTION_NAME);
    }

    // --- CREATE ---
    public static Task<Void> createProfessor(String uid, User user) {
        return ProfessorHelper.getUsersCollection().document(uid).set(user);
    }

    // --- GET ---
    public static Task<DocumentSnapshot> getProfessor(String uid){
        return ProfessorHelper.getUsersCollection().document(uid).get();
    }

    // --- UPDATE ---
    // --- Create more update methods to update more data related t the user
    public static Task<Void> updateProfessorName(String username, String uid) {
        return ProfessorHelper.getUsersCollection().document(uid).update("username", username);
    }


    public static Task<Void> updateIsProfessor(String uid, Boolean IsProfessor) {
        return ProfessorHelper.getUsersCollection().document(uid).update("isMentor", IsProfessor);
    }

    // --- DELETE ---
    public  static Task<Void> deleteUser(String uid) {
        return ProfessorHelper.getUsersCollection().document(uid).delete();
    }


}
