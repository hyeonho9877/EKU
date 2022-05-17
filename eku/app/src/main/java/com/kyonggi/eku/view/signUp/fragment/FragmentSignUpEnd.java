package com.kyonggi.eku.view.signUp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kyonggi.eku.databinding.FragmentSignUpEndBinding;
import com.kyonggi.eku.view.signUp.OnConfirmedListener;

public class FragmentSignUpEnd extends Fragment {
    private FragmentSignUpEndBinding binding;
    private OnConfirmedListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignUpEndBinding.inflate(inflater, container, false);
        initListeners();
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnConfirmedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initListeners() {
        binding.buttonSignUpComplete.setOnClickListener(v->{
            listener.onSignUpEnd();
        });
    }
}