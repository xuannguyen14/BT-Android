package com.example.selfieapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.room.Room;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.selfieapp.DataModel.ImageDAO;
import com.example.selfieapp.DataModel.ImageDatabase;
import com.example.selfieapp.DataModel.SelfieImage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.selfieapp.DataModel.DataConverter.convertImage2ByteArray;

public class MainActivity extends AppCompatActivity {

    ListView lvImage;
    int REQUEST_CODE_CAMERA = 123;
    List<SelfieImage> images;
    ImageAdapter imageAdapter;
    ImageDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // su dung room database de luu anh da chup va lay anh trong database
        db = Room.databaseBuilder(getApplicationContext(),
                ImageDatabase.class, "Database").allowMainThreadQueries().build();
        ImageDAO imageDAO = db.imageDAO();

        lvImage = findViewById(R.id.listImage);
        images = new ArrayList<>();
        images = imageDAO.getAll();

        imageAdapter = new ImageAdapter(this, R.layout.image_row, images);
        lvImage.setAdapter(imageAdapter);

        // notification
        NotificationChannel();
        // Announcement at 00:24p every day
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 46);
        calendar.set(Calendar.SECOND, 00);

        if(Calendar.getInstance().after(calendar)){
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        Intent intent = new Intent(MainActivity.this, MemoBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_camera, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //mở camera
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
        return super.onOptionsItemSelected(item);
    }

    // lấy hình chụp từ camera
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data"); // default
            SelfieImage selfieImage = new SelfieImage();

            if(bitmap != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    selfieImage.setName("image " + (LocalDateTime.now()));
                }
                selfieImage.setImage(convertImage2ByteArray(bitmap));
                images.add(selfieImage);
                addImage(selfieImage);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

     private void addImage(SelfieImage selfieImage){
         ImageDAO imageDAO = db.imageDAO();
         imageDAO.insertAll(selfieImage);
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

    private void NotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Notification", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if(notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}