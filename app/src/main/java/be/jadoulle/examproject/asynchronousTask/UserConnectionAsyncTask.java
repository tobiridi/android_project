package be.jadoulle.examproject.asynchronousTask;

import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import be.jadoulle.examproject.MainActivity;
import be.jadoulle.examproject.R;
import be.jadoulle.examproject.pojo.User;
import be.jadoulle.examproject.utilitary.GlobalSettings;
import be.jadoulle.examproject.utilitary.Utilities;

public class UserConnectionAsyncTask extends AsyncTask<String, Void, String> {
    private MainActivity activity;

    public UserConnectionAsyncTask(MainActivity activity){
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        if (Utilities.isEmptyFields(strings)) {
            return this.activity.getResources().getString(R.string.empty_fields_message);
        }
        if (!Utilities.isValidEmail(strings[0])) {
            return this.activity.getResources().getString(R.string.email_not_matched_message);
        }

        //connection to rpc php
        String parameters = "email=" + strings[0] + "&password=" + strings[1];
        HttpURLConnection connection = Utilities.httpPostMethod(parameters);

        //read data send from the server
        String data = Utilities.readServerJsonData(connection);

        if (data == null)
            return this.activity.getResources().getString(R.string.login_error_message);

        connection.disconnect();
        return data;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        super.onPostExecute(jsonString);
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if (!jsonObject.isNull("id")){
                //transform JSON into object
                User user = new Gson().fromJson(jsonObject.toString(), User.class);
                this.activity.userSaleObjectList(user);
            }
            else if (!jsonObject.isNull("errorMessage")){
                String text  = this.activity.getResources().getString(R.string.login_user_not_found_message);
                Toast.makeText(this.activity, text, Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            ///e.printStackTrace();
            //System.out.println(jsonString);
            String text = this.activity.getResources().getString(R.string.login_error_message);
            Toast.makeText(this.activity, text, Toast.LENGTH_SHORT).show();
        }
    }
}
