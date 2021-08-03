package com.example.bhargav.helper;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class ChangePassword extends AppCompatActivity {
    private Button set;
    private EditText password;
    private String PASSWORD;
    public String filename="myfile.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        set=(Button)findViewById(R.id.btn_change);
        password=(EditText)findViewById(R.id.et_pwd);

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PASSWORD = password.getText().toString();
                File filepath = Environment.getExternalStorageDirectory();
                File dir = new File(filepath.getAbsolutePath() + "/HELPER/");
                dir.mkdirs();
                try {
                    File my = new File(dir, filename);
                    FileWriter writer = new FileWriter(my);
                    writer.append(PASSWORD);
                    writer.flush();
                    writer.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getBaseContext(), "Password Changed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
