package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NextActivity extends AppCompatActivity {

    TextView textView;
    Button btn_finish;

    Button btn_start;
    Button btn_stop;
    Button btn_alarm;

    AlarmManager alarmManager;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == "ALARM"){
                Toast.makeText(context, "alarm !!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        registerReceiver(receiver, new IntentFilter("ALARM"));

        btn_alarm = findViewById(R.id.btn_alarm);
        btn_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("ALARM");
                PendingIntent pendingIntent = PendingIntent
                        .getBroadcast(NextActivity.this, 1, intent, PendingIntent.FLAG_IMMUTABLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, 5000, pendingIntent);
                }else {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, 5000, pendingIntent);
                }
            }
        });
// ---------------------------------------------------
        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NextActivity.this, MyService.class);
                intent.putExtra("data", "from next activity");
                startService(intent);
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NextActivity.this, MyService.class);
                stopService(intent);
            }
        });

        textView = findViewById(R.id.tv_msg);
        btn_finish = findViewById(R.id.btn_finish);

        if (getIntent() != null){
            String msg = getIntent().getStringExtra("msg");
            if (msg != null){
                textView.setText(msg);
            }
        }

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NextActivity.this.finish();
                Intent intent = new Intent(NextActivity.this, MainActivity.class);
                intent.putExtra("msg","from next activity");
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}