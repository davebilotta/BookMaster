package bookmaster.davebilotta.com.bookmaster;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Dave on 2/16/2016.
 */
public class Utils {

    public static void log(String msg) {

        Log.v("Message", "***** " + msg + " *****");
    }

    public static void log (String tag, String msg) {
       Log.v(tag, "***** " + msg + " *****");
    }

    public static void showUserMessage(Context context,String message) {
        // TODO: How get context of what called this without passing in?
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
    }
}
