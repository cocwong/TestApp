package com.test;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.testapp.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ZoomActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_refresh);
//        recycler = findViewById(R.id.recycler);
//        ScaleLayoutManager manager = new ScaleLayoutManager(this, 10);
//        recycler.setLayoutManager(manager);
        tv = findViewById(R.id.tv);
        getLocation();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            getDetail();
        }
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            ZoomActivity.this.location = location;
            System.out.println(location.getLongitude());
            getData();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    System.out.println("available");
                    location = manager.getLastKnownLocation(provider);
                    getData();
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    System.out.println("unavailable");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    System.out.println("out of service");
                    break;
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
            System.out.println("enable"+provider);
            location = manager.getLastKnownLocation(provider);
            getData();
        }

        @Override
        public void onProviderDisabled(String provider) {
            System.out.println("disable");
            location = null;
        }
    };

    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(false);
        criteria.setBearingRequired(false);
        criteria.setAltitudeRequired(false);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    private Location location;
    private LocationManager manager;
    private String provider;

    @SuppressWarnings("MissingPermission")
    private void getDetail() {
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = manager.getProviders(true);
        for (String str :
                providers) {
            System.out.println(str);
        }
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            //当没有可用的位置提供器时，弹出Toast提示用户
            System.out.println("no_usable");
            return;
        }
        location = manager.getLastKnownLocation(provider);
        System.out.println(provider);
        if (location != null) {
            System.out.println(location.getLongitude());
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
    }

    private void getData() {
        if (location == null) return;
        Geocoder geocoder = new Geocoder(this, Locale.CHINA);
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                String addressLine = address.getAddressLine(0);
                System.out.println(addressLine);
                System.out.println(address.getPhone());
                tv.setText(addressLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getDetail();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.removeUpdates(locationListener);
    }
}
