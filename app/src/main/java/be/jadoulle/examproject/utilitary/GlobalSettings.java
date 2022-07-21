package be.jadoulle.examproject.utilitary;

public final class GlobalSettings {
    public static String urlServer = "http://192.168.0.38/android/RPC_PHP.php";
    public static String urlServerHost = "http://192.168.0.38/android/";
    public static int connectionTimeout = 5000;     // milliseconds
    public static long gpsUpdateTime = 5000L;       // milliseconds
    public static float gpsMinDistance = 20;        // kilometer
    public static int passwordMinLength = 4;
    public static int textMinLength = 3;
    public static byte maxPictures = 10;            // set max images for a sale object
}
