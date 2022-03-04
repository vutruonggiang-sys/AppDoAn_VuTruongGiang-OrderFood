package com.example.appdoan_vutruonggiang.view.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.User;
import com.example.appdoan_vutruonggiang.presenter.Food;
import com.example.appdoan_vutruonggiang.presenter.ProcessBank;
import com.example.appdoan_vutruonggiang.presenter.ProcessingDangXuat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends Activity {
    TextView tien, passAccount;
    CircleImageView imageUser;
    ImageView eye_open, home, search, giohang, account;
    EditText nameAccount, emailAccount;
    AppCompatButton but_save_account, but_cancel_account;
    LinearLayout tv_phanHoi, tv_change_pass, tv_chonThe, tv_DangXuat;
    String hoTen = "", pass = "";
    List<Food> foodList;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();;
    DatabaseReference databaseReference;
    ProcessBank process_bank;
    private Uri imageUri;
    private final int REQUEST_CODE_CAMERA = 125;
    DatabaseReference databaseReferenceAvt;
    FirebaseUser user;
    String email="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_account);
        anhXa();
        user= FirebaseAuth.getInstance().getCurrentUser();
        String[] arrayEmail=user.getEmail().split("@");
        email=email+arrayEmail[0];
        databaseReferenceAvt=firebaseDatabase.getReference().child("avatar").child(email);
        databaseReference = firebaseDatabase.getReference().child("user");
        Bundle bundle = this.getIntent().getExtras();
        foodList = bundle.getParcelableArrayList("list");

        nameAccount.setText(hoTen);
        emailAccount.setText("email");
        emailAccount.setEnabled(false);
        passAccount.setText("*******");
        //loadAvt();
        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        but_cancel_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        but_save_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AccountActivity.this, "Bạn đã lưu thành công", Toast.LENGTH_SHORT).show();
            }
        });
        tv_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ChangePassActivity.class);
                ArrayList<Food> listSearch = (ArrayList<Food>) foodList;
                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("list", listSearch);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
        tv_chonThe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process_bank.bank(AccountActivity.this, email);
            }
        });
        tv_DangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProcessingDangXuat.dangXuat(AccountActivity.this, LoginActivity.class);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CartActivity.class);
                ArrayList<Food> listSearch = (ArrayList<Food>) foodList;
                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("list", listSearch);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SearchActivity.class);
                ArrayList<Food> listSearch = (ArrayList<Food>) foodList;
                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("list", listSearch);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
        tv_phanHoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ChatActivity.class);
                ArrayList<Food> listSearch = (ArrayList<Food>) foodList;
                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("list", listSearch);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageUser.setImageURI(imageUri);
            updateAvt();
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    public void updateAvt() {
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        StorageReference storageReference=firebaseStorage.getReference();
        StorageReference riversRef = storageReference.child("imagesAvt/" + email + ".jpg");
        riversRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri linkDownloadUrl=taskSnapshot.getUploadSessionUri();
                databaseReferenceAvt.setValue(linkDownloadUrl.toString());
                Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed To Upload", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void loadAvt(){

        databaseReferenceAvt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String url=snapshot.getValue().toString();
                if(url!=null){
                    Glide.with(AccountActivity.this).load(url).into(imageUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void anhXa() {
        tien = findViewById(R.id.tien);
        eye_open = findViewById(R.id.eye_open);
        home = findViewById(R.id.home);
        search = findViewById(R.id.search);
        giohang = findViewById(R.id.giohang);
        account = findViewById(R.id.account);
        nameAccount = findViewById(R.id.nameAccount);
        emailAccount = findViewById(R.id.emailAccount);
        passAccount = findViewById(R.id.passAccount);
        but_cancel_account = findViewById(R.id.but_cancel_account);
        but_save_account = findViewById(R.id.but_save_account);
        tv_phanHoi = findViewById(R.id.tv_phanHoi);
        tv_change_pass = findViewById(R.id.tv_change_pass);
        tv_chonThe = findViewById(R.id.tv_chonThe);
        tv_DangXuat = findViewById(R.id.tv_DangXuat);
        imageUser = findViewById(R.id.imageUser);
        process_bank = new ProcessBank();
    }
}