package be.jadoulle.examproject.asynchronousTask;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;

import be.jadoulle.examproject.InscriptionActivity;
import be.jadoulle.examproject.R;
import be.jadoulle.examproject.utilitary.Utilities;

public class UserCreateAsyncTask extends AsyncTask<String, Void, String> {
    private InscriptionActivity activity;

    public UserCreateAsyncTask(InscriptionActivity activity){
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;

        if (!Utilities.isValidPassword(strings[2],strings[3])) {
            return this.activity.getResources().getString(R.string.password_not_matched_message);
        }
        if (!Utilities.isValidEmail(strings[1])) {
            return this.activity.getResources().getString(R.string.email_not_matched_message);
        }
        if (Utilities.isEmptyFields(strings)){
            return this.activity.getResources().getString(R.string.empty_fields_message);
        }

        String parameters = "username="+strings[0]+"&email="+strings[1]+"" +
                "&password="+strings[2]+"&postal_address="+strings[4]+"" +
                "&postal_code="+strings[5]+"&city="+strings[6]+"&street_number="+strings[7];

        try {
             connection = Utilities.httpPostMethod(parameters);

            if(connection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                connection.disconnect();
                return this.activity.getResources().getString(R.string.user_success_message);
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        connection.disconnect();
        return this.activity.getResources().getString(R.string.user_fail_message);
    }

    @Override
    protected void onPostExecute(String message) {
        super.onPostExecute(message);
        this.activity.confirmUserCreation(message);
    }
}
