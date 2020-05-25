package com.csl.csl_blinddate;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csl.csl_blinddate.Adapter.ChatListAdapter;
import com.csl.csl_blinddate.Data.ChatListData;
import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.csl.csl_blinddate.Data.RetrofitRepoList;
import com.csl.csl_blinddate.Data.UserData;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.csl.csl_blinddate.RetrofitService.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatTabFragment extends Fragment {
    RecyclerView chatlistRecyclerView;
    ChatListAdapter chatListAdapter;

    public ChatTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Layout View 초기화
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.MaterialTheme);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        View view = localInflater.inflate(R.layout.fragment_chat_tab, container, false);

        // View Member 초기화
        chatlistRecyclerView = view.findViewById(R.id.chatlistRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        chatlistRecyclerView.setLayoutManager(linearLayoutManager);

        chatListAdapter = new ChatListAdapter();
        chatlistRecyclerView.setAdapter(chatListAdapter);

        // refresh
        refresh();

        return view;
    }

    public void refresh() {
        chatListAdapter.clear(); // 초기화
        chatListAdapter.notifyDataSetChanged();

        HashMap<String, Object> data = new HashMap<>();
        data.put("userID", UserData.getInstance().getUserID());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<RetrofitRepoList> call = retrofitService.ChatListRefresh(data);
        call.enqueue(new Callback<RetrofitRepoList>() {
            @Override
            public void onResponse(Call<RetrofitRepoList> call, Response<RetrofitRepoList> response) {
                ArrayList<RetrofitRepo> arrayList = response.body().getRepoArrayList();
                int size = arrayList.size();
                for(int temp = 0; temp<size; temp++) {
                    RetrofitRepo repo = arrayList.get(temp);
                    ChatListData chatListData = new ChatListData(repo.getMeeting_id(),repo.getSchool(),repo.getUserID(),repo.getMember());
                    chatListAdapter.addItem(chatListData);
                }
                chatListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RetrofitRepoList> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
