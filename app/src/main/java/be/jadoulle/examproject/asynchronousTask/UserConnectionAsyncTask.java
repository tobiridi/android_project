package be.jadoulle.examproject.asynchronousTask;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import be.jadoulle.examproject.MainActivity;

public class UserConnectionAsyncTask extends AsyncTask<String, Void, Void> {
    private MainActivity activity;

    public UserConnectionAsyncTask(MainActivity activity){
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(String... strings) {
        //TODO : check if the user can be connected, rpc php ready
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        //TODO : call activity method
        this.activity.changeActivity();
    }
}
