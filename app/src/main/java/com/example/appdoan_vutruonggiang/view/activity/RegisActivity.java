package com.example.appdoan_vutruonggiang.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.GiamGia;
import com.example.appdoan_vutruonggiang.modle.User;
import com.example.appdoan_vutruonggiang.presenter.ProcessingDangKy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisActivity extends Activity {
    TextInputLayout tilYourEmail, tilYourName, tilPass, tilConfirmPass;
    TextInputEditText tietYourEmail, tietYourName, tietPass, tietConfirmPass;
    TextView tvBackLogin;
    ImageView imgBackArrow;
    AppCompatButton butCancel, butRegis;
    ProcessingDangKy processingDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_regis);
        anhXa();
        tvBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        butRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tietConfirmPass.getText().toString().trim().equals("") || tietPass.getText().toString().trim().equals("") ||
                        tietYourName.getText().toString().trim().equals("") || tietYourEmail.getText().toString().trim().equals("")) {
                    Toast.makeText(RegisActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!tietConfirmPass.getText().toString().equals(tietPass.getText().toString())) {
                    Toast.makeText(RegisActivity.this, "Mât khẩu không đúng", Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference().child("user");
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.createUserWithEmailAndPassword(tietYourEmail.getText().toString().trim(), tietPass.getText().toString().trim())
                        .addOnCompleteListener(RegisActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    String[] email = tietYourEmail.getText().toString().split("@");
                                    User user = new User(tietYourName.getText().toString(), tietPass.getText().toString(), tietYourEmail.getText().toString());
                                    databaseReference.child(email[0]).setValue(user);
                                    Toast.makeText(RegisActivity.this, "Bạn đã đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    DatabaseReference databaseReference1 = firebaseDatabase.getReference().child("bank");
                                    databaseReference1.child(email[0]).setValue("Bạn chưa chọn dịch vụ");
                                    DatabaseReference databaseReference2 = firebaseDatabase.getReference().child("ma_giam_gia");
                                    databaseReference2.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                                            for (DataSnapshot data : dataSnapshotIterable) {
                                                GiamGia giamGia = data.getValue(GiamGia.class);
                                                DatabaseReference databaseReference3 = firebaseDatabase.getReference().child("giam_gia");
                                                databaseReference3.child(email[0]).child(giamGia.getId()).setValue(giamGia);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }else{
                                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

    public void anhXa() {
        tilYourEmail = findViewById(R.id.tilYourNumber);
        tietYourEmail = findViewById(R.id.tietYourNumber);
        tietYourName = findViewById(R.id.tietYourName);
        tilYourName = findViewById(R.id.tilYourName);
        tilPass = findViewById(R.id.tilPassword);
        tietPass = findViewById(R.id.tietPassword);
        tilConfirmPass = findViewById(R.id.tilConfirmPassword);
        tietConfirmPass = findViewById(R.id.tietConfirmPassword);
        butCancel = findViewById(R.id.acbCancel);
        butRegis = findViewById(R.id.acbRegis);
        tvBackLogin = findViewById(R.id.tv_BackLogin);
        imgBackArrow = findViewById(R.id.imgBackArrow);
        processingDangKy = new ProcessingDangKy();
    }
}