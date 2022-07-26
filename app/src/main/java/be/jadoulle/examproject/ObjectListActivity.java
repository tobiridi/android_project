package be.jadoulle.examproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;

import be.jadoulle.examproject.asynchronousTask.SaleObjectListAsyncTask;
import be.jadoulle.examproject.pojo.SaleObject;
import be.jadoulle.examproject.pojo.User;
import be.jadoulle.examproject.utilitary.GlobalSettings;

public class ObjectListActivity extends AppCompatActivity {
    public static final int OBJECT_LIST_ACTIVITY_CODE = 3;
    public User user = null;
    public ArrayList<SaleObject> allSaleObjects = new ArrayList<>();
    public ArrayList<SaleObject> saleObjects = new ArrayList<>();
    private float currentDistance = GlobalSettings.gpsDefaultDistance;
    private LocationManager locationManager = null;


    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            //clear the old list
            ObjectListActivity.this.saleObjects.clear();
            //get longitude
            double longitude = location.getLongitude();
            //get latitude
            double latitude = location.getLatitude();

            //String text = "Current location :\n" + "Longitude : " + longitude + " latitude : " + latitude;
            //System.out.println(text);

            for (SaleObject saleObject : ObjectListActivity.this.allSaleObjects) {
                //get distance between each sale object
                float[] distanceResult = new float[1];
                Location.distanceBetween(saleObject.getLatitude(),saleObject.getLongitude(),latitude,longitude,distanceResult);
                //System.out.println("distance avec l'objet : " + distanceResult[0] + " meters");
                //System.out.println("distance en km : " + distanceResult[0] / 1000 + " km");
                //System.out.println("distance rechercher : " + ObjectListActivity.this.currentDistance + " km");

                //distance in meters to km
                if (distanceResult[0] / 1000 <= ObjectListActivity.this.currentDistance) {
                    ObjectListActivity.this.saleObjects.add(saleObject);
                    //System.out.println("elements dans la liste : " + ObjectListActivity.this.saleObjects.size());
                }
            }

            //System.out.println("taille de la liste pendant le listener : " + ObjectListActivity.this.saleObjects.size());
            ObjectListActivity.this.reloadSaleObjectList();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            //EMTPY
        }
    };

    private View.OnClickListener logout_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent back_intent = new Intent();
            back_intent.putExtra("logout_message", getResources().getString(R.string.logout_message));
            setResult(RESULT_CANCELED, back_intent);
            finish();
        }
    };

    private View.OnClickListener new_sale_object_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent new_sale_object_intent = new Intent(ObjectListActivity.this, NewSaleObjectActivity.class);
            new_sale_object_intent.putExtra("user", ObjectListActivity.this.user);
            startActivity(new_sale_object_intent);
        }
    };

    //TODO : to do
    private View.OnClickListener details_sale_object_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            Intent new_sale_object_intent = new Intent(ObjectListActivity.this, ???);
//            //send the clicked sale_object
//            new_sale_object_intent.putExtra("user", ObjectListActivity.this.user);
//            startActivity(new_sale_object_intent);
            Toast.makeText(ObjectListActivity.this, "In progress ...", Toast.LENGTH_SHORT).show();
        }
    };

    //TODO : to do
    private View.OnClickListener tracking_object_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent tracking_object_intent = new Intent(ObjectListActivity.this, TrackingObjectActivity.class);
            tracking_object_intent.putExtra("user", ObjectListActivity.this.user);
            startActivity(tracking_object_intent);
        }
    };

    private View.OnClickListener max_distance_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //get distance search
            EditText et_max_distance = findViewById(R.id.et_max_distance);
            ObjectListActivity.this.currentDistance = Float.parseFloat(et_max_distance.getText().toString());

            //get GPS status
            if (ObjectListActivity.this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                if (ActivityCompat.checkSelfPermission(ObjectListActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(ObjectListActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        //show to the user the requires permissions
                        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_COARSE_LOCATION};

                        ActivityCompat.requestPermissions(ObjectListActivity.this, permissions, OBJECT_LIST_ACTIVITY_CODE);
                    }
                }
                else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, GlobalSettings.gpsUpdateTime, ObjectListActivity.this.currentDistance, locationListener);
                }
            }
            else {
                Intent gps_intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(gps_intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_list);
        ObjectListActivity.this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //get the user connected
        this.user = (User) getIntent().getSerializableExtra("user");
        //System.out.println("user : " + this.user);

        EditText et_max_distance = findViewById(R.id.et_max_distance);
        Button btn_logout = findViewById(R.id.btn_logout);
        Button btn_add_new_object = findViewById(R.id.btn_add_new_object);
        Button btn_tracking_object = findViewById(R.id.btn_tracking_object);
        Button btn_max_distance_refresh = findViewById(R.id.btn_max_distance_refresh);

        et_max_distance.setText(String.valueOf((int) GlobalSettings.gpsDefaultDistance));
        btn_logout.setOnClickListener(logout_listener);
        btn_add_new_object.setOnClickListener(new_sale_object_listener);
        btn_tracking_object.setOnClickListener(tracking_object_listener);
        btn_max_distance_refresh.setOnClickListener(max_distance_listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("je passe dans le onResume");
        //get all objects in DB
        ObjectListActivity.this.allSaleObjects.clear();
        new SaleObjectListAsyncTask(ObjectListActivity.this).execute();
    }

    //test
    //onDestroy is called when the activity is finish from cancel button or previous button
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }

    private String saleObjectContent(SaleObject saleObject) {
        String description = saleObject.getDescription().substring(0, GlobalSettings.saleObjectDescriptionMinLength);
        if (saleObject.getDescription().length() > GlobalSettings.saleObjectDescriptionMinLength) {
            description += "...";
        }

        return getResources().getString(R.string.object_name_text) + " : " + saleObject.getName() + "\n" +
               getResources().getString(R.string.object_type_text) + " : " + saleObject.getType() + "\n" +
               getResources().getString(R.string.object_description_text) + " : " + description + "\n" +
               getResources().getString(R.string.object_price_text) + " : " + saleObject.getPrice() + " " + getResources().getString(R.string.currency);
    }

    private void reloadSaleObjectList() {
        try {
            TableLayout tl_sale_objects_list = findViewById(R.id.tl_sale_objects_list);
            tl_sale_objects_list.removeAllViews();

            if (ObjectListActivity.this.saleObjects.size() > 0) {
                for (SaleObject saleObject : ObjectListActivity.this.saleObjects) {
                    TableRow tableRow = new TableRow(ObjectListActivity.this);
                    tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
                    tableRow.setPadding(0,10,0,0);
                    tableRow.setGravity(Gravity.CENTER);

                    //image to the left of the button
                    //Drawable img = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_add_photo_alternate_48px, null);
                    //img.setBounds(0,0,180,180);

                    Button btn = new Button(ObjectListActivity.this);
                    //btn.setCompoundDrawables(img,null,null,null);
                    btn.setCompoundDrawablePadding(20);
                    btn.setWidth(tl_sale_objects_list.getWidth());
                    btn.setLines(5);
                    btn.setText(ObjectListActivity.this.saleObjectContent(saleObject));
                    btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_gradient_corner_background, null));
                    btn.setOnClickListener(details_sale_object_listener);

                    tableRow.addView(btn);
                    tl_sale_objects_list.addView(tableRow, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
                }
            }
            else {
                Toast.makeText(ObjectListActivity.this, getResources().getString(R.string.objects_not_found_message), Toast.LENGTH_SHORT).show();
            }

        } catch (IndexOutOfBoundsException ex) {
            //ex.printStackTrace();
            Toast.makeText(ObjectListActivity.this, getResources().getString(R.string.objects_not_loaded_message), Toast.LENGTH_SHORT).show();
        }
    }
}