package com.example.salinda.salseforseautomation.Message.Message;


import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.salinda.salseforseautomation.R;
import com.example.salinda.salseforseautomation.model.LoginModel;
import com.example.salinda.salseforseautomation.session.SessionHandler;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class NotificationsService extends IntentService {

    private DatabaseReference mRootRef;
    private LoginModel mCurrentUser;
    private NotificationCompat.Builder notifications;

    public NotificationsService() {
        super("NotificationsService");
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        mRootRef= FirebaseDatabase.getInstance().getReference();
        mCurrentUser = new SessionHandler(getBaseContext()).getUserDetails();
        mRootRef.child("notifications").child(Integer.toString(mCurrentUser.getId()))
                .addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    }
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        Message messages=dataSnapshot.getValue(Message.class);

                        if(!messages.isSeen()){
                            notifications = new NotificationCompat.Builder(getBaseContext());

                            notifications.setAutoCancel(true);
                            notifications.setTicker("you have new messages");
                            notifications.setSmallIcon(R.drawable.message_icon);
                            notifications.setContentTitle(messages.getMessage());
                            notifications.setContentText(messages.getMessage());
                            notifications.setVibrate(new long[]{0,1000,0});
                            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            notifications.setSound(alarmSound);

                            Intent intent = new Intent(getBaseContext(), Messagechat.class);
                            intent.putExtra("user_id", Integer.parseInt(messages.getFrom()));
                           // intent.putExtra("user_name", messages.getSenderName());
                            PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            notifications.setContentIntent(pendingIntent);

                            NotificationManager notificationManager = (NotificationManager) getSystemService(getBaseContext().NOTIFICATION_SERVICE);
                            notificationManager.notify((int)messages.getTime(),notifications.build());
                        }

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
}

