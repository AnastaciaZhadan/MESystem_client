package auxilary.models;

/**
 * Created by Anastacia on 20.03.2018.
 */
public class Message {
    private User sender;
    private User receiver;
    private Group groupReceiver;
    private String messageBody;
    private String timeSended;

    public void setSender(User sender){
        this.sender = sender;
    }

    public void setReceiver(User receiver){
        this.receiver = receiver;
    }

    public void setGroupReceiver(Group groupReceiver){
        this.groupReceiver = groupReceiver;
    }

    public void setTimeSended(String time){
        this.timeSended = time;
    }

    public void setMessageBody(String messageBody){
        this.messageBody = messageBody;
    }

    public User getSender(){
        return sender;
    }

    public User getReceiver(){
        return receiver;
    }

    public Group getGroupReceiver(){
        return groupReceiver;
    }

    public String getMessageBody(){
        return messageBody;
    }

    public String getTimeSended(){
        return timeSended;
    }
}
