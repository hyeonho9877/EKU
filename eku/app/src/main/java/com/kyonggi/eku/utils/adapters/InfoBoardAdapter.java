package com.kyonggi.eku.utils.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kyonggi.eku.DetailAnnounce;
import com.kyonggi.eku.databinding.BoardLoadingBinding;
import com.kyonggi.eku.databinding.InfoBoardItemBinding;
import com.kyonggi.eku.model.InfoBoardPreview;

import java.util.List;
import java.util.Objects;

public class InfoBoardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private static final String TAG = "InfoBoardAdapter";
    private List<InfoBoardPreview> list;
    private final Context context;

    public InfoBoardAdapter(List<InfoBoardPreview> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public boolean insertFromHead(List<InfoBoardPreview> newList) {
        Log.d(TAG, "insert: " + newList);
        if (newList.size() != 0 && !(newList.get(0) == list.get(0))) {
            list.addAll(0, newList);
            return true;
        }
        return false;
    }

    public boolean insertFromTail(List<InfoBoardPreview> oldList) {
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
            InfoBoardItemBinding binding = InfoBoardItemBinding.inflate(layoutInflater, parent, false);
            return new InfoBoardViewHolder(binding);
        } else {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            BoardLoadingBinding binding = BoardLoadingBinding.inflate(layoutInflater, parent, false);
            return new LoadingViewHolder(binding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InfoBoardViewHolder) {
            ((InfoBoardViewHolder) holder).bind(list.get(position));
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }

    private void showLoadingView(LoadingViewHolder holder, int position) {

    }

    public List<InfoBoardPreview> getCurrentList() {
        return list;
    }

    class InfoBoardViewHolder extends RecyclerView.ViewHolder {
        private final InfoBoardItemBinding binding;
        private int writerNo;
        private long id;


        public InfoBoardViewHolder(InfoBoardItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(InfoBoardPreview item) {
            this.writerNo = item.getNo();
            this.id = item.getId();
            // 각각의 UI 에 알람 정보를 업데이트한다
            binding.textInfoBoardWriter.setText(item.getWriter());
            binding.textInfoBoardTitle.setText(item.getTitle());
            binding.textInfoBoardTime.setText(item.getTime());
            String view = "조회 " + item.getView();
            binding.textInfoBoardView.setText(view);
            binding.textInfoBoardTitle.setOnClickListener(v -> {
                        Intent intent = new Intent(context, DetailAnnounce.class);
                        intent.putExtra("id", String.valueOf(id));
                        context.startActivity(intent);
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

    static class DiffUtilCallback extends DiffUtil.ItemCallback<InfoBoardPreview> {


        @Override
        public boolean areItemsTheSame(@NonNull InfoBoardPreview oldItem, @NonNull InfoBoardPreview newItem) {
            // 두 알람의 아이디가 같으면 같은 아이디로 간주한다
            return Objects.equals(oldItem.getId(), newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull InfoBoardPreview oldItem, @NonNull InfoBoardPreview newItem) {
            return oldItem.equals(newItem);
        }
    }
}
