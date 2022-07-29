package be.jadoulle.examproject.utilitary;

public final class GlobalSettings {
    //pc maison
    //public static String urlServer                    = "http://192.168.0.38/android/RPC_PHP.php";
    //public static String urlServerHost                = "http://192.168.0.38/android/";
    //pc portable
    public static final String urlServer                    = "http://192.168.99.123/android/RPC_PHP.php";
    public static final int connectionTimeout               = 5000;        // milliseconds
    public static final long gpsUpdateTime                  = 5000L;       // milliseconds
    public static final float gpsDefaultDistance            = 20.0F;       // kilometer
    public static final int passwordMinLength               = 4;
    public static final int saleObjectNameMinLength         = 3;
    public static final int saleObjectDescriptionMinLength  = 10;
    public static final byte maxPictures                    = 5;           // set max images for one sale object

    //ACTIVITY_CODE
    public static final int MAIN_ACTIVITY_CODE              = 1;
    public static final int INSCRIPTION_ACTIVITY_CODE       = 2;
    public static final int OBJECT_LIST_ACTIVITY_CODE       = 3;
    public static final int NEW_SALE_OBJECT_ACTIVITY_CODE   = 4;
    public static final int TRACKING_OBJECT_ACTIVITY_CODE   = 5;
    public static final int DETAILS_SALE_ACTIVITY_CODE      = 6;
}
