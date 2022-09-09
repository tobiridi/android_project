package be.jadoulle.examproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import be.jadoulle.examproject.asynchronousTask.SaleObjectCreateAsyncTask;
import be.jadoulle.examproject.asynchronousTask.SaleObjectTypeListAsyncTask;
import be.jadoulle.examproject.pojo.User;
import be.jadoulle.examproject.utilitary.GlobalSettings;

public class SaleObjectTypeActivity extends AppCompatActivity {

    public static final int SALE_OBJECT_TYPE_ACTIVITY_CODE = GlobalSettings.SALE_OBJECT_TYPE_ACTIVITY_CODE;

    private View.OnClickListener cancel_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setResult(RESULT_CANCELED);
            finish();
        }
    };

    private AdapterView.OnItemClickListener selected_type_listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ListView lv_saleObjectTypes = findViewById(R.id.lv_saleObjectTypes);
            Object o = lv_saleObjectTypes.getItemAtPosition(i);
            String selected = o.toString();

            //back to NewSaleObjectActivity
            Intent saleObjectTypeIntent = new Intent();
            saleObjectTypeIntent.putExtra("objectType", selected);
            setResult(RESULT_OK, saleObjectTypeIntent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_object_type);

        Button btn_cancel = findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(cancel_listener);

        //get types available
        new SaleObjectTypeListAsyncTask(SaleObjectTypeActivity.this).execute();
    }

    public void uploadSaleObjectTypes(String[] types) {

        ListView lv_saleObjectTypes = findViewById(R.id.lv_saleObjectTypes);
        if (types != null){
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SaleObjectTypeActivity.this, android.R.layout.simple_list_item_1, types);
            lv_saleObjectTypes.setAdapter(arrayAdapter);
        }

        //get the selected item
        lv_saleObjectTypes.setOnItemClickListener(selected_type_listener);
    }
}