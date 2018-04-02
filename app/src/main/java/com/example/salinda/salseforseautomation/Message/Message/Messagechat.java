package com.example.salinda.salseforseautomation.Message.Message;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.model.LoginModel;
import com.example.salinda.salseforseautomation.session.SessionHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Messagechat extends AppCompatActivity {

    private String mChatuser;
    // private Toolbar mChatToolbar;
    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;
    private String mCurrent_user_id;
    private LoginModel loginModel;
    private SessionHandler sessionHandler;

    private ImageButton mChatAddButton;
    private ImageButton mChatSendButton;
    private EditText mChatMessageView;

    private RecyclerView mMessagesList;


    //retriving messages
    private final List<Message> messageList = new ArrayList<>();
    private LinearLayoutManager mLinearLayout;
    private MessageAdapter mAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagechat);


        sessionHandler=new SessionHandler(this);
        loginModel=sessionHandler.getUserDetails();

        mChatAddButton=(ImageButton)findViewById(R.id.chat_add_button);
        mChatSendButton=(ImageButton)findViewById(R.id.chat_send_button);
        mChatMessageView=(EditText) findViewById(R.id.chat_message_view);

        mAdapter=new MessageAdapter(messageList);

        mMessagesList=(RecyclerView)findViewById(R.id.messages_list);
        mLinearLayout=new LinearLayoutManager(this);

        mMessagesList.setHasFixedSize(true);
        mMessagesList.setLayoutManager(mLinearLayout);

        mMessagesList.setAdapter(mAdapter);




        //mChatToolbar=findViewById(R.id.chat_app_bar);


        mRootRef= FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        // mCurrent_user_id=mAuth.getCurrentUser().getUid();
        mCurrent_user_id=Integer.toString(loginModel.getId());



        // setSupportActionBar(mChatToolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mChatuser=Integer.toString(getIntent().getIntExtra("user_id",0));
        String user_name=getIntent().getStringExtra("user_name");

       // setTitle(user_name);



        // getSupportActionBar().setTitle(user_name);//display chat user id on the app bar

        loadMessages();

       /* mRootRef.child("Chat").child(mCurrent_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.hasChild(mChatuser)){//if chat is not exists

                    Map chatAddMap=new HashMap();
                    chatAddMap.put("seen",false);
                    chatAddMap.put("timestamps", ServerValue.TIMESTAMP);

                    Map chatUserMap=new HashMap();
                    chatUserMap.put("Chat/"+mCurrent_user_id+"/"+mChatuser,chatAddMap);
                    chatUserMap.put("Chat/"+mChatuser+"/"+mCurrent_user_id,chatAddMap);

                    mRootRef.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError!=null){

                                Log.d("Chat_Log",databaseError.getMessage().toString());
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/

        mChatSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

    }
    //load messages from database
    private void loadMessages() {

        mRootRef.child("messages").child(mCurrent_user_id).child(mChatuser).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message messages=dataSnapshot.getValue(Message.class);
                messageList.add(messages);
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void sendMessage() {

        String message=mChatMessageView.getText().toString();

        if (!TextUtils.isEmpty(message)){

            String current_user_ref="messages/"+mCurrent_user_id+"/"+mChatuser;
            String chat_user_ref="messages/"+mChatuser+"/"+mCurrent_user_id;

            DatabaseReference user_message_push=mRootRef.child("messages").child(String.valueOf(mCurrent_user_id)).child(String.valueOf(mChatuser)).push();

            String push_id=user_message_push.getKey();

            Map messageMap=new HashMap();

            messageMap.put("message",message);
            messageMap.put("seen",false);
            messageMap.put("type","text");
            messageMap.put("time", ServerValue.TIMESTAMP);
            messageMap.put("from",mCurrent_user_id);

            Map messageUserMap=new HashMap();

            messageUserMap.put(current_user_ref+"/"+push_id,messageMap);
            messageUserMap.put(chat_user_ref+"/"+push_id,messageMap);

            mChatMessageView.setText("");


            mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    if (databaseError!=null){

                        Log.d("Chat_Log",databaseError.getMessage().toString());
                    }

                }
            });

        }
    }
}

