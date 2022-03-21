package com.example.appdoan_vutruonggiang.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.view.activity.HomePageActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePassFragment extends Fragment {
    private View view;
    ImageView but_back_account;
    EditText ed_old_pass,ed_new_pass,ed_confirm_pass;
    AppCompatButton but_confirm;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    HomePageActivity homePageActivity;

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

        but_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//        if(!pass.equals(ed_old_pass.getText().toString().trim())){
//            Toast.makeText(ChangePassActivity.this,"Bạn nhập sai mật khẩu cũ",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(ed_old_pass.getText().toString().trim().equals("")||ed_new_pass.getText().toString().trim().equals("")||ed_confirm_pass.getText().toString().trim().equals("")){
//            Toast.makeText(ChangePassActivity.this,"Không được để trống",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(pass.toString().trim().equals(ed_new_pass.getText().toString().trim())){
//            Toast.makeText(ChangePassActivity.this,"Bạn đã nhập mật khẩu cũ",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(ed_new_pass.getText().toString().trim().equals(ed_confirm_pass.getText().toString().trim())){
//            Toast.makeText(ChangePassActivity.this,"Bạn đã thay đổi mật khẩu",Toast.LENGTH_SHORT).show();
//            User user=new User(hoTen,ed_new_pass.getText().toString(),email);
//            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
//            DatabaseReference databaseReference=firebaseDatabase.getReference().child("user");
//            databaseReference.child(email).setValue(user);
//        }else{
//            Toast.makeText(ChangePassActivity.this,"xác nhận sai mật khẩu",Toast.LENGTH_SHORT).show();
//            return;
//        }
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
