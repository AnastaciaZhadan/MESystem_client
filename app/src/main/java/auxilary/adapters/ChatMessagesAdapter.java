package auxilary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import auxilary.models.Message;
import dv608.mesystem.R;

/**
 * Created by Anastacia on 21.03.2018.
 */
public class ChatMessagesAdapter extends ArrayAdapter<Message> {
    private Context context;
    private List<Message> messageList = null;

    public ChatMessagesAdapter(List<Message> messageList, Context context) {
        super(context, R.layout.chat_line_row, messageList);
        this.context = context;
        this.messageList = messageList;
    }

    @Override // Called by ListView when displaying a row
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_line_row, parent, false);
        }

        TextView senderNameView = (TextView) convertView.findViewById(R.id.chatSenderName_textView);
        TextView messageBodyView = (TextView) convertView.findViewById(R.id.chatMessage_textView);
        //TextView sentTimeView = (TextView) convertView.findViewById(R.id.messageSendDate_TextView);
        Message message = messageList.get(position);

        senderNameView.setText(message.getSender().getUsername());
        messageBodyView.setText(message.getMessageBody());

        //String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(message.getTimestamp());
        //sentTimeView.setText(message.getTimeSended());

        return convertView;
    }
}