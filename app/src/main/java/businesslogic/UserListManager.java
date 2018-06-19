package businesslogic;

import java.util.ArrayList;
import java.util.List;

import auxilary.models.ChatWrapper;
import auxilary.models.Group;
import auxilary.models.User;
import dataaccess.RESTManager;
import dataaccess.RESTManagerInterface;

/**
 * Created by Anastacia on 22.03.2018.
 */
public class UserListManager implements UserListInterface {

    @Override
    public List<User> getUserList() {
        RESTManagerInterface rest = new RESTManager();
        List<User> userList = rest.getUserList();
        return userList;
    }

    @Override
    public List<ChatWrapper> getChatList(){
        RESTManagerInterface rest = new RESTManager();
        List<ChatWrapper> chatList = new ArrayList<>();

        List<User> userList = this.getUserList();
        List<Group> groupList = rest.getGroupsOfUser();
        for (User user: userList) {
            ChatWrapper chat = new ChatWrapper();
            chat.setUser(user);
            chatList.add(chat);
        }

        for (Group group: groupList) {
            ChatWrapper chat = new ChatWrapper();
            chat.setGroup(group);
            chatList.add(chat);
        }
        return chatList;
    }
}
