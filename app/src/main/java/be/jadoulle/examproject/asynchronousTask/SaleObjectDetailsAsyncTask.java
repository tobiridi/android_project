package be.jadoulle.examproject.asynchronousTask;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

import be.jadoulle.examproject.DetailsSaleObjectActivity;
import be.jadoulle.examproject.pojo.User;
import be.jadoulle.examproject.utilitary.Utilities;

public class SaleObjectDetailsAsyncTask extends AsyncTask<String, Void, String> {

    private DetailsSaleObjectActivity activity;

    public SaleObjectDetailsAsyncTask(DetailsSaleObjectActivity activity){
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        String parameters = "id_sale_object=" + this.activity.selectedSaleObject.getId();
        HttpURLConnection connection = Utilities.httpGetMethod(parameters);
        try {
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return Utilities.readServerJsonData(connection);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        super.onPostExecute(jsonString);
        try {
            System.out.println(jsonString);
            JSONObject jsonObject = new JSONObject(jsonString);
            if (!jsonObject.isNull("seller")){
                //transform JSON into object
                User seller = new Gson().fromJson(jsonObject.get("seller").toString(), User.class);
                this.activity.selectedSaleObject.setUser(seller);

            }
            if (!jsonObject.isNull("images")) {
                JSONArray jsonArray = jsonObject.getJSONArray("images");
                //System.out.println("number of elements : " + jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    //get base64
                    Bitmap bitmap = Utilities.base64ToBitmap(jsonArray.get(i).toString());
                    this.activity.saleObjectBitmaps.add(bitmap);
                }
            }
            this.activity.setDetailsInformation();
        } catch (JSONException e) {
            e.printStackTrace();
            //System.out.println(jsonString);
        }
    }
}
