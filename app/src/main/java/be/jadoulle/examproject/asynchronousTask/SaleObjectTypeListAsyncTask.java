package be.jadoulle.examproject.asynchronousTask;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import be.jadoulle.examproject.SaleObjectTypeActivity;
import be.jadoulle.examproject.pojo.SaleObject;
import be.jadoulle.examproject.pojo.User;
import be.jadoulle.examproject.utilitary.Utilities;

public class SaleObjectTypeListAsyncTask extends AsyncTask<Void, Void, String> {

    private SaleObjectTypeActivity activity;

    public SaleObjectTypeListAsyncTask(SaleObjectTypeActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String parameters = "sale_object_types=true";
        HttpURLConnection connection = Utilities.httpGetMethod(parameters);

        return Utilities.readServerJsonData(connection);
    }

    @Override
    protected void onPostExecute(String jsonString) {
        super.onPostExecute(jsonString);
        ArrayList<String> types = new ArrayList<>();
        String language = Locale.getDefault().getLanguage();

        try {
            //System.out.println("réponse en json : " +jsonString);
            JSONObject jsonObjects = new JSONObject(jsonString);

            if (language.equals("en")) {
                JSONArray array = jsonObjects.getJSONArray("english");
                for (int i = 0; i < array.length(); i++) {
                    types.add(array.getString(i));
                }
            }
            else if (language.equals("fr")) {
                JSONArray array = jsonObjects.getJSONArray("french");
                for (int i = 0; i < array.length(); i++) {
                    types.add(array.getString(i));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.activity.uploadSaleObjectTypes(types.toArray(new String[0]));
    }
}
