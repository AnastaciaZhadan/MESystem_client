package auxilary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import auxilary.models.ChatWrapper;
import dv608.mesystem.R;

/**
 * Created by Anastacia on 03.04.2018.
 */
public class ChatListAdapter extends ArrayAdapter<ChatWrapper> {
    private Context context;
    private List<ChatWrapper> chatList = null;

    public ChatListAdapter(List<ChatWrapper> chatList, Context context){
        super(context, R.layout.user_list_row, chatList);
        this.context = context;
        this.chatList = chatList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_list_row, parent, false);
        }

        TextView userNameView = (TextView) convertView.findViewById(R.id.chatUserName_textView);
        TextView newMesEditText = (TextView) convertView.findViewById(R.id.newMes_textView);

        ChatWrapper chat = chatList.get(position);

        userNameView.setText(chat.getName());

        if(chat.hasSentNewMessage())
            newMesEditText.setVisibility(View.VISIBLE);
        return convertView;
    }
}
