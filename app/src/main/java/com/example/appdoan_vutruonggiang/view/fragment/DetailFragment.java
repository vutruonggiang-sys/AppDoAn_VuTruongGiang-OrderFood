package com.example.appdoan_vutruonggiang.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.adapter.AdapterRecyleViewBinhLuan;
import com.example.appdoan_vutruonggiang.modle.BinhLuan;
import com.example.appdoan_vutruonggiang.modle.NhaHang;
import com.example.appdoan_vutruonggiang.presenter.Food;
import com.example.appdoan_vutruonggiang.presenter.ProcessFood;
import com.example.appdoan_vutruonggiang.view.activity.HomePageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailFragment extends Fragment {
    private View view;
    TextView tvNameItem, tvPriceItem, tvDetailItem, tvnameNhaHang, Webview_NhaHang,
            tvAddressNhaHang, tv_Detail_OC, tv_Detail_mo_dong;
    Button but_Dang_binhluan, but_order_item;
    ImageView but_back, imAnhNhaHang, but_map, imgDetailFullWindow, imgExit;
    CircleImageView imAnhDetailItem;
    RatingBar ReviewItem;
    RecyclerView dataBinhLuan;
    EditText input_comment;
    WebView webView;
    String url = "", name = "", detail = "", id = "", idNhaHang = "", type = "";
    float price = 0, rating = 5;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<BinhLuan> binhLuanList;
    List<NhaHang> nhaHangList;
    String urlWeb = "https://www.facebook.com/vutruonggiang1912/";
    long V, V1;
    ProcessFood processFood;
    FirebaseUser user;
    String email = "";
    HomePageActivity homePageActivity;
    public static Fragment newInstance() {

        Bundle args = new Bundle();

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.detail_fragment,container,false);
        init();
        getData();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String[] arrayEmail = user.getEmail().split("@");
        email = email + arrayEmail[0];
        Glide.with(homePageActivity).load(url).into(imAnhDetailItem);
        tvNameItem.setText(name);
        tvPriceItem.setText(customFormat("###,###",price) + " VND");
        tvDetailItem.setText(detail);
        ReviewItem.setRating(rating);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(homePageActivity, RecyclerView.VERTICAL, false);
        dataBinhLuan.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(homePageActivity, DividerItemDecoration.VERTICAL);
        dataBinhLuan.addItemDecoration(decoration);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("comments").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binhLuanList = new ArrayList<>();
                Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                for (DataSnapshot data : dataSnapshotIterable) {
                    BinhLuan binhLuan = data.getValue(BinhLuan.class);
                    binhLuanList.add(binhLuan);
                }
                AdapterRecyleViewBinhLuan adapterRecyleViewBinhLuan = new AdapterRecyleViewBinhLuan(binhLuanList, homePageActivity, email, id);
                dataBinhLuan.setAdapter(adapterRecyleViewBinhLuan);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        but_Dang_binhluan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_comment.getText().toString().trim().equals(""))
                    return;
                BinhLuan binhLuan = new BinhLuan(user.getEmail(), input_comment.getText().toString(), email, Long.valueOf(binhLuanList.size() + 1));
                binhLuanList.add(binhLuan);
                databaseReference.child("comments").child(id).child(binhLuanList.size() + "").setValue(binhLuan);
                AdapterRecyleViewBinhLuan adapterRecyleViewBinhLuan = new AdapterRecyleViewBinhLuan(binhLuanList, homePageActivity, email, id);
                dataBinhLuan.setAdapter(adapterRecyleViewBinhLuan);
                input_comment.setText("");
            }
        });

        databaseReference.child("nhaHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nhaHangList = new ArrayList<>();
                Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                for (DataSnapshot data : dataSnapshotIterable) {
                    NhaHang nhaHang = data.getValue(NhaHang.class);
                    nhaHangList.add(nhaHang);
                    if (nhaHang.getId().equals(idNhaHang)) {
                        V = nhaHang.getV();
                        V1 = nhaHang.getV1();
                        Glide.with(homePageActivity).load(nhaHang.getUrl()).into(imAnhNhaHang);
                        tvnameNhaHang.setText(nhaHang.getName());
                        tvAddressNhaHang.setText(nhaHang.getAddress());
                        tv_Detail_OC.setText(nhaHang.getOpen() + "-" + nhaHang.getClose());
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        String gioHienTai = simpleDateFormat.format(calendar.getTime());
                        try {
                            Date hienTai = simpleDateFormat.parse(gioHienTai);
                            Date mo = simpleDateFormat.parse(nhaHang.getOpen());
                            Date dong = simpleDateFormat.parse(nhaHang.getClose());
                            if (hienTai.after(mo) && hienTai.before(dong)) {
                                tv_Detail_mo_dong.setText("Đang Mở Cửa");
                            } else {
                                tv_Detail_mo_dong.setText("Đóng Cửa");
                                tv_Detail_mo_dong.setTextColor(Color.BLACK);
                            }
                        } catch (Exception e) {

                        }
                        but_map.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Food food = new Food(id, url, name, detail, idNhaHang, type, price, rating);
        but_order_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processFood.getChoose(homePageActivity, food, email);
            }
        });

        imAnhDetailItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgDetailFullWindow.setVisibility(View.VISIBLE);
                Glide.with(homePageActivity).load(url).into(imgDetailFullWindow);
                imgExit.setVisibility(View.VISIBLE);
                but_order_item.setVisibility(View.GONE);
            }
        });

        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgDetailFullWindow.setVisibility(View.GONE);
                imgExit.setVisibility(View.GONE);
                but_order_item.setVisibility(View.VISIBLE);
            }
        });

        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePageActivity.getFragment(HomePageFragment.newInstance());
                homePageActivity.getBottomNavigationView().setVisibility(View.VISIBLE);
            }
        });
        Webview_NhaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(urlWeb);
            }
        });
        return view;
    }

    public void getData(){
        Bundle bundle=getArguments();
        if(bundle!=null){
            url = url + bundle.getString("url");
            name = name + bundle.getString("food_name");
            detail = detail + bundle.getString("food_detail");
            price = bundle.getFloat("food_price");
            rating = bundle.getFloat("food_rating");
            id = id + bundle.getString("food_id");
            idNhaHang = idNhaHang + bundle.getString("food_idnhahang");
            type = type + bundle.getString("food_type");
        }
    }

    public String customFormat(String pattern, float value ) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(value);
        return  output;
    }

    public void init() {
        homePageActivity= (HomePageActivity) getActivity();
        tvNameItem = view.findViewById(R.id.tvNameItem);
        tvPriceItem = view.findViewById(R.id.tvPriceItem);
        tvDetailItem = view.findViewById(R.id.tvDetailItem);
        tvnameNhaHang = view.findViewById(R.id.tvnameNhaHang);
        Webview_NhaHang = view.findViewById(R.id.Webview_NhaHang);
        tvAddressNhaHang = view.findViewById(R.id.tvAddressNhaHang);
        tv_Detail_OC = view.findViewById(R.id.tv_Detail_OC);
        tv_Detail_mo_dong = view.findViewById(R.id.tv_Detail_mo_dong);
        but_Dang_binhluan = view.findViewById(R.id.but_Dang_binhluan);
        but_order_item = view.findViewById(R.id.but_order_item);
        but_back = view.findViewById(R.id.but_back);
        imAnhDetailItem = view.findViewById(R.id.imAnhDetailItem);
        imAnhNhaHang = view.findViewById(R.id.imAnhNhaHang);
        ReviewItem = view.findViewById(R.id.ReviewItem);
        dataBinhLuan = view.findViewById(R.id.dataBinhLuan);
        input_comment = view.findViewById(R.id.input_comment);
        webView = view.findViewById(R.id.Webview);
        but_map = view.findViewById(R.id.butMap);
        imgDetailFullWindow=view.findViewById(R.id.imgDetailFullWindow);
        imgExit=view.findViewById(R.id.imgExitNext);
        processFood = new ProcessFood();
    }
}
