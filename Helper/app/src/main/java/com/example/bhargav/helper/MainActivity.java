package com.example.bhargav.helper;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class MainActivity extends AppCompatActivity {
    private Button pwd,change,exit,about,permission;
    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance=this;

        pwd=(Button)findViewById(R.id.btn_pwd);
        change=(Button)findViewById(R.id.btn_change);
        about=(Button)findViewById(R.id.btn_about);
        exit=(Button)findViewById(R.id.btn_exit);
        permission=(Button)findViewById(R.id.btn_permission);

        pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openset();
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openchange();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openpermission();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openabout();
            }
        });

    }


    public void openset()
    {
        Intent intent= new Intent(this,Set_password.class);
        startActivity(intent);
    }

    public void openchange()
    {
        Intent intent= new Intent(this,ChangePassword.class);
        startActivity(intent);
    }

    public void openpermission()
    {
        Intent intent= new Intent(this,Permissions.class);
        startActivity(intent);
    }

    public void openabout()
    {
        Intent intent= new Intent(this,AboutApp.class);
        startActivity(intent);
    }

}
