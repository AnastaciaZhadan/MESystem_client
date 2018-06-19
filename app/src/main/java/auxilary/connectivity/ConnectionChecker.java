package auxilary.connectivity;

/**
 * Created by Anastacia on 27.03.2018.
 */
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Anastacia on 29.10.2016.
 */
public class ConnectionChecker {
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}