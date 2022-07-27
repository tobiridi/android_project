package be.jadoulle.examproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import be.jadoulle.examproject.asynchronousTask.SaleObjectCreateAsyncTask;
import be.jadoulle.examproject.asynchronousTask.TrackingSaleObjectAddAsyncTask;
import be.jadoulle.examproject.pojo.SaleObject;
import be.jadoulle.examproject.pojo.User;

public class DetailsSaleObjectActivity extends AppCompatActivity {
    public static final int DETAILS_SALE_OBJECT_ACTIVITY_CODE = 6;
    public User user = null;
    public SaleObject selectedSaleObject = null;
    private double saleObjectDistance = 0.0;

    private View.OnClickListener validate_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CheckBox cb_tracking_object = findViewById(R.id.cb_tracking_object);
            if (cb_tracking_object.isChecked()) {
                new TrackingSaleObjectAddAsyncTask(DetailsSaleObjectActivity.this).execute();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_sale_object);

        Intent details_sale_object_intent = getIntent();
        DetailsSaleObjectActivity.this.user = (User) details_sale_object_intent.getSerializableExtra("user");
        DetailsSaleObjectActivity.this.selectedSaleObject = (SaleObject) details_sale_object_intent.getSerializableExtra("selectedSaleObject");
        DetailsSaleObjectActivity.this.saleObjectDistance = details_sale_object_intent.getDoubleExtra("saleObjectDistance",0.0);

        System.out.println(user);
        System.out.println(selectedSaleObject);
        System.out.println(saleObjectDistance);

        findViewById(R.id.btn_validate).setOnClickListener(validate_listener);

        //TODO : async task set information

    }

    public void setDetailsInformation(User seller) {
        TextView tv_object_name = findViewById(R.id.tv_object_name);
        TextView tv_object_type = findViewById(R.id.tv_object_type);
        TextView tv_object_description = findViewById(R.id.tv_object_description);
        TextView tv_object_price = findViewById(R.id.tv_object_price);
        TextView tv_username_seller = findViewById(R.id.tv_username_seller);
        TextView tv_object_distance = findViewById(R.id.tv_object_distance);

        tv_object_name.setText(selectedSaleObject.getName());
        tv_object_type.setText(selectedSaleObject.getType());
        tv_object_description.setText(selectedSaleObject.getDescription());
        tv_object_price.setText(String.valueOf(selectedSaleObject.getPrice() + " " + this.getResources().getString(R.string.currency)));
        tv_username_seller.setText(String.valueOf(seller.getUsername() + "\n" + seller.getEmail()));
        tv_object_distance.setText(String.valueOf(saleObjectDistance + " " + this.getResources().getString(R.string.distance_measure)));
    }

    public void validateAction() {
        String text = DetailsSaleObjectActivity.this.getResources().getString(R.string.validation_success_message);
        Toast.makeText(DetailsSaleObjectActivity.this, text, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }
}