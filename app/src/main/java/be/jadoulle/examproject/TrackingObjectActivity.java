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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;

import be.jadoulle.examproject.asynchronousTask.TrackingSaleObjectAddAsyncTask;
import be.jadoulle.examproject.asynchronousTask.TrackingSaleObjectListAsyncTask;
import be.jadoulle.examproject.pojo.SaleObject;
import be.jadoulle.examproject.pojo.User;
import be.jadoulle.examproject.utilitary.GlobalSettings;
import be.jadoulle.examproject.utilitary.Utilities;

public class TrackingObjectActivity extends AppCompatActivity {
    public static final int TRACKING_OBJECT_ACTIVITY_CODE = GlobalSettings.TRACKING_OBJECT_ACTIVITY_CODE;
    public User user = null;
    public ArrayList<SaleObject> allTrackingSaleObjects = new ArrayList<>();
    private double userLatitude = 0.0;
    private double userLongitude = 0.0;


    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            //get longitude
            TrackingObjectActivity.this.userLongitude= location.getLongitude();
            //get latitude
            TrackingObjectActivity.this.userLatitude= location.getLatitude();

            //String text = "Current location :\n" + "Longitude : " + longitude + " latitude : " + latitude;
            //System.out.println(text);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            //EMTPY
        }
    };

    private View.OnClickListener validate_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setResult(RESULT_OK);
            finish();
        }
    };

    private View.OnClickListener details_sale_object_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SaleObject selectedSaleObject = null;
            try {
                for (SaleObject saleObject: TrackingObjectActivity.this.allTrackingSaleObjects) {
                    if (saleObject.getId() == view.getId()) {
                        selectedSaleObject = saleObject;
                        break;
                    }
                }

                float[] distanceResult = new float[1];
                Location.distanceBetween(TrackingObjectActivity.this.userLatitude,TrackingObjectActivity.this.userLongitude,
                                         selectedSaleObject.getLatitude(),selectedSaleObject.getLongitude(),distanceResult);

                Intent details_sale_object_intent = new Intent(TrackingObjectActivity.this, DetailsSaleObjectActivity.class);
                //send the clicked sale_object
                details_sale_object_intent.putExtra("user", TrackingObjectActivity.this.user);
                details_sale_object_intent.putExtra("selectedSaleObject",selectedSaleObject);
                details_sale_object_intent.putExtra("saleObjectDistance",(double) distanceResult[0]);
                details_sale_object_intent.putExtra("isAlreadyTracking",true);
                startActivity(details_sale_object_intent);

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_object);

        this.user = (User) getIntent().getSerializableExtra("user");

        findViewById(R.id.btn_validate).setOnClickListener(validate_listener);

        new TrackingSaleObjectListAsyncTask(TrackingObjectActivity.this).execute();

        //get GPS status
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(TrackingObjectActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(TrackingObjectActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    //show to the user the requires permissions
                    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_COARSE_LOCATION};

                    ActivityCompat.requestPermissions(TrackingObjectActivity.this, permissions, TRACKING_OBJECT_ACTIVITY_CODE);
                }
            }
            else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, GlobalSettings.gpsUpdateTime, GlobalSettings.gpsDefaultDistance, locationListener);
            }
        }
        else {
            Intent gps_intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(gps_intent);
        }
    }

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

    public void reloadSaleObjectList() {
        try {
            TableLayout tl_sale_objects_list = findViewById(R.id.tl_sale_objects_list);
            tl_sale_objects_list.removeAllViews();

            if (TrackingObjectActivity.this.allTrackingSaleObjects.size() > 0) {
                for (SaleObject saleObject : TrackingObjectActivity.this.allTrackingSaleObjects) {
                    TableRow tableRow = new TableRow(TrackingObjectActivity.this);
                    tableRow.setLayoutParams(new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    tableRow.setPadding(0,10,0,0);
                    tableRow.setGravity(Gravity.CENTER);

                    //image to the left of the button
                    //Drawable img = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_add_photo_alternate_48px, null);
                    //img.setBounds(0,0,180,180);

                    Button btn = new Button(TrackingObjectActivity.this);
                    //btn.setCompoundDrawables(img,null,null,null);
                    btn.setCompoundDrawablePadding(20);
                    btn.setWidth(tl_sale_objects_list.getWidth());
                    btn.setLines(5);
                    btn.setText(TrackingObjectActivity.this.saleObjectContent(saleObject));
                    btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_gradient_corner_background, null));
                    btn.setOnClickListener(details_sale_object_listener);
                    btn.setId(saleObject.getId());

                    tableRow.addView(btn);
                    tl_sale_objects_list.addView(tableRow, new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
                }
            }
            else {
                Toast.makeText(TrackingObjectActivity.this, getResources().getString(R.string.objects_not_found_message), Toast.LENGTH_SHORT).show();
            }

        } catch (IndexOutOfBoundsException ex) {
            //ex.printStackTrace();
            Toast.makeText(TrackingObjectActivity.this, getResources().getString(R.string.objects_not_loaded_message), Toast.LENGTH_SHORT).show();
        }
    }
}