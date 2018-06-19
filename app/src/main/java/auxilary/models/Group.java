package auxilary.models;

import java.util.List;

/**
 * Created by Anastacia on 20.03.2018.
 */
public class Group {
    private Integer id;
    private String name;
    private List<User> users;

    public void setName(String name){
        this.name = name;
    }

    public void setUsers(List<User> users){
        this.users = users;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public List<User> getUsers(){
        return users;
    }
}
