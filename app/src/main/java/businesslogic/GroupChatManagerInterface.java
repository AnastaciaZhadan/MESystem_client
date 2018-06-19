package businesslogic;

import java.util.List;

import auxilary.models.Group;
import auxilary.models.Message;
import auxilary.models.User;

/**
 * Created by Anastacia on 02.04.2018.
 */
public interface GroupChatManagerInterface {
    Group createGroup(String name, List<User> userList);

    List<Message> getChatWithGroup(Group group);

    boolean sendMessage(Message message);
}
