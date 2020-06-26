package com.csl.csl_blinddate.Adapter;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csl.csl_blinddate.Data.ApplyData;
import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.csl.csl_blinddate.Data.UserData;
import com.csl.csl_blinddate.R;
import com.csl.csl_blinddate.RetrofitService;


import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.csl.csl_blinddate.RetrofitService.URL;

public class ApplyAdapter extends RecyclerView.Adapter<ApplyAdapter.ViewHolder> {
    public interface OnRefreshChanged {
        public void onRefreshChanged(boolean refresh);
    }

    private OnRefreshChanged onRefreshChanged;

    public void setOnRefreshChanged(OnRefreshChanged onRefreshChanged){
        this.onRefreshChanged = onRefreshChanged;
    }

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

    public void clear() {
        data.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView apply_ageText;
        TextView apply_schoolText;
        TextView apply_commentText;
        Button apply_refuseButton;
        Button apply_acceptButton;
        private Drawable drawable;
        int apply_id;

        ViewHolder(final View itemView) {
            super(itemView);
            apply_ageText = itemView.findViewById(R.id.apply_ageText);
            apply_schoolText = itemView.findViewById(R.id.apply_schoolText);
            apply_commentText = itemView.findViewById(R.id.apply_commentText);
            apply_refuseButton = itemView.findViewById(R.id.apply_refuseButton);
            apply_acceptButton = itemView.findViewById(R.id.apply_acceptButton);
        }

        void onBind(ApplyData data) {
            apply_id = data.getApply_id();
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

            apply_acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("수락시 미팅이 '닫힘'상태로 전환됩니다")
                            .setPositiveButton("수락", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    select(true);
                                }
                            })
                            .setNegativeButton("취소",null)
                            .create()
                            .show();
                }
            });

            apply_refuseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    select(false);
                }
            });
        }

        void select(final boolean selection) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("userID_A", UserData.getInstance().getUserID());
            data.put("apply_id", apply_id);
            data.put("selection",selection);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RetrofitService retrofitService = retrofit.create(RetrofitService.class);
            Call<RetrofitRepo> call = retrofitService.ApplySelect(data);
            call.enqueue(new Callback<RetrofitRepo>() {
                @Override
                public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                    RetrofitRepo repo = response.body();
                    if(repo.isSuccess()) {
                        if(selection) {
                            Toast.makeText(itemView.getContext(),"수락 하였습니다",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(itemView.getContext(),"거절 하였습니다",Toast.LENGTH_SHORT).show();
                        }
                        if(onRefreshChanged != null) {
                            onRefreshChanged.onRefreshChanged(true);
                        }
                    }
                    else {
                        Toast.makeText(itemView.getContext(),"서버통신 에러",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RetrofitRepo> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
}