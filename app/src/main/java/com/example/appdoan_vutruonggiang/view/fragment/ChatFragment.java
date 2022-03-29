package com.example.appdoan_vutruonggiang.view.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.adapter.AdapterChat;
import com.example.appdoan_vutruonggiang.modle.Chat;
import com.example.appdoan_vutruonggiang.presenter.Food;
import com.example.appdoan_vutruonggiang.view.activity.HomePageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class ChatFragment extends Fragment {

    private View view;
    ImageView back_chat;
    EditText chat_content;
    Button but_send;
    RecyclerView dataChat;
    List<Chat> chatList;
    List<Food> foodList;
    AdapterChat adapterChat;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user;
    String email="";
    HomePageActivity homePageActivity;
    public static Fragment newInstance() {

        Bundle args = new Bundle();

        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.chat_fragment,container,false);
        anhXa();
        user= FirebaseAuth.getInstance().getCurrentUser();
        String[] arrayEmail=user.getEmail().split("@");
        email=email+arrayEmail[0];

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(homePageActivity,RecyclerView.VERTICAL,false);
        dataChat.setLayoutManager(layoutManager);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("chat").child(email);
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
                homePageActivity.getFragment(AccountFragment.newInstance());
                homePageActivity.getBottomNavigationView().setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    private void sendData(long d){
        String content=chat_content.getText().toString();
        if(content.isEmpty()) return;
        Calendar calendar=Calendar.getInstance();
        DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String time=dateFormat.format(calendar.getTime());
        Chat chat=new Chat(d,content,time,"");
        chatList.add(chat);
        databaseReference.child(d+"").setValue(chat);
        chat_content.setText("");
    }
    private void checkKeyboard(){
        final View root=view.findViewById(R.id.root);
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
        homePageActivity= (HomePageActivity) getActivity();
        back_chat=view.findViewById(R.id.back_chat);
        chat_content=view.findViewById(R.id.chat_content);
        but_send=view.findViewById(R.id.but_send);
        dataChat=view.findViewById(R.id.dataChat);
    }
}
