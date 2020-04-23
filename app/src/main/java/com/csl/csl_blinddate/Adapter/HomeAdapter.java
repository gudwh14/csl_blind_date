package com.csl.csl_blinddate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csl.csl_blinddate.BoardActivity;
import com.csl.csl_blinddate.Data.HomeData;
import com.csl.csl_blinddate.R;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private ArrayList<HomeData> data = new ArrayList<>();

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_view,parent,false);
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

    public void addItem(HomeData data2) {
        // 외부에서 item을 추가시킬 함수입니다.
        data.add(data2);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView homeTitleText;
        private TextView homePostText_1;
        private TextView homePostText_2;
        private TextView homePostText_3;
        private ImageView imageView;
        Intent intent;
        private String title;
        private LinearLayout homeTitleLayout;

        ViewHolder(View itemView) {
            super(itemView);

            homeTitleText = itemView.findViewById(R.id.homeTitleText);
            homePostText_1 = itemView.findViewById(R.id.homePostText_1);
            homePostText_2 = itemView.findViewById(R.id.homePostText_2);
            homePostText_3 = itemView.findViewById(R.id.homePostText_3);
            imageView = itemView.findViewById(R.id.homeimageView);
            homeTitleLayout = itemView.findViewById(R.id.homeTitleLayout);
        }

        void onBind(HomeData data) {
            final Context context = data.getContext();
            intent = new Intent(context, BoardActivity.class);
            title = data.getHomeTitle();
            if(title.equals("OOTD")) {
                imageView.setImageResource(R.drawable.photo_icon);
            }
            homeTitleText.setText(" "+ data.getHomeTitle().toString());

            homeTitleLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent.putExtra("title",title);
                    context.startActivity(intent);
                }
            });
            homePostText_1.setText(data.getHomePost_1().toString());
            homePostText_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            homePostText_2.setText(data.getHomePost_2().toString());
            homePostText_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            homePostText_3.setText(data.getHomePost_3().toString());
            homePostText_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
