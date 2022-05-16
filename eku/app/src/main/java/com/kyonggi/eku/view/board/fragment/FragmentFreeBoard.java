package com.kyonggi.eku.view.board.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kyonggi.eku.databinding.FragmentFreeBoardBinding;
import com.kyonggi.eku.databinding.FragmentInfoBoardBinding;
import com.kyonggi.eku.model.FreeBoardPreview;
import com.kyonggi.eku.model.InfoBoardPreview;
import com.kyonggi.eku.utils.adapters.FreeBoardAdapter;
import com.kyonggi.eku.utils.adapters.InfoBoardAdapter;
import com.kyonggi.eku.view.board.activity.ActivityBoard;

import java.util.List;

public class FragmentFreeBoard extends Fragment {

    private static final String TAG = "FragmentInfoBoard";
    private FragmentFreeBoardBinding binding;
    private ActivityBoard activity;
    private FreeBoardAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFreeBoardBinding.inflate(inflater, container, false);

        adapter = new FreeBoardAdapter();
        activity = (ActivityBoard) getActivity();

        activity.getFreeBoardArticles();
        return binding.getRoot();
    }

    public void listArticles(List<FreeBoardPreview> articles) {
        adapter.submitList(articles);
        adapter.notifyDataSetChanged();
        binding.recyclerViewFreeBard.setAdapter(adapter);
    }
}