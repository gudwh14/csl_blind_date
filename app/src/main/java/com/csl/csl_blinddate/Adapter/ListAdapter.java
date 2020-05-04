package com.csl.csl_blinddate.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csl.csl_blinddate.Data.ListData;
import com.csl.csl_blinddate.ListInformActivity;
import com.csl.csl_blinddate.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;

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

    public void addItem(ListData data2) {
        // 외부에서 item을 추가시킬 함수입니다.
        data.add(data2);
    }

    public void clear() {
        data.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ListSchool_Text;
        private TextView ListCertify_Text;
        private TextView list_ageText;
        private Chip ListMember_Chip;
        private ImageView ListGender_Image;
        private MaterialButton ListOpen_Button;
        private Drawable drawable;

        private boolean open;
        private int id;
        private Context context;

        ViewHolder(View itemView) {
            super(itemView);
            list_ageText = itemView.findViewById(R.id.list_ageText);
            ListSchool_Text = itemView.findViewById(R.id.ListSchool_Text);
            ListCertify_Text = itemView.findViewById(R.id.ListCertify_Text);
            ListMember_Chip = itemView.findViewById(R.id.ListMember_Chip);
            ListGender_Image = itemView.findViewById(R.id.ListGender_Image);
            ListOpen_Button = itemView.findViewById(R.id.ListOpen_Button);

        }

        void onBind(ListData data) {
            ListSchool_Text.setText(data.getSchool());
            open = data.isOpen();
            id = data.getList_id();
            context = itemView.getContext();

            list_ageText.setText(data.getAge()+"살");

            if(data.getCertification()) {
                drawable = context.getResources().getDrawable(R.drawable.check_icon);
                ListCertify_Text.setText("인증");
            }
            else {
                drawable = context.getResources().getDrawable(R.drawable.noncertify_icon);
                ListCertify_Text.setText("미 인증");
            }
            drawable.setBounds(0,0,60,60);
            ListCertify_Text.setCompoundDrawables(drawable, null, null, null);

            ListMember_Chip.setText(data.getMember() + " : " + data.getMember());

            if(data.getGender().equals("M")) {
                ListGender_Image.setImageResource(R.drawable.boy_icon);
            }
            else {
                ListGender_Image.setImageResource(R.drawable.girl_icon);
            }

            if(open) {
                ListOpen_Button.setText("신청");
            }
            else {
                ListOpen_Button.setText("닫힘");
                ListOpen_Button.setBackgroundTintList(itemView.getResources().getColorStateList(R.color.grey));
            }

            ListOpen_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(open) {
                        Intent intent = new Intent(context, ListInformActivity.class);
                        intent.putExtra("id",id);
                        context.startActivity(intent);
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("안내")
                                .setMessage("한발 늦었네요....\n이미 다른 분들이 신청한 미팅 입니다")
                                .setPositiveButton("확인",null)
                                .create()
                                .show();
                    }
                }
            });
        }
    }
}
