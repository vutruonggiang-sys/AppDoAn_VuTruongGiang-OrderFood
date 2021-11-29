package com.example.appdoan_vutruonggiang.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoan_vutruonggiang.UI.BinhLuan;
import com.example.appdoan_vutruonggiang.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class AdapterRecyleViewBinhLuan extends RecyclerView.Adapter<AdapterRecyleViewBinhLuan.ViewHoder> {
    List<BinhLuan> binhLuanList;
    Context context;
    String sdt,id;

    public AdapterRecyleViewBinhLuan(List<BinhLuan> binhLuanList,Context context,String sdt,String id) {
        this.binhLuanList = binhLuanList;
        this.context=context;
        this.sdt=sdt;
        this.id=id;
    }

    @NonNull
    @Override
    public AdapterRecyleViewBinhLuan.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_binhluan,parent,false);
        ViewHoder viewHoder=new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyleViewBinhLuan.ViewHoder holder, int position) {
        BinhLuan binhLuan=binhLuanList.get(position);
        holder.tvName.setText(binhLuan.getName());
        holder.tvNoiDung.setText(binhLuan.getNoidung());
        if(binhLuan.getSdt().equals(sdt)) {
            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
            DatabaseReference databaseReference=firebaseDatabase.getReference().child("comments").child(id);
            holder.i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.i.setVisibility(View.GONE);
                    holder.edit.setVisibility(View.VISIBLE);
                    holder.delete.setVisibility(View.VISIBLE);
                    holder.delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            databaseReference.child(binhLuan.getD()+"").removeValue();
                        }
                    });
                    holder.edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            holder.re.setVisibility(View.VISIBLE);
                            holder.ed_Edit_Comment.setText(binhLuan.getNoidung());
                            holder.but_Edit_Comment.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    /*
                                    Handler handler=new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            HashMap<String,Object> valuesObject=new HashMap<>();
                                            valuesObject.put("d",binhLuan.getD());
                                            valuesObject.put("name",binhLuan.getName());
                                            valuesObject.put("noidung",holder.ed_Edit_Comment.getText());
                                            valuesObject.put("sdt",binhLuan.getSdt());
                                            HashMap<String,Object> keyObject=new HashMap<>();
                                            keyObject.put(binhLuan.getD()+"",valuesObject);
                                            databaseReference.updateChildren(keyObject);
                                        }
                                    },3000);*/
                                    BinhLuan binhLuan1=new BinhLuan(binhLuan.getName(),holder.ed_Edit_Comment.getText().toString(),
                                            binhLuan.getSdt(),binhLuan.getD());
                                    databaseReference.child(binhLuan.getD()+"").removeValue();
                                    databaseReference.child(binhLuan.getD()+"").setValue(binhLuan1);
                                    holder.tvNoiDung.setText(holder.ed_Edit_Comment.getText());
                                    holder.re.setVisibility(View.GONE);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(binhLuanList!=null) {
            return binhLuanList.size();
        }else{
            return 0;
        }
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView tvName,tvNoiDung,edit,delete;
        ImageView i;
        RelativeLayout re;
        EditText ed_Edit_Comment;
        Button but_Edit_Comment;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_Name_binhluan);
            tvNoiDung=itemView.findViewById(R.id.tv_noidung_binhluan);
            i=itemView.findViewById(R.id.But_Edit_Delete_Comment);
            re=itemView.findViewById(R.id.Edit_Comment);
            ed_Edit_Comment=itemView.findViewById(R.id.ed_Edit_Comment);
            but_Edit_Comment=itemView.findViewById(R.id.but_Edit_Comment);
            edit=itemView.findViewById(R.id.edit_cmt);
            delete=itemView.findViewById(R.id.delete_cmt);
        }
    }
}
