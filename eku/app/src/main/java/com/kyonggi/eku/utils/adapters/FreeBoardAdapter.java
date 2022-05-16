package com.kyonggi.eku.utils.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.kyonggi.eku.databinding.FreeBoardItemBinding;
import com.kyonggi.eku.databinding.InfoBoardItemBinding;
import com.kyonggi.eku.model.FreeBoardPreview;
import com.kyonggi.eku.model.InfoBoardPreview;

import java.util.Objects;

public class FreeBoardAdapter extends ListAdapter<FreeBoardPreview, FreeBoardAdapter.FreeBoardViewHolder> {

    public FreeBoardAdapter() {
        super(new DiffUtilCallback());
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @NonNull
    @Override
    public FreeBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FreeBoardItemBinding binding = FreeBoardItemBinding.inflate(layoutInflater, parent, false);
        return new FreeBoardViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FreeBoardViewHolder holder, int position) {
        holder.bind(getItem(position));
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
