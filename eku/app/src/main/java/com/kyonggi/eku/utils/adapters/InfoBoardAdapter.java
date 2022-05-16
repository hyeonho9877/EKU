package com.kyonggi.eku.utils.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.kyonggi.eku.databinding.InfoBoardItemBinding;
import com.kyonggi.eku.model.InfoBoardPreview;

import java.util.Objects;

public class InfoBoardAdapter extends ListAdapter<InfoBoardPreview, InfoBoardAdapter.InfoBoardViewHolder> {


    public InfoBoardAdapter() {
        super(new DiffUtilCallback());
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @NonNull
    @Override
    public InfoBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        InfoBoardItemBinding binding = InfoBoardItemBinding.inflate(layoutInflater, parent, false);
        return new InfoBoardViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoBoardViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class InfoBoardViewHolder extends RecyclerView.ViewHolder {
        private final InfoBoardItemBinding binding;
        private Integer writerNo;


        public InfoBoardViewHolder(InfoBoardItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(InfoBoardPreview item) {
            this.writerNo = item.getNo();
            // 각각의 UI 에 알람 정보를 업데이트한다
            binding.textInfoBoardWriter.setText(item.getWriter());
            binding.textInfoBoardTitle.setText(item.getTitle());
            binding.textInfoBoardTime.setText(item.getTime());
            String view = "조회 " + item.getView();
            binding.textInfoBoardView.setText(view);
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
