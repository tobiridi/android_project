package be.jadoulle.examproject.asynchronousTask;

import android.os.AsyncTask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.jadoulle.examproject.InscriptionActivity;
import be.jadoulle.examproject.R;
import be.jadoulle.examproject.pojo.User;

public class UserCreateAsyncTask extends AsyncTask<String, Void, String> {
    private InscriptionActivity activity;

    public UserCreateAsyncTask(InscriptionActivity activity){
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        String message = null;
        //TODO : check if values are correct + create a new user in the DB (call RPC PHP)
        for (String string : strings) {
            System.out.println(string);
        }

        //TODO : a supprimer
        System.out.println("verif password: " + isValidPassword(strings[2], strings[3]));
        System.out.println("verif email: " + isValidEmail(strings[1]));

        if(isValidPassword(strings[2], strings[3])){
            if(isValidEmail(strings[1])){
                //TODO : contact RPC php
                message += "user created";
            }
            else {
                message += "invalid emailAddress";
            }
        }
        else {
            message += "invalid password";
        }

        return message;
    }

    @Override
    protected void onPostExecute(String message) {
        super.onPostExecute(message);
        this.activity.successUserCreation(message);
    }

    //verification methods
    private boolean isValidPassword(String password, String confirmPassword){
        return password.equals(confirmPassword) && isValidLength(password, 3);
    }

    private boolean isValidEmail(String emailAddress){
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailAddress);
        return matcher.find();
    }

    private boolean isValidLength(String text, int minLength){
        return text.length() >= minLength;
    }
}
