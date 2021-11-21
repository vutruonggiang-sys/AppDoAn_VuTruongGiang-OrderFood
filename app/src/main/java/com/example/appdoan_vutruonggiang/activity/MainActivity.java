package com.example.appdoan_vutruonggiang.activity;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.example.appdoan_vutruonggiang.Adapter.AdapterRecyleViewFood;
import com.example.appdoan_vutruonggiang.Adapter.AdapterRecyleViewFood1;
import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.inteface.IItemFood;
import com.example.appdoan_vutruonggiang.presenter.Food;
import com.example.appdoan_vutruonggiang.presenter.Process_Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    ViewFlipper vf_Khuyen_mai;
    LinearLayout DoAnNhanh,DoUong,Com,All;
    RecyclerView dataRecyleView1,dataRecyleView;
    ImageView home,search,giohang,account;
    List<Food> foodList=new ArrayList<>();
    //String urlStr="https://demo1859626.mockable.io/itemfood2";
    AdapterRecyleViewFood adapterRecyleViewFood;
    AdapterRecyleViewFood1 adapterRecyleViewFood1;
    String sdt="",hoTen="",diaChi="",email="",pass="";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        anhXa();

        Bundle bundle=getIntent().getExtras();
        sdt=sdt+bundle.getString("phoneNumber");
        hoTen=hoTen+bundle.getString("hoten");
        diaChi=diaChi+bundle.getString("diachi");
        email=email+bundle.getString("gmail");
        pass=pass+bundle.get("pass");

        Process_Food.khuyenMai(vf_Khuyen_mai);
        //new DataGetFood().execute();

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false);
        dataRecyleView.setLayoutManager(layoutManager);
        RecyclerView.LayoutManager layoutManager1=new LinearLayoutManager(MainActivity.this,RecyclerView.HORIZONTAL,false);
        dataRecyleView1.setLayoutManager(layoutManager1);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("food");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> dataSnapshotIterable=snapshot.getChildren();
                for(DataSnapshot data:dataSnapshotIterable){
                    Food food=data.getValue(Food.class);
                    foodList.add(food);
                }
                Process_Food.sapTheoDanhGia(foodList);
                adapterRecyleViewFood=new AdapterRecyleViewFood(foodList, MainActivity.this, new IItemFood() {
                    @Override
                    public void onClickItemFood(Food food) {
                        chuyenManHinh(food);
                    }
                },sdt);
                adapterRecyleViewFood1=new AdapterRecyleViewFood1(foodList, MainActivity.this, new IItemFood() {
                    @Override
                    public void onClickItemFood(Food food) {
                        chuyenManHinh(food);
                    }
                },sdt);
                dataRecyleView.setAdapter(adapterRecyleViewFood);
                dataRecyleView1.setAdapter(adapterRecyleViewFood1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //chon do an nhanh, do uong, com...
        DoAnNhanh.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               List<Food> fooDFast;
               fooDFast=Process_Food.doAnNhanh(foodList);
               adapterRecyleViewFood=new AdapterRecyleViewFood(fooDFast, MainActivity.this, new IItemFood() {
                   @Override
                   public void onClickItemFood(Food food) {
                       chuyenManHinh(food);
                   }
               },sdt);
               adapterRecyleViewFood1=new AdapterRecyleViewFood1(fooDFast, MainActivity.this, new IItemFood() {
                   @Override
                   public void onClickItemFood(Food food) {
                       chuyenManHinh(food);
                   }
               },sdt);
               dataRecyleView.setAdapter(adapterRecyleViewFood);
               dataRecyleView1.setAdapter(adapterRecyleViewFood1);
           }
        });
        DoUong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Food> foodDrink=Process_Food.doUong(foodList);
                adapterRecyleViewFood=new AdapterRecyleViewFood(foodDrink, MainActivity.this, new IItemFood() {
                    @Override
                    public void onClickItemFood(Food food) {
                        chuyenManHinh(food);
                    }
                },sdt);
                adapterRecyleViewFood1=new AdapterRecyleViewFood1(foodDrink, MainActivity.this, new IItemFood() {
                    @Override
                    public void onClickItemFood(Food food) {
                        chuyenManHinh(food);
                    }
                },sdt);
                dataRecyleView.setAdapter(adapterRecyleViewFood);
                dataRecyleView1.setAdapter(adapterRecyleViewFood1);
            }
        });
        Com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Food> foodRice=Process_Food.com(foodList);
                adapterRecyleViewFood=new AdapterRecyleViewFood(foodRice, MainActivity.this, new IItemFood() {
                    @Override
                    public void onClickItemFood(Food food) {
                        chuyenManHinh(food);
                    }
                },sdt);
                adapterRecyleViewFood1=new AdapterRecyleViewFood1(foodRice, MainActivity.this, new IItemFood() {
                    @Override
                    public void onClickItemFood(Food food) {
                        chuyenManHinh(food);
                    }
                },sdt);
                dataRecyleView.setAdapter(adapterRecyleViewFood);
                dataRecyleView1.setAdapter(adapterRecyleViewFood1);
            }
        });
        All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterRecyleViewFood=new AdapterRecyleViewFood(foodList, MainActivity.this, new IItemFood() {
                    @Override
                    public void onClickItemFood(Food food) {
                        chuyenManHinh(food);
                    }
                },sdt);
                adapterRecyleViewFood1=new AdapterRecyleViewFood1(foodList, MainActivity.this, new IItemFood() {
                    @Override
                    public void onClickItemFood(Food food) {
                        chuyenManHinh(food);
                    }
                },sdt);
                dataRecyleView.setAdapter(adapterRecyleViewFood);
                dataRecyleView1.setAdapter(adapterRecyleViewFood1);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Food> listSearch= (ArrayList<Food>) foodList;
                Intent intent=new Intent(MainActivity.this, SearchActivity.class);
                Bundle bundle1=new Bundle();
                bundle1.putParcelableArrayList("list",listSearch);
                bundle1.putString("phoneNumber",sdt);
                bundle1.putString("hoten",hoTen);
                bundle1.putString("diachi",diaChi);
                bundle1.putString("gmail",email);
                bundle1.putString("pass",pass);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, AccountActivity.class);
                Bundle bundle1=new Bundle();
                bundle1.putString("phoneNumber",sdt);
                bundle1.putString("hoten",hoTen);
                bundle1.putString("diachi",diaChi);
                bundle1.putString("gmail",email);
                bundle1.putString("pass",pass);
                ArrayList<Food> listSearch= (ArrayList<Food>) foodList;
                bundle1.putParcelableArrayList("list",listSearch);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
        giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, CartActivity.class);
                Bundle bundle1=new Bundle();
                bundle1.putString("phoneNumber",sdt);
                bundle1.putString("hoten",hoTen);
                bundle1.putString("diachi",diaChi);
                bundle1.putString("gmail",email);
                bundle1.putString("pass",pass);
                ArrayList<Food> listSearch= (ArrayList<Food>) foodList;
                bundle1.putParcelableArrayList("list",listSearch);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }
//    class DataGetFood extends AsyncTask<Void,Void,String>{
//        String result ="";
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            try{
//                URL url=new URL(urlStr);
//                URLConnection urlConnection=url.openConnection();
//                InputStream is=urlConnection.getInputStream();
//                int byteChar;
//                while((byteChar=is.read())!=-1){
//                    result+=(char)byteChar;
//                }
//            } catch (Exception e) {
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
//                    String id=jsonObject.getString("id");
//                    String url=jsonObject.getString("url");
//                    String name=jsonObject.getString("name");
//                    float price=(float) jsonObject.getDouble("price");
//                    String idNhaHang=jsonObject.getString("idNhaHang");
//                    String detail=jsonObject.getString("detail");
//                    float review=(float) jsonObject.getDouble("review");
//                    String type=jsonObject.getString("type");
//                    Food food=new Food(id,url,name,detail,idNhaHang,type,price,review);
//                    foodList.add(food);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
////            RecyclerView.ItemDecoration decoration=new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL);
////            binding.dataRecyleView.addItemDecoration(decoration);
//            foodList=Process_Food.sapTheoDanhGia(foodList);
//            binding.dataRecyleView.setHasFixedSize(true);
//            adapterRecyleViewFood=new AdapterRecyleViewFood(foodList, MainActivity.this, new IItemFood() {
//                @Override
//                public void onClickItemFood(Food food) {
//                    chuyenManHinh(food);
//                }
//            },sdt);
//            adapterRecyleViewFood1=new AdapterRecyleViewFood1(foodList, MainActivity.this, new IItemFood() {
//                @Override
//                public void onClickItemFood(Food food) {
//                    chuyenManHinh(food);
//                }
//            },sdt);
//            binding.dataRecyleView.setAdapter(adapterRecyleViewFood);
//            binding.dataRecyleView1.setAdapter(adapterRecyleViewFood1);
//        }
//    }

    private void chuyenManHinh(Food food){

        Intent intent=new Intent(MainActivity.this, DetailActivity.class);
        Bundle bundle1=new Bundle();
        bundle1.putString("phoneNumber",sdt);
        bundle1.putString("hoten",hoTen);
        bundle1.putString("diachi",diaChi);
        bundle1.putString("gmail",email);
        bundle1.putString("pass",pass);

        bundle1.putString("url",food.getUrl());
        bundle1.putString("food_name",food.getName());
        bundle1.putFloat("food_price",food.getPrice());
        bundle1.putFloat("food_rating",food.getReview());
        bundle1.putString("food_detail",food.getDetail());
        bundle1.putString("food_idnhahang",food.getIdNhaHang());
        bundle1.putString("food_id",food.getId());
        bundle1.putString("food_type",food.getType());
        intent.putExtras(bundle1);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(adapterRecyleViewFood!=null){
            adapterRecyleViewFood.release();
        }
        if(adapterRecyleViewFood1!=null){
            adapterRecyleViewFood1.release();
        }
    }
    public void anhXa(){
        vf_Khuyen_mai=findViewById(R.id.vf_Khuyen_mai);
        DoAnNhanh=findViewById(R.id.DoAnNhanh);
        DoUong=findViewById(R.id.DoUong);
        Com=findViewById(R.id.Com);
        All=findViewById(R.id.All);
        dataRecyleView1=findViewById(R.id.dataRecyleView1);
        dataRecyleView=findViewById(R.id.dataRecyleView);
        home=findViewById(R.id.home);
        search=findViewById(R.id.search);
        giohang=findViewById(R.id.giohang);
        account=findViewById(R.id.account);
    }
}