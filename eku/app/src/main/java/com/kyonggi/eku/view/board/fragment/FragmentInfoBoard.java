package com.kyonggi.eku.view.board.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kyonggi.eku.databinding.FragmentInfoBoardBinding;
import com.kyonggi.eku.model.InfoBoardPreview;
import com.kyonggi.eku.utils.adapters.InfoBoardAdapter;
import com.kyonggi.eku.view.board.activity.ActivityBoard;

import java.util.List;

public class FragmentInfoBoard extends Fragment {

    private static final String TAG = "FragmentInfoBoard";
    private FragmentInfoBoardBinding binding;
    private ActivityBoard activity;
    private InfoBoardAdapter adapter;
    private RecyclerView.OnScrollListener scrollListener;
    private boolean isLoading = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInfoBoardBinding.inflate(inflater, container, false);

        activity = (ActivityBoard) getActivity();

        initListeners();
        activity.getInfoBoardArticles();

        return binding.getRoot();
    }

    private void initListeners() {
        binding.swipeLayoutInfoBoard.setOnRefreshListener(() -> {
            binding.swipeLayoutInfoBoard.setRefreshing(true);
            activity.updateInfoBoard(adapter.getCurrentList().get(0).getId());
            Log.d(TAG, "initListeners: " + adapter.getCurrentList().get(0).getId());
            binding.swipeLayoutInfoBoard.setRefreshing(false);
        });

        scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int itemCount = recyclerView.getAdapter().getItemCount();
                if (!isLoading) {
                    List<InfoBoardPreview> currentList = adapter.getCurrentList();
                    if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == currentList.size() - 1) {
                        currentList.add(null);
                        adapter.notifyItemInserted(currentList.size() - 1);
                        activity.loadMoreInfoArticles(currentList.get(itemCount - 1).getId());
                        isLoading = true;
                    }
                }
            }
        };
        binding.recyclerViewInfoBard.addOnScrollListener(scrollListener);
    }

    public void listArticles(List<InfoBoardPreview> articles) {
        adapter = new InfoBoardAdapter(articles, getContext());
        adapter.notifyItemRangeInserted(0, articles.size());
        binding.recyclerViewInfoBard.setAdapter(adapter);
    }

    public void updateArticles(List<InfoBoardPreview> newArticles) {
        if (adapter.insertFromHead(newArticles)) {
            adapter.notifyItemRangeInserted(0, newArticles.size());
            new Handler().postDelayed(() -> binding.recyclerViewInfoBard.scrollToPosition(0), 0);
        }
    }

    public void loadMoreArticles(List<InfoBoardPreview> oldArticles) {
        List<InfoBoardPreview> currentList = adapter.getCurrentList();
        currentList.remove(currentList.size() - 1);
        adapter.notifyItemRemoved(currentList.size()-1);
        isLoading = false;
        if (adapter.insertFromTail(oldArticles)) {
            adapter.notifyItemRangeInserted(adapter.getCurrentList().size(), oldArticles.size());
            binding.recyclerViewInfoBard.removeOnScrollListener(scrollListener);
        } else {
            adapter.notifyItemRangeInserted(adapter.getCurrentList().size(), oldArticles.size());
        }
    }
}