package be.jadoulle.examproject.asynchronousTask;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.HttpURLConnection;

import be.jadoulle.examproject.TrackingObjectActivity;
import be.jadoulle.examproject.pojo.SaleObject;
import be.jadoulle.examproject.pojo.User;
import be.jadoulle.examproject.utilitary.Utilities;

public class TrackingSaleObjectListAsyncTask extends AsyncTask<Void, Void, String> {

    private TrackingObjectActivity activity;

    public TrackingSaleObjectListAsyncTask(TrackingObjectActivity activity){
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String parameters = "id_user=" + this.activity.user.getId() + "&tracking_object=true";
        HttpURLConnection connection = Utilities.httpGetMethod(parameters);
        return Utilities.readServerJsonData(connection);
    }

    @Override
    protected void onPostExecute(String jsonString) {
        super.onPostExecute(jsonString);
        try {
            //System.out.println(jsonString);
            JSONArray jsonObjects = new JSONArray(jsonString);
            for (int i = 0; i < jsonObjects.length(); i++) {
                int id_user = jsonObjects.getJSONObject(i).getInt("id_user");
                User user = new User(id_user);
                SaleObject saleObject = new Gson().fromJson(jsonObjects.getJSONObject(i).toString(), SaleObject.class);
                saleObject.setUser(user);
                this.activity.allTrackingSaleObjects.add(saleObject);
            }
            this.activity.reloadSaleObjectList();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
