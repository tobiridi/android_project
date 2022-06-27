package be.jadoulle.examproject.asynchronousTask;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import be.jadoulle.examproject.MainActivity;

public class UserConnectionAsyncTask extends AsyncTask<Void, Void, Void> {
    private MainActivity activity;

    public UserConnectionAsyncTask(MainActivity activity){
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        //TODO : check if the user can be connected
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        //TODO : call activity method
    }
}
