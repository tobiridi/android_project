package be.jadoulle.examproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import be.jadoulle.examproject.asynchronousTask.UserConnectionAsyncTask;
import be.jadoulle.examproject.pojo.User;

public class MainActivity extends AppCompatActivity {
    public static final int MAIN_ACTIVITY_CODE = 1;

    private View.OnClickListener connection_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO : use async task to verif in db, script php ready
            EditText et_email = findViewById(R.id.et_email);
            EditText et_password = findViewById(R.id.et_password);
            new UserConnectionAsyncTask(MainActivity.this).execute(
                    et_email.getText().toString(),
                    et_password.getText().toString()
            );
            /**
             * if correct => connect the user and pass the user to "ObjectListActivity"
             * else display a toast
             */
            Toast.makeText(MainActivity.this, "Connection ...", Toast.LENGTH_SHORT).show();

            //test de trouver les coordonnées GPS depuis une adresse, fonctionne
            Geocoder geocoder = new Geocoder(MainActivity.this);
            try {
                String address = "37 Rue des Alouettes, Courcelles, 6180";
                Address gps_address = geocoder.getFromLocationName(address,1).get(0);
                System.out.println(gps_address);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    private View.OnClickListener inscription_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent inscription_intent = new Intent(MainActivity.this, InscriptionActivity.class);
            startActivityForResult(inscription_intent, MAIN_ACTIVITY_CODE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_connection = findViewById(R.id.btn_connection);
        Button btn_inscription = findViewById(R.id.btn_inscription);

        btn_connection.setOnClickListener(connection_listener);
        btn_inscription.setOnClickListener(inscription_listener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null && resultCode == RESULT_OK && requestCode == MAIN_ACTIVITY_CODE) {
            Toast.makeText(MainActivity.this, data.getStringExtra("confirm_message"), Toast.LENGTH_SHORT).show();
        }
        else if(data != null && resultCode == RESULT_CANCELED && requestCode == MAIN_ACTIVITY_CODE) {
            Toast.makeText(MainActivity.this, data.getStringExtra("cancel_message"), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Message ...", Toast.LENGTH_SHORT).show();
        }
    }

    //TODO : change name, go to object list activity
    public void changeActivity(User user) {
        Intent object_list_intent = new Intent(MainActivity.this, ObjectListActivity.class);
        object_list_intent.putExtra("user",user);
        startActivityForResult(object_list_intent,MAIN_ACTIVITY_CODE);
    }
}