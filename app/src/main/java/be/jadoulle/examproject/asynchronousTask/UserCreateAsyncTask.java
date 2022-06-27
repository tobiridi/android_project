package be.jadoulle.examproject.asynchronousTask;

import android.os.AsyncTask;

import be.jadoulle.examproject.InscriptionActivity;
import be.jadoulle.examproject.R;

public class UserCreateAsyncTask extends AsyncTask<Void, Void, Void> {
    private InscriptionActivity activity;

    public UserCreateAsyncTask(InscriptionActivity activity){
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        //TODO : check if values are correct + create a new user in the DB (call RPC PHP)
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        //TODO : call activity method
        this.activity.endMessage(this.activity.getString(R.string.user_success_message));
    }
}
