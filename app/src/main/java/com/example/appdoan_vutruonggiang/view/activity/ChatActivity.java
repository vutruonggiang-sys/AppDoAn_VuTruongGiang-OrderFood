package com.example.appdoan_vutruonggiang.view.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.appdoan_vutruonggiang.adapter.AdapterChat;
import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.Chat;
import com.example.appdoan_vutruonggiang.presenter.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatActivity extends Activity{
    ImageView back_chat;
    EditText chat_content;
    Button but_send;
    RecyclerView dataChat;
    List<Chat> chatList;
    String sdt="",hoTen="",pass="";
    List<Food> foodList;
    AdapterChat adapterChat;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    int d=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);
        anhXa();
        Bundle bundle=this.getIntent().getExtras();
        sdt=sdt+bundle.getString("phoneNumber");
        hoTen=hoTen+bundle.getString("hoten");
        pass=pass+bundle.get("pass");
        foodList=bundle.getParcelableArrayList("list");

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(ChatActivity.this,RecyclerView.VERTICAL,false);
        dataChat.setLayoutManager(layoutManager);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("chat").child(sdt);
        Query query=databaseReference.orderByChild("id");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    chatList=new ArrayList<>();
                    Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                    for (DataSnapshot data : dataSnapshotIterable) {
                        Chat chat = data.getValue(Chat.class);
                        chatList.add(chat);
                    }
                    adapterChat = new AdapterChat(chatList);
                    dataChat.setAdapter(adapterChat);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        but_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(Long.valueOf(chatList.size()));
            }
        });
        chat_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkKeyboard();
            }
        });
        back_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), AccountActivity.class);
                ArrayList<Food> listSearch= (ArrayList<Food>) foodList;
                Bundle bundle1=new Bundle();
                bundle1.putString("phoneNumber",sdt);
                bundle1.putString("hoten",hoTen);
                bundle1.putString("pass",pass);
                bundle1.putParcelableArrayList("list",listSearch);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }
    private void sendData(long d){
        String content=chat_content.getText().toString();
        if(content.isEmpty()) return;
        Calendar calendar=Calendar.getInstance();
        DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String time=dateFormat.format(calendar.getTime());
        Chat chat=new Chat(d,content,time);
        chatList.add(chat);
        databaseReference.child(d+"").setValue(chat);
//        adapterChat.notifyDataSetChanged();
//        binding.dataChat.scrollToPosition(chatList.size()-1);
        chat_content.setText("");
    }
    private void checkKeyboard(){
        final View root=findViewById(R.id.root);
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r=new Rect();
                root.getWindowVisibleDisplayFrame(r);
                int height=root.getRootView().getHeight()-r.height();
                if(height>0.25*root.getRootView().getHeight()){
                    if(chatList.size()>0){
                        dataChat.scrollToPosition(chatList.size()-1);
                        root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }
    public void anhXa(){
        back_chat=findViewById(R.id.back_chat);
        chat_content=findViewById(R.id.chat_content);
        but_send=findViewById(R.id.but_send);
        dataChat=findViewById(R.id.dataChat);
    }
}