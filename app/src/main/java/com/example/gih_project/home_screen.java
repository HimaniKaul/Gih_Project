package com.example.gih_project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class home_screen extends AppCompatActivity {
    private static final int PermissionRequest = 200;
    public static final int REQUEST_CODE= 0;
    Button scanbtn;
    TextView result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        scanbtn=findViewById(R.id.scan_button);
        result=findViewById(R.id.result);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, PermissionRequest);
        }
        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(home_screen.this,MainActivity.class);
                startActivityForResult(i,REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        if(requestCode==REQUEST_CODE ){
            if(resultCode== RESULT_OK){
            if(data!=null)
            {   final Barcode barcode=data.getParcelableExtra("barcode");
                 result.post(new Runnable() {
                     @Override
                     public void run() {
                         result.setText(barcode.displayValue);
                     }
                 }); }
           // else {result.setText("No Barcode Found");}

            }}}
}