package com.example.collegeassistant.api.UserHelper;


import com.example.collegeassistant.Models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfessorHelper {

    private static final String COLLECTION_NAME = "science_staff";
    private String DEPARTMENT_NAME ;
    private static final String SUB_COLLECTION_NAME = "professors_staff";

    private ProfessorHelper helper = new ProfessorHelper();

    // --- COLLECTION REFERENCE ---
    private CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance()
                .collection(COLLECTION_NAME)
                .document(DEPARTMENT_NAME)
                .collection(SUB_COLLECTION_NAME);
    }

    // --- CREATE ---
    public Task<Void> createProfessor(String uid, String username, String urlPicture, String assignedDepartment) {
        DEPARTMENT_NAME = assignedDepartment;
        User userToCreate = new User(uid, username, urlPicture, assignedDepartment);
        return helper.getUsersCollection().document(uid).set(userToCreate);
    }

    // --- GET ---
    public Task<DocumentSnapshot> getProfessor(String uid, String assignedDepartment){
        DEPARTMENT_NAME = assignedDepartment;
        return helper.getUsersCollection().document(uid).get();
    }

    // --- UPDATE ---
    // --- Create more update methods to update more data related t the user
    public Task<Void> updateProfessorName(String username, String uid, String assignedDepartment) {
        DEPARTMENT_NAME = assignedDepartment;
        return helper.getUsersCollection().document(uid).update("username", username);
    }


    public Task<Void> updateIsProfessor(String uid, Boolean IsProfessor, String assignedDepartment) {
        DEPARTMENT_NAME = assignedDepartment;
        return helper.getUsersCollection().document(uid).update("isMentor", IsProfessor);
    }

    // --- DELETE ---
    public  Task<Void> deleteUser(String uid, String assignedDepartment) {
        DEPARTMENT_NAME = assignedDepartment;
        return helper.getUsersCollection().document(uid).delete();
    }


}
