package auxilary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import auxilary.models.User;
import dv608.mesystem.R;

/**
 * Created by Anastacia on 21.03.2018.
 */
public class UserListAdapter extends ArrayAdapter<User> {
    private Context context;
    private List<User> userList = null;

    public UserListAdapter(List<User> userList, Context context){
        super(context, R.layout.user_list_row, userList);
        this.context = context;
        this.userList = userList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_list_row, parent, false);
        }

        TextView userNameView = (TextView) convertView.findViewById(R.id.chatUserName_textView);
        User user = userList.get(position);

        userNameView.setText(user.getUsername());
        return convertView;
    }
}
