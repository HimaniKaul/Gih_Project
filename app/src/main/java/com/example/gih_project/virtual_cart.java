package com.example.gih_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class virtual_cart extends AppCompatActivity {
    private RecyclerView recyclerView;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_cart);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Products");
        databaseReference.keepSynced(true);
        recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<id_for_firebase,ProductViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<id_for_firebase, ProductViewHolder>
                (id_for_firebase.class,R.layout.virtual_cart_design,ProductViewHolder.class,databaseReference) {
            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, id_for_firebase model, int position) {
                  viewHolder.setTitle(model.get_product_title());
//                viewHolder.setContent(model.getBlog_content());
//                viewHolder.setImage(getApplicationContext(),model.getImage_uri());
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    public static class ProductViewHolder extends RecyclerView.ViewHolder
    {
        View mview;
        public ProductViewHolder(View itemView)
        {
            super(itemView);
            mview=itemView;
        }

        public void setTitle(String Title)
        {
            TextView post_title=mview.findViewById(R.id.product_title);
            post_title.setText(Title);
        }
//        public void setContent(String cont)
//        {
//            TextView post_cont=mview.findViewById(R.id.blog);
//            post_cont.setText(cont);
//        }

    }


}