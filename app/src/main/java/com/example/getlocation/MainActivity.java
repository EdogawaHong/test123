package com.example.getlocation;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.example.getlocation.databinding.ActivityMainBinding;
import com.google.android.gms.maps.model.LatLng;

import pub.devrel.easypermissions.EasyPermissions;

import static androidx.core.content.ContextCompat.startActivity;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        if (!checkRequiredPermissions()) checkRequiredPermissions();

        final GPSLocation gpsLocation = new GPSLocation(this);

        if (gpsLocation.canGetLocation) {
            gpsLocation.getLocation();
            binding.tvText.setText("Lat" + gpsLocation.getLatitude() + "\nLon" + gpsLocation.getLongitude());
        } else {
            binding.tvText.setText("Unabletofind");
            System.out.println("Unable");
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        binding.btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng = gpsLocation.getLocationFromAddress(getBaseContext(), binding.etInputText.getText().toString());

                if (latLng != null) {
                    binding.tvText.setText("Lat" + latLng.latitude + "\nLon" + latLng.longitude);
                } else {
                    binding.tvText.setText("Unabletofind");
                }
            }
        });
    }

    private boolean checkRequiredPermissions() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CHANGE_NETWORK_STATE,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.WAKE_LOCK};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "do not have permission....",
                    20000, perms);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //ok rôi thu buil xem sao
}