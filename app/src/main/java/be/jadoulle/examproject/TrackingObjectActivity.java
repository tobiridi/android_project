package be.jadoulle.examproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TrackingObjectActivity extends AppCompatActivity {
    public static final int TRACKING_OBJECT_ACTIVITY_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_object);
    }
}