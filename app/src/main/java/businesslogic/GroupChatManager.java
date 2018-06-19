package businesslogic;

import java.util.List;

import auxilary.models.Group;
import auxilary.models.Message;
import auxilary.models.User;
import dataaccess.RESTManager;
import dataaccess.RESTManagerInterface;

/**
 * Created by Anastacia on 02.04.2018.
 */
public class GroupChatManager implements GroupChatManagerInterface{
    @Override
    public Group createGroup(String name, List<User> userList) {
        Group group = new Group();
        group.setName(name);
        group.setUsers(userList);

        RESTManagerInterface rest = new RESTManager();
        return rest.createGroup(group);
    }

    @Override
    public List<Message> getChatWithGroup(Group group) {
        RESTManagerInterface rest = new RESTManager();
        List<Message> chatWithGroup = rest.getChatWithGroup(group.getId());
        return chatWithGroup;
    }

    @Override
    public boolean sendMessage(Message message) {
        RESTManagerInterface rest = new RESTManager();
        return rest.sendMessageToGroup(message.getGroupReceiver().getId(), message.getMessageBody());
    }
}
