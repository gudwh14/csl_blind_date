package com.csl.csl_blinddate.Adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csl.csl_blinddate.Data.ApplyData;
import com.csl.csl_blinddate.Data.ChatData;
import com.csl.csl_blinddate.R;

import java.util.ArrayList;

public class ApplyAdapter extends RecyclerView.Adapter<ApplyAdapter.ViewHolder> {
    private ArrayList<ApplyData> data = new ArrayList<>();

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.apply_view,parent,false);
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

    public void addItem(ApplyData data2) {
        // 외부에서 item을 추가시킬 함수입니다.
        data.add(data2);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView apply_ageText;
        TextView apply_schoolText;
        TextView apply_commentText;
        Button apply_refuseButton;
        Button apply_acceptButton;
        private Drawable drawable;

        ViewHolder(final View itemView) {
            super(itemView);
            apply_ageText = itemView.findViewById(R.id.apply_ageText);
            apply_schoolText = itemView.findViewById(R.id.apply_schoolText);
            apply_commentText = itemView.findViewById(R.id.apply_commentText);
            apply_refuseButton = itemView.findViewById(R.id.apply_refuseButton);
            apply_acceptButton = itemView.findViewById(R.id.apply_acceptButton);
        }

        void onBind(ApplyData data) {
            apply_ageText.setText("나이  : "+data.getAge());

            apply_schoolText.setText(data.getSchool());
            if(data.isCertify()) {
                drawable = itemView.getContext().getResources().getDrawable(R.drawable.check_icon);
            }
            else {
                drawable = itemView.getContext().getResources().getDrawable(R.drawable.noncertify_icon);
            }
            drawable.setBounds(0,0,60,60);
            apply_schoolText.setCompoundDrawables(null, null, drawable, null);

            apply_commentText.setText(data.getComment());
        }
    }
}