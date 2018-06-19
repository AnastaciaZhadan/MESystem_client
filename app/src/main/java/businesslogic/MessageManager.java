package businesslogic;

import java.util.List;

import auxilary.MESystemApplication;
import auxilary.models.Message;
import auxilary.models.User;
import dataaccess.RESTManager;
import dataaccess.RESTManagerInterface;

/**
 * Created by Anastacia on 22.03.2018.
 */
public class MessageManager implements MessageManagerInterface {
    User currentUser = MESystemApplication.getAppContext().getCurrentUser();
    @Override
    public List<Message> getChatWithUser(User user) {
        RESTManagerInterface rest = new RESTManager();
        List<Message> chatWithUser = rest.getChatWithUser(user.getId());
        return chatWithUser;
    }

    @Override
    public boolean sendMessage(Message message) {
        RESTManagerInterface rest = new RESTManager();
        return rest.sendMessageToUser(message.getReceiver().getId(), message.getMessageBody());
    }

    @Override
    public List<Message> getNewMessages() {
        RESTManagerInterface rest = new RESTManager();
        List<Message> newMessages = rest.getNewMessages();
        return newMessages;
    }

    @Override
    public boolean sendMessageToGroup(Message message) {
        RESTManagerInterface rest = new RESTManager();
        return rest.sendMessageToGroup(message.getGroupReceiver().getId(), message.getMessageBody());
    }
}
