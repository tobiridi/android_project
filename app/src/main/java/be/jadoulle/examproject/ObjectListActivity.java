package be.jadoulle.examproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import be.jadoulle.examproject.pojo.User;

public class ObjectListActivity extends AppCompatActivity {
    public static final int OBJECT_LIST_ACTIVITY_CODE = 3;
    private User user = null;

    private View.OnClickListener logout_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent back_intent = new Intent();
            back_intent.putExtra("cancel_message", getString(R.string.logout_message));
            setResult(RESULT_CANCELED, back_intent);
            finish();
        }
    };

    private View.OnClickListener new_object_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent new_sale_object_intent = new Intent(ObjectListActivity.this, NewSaleObjectActivity.class);
            new_sale_object_intent.putExtra("user", ObjectListActivity.this.user);
            startActivityForResult(new_sale_object_intent,OBJECT_LIST_ACTIVITY_CODE);
        }
    };

    private View.OnClickListener tracking_object_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent tracking_object_intent = new Intent(ObjectListActivity.this, TrackingObjectActivity.class);
            tracking_object_intent.putExtra("user", ObjectListActivity.this.user);
            startActivityForResult(tracking_object_intent,OBJECT_LIST_ACTIVITY_CODE);
        }
    };

    private View.OnClickListener details_object_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_list);

        //get the user connected
        Intent mainIntent = getIntent();
        ObjectListActivity.this.user = (User) mainIntent.getSerializableExtra("user");
        System.out.println(ObjectListActivity.this.user);

        Button btn_logout = findViewById(R.id.btn_logout);
        Button btn_add_new_object = findViewById(R.id.btn_add_new_object);
        Button btn_tracking_object = findViewById(R.id.btn_tracking_object);

        btn_logout.setOnClickListener(logout_listener);
        btn_add_new_object.setOnClickListener(new_object_listener);
        btn_tracking_object.setOnClickListener(tracking_object_listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null && resultCode == RESULT_OK && requestCode == OBJECT_LIST_ACTIVITY_CODE) {
            Toast.makeText(ObjectListActivity.this, data.getStringExtra("confirm_message"), Toast.LENGTH_SHORT).show();
        }
        else if(data != null && resultCode == RESULT_CANCELED && requestCode == OBJECT_LIST_ACTIVITY_CODE) {
            Toast.makeText(ObjectListActivity.this, data.getStringExtra("cancel_message"), Toast.LENGTH_SHORT).show();
        }
        else{
            //nothing
            //Toast.makeText(ObjectListActivity.this, "Message ...", Toast.LENGTH_SHORT).show();
        }
    }
}