
package com.example.collegeassistant.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;


import com.bumptech.glide.Glide;

import com.example.collegeassistant.api.user_helper.UserHelper;
import com.example.collegeassistant.models.Message;
import com.example.collegeassistant.api.message_helper.MessageHelper;
import com.example.collegeassistant.R;
import com.example.collegeassistant.models.User;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;



import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.reyclerview_message_list) RecyclerView mRecycler;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;
    @BindView(R.id.photoPickerButton)ImageButton  mPhotoPickerButton;
    @BindView(R.id.messageEditText) EditText mMessageEditText;
    @BindView(R.id.sendButton) Button mSendButton;


    private MessageAdapter mAdapter;

    private final String TAG = "ChatActivity";
    private static final String CHAT = "chat";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;



    FirebaseAuth mAuth;
    FirebaseUser mUser;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        UserHelper.getUser(mUser.getUid()).addOnCompleteListener(this, new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                user = task.getResult().toObject(User.class);
            }
        });


        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        //rECYCLERvIEW
        this.configureRecyclerView();

        // ImagePickerButton shows an image picker to upload a image for a message
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fire an intent to show an image picker
            }
        });

        // Enable Send button when there's text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageHelper.createMessageForChat(
                        mMessageEditText.getText().toString(), CHAT, user)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG,e.getLocalizedMessage());
                            }
                        });
                // Clear input box
                mMessageEditText.setText("");
            }
        });
    }


    private void configureRecyclerView(){

        //Configure Adapter & RecyclerView
        //todo:update Message DB path
        this.mAdapter = new MessageAdapter(generateOptionsForAdapter(MessageHelper.getAllMessageForChat(CHAT)), Glide.with(this), this.mUser.getUid());
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mRecycler.smoothScrollToPosition(mAdapter.getItemCount()); // Scroll to bottom on new messages
            }
        });
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(this.mAdapter);
    }

    //Create options for RecyclerView from a Query
    private FirestoreRecyclerOptions<Message> generateOptionsForAdapter(Query query){
        return new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .setLifecycleOwner(this)//controls the listening process
                .build();
    }



}