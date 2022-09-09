package be.jadoulle.examproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import be.jadoulle.examproject.asynchronousTask.SaleObjectCreateAsyncTask;
import be.jadoulle.examproject.pojo.User;
import be.jadoulle.examproject.utilitary.GlobalSettings;
import be.jadoulle.examproject.utilitary.Utilities;

public class NewSaleObjectActivity extends AppCompatActivity {
    public static final int NEW_SALE_OBJECT_ACTIVITY_CODE = GlobalSettings.NEW_SALE_OBJECT_ACTIVITY_CODE;
    public User user = null;
    public ArrayList<String> encodedBitmaps = new ArrayList<>();
    private String objectType = null;

    private View.OnClickListener cancel_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setResult(RESULT_CANCELED);
            finish();
        }
    };

    private View.OnClickListener confirm_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText et_object_name = findViewById(R.id.et_object_name);
            EditText et_object_description = findViewById(R.id.et_object_description);
            EditText et_object_price = findViewById(R.id.et_object_price);

            new SaleObjectCreateAsyncTask(NewSaleObjectActivity.this).execute(
                    et_object_name.getText().toString(),
                    NewSaleObjectActivity.this.objectType,
                    et_object_description.getText().toString(),
                    et_object_price.getText().toString()
            );
        }
    };

    private View.OnClickListener add_image_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //use camera
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent, NEW_SALE_OBJECT_ACTIVITY_CODE);
        }
    };

    private View.OnClickListener select_object_type_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //select object type
            Intent objectTypeIntent = new Intent(NewSaleObjectActivity.this, SaleObjectTypeActivity.class);
            startActivityForResult(objectTypeIntent,NEW_SALE_OBJECT_ACTIVITY_CODE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sale_object);

        Button btn_cancel = findViewById(R.id.btn_cancel);
        Button btn_confirm = findViewById(R.id.btn_confirm);
        Button btn_add_image_sale_object = findViewById(R.id.btn_add_image_sale_object);
        Button btn_select_object_type = findViewById(R.id.btn_select_object_type);

        btn_cancel.setOnClickListener(cancel_listener);
        btn_confirm.setOnClickListener(confirm_listener);
        btn_add_image_sale_object.setOnClickListener(add_image_listener);
        btn_select_object_type.setOnClickListener(select_object_type_listener);

        this.user = (User) getIntent().getSerializableExtra("user");
        //System.out.println("user : " + this.user);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //check max pictures authorized
        LinearLayout ll_image_sale_object = findViewById(R.id.ll_image_sale_object);
        if (ll_image_sale_object.getChildCount() >= GlobalSettings.maxPictures) {
            Button btn_add_image_sale_object = findViewById(R.id.btn_add_image_sale_object);
            btn_add_image_sale_object.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when take pictures of the object
        if (data != null && requestCode == NEW_SALE_OBJECT_ACTIVITY_CODE) {
            if (resultCode == RESULT_OK) {
                String selectedObjectType = data.getStringExtra("objectType");
                if (selectedObjectType != null) {
                    NewSaleObjectActivity.this.objectType = selectedObjectType;
                }
                else {
                    LinearLayout ll_image_sale_object = findViewById(R.id.ll_image_sale_object);

                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");

                    //convert bitmap to base64
                    encodedBitmaps.add(Utilities.bitmapToBase64(bitmap));

                    //add the image(s) to the list of sale object's images
                    ImageView imageView = new ImageView(NewSaleObjectActivity.this);
                    imageView.setImageBitmap(bitmap);
                    imageView.setPaddingRelative(10, 10, 10, 10);

                    ll_image_sale_object.addView(imageView);
                }
            }
        }
    }

    public void confirmSaleObjectCreation(String message) {
        Toast.makeText(NewSaleObjectActivity.this, message, Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }
}