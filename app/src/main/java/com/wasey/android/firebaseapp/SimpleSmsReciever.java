package com.wasey.android.firebaseapp;


import android.content.*;
import android.os.Bundle;
import android.telephony.*;
import android.util.Log;
import android.widget.Toast;



public class SimpleSmsReciever extends BroadcastReceiver {

    private static final String TAG = "Message recieved";

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle pudsBundle = intent.getExtras();
        if (pudsBundle != null) {
            String number = "";
            String message = "";
            Object[] pdus = (Object[]) pudsBundle.get("pdus");
            //put a check here for when you recieve a message from only a certain number, only then show TOAST.
            for (int i = 0; i < pdus.length; i++) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
                number = smsMessage.getOriginatingAddress();
                message = smsMessage.getDisplayMessageBody();

            }
            //put a check here for when you recieve a message from only a certain number, only then show TOAST.
            if (number.matches("12345")) {
                Toast.makeText(context, "SMS Received From :" + number + "\n" + message, Toast.LENGTH_LONG).show();
            }
        }
    }
}


