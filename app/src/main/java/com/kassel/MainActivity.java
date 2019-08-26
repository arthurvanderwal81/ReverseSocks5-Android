package com.kassel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent socksIntent = new Intent();
        socksIntent.setClass(this, SOCKSIntentService.class);
        startService(socksIntent);

        Intent reverseTunnelIntent = new Intent();
        reverseTunnelIntent.setClass(this, ReverseTunnelIntentService.class);
        startService(reverseTunnelIntent);
    }
}
