package com.kyonggi.eku.view.signUp.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.kyonggi.eku.R;
import com.kyonggi.eku.databinding.DialogDeptBinding;
import com.kyonggi.eku.utils.adapters.DeptAdapter;
import com.kyonggi.eku.utils.callbacks.OnDeptSelectedListener;
import com.kyonggi.eku.view.signUp.fragment.FragmentSignupInfo;

import java.util.List;

public class DialogDepartmentPicker extends DialogFragment {
    private static final String TAG = "DialogDepartmentPicker";
    private final List<String> deptList;
    private final OnDeptSelectedListener listener;
    private DialogDeptBinding binding;

    public DialogDepartmentPicker(List<String> deptList, FragmentSignupInfo fragmentSignupInfo) {
        this.deptList = deptList;
        listener = fragmentSignupInfo;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogDeptBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DeptAdapter deptAdapter = new DeptAdapter(deptList, listener, this);
        binding.recyclerViewDept.setAdapter(deptAdapter);
        deptAdapter.notifyItemRangeInserted(0, deptList.size());
        builder.setMessage(R.string.department_hint)
                .setView(binding.getRoot());
        return builder.create();
    }
}
