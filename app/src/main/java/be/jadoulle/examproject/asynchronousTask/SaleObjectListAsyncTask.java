package be.jadoulle.examproject.asynchronousTask;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

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
        super.onPostExecute(jsonString);
        //System.out.println(jsonString);
        try {
            JSONArray jsonObjects = new JSONArray(jsonString);
            for (int i = 0; i < jsonObjects.length(); i++) {
                int id_user = jsonObjects.getJSONObject(i).getInt("id_user");
                User user = new User(id_user);
                SaleObject saleObject = new Gson().fromJson(jsonObjects.getJSONObject(i).toString(), SaleObject.class);
                saleObject.setUser(user);
                //System.out.println("ajouter dans allSaleObject : " + saleObject);
                this.activity.allSaleObjects.add(saleObject);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
