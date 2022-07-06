package be.jadoulle.examproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import be.jadoulle.examproject.asynchronousTask.UserCreateAsyncTask;
import be.jadoulle.examproject.pojo.User;

public class InscriptionActivity extends AppCompatActivity {
    public static final int INSCRIPTION_ACTIVITY_CODE = 2;

    private View.OnClickListener cancel_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent back_intent = new Intent();
            back_intent.putExtra("cancel_message", getString(R.string.cancel_message));
            setResult(RESULT_CANCELED, back_intent);
            finish();
        }
    };

    private View.OnClickListener confirm_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //get all view's elements
            EditText et_username = findViewById(R.id.et_username);
            EditText et_email = findViewById(R.id.et_email);
            EditText et_password = findViewById(R.id.et_password);
            EditText et_confirm_password = findViewById(R.id.et_confirm_password);
            EditText et_postal_address = findViewById(R.id.et_postal_address);
            EditText et_street_number = findViewById(R.id.et_street_number);
            EditText et_postal_code = findViewById(R.id.et_postal_code);
            EditText et_city = findViewById(R.id.et_city);

            new UserCreateAsyncTask(InscriptionActivity.this).execute(
                    et_username.getText().toString(),
                    et_email.getText().toString(),
                    et_password.getText().toString(),
                    et_confirm_password.getText().toString(),
                    et_postal_address.getText().toString(),
                    et_postal_code.getText().toString(),
                    et_city.getText().toString(),
                    et_street_number.getText().toString()
            );
        }
    };

    View.OnFocusChangeListener focus_listener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            EditText et_password = findViewById(R.id.et_password);
            EditText et_confirm_password = findViewById(R.id.et_confirm_password);
            if(!view.isInEditMode()){
                String confirmPassword = et_confirm_password.getText().toString();
                String password = et_password.getText().toString();
                if (password.equals(confirmPassword)){
                    Toast.makeText(InscriptionActivity.this, "mot de passe identique", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(InscriptionActivity.this, "mot de passe non identique", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        Button btn_cancel = findViewById(R.id.btn_cancel);
        Button btn_confirm = findViewById(R.id.btn_confirm);

        btn_cancel.setOnClickListener(cancel_listener);
        btn_confirm.setOnClickListener(confirm_listener);

        //TODO : temp
        EditText et_confirm_password = findViewById(R.id.et_confirm_password);
        et_confirm_password.setOnFocusChangeListener(focus_listener);

    }

    //methods called by AsyncTask
    public void confirmUserCreation(String message) {
        //TODO : if user is created or not
        Intent success_intent = new Intent();
        //TODO : faire un toast
        success_intent.putExtra("confirm_message", message);
        setResult(RESULT_OK, success_intent);
        finish();
    }
}