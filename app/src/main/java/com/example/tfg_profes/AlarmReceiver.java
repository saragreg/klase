package com.example.tfg_profes;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Obtener el Bundle de datos del intent
        /*Bundle bundle = intent.getExtras();
        String descr="";

        // Verificar si el Bundle no es nulo
        if (bundle != null) {
            // Obtener los datos específicos del Bundle
            descr = bundle.getString("descr");
        }*/
        // Crear y mostrar la notificación
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Construir la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.libro)
                .setContentTitle("Alarma")
                .setContentText("¡Mañana tienes clase!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Mostrar la notificación
        notificationManager.notify(1, builder.build());
    }
}
