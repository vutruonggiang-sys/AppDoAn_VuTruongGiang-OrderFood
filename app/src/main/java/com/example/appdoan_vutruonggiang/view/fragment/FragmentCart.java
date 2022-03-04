package com.example.appdoan_vutruonggiang.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoan_vutruonggiang.adapter.AdapterMenuCityDistrict;
import com.example.appdoan_vutruonggiang.adapter.AdapterRecyleViewGiaoHangCart;
import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.Food_Order;
import com.example.appdoan_vutruonggiang.modle.NhaHang;
import com.example.appdoan_vutruonggiang.modle.ThongTinNguoiOrder;
import com.example.appdoan_vutruonggiang.view.activity.CartActivity;
import com.example.appdoan_vutruonggiang.presenter.ProcessMaGiamGia;
import com.example.appdoan_vutruonggiang.sqlite.SqliteHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentCart extends Fragment {
    RecyclerView recyclerView;
    List<Food_Order> food_orderList;
    CartActivity cartActivity;
    FirebaseDatabase firebaseDatabase;
    SqliteHelper sqliteHelper;
    List<Food_Order> listTable;
    TextView tv_Chon, tv_MaGiamGia;
    ProcessMaGiamGia process_maGiamGia;
    TextInputLayout tilPhoneNumber;
    TextInputLayout tilName;
    TextInputLayout tilCity;
    TextInputLayout tilDistrict;
    TextInputLayout tilAddress;
    TextInputEditText tietPhoneNumber;
    TextInputEditText tietName;
    AutoCompleteTextView acCity;
    AutoCompleteTextView acDistrict;
    TextInputEditText tietAddress;
    AppCompatButton butOrder;
    LinearLayout layoutAddressOrder;
    SharedPreferences sharedPreferences;

    public static Fragment newInstance() {
        Bundle args = new Bundle();

        FragmentCart fragment = new FragmentCart();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cartActivity = (CartActivity) getActivity();
        process_maGiamGia = new ProcessMaGiamGia();

        tilPhoneNumber = view.findViewById(R.id.tilPhoneNumber);
        tilName = view.findViewById(R.id.tilName);
        tilCity = view.findViewById(R.id.tilCity);
        tilDistrict = view.findViewById(R.id.tilDistrict);
        tilAddress = view.findViewById(R.id.tilAddress);
        tietPhoneNumber = view.findViewById(R.id.tietPhoneNumber);
        tietName = view.findViewById(R.id.tietName);
        acCity = view.findViewById(R.id.acCity);
        acDistrict = view.findViewById(R.id.acDistrict);
        tietAddress = view.findViewById(R.id.tietAddress);
        butOrder = view.findViewById(R.id.butOrderCart);
        recyclerView = view.findViewById(R.id.dataGioHang);
        tv_Chon = view.findViewById(R.id.tv_chonMaGiamGia);
        tv_MaGiamGia = view.findViewById(R.id.tv_maGiamGia);
        layoutAddressOrder = view.findViewById(R.id.layoutAddressOrder);
        sqliteHelper = new SqliteHelper(cartActivity);
        sharedPreferences = cartActivity.getSharedPreferences("cities", Context.MODE_PRIVATE);

        sqliteHelper.onDeleteAllGioHang();

        tietName.setText(cartActivity.getHoTen());
        tietPhoneNumber.setText("");
        List<String> cities = Arrays.asList("Hà Nội", "Ninh Bình", "Hà Nam");
        AdapterMenuCityDistrict adapterMenuCity = new AdapterMenuCityDistrict(cartActivity, R.layout.item_city_district, cities);
        acCity.setAdapter(adapterMenuCity);
        List<String> districts1 = Arrays.asList("Bắc Từ Liêm", "Nam Từ Liêm", "Cầu Giấy");
        List<String> districts2 = Arrays.asList("Hoa Lư", "TP Ninh Bình");
        List<String> districts3 = Arrays.asList("TP Phủ Lý", "Duy Tiên");
        acDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("city", acCity.getText().toString().trim());
                editor.commit();
                if (sharedPreferences.getString("city", "").equals("City")) {
                    AdapterMenuCityDistrict adapterMenuDistrict = new AdapterMenuCityDistrict(cartActivity, R.layout.item_city_district, new ArrayList<>());
                    acDistrict.setAdapter(adapterMenuDistrict);
                } else if (sharedPreferences.getString("city", "").equals(cities.get(0))) {
                    AdapterMenuCityDistrict adapterMenuDistrict = new AdapterMenuCityDistrict(cartActivity, R.layout.item_city_district, districts1);
                    acDistrict.setAdapter(adapterMenuDistrict);
                } else if (sharedPreferences.getString("city", "").equals(cities.get(1))) {
                    AdapterMenuCityDistrict adapterMenuDistrict = new AdapterMenuCityDistrict(cartActivity, R.layout.item_city_district, districts2);
                    acDistrict.setAdapter(adapterMenuDistrict);
                } else if (sharedPreferences.getString("city", "").equals(cities.get(2))) {
                    AdapterMenuCityDistrict adapterMenuDistrict = new AdapterMenuCityDistrict(cartActivity, R.layout.item_city_district, districts3);
                    acDistrict.setAdapter(adapterMenuDistrict);
                }
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        // lay food da them gio hang
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("food_giohang").child(cartActivity.getEmail());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                food_orderList = new ArrayList<>();
                Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                for (DataSnapshot data : dataSnapshotIterable) {
                    Food_Order food_order = data.getValue(Food_Order.class);
                    food_orderList.add(food_order);
                }
                AdapterRecyleViewGiaoHangCart adapterRecyleViewGiaoHangCart = new AdapterRecyleViewGiaoHangCart(food_orderList, cartActivity);
                recyclerView.setAdapter(adapterRecyleViewGiaoHangCart);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //databaseReference=firebaseDatabase.getReference().child("food_giohang").child(cartActivity.getSdt());
        //xoa mot food khoi gio hang
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Food_Order food_order = food_orderList.get(position);
                food_orderList.remove(position);
                AdapterRecyleViewGiaoHangCart adapterRecyleViewGiaoHangCart1 = new AdapterRecyleViewGiaoHangCart(food_orderList, cartActivity);
                recyclerView.setAdapter(adapterRecyleViewGiaoHangCart1);
                databaseReference.child(food_order.getId()).removeValue();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        // chon giam gia
        tv_Chon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process_maGiamGia.getMaGiamGia(cartActivity, cartActivity.getEmail());
                DatabaseReference databaseReference1 = firebaseDatabase.getReference().child("luu_ma_van_chuyen").child(cartActivity.getEmail());
                databaseReference1.child("haha").child("giamGia").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        tv_MaGiamGia.setText(snapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                databaseReference1.child("haha").child("giamGia").setValue(0);
            }
        });

        // dat hang tu 1 quan
        butOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listTable = sqliteHelper.getAllGioHang();
                for (int i = 1; i < listTable.size(); i++) {
                    if (!listTable.get(i).getIdNhaHang().equals(listTable.get(0).getIdNhaHang())) {
                        Toast.makeText(cartActivity, "Bạn cần chọn các món ăn từ một nhà hàng để đưa vào đơn hàng! Vui Lòng Quý Khách Chọn Lại", Toast.LENGTH_LONG).show();
                        sqliteHelper.onDeleteAllGioHang();
                        return;
                    }
                }
                AdapterRecyleViewGiaoHangCart adapterRecyleViewGiaoHangCart = new AdapterRecyleViewGiaoHangCart(food_orderList, cartActivity);
                recyclerView.setAdapter(adapterRecyleViewGiaoHangCart);
                if (listTable.size() != 0) {
                    DatabaseReference databaseReference1 = firebaseDatabase.getReference().child("nhaHang");
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                            for (DataSnapshot data : dataSnapshotIterable) {
                                NhaHang nhaHang = data.getValue(NhaHang.class);
                                if (nhaHang.getId().equals(listTable.get(0).getIdNhaHang())) {
                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                                    String gioHienTai = simpleDateFormat.format(calendar.getTime());
                                    try {
                                        Date hienTai = simpleDateFormat.parse(gioHienTai);
                                        Date mo = simpleDateFormat.parse(nhaHang.getOpen());
                                        Date dong = simpleDateFormat.parse(nhaHang.getClose());
                                        if (hienTai.after(mo) && hienTai.before(dong)) {
                                            //luu thong tin nguoi nhan.
                                            if (!tietPhoneNumber.getText().toString().trim().equals("") || !tietName.getText().toString().trim().equals("")
                                                    || !tietAddress.getText().toString().trim().equals("") || !acCity.getText().toString().trim().equals("") ||
                                                    !acDistrict.getText().toString().trim().equals("")) {
                                                if (!acCity.getText().toString().trim().equals("City") && !acDistrict.getText().toString().trim().equals("District")) {
                                                    cartActivity.setTien_giam_gia(Long.parseLong(tv_MaGiamGia.getText().toString()));
                                                    DatabaseReference databaseReference2;
                                                    Calendar calendar1 = Calendar.getInstance();
                                                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                                    String time = dateFormat.format(calendar1.getTime());
                                                    //luu thong tin mon an trong hoa don
                                                    databaseReference2 = firebaseDatabase.getReference().child("da_giao").child(cartActivity.getEmail()).child(time);
                                                    for (int i = 0; i < listTable.size(); i++) {
                                                        databaseReference2.child(listTable.get(i).getId()).setValue(listTable.get(i));
                                                    }
                                                    String address = tietAddress.getText().toString().trim() + ", " + acDistrict.getText().toString().trim() + ", " + acCity.getText().toString().trim();
                                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                                    DatabaseReference databaseReference = firebaseDatabase.getReference().child("thong_tin_nguoi_nhan_hang").child(cartActivity.getEmail());
                                                    ThongTinNguoiOrder thongTinNguoiOrder = new ThongTinNguoiOrder(time, Long.parseLong(tv_MaGiamGia.getText().toString()), tietName.getText().toString()
                                                            , tietPhoneNumber.getText().toString(), address);
                                                    databaseReference.child(time).setValue(thongTinNguoiOrder);
                                                    DatabaseReference databaseReference3=firebaseDatabase.getReference().child("food_giohang").child(cartActivity.getEmail());
                                                    for (int i = 0; i < listTable.size(); i++) {
                                                        databaseReference3.child(listTable.get(i).getId()).removeValue();
                                                        food_orderList.remove(listTable.get(i));
                                                    }
                                                    AdapterRecyleViewGiaoHangCart adapterRecyleViewGiaoHangCart2 = new AdapterRecyleViewGiaoHangCart(food_orderList, cartActivity);
                                                    recyclerView.setAdapter(adapterRecyleViewGiaoHangCart2);
                                                    sqliteHelper.onDeleteAllGioHang();
                                                    Toast.makeText(cartActivity, "Hàng đã bắt giao", Toast.LENGTH_SHORT).show();
                                                }
                                            }else{
                                                Toast.makeText(cartActivity, "Bạn cần điền đủ thông tin người nhận", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            openAndClose(mo, dong, hienTai, cartActivity);
                                            sqliteHelper.onDeleteAllGioHang();
                                        }
                                    } catch (Exception e) {

                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    chuaChon(cartActivity);
                }

            }
        });
        return view;
    }

    public static void openAndClose(Date mo, Date dong, Date hienTai, Context context) {
        if (hienTai.before(mo))
            Toast.makeText(context, "Hiện tại quán chưa mở cửa, Quý Khách Vui Lòng Chờ hoặc Chọn Cửa Hàng Mở Cửa", Toast.LENGTH_LONG).show();
        if (hienTai.after(dong))
            Toast.makeText(context, "Hiện tại quán đã đóng cửa, Quý Khách Vui Lòng Chờ hoặc Chọn Cửa Hàng Mở Cửa", Toast.LENGTH_LONG).show();
    }

    public static void chuaChon(Context context) {
        Toast.makeText(context, "Bạn chưa chọn, Vui lòng bạn chọn để chúng tôi giao hàng nhanh nhất!!!", Toast.LENGTH_LONG).show();

    }
}
