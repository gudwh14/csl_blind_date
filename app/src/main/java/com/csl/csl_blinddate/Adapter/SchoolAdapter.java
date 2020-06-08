package com.csl.csl_blinddate.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csl.csl_blinddate.BoardViewActivity;
import com.csl.csl_blinddate.Data.BoardData;
import com.csl.csl_blinddate.Data.SchoolData;
import com.csl.csl_blinddate.R;
import com.csl.csl_blinddate.Register.MailCertifyActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.ViewHolder> {
    private ArrayList<SchoolData> data = new ArrayList<>();

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_list_view,parent,false);
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

    public void addItem(SchoolData data2) {
        // 외부에서 item을 추가시킬 함수입니다.
        data.add(data2);
    }

    public void addAll(List<SchoolData> list) {
        data.addAll(list);
    }

    public void clear() {
        data.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView schoolText;



        ViewHolder(final View itemView) {
            super(itemView);

            schoolText = itemView.findViewById(R.id.schoolListView_schoolText);
        }

        void onBind(SchoolData data) {
            final String school = data.getSchool();
            final String mail = data.getMail();

            schoolText.setText(school);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent();
                        intent.putExtra("school",school);
                        intent.putExtra("mail",mail);
                        ((Activity)(view.getContext())).setResult(Activity.RESULT_OK,intent);
                        ((Activity)(view.getContext())).finish();
                    }
                }
            });
        }

    }
}