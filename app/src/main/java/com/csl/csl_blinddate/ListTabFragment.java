package com.csl.csl_blinddate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.csl.csl_blinddate.Adapter.ListAdapter;
import com.csl.csl_blinddate.Data.ListData;
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
public class ListTabFragment extends Fragment {
    RecyclerView listRecyclerView;
    ListAdapter listAdapter;
    Button listWriteButton;
    SwipeRefreshLayout listSwipeLayout;
    CheckBox list_newbieCheckBox;
    FrameLayout listTab_layout;

    RetrofitRepo repo;
    ListData listData;
    int newbie;
    public ListTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.MaterialTheme);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        View view = localInflater.inflate(R.layout.fragment_list_tab, container, false);


        // RecyclerView, Adapter 초기화
        listTab_layout = view.findViewById(R.id.listTab_layout);
        listRecyclerView = view.findViewById(R.id.ListRecyclerView);
        listSwipeLayout = view.findViewById(R.id.listSwipeLayout);
        listAdapter = new ListAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        listRecyclerView.setLayoutManager(linearLayoutManager);

        listRecyclerView.setAdapter(listAdapter);

        list_newbieCheckBox = view.findViewById(R.id.list_newbieCheckBox);

        if (UserData.getInstance().getAge() != 20) {
            list_newbieCheckBox.setVisibility(View.INVISIBLE);
        }
        // List Refresh
        listSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                listSwipeLayout.setRefreshing(false);
            }
        });

        //
        listWriteButton = view.findViewById(R.id.ListWriteButton);
        listWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ListWriteActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void refresh() {
        listAdapter.clear(); // adapter 초기화
        listAdapter.notifyDataSetChanged();

        if(list_newbieCheckBox.isChecked()) {
            newbie = 1;
        }
        else {
            newbie = 0;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        HashMap<String, Object> data = new HashMap<>();
        data.put("newbie",newbie);
        Call<RetrofitRepoList> call = retrofitService.ListRefresh(data);
        call.enqueue(new Callback<RetrofitRepoList>() {
            @Override
            public void onResponse(Call<RetrofitRepoList> call, Response<RetrofitRepoList> response) {
                RetrofitRepoList repoList = (RetrofitRepoList)response.body();
                ArrayList<RetrofitRepo> arrayList = repoList.getRepoArrayList();
                int size = arrayList.size();
                for (int temp = 0; temp < size; temp++) {
                    repo = arrayList.get(temp);
                    listData = new ListData(repo.getMeeting_id(),repo.getAge(),repo.getUserID(),repo.getSchool(),repo.isCertification(),repo.getMember(),repo.getGender(),repo.isNewbie(),repo.isStatus());
                    listAdapter.addItem(listData);
                }
                listAdapter.notifyDataSetChanged();
                //listRecyclerView.setAdapter(listAdapter);
            }

            @Override
            public void onFailure(Call<RetrofitRepoList> call, Throwable t) {
                Toast.makeText(getContext(),"LIST_REQUEST 실패",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onStart() {
        refresh();
        super.onStart();
    }
}
