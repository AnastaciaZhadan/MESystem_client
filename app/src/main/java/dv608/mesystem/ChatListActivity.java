package dv608.mesystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import auxilary.MESystemApplication;
import auxilary.adapters.ChatListAdapter;
import auxilary.models.ChatWrapper;
import auxilary.models.Message;
import businesslogic.MessageManager;
import businesslogic.MessageManagerInterface;
import businesslogic.UserListInterface;
import businesslogic.UserListManager;

public class ChatListActivity extends AppCompatActivity {
    private ListView userListView;
    private Toolbar toolbar;
    private TextView usernameTextView;
    private ListAdapter chatListAdapter = null;

    private UserListInterface userListManager = null;
    private List<ChatWrapper> chatList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        usernameTextView = (TextView) findViewById(R.id.username_textView);
        usernameTextView.setText(MESystemApplication.getCurrentUser().getUsername());

        userListManager = new UserListManager();
        chatList = userListManager.getChatList();

        removeCurrentUser();

        userListView = (ListView) findViewById(R.id.user_listView);
        //setChatListAdapter();

        MessageManagerInterface messageManager = new MessageManager();
        List<Message> newMessages = messageManager.getNewMessages();
        updateUserListView(newMessages);
    }

    private void removeCurrentUser() {
        ChatWrapper chatToRemove = null;
        for (ChatWrapper chat:chatList) {
            if(chat.getType().equals("user") && chat.getUser().getId() == MESystemApplication.getCurrentUser().getId()) {
                chatToRemove = chat;
                break;
            }
        }

        chatList.remove(chatToRemove);
    }

    private void setChatListAdapter(){
        chatListAdapter = new ChatListAdapter(chatList, this);
        userListView.setAdapter(chatListAdapter);
        userListView.setOnItemClickListener(new ChatClick());
    }

    private class ChatClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ChatWrapper selectedChat = chatList.get(i);
            Intent intent = new Intent(ChatListActivity.this, ChatActivity.class);
            intent.putExtra("chatId", selectedChat.getId());
            intent.putExtra("type", selectedChat.getType());
            startActivity(intent);
            finish();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.update_button) {
            MessageManagerInterface messageManager = new MessageManager();
            List<Message> newMessages = messageManager.getNewMessages();
            updateUserListView(newMessages);
            return true;
        }
        if(item.getItemId() == R.id.add_group_button){
            Intent intent = new Intent(this, CreateGroupActivity.class);
            this.startActivityForResult(intent, 11);
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent result) {
        switch (requestCode) {
            case (11): {
                if (resultCode == RESULT_OK) {
                    chatList = userListManager.getChatList();
                    removeCurrentUser();
                    setChatListAdapter();
                }
                break;
            }
        }
    }

    private void updateUserListView(List<Message> newMessages) {
        List<ChatWrapper> toRemoveList = new ArrayList<>();
        for (Message newMessage:newMessages) {
            ChatWrapper sender = new ChatWrapper();
            if(newMessage.getSender()!= null)
                sender.setUser(newMessage.getSender());

            for (ChatWrapper chat : chatList) {
                if(chat.getType().equals("user")) {
                    if (sender.getId() == chat.getUser().getId()) {
                       // chatList.remove(chat);
                        toRemoveList.add(chat);
                        chat.setHasSentNewMessage(true);
                        //chatList.add(0, chat);
                    }
                }
            }
        }
        removeCurrentUser();
        setChatListAdapter();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
}
