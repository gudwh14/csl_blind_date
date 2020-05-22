package com.csl.csl_blinddate.Adapter;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.csl.csl_blinddate.Data.CommentData;
import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.csl.csl_blinddate.Data.UserData;
import com.csl.csl_blinddate.R;
import com.csl.csl_blinddate.RetrofitService;
import com.csl.csl_blinddate.SplashActivity;

import java.util.ArrayList;
import java.util.HashMap;

import static com.csl.csl_blinddate.RetrofitService.URL;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    long timer = 0;

    // 인터페이스 작성
    public interface OnRefreshChanged {
        public void onRefreshChanged(boolean refresh);
    }

    private OnRefreshChanged onRefreshChanged;

    public void setOnRefreshChanged(OnRefreshChanged onRefreshChanged){
        this.onRefreshChanged = onRefreshChanged;
    }
    //
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
        ImageView comment_replyImage;
        private Drawable drawable;
        boolean anonymous = false;
        int up;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        ViewHolder(final View itemView) {
            super(itemView);

            comment_userText = itemView.findViewById(R.id.comment_userText);
            comment_timeText = itemView.findViewById(R.id.comment_timeText);
            comment_upText = itemView.findViewById(R.id.comment_upText);
            comment_commentText = itemView.findViewById(R.id.comment_commentText);
            comment_upImage = itemView.findViewById(R.id.comment_upImage);
            comment_replyImage = itemView.findViewById(R.id.comment_replyImage);

            drawable = itemView.getContext().getResources().getDrawable(R.drawable.heart_icon);
            drawable.setBounds(0,0,40,40);


        }

        void onBind(CommentData data) {
            final int comment_id = data.getComment_id();
            final int board_id = data.getBoard_id();
            if(data.isCommentUp()) {
                up = 1;
                comment_upImage.setImageResource(R.drawable.heart_after_icon);
            }
            else {
                up = 0;
                comment_upImage.setImageResource(R.drawable.heart_icon);
            }

            if(data.getBoard_title().equals("익명게시판")) {
                anonymous = true;
            }

            String[] time = data.getTime().split(" ");
            if(data.isWriter()) {
                comment_userText.setText("글쓴이");
                comment_userText.setTextColor(Color.parseColor("#FF486297"));
            }
            else {
                if (data.isAnonymous()) {
                    comment_userText.setText("익명" + data.getAnony_count());
                } else {
                    comment_userText.setText(data.getUserID());
                }
            }
            comment_upText.setCompoundDrawables(drawable,null,null,null);
            comment_upText.setText(" " + data.getUp());

            comment_commentText.setText(data.getComment());
            comment_timeText.setText(time[1] + " " + time[2]);

            comment_replyImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View layout = LayoutInflater.from(view.getContext()).inflate(R.layout.reply,null);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("답글")
                            .setView(layout);

                    final AlertDialog alertDialog = builder.create();

                    ImageView replyView_commentSendView = layout.findViewById(R.id.replyView_commentSendView);
                    final EditText replyView_commentText = layout.findViewById(R.id.replyView_commentText);

                    replyView_commentSendView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String comment = replyView_commentText.getText().toString();
                            if(comment.trim().equals("")) {
                                Toast.makeText(view.getContext(),"답글을 입력해주세요",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                HashMap<String, Object> data = new HashMap<>();
                                data.put("board_id",board_id);
                                data.put("comment_id",comment_id);
                                data.put("userID", UserData.getInstance().getUserID());
                                data.put("comment",comment);
                                data.put("anonymous",anonymous);

                                Call<RetrofitRepo> call = retrofitService.ReplyInsert(data);
                                call.enqueue(new Callback<RetrofitRepo>() {
                                    @Override
                                    public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                                        RetrofitRepo repo = response.body();
                                        if(repo.isSuccess()) {
                                            alertDialog.dismiss();
                                            if(onRefreshChanged != null) {
                                                onRefreshChanged.onRefreshChanged(true);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<RetrofitRepo> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                            }
                        }
                    });

                    alertDialog.show();
                }
            });

            comment_upImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(System.currentTimeMillis()-timer>=1000) {
                        HashMap<String, Object> data = new HashMap<>();
                        data.put("id",comment_id);
                        data.put("userID", UserData.getInstance().getUserID());
                        data.put("code",3);
                        data.put("on",up);

                        Call<RetrofitRepo> call = retrofitService.BoardDetailInsert(data);
                        call.enqueue(new Callback<RetrofitRepo>() {
                            @Override
                            public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                                RetrofitRepo repo = response.body();
                                if(!repo.isSuccess()) {

                                }
                                else {
                                    if(onRefreshChanged != null) {
                                        onRefreshChanged.onRefreshChanged(true);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<RetrofitRepo> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                        try {
                            Thread.sleep(150) ;
                        } catch (Exception e) {
                            e.printStackTrace() ;
                        }
                        timer=System.currentTimeMillis();
                    }
                    else if(System.currentTimeMillis()-timer<1000) {
                        Toast.makeText(view.getContext(), "잠시후 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    class my_ViewHolder extends RecyclerView.ViewHolder {
        TextView reply_userText;
        TextView reply_timeText;
        TextView reply_upText;
        TextView reply_commentText;
        ImageView reply_upImage;
        private Drawable drawable;
        int up;
        int comment_id;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

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
            comment_id = data.getComment_id();
            if(data.isCommentUp()) {
                up = 1;
                reply_upImage.setImageResource(R.drawable.heart_after_icon);
            }
            else {
                up = 0;
                reply_upImage.setImageResource(R.drawable.heart_icon);
            }

            if(data.isWriter()) {
                reply_userText.setText("글쓴이");
                reply_userText.setTextColor(Color.parseColor("#FF486297"));
            }
            else {
                if (data.isAnonymous()) {
                    reply_userText.setText("익명" + data.getAnony_count());
                } else {
                    reply_userText.setText(data.getUserID());
                }
            }
            reply_upText.setCompoundDrawables(drawable,null,null,null);
            reply_upText.setText(" " + data.getUp());

            reply_commentText.setText(data.getComment());
            reply_timeText.setText(time[1] + " " + time[2]);

            reply_upImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(System.currentTimeMillis()-timer>=1000) {
                        HashMap<String, Object> data = new HashMap<>();
                        data.put("id",comment_id);
                        data.put("userID",UserData.getInstance().getUserID());
                        data.put("code",3);
                        data.put("on",up);

                        Call<RetrofitRepo> call = retrofitService.BoardDetailInsert(data);
                        call.enqueue(new Callback<RetrofitRepo>() {
                            @Override
                            public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                                RetrofitRepo repo = response.body();
                                if(!repo.isSuccess()) {

                                }
                                else {
                                    if(onRefreshChanged != null) {
                                        onRefreshChanged.onRefreshChanged(true);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<RetrofitRepo> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                        try {
                            Thread.sleep(150) ;
                        } catch (Exception e) {
                            e.printStackTrace() ;
                        }
                        timer=System.currentTimeMillis();
                    }
                    else if(System.currentTimeMillis()-timer<1000) {
                        Toast.makeText(view.getContext(), "잠시후 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


}