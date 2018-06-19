package businesslogic;

import java.util.List;

import auxilary.models.ChatWrapper;
import auxilary.models.User;

/**
 * Created by Anastacia on 22.03.2018.
 */
public interface UserListInterface {
    List<User> getUserList();

    List<ChatWrapper> getChatList();
}
