package com.example.appdoan_vutruonggiang.view.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appdoan_vutruonggiang.adapter.AdapterRecyleViewBinhLuan;
import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.BinhLuan;
import com.example.appdoan_vutruonggiang.modle.NhaHang;
import com.example.appdoan_vutruonggiang.ggmap.MapsActivity;
import com.example.appdoan_vutruonggiang.presenter.Food;
import com.example.appdoan_vutruonggiang.presenter.ProcessFood;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DetailActivity extends Activity {
    TextView tvNameItem,tvPriceItem,tvDetailItem,xemBinhLuan,tvnameNhaHang,Webview_NhaHang,tvAddressNhaHang,tv_Detail_OC,tv_Detail_mo_dong;
    Button but_Dang_binhluan,but_order_item;
    ImageView but_back,imAnhDetailItem,imAnhNhaHang,but_map;
    RatingBar ReviewItem,ratingNhaHang;
    RecyclerView dataBinhLuan;
    EditText input_comment;
    WebView webView;
    String sdt="",hoTen="",diaChi="",email="",pass="";
    //String urlStr="https://demo1859626.mockable.io/NhaHang";
    String url="",name="",detail="",id="",idNhaHang="",type="";
    float price=0,rating=5;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<BinhLuan> binhLuanList;
    List<NhaHang> nhaHangList;
    String urlWeb="https://www.facebook.com/vutruonggiang1912/";
    long V,V1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);
        anhXa();

        Bundle bundle=getIntent().getExtras();

        sdt=sdt+bundle.getString("phoneNumber");
        hoTen=hoTen+bundle.getString("hoten");
        diaChi=diaChi+bundle.getString("diachi");
        email=email+bundle.getString("gmail");
        pass=pass+bundle.getString("pass");

        url=url+bundle.getString("url");
        name=name+bundle.getString("food_name");
        detail=detail+bundle.getString("food_detail");
        price=bundle.getFloat("food_price");
        rating=bundle.getFloat("food_rating");
        id=id+bundle.getString("food_id");
        idNhaHang=idNhaHang+bundle.getString("food_idnhahang");
        type=type+bundle.getString("food_type");

        Glide.with(DetailActivity.this).load(url).into(imAnhDetailItem);
        tvNameItem.setText(name);
        tvPriceItem.setText(price+" VND");
        tvDetailItem.setText(detail);
        ReviewItem.setRating(rating);
        //new data().execute();

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(DetailActivity.this,RecyclerView.VERTICAL,false);
        dataBinhLuan.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration decoration=new DividerItemDecoration(DetailActivity.this,DividerItemDecoration.VERTICAL);
        dataBinhLuan.addItemDecoration(decoration);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

        databaseReference.child("comments").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binhLuanList=new ArrayList<>();
                Iterable<DataSnapshot> dataSnapshotIterable= snapshot.getChildren();
                for (DataSnapshot data: dataSnapshotIterable) {
                    BinhLuan binhLuan=data.getValue(BinhLuan.class);
                    binhLuanList.add(binhLuan);
                }
                AdapterRecyleViewBinhLuan adapterRecyleViewBinhLuan=new AdapterRecyleViewBinhLuan(binhLuanList,DetailActivity.this,sdt,id);
                dataBinhLuan.setAdapter(adapterRecyleViewBinhLuan);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        but_Dang_binhluan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input_comment.getText().toString().trim().equals(""))
                    return;
                BinhLuan binhLuan = new BinhLuan(hoTen, input_comment.getText().toString(),sdt,Long.valueOf(binhLuanList.size()+1));
                binhLuanList.add(binhLuan);
                databaseReference.child("comments").child(id).child(binhLuanList.size() + "").setValue(binhLuan);
                AdapterRecyleViewBinhLuan adapterRecyleViewBinhLuan = new AdapterRecyleViewBinhLuan(binhLuanList,DetailActivity.this,sdt,id);
                dataBinhLuan.setAdapter(adapterRecyleViewBinhLuan);
                input_comment.setText("");
            }
        });

        databaseReference.child("nhaHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nhaHangList=new ArrayList<>();
                Iterable<DataSnapshot> dataSnapshotIterable=snapshot.getChildren();
                for(DataSnapshot data:dataSnapshotIterable){
                    NhaHang nhaHang=data.getValue(NhaHang.class);
                    nhaHangList.add(nhaHang);
                    if(nhaHang.getId().equals(idNhaHang)){
                        V=nhaHang.getV();
                        V1=nhaHang.getV1();
                        Glide.with(DetailActivity.this).load(nhaHang.getUrl()).into(imAnhNhaHang);
                        tvnameNhaHang.setText(nhaHang.getName());
                        tvAddressNhaHang.setText(nhaHang.getAddress());
                        ratingNhaHang.setRating(Float.parseFloat(String.valueOf(nhaHang.getRating())));
                        tv_Detail_OC.setText(nhaHang.getOpen()+"-"+nhaHang.getClose());
                        Calendar calendar=Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
                        String gioHienTai=simpleDateFormat.format(calendar.getTime());
                        try{
                            Date hienTai= simpleDateFormat.parse(gioHienTai);
                            Date mo=simpleDateFormat.parse(nhaHang.getOpen());
                            Date dong=simpleDateFormat.parse(nhaHang.getClose());
                            if(hienTai.after(mo)&&hienTai.before(dong)){
                                tv_Detail_mo_dong.setText("Đang Mở Cửa");
                            }else{
                                tv_Detail_mo_dong.setText("Đóng Cửa");
                                tv_Detail_mo_dong.setTextColor(Color.BLACK);
                            }
                        }catch (Exception e){

                        }
                        but_map.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(DetailActivity.this, MapsActivity.class);
                                Bundle bundle1=new Bundle();

                                bundle1.putString("phoneNumber",sdt);
                                bundle1.putString("hoten",hoTen);
                                bundle1.putString("diachi",diaChi);
                                bundle1.putString("gmail",email);
                                bundle1.putString("pass",pass);

                                bundle1.putLong("v",V);
                                bundle1.putLong("v1",V1);
                                bundle1.putString("name_NH",nhaHang.getName());

                                bundle1.putString("url",url);
                                bundle1.putString("food_name",name);
                                bundle1.putFloat("food_price",price);
                                bundle1.putFloat("food_rating",rating);
                                bundle1.putString("food_detail",detail);
                                bundle1.putString("food_idnhahang",idNhaHang);
                                bundle1.putString("food_id",id);
                                bundle1.putString("food_type",type);

                                intent.putExtras(bundle1);
                                startActivity(intent);
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Food food=new Food(id,url,name,detail,idNhaHang,type,price,rating);
        but_order_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProcessFood.getChoose(DetailActivity.this,food,sdt);
            }
        });

        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bundle bundle=getIntent().getExtras();
                Intent intent=new Intent(DetailActivity.this, MainActivity.class);
                Bundle bundle1=new Bundle();
                bundle1.putString("phoneNumber",sdt);
                bundle1.putString("hoten",hoTen);
                bundle1.putString("diachi",diaChi);
                bundle1.putString("gmail",email);
                bundle1.putString("pass",pass);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
        Webview_NhaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(urlWeb);
            }
        });
    }
    public void anhXa(){
        tvNameItem=findViewById(R.id.tvNameItem);
        tvPriceItem=findViewById(R.id.tvPriceItem);
        tvDetailItem=findViewById(R.id.tvDetailItem);
        xemBinhLuan=findViewById(R.id.xemBinhLuan);
        tvnameNhaHang=findViewById(R.id.tvnameNhaHang);
        Webview_NhaHang=findViewById(R.id.Webview_NhaHang);
        tvAddressNhaHang=findViewById(R.id.tvAddressNhaHang);
        tv_Detail_OC=findViewById(R.id.tv_Detail_OC);
        tv_Detail_mo_dong=findViewById(R.id.tv_Detail_mo_dong);
        but_Dang_binhluan=findViewById(R.id.but_Dang_binhluan);
        but_order_item=findViewById(R.id.but_order_item);
        but_back=findViewById(R.id.but_back);
        imAnhDetailItem=findViewById(R.id.imAnhDetailItem);
        imAnhNhaHang=findViewById(R.id.imAnhNhaHang);
        ReviewItem=findViewById(R.id.ReviewItem);
        ratingNhaHang=findViewById(R.id.ratingNhaHang);
        dataBinhLuan=findViewById(R.id.dataBinhLuan);
        input_comment=findViewById(R.id.input_comment);
        webView=findViewById(R.id.Webview);
        but_map=findViewById(R.id.but_map);
    }
//    class data extends AsyncTask<Void,Void,String>{
//        String result="";
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            try {
//                URL url=new URL(urlStr);
//                URLConnection conn=url.openConnection();
//                InputStream in=conn.getInputStream();
//                int byteChar;
//                while ((byteChar=in.read())!=-1){
//                    result +=(char) byteChar;
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            try {
//                JSONArray jsonArray=new JSONArray(result);
//                for(int i=0;i<jsonArray.length();i++){
//                    JSONObject jsonObject=jsonArray.getJSONObject(i);
//                    String idNH=jsonObject.getString("id");
//                    if(idNH.equals(idNhaHang)){
//                        String ten=jsonObject.getString("name");
//                        String diaChi=jsonObject.getString("address");
//                        String urlNH=jsonObject.getString("url");
//                        String rating=jsonObject.getString("rating");
//                        String open=jsonObject.getString("open");
//                        String close=jsonObject.getString("close");
//                        Glide.with(DetailActivity.this).load(urlNH).into(binding.imAnhNhaHang);
//                        binding.tvnameNhaHang.setText(ten);
//                        binding.tvAddressNhaHang.setText(diaChi);
//                        binding.ratingNhaHang.setRating(Float.parseFloat(rating));
//                        binding.tvDetailOC.setText(open+"-"+close);
//                        Calendar calendar=Calendar.getInstance();
//                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
//                        String gioHienTai=simpleDateFormat.format(calendar.getTime());
//                        try{
//                            Date hienTai= simpleDateFormat.parse(gioHienTai);
//                            Date mo=simpleDateFormat.parse(open);
//                            Date dong=simpleDateFormat.parse(close);
//                            if(hienTai.after(mo)&&hienTai.before(dong)){
//                                binding.tvDetailMoDong.setText("Đang Mở Cửa");
//                            }else{
//                                binding.tvDetailMoDong.setText("Đóng Cửa");
//                                binding.tvDetailMoDong.setTextColor(Color.BLACK);
//                            }
//                        }catch (Exception e){
//
//                        }
//                        return;
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}