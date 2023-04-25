package com.example.sipemroomapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.sipemroomapp.Customer_fragment.CustomerHomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replace(new CustomerHomeFragment());
    }

    private void replace(Fragment  fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameCustomer, fragment).addToBackStack(null).commit();
    }
}