package be.jadoulle.examproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import be.jadoulle.examproject.asynchronousTask.SaleObjectDetailsAsyncTask;
import be.jadoulle.examproject.asynchronousTask.TrackingSaleObjectAddAsyncTask;
import be.jadoulle.examproject.pojo.SaleObject;
import be.jadoulle.examproject.pojo.User;
import be.jadoulle.examproject.utilitary.GlobalSettings;

public class DetailsSaleObjectActivity extends AppCompatActivity {
    public static final int DETAILS_SALE_OBJECT_ACTIVITY_CODE = GlobalSettings.DETAILS_SALE_ACTIVITY_CODE;
    public User user = null;
    public SaleObject selectedSaleObject = null;
    private double saleObjectDistance = 0.0;
    public ArrayList<Bitmap> saleObjectBitmaps = new ArrayList<>();
    private boolean isAlreadyTracking = false;

    private View.OnClickListener validate_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CheckBox cb_tracking_object = findViewById(R.id.cb_tracking_object);
            if (cb_tracking_object.isChecked() && !(DetailsSaleObjectActivity.this.isAlreadyTracking)) {
                new TrackingSaleObjectAddAsyncTask(DetailsSaleObjectActivity.this).execute();
            }
            else {
                setResult(RESULT_OK);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_sale_object);

        Intent details_sale_object_intent = getIntent();
        this.user = (User) details_sale_object_intent.getSerializableExtra("user");
        this.selectedSaleObject = (SaleObject) details_sale_object_intent.getSerializableExtra("selectedSaleObject");
        this.saleObjectDistance = details_sale_object_intent.getDoubleExtra("saleObjectDistance",0.0);
        this.isAlreadyTracking = details_sale_object_intent.getBooleanExtra("isAlreadyTracking", false);

        //System.out.println(user);
        //System.out.println(selectedSaleObject);
        //System.out.println(saleObjectDistance);

        findViewById(R.id.btn_validate).setOnClickListener(validate_listener);

        //if pass from tracking object Activity
        CheckBox cb_tracking_object = findViewById(R.id.cb_tracking_object);
        cb_tracking_object.setChecked(this.isAlreadyTracking);

        new SaleObjectDetailsAsyncTask(DetailsSaleObjectActivity.this).execute();

    }

    public void setDetailsInformation() {
        TextView tv_object_name = findViewById(R.id.tv_object_name);
        TextView tv_object_type = findViewById(R.id.tv_object_type);
        TextView tv_object_description = findViewById(R.id.tv_object_description);
        TextView tv_object_price = findViewById(R.id.tv_object_price);
        TextView tv_seller = findViewById(R.id.tv_seller);
        TextView tv_object_distance = findViewById(R.id.tv_object_distance);

        String object_name = selectedSaleObject.getName();
        String object_type = selectedSaleObject.getType();
        String object_description = selectedSaleObject.getDescription();
        String object_price = String.format("%,.2f" + this.getResources().getString(R.string.currency),selectedSaleObject.getPrice());
        String seller = String.valueOf(selectedSaleObject.getUser().getUsername() + "\n" + selectedSaleObject.getUser().getEmail());
        String object_distance = String.format("%,.2f " + this.getResources().getString(R.string.distance_measure),saleObjectDistance /1000.0);

        tv_object_name.setText(object_name);
        tv_object_type.setText(object_type);
        tv_object_description.setText(object_description);
        tv_object_price.setText(object_price);
        tv_seller.setText(seller);
        tv_object_distance.setText(object_distance);

        //display images of the sale object
        if (saleObjectBitmaps.size() > 0) {
            LinearLayout ll_image_sale_object = findViewById(R.id.ll_image_sale_object);
            for (int i = 0; i < saleObjectBitmaps.size(); i++) {
                ImageView imageView = new ImageView(DetailsSaleObjectActivity.this);
                imageView.setImageBitmap(saleObjectBitmaps.get(i));
                imageView.setPaddingRelative(10,10,10,10);
                ll_image_sale_object.addView(imageView);
            }
        }
    }

    public void validateAction(String message) {
        Toast.makeText(DetailsSaleObjectActivity.this, message, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }
}