package be.jadoulle.examproject.asynchronousTask;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.jadoulle.examproject.InscriptionActivity;
import be.jadoulle.examproject.R;
import be.jadoulle.examproject.pojo.User;
import be.jadoulle.examproject.utilitary.Utilities;

public class UserCreateAsyncTask extends AsyncTask<String, Void, String> {
    private InscriptionActivity activity;

    public UserCreateAsyncTask(InscriptionActivity activity){
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        if (!isValidPassword(strings[2],strings[3])){
            return this.activity.getString(R.string.password_not_matched_message);
        }

        String parameters = "username="+strings[0]+"&email="+strings[1]+"" +
                "&password="+strings[2]+"&postal_address="+strings[4]+"" +
                "&street_number="+strings[5]+"&postal_code="+strings[6]+"&city="+strings[7];

        try {
            HttpURLConnection connection = Utilities.httpPostMethod(parameters);

            if(connection.getResponseCode() == 201) {
                return this.activity.getString(R.string.user_success_message);
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        return this.activity.getString(R.string.user_fail_message);
    }

    @Override
    protected void onPostExecute(String message) {
        super.onPostExecute(message);
        this.activity.confirmUserCreation(message);
    }

    //validation methods
    private boolean isValidPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword) && password.length() >= 3;
    }

    private boolean isValidEmail(String emailAddress) {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailAddress);
        return matcher.find();
    }

    private boolean isValidLength(String text, int minLength) {
        return text.length() >= minLength;
    }
}
