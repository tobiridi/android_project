package be.jadoulle.examproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NewSaleObjectActivity extends AppCompatActivity {
    public static final int NEW_SALE_OBJECT_ACTIVITY_CODE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sale_object);
    }
}