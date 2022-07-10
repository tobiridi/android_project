package be.jadoulle.examproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import be.jadoulle.examproject.pojo.User;

public class ObjectListActivity extends AppCompatActivity {
    public static final int OBJECT_LIST_ACTIVITY_CODE = 3;
    public User user = null;

    private View.OnClickListener logout_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent back_intent = getIntent();
            back_intent.putExtra("logout_message", getString(R.string.logout_message));
            setResult(RESULT_CANCELED, back_intent);
            finish();
        }
    };

    private View.OnClickListener new_object_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent new_sale_object_intent = new Intent(ObjectListActivity.this, NewSaleObjectActivity.class);
            new_sale_object_intent.putExtra("user", ObjectListActivity.this.user);
            startActivityForResult(new_sale_object_intent, OBJECT_LIST_ACTIVITY_CODE);
        }
    };

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
            Toast.makeText(ObjectListActivity.this, "In progress ...", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_list);

        //get the user connected
        this.user = (User) getIntent().getSerializableExtra("user");
        System.out.println("user : " + this.user);

        Button btn_logout = findViewById(R.id.btn_logout);
        Button btn_add_new_object = findViewById(R.id.btn_add_new_object);
        Button btn_tracking_object = findViewById(R.id.btn_tracking_object);
        Button btn_max_distance_refresh = findViewById(R.id.btn_max_distance_refresh);
        //TODO : get all object around the user
        LinearLayout ll_sale_objects_list = findViewById(R.id.ll_sale_objects_list);

        btn_logout.setOnClickListener(logout_listener);
        btn_add_new_object.setOnClickListener(new_object_listener);
        btn_tracking_object.setOnClickListener(tracking_object_listener);
        btn_max_distance_refresh.setOnClickListener(max_distance_listener);

    }

    //test
    //onDestroy is called when the activity is finish from cancel button or previous button
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Toast.makeText(ObjectListActivity.this, "Destroyed", Toast.LENGTH_SHORT).show();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //TODO : useless
        if (data != null && requestCode == OBJECT_LIST_ACTIVITY_CODE){

        }
    }
}