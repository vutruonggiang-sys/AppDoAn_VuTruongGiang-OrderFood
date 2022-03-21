package com.example.appdoan_vutruonggiang.ggmap;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.appdoan_vutruonggiang.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String sdt="",hoTen="",diaChi="",email="",pass="";
    String url="",name="",detail="",id="",idNhaHang="",type="";
    String nameNH="";
    float price=0,rating=5;
    long V,V1;
    ImageView ButArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButArrow=findViewById(R.id.but_Back_Detail);
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

        V=bundle.getLong("v");
        V1=bundle.getLong("v1");
        nameNH=nameNH+bundle.getString("name_NH");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ButArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backDetail();
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(V, V1);
        mMap.addMarker(new MarkerOptions().position(sydney).title(nameNH));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,25));
    }
    public void backDetail(){
//        Intent intent=new Intent(MapsActivity.this, DetailActivity.class);
//        Bundle bundle1=new Bundle();
//        bundle1.putString("phoneNumber",sdt);
//        bundle1.putString("hoten",hoTen);
//        bundle1.putString("diachi",diaChi);
//        bundle1.putString("gmail",email);
//        bundle1.putString("pass",pass);
//
//        bundle1.putString("url",url);
//        bundle1.putString("food_name",name);
//        bundle1.putFloat("food_price",price);
//        bundle1.putFloat("food_rating",rating);
//        bundle1.putString("food_detail",detail);
//        bundle1.putString("food_idnhahang",idNhaHang);
//        bundle1.putString("food_id",id);
//        bundle1.putString("food_type",type);
//        intent.putExtras(bundle1);
//        startActivity(intent);
    }
}