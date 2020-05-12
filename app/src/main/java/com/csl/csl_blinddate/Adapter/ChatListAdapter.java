package com.csl.csl_blinddate.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csl.csl_blinddate.ChatActivity;
import com.csl.csl_blinddate.Data.ChatListData;
import com.csl.csl_blinddate.R;
import com.google.android.material.chip.Chip;


import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {
    private ArrayList<ChatListData> data = new ArrayList<>();

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatlist_view,parent,false);
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

    public void addItem(ChatListData data2) {
        // 외부에서 item을 추가시킬 함수입니다.
        data.add(data2);
    }

    public void clear() {
        data.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView chatlist_schoolText;
        private Chip chatlist_memberChip;
        private TextView chatlist_nameText;
        private String title;

        ViewHolder(final View itemView) {
            super(itemView);

            chatlist_schoolText = itemView.findViewById(R.id.chatlist_schoolText);
            chatlist_memberChip = itemView.findViewById(R.id.chatlist_memeberChip);
            chatlist_nameText = itemView.findViewById(R.id.chatlist_nameText);

        }

        void onBind(ChatListData data) {
            chatlist_schoolText.setText(data.getSchool());
            chatlist_memberChip.setText(data.getMember() + " : " + data.getMember());
            chatlist_nameText.setText(data.getName() + " 님");
            title = data.getSchool() +" "+ data.getMember() +" : " + data.getMember() +" 미팅";
            final int meeting_id = data.getList_id();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(view.getContext(), ChatActivity.class);
                        intent.putExtra("title",title);
                        intent.putExtra("meeting_id",meeting_id);
                        view.getContext().startActivity(intent);
                    }
                }
            });
        }
    }
}