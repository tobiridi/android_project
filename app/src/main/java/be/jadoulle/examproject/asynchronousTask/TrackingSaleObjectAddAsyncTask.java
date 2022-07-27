package be.jadoulle.examproject.asynchronousTask;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;

import be.jadoulle.examproject.DetailsSaleObjectActivity;
import be.jadoulle.examproject.utilitary.Utilities;

public class TrackingSaleObjectAddAsyncTask extends AsyncTask<Void, Void, Void> {

    private DetailsSaleObjectActivity activity;

    public TrackingSaleObjectAddAsyncTask(DetailsSaleObjectActivity activity){
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String parameters = "id_user=" + this.activity.user.getId() + "&id_sale_object=" + this.activity.selectedSaleObject.getId();
        HttpURLConnection connection = Utilities.httpPostMethod(parameters);
        connection.disconnect();
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        super.onPostExecute(v);
        this.activity.validateAction();
//        User seller = new User(100,"toto","toto@hotmail.com","totototo","rue churchill",45,"6180","Courcelles");
//        float distanceUserSaleObject = Float.parseFloat(s[1]);
//        this.activity.setDetailsInformation(seller);
    }
}
