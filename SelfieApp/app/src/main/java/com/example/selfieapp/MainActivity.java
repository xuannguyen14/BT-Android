package com.example.selfieapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.selfieapp.DataModel.SelfieImage;

import java.util.ArrayList;

import static com.example.selfieapp.DataModel.DataConverter.convertImage2ByteArray;

public class MainActivity extends AppCompatActivity {

    ListView lvImage;
    int REQUEST_CODE_CAMERA = 123;
    ArrayList<SelfieImage> images;
    ImageAdapter imageAdapter;
    int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvImage = findViewById(R.id.listImage);
        images = new ArrayList<>();
        imageAdapter = new ImageAdapter(this, R.layout.image_row, images);
        lvImage.setAdapter(imageAdapter);

        sendNotification();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_camera, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //mở camera
        alarmNotification();
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
        return super.onOptionsItemSelected(item);
    }

    // lấy hình chụp từ camera
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data"); // default
            int s = 1;
            if(bitmap != null) {
                images.add(new SelfieImage("image " + s, convertImage2ByteArray(bitmap)));
            }
            s++;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    // xin quyền mở camera, kiem tra cau tra loi cua user khi nguoi dung bam tra loi
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_CAMERA && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        } else{
            Toast.makeText(this, "You did not allow the camera to be opened!", Toast.LENGTH_LONG).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void sendNotification() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        Notification notification = new NotificationCompat.Builder(this, NotificationClass.CHANNEL_ID)
                .setContentTitle("Notification")
                .setContentText("It's selfie time!")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(bitmap)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    private void alarmNotification(){
        // Display Android notification at a particular time with Alarm Manager
        Intent intent = new Intent(MainActivity.this, NotificationClass.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long timeStart = System.currentTimeMillis();
        long tenSecondsInMillis = 1000 * 10;

        alarmManager.set(AlarmManager.RTC_WAKEUP, timeStart + tenSecondsInMillis, pendingIntent);
    }
}