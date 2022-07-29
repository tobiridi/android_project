package be.jadoulle.examproject.asynchronousTask;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;

import be.jadoulle.examproject.DetailsSaleObjectActivity;
import be.jadoulle.examproject.R;
import be.jadoulle.examproject.utilitary.Utilities;

public class TrackingSaleObjectAddAsyncTask extends AsyncTask<Void, Void, String> {

    private DetailsSaleObjectActivity activity;

    public TrackingSaleObjectAddAsyncTask(DetailsSaleObjectActivity activity){
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String message = null;
        String parameters = "id_user=" + this.activity.user.getId() + "&id_sale_object=" + this.activity.selectedSaleObject.getId();
        HttpURLConnection connection = Utilities.httpPostMethod(parameters);
        try {
            if (connection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                message = this.activity.getResources().getString(R.string.tracking_success_message);
            }
            else if (connection.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
                message = this.activity.getResources().getString(R.string.tracking_already_exists_message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    protected void onPostExecute(String message) {
        super.onPostExecute(message);
        this.activity.validateAction(message);
    }
}
