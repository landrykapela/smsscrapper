package tz.co.neelansoft.scrapsms;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class SMS {

    private static final int REQUEST_READ_SMS_CODE = 100;
    private SmsManager SMSManager;
    private Context mContext;
    public SMS(Context context){
        this.mContext = context;
        this.SMSManager = SmsManager.getDefault();
    }

    public ArrayList<TextMessage> getInbox(){
        ArrayList<TextMessage> inbox = new ArrayList<>();
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Cursor inboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"),null,null,null,null);
        int smsBodyIndex = inboxCursor.getColumnIndex("body");
        int smsAddressIndex = inboxCursor.getColumnIndex("address");
        if(smsBodyIndex > 0 && inboxCursor.moveToFirst()){
            inbox.clear();
            while(inboxCursor.moveToNext()){
                String addr = inboxCursor.getString(smsAddressIndex);
                String body = inboxCursor.getString(smsBodyIndex);
                TextMessage tm = new TextMessage(addr,body);
                inbox.add(tm);
            }
        }
        return inbox;
    }
}
