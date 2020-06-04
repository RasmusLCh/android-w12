package com.example.imagerefac;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;


import com.example.imagerefac.Controller.ImageController;
import com.example.imagerefac.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    public ImageView imageView;
    private ImageController ic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        ic = new ImageController(this);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
    }

    public void photoRollBtnPressed(View view){
        // 1. make an intent
        // start activity (which will make Android system launch one activity which CAN
        // handle this "request"
        Intent intent = new Intent(Intent.ACTION_PICK);  //
        intent.setType("image/*");
        startActivityForResult(intent, 0); // 0 means photoroll, 1 means camera
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // 1. check if result is OK. If not, then return
        if(resultCode != -1) return; // -1 indicates OK
        ic.handleImageReturn(requestCode, intent);
    }

    public void cameraBtnPressed(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  // we ask Android for something different
        startActivityForResult(intent, 1);
    }
    public void saveImage(View view){
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"Title_test","Description_test");
    }

    public void resizeImage (View view) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap reSize = Bitmap.createScaledBitmap(bitmap, 600, 500, true);
        imageView.setImageBitmap(reSize);
    }


}
