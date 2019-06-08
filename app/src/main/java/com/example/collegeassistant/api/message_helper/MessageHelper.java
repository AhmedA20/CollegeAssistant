package com.example.collegeassistant.api.message_helper;

import com.example.collegeassistant.models.Message;
import com.example.collegeassistant.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

public class MessageHelper {//-----organizes queries-------

    private static final String COLLECTION_NAME = "messages";

    // --- GET ---
    public static Query getAllMessageForChat(String chat){
        return ChatHelper.getChatCollection()
                .document(chat)
                .collection(COLLECTION_NAME)
                .orderBy("dateCreated")
                .limit(50);
    }

   //---Save to cloud---
    public static Task<DocumentReference> createMessageForChat(String textMessage, String chat, User userSender){

        // 1 - Create the Message object
        Message message = new Message(textMessage, userSender);

        // 2 - Store Message to Firestore
        return ChatHelper.getChatCollection()
                .document(chat)
                .collection(COLLECTION_NAME)
                .add(message);
    }
}

