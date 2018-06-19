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

import java.util.List;

import auxilary.adapters.UserListAdapter;
import auxilary.models.ChatWrapper;
import auxilary.models.Message;
import auxilary.models.User;
import businesslogic.MessageManager;
import businesslogic.MessageManagerInterface;
import businesslogic.UserListInterface;
import businesslogic.UserListManager;

public class UserListActivity extends AppCompatActivity {
    private ListView userListView;
    private Toolbar toolbar;
    private ListAdapter userListAdapter = null;

    private UserListInterface userListManager = null;
    private List<User> usersList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userListManager = new UserListManager();
        usersList = userListManager.getUserList();


        userListView = (ListView) findViewById(R.id.user_listView);
        setUserListAdapter();
    }

    private void setUserListAdapter(){
        userListAdapter = new UserListAdapter(usersList, this);
        userListView.setAdapter(userListAdapter);
        userListView.setOnItemClickListener(new UserClick());
    }

    private class UserClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            User selectedUserChat = usersList.get(i);
            Intent intent = new Intent(UserListActivity.this, ChatActivity.class);
            intent.putExtra("chatId","" + selectedUserChat.getId().toString());
            startActivity(intent);
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
            this.startActivity(intent);
            return true;
        }
        return false;
    }

    @Override // Catch result
    protected void onActivityResult(int requestCode,int resultCode, Intent result) {
        switch (requestCode) {
            case (11): {
                if (resultCode == RESULT_OK) {
                    usersList = userListManager.getUserList();
                    setUserListAdapter();
                }
                break;
            }
        }

    }

    private void updateUserListView(List<Message> newMessages) {
        for (Message newMessage:newMessages) {
            ChatWrapper sender = new ChatWrapper();
            if(newMessage.getSender()!= null)
                sender.setUser(newMessage.getSender());
            for (User user:usersList) {
                if(sender.getId() == user.getId()){
                    usersList.remove(user);
                    user.setHasSentNewMessage(true);
                    usersList.add(0, user);
                }
            }
        }

        setUserListAdapter();

        for (int i = 0; i < newMessages.size() - 1; i++) {
            View userView = getViewByPosition(i, userListView);
            TextView newMesEditText = (TextView) userView.findViewById(R.id.newMes_textView);
            newMesEditText.setVisibility(View.VISIBLE);
        }
    }

    public View getViewByPosition(int pos, ListView userList) {
        final int firstItemPosition = userList.getFirstVisiblePosition();
        final int lastItemPosition = firstItemPosition + userList.getChildCount() - 1;

        if (pos < firstItemPosition || pos > lastItemPosition ) {
            return userList.getAdapter().getView(pos, null, userList);
        } else {
            final int childIndex = pos - firstItemPosition;
            return userList.getChildAt(childIndex);
        }
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
