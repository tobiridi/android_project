package be.jadoulle.examproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import be.jadoulle.examproject.asynchronousTask.UserConnectionAsyncTask;
import be.jadoulle.examproject.pojo.User;

public class MainActivity extends AppCompatActivity {
    public static final int MAIN_ACTIVITY_CODE = 1;

    private View.OnClickListener connection_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText et_email = findViewById(R.id.et_email);
            EditText et_password = findViewById(R.id.et_password);
            new UserConnectionAsyncTask(MainActivity.this).execute(
                    et_email.getText().toString(),
                    et_password.getText().toString()
            );
        }
    };

    private View.OnClickListener inscription_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent inscription_intent = new Intent(MainActivity.this, InscriptionActivity.class);
            startActivityForResult(inscription_intent, MAIN_ACTIVITY_CODE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_connection).setOnClickListener(connection_listener);
        findViewById(R.id.btn_inscription).setOnClickListener(inscription_listener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //set empty EditText
        EditText email = findViewById(R.id.et_email);
        EditText password = findViewById(R.id.et_password);
        email.setText("");
        password.setText("");

        //check permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                //show to the user the requires permissions
                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION};

                ActivityCompat.requestPermissions(this, permissions, MAIN_ACTIVITY_CODE);

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String text;
        if (data != null && requestCode == MAIN_ACTIVITY_CODE) {
            if (resultCode == RESULT_OK) {
                text = data.getStringExtra("inscription_message");
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
            }
            else if (resultCode == RESULT_CANCELED) {
                text = data.getStringExtra("logout_message");
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check user permissions
        if (requestCode == MAIN_ACTIVITY_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(MainActivity.this, "Permissions authorized", Toast.LENGTH_SHORT).show();
                findViewById(R.id.btn_connection).setOnClickListener(connection_listener);
                findViewById(R.id.btn_inscription).setOnClickListener(inscription_listener);
                //Toast.makeText(MainActivity.this, "gps perm ok", Toast.LENGTH_SHORT).show();
            }
            else {
                findViewById(R.id.btn_connection).setOnClickListener(null);
                findViewById(R.id.btn_inscription).setOnClickListener(null);
                //Toast.makeText(MainActivity.this, "gps perm pas ok", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void userSaleObjectList(User user) {
        Intent object_list_intent = new Intent(MainActivity.this, ObjectListActivity.class);
        object_list_intent.putExtra("user", user);
        startActivityForResult(object_list_intent, MAIN_ACTIVITY_CODE);
    }


    //else {
//            //get the position, permissions granted
//            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//            //check if GPS is enabled
//            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                Intent gps_intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(gps_intent);
//            } else {
//                //get the location
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, GlobalSettings.gpsUpdateTime, GlobalSettings.gpsMinDistance, locationListener);
//            }
//        }


    //get user position
    //create listener associate to locationManager
//    private LocationListener locationListener = new LocationListener() {
//        @Override
//        public void onLocationChanged(@NonNull Location location) {
//            //get longitude
//            double longitude = location.getLongitude();
//            //get latitude
//            double latitude = location.getLatitude();
//
//            String text = "Current location :\n" +
//                    "Longitude : " + longitude + " latitude : " + latitude;
//            System.out.println(text);
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//            //obligate to implement
//            System.out.println("Location status changed");
//        }
//    };



    //check if the first asking permission
//                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) &&
//                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
//                }



    //get the position, permissions granted
//                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//                //check if GPS is enabled
//                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                    Intent gps_intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                    startActivity(gps_intent);
//                } else {
//                    if (ActivityCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED &&
//                        ActivityCompat.checkSelfPermission(this, permissions[1]) != PackageManager.PERMISSION_GRANTED) {
//                        //get the location
//                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, GlobalSettings.gpsUpdateTime, GlobalSettings.gpsMinDistance, locationListener);
//                    }
//                }

}