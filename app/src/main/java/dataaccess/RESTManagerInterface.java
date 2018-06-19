package dataaccess;

import java.util.List;

import auxilary.models.Group;
import auxilary.models.Message;
import auxilary.models.User;

/**
 * Created by Anastacia on 27.03.2018.
 */
public interface RESTManagerInterface {
    User signUp(String username, String passwordHash);
    User login(String username, String passwordHash);

    List<User> getUserList();

    boolean sendMessageToUser(Integer receiverId, String messageBody);

    List<Message> getChatWithUser(Integer userId);

    List<Message> getNewMessages();

    Group createGroup(Group group);

    List<Group> getGroupsOfUser();

    boolean sendMessageToGroup(Integer id, String messageBody);

    List<Message> getChatWithGroup(Integer id);
}
