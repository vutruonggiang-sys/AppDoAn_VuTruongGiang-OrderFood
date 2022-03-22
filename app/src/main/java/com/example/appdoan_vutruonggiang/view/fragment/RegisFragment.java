package com.example.appdoan_vutruonggiang.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.GiamGia;
import com.example.appdoan_vutruonggiang.modle.User;
import com.example.appdoan_vutruonggiang.presenter.ProcessingDangKy;
import com.example.appdoan_vutruonggiang.view.activity.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisFragment extends Fragment {

    private View view;
    TextInputLayout tilYourEmail, tilYourName, tilPass, tilConfirmPass;
    TextInputEditText tietYourEmail, tietYourName, tietPass, tietConfirmPass;
    TextView tvBackLogin;
    ImageView imgBackArrow;
    AppCompatButton butCancel, butRegis;
    ProcessingDangKy processingDangKy;
    LoginActivity activity;
    public static Fragment newInstance() {

        Bundle args = new Bundle();

        RegisFragment fragment = new RegisFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.regis_fragment,container,false);
        init();
        tvBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragment(LoginFragment.newInstance());
            }
        });
        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragment(LoginFragment.newInstance());
            }
        });
        butRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tietConfirmPass.getText().toString().trim().equals("") || tietPass.getText().toString().trim().equals("") ||
                        tietYourName.getText().toString().trim().equals("") || tietYourEmail.getText().toString().trim().equals("")) {
                    Toast.makeText(activity, "Không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!tietConfirmPass.getText().toString().equals(tietPass.getText().toString())) {
                    Toast.makeText(activity, "Mât khẩu không đúng", Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference().child("user");
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.createUserWithEmailAndPassword(tietYourEmail.getText().toString().trim(), tietPass.getText().toString().trim())
                        .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    String[] email = tietYourEmail.getText().toString().split("@");
                                    UserProfileChangeRequest userProfileChangeRequest=new UserProfileChangeRequest.Builder()
                                            .setDisplayName(tietYourName.getText().toString().trim())
                                            .build();
                                    FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                                    firebaseUser.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                            }
                                        }
                                    });
                                    User user = new User(tietYourName.getText().toString(), tietPass.getText().toString(), tietYourEmail.getText().toString()," ");
                                    databaseReference.child(email[0]).setValue(user);
                                    Toast.makeText(activity, "Bạn đã đăng ký thành công", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(activity,"Error",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
        return view;
    }
    public void init() {
        activity= (LoginActivity) getActivity();
        tilYourEmail = view.findViewById(R.id.tilYourNumber);
        tietYourEmail = view.findViewById(R.id.tietYourNumber);
        tietYourName = view.findViewById(R.id.tietYourName);
        tilYourName = view.findViewById(R.id.tilYourName);
        tilPass = view.findViewById(R.id.tilPassword);
        tietPass = view.findViewById(R.id.tietPassword);
        tilConfirmPass = view.findViewById(R.id.tilConfirmPassword);
        tietConfirmPass = view.findViewById(R.id.tietConfirmPassword);
        butCancel = view.findViewById(R.id.acbCancel);
        butRegis = view.findViewById(R.id.acbRegis);
        tvBackLogin = view.findViewById(R.id.tv_BackLogin);
        imgBackArrow = view.findViewById(R.id.imgBackArrow);
        processingDangKy = new ProcessingDangKy();
    }
}
