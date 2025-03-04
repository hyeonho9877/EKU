package com.kyonggi.eku.view.board.activity;

import static com.kyonggi.eku.view.board.activity.ActivityBoard.INIT;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kyonggi.eku.databinding.ActivityBoardSearchBinding;
import com.kyonggi.eku.model.FreeBoardPreview;
import com.kyonggi.eku.model.InfoBoardPreview;
import com.kyonggi.eku.presenter.board.BoardPresenter;
import com.kyonggi.eku.utils.adapters.InfoBoardAdapter;
import com.kyonggi.eku.utils.callbacks.OnBoardResponseListeners;

import java.util.List;

public class ActivityInfoBoardSearch extends AppCompatActivity implements OnBoardResponseListeners {
    private static final String TAG = "ActivityInfoBoardSearch";
    private ActivityBoardSearchBinding binding;
    private BoardPresenter presenter;
    private InfoBoardAdapter adapter;
    private boolean isLoading = false;
    private String buildingNumber;
    private RecyclerView.OnScrollListener scrollListener;
    private String keyword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new BoardPresenter(this, this);
        buildingNumber = getIntent().getStringExtra("buildingNumber");
        initListeners();
    }

    private void initListeners() {
        binding.editTextSearch.setOnKeyListener((view, i, keyEvent) -> {
            keyword = binding.editTextSearch.getText().toString();
            if (keyword.length() != 0 && i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                binding.animBoardSearchLoading.setVisibility(View.VISIBLE);
                presenter.searchInfoBoard(keyword, buildingNumber);
            }
            return false;
        });
    }

    @Override
    public void onInfoBoardSuccess(List<InfoBoardPreview> articles, String purpose) {
        if (purpose.equals(INIT)){
            if(articles.size() >= 20) addScrollListeners();
            adapter = new InfoBoardAdapter(articles, this);
            binding.animBoardSearchLoading.setVisibility(View.INVISIBLE);
            binding.recyclerViewSearch.setVisibility(View.VISIBLE);
            binding.recyclerViewSearch.setAdapter(adapter);
            adapter.notifyItemRangeInserted(0, articles.size());
        } else {
            List<InfoBoardPreview> currentList = adapter.getCurrentList();
            currentList.remove(currentList.size() - 1);
            adapter.notifyItemRemoved(currentList.size()-1);
            isLoading = false;
            new Handler().postDelayed(()->{
                if (adapter.insertFromTail(articles)) {
                    adapter.notifyItemRangeInserted(adapter.getCurrentList().size(), articles.size());
                    binding.recyclerViewSearch.removeOnScrollListener(scrollListener);
                } else {
                    adapter.notifyItemRangeInserted(adapter.getCurrentList().size(), articles.size());
                }
            }, 200);
        }
    }

    @Override
    public void onFreeBoardSuccess(List<FreeBoardPreview> articles, String purpose) {
        Toast.makeText(this, "Something went wrong..", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onFailed() {
        Toast.makeText(this, "게시글 검색에 실패하였습니다.", Toast.LENGTH_SHORT).show();
    }

    private void addScrollListeners(){
        scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int itemCount = recyclerView.getAdapter().getItemCount();
                if (!isLoading) {
                    List<InfoBoardPreview> currentList = adapter.getCurrentList();
                    if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == currentList.size() - 1) {
                        currentList.add(null);
                        binding.recyclerViewSearch.post(() -> adapter.notifyItemInserted(currentList.size() - 1));
                        presenter.loadMoreInfoArticles(currentList.get(itemCount - 1).getId(), buildingNumber, keyword);
                        isLoading = true;
                    }
                }
            }
        };

        binding.recyclerViewSearch.addOnScrollListener(scrollListener);
    }
}
