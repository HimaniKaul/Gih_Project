package com.example.gih_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class virtual_cart extends AppCompatActivity {
    private RecyclerView recyclerView;
    DatabaseReference databaseReference;
    private static int count=0;
    Toolbar toolbar;
    id_for_firebase items[] = new id_for_firebase[100];
    int i = 0;
    static float total = 0;
    static int flag;
    public virtual_cart(){ }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_cart);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Products");
        databaseReference.keepSynced(true);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<id_for_firebase, ProductViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<id_for_firebase, ProductViewHolder>
                (id_for_firebase.class, R.layout.virtual_cart_design, ProductViewHolder.class, databaseReference) {
            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, id_for_firebase model, int position) {
                viewHolder.setTitle(model.get_product_title());
                viewHolder.setPrice(model.get_Price());
                viewHolder.setImage(getApplicationContext(), model.getImage_uri());
                count++;
                items[i] = model;
                i++;
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        View mview;

        public ProductViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
            ImageView i=mview.findViewById(R.id.del);
            final RelativeLayout r=mview.findViewById(R.id.r);
            i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    r.setVisibility(View.GONE);
                }
            });
        }

        public void setTitle(String cont) {
            TextView post_cont = mview.findViewById(R.id.product_title);
            post_cont.setText(cont);
        }

        public void setPrice(String cont) {
            TextView post = mview.findViewById(R.id.product_price);
            post.setText(cont);
        }

        public void setImage(Context ctx, String image) {
            ImageView post_Image = mview.findViewById(R.id.product_image);
            Picasso.with(ctx).load(image).into(post_Image);
        }


    }

    //setting menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.payment_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pay: {
                for(int j=0; j<i; j++){
                    total += Integer.parseInt(items[j].get_Price());
                }
                System.out.println("Total Price = " + total);
                System.out.println("----------------------------------------------");
                Intent i= new Intent(virtual_cart.this,BillCheckout.class);
                startActivity(i);
                break;}
                case R.id.del:
                    databaseReference.removeValue();
                    flag = 0;
                    System.out.println("-----------------------------------------"+flag);
                    break;
        }
        return super.onOptionsItemSelected(item);
        }

        public static float total(){
            return  total;
        }

        public static int flag(){
       return  flag;
    }

//    public static int get_no_items() {
//        return count;
//    }
}