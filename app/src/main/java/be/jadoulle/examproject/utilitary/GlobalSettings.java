package be.jadoulle.examproject.utilitary;

public final class GlobalSettings {
    //pc maison
    //public static String urlServer                    = "http://192.168.0.38/android/RPC_PHP.php";
    //pc portable
    public static String urlServer                    = "http://192.168.99.123/android/RPC_PHP.php";
    //public static String urlServerHost                = "http://192.168.0.38/android/";
    public static int connectionTimeout               = 5000;        // milliseconds
    public static long gpsUpdateTime                  = 5000L;       // milliseconds
    public static float gpsDefaultDistance            = 20.0F;       // kilometer
    public static int passwordMinLength               = 4;
    public static int saleObjectNameMinLength         = 3;
    public static int saleObjectDescriptionMinLength  = 10;
    public static byte maxPictures                    = 5;           // set max images for a sale object
}
