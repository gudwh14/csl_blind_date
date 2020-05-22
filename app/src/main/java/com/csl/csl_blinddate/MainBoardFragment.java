package com.csl.csl_blinddate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.csl.csl_blinddate.Adapter.HomeAdapter;
import com.csl.csl_blinddate.Adapter.HotMeetingAdapter;
import com.csl.csl_blinddate.Data.HomeData;
import com.csl.csl_blinddate.Data.ListData;
import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.csl.csl_blinddate.Data.RetrofitRepoList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.csl.csl_blinddate.RetrofitService.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainBoardFragment extends Fragment {
    RecyclerView homeRecyclerView;
    RecyclerView hotmeeting_recyclerVIew;
    HomeAdapter homeAdapter;
    HotMeetingAdapter hotMeetingAdapter;
    String board_title_1 = "자유게시판";
    String board_title_2 = "OOTD";
    String board_title_3 = "익명게시판";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RetrofitService retrofitService = retrofit.create(RetrofitService.class);
    public MainBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_board, container, false);
        homeRecyclerView = view.findViewById(R.id.homeRecyclerView);
        hotmeeting_recyclerVIew = view.findViewById(R.id.hotmeeting_recyclerView);
        hotMeetingAdapter = new HotMeetingAdapter();
        homeAdapter = new HomeAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        homeRecyclerView.setLayoutManager(linearLayoutManager);
        hotmeeting_recyclerVIew.setLayoutManager(linearLayoutManager2);

        hotmeeting_recyclerVIew.setAdapter(hotMeetingAdapter);
        homeRecyclerView.setAdapter(homeAdapter);

        refresh();
        hotMeetingRefresh();

        return view;
    }

    public void refresh() {
        homeAdapter.clear();
        homeAdapter.notifyDataSetChanged();

        HashMap<String,Object> data = new HashMap<>();
        data.put("board_title_1",board_title_1);
        data.put("board_title_2",board_title_2);
        data.put("board_title_3",board_title_3);

        Call<RetrofitRepoList> call = retrofitService.MainBoardRefresh(data);
        call.enqueue(new Callback<RetrofitRepoList>() {
            @Override
            public void onResponse(Call<RetrofitRepoList> call, Response<RetrofitRepoList> response) {
                ArrayList<RetrofitRepo> arrayList = response.body().getRepoArrayList();
                HomeData homeData_1 = new HomeData(board_title_1);
                HomeData homeData_2 = new HomeData(board_title_2);
                HomeData homeData_3 = new HomeData(board_title_3);
                int board1=0 ,board2 = 0,board3 = 0;
                for(int temp = 0; temp < arrayList.size(); temp++) {
                    RetrofitRepo repo = arrayList.get(temp);
                    String title = repo.getTitle();

                    if(repo.getBoard_title().equals(board_title_1)) {
                        if(board1 == 0) {
                            homeData_1.setHomePost_1(title);
                            homeData_1.setHomePost_1_id(repo.getBoard_id());
                            board1++;
                        }
                        else if(board1 == 1){
                            homeData_1.setHomePost_2(title);
                            homeData_1.setHomePost_2_id(repo.getBoard_id());
                            board1++;
                        }
                        else if(board1 == 2) {
                            homeData_1.setHomePost_3(title);
                            homeData_1.setHomePost_3_id(repo.getBoard_id());
                            board1++;
                        }
                    }
                    else if(repo.getBoard_title().equals(board_title_2)) {
                        if(board2 == 0) {
                            homeData_2.setHomePost_1(title);
                            homeData_2.setHomePost_1_id(repo.getBoard_id());
                            board1++;
                        }
                        else if(board2 == 1){
                            homeData_2.setHomePost_2(title);
                            homeData_2.setHomePost_2_id(repo.getBoard_id());
                            board1++;
                        }
                        else if(board2 == 2) {
                            homeData_2.setHomePost_3(title);
                            homeData_2.setHomePost_3_id(repo.getBoard_id());
                            board1++;
                        }
                    }
                    else if(repo.getBoard_title().equals(board_title_3)) {
                        if(board3 == 0) {
                            homeData_3.setHomePost_1(title);
                            homeData_3.setHomePost_1_id(repo.getBoard_id());
                            board1++;
                        }
                        else if(board3 == 1){
                            homeData_3.setHomePost_2(title);
                            homeData_3.setHomePost_2_id(repo.getBoard_id());
                            board1++;
                        }
                        else if(board3 == 2) {
                            homeData_3.setHomePost_3(title);
                            homeData_3.setHomePost_3_id(repo.getBoard_id());
                            board1++;
                        }
                    }
                }
                homeAdapter.addItem(homeData_1);
                homeAdapter.addItem(homeData_2);
                homeAdapter.addItem(homeData_3);

                homeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RetrofitRepoList> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void hotMeetingRefresh() {
        hotMeetingAdapter.clear();
        hotMeetingAdapter.notifyDataSetChanged();

        HashMap<String, Object> data = new HashMap<>();
        Call<RetrofitRepoList> call = retrofitService.HotListRefresh(data);
        call.enqueue(new Callback<RetrofitRepoList>() {
            @Override
            public void onResponse(Call<RetrofitRepoList> call, Response<RetrofitRepoList> response) {
                ArrayList<RetrofitRepo> arrayList = response.body().getRepoArrayList();
                int size = arrayList.size();
                for(int temp = 0 ; temp<size; temp++) {
                    RetrofitRepo repo = arrayList.get(temp);
                    ListData listData = new ListData(repo.getMeeting_id(),repo.getAge(),repo.getUserID(),repo.getSchool(),repo.isCertification(),repo.getMember(),repo.getGender(),repo.isNewbie(),repo.isStatus());
                    hotMeetingAdapter.addItem(listData);
                }
                hotMeetingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RetrofitRepoList> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
