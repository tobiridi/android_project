package be.jadoulle.examproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import be.jadoulle.examproject.asynchronousTask.SaleObjectCreateAsyncTask;
import be.jadoulle.examproject.pojo.User;

public class NewSaleObjectActivity extends AppCompatActivity {
    public static final int NEW_SALE_OBJECT_ACTIVITY_CODE = 4;
    public User user = null;

    View.OnClickListener cancel_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setResult(RESULT_CANCELED);
            finish();
        }
    };

    View.OnClickListener confirm_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText et_object_name = findViewById(R.id.et_object_name);
            EditText et_object_type = findViewById(R.id.et_object_type);
            EditText et_object_description = findViewById(R.id.et_object_description);
            EditText et_object_price = findViewById(R.id.et_object_price);

            new SaleObjectCreateAsyncTask(NewSaleObjectActivity.this).execute(
                    et_object_name.getText().toString(),
                    et_object_type.getText().toString(),
                    et_object_description.getText().toString(),
                    et_object_price.getText().toString()
            );
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sale_object);

        Button btn_cancel = findViewById(R.id.btn_cancel);
        Button btn_confirm = findViewById(R.id.btn_confirm);

        btn_cancel.setOnClickListener(cancel_listener);
        btn_confirm.setOnClickListener(confirm_listener);

        this.user = (User) getIntent().getSerializableExtra("user");
        System.out.println("user : " + this.user);
    }

    public void confirmSaleObjectCreation(String message) {
        Toast.makeText(NewSaleObjectActivity.this, message, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }
}