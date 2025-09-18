package com.example.budget_watcher;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        test = findViewById(R.id.test);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 1);
        } else {
            ReadSMS();
        }
    }

    private void ReadSMS() {
        Uri sms = Uri.parse("content://sms");
        Cursor cursor = getContentResolver().query(sms, null, null, null, "date DESC");

        if (cursor != null && cursor.moveToFirst()) {
            int messagePos = cursor.getColumnIndex("body");
            int addressPos = cursor.getColumnIndex("address");
            int datePos = cursor.getColumnIndex("date");

            do {
                String message = cursor.getString(messagePos);
                String address = cursor.getString(addressPos);
                long dateTime = cursor.getLong(datePos);
                Date date = new Date(dateTime);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedDate = dateFormat.format(date);

                Toast.makeText(this, "From: " + address + "\nMessage: " + message + "\nDate: " + formattedDate, Toast.LENGTH_SHORT).show();
                test.setText("Message: " + message + "\nFrom: " + address + "\nDate: " + formattedDate);
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            test.setText("No messages found.");
        }
    }
}
