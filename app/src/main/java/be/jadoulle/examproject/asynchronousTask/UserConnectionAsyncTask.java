package be.jadoulle.examproject.asynchronousTask;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import be.jadoulle.examproject.MainActivity;
import be.jadoulle.examproject.pojo.User;
import be.jadoulle.examproject.utilitary.GlobalSettings;

public class UserConnectionAsyncTask extends AsyncTask<String, Void, User> {
    private MainActivity activity;

    public UserConnectionAsyncTask(MainActivity activity){
        this.activity = activity;
    }

    @Override
    protected User doInBackground(String... strings) {
        //test connection to rpc php
        //indique l'url de la machine à contactez
        try {
            //on peut créer une classe qui va contenir plusieurs variable statiques accessible dans tout le projet
            //comme par exemple l'url ru rpc a contactez
            //les paramètres en get sont envoyé en ?param=val&parm=val
            URL url = new URL(GlobalSettings.urlServer);
            //permet d'établir la connexion avec des paramètres
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000); //prècise le timeout
            //accède en GET au serveur
            //connection.setRequestMethod("GET");

            //accède en POST au serveur
            connection.setRequestMethod("POST");
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            //on met les paramètres
            String parameters = "text=coucuo&nombre=15";
            bufferedWriter.write(parameters);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            connection.connect(); //établi la connexion
            int responseCode = connection.getResponseCode(); //le code retourner par le serveur (200, 300, 400, ...)
            if (responseCode == 200){
                //lire les données reçues par le serveur
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                Scanner scanner = new Scanner(inputStreamReader);
                scanner.useDelimiter("\n");
                while (scanner.hasNext()){
                    System.out.println(scanner.next());
                }
                scanner.close();
                return new User();

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("erreur url");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("erreur autre");
        }




        //TODO : check if the user can be connected, rpc php ready
        //TODO : return null if the user is not found
        User user = null;

        //test temp
        user = new User(3,"toto","tony@hotmail.be","tony",
                "rue de totoutout",23,"4567","Charleroi");
        return user;
    }

    @Override
    protected void onPostExecute(User user) {
        //récupèrer les infos au format json
        try {
            JSONObject jsonObject = new JSONObject(user.toString());
            //vérifier si une clé se trouve dans le json
            if(jsonObject.isNull("test")){
                System.out.println("Erreur dans le json");
            }
            //faire des get sur "jsonObject" pour récupèrer des infos

        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Erreur dans le json");
        }


        super.onPostExecute(user);
        //TODO : call activity method
        this.activity.changeActivity(user);
    }
}
