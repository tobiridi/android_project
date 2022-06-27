package be.jadoulle.examproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int MAIN_ACTIVITY_CODE = 1;

    private View.OnClickListener connection_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent connection_intent = new Intent();
            //TODO : use async task to verif in db
            /**
             * if correct => connect the user
             * else display a toast
             */
            Toast.makeText(MainActivity.this, "Connection ...", Toast.LENGTH_SHORT).show();
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
        if(data != null && resultCode == RESULT_OK){
            Toast.makeText(MainActivity.this, data.getStringExtra("confirm_message"), Toast.LENGTH_SHORT).show();
        }
        else if(data != null && resultCode == RESULT_CANCELED){
            Toast.makeText(MainActivity.this, data.getStringExtra("cancel_message"), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this, "Message ...", Toast.LENGTH_SHORT).show();
        }
    }
}