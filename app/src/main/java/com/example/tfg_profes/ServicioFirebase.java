package com.example.tfg_profes;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class ServicioFirebase extends FirebaseMessagingService {
    public ServicioFirebase() {
    }

    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {

        }
        if (remoteMessage.getNotification() != null) {
            System.out.println(remoteMessage.getNotification().getBody());
        }

    }
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        //hacer php con el update
    }
}
