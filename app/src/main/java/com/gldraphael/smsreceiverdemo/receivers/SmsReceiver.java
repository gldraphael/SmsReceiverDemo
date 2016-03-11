package com.gldraphael.smsreceiverdemo.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gldraphael on 11/03/16.
 */
public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "###SMSReceiver";

    private OnVerificationCodeReceivedListener listener;

    public SmsReceiver(@NonNull OnVerificationCodeReceivedListener listener){
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                if (pdusObj != null) {
                    for (Object aPdusObj : pdusObj) {
                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                        String senderAddress = currentMessage.getDisplayOriginatingAddress();
                        String message = currentMessage.getDisplayMessageBody();

                        Log.v(TAG, "Received SMS: " + message + ", Sender: " + senderAddress);

                        // Ensure the SMS is for your app by checking the sender
                        // if (!senderAddress.toUpperCase().contains("MYAPP")) {
                        //    return;
                        // }

                        // get the verification code from the SMS
                        String verificationCode = getVerificationCode(message);
                        if (verificationCode != null) {
                            Log.v(TAG, "Verification Code: " + verificationCode);
                            listener.onCodeReceived(verificationCode);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Looks for a 6 digit verification code and returns it if found
     * @param message The sms message to get parse the code from
     * @return Verification Code if found, else returns null
     */
    private String getVerificationCode(String message) {
        Pattern pattern = Pattern.compile("([\\d]{6})");
        Matcher m = pattern.matcher(message);
        if(m.find()){
            return m.group(1);
        }
        return null;
    }

    public interface OnVerificationCodeReceivedListener {
        void onCodeReceived(String code);
    }
}
