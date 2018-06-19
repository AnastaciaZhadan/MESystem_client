package businesslogic;

import auxilary.MESystemApplication;
import auxilary.cryptography.Cryptography;
import auxilary.cryptography.CryptographyInterface;
import auxilary.models.User;
import dataaccess.RESTManager;

/**
 * Created by Anastacia on 21.03.2018.
 */
public class SimpleAccountManager implements AccountManagerInterface {
    User currentAccountUser = null;

    @Override
    public boolean signUp(User user) {
        currentAccountUser = user;
        RESTManager rest = new RESTManager();
        CryptographyInterface crypt = new Cryptography();
        User signeUpUser = rest.signUp(user.getUsername(), crypt.getPasswordHash(user.getPassword()));
        if(user!=null) {
            currentAccountUser = signeUpUser;
            MESystemApplication.setCurrentUser(signeUpUser);
            return true;
        }
        return false;
    }

    @Override
    public boolean login(User user) {
        RESTManager rest = new RESTManager();
        CryptographyInterface crypt = new Cryptography();
        User logedInUser = rest.login(user.getUsername(), crypt.getPasswordHash(user.getPassword()));
        if(user!=null) {
            currentAccountUser = logedInUser;
            MESystemApplication.setCurrentUser(logedInUser);
            return true;
        }
        return false;
    }

    @Override
    public User getCurrentAccountUser(){
        return currentAccountUser;
    }
}
