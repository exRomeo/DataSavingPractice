package com.example.datasavingpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button nextButton;
    EditText nameField;
    EditText phoneField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nextButton = findViewById(R.id.nextButton);
        nameField = findViewById(R.id.nameField);
        phoneField = findViewById(R.id.phoneField);
        nextButton.setOnClickListener(view -> {
            Intent toData = new Intent(this, DataActivity.class);
            toData.putExtra("name",nameField.getText().toString());
            toData.putExtra("phone", phoneField.getText().toString());
            startActivity(toData);
        });
    }
}