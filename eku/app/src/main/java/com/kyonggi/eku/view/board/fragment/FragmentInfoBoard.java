package com.kyonggi.eku.view.board.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kyonggi.eku.databinding.FragmentInfoBoardBinding;
import com.kyonggi.eku.model.BoardPreview;
import com.kyonggi.eku.model.InfoBoardPreview;
import com.kyonggi.eku.utils.adapters.InfoBoardAdapter;
import com.kyonggi.eku.view.board.activity.ActivityBoard;

import java.util.List;
import java.util.stream.Collectors;

public class FragmentInfoBoard extends Fragment {

    private static final String TAG = "FragmentInfoBoard";
    private FragmentInfoBoardBinding binding;
    private ActivityBoard activity;
    InfoBoardAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInfoBoardBinding.inflate(inflater, container, false);

        adapter = new InfoBoardAdapter();
        activity = (ActivityBoard) getActivity();

        activity.getInfoBoardArticles();
        return binding.getRoot();
    }

    public void listArticles(List<InfoBoardPreview> articles) {
        adapter.submitList(articles);
        adapter.notifyItemRangeInserted(0, articles.size());
        binding.recyclerViewInfoBard.setAdapter(adapter);
    }
}