package com.example.collegeassistant.UserHelper;



import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentHelper {

    private static final String COLLECTION_NAME = "students";


    private StudentHelper helper = new StudentHelper();


    // --- COLLECTION REFERENCE ---
    public CollectionReference getUsersCollection(String year, String department){
        return FirebaseFirestore.getInstance()
                .collection(COLLECTION_NAME)
                .document(year)
                .collection(department);
        }

    // --- CREATE ---
    public Task<Void> createStudent(String uid, String username, String urlPicture, String year, String department) {
        User userToCreate = new User(uid, username, urlPicture, year, department);
        return helper.getUsersCollection(year, department).document(uid).set(userToCreate);
    }

    // --- GET ---
    public Task<DocumentSnapshot> getStudent(String uid,String year, String department){
        return helper.getUsersCollection(year, department).document(uid).get();
    }

    // --- UPDATE ---
    public Task<Void> updateStudentName(String username, String uid,String year, String department) {
        return helper.getUsersCollection(year, department).document(uid).update("username", username);
    }


    // --- DELETE ---
    public Task<Void> deleteUser(String uid, String year, String department) {
        return helper.getUsersCollection(year, department).document(uid).delete();
    }

}
