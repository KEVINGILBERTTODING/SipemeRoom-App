package com.example.sipemroomapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.sipemroomapp.AdminFragment.AdminDataTransaksiFragment;
import com.example.sipemroomapp.AdminFragment.AdminHomeFragment;
import com.example.sipemroomapp.AdminFragment.AdminProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AdminMainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        bottomNavigationView = findViewById(R.id.bottomNav);
        replace(new AdminHomeFragment());

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menuHome) {
                    replace(new AdminHomeFragment());
                    return true;
                } else if (item.getItemId() == R.id.menuTransactions) {
                    replace(new AdminDataTransaksiFragment());
                    return true;
                }else if (item.getItemId() == R.id.menuProfile){
                    replace(new AdminProfileFragment());
                    return true;
                }
                return false;
            }
        });

    }


    private void replace(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, fragment).addToBackStack(null).commit();
    }
}