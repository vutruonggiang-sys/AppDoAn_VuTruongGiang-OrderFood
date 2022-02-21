package com.example.appdoan_vutruonggiang.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoan_vutruonggiang.modle.Chat;
import com.example.appdoan_vutruonggiang.R;

import java.util.List;

public class AdapterChat  extends RecyclerView.Adapter<AdapterChat.ViewHoder> {
    List<Chat> chatList;

    public AdapterChat(List<Chat> chatList) {
        this.chatList = chatList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterChat.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_chat,parent,false);
        ViewHoder viewHoder=new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChat.ViewHoder holder, int position) {
        Chat chat=chatList.get(position);
        if(chat==null)
            return;
        holder.tvContent.setText(chat.getContent());
        holder.tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvTime.setVisibility(View.VISIBLE);
                holder.tvTime.setText(chat.getTime());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(chatList==null)
        return 0;
        else
            return chatList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView tvContent,tvTime;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            tvContent=itemView.findViewById(R.id.tv_chat_content);
            tvTime=itemView.findViewById(R.id.tv_time_chat);
        }
    }
}
