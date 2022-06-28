package be.jadoulle.examproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import be.jadoulle.examproject.asynchronousTask.UserCreateAsyncTask;

public class InscriptionActivity extends AppCompatActivity {
    public static final int INSCRIPTION_ACTIVITY_CODE = 2;

    private View.OnClickListener cancel_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent back_intent = new Intent();
            back_intent.putExtra("cancel_message", getString(R.string.cancel_text));
            setResult(RESULT_CANCELED, back_intent);
            finish();
        }
    };

    private View.OnClickListener confirm_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //get all elements
            ArrayList<EditText> allUserInfo = new ArrayList<>();
            allUserInfo.add(findViewById(R.id.et_username));
            allUserInfo.add(findViewById(R.id.et_email));
            allUserInfo.add(findViewById(R.id.et_password));
            allUserInfo.add(findViewById(R.id.et_confirm_password));
            allUserInfo.add(findViewById(R.id.et_postal_address));
            allUserInfo.add(findViewById(R.id.et_postal_code));
            allUserInfo.add(findViewById(R.id.et_city));


            //TODO : insert a new user in DB (asyncTask) + back to main activity
            //TODO : send the form data as parameter to AsyncTask
            new UserCreateAsyncTask(InscriptionActivity.this).execute();
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
    }

    //methods called by AsyncTask
    public void endMessage(String message){
        Intent success_intent = new Intent();
        success_intent.putExtra("confirm_message", message);
        setResult(RESULT_OK, success_intent);
        finish();
    }
}