package com.azhar.barcodegenerator;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.azhar.barcodegenerator.fragments.GenerateBarcodeFragment;
import com.azhar.barcodegenerator.fragments.GenerateQRcodeFragment;
import com.azhar.barcodegenerator.fragments.ScanFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();

        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, new ScanFragment());
        transaction.commit();
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void checkPermission() {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_scan:
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame, new ScanFragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_generate_barcode:
                    FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                    transaction2.replace(R.id.content_frame, new GenerateBarcodeFragment());
                    transaction2.commit();
                    return true;
                case R.id.navigation_generate_qrcode:
                    FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                    transaction3.replace(R.id.content_frame, new GenerateQRcodeFragment());
                    transaction3.commit();
                    return true;
            }
            return false;
        }
    };
}
