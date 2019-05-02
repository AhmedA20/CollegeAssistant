package com.example.collegeassistant.MessageHelper;

import com.example.collegeassistant.MessageHelper.ChatHelper;
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
}
