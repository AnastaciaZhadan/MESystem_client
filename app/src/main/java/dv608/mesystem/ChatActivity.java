package dv608.mesystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import auxilary.MESystemApplication;
import auxilary.adapters.ChatMessagesAdapter;
import auxilary.models.Group;
import auxilary.models.Message;
import auxilary.models.User;
import businesslogic.GroupChatManager;
import businesslogic.GroupChatManagerInterface;
import businesslogic.MessageManager;
import businesslogic.MessageManagerInterface;

public class ChatActivity extends AppCompatActivity {
    ListView chatListView;
    Button sendMessageButton;
    EditText messageBodyEditText;
    private User chatedUser;
    private Group chatedGroup;
    List<Message> chat = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Integer id = getIntent().getIntExtra("chatId", 0);
        String type = getIntent().getStringExtra("type");

        chat = new ArrayList<>();

        if(type.equals("user")) {
            chatedUser = new User();
            chatedUser.setId(id);
            MessageManagerInterface messageManager = new MessageManager();
            chat = messageManager.getChatWithUser(chatedUser);
        } else {
            chatedGroup = new Group();
            chatedGroup.setId(id);
            GroupChatManagerInterface messageManager = new GroupChatManager();
            chat = messageManager.getChatWithGroup(chatedGroup);
        }

        Collections.reverse(chat);

        chatListView = (ListView) findViewById(R.id.chat_listView);

        setChatAdapter();

        sendMessageButton = (Button) findViewById(R.id.sendMes_button);
        sendMessageButton.setOnClickListener(new SendMessageClick());

        messageBodyEditText = (EditText) findViewById(R.id.enterMes_editText);
    }

    private void setChatAdapter(){
        ListAdapter userListAdapter = new ChatMessagesAdapter(chat, this);
        chatListView.setAdapter(userListAdapter);
    }

    private class SendMessageClick implements View.OnClickListener {
        public void onClick(View v) {
            if(!messageBodyEditText.getText().toString().equals("")){
                Message message = new Message();
                if(chatedUser!=null)
                    message.setReceiver(chatedUser);
                else message.setGroupReceiver(chatedGroup);

                message.setSender(MESystemApplication.getAppContext().getCurrentUser());
                message.setMessageBody(messageBodyEditText.getText().toString());

                boolean sent;
                if(chatedUser!=null) {
                    MessageManagerInterface messageManager = new MessageManager();
                    sent = messageManager.sendMessage(message);
                } else {
                    GroupChatManagerInterface groupManager = new GroupChatManager();
                    sent = groupManager.sendMessage(message);
                }

                if(!sent)
                   Toast.makeText(ChatActivity.this, "Error occured while sending the message", Toast.LENGTH_SHORT).show();
                else {
                    messageBodyEditText.setText("");
                    chat.add(0, message);
                    setChatAdapter();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ChatActivity.this, ChatListActivity.class);
        ChatActivity.this.startActivity(intent);
    }
}
