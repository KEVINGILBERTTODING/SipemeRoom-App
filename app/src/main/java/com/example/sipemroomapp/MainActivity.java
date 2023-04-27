package com.example.sipemroomapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.sipemroomapp.Customer_fragment.CustomerHomeFragment;
import com.example.sipemroomapp.Customer_fragment.CustomerMyTransactionsFragment;
import com.example.sipemroomapp.Customer_fragment.CustomerProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNav);
        replace(new CustomerHomeFragment());

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menuHome) {
                    replace(new CustomerHomeFragment());
                    return true;
                } else if (item.getItemId() == R.id.menuTransactions) {
                    replace(new CustomerMyTransactionsFragment());
                    return true;
                }else if (item.getItemId() == R.id.menuProfile){
                    replace(new CustomerProfileFragment());
                    return true;
                }
                return false;
            }
        });

    }

    private void replace(Fragment  fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameCustomer, fragment).addToBackStack(null).commit();
    }
}