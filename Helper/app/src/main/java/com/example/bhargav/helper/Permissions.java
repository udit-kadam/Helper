package com.example.bhargav.helper;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Permissions extends AppCompatActivity {
    private Button storage,sms,contact,location;
    private final static int REQUEST_CODE_PERMISSION_SEND_SMS=123;
    private final static int REQUEST_CODE_PERMISSION_READ_SMS=456;
    private final static int REQUEST_CODE_PERMISSION_READ_CONTACTS=789;
    private final static int REQUEST_CODE_PERMISSION_ACCESS_COARSE_LOCATION=11;
    private final static int REQUEST_CODE_PERMISSION_ACCESS_FINE_LOCATION=12;
    private final static int REQUEST_CODE_PERMISSION_WRITE_EXTERNAL_STORAGE=111;
    private final static int REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE=111;
    private final static int REQUEST_CODE_PERMISSION_READ_PHONE_STATE=15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

        storage=(Button)findViewById(R.id.btn_storage);
        sms=(Button)findViewById(R.id.btn_sms);
        location=(Button)findViewById(R.id.btn_location);
        contact=(Button)findViewById(R.id.btn_contact);


        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkPermission(android.Manifest.permission.READ_SMS)||!checkPermission(android.Manifest.permission.SEND_SMS)){
                    if(!checkPermission(android.Manifest.permission.READ_SMS))
                    {
                        ActivityCompat.requestPermissions(Permissions.this,new String[]{
                                (android.Manifest.permission.READ_SMS)},REQUEST_CODE_PERMISSION_READ_SMS);
                    }
                    if(!checkPermission(android.Manifest.permission.SEND_SMS))
                    {
                        ActivityCompat.requestPermissions(Permissions.this,new String[]{
                                (Manifest.permission.SEND_SMS)},REQUEST_CODE_PERMISSION_SEND_SMS);
                    }
                }
                else {
                    sms.setEnabled(false);
                }
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkPermission(Manifest.permission.READ_CONTACTS)){
                    if(!checkPermission(Manifest.permission.READ_CONTACTS))
                    {
                        ActivityCompat.requestPermissions(Permissions.this,new String[]{
                                (Manifest.permission.READ_CONTACTS)},REQUEST_CODE_PERMISSION_READ_CONTACTS);
                    }
                    if(!checkPermission(Manifest.permission.READ_PHONE_STATE))
                    {
                        ActivityCompat.requestPermissions(Permissions.this,new String[]{
                                (Manifest.permission.READ_CONTACTS)},REQUEST_CODE_PERMISSION_READ_PHONE_STATE);
                    }
                }
                else {
                    contact.setEnabled(false);
                }
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) || !checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)){
                    if(!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION))
                    {
                        ActivityCompat.requestPermissions(Permissions.this,new String[]{
                                (Manifest.permission.ACCESS_FINE_LOCATION)},REQUEST_CODE_PERMISSION_ACCESS_FINE_LOCATION);
                    }
                    if(!checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION))
                    {
                        ActivityCompat.requestPermissions(Permissions.this,new String[]{
                                (Manifest.permission.ACCESS_COARSE_LOCATION)},REQUEST_CODE_PERMISSION_ACCESS_COARSE_LOCATION);
                    }
                }
                else {
                    location.setEnabled(false);
                }
            }
        });

        storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) || !checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
                    if(!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    {
                        ActivityCompat.requestPermissions(Permissions.this,new String[]{
                                (Manifest.permission.WRITE_EXTERNAL_STORAGE)},REQUEST_CODE_PERMISSION_WRITE_EXTERNAL_STORAGE);
                    }
                    if(!checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE))
                    {
                        ActivityCompat.requestPermissions(Permissions.this,new String[]{
                                (Manifest.permission.READ_EXTERNAL_STORAGE)},REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE);
                    }
                }
                else {
                    storage.setEnabled(false);
                }
            }
        });
    }
    public boolean checkPermission(String permission)
    {
        int checkPermission= ContextCompat.checkSelfPermission(this, permission);
        return checkPermission == PackageManager.PERMISSION_GRANTED;
    }
}
