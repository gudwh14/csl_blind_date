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

        ChatListData chatListData = new ChatListData(getContext(),"서울대학교","딱좋아",2);
        chatListAdapter.addItem(chatListData);
        chatListAdapter.notifyDataSetChanged();

        return view;
    }
}
