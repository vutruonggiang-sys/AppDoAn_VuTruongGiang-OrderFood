package com.example.appdoan_vutruonggiang.view.fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.presenter.ProcessBank;
import com.example.appdoan_vutruonggiang.presenter.ProcessingDangXuat;
import com.example.appdoan_vutruonggiang.view.activity.LoginActivity;
import com.example.appdoan_vutruonggiang.view.activity.HomePageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {

    private View view;
    TextView tien, passAccount;
    CircleImageView imageUser;
    ImageView eye_open, home;
    EditText nameAccount, emailAccount;
    AppCompatButton but_save_account, but_cancel_account;
    LinearLayout tv_phanHoi, tv_change_pass, tv_chonThe, tv_DangXuat, tvLanguage;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    ProcessBank process_bank;
    private Uri imageUri;
    private final int REQUEST_CODE_GALLERY = 123;
    private final int REQUEST_CODE_CAMERA = 125;
    FirebaseUser user;
    String email = "";
    HomePageActivity homePageActivity;
    private static final String ENGLISH_CODE = "en";
    private static final String VN_CODE = "vi";
    AlertDialog alertDialog;
    DatabaseReference databaseReference;

    public static Fragment newInstance() {

        Bundle args = new Bundle();

        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.account_fragment, container, false);
        anhXa();
        databaseReference=firebaseDatabase.getReference().child("user");
        user = FirebaseAuth.getInstance().getCurrentUser();
        String[] arrayEmail = user.getEmail().split("@");
        email = email + arrayEmail[0];

        if(user.getDisplayName()!=null){
            nameAccount.setText(user.getDisplayName());
        }
        databaseReference.child(email).child("url").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.getValue().toString().trim().equals("")){
                    imageUser.setImageURI(user.getPhotoUrl());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        emailAccount.setText(user.getEmail());
        emailAccount.setEnabled(false);
        passAccount.setText("*******");
        but_save_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(imageUri);
            }
        });
        //loadAvt();
        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        tv_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePageActivity.getFragment(ChangePassFragment.newInstance());
                homePageActivity.getBottomNavigationView().setVisibility(View.GONE);
            }
        });
        tv_chonThe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process_bank.bank(homePageActivity, email);
            }
        });
        tv_DangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProcessingDangXuat.dangXuat(homePageActivity, LoginActivity.class);
            }
        });
        tv_phanHoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePageActivity.getFragment(ChatFragment.newInstance());
                homePageActivity.getBottomNavigationView().setVisibility(View.GONE);
            }
        });
        tvLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] strings = {"", ENGLISH_CODE, VN_CODE};
                alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle(getContext().getString(R.string.language))
                        .setSingleChoiceItems(strings, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (strings[which].equals(ENGLISH_CODE)) {
                                    changeLanguage(ENGLISH_CODE);
                                }
                                if (strings[which].equals(VN_CODE)) {
                                    changeLanguage(VN_CODE);
                                }
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });
        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(homePageActivity).create();
                alertDialog.setTitle(homePageActivity.getString(R.string.camera));
                alertDialog.setIcon(R.drawable.camera);
                alertDialog.setButton2("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        aksPermissionAndCapture();
                        openGallery();
                    }
                });
                alertDialog.setButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        aksPermissionAndCamera();
                        openCamera();
                    }
                });
                alertDialog.show();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null && data.getExtras() != null) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            imageUser.setImageBitmap(bitmap);
            imageUri= Uri.parse(imageUser.getResources().toString());
        }
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageUser.setImageURI(imageUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_CAMERA: {
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(homePageActivity, "Permission Denied", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case REQUEST_CODE_GALLERY: {
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    Toast.makeText(homePageActivity, "Permission Denied", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    public void aksPermissionAndCapture() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        } else {
            int readPermission = ActivityCompat.checkSelfPermission(homePageActivity, Manifest.permission.READ_EXTERNAL_STORAGE);
            int writePermission = ActivityCompat.checkSelfPermission(homePageActivity, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
                homePageActivity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY);
            }
        }
    }

    public void aksPermissionAndCamera() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        } else {
            int camera = ActivityCompat.checkSelfPermission(homePageActivity, Manifest.permission.CAMERA);
            if (camera != PackageManager.PERMISSION_GRANTED) {
                homePageActivity.requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_CAMERA);
            }
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    public void changeLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = homePageActivity.getResources();
        Configuration configuration = homePageActivity.getResources().getConfiguration();
        configuration.setLocale(locale);
        homePageActivity.createConfigurationContext(configuration);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        DatabaseReference databaseReference=firebaseDatabase.getReference().child("language");
        databaseReference.child(email).setValue(language);
        Intent intent = homePageActivity.getIntent();
        homePageActivity.finish();
        startActivity(intent);
    }

    public void updateProfile(Uri uri){
        UserProfileChangeRequest userProfileChangeRequest=new UserProfileChangeRequest.Builder()
                .setDisplayName(nameAccount.getText().toString().trim())
                .setPhotoUri(uri)
                .build();
        user.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if ((task.isSuccessful())){
                    databaseReference.child(email).child("url").setValue(user.getPhotoUrl().toString());
                    Toast.makeText(homePageActivity,getString(R.string.updateProfile),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(homePageActivity,getString(R.string.error),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateAvt() {
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        StorageReference storageReference=firebaseStorage.getReference();
        StorageReference riversRef = storageReference.child("imagesAvt/" + email + ".jpg");
        riversRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri linkDownloadUrl=taskSnapshot.getUploadSessionUri();
                databaseReference.child(email).child("url").setValue(linkDownloadUrl);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    public void anhXa() {
        homePageActivity = (HomePageActivity) getActivity();
        tien = view.findViewById(R.id.tien);
        eye_open = view.findViewById(R.id.eye_open);
        home = view.findViewById(R.id.home);
        nameAccount = view.findViewById(R.id.nameAccount);
        emailAccount = view.findViewById(R.id.emailAccount);
        passAccount = view.findViewById(R.id.passAccount);
        but_cancel_account = view.findViewById(R.id.but_cancel_account);
        but_save_account = view.findViewById(R.id.but_save_account);
        tv_phanHoi = view.findViewById(R.id.tv_phanHoi);
        tv_change_pass = view.findViewById(R.id.tv_change_pass);
        tv_chonThe = view.findViewById(R.id.tv_chonThe);
        tv_DangXuat = view.findViewById(R.id.tv_DangXuat);
        imageUser = view.findViewById(R.id.imageUser);
        tvLanguage = view.findViewById(R.id.tvLanguage);
        process_bank = new ProcessBank();
    }
}
