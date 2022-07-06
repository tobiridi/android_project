package be.jadoulle.examproject.asynchronousTask;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;

import be.jadoulle.examproject.NewSaleObjectActivity;
import be.jadoulle.examproject.R;
import be.jadoulle.examproject.utilitary.Utilities;

public class SaleObjectCreateAsyncTask extends AsyncTask<String, Void, String> {

    private NewSaleObjectActivity activity;

    public SaleObjectCreateAsyncTask(NewSaleObjectActivity activity){
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        int id_user = this.activity.user.getId();
        int street_number = this.activity.user.getStreet_number();
        String address = this.activity.user.getPostal_address();
        String city = this.activity.user.getCity();
        String postal_code = this.activity.user.getPostal_code();

        //find GPS position from address
        Geocoder geocoder = new Geocoder(this.activity);

        try {
            //String addressTemp = "rue des alouettes 37, 6180 Courcelles";
            String fullAddress = address + " " + street_number + ", " + postal_code + " " + city;
            Address gps_address = geocoder.getFromLocationName(fullAddress, 1).get(0);
            //System.out.println(gps_address);
            double longitude = gps_address.getLongitude();
            double latitude = gps_address.getLatitude();

            String parameters = "object_name="+strings[0]+"&object_type="+strings[1]+"" +
                    "&object_description="+strings[2]+"&price="+strings[3]+"" +
                    "&id_user="+id_user+"&longitude="+longitude+"&latitude="+latitude;

            HttpURLConnection connection = Utilities.httpPostMethod(parameters);

            if(connection.getResponseCode() == 201) {
                return this.activity.getString(R.string.sale_object_success_message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.activity.getString(R.string.sale_object_fail_message);
    }

    @Override
    protected void onPostExecute(String message) {
        super.onPostExecute(message);
        this.activity.confirmSaleObjectCreation(message);
    }
}
