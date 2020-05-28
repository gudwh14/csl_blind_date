package com.csl.csl_blinddate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csl.csl_blinddate.BoardViewActivity;
import com.csl.csl_blinddate.ChatActivity;
import com.csl.csl_blinddate.Data.ApplyData;
import com.csl.csl_blinddate.Data.BoardData;
import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.csl.csl_blinddate.R;
import com.csl.csl_blinddate.RetrofitService;
import com.csl.csl_blinddate.SplashActivity;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.csl.csl_blinddate.RetrofitService.URL;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private ArrayList<BoardData> data = new ArrayList<>();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy MM/dd HH:mm");

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_view,parent,false);
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

    public void addItem(BoardData data2) {
        // 외부에서 item을 추가시킬 함수입니다.
        data.add(data2);
    }

    public void clear() {
        data.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView board_userText;
        TextView board_timeText;
        TextView board_titleText;
        TextView board_upText;
        TextView board_commentsText;
        ImageView board_imageView;
        Context context;

        private Drawable up_drawable;
        private Drawable comments_drawable;
        int board_id;
        String board_title;

        ViewHolder(final View itemView) {
            super(itemView);
            context = itemView.getContext();
            board_userText = itemView.findViewById(R.id.board_userText);
            board_timeText = itemView.findViewById(R.id.board_timeText);
            board_titleText = itemView.findViewById(R.id.board_titleText);
            board_upText = itemView.findViewById(R.id.board_upText);
            board_commentsText = itemView.findViewById(R.id.board_commentsText);
            board_imageView = itemView.findViewById(R.id.board_imageView);

            up_drawable = context.getResources().getDrawable(R.drawable.heart_icon);
            comments_drawable = context.getResources().getDrawable(R.drawable.comments_icon);

            up_drawable.setBounds(0,0,40,40);
            comments_drawable.setBounds(0,0,40,40);
        }

        void onBind(BoardData data) {
            Date time = new Date();
            board_title = data.getBoard_title();

            String str_time = simpleDateFormat.format(time);
            String[] n_time = str_time.split(" ");
            String[] b_time = data.getTime().split(" ");
            if((n_time[0]+n_time[1]).equals(b_time[0]+b_time[1])) {
                board_timeText.setText(b_time[2]);
            }
            else {
                board_timeText.setText(b_time[1]);
            }

            board_id = data.getBoard_id();
            if(board_title.equals("익명게시판")) {
                board_userText.setText("익명");
            }
            else {
                board_userText.setText(data.getUserID());
            }

            if(!(data.getImage_path().equals(""))) {
                board_imageView.setVisibility(View.VISIBLE);
            }
            else {
                board_imageView.setVisibility(View.INVISIBLE);
            }

            board_titleText.setText(data.getTitle());

            board_upText.setText(" "+data.getUp());
            board_commentsText.setText(" "+data.getComments());

            board_upText.setCompoundDrawables(up_drawable,null,null,null);
            board_commentsText.setCompoundDrawables(comments_drawable,null,null,null);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(view.getContext(), BoardViewActivity.class);
                        intent.putExtra("title",board_title);
                        intent.putExtra("board_id",board_id);
                        view.getContext().startActivity(intent);
                    }
                }
            });
        }

    }
}