package com.csl.csl_blinddate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csl.csl_blinddate.Data.ChatData;
import com.csl.csl_blinddate.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ChatData> data = new ArrayList<>();

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        if(viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_my_view,parent,false);
            return new my_ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_view,parent,false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        if(holder instanceof my_ViewHolder) {
            ((my_ViewHolder)holder).onBind(data.get(position));
        }
        else {
            ((ViewHolder)holder).onBind(data.get(position));
        }
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(data.get(position).isMyText()) {
            return 1;
        }
        else {
            return 0;
        }
    }

    public void addItem(ChatData data2) {
        // 외부에서 item을 추가시킬 함수입니다.
        data.add(data2);
    }

    public void clear() {
        data.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userID;
        private TextView chatMsg;
        private TextView chat_timeText;

        ViewHolder(final View itemView) {
            super(itemView);

            userID = itemView.findViewById(R.id.chat_nameText);
            chatMsg = itemView.findViewById(R.id.chat_msgView);
            chat_timeText = itemView.findViewById(R.id.chat_timeView);


        }

        void onBind(ChatData data) {
            userID.setText(data.getUserID());
            String[] time =  data.getTime().split(" ");
            if(time[0].equals("am")) {
                time[0] = "오전 ";
            }
            else {
                time[0] = "오후 ";
            }
            chat_timeText.setText(time[0]+time[1]);
            chatMsg.setText(data.getChatMsg());
        }
    }

    class my_ViewHolder extends RecyclerView.ViewHolder {
        private TextView chatMsg;
        private TextView chat_timeText;

        my_ViewHolder(final View itemView) {
            super(itemView);

            chatMsg = itemView.findViewById(R.id.my_chat_msgView);
            chat_timeText = itemView.findViewById(R.id.my_chat_timeView);


        }

        void onBind(ChatData data) {
            String[] time =  data.getTime().split(" ");
            if(time[0].equals("am")) {
                time[0] = "오전 ";
            }
            else {
                time[0] = "오후 ";
            }
            chat_timeText.setText(time[0]+time[1]);
            chatMsg.setText(data.getChatMsg());
        }
    }
}