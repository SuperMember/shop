package com.example.lenovo.taoshop;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.taoshop.service.MinaService;
import com.example.lenovo.taoshop.utils.mina.SessionManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MinaActivity extends AppCompatActivity {
    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.btn)
    Button btn;
    @Bind(R.id.write)
    Button write;
    private MessageBroadCastReceiver messageBroadCastReceiver = new MessageBroadCastReceiver();
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mina);
        ButterKnife.bind(this);
        register();
    }

    private void initNotification(String msg) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MessageActivity.class), 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                //点击通知后自动清除
                .setAutoCancel(true)
                .setContentTitle("新信息")
                .setContentText(msg)
                .setContentIntent(pendingIntent);
        //发送通知
        notificationManager.notify(3, builder.build());
    }

    public void register() {
        IntentFilter intentFilter = new IntentFilter("com.example.lenovo.taoshop.utils.mina");
        LocalBroadcastManager.getInstance(this).registerReceiver(messageBroadCastReceiver, intentFilter);
    }

    public void unregister() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageBroadCastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MinaService.class));
        unregister();
    }

    @OnClick({R.id.btn, R.id.write})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                Intent intent = new Intent(this, MinaService.class);
                intent.setAction("com.mina.service");
                startService(intent);
                break;
            case R.id.write:
                SessionManager.getInstance().writeToService("你好");
                break;
        }

    }

    public class MessageBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String mes = intent.getStringExtra("message");
            text.setText(mes);
            initNotification(mes);
        }
    }
}
