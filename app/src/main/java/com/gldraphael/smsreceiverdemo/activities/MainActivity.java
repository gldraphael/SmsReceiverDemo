package com.gldraphael.smsreceiverdemo.activities;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gldraphael.smsreceiverdemo.R;
import com.gldraphael.smsreceiverdemo.receivers.SmsReceiver;

public class MainActivity extends AppCompatActivity {

    private TextView txtPrompt;
    private TextView txtCode;

    private SmsReceiver smsReceiver = new SmsReceiver(new SmsReceiver.OnSmsReceivedListener() {
        @Override
        public void onSmsReceived(String code) {
            if(code == null) {
                txtPrompt.setText(R.string.parse_error);
                txtCode.setText(null);
            }
            else {
                txtCode.setText(code);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtPrompt = (TextView) findViewById(R.id.txtPrompt);
        txtCode = (TextView) findViewById(R.id.txtCode);
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
