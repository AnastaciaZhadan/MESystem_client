package auxilary.models;

/**
 * Created by Anastacia on 03.04.2018.
 */
public class ChatWrapper {
    private String type;
    private Group group;
    private User user;
    private boolean sentNewMessage = false;

    public boolean hasSentNewMessage(){
        return sentNewMessage;
    }

    public void setHasSentNewMessage(boolean hasSentNewMessage){
        this.sentNewMessage = hasSentNewMessage;
    }

    public void setUser(User user){
        this.user = user;
        this.type = "user";
    }

    public void setGroup(Group group){
        this.group = group;
        this.type = "group";
    }

    public Group getGroup(){
        return group;
    }

    public User getUser(){
        return user;
    }

    public String getType(){
        return type;
    }

    public Integer getId(){
        if(type.equals("user"))
            return user.getId();
        return group.getId();
    }

    public String getName(){
        if(type.equals("user"))
            return user.getUsername();
        return "Group: " + group.getName();
    }


}
