package com.csl.csl_blinddate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csl.csl_blinddate.BoardActivity;
import com.csl.csl_blinddate.BoardViewActivity;
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

    public void clear() {
        data.clear();
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
        Context context;
        int board_id_1,board_id_2,board_id_3;

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
            context = itemView.getContext();
            intent = new Intent(context, BoardActivity.class);
            title = data.getHomeTitle();
            board_id_1 = data.getHomePost_1_id();
            board_id_2 = data.getHomePost_2_id();
            board_id_3 = data.getHomePost_3_id();
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
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = 0;
                    Intent intent = new Intent(view.getContext(), BoardViewActivity.class);
                    intent.putExtra("title", title);
                    switch (view.getId()) {
                        case R.id.homePostText_1:
                            id = board_id_1;
                            intent.putExtra("board_id", board_id_1);
                            break;
                        case R.id.homePostText_2 :
                            id = board_id_2;
                            intent.putExtra("board_id", board_id_2);
                            break;
                        case R.id.homePostText_3 :
                            id = board_id_3;
                            intent.putExtra("board_id", board_id_3);
                            break;
                    }
                    if(id != 0) {
                        view.getContext().startActivity(intent);
                    }
                }
            };
            homePostText_1.setText(data.getHomePost_1().toString());
            homePostText_1.setOnClickListener(clickListener);
            homePostText_2.setText(data.getHomePost_2().toString());
            homePostText_2.setOnClickListener(clickListener);
            homePostText_3.setText(data.getHomePost_3().toString());
            homePostText_3.setOnClickListener(clickListener);
        }
    }
}
