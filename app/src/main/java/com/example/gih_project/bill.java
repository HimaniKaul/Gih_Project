package com.example.gih_project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class bill extends virtual_cart{
    float total;
    public bill(float total){
        this.total = total;
        System.out.println("total at constructor: " + total);
        checkout();
    }

    public static void main(String[] args) {

    }

    public void checkout(){
//        Intent i = new Intent(bill.this, BillCheckout.class); // use bill object
//        startActivity(i);
    }
}
