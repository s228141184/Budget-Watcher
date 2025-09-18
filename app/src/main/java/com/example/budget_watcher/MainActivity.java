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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView test;
    private static final int REQUEST_CODE_READ_SMS = 1;
    private MessageAdapter adapter;

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

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new Spacing(10));
        adapter = new MessageAdapter();
        recyclerView.setAdapter(adapter);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 1);
        } else {
            loadMessages();
        }
    }

    private void loadMessages() {
        List<Message> messages = new ArrayList<>();
        Uri smsUri = Uri.parse("content://sms");
        Cursor cursor = getContentResolver().query(smsUri, null, null, null, "date DESC");

        if (cursor != null && cursor.moveToFirst()) {
            int bodyIdx = cursor.getColumnIndex("body");
            int addressIdx = cursor.getColumnIndex("address");
            int dateIdx = cursor.getColumnIndex("date");

            do {
                String body = cursor.getString(bodyIdx);
                String address = cursor.getString(addressIdx);
                long dateLong = cursor.getLong(dateIdx);
                Date date = new Date(dateLong);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedDate = dateFormat.format(date);

                messages.add(new Message(body, address, formattedDate));
            } while (cursor.moveToNext());

            cursor.close();
        } else {
            Toast.makeText(this, "No messages found", Toast.LENGTH_SHORT).show();
        }

        adapter.setMessages(messages);
    }
}
