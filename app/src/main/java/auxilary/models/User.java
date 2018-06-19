package auxilary.models;

/**
 * Created by Anastacia on 20.03.2018.
 */
public class User {
    private Integer id;
    private String name;
    private String password;
    private boolean sentNewMessage;

    public String getUsername(){
        return name;
    }

    public Integer getId(){
        return id;
    }

    public String getPassword(){
        return password;
    }

    public boolean hasSentNewMessage(){
        return sentNewMessage;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setHasSentNewMessage(boolean hasSentNewMessage){
        this.sentNewMessage = hasSentNewMessage;
    }
}
