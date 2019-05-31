package com.example.collegeassistant.api.UserHelper;



import com.example.collegeassistant.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentHelper {

    private static final String COLLECTION_NAME = "students";

    // --- COLLECTION REFERENCE ---
    public static CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance()
                .collection(COLLECTION_NAME);
        }

    // --- CREATE ---
    public static Task<Void> createStudent(String uid, User user) {
        return StudentHelper.getUsersCollection().document(uid).set(user);
    }

    // --- GET ---
    public static Task<DocumentSnapshot> getStudent(String uid){
        return StudentHelper.getUsersCollection().document(uid).get();
    }

    // --- UPDATE ---
    // --- Create more update methods to update more data related t the user
    public static Task<Void> updateStudentName(String username, String uid) {
        return StudentHelper.getUsersCollection().document(uid).update("username", username);
    }


    // --- DELETE ---
    public Task<Void> deleteUser(String uid) {
        return StudentHelper.getUsersCollection().document(uid).delete();
    }

}
