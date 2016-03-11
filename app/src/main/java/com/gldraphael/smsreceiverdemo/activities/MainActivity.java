package com.gldraphael.smsreceiverdemo.activities;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gldraphael.smsreceiverdemo.R;
import com.gldraphael.smsreceiverdemo.receivers.SmsReceiver;

public class MainActivity extends AppCompatActivity {

    private SmsReceiver smsReceiver = new SmsReceiver(new SmsReceiver.OnVerificationCodeReceivedListener() {
        @Override
        public void onCodeReceived(String code) {
            // TODO: handle event here
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(smsReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsReceiver);
    }
}
