package com.example.datasavingpractice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class DataActivity extends AppCompatActivity {
    String name;
    String phone;
    TextView tvName;
    TextView tvPhone;
    Button savePref;
    Button showPref;
    Button saveInternal;
    Button showInternal;
    Button saveSQL;
    Button showSQL;
    Button back;
    DataAccess dataAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dataAccess = new DataAccess(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        Intent incoming = getIntent();
        tvName = findViewById(R.id.nameText);
        tvPhone = findViewById(R.id.phoneText);
        savePref = findViewById(R.id.btnSaveShared);
        showPref = findViewById(R.id.btnShowShared);
        saveInternal = findViewById(R.id.buttonSaveInternal);
        showInternal = findViewById(R.id.buttonShowInternal);
        saveSQL = findViewById(R.id.buttonSQLITEsave);
        showSQL = findViewById(R.id.buttonSQLITEshow);
        back = findViewById(R.id.btnBack);
        back.setOnClickListener(view -> finish());
        if (incoming != null) {
            name = incoming.getStringExtra("name");
            phone = incoming.getStringExtra("phone");
            tvName.setText(name);
            tvPhone.setText(phone);
        }
        SharedPreferences sp = getSharedPreferences("contact", MODE_PRIVATE);
        savePref.setOnClickListener(view -> {
            tvName.setText("");
            tvPhone.setText("");
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("name", name);
            editor.putString("phone", phone);
            editor.apply();
        });

        showPref.setOnClickListener(v -> {
            String userName = sp.getString("name", "Data Not Found :'(");
            String userPhone = sp.getString("phone", "Data Not Found :'(");
            tvName.setText(userName);
            tvPhone.setText(userPhone);
        });
        saveInternal.setOnClickListener(view -> {
            tvName.setText("");
            tvPhone.setText("");
            String data = name + "-" + phone;
            FileOutputStream outputStream;
            try {
                outputStream = openFileOutput("contact", Context.MODE_PRIVATE);
                outputStream.write(data.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        showInternal.setOnClickListener(view -> {
            try {
                FileInputStream fileName = openFileInput("contact");
                InputStreamReader InputRead = new InputStreamReader(fileName);

                char[] inputBuffer = new char[30];
                StringBuilder data = new StringBuilder();
                int charRead;

                while ((charRead = InputRead.read(inputBuffer)) > 0) {
                    data.append(String.copyValueOf(inputBuffer, 0, charRead));
                }
                InputRead.close();
                String[] details = data.toString().split("-");
                tvName.setText(details[0]);
                tvPhone.setText(details[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        saveSQL.setOnClickListener(view -> {
            tvName.setText("");
            tvPhone.setText("");
            dataAccess.addContact(new Contact(name, phone));
        });
        showSQL.setOnClickListener(view -> {
            tvName.setText(dataAccess.getContact().getName());
            tvPhone.setText(dataAccess.getContact().getPhone());
        });
    }
}