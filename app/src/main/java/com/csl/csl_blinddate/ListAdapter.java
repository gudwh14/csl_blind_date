package com.csl.csl_blinddate;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<ListData> data = new ArrayList<>();

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view,parent,false);
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

    void addItem(ListData data2) {
        // 외부에서 item을 추가시킬 함수입니다.
        data.add(data2);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ListSchool_Text;
        private TextView ListCertify_Text;
        private TextView ListMember_Text;
        private ImageView ListGender_Image;
        private Button ListOpen_Button;
        private Drawable drawable;

        ViewHolder(View itemView) {
            super(itemView);
            ListSchool_Text = itemView.findViewById(R.id.ListSchool_Text);
            ListCertify_Text = itemView.findViewById(R.id.ListCertify_Text);
            ListMember_Text = itemView.findViewById(R.id.ListMember_Text);
            ListGender_Image = itemView.findViewById(R.id.ListGender_Image);
            ListOpen_Button = itemView.findViewById(R.id.ListOpen_Button);

        }

        void onBind(ListData data) {
            ListSchool_Text.setText(data.getSchool());

            if(data.getCertification()) {
                drawable = data.getContext().getResources().getDrawable(R.drawable.check_icon);
                ListCertify_Text.setText("인증");
            }
            else {
                drawable = data.getContext().getResources().getDrawable(R.drawable.noncertify_icon);
                ListCertify_Text.setText("미 인증");
            }
            drawable.setBounds(0,0,60,60);
            ListCertify_Text.setCompoundDrawables(drawable, null, null, null);

            ListMember_Text.setText(data.getMember() + " : " + data.getMember());

            if(data.getGender()) {
                ListGender_Image.setImageResource(R.drawable.boy_icon);
            }
            else {
                ListGender_Image.setImageResource(R.drawable.girl_icon);
            }

            if(data.getOpen()) {
                ListOpen_Button.setText("Open");
            }
            else {
                ListOpen_Button.setText("다음 기회에....");
            }
        }
    }
}
