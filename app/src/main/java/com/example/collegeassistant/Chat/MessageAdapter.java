package com.example.collegeassistant.Chat;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.example.collegeassistant.Models.Message;
import com.example.collegeassistant.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class MessageAdapter extends FirestoreRecyclerAdapter<Message, MessageHolder> {

    //todo:------------------WARNING-------------------
    /*
    1-UNKNOWN METHOD TO RETRIEVE MESSAGE DATA SAFELY
    2-method chain (onBindViewHolder)--->(viewType)--->(onCreateViewHolder) ELSE you are screwed
     */
    //todo:------------------WARNING-------------------


    //FOR DATA
    private final RequestManager glide;
    private String uid;
    private boolean isCurrentUser;

    private static final int SENT = 0;
    private static final int RECEIVED = 1;


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     * @param glide
     */
    public MessageAdapter(@NonNull FirestoreRecyclerOptions<Message> options, RequestManager glide, String uid) {
        super(options);
        this.glide = glide;
        this.uid=uid;
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageHolder holder, int position, @NonNull Message model) {
       holder.bind(model, uid);
    }




    //@param i == ViewType
    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view;
        if (i==SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new MessageHolder(view, glide);
        } else{
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new MessageHolder(view, glide);
        }
    }


    @Override
    public void onError(FirebaseFirestoreException e) {
        // Called when there is an error getting a query snapshot. You may want to update
        // your UI to display an error message to the user.
        // ...
        Log.e("error", e.getMessage());
    }


}
