package com.kyonggi.eku.view.board.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kyonggi.eku.databinding.FragmentFreeBoardBinding;
import com.kyonggi.eku.model.FreeBoardPreview;
import com.kyonggi.eku.utils.adapters.FreeBoardAdapter;
import com.kyonggi.eku.view.board.activity.ActivityBoard;

import java.util.List;

public class FragmentFreeBoard extends Fragment {

    private static final String TAG = "FragmentFreeBoard";
    private FragmentFreeBoardBinding binding;
    private ActivityBoard activity;
    private FreeBoardAdapter adapter;
    private RecyclerView.OnScrollListener scrollListener;
    private boolean isLoading = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFreeBoardBinding.inflate(inflater, container, false);
        activity = (ActivityBoard) getActivity();

        initListeners();
        activity.getFreeBoardArticles();
        return binding.getRoot();
    }

    private void initListeners() {
        binding.swipeLayoutFreeBoard.setOnRefreshListener(() -> {
            binding.swipeLayoutFreeBoard.setRefreshing(true);
            activity.getFreeBoardArticles();
            binding.swipeLayoutFreeBoard.setRefreshing(false);
        });

        binding.recyclerViewFreeBard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    public void listArticles(List<FreeBoardPreview> articles) {
        if(articles.size() >= 20) addScrollListener();
        else if(articles.size()==0) binding.textFreeBoardNoArticles.setVisibility(View.VISIBLE);
        else binding.textFreeBoardNoArticles.setVisibility(View.INVISIBLE);

        adapter = new FreeBoardAdapter(articles, getContext());
        adapter.notifyItemRangeInserted(0, articles.size());
        binding.recyclerViewFreeBard.setAdapter(adapter);
    }

    public void updateArticles(List<FreeBoardPreview> newArticles) {
        if (adapter.insertFromHead(newArticles)) {
            adapter.notifyItemRangeInserted(0, newArticles.size());
            new Handler().postDelayed(() -> binding.recyclerViewFreeBard.scrollToPosition(0), 0);
        }
    }

    public void loadMoreArticles(List<FreeBoardPreview> oldArticles) {
        List<FreeBoardPreview> currentList = adapter.getCurrentList();
        currentList.remove(currentList.size() - 1);
        adapter.notifyItemRemoved(currentList.size()-1);
        isLoading = false;
        new Handler().postDelayed(()->{
            if (adapter.insertFromTail(oldArticles)) {
                adapter.notifyItemRangeInserted(adapter.getCurrentList().size(), oldArticles.size());
                binding.recyclerViewFreeBard.removeOnScrollListener(scrollListener);
            } else {
                adapter.notifyItemRangeInserted(adapter.getCurrentList().size(), oldArticles.size());
            }
        }, 200);
    }

    private void addScrollListener(){
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
                    List<FreeBoardPreview> currentList = adapter.getCurrentList();
                    if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == currentList.size() - 1) {
                        currentList.add(null);
                        binding.recyclerViewFreeBard.post(() -> adapter.notifyItemInserted(currentList.size() - 1));
                        activity.loadMoreFreeArticles(currentList.get(itemCount - 1).getId());
                        isLoading = true;
                    }
                }
            }
        };
        binding.recyclerViewFreeBard.addOnScrollListener(scrollListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(() -> activity.getFreeBoardArticles(), 200);
    }
}