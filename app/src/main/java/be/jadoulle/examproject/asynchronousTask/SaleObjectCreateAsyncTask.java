package be.jadoulle.examproject.asynchronousTask;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

import be.jadoulle.examproject.NewSaleObjectActivity;
import be.jadoulle.examproject.R;
import be.jadoulle.examproject.utilitary.GlobalSettings;
import be.jadoulle.examproject.utilitary.Utilities;

public class SaleObjectCreateAsyncTask extends AsyncTask<String, Void, String> {

    private NewSaleObjectActivity activity;

    public SaleObjectCreateAsyncTask(NewSaleObjectActivity activity){
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {

        if (Utilities.isEmptyFields(strings)) {
            return this.activity.getResources().getString(R.string.empty_fields_message);
        }

        if (!Utilities.isPositivePrice(Double.parseDouble(strings[3]))) {
            return this.activity.getResources().getString(R.string.price_not_positive_message);
        }

        if (!Utilities.isValidLength(strings[0], GlobalSettings.saleObjectNameMinLength)) {
            return "\"" + this.activity.getResources().getString(R.string.object_name_text) + "\" : " + this.activity.getResources().getString(R.string.text_too_short_message);
        }

        if (!Utilities.isValidLength(strings[2], GlobalSettings.saleObjectDescriptionMinLength)) {
            return "\"" + this.activity.getResources().getString(R.string.object_description_text) + "\" : " + this.activity.getResources().getString(R.string.text_too_short_message);
        }

        int id_user = this.activity.user.getId();
        int street_number = this.activity.user.getStreet_number();
        String address = this.activity.user.getPostal_address();
        String city = this.activity.user.getCity();
        String postal_code = this.activity.user.getPostal_code();

        //find GPS position from address
        Geocoder geocoder = new Geocoder(this.activity);

        try {
            //address example : "rue des alouettes 37, 6180 Courcelles"
            String fullAddress = address + " " + street_number + ", " + postal_code + " " + city;
            Address gps_address = geocoder.getFromLocationName(fullAddress, 1).get(0);

            //System.out.println(gps_address);

            double longitude = gps_address.getLongitude();
            double latitude = gps_address.getLatitude();

            String parameters = "object_name="+strings[0]+"&object_type="+strings[1]+"" +
                    "&object_description="+strings[2]+"&price="+strings[3]+"" +
                    "&id_user="+id_user+"&longitude="+longitude+"&latitude="+latitude;

            HttpURLConnection connection1 = Utilities.httpPostMethod(parameters);

            if (connection1.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                //get the id_sale_object of the sale object
                String last_sale_object_id_json = Utilities.readServerJsonData(connection1);
                int id_sale_object = 0;
                try {
                    if (last_sale_object_id_json != null) {
                        JSONObject jsonObject = new JSONObject(last_sale_object_id_json);
                        if (!jsonObject.isNull("last_sale_object_id")){
                            id_sale_object = jsonObject.getInt("last_sale_object_id");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //System.out.println("id sale object : "  + id_sale_object);

                // TODO : Warning : i send the bitmap in base64 to the server
                //save images of sale object
                for (int i = 0; i < this.activity.encodedBitmaps.size(); i++) {
                    parameters = "img_base64=" + this.activity.encodedBitmaps.get(i) + "&id_sale_object=" + id_sale_object;

                    HttpURLConnection connection2 = Utilities.httpPostMethod(parameters);

                    if (connection2.getResponseCode() != HttpURLConnection.HTTP_CREATED)
                        break;
                }

                return this.activity.getResources().getString(R.string.sale_object_success_message);
            }

            connection1.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

       return this.activity.getResources().getString(R.string.sale_object_fail_message);
    }

    @Override
    protected void onPostExecute(String message) {
        super.onPostExecute(message);
        this.activity.confirmSaleObjectCreation(message);
    }
}
