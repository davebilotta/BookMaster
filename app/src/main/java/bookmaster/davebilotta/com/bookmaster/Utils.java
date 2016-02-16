package bookmaster.davebilotta.com.bookmaster;

import android.util.Log;

/**
 * Created by Dave on 2/16/2016.
 */
public class Utils {

    public static void log(String msg) {
        Log.v("Message", msg);
    }

    public static void log (String tag, String msg) {
       Log.v(tag, msg);
    }
}
