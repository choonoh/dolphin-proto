package com.example.soobook;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomBookAdapter extends RecyclerView.Adapter<CustomBookAdapter.CustomViewHolder> {
    FirebaseDatabase database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
    private ArrayList<Book> arrayList;
    private Context context;

    public CustomBookAdapter(ArrayList<Book> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_book, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);


        return holder;


    }



    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getRecImage())
                .into(holder.iv_rec);
        holder.tv_owner.setText(arrayList.get(position).getOwner());
        holder.tv_auth.setText(arrayList.get(position).getAuth());
        holder.tv_title.setText(arrayList.get(position).gettitle());

        holder.itemView.setOnClickListener(v ->  {

            String title = arrayList.get(position).gettitle();
            String Isbn = arrayList.get(position).getIsbn();
            String Auth = arrayList.get(position).getAuth();
            String Rec = arrayList.get(position).getRec();
            String Pub = arrayList.get(position).getPub();
            String Star = arrayList.get(position).getStar();
            String Owner = arrayList.get(position).getOwner();
            Intent intent = new Intent(context,BookDetailView.class);
            intent.putExtra("title", title);
            intent.putExtra("isbn",Isbn);
            intent.putExtra("auth", Auth);
            intent.putExtra("rec",Rec);
            intent.putExtra("pub", Pub);
            intent.putExtra("star",Star);
            intent.putExtra("owner",Owner.toString());
            context.startActivity(intent);

        });


    }
    @Override
    public int getItemCount() {
        // 삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_rec;
        TextView tv_owner;
        TextView tv_auth;
        TextView tv_title;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_rec = itemView.findViewById(R.id.iv_rec);
            this.tv_owner = itemView.findViewById(R.id.tv_owner);
            this.tv_auth = itemView.findViewById(R.id.tv_auth);
            this.tv_title = itemView.findViewById(R.id.tv_title);

        }

    }

}
