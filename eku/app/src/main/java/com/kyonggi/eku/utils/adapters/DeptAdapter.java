package com.kyonggi.eku.utils.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.kyonggi.eku.databinding.ItemDeptBinding;
import com.kyonggi.eku.view.signUp.OnDeptSelectedListener;
import com.kyonggi.eku.view.signUp.dialog.DialogDepartmentPicker;

import java.util.List;

public class DeptAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "DeptAdapter";
    private final OnDeptSelectedListener listener;
    private final List<String> deptList;
    private final DialogFragment dialog;

    public DeptAdapter(List<String> deptList, OnDeptSelectedListener listener, DialogDepartmentPicker dialog) {
        this.deptList = deptList;
        this.listener = listener;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemDeptBinding binding = ItemDeptBinding.inflate(layoutInflater, parent, false);
        return new DeptAdapter.DepartmentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((DepartmentViewHolder) holder).bind(deptList.get(position));
    }

    @Override
    public int getItemCount() {
        return deptList.size();
    }

    class DepartmentViewHolder extends RecyclerView.ViewHolder {
        private final ItemDeptBinding binding;

        public DepartmentViewHolder(ItemDeptBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String item) {
            binding.textDept.setText(item);
            binding.constraintLayoutDeptDialog.setOnClickListener(v->{
                listener.onSelected(item);
                dialog.dismiss();
            });
        }
    }
}
