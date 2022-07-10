package be.jadoulle.examproject.utilitary;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
    public static HttpURLConnection httpGetMethod(String parameters){
        HttpURLConnection connection = null;
        try {
            URL url = new URL(GlobalSettings.urlServer + "?" + parameters);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(GlobalSettings.connectionTimeout);
            //GET HTTP request to server
            connection.setRequestMethod("GET");
            //the connection is established
            connection.connect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static HttpURLConnection httpPostMethod(String parameters){
        HttpURLConnection connection = null;
        try {
            URL url = new URL(GlobalSettings.urlServer);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(GlobalSettings.connectionTimeout);
            connection.setDoOutput(true);
            //POST HTTP request to server
            connection.setRequestMethod("POST");

            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            //put parameters in POST request
            bufferedWriter.write(parameters);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            //the connection is established
            connection.connect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static String readServerJsonData(HttpURLConnection connection) {
        String jsonString = "";
        InputStream inputStream;

        try {
            if (connection.getResponseCode() != HttpURLConnection.HTTP_NOT_FOUND)
                inputStream = new BufferedInputStream(connection.getInputStream());
            else
                inputStream = new BufferedInputStream(connection.getErrorStream());

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            Scanner scanner = new Scanner(inputStreamReader);
            scanner.useDelimiter("\n");

            while (scanner.hasNext()){
                jsonString += scanner.next();
            }
            scanner.close();
            inputStreamReader.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }

    //validation methods
    public static boolean isValidPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword) && isValidLength(password, 4);
    }

    public static boolean isValidEmail(String emailAddress) {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailAddress);
        return matcher.find();
    }

    public static boolean isValidLength(String text, int minLength) {
        return text.length() >= minLength;
    }

    public static boolean isEmptyFields(String[] fields) {
        boolean isEmptyString = false;
        for (String field : fields) {
            isEmptyString = field.isEmpty();

            if (isEmptyString)
                break;
        }
        return isEmptyString;
    }

    public static boolean isPositivePrice(double number) {
        return number > 0;
    }
}
