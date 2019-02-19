package com.example.gih_project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class home_screen extends AppCompatActivity {
    private static final int PermissionRequest = 200;
    public static final int REQUEST_CODE= 0;
    Button scanbtn;
    Button addbtn;
    TextView result;
    private Toolbar myToolbar;
    String products;
    DatabaseReference databaseReference;
     TextView textCartItemCount;
    int mCartItemCount=0;
    Bundle bundle;
    String message;
    String username;
    TextView name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        scanbtn=findViewById(R.id.scan_button);
        addbtn=findViewById(R.id.add_to_cart);
        name=findViewById(R.id.name);
        databaseReference=FirebaseDatabase.getInstance().getReference("Products");
        //receiving the string
        bundle= getIntent().getExtras();
        message= bundle.getString("message");
        username=bundle.getString("user_name");

        //setting toolbar
        myToolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        //generate the barcode
        ImageView imageView=findViewById(R.id.barcode_id);
        // Whatever you need to encode in the QR code
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            name.setText(username);
            BitMatrix bitMatrix = multiFormatWriter.encode(message, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    public void addProduct() {
        String product_name=result.getText().toString().trim();
        if(!TextUtils.isEmpty(product_name))
        {   String id=databaseReference.push().getKey();
            id_for_firebase i= new id_for_firebase(id, product_name);
            databaseReference.child(id).setValue(i);
            Toast.makeText(home_screen.this,"Added To Cart", Toast.LENGTH_SHORT).show();
            mCartItemCount++;
            setupBadge();
        }
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
                         products=barcode.displayValue;

                     }
                 }); }
            }}}

            //setting menu
            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                getMenuInflater().inflate(R.menu.basket, menu);
                final MenuItem menuItem = menu.findItem(R.id.action_cart);
                View actionView = MenuItemCompat.getActionView(menuItem);
                textCartItemCount = actionView.findViewById(R.id.cart_badge);
                actionView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onOptionsItemSelected(menuItem);
                    }
                });

                return true;
            }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart: {
                Intent i =new Intent(home_screen.this,virtual_cart.class);
                startActivity(i);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupBadge() {
        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

}