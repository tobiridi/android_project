package be.jadoulle.examproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import be.jadoulle.examproject.utilitary.GlobalSettings;

public class TrackingObjectActivity extends AppCompatActivity {
    public static final int TRACKING_OBJECT_ACTIVITY_CODE = GlobalSettings.TRACKING_OBJECT_ACTIVITY_CODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_object);
    }
}