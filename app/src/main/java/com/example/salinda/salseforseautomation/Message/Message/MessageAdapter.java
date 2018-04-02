package com.example.salinda.salseforseautomation.Message.Message;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.model.LoginModel;
import com.example.salinda.salseforseautomation.session.SessionHandler;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {


    private List<Message> mMessageList;
    private FirebaseAuth mAuth;
    // private LoginModel loginModel;
    private SessionHandler sessionHandler;
    private LoginModel loginModel;


    public MessageAdapter(List<Message> mMessageList) {

        this.mMessageList = mMessageList;

    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_single_layout, parent, false);

        return new MessageViewHolder(v);


    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText;

        // public TextView messageText;
        private LinearLayout linearLayout;
        private CardView cardView;


        public MessageViewHolder(View view) {
            super(view);


            messageText = (TextView) view.findViewById(R.id.message_text_layout);
            linearLayout = (LinearLayout) view.findViewById(R.id.linearLayoutCardView);
            sessionHandler = new SessionHandler(view.getContext());
            loginModel = sessionHandler.getUserDetails();
            cardView = (CardView) view.findViewById(R.id.cardViewMessage);


        }
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {


        Message c = mMessageList.get(position);

        String from_user = c.getFrom();


        // mAuth = FirebaseAuth.getInstance();
        //String current_user_id=mAuth.getCurrentUser().getUid();

        String current_user_id = Integer.toString(loginModel.getId());

        // mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user)


        if (from_user.equals(current_user_id)) {
            //this is the message send by current user
            // -----  holder.messageText.setBackgroundColor(Color.WHITE);
            // holder.messageText.setBackgroundColor(Color.parseColor("#7EC0EE"));
            //-----  holder.messageText.setTextColor(Color.BLACK);
            holder.cardView.setCardBackgroundColor(Color.WHITE);
            holder.messageText.setTextColor(Color.BLACK);
            holder.linearLayout.setGravity(Gravity.LEFT);


        } else {
            //this message sent by other user
            //holder.messageText.setBackgroundColor(7EC0EE);
            //  holder.messageText.
            holder.cardView.setBackgroundResource(R.drawable.message_text_background);
            holder.messageText.setTextColor(Color.WHITE);
            holder.linearLayout.setGravity(Gravity.RIGHT);

        }

        holder.messageText.setText(c.getMessage());

    }


    @Override
    public int getItemCount() {
        return mMessageList.size();
    }
}