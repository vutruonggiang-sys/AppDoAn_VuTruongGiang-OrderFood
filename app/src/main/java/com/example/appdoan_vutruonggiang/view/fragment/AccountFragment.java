package com.example.appdoan_vutruonggiang.view.fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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

import com.bumptech.glide.Glide;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {

    private View view;
    TextView tvEmail;
    CircleImageView imageUser;
    ImageView imgEdit;
    EditText edNameAccount;
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
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference riversRef;

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
        databaseReference = firebaseDatabase.getReference().child("user");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String[] arrayEmail = user.getEmail().split("@");
        email = email + arrayEmail[0];
        riversRef = storageReference.child("imagesAvt/").child(email + ".jpg");
        databaseReference.child(email).child("url").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.getValue().toString().equals("default")){
                    Glide.with(homePageActivity).load(snapshot.getValue()).into(imageUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        edNameAccount.setEnabled(false);
        if (user.getDisplayName() != null) {
            edNameAccount.setText(user.getDisplayName());
        }
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edNameAccount.setEnabled(true);
            }
        });

        tvEmail.setText(user.getEmail());
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
                String[] strings = {"Check Language", ENGLISH_CODE, VN_CODE};
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
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageUser.setImageBitmap(bitmap);
            updateAvt(saveImageCamera((Bitmap) data.getExtras().get("data")));
        }
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageUser.setImageURI(imageUri);
            updateAvt(imageUri);
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
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("language");
        databaseReference.child(email).setValue(language);
        Intent intent = homePageActivity.getIntent();
        homePageActivity.finish();
        startActivity(intent);
    }

    public void updateProfile(Uri uri) {
        if(uri==null){
            return;
        }
        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(edNameAccount.getText().toString().trim())
                .build();
        user.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if ((task.isSuccessful())) {
                    updateAvt(uri);
                } else {
                    Toast.makeText(homePageActivity, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateAvt(Uri uri) {
        riversRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        databaseReference.child(email).child("url").setValue(uri.toString());
                        Toast.makeText(homePageActivity, getString(R.string.updateProfile), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public Uri saveImageCamera(Bitmap bitmap){
        ContextWrapper contextWrapper=new ContextWrapper(homePageActivity.getApplicationContext());
        File fileCamera=contextWrapper.getDir("pathCamera", Context.MODE_PRIVATE);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy/hh/mm/ss");
        Calendar calendar = Calendar.getInstance();
        File path=new File(fileCamera,email+simpleDateFormat.format(calendar.getTime()));
        try {
            FileOutputStream fileOS= new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOS);
            fileOS.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return Uri.fromFile(path);
    }

    public void anhXa() {
        homePageActivity = (HomePageActivity) getActivity();
        imgEdit = view.findViewById(R.id.imgEdit);
        edNameAccount = view.findViewById(R.id.nameAccount);
        tv_phanHoi = view.findViewById(R.id.tv_phanHoi);
        tv_change_pass = view.findViewById(R.id.tv_change_pass);
        tv_chonThe = view.findViewById(R.id.tv_chonThe);
        tv_DangXuat = view.findViewById(R.id.tv_DangXuat);
        imageUser = view.findViewById(R.id.imageUser);
        tvLanguage = view.findViewById(R.id.tvLanguage);
        tvEmail = view.findViewById(R.id.tvEmail);
        process_bank = new ProcessBank();
    }
}
