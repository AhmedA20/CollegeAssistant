package com.example.collegeassistant.MessageHelper;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChatHelper {//---------fetches collections from firestore---------


    private static final String COLLECTION_NAME = "Chat";

        // --- COLLECTION REFERENCE ---
     public static CollectionReference getChatCollection(){
         return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
     }
}
