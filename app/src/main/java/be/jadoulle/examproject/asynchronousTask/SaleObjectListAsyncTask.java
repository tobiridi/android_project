package be.jadoulle.examproject.asynchronousTask;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

import be.jadoulle.examproject.ObjectListActivity;
import be.jadoulle.examproject.pojo.SaleObject;
import be.jadoulle.examproject.pojo.User;
import be.jadoulle.examproject.utilitary.Utilities;


public class SaleObjectListAsyncTask extends AsyncTask<Void, Void, String> {

    private ObjectListActivity activity;

    public SaleObjectListAsyncTask(ObjectListActivity activity){
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String parameters = "all_sale_object=true";
        HttpURLConnection connection = Utilities.httpGetMethod(parameters);
        return Utilities.readServerJsonData(connection);
    }

    @Override
    protected void onPostExecute(String jsonString) {
        System.out.println(jsonString);
        super.onPostExecute(jsonString);
        try {
            JSONArray jsonObjects = new JSONArray(jsonString);
            for (int i = 0; i < jsonObjects.length(); i++) {
                SaleObject saleObject = new Gson().fromJson(jsonObjects.get(i).toString(), SaleObject.class);
                this.activity.saleObjects.add(saleObject);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
