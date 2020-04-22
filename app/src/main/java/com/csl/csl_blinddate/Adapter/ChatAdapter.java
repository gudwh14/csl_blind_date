package com.csl.csl_blinddate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csl.csl_blinddate.Data.ChatData;
import com.csl.csl_blinddate.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private ArrayList<ChatData> data = new ArrayList<>();

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(data.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return data.size();
    }

    public void addItem(ChatData data2) {
        // 외부에서 item을 추가시킬 함수입니다.
        data.add(data2);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userID;
        private TextView chatMsg;

        ViewHolder(final View itemView) {
            super(itemView);

            userID = itemView.findViewById(R.id.chat_nameText);
            chatMsg = itemView.findViewById(R.id.chat_msgView);


        }

        void onBind(ChatData data) {
            userID.setText(data.getUserID());
            chatMsg.setText(data.getChatMsg());
        }
    }
}