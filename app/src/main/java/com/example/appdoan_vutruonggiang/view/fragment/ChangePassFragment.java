package com.example.appdoan_vutruonggiang.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.User;
import com.example.appdoan_vutruonggiang.view.activity.HomePageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePassFragment extends Fragment {
    private View view;
    ImageView but_back_account;
    EditText ed_old_pass,ed_new_pass,ed_confirm_pass;
    AppCompatButton but_confirm;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    HomePageActivity homePageActivity;
    FirebaseAuth auth;
    FirebaseUser user;
    String email="";
    String pass="";

    public static Fragment newInstance() {

        Bundle args = new Bundle();

        ChangePassFragment fragment = new ChangePassFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.change_pass_fragment,container,false);
        anhXa();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("user");
        user = FirebaseAuth.getInstance().getCurrentUser();
        String[] arrayEmail = user.getEmail().split("@");
        email = email + arrayEmail[0];

        String passOld=ed_old_pass.getText().toString().trim();
        String passNew=ed_new_pass.getText().toString().trim();
        String passConfirm=ed_confirm_pass.getText().toString().trim();
        databaseReference.child(email).child("pass").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pass=pass+snapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        but_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        if(!pass.equals(passOld)){
            Toast.makeText(homePageActivity,"Bạn nhập sai mật khẩu cũ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(passOld.equals("")||passConfirm.equals("")||passNew.equals("")){
            Toast.makeText(homePageActivity,"Không được để trống",Toast.LENGTH_SHORT).show();
            return;
        }
        if(pass.equals(passNew)){
            Toast.makeText(homePageActivity,"Bạn đã nhập mật khẩu cũ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(ed_new_pass.getText().toString().trim().equals(ed_confirm_pass.getText().toString().trim())){
            user.updatePassword(passNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        databaseReference.child(email).child("pass").setValue(passNew);
                        Toast.makeText(homePageActivity,"Bạn đã thay đổi mật khẩu",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(homePageActivity,"xác nhận sai mật khẩu",Toast.LENGTH_SHORT).show();
            return;
        }
            }
        });
        but_back_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePageActivity.getFragment(AccountFragment.newInstance());
                homePageActivity.getBottomNavigationView().setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    public void anhXa(){
        homePageActivity= (HomePageActivity) getActivity();
        but_back_account=view.findViewById(R.id.but_back_account);
        ed_old_pass=view.findViewById(R.id.ed_old_pass);
        ed_new_pass=view.findViewById(R.id.ed_new_pass);
        ed_confirm_pass=view.findViewById(R.id.ed_confirm_pass);
        but_confirm=view.findViewById(R.id.but_confirm);
    }
}
