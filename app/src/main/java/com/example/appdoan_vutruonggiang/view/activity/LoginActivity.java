package com.example.appdoan_vutruonggiang.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.presenter.NetworkChangeListener;
import com.example.appdoan_vutruonggiang.view.fragment.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String email = "";
    String language = "";
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String[] arrayEmail = user.getEmail().split("@");
            email = email + arrayEmail[0];
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference().child("language");
            try {
                databaseReference.child(email).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        language = language + snapshot.getValue();
                        if (language.length() == 0) {
                            changeLanguage("en");
                        } else {
                            changeLanguage(language);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            } catch (Exception exception) {
                changeLanguage("en");
            }
        } else {
            language = language + "en";
            changeLanguage("en");
        }
        getFragment(LoginFragment.newInstance());
    }

    public void getFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }

    public void changeLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration configuration = getResources().getConfiguration();
        configuration.setLocale(locale);
        createConfigurationContext(configuration);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}