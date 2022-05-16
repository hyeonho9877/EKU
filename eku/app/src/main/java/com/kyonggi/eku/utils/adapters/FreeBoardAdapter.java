package com.kyonggi.eku.utils.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.kyonggi.eku.databinding.BoardLoadingBinding;
import com.kyonggi.eku.databinding.FreeBoardItemBinding;
import com.kyonggi.eku.databinding.InfoBoardItemBinding;
import com.kyonggi.eku.model.FreeBoardPreview;
import com.kyonggi.eku.model.InfoBoardPreview;

import java.util.List;
import java.util.Objects;

public class FreeBoardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private static final String TAG = "FreeBoardAdapter";
    private List<FreeBoardPreview> list;

    public FreeBoardAdapter(List<FreeBoardPreview> list) {
        this.list = list;
    }

    public boolean insertFromHead(List<FreeBoardPreview> newList) {
        Log.d(TAG, "insert: " + newList);
        if (newList.size() != 0 && !(newList.get(0) == list.get(0))) {
            list.addAll(0, newList);
            return true;
        }
        return false;
    }

    public boolean insertFromTail(List<FreeBoardPreview> oldList) {
        Log.d(TAG, "insertFromTail: " + oldList.size());
        if (oldList.size() < 20) {
            list.addAll(oldList);
            return true;
        } else {
            list.addAll(oldList);
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            FreeBoardItemBinding binding = FreeBoardItemBinding.inflate(layoutInflater, parent, false);
            return new FreeBoardAdapter.FreeBoardViewHolder(binding);
        } else {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            BoardLoadingBinding binding = BoardLoadingBinding.inflate(layoutInflater, parent, false);
            return new FreeBoardAdapter.LoadingViewHolder(binding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FreeBoardAdapter.FreeBoardViewHolder) {
            ((FreeBoardAdapter.FreeBoardViewHolder) holder).bind(list.get(position));
        } else if (holder instanceof FreeBoardAdapter.LoadingViewHolder) {
            showLoadingView((FreeBoardAdapter.LoadingViewHolder) holder, position);
        }
    }

    private void showLoadingView(FreeBoardAdapter.LoadingViewHolder holder, int position) {

    }

    public List<FreeBoardPreview> getCurrentList() {
        return list;
    }

    class FreeBoardViewHolder extends RecyclerView.ViewHolder {
        private final FreeBoardItemBinding binding;
        private Integer writerNo;


        public FreeBoardViewHolder(FreeBoardItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(FreeBoardPreview item) {
            this.writerNo = item.getNo();
            // 각각의 UI 에 알람 정보를 업데이트한다
            binding.textFreeBoardWriter.setText(item.getWriter());
            binding.textFreeBoardTitle.setText(item.getTitle());
            binding.textFreeBoardTime.setText(item.getTime());
            String view = "조회 " + item.getView();
            binding.textFreeBoardView.setText(view);
            binding.textFreeBoardComment.setText(String.valueOf(item.getComments()));
            binding.textFreeBoardTitle.setOnClickListener(v -> {
                        Log.d("tag", "bind: " + this.getAbsoluteAdapterPosition());
                    }

            );
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        private BoardLoadingBinding binding;

        public LoadingViewHolder(@NonNull BoardLoadingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    static class DiffUtilCallback extends DiffUtil.ItemCallback<FreeBoardPreview> {


        @Override
        public boolean areItemsTheSame(@NonNull FreeBoardPreview oldItem, @NonNull FreeBoardPreview newItem) {
            // 두 알람의 아이디가 같으면 같은 아이디로 간주한다
            return Objects.equals(oldItem.getId(), newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull FreeBoardPreview oldItem, @NonNull FreeBoardPreview newItem) {
            return oldItem.equals(newItem);
        }
    }
}
