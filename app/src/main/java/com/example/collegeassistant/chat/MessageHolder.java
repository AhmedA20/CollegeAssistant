package com.example.collegeassistant.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.collegeassistant.models.Message;
import com.example.collegeassistant.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageHolder extends RecyclerView.ViewHolder {

    //Received items view binder
    @BindView(R.id.image_message_profile) ImageView profile;
    @BindView(R.id.text_message_name) TextView sender;
    @BindView(R.id.recMessageText) TextView message;
    @BindView(R.id.recImg) ImageView recievedImage;
    @BindView(R.id.received_message_time) TextView time_received;

    //Sent items view binder
    @BindView(R.id.sent_message_time) TextView time_sent;
    @BindView(R.id.sentImg) ImageView img;
    @BindView(R.id.sentMessageText) TextView text;

    //image manager view binder
    private final RequestManager glide;
    private boolean isCurrentUser;

    public MessageHolder(@NonNull View itemView, RequestManager glide) {
        super(itemView);
        this.glide = glide;
        ButterKnife.bind(this, itemView);
    }

    //Received items
    public void bind(Message message, String uid){
        isCurrentUser = message.getUserSender().getUid().equals(uid);
        if(isCurrentUser){//received items
            //sets the message
            this.message.setText(message.getMessage());

            //setting user
            this.sender.setText(message.getUserSender().getUsername());

            //set image to view
            if(message.getUserSender().getUrlPicture()!=null){//profile
                profile.setVisibility(View.VISIBLE);
                glide.load(message.getUserSender().getUrlPicture()).into(profile);
            }

            if(message.getUrlImage()!=null){//message content
                recievedImage.setVisibility(View.VISIBLE);
                glide.load(message.getUrlImage()).into(recievedImage);
            }

            //set time
            time_received.setText(this.convertDateToHour(message.getDateCreated()));
        }else{//item sent view holder
            //sets the message
            text.setText(message.getMessage());

            //set image
            if(message.getUrlImage()!=null){//message content
                img.setVisibility(View.VISIBLE);
                glide.load(message.getUrlImage()).into(img);
            }

            //set message time
            time_sent.setText(this.convertDateToHour(message.getDateCreated()));
        }
    }

    private String convertDateToHour(Date date){
        DateFormat dfTime = new SimpleDateFormat("HH:mm");
        return dfTime.format(date);
    }
}
