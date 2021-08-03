package com.example.bhargav.helper;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import static android.content.Context.KEYGUARD_SERVICE;
import static android.support.constraint.Constraints.TAG;

public class SMS extends BroadcastReceiver {
    public static final String SMS_BUNDLE = "pdus";
    private String ownerMobileNumber, sendingNumber;
    public String filename="myfile.txt";
    List IMEI;
    PowerManager pm;
    private String provider;
    LocationManager locationManager;

    public void onReceive(Context context, Intent intent) {

        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody().toString();
                sendingNumber = smsMessage.getOriginatingAddress();
                String line=null;

                File filepath = Environment.getExternalStorageDirectory();
                File dir = new File(filepath.getAbsolutePath() + "/HELPER/");
                dir.mkdirs();
                File hello = new File(dir,filename);

                String ret = "";
                try {
                    InputStream inputStream = context.openFileInput(filename);

                    if ( inputStream != null ) {
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String receiveString = "";
                        StringBuilder stringBuilder = new StringBuilder();

                        while ( (receiveString = bufferedReader.readLine()) != null ) {
                            stringBuilder.append(receiveString);
                        }

                        inputStream.close();
                        ret = stringBuilder.toString();
                    }
                }
                catch (FileNotFoundException e) {
                    Log.e("login activity", "File not found: " + e.toString());
                } catch (IOException e) {
                    Log.e("login activity", "Can not read file: " + e.toString());
                }
                int cnt=ret.length();
                int c=smsBody.length();

                String SMG[]=smsBody.split(" ");
                String con=smsBody.substring(cnt+1,c);
                String code=SMG[0];
                String action=SMG[1];
                int sss=code.length();
                String contact = action;
                String srs=ret;

                if(code.contains(ret)) {

                    if (action.equals("Silent") || action.equals("silent")) {
                        Intent in = new Intent(context, MainActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                                12345, in, PendingIntent.FLAG_CANCEL_CURRENT);
                        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                        am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        Toast.makeText(context, "Now in Silent Mode", Toast.LENGTH_LONG).show();
                    }


                    if (action.equals("Normal") || action.equals("normal")) {
                        Intent in = new Intent(context, MainActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                                12345, in, PendingIntent.FLAG_CANCEL_CURRENT);
                        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                        Toast.makeText(context, "Now in Normal Mode", Toast.LENGTH_SHORT).show();
                    }


                    if (action.equals("Vibrate") || action.equals("vibrate")) {
                        Intent in = new Intent(context, MainActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                                12345, in, PendingIntent.FLAG_CANCEL_CURRENT);
                        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                        am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                        Toast.makeText(context, "Now in Vibrate Mode", Toast.LENGTH_SHORT).show();
                    }


                    if (action.equals("Location") || action.equals("location")) {
                        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                        Location location;
                        boolean isGPSEnabled = locationManager
                                .isProviderEnabled(LocationManager.GPS_PROVIDER);
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            String sendMessageBody = "";
                            SmsManager smsMan = SmsManager.getDefault();
                            sendMessageBody = "Latitude:"+latitude + "\n" +"Longitude:"+longitude;
                            smsMan.sendTextMessage(sendingNumber, null, sendMessageBody, null, null);
                        }
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();
                                String sendMessageBody = "";
                                SmsManager smsMan = SmsManager.getDefault();
                                sendMessageBody = "Latitude:"+latitude + "\n" +"Longitude:"+longitude;
                                smsMan.sendTextMessage(sendingNumber, null, sendMessageBody, null, null);
                            }
                        }
                    }
                    if (action.equals("Imei") || action.equals("imei")) {
                        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                        IMEI = Collections.singletonList(tMgr.getImei());

                        SmsManager sm = SmsManager.getDefault();
                        sm.sendTextMessage(sendingNumber, null, "IMEI No:-"+String.valueOf(IMEI), null, null);
                    } else {
                        String sendMessageBody = "";
                        final String SELECTION = "((" +
                                ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND (" +
                                ContactsContract.Contacts.DISPLAY_NAME + " LIKE '" + contact + "%' ))";
                        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, SELECTION, null, null);
                        while (cursor.moveToNext()) {
                            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                            String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            sendMessageBody += name + "\n" + phoneNumber + "\n";
                        }
                        Toast.makeText(context.getApplicationContext(), sendMessageBody, Toast.LENGTH_SHORT).show();
                        if (sendMessageBody.length() >= 5) {
                            SmsManager ss = SmsManager.getDefault();
                            ss.sendTextMessage(sendingNumber, null, "" + sendMessageBody, null, null);

                        }


                    }
                }

            }
            }

        }
    }

