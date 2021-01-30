package com.example.soobook.ui.MyLib;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.soobook.BookDetailView;
import com.example.soobook.My_lib_add;
import com.example.soobook.R;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soobook.Book;
import com.example.soobook.CustomBookAdapter;
import com.example.soobook.Test;
import com.example.soobook.ui.FindLib.DetailView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyLibFragment extends Fragment{

    private RecyclerView.Adapter adapter;
    private ArrayList<Book> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_lib, container, false);
        String user_email = getArguments().getString("user_email");
        String user_UID = getArguments().getString("user_UID");

        Log.e(this.getClass().getName(), user_email + ", " + user_UID);

        ImageButton add_btn = root.findViewById(R.id.add_btn);
        add_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), My_lib_add.class);
            intent.putExtra("user_email", user_email);
            intent.putExtra("user_UID", user_UID);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        ImageButton search_btn = root.findViewById(R.id.search_btn);
        search_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Test.class);
            intent.putExtra("user_email", user_email);
            intent.putExtra("user_UID", user_UID);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview); // 아디 연결
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // User 객체를 담을 어레이 리스트 (어댑터쪽으로)



        FirebaseDatabase database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        DatabaseReference databaseReference = database.getReference("Book/"+user_UID+"/"); // DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    Book book = snapshot.getValue(Book.class); // 만들어뒀던 User 객체에 데이터를 담는다.
                    arrayList.add(book); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                    String  rec= book.getRec();
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }

            });



        adapter = new CustomBookAdapter(arrayList, getActivity());
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결

        return root;
    }


}