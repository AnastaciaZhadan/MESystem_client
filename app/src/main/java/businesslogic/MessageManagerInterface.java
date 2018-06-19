package businesslogic;

import java.util.List;

import auxilary.models.Message;
import auxilary.models.User;

/**
 * Created by Anastacia on 22.03.2018.
 */
public interface MessageManagerInterface {
    List<Message> getChatWithUser(User user);

    boolean sendMessage(Message message);

    List<Message> getNewMessages();

    boolean sendMessageToGroup(Message message);
}
