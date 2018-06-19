package dataaccess;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import auxilary.MESystemApplication;
import auxilary.connectivity.MESystemServerConnector;
import auxilary.models.Group;
import auxilary.models.Message;
import auxilary.models.User;

/**
 * Created by Anastacia on 27.03.2018.
 */
public class RESTManager implements RESTManagerInterface {
    //private static String ip = "http://217.10.108.219:8080/";
   // private static String ip = "http://192.168.100.7:8080/";
    private static String ip = "http://194.47.122.229:8080/";

    @Override
    public User signUp(String username, String passwordHash) {
        User signedUpUser = new User();
        String jsonResString = null;
        try {
            jsonResString = new ServerDataRetriever().execute(new String[]{ip + "account/signup",
                    "username=" + username + "&pass=" + passwordHash, "GET"}).get();
            JSONObject userJsonObj = new JSONObject(jsonResString);
            signedUpUser.setId(userJsonObj.getInt("id"));
            signedUpUser.setName(userJsonObj.getString("username"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(signedUpUser.getId()!=null)
            return signedUpUser;
        return null;
    }

    @Override
    public User login(String username, String passwordHash) {
        User logedInUser = new User();
        String jsonResString = null;
        try {
            jsonResString = new ServerDataRetriever().execute(new String[]{ip + "account/login",
                    "username=" + username + "&pass=" + passwordHash, "GET"}).get();
            if(jsonResString == null)
                logedInUser = null;
            else {
                JSONObject userJsonObj = new JSONObject(jsonResString);
                logedInUser.setId(userJsonObj.getInt("id"));
                logedInUser.setName(userJsonObj.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logedInUser;
    }

    @Override
    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        String jsonResString = null;
        try {
            jsonResString = new ServerDataRetriever().execute(new String[]{ip + "users",
                    "", "GET"}).get();
            JSONArray usersJsonArray = new JSONArray(jsonResString);
            for (int  i= 0;i < usersJsonArray.length();i++) {
                JSONObject userJsonObj = usersJsonArray.getJSONObject(i);
                User user = new User();
                user.setId(userJsonObj.getInt("id"));
                user.setName(userJsonObj.getString("username"));
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public boolean sendMessageToUser(Integer receiverId, String messageBody) {
        String jsonResString = null;
        try {
            String encodedMessageBody = URLEncoder.encode(messageBody, "UTF-8");
            jsonResString = new ServerDataRetriever().execute(new String[]{ip + "messaging/send",
                    "receiverId=" + receiverId + "&senderId=" + MESystemApplication.getCurrentUser().getId()
                            + "&messageBody=" + encodedMessageBody, "GET"}).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(jsonResString != null) {
            if (jsonResString.equals("true"))
                return true;
        }
        return false;
    }

    @Override
    public List<Message> getChatWithUser(Integer userId) {
        List<Message> chat = new ArrayList<>();
        String jsonResString = null;
        try {
            Integer currentUserId = MESystemApplication.getCurrentUser().getId();
            jsonResString = new ServerDataRetriever().execute(new String[]{ip + "messaging/chat-with-user",
                    "userId=" + userId + "&currentUserId=" + currentUserId, "GET"}).get();
            if(jsonResString != null){
                JSONArray usersJsonArray = new JSONArray(jsonResString);
                for (int  i= 0;i < usersJsonArray.length();i++) {
                    JSONObject messageJsonObj = usersJsonArray.getJSONObject(i);
                    Message message = new Message();
                    message.setMessageBody(messageJsonObj.getString("messageBody"));
                    //message.setTimeSended(messageJsonObj.getString("timeSended"));

                    JSONObject senderJsonObj = messageJsonObj.getJSONObject("sender");
                    User sender = new User();
                    sender.setId(senderJsonObj.getInt("id"));
                    sender.setName(senderJsonObj.getString("username"));
                    message.setSender(sender);

                    JSONObject receiverJsonObj = messageJsonObj.getJSONObject("receiver");
                    User receiver = new User();
                    receiver.setId(receiverJsonObj.getInt("id"));
                    receiver.setName(receiverJsonObj.getString("username"));
                    message.setReceiver(receiver);

                    chat.add(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chat;
    }

    @Override
    public List<Message> getNewMessages() {
        List<Message> chat = new ArrayList<>();
        String jsonResString = null;
        try {
            jsonResString = new ServerDataRetriever().execute(new String[]{ip + "messaging/new-messages",
                    "userId=" + MESystemApplication.getCurrentUser().getId(), "GET"}).get();
            if(jsonResString != null){
                JSONArray usersJsonArray = new JSONArray(jsonResString);
                for (int  i= 0;i < usersJsonArray.length();i++) {
                    JSONObject messageJsonObj = usersJsonArray.getJSONObject(i);
                    Message message = new Message();
                    message.setMessageBody(messageJsonObj.getString("messageBody"));
                    //message.setTimeSended(messageJsonObj.getString("timeSended"));

                    JSONObject senderJsonObj = messageJsonObj.getJSONObject("sender");
                    User sender = new User();
                    sender.setId(senderJsonObj.getInt("id"));
                    sender.setName(senderJsonObj.getString("username"));
                    message.setSender(sender);

                    if(messageJsonObj.has("receiver")) {
                        JSONObject receiverJsonObj = messageJsonObj.getJSONObject("receiver");
                        User receiver = new User();
                        receiver.setId(receiverJsonObj.getInt("id"));
                        receiver.setName(receiverJsonObj.getString("username"));
                        message.setReceiver(receiver);
                    } else if(messageJsonObj.has("groupReceiver")) {
                        JSONObject groupReceiverJsonObj = messageJsonObj.getJSONObject("groupReceiver");
                        Group groupReceiver = new Group();
                        groupReceiver.setId(groupReceiverJsonObj.getInt("groupId"));
                        groupReceiver.setName(groupReceiverJsonObj.getString("groupName"));
                        message.setGroupReceiver(groupReceiver);
                    }

                    chat.add(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chat;
    }

    @Override
    public Group createGroup(Group group) {
        String jsonResString = null;
        String usersIdParamData = "";
        for (User user: group.getUsers())
            usersIdParamData+=user.getId().toString() + ",";
        usersIdParamData = usersIdParamData.substring(0, usersIdParamData.length() - 1);

        try {
            jsonResString = new ServerDataRetriever().execute(new String[]{ip + "group/creategroup",
                    "name=" + URLEncoder.encode(group.getName(), "UTF-8") + "&usersIds=" + usersIdParamData, "GET"}).get();
            JSONObject userJsonObj = new JSONObject(jsonResString);
            group.setId(userJsonObj.getInt("groupId"));
            return group;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Group> getGroupsOfUser() {
        List<Group> groupList = new ArrayList<>();
        String jsonResString = null;
        try {
            jsonResString = new ServerDataRetriever().execute(new String[]{ip + "group/groups",
                    "userId=" + MESystemApplication.getCurrentUser().getId(), "GET"}).get();
            JSONArray usersJsonArray = new JSONArray(jsonResString);
            for (int  i= 0; i < usersJsonArray.length();i++) {
                JSONObject userJsonObj = usersJsonArray.getJSONObject(i);
                Group group = new Group();
                group.setId(userJsonObj.getInt("groupId"));
                group.setName(userJsonObj.getString("groupName"));
                groupList.add(group);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupList;
    }

    @Override
    public boolean sendMessageToGroup(Integer id, String messageBody) {
        String jsonResString = null;
        try {
            jsonResString = new ServerDataRetriever().execute(new String[]{ip + "group/send",
                    "groupId=" + id + "&senderId=" + MESystemApplication.getCurrentUser().getId()
                            + "&messageBody=" + URLEncoder.encode(messageBody, "UTF-8"), "GET"}).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(jsonResString != null) {
            if (jsonResString.equals("true"))
                return true;
        }
        return false;
    }

    @Override
    public List<Message> getChatWithGroup(Integer groupId) {
        List<Message> chat = new ArrayList<>();
        String jsonResString = null;
        try {
            jsonResString = new ServerDataRetriever().execute(new String[]{ip + "group/chat-with-group",
                    "groupId=" + groupId, "GET"}).get();
            if(jsonResString != null){
                JSONArray usersJsonArray = new JSONArray(jsonResString);
                for (int  i= 0;i < usersJsonArray.length();i++) {
                    JSONObject messageJsonObj = usersJsonArray.getJSONObject(i);
                    Message message = new Message();
                    message.setMessageBody(messageJsonObj.getString("messageBody"));
                    //message.setTimeSended(messageJsonObj.getString("timeSended"));

                    JSONObject senderJsonObj = messageJsonObj.getJSONObject("sender");
                    User sender = new User();
                    sender.setId(senderJsonObj.getInt("id"));
                    sender.setName(senderJsonObj.getString("username"));
                    message.setSender(sender);

                    chat.add(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chat;
    }

    private class ServerDataRetriever extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... data) {
            try {
                MESystemServerConnector connector = new MESystemServerConnector(data[0], data[1], data[2]);
                return connector.execute();
            } catch (Exception e) {
                Toast.makeText(MESystemApplication.getAppContext(), "No connection with server", Toast.LENGTH_LONG).show();
                return "Connection error";
            }
        }

        protected void onProgressUpdate(Void... progress) {}

        protected void onPostExecute(String result) {
            if(result == null)
                return;
            if(result.equals("Connection error")){
                return;
            }
        }
    }
}