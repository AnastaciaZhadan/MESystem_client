package auxilary;

import android.app.Application;

import auxilary.models.User;

/**
 * Created by Anastacia on 21.03.2018.
 */
public class MESystemApplication extends Application {
    private static MESystemApplication appContext;
    private static User currentUser;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }

    public static MESystemApplication getAppContext(){
        return appContext;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
