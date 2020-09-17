package com.example.restapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restapi.network.MyIntentService;
import com.example.restapi.utils.NetworkHelper;

public class MainActivity extends AppCompatActivity {
Button button;
TextView textView;
private boolean isNetworkOk;

private BroadcastReceiver mReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra(MyIntentService.SERVICE_PAYLOAD);
        Toast.makeText(context, "data "+data, Toast.LENGTH_SHORT).show();
        textView.setText(data);
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btn);
        textView = findViewById(R.id.textView);

        isNetworkOk = NetworkHelper.isNetworkAvailable(MainActivity.this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runCode();
            }
        });
    }

    private void runCode() {
        Intent intent = new Intent(MainActivity.this, MyIntentService.class);
        startService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        LocalBroadcastManager.getInstance(MainActivity.this)
                .registerReceiver(mReceiver, new IntentFilter(MyIntentService.SERVICE_MESSAGE));
    }

    @Override
    protected void onStop() {
        super.onStop();

        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mReceiver);
    }
}
