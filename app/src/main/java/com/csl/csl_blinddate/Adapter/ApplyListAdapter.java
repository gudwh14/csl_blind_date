package com.csl.csl_blinddate.Adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csl.csl_blinddate.Data.ApplyData;
import com.csl.csl_blinddate.Data.ApplyListData;
import com.csl.csl_blinddate.R;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class ApplyListAdapter extends RecyclerView.Adapter<ApplyListAdapter.ViewHolder> {
    private ArrayList<ApplyListData> data = new ArrayList<>();

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.applylist_view,parent,false);
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

    public void addItem(ApplyListData data2) {
        // 외부에서 item을 추가시킬 함수입니다.
        data.add(data2);
    }

    public void clear() {
        data.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView applyList_schoolText;
        Chip applyList_memberChip;
        TextView applyList_timeText;
        TextView applyList_statusText;

        ViewHolder(final View itemView) {
            super(itemView);
            applyList_schoolText = itemView.findViewById(R.id.applyList_schoolText);
            applyList_memberChip = itemView.findViewById(R.id.applyList_memberChip);
            applyList_timeText = itemView.findViewById(R.id.applyList_timeText);
            applyList_statusText = itemView.findViewById(R.id.applyList_statusText);

        }

        void onBind(ApplyListData data) {
            applyList_schoolText.setText(data.getSchool());
            applyList_memberChip.setText(data.getMember() + " : "+data.getMember());

            if(data.getDate()==0) {
                applyList_timeText.setText("오늘");
            }
            else {
                applyList_timeText.setText(data.getDate()+"일전");
            }

            switch (data.getStatus()) {
                case 0 :
                    applyList_statusText.setText("대기중");
                    applyList_statusText.setTextColor(Color.parseColor("#000000"));
                    break;
                case 1 :
                    applyList_statusText.setText("거절됨");
                    applyList_statusText.setTextColor(Color.parseColor("#DB4455"));
                    break;
                case 2 :
                    applyList_statusText.setText("수락됨");
                    applyList_statusText.setTextColor(Color.parseColor("#4B89DC"));
                    break;
                default:
                    break;
            }
        }
    }
}