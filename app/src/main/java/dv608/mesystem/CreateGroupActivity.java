package dv608.mesystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import auxilary.MESystemApplication;
import auxilary.models.Group;
import auxilary.models.User;
import businesslogic.GroupChatManager;
import businesslogic.GroupChatManagerInterface;
import businesslogic.UserListInterface;
import businesslogic.UserListManager;

public class CreateGroupActivity extends AppCompatActivity {
    private ListView userListView;
    private Toolbar toolbar;
    private ListAdapter userListAdapter = null;
    private EditText groupNameEditText = null;

    List<User> usersList = null;
    List<User> chatUsersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        toolbar = (Toolbar) findViewById(R.id.create_group_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        groupNameEditText = (EditText) findViewById(R.id.enterGroupName_editText);

        UserListInterface userListInterface = new UserListManager();
        usersList = userListInterface.getUserList();

        User currentUser = null;
        for (User user:usersList) {
            if(user.getId() == MESystemApplication.getCurrentUser().getId())
                currentUser = user;
        }
        usersList.remove(currentUser);

        chatUsersList.add(MESystemApplication.getCurrentUser());

        userListView = (ListView) findViewById(R.id.create_group_users_listView);
        userListAdapter = new ChatUserListAdapter(usersList, this);
        userListView.setAdapter(userListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_group_toolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.create_group_button) {
            GroupChatManagerInterface groupChatManager = new GroupChatManager();
            String groupName = groupNameEditText.getText().toString();
            if(groupName.equals("")) {
                Toast.makeText(CreateGroupActivity.this, "Enter group name!", Toast.LENGTH_SHORT).show();
                return false;
            }

            Group resultGroup = groupChatManager.createGroup(groupName, chatUsersList);
            if(resultGroup!=null){
                Intent result = new Intent();
                setResult(RESULT_OK,result);
                finish();
                return true;
            } else{
                Toast.makeText(CreateGroupActivity.this, "Cannot create group!", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return false;
    }

    private class ChatUserListAdapter extends ArrayAdapter<User> {
        private Context context;

        public ChatUserListAdapter(List<User> userList, Context context){
            super(context, R.layout.user_list_row, userList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.user_row_with_button, parent, false);
            }

            TextView userNameView = (TextView) convertView.findViewById(R.id.group_user_name_textView);
            ImageButton addButton = (ImageButton) convertView.findViewById(R.id.add_user_to_group_button);
            addButton.setTag(position);

            User user = usersList.get(position);
            userNameView.setText(user.getUsername());
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();
                    if(view.getBackground().getConstantState()
                            .equals(getResources().getDrawable(R.drawable.plus, null).getConstantState())){
                        view.setBackgroundResource(R.drawable.close);
                        chatUsersList.add(usersList.get(position));
                    } else {
                        User userToDeleteFromGroup = usersList.get(position);
                        for (User user: chatUsersList) {
                            if(user.getId() == userToDeleteFromGroup.getId()) {
                                view.setBackgroundResource(R.drawable.plus);
                                chatUsersList.remove(user);
                                break;
                            }
                        }
                    }
                }
            });
            return convertView;
        }
    }
}
