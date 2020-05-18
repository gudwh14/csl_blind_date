package com.csl.csl_blinddate.Adapter;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csl.csl_blinddate.Data.CommentData;
import com.csl.csl_blinddate.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<CommentData> data = new ArrayList<>();

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        if(viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_view,parent,false);
            return new my_ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_view,parent,false);
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
        if(data.get(position).isReply()) {
            return 1;
        }
        else {
            return 0;
        }
    }

    public void addItem(CommentData data2) {
        // 외부에서 item을 추가시킬 함수입니다.
        data.add(data2);
    }

    public void clear() {
        data.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView comment_userText;
        TextView comment_timeText;
        TextView comment_upText;
        TextView comment_commentText;
        ImageView comment_upImage;
        private Drawable drawable;

        ViewHolder(final View itemView) {
            super(itemView);

            comment_userText = itemView.findViewById(R.id.comment_userText);
            comment_timeText = itemView.findViewById(R.id.comment_timeText);
            comment_upText = itemView.findViewById(R.id.comment_upText);
            comment_commentText = itemView.findViewById(R.id.comment_commentText);
            comment_upImage = itemView.findViewById(R.id.comment_upImage);

            drawable = itemView.getContext().getResources().getDrawable(R.drawable.heart_icon);
            drawable.setBounds(0,0,40,40);

        }

        void onBind(CommentData data) {
            String[] time = data.getTime().split(" ");
            comment_userText.setText(data.getUserID());
            comment_upText.setCompoundDrawables(drawable,null,null,null);
            comment_upText.setText(" " + data.getUp());

            comment_commentText.setText(data.getComment());
            comment_timeText.setText(time[1] + " " + time[2]);
        }
    }

    class my_ViewHolder extends RecyclerView.ViewHolder {
        TextView reply_userText;
        TextView reply_timeText;
        TextView reply_upText;
        TextView reply_commentText;
        ImageView reply_upImage;
        private Drawable drawable;

        my_ViewHolder(final View itemView) {
            super(itemView);


            reply_userText = itemView.findViewById(R.id.reply_userText);
            reply_timeText = itemView.findViewById(R.id.reply_timeText);
            reply_upText = itemView.findViewById(R.id.reply_upText);
            reply_commentText = itemView.findViewById(R.id.reply_commentText);
            reply_upImage = itemView.findViewById(R.id.reply_upImage);

            drawable = itemView.getContext().getResources().getDrawable(R.drawable.heart_icon);
            drawable.setBounds(0,0,40,40);
        }

        void onBind(CommentData data) {
            String[] time = data.getTime().split(" ");
            reply_userText.setText(data.getUserID());
            reply_upText.setCompoundDrawables(drawable,null,null,null);
            reply_upText.setText(" " + data.getUp());

            reply_commentText.setText(data.getComment());
            reply_timeText.setText(time[1] + " " + time[2]);
        }
    }
}