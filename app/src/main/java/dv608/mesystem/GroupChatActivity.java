package dv608.mesystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import auxilary.MESystemApplication;
import auxilary.models.Group;
import auxilary.models.Message;
import businesslogic.MessageManager;
import businesslogic.MessageManagerInterface;

public class GroupChatActivity extends AppCompatActivity {
    ListView chatListView;
    Button sendMessageButton;
    EditText messageBodyEditText;
    private Group chatedGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        String id = getIntent().getStringExtra("groupId");
        chatedGroup = new Group();
        chatedGroup.setId(Integer.getInteger(id));
        MessageManagerInterface messageManager = new MessageManager();
        //List<Message> chat = messageManager.getChatWithUser(chatedUser);

        chatListView = (ListView) findViewById(R.id.chat_listView);
       // ListAdapter userListAdapter = new ChatMessagesAdapter(chat, this);
       // chatListView.setAdapter(userListAdapter);

        sendMessageButton = (Button) findViewById(R.id.sendMes_button);
        sendMessageButton.setOnClickListener(new SendMessageClick());

        messageBodyEditText = (EditText) findViewById(R.id.enterMes_editText);
    }

    private class SendMessageClick implements View.OnClickListener {
        public void onClick(View v) {
            if(!messageBodyEditText.getText().toString().equals("")){
                Message message = new Message();
                message.setGroupReceiver(chatedGroup);
                message.setSender(MESystemApplication.getAppContext().getCurrentUser());
                message.setMessageBody(messageBodyEditText.getText().toString());
                MessageManagerInterface messageManager = new MessageManager();
                boolean sent = messageManager.sendMessageToGroup(message);
                if(!sent)
                    Toast.makeText(GroupChatActivity.this, "Error occured while sending the message", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
