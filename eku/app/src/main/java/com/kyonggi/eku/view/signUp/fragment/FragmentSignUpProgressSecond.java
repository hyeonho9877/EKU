package com.kyonggi.eku.view.signUp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kyonggi.eku.databinding.FragmentSignUpProgressFirstBinding;
import com.kyonggi.eku.databinding.FragmentSignUpProgressSecondBinding;

public class FragmentSignUpProgressSecond extends Fragment {

    private FragmentSignUpProgressSecondBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignUpProgressSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}