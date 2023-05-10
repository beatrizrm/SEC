package pt.tecnico.BFTB.bftservice.tools;

public final class BFTLogger {

    // Logging attributes
    private static boolean _LOG_ERROR = true;
    private static boolean _LOG_INFO = true;
    private static boolean _LOG_DEBUG = false;

    // Method to log an error on the system out
    public static final void LogError(String message)
    {
        if(_LOG_ERROR)
            synchronized(BFTLogger.class) {
                System.err.println("[ERROR] " + message);
            }
    }

    // Method to log an error on the system out
    public static final void LogInfo(String message)
    {
        if(_LOG_INFO)
            synchronized(BFTLogger.class) {
                System.out.println("[INFO] " + message);
            }
    }

    // Method to log an error on the system out
    public static final void LogDebug(String message)
    {
        if(_LOG_DEBUG)
            synchronized(BFTLogger.class) {
                System.out.println("[DEBUG] " + message);
            }
    }
}
