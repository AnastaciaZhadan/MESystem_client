package businesslogic;

import auxilary.models.User;

/**
 * Created by Anastacia on 21.03.2018.
 */
public interface AccountManagerInterface {
    public boolean signUp(User user);

    public boolean login(User user);

    public User getCurrentAccountUser();
}
