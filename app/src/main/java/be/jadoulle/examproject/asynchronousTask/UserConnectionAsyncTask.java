package be.jadoulle.examproject.asynchronousTask;

import android.os.AsyncTask;
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

public class UserConnectionAsyncTask extends AsyncTask<String, Void, String> {
    private MainActivity activity;

    public UserConnectionAsyncTask(MainActivity activity){
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        String jsonString = null;
        //connection to rpc php
        try {
            //GET parameters are sent via a string : ?param=val&param=val
            URL url = new URL(GlobalSettings.urlServer);

            //established the connection with parameters
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            //GET HTTP request to server
            //connection.setRequestMethod("GET");

            //POST HTTP request to server
            connection.setRequestMethod("POST");
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            //put parameters in POST request
            String parameters = "email=" + strings[0] + "&password=" + strings[1];
            bufferedWriter.write(parameters);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            //the connection is established
            connection.connect();

            if (connection.getResponseCode() == 200){
                //read data send from the server
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                Scanner scanner = new Scanner(inputStreamReader);
                scanner.useDelimiter("\n");

                while (scanner.hasNext()){
                    jsonString += scanner.next();
                }
                scanner.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        super.onPostExecute(jsonString);

        String text = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if (!jsonObject.isNull("id")){
                //transform JSON into object
                User user = new Gson().fromJson(jsonObject.toString(), User.class);
                this.activity.userSaleObjectList(user);
            }
            else if (!jsonObject.isNull("errorMessage")){
                text = this.activity.getString(R.string.login_user_not_found_message);
            }
            else{
                text = this.activity.getString(R.string.login_error_message);
            }
            Toast.makeText(this.activity, text, Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
