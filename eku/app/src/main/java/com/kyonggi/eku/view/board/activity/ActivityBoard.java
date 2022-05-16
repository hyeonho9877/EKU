package com.kyonggi.eku.view.board.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kyonggi.eku.R;
import com.kyonggi.eku.databinding.ActivityBoardBinding;
import com.kyonggi.eku.model.BoardPreview;
import com.kyonggi.eku.model.InfoBoardPreview;
import com.kyonggi.eku.presenter.board.InfoBoardPresenter;
import com.kyonggi.eku.view.board.OnResponseListeners;
import com.kyonggi.eku.view.board.fragment.FragmentFreeBoard;
import com.kyonggi.eku.view.board.fragment.FragmentInfoBoard;

import java.util.List;

public class ActivityBoard extends AppCompatActivity implements OnResponseListeners {

    private static final String TAG = "ActivityBoard";
    private ActivityBoardBinding binding;
    private InfoBoardPresenter presenter;
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private String currentMode;
    FragmentFreeBoard fragmentFreeBoard = new FragmentFreeBoard();
    FragmentInfoBoard fragmentInfoBoard = new FragmentInfoBoard();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        binding = ActivityBoardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setBoard();

        presenter = new InfoBoardPresenter(this, this);
        initListeners();
    }

    private void initListeners() {
        binding.buttonFreeBoard.setOnClickListener(v->{
            if (currentMode.equals(BOARD_INFO)) switchBoard();
        });
        binding.buttonInfoBoard.setOnClickListener(v->{
            if(currentMode.equals(BOARD_FREE)) switchBoard();
        });
        binding.swipeLayoutBoard.setOnRefreshListener(() -> {
            binding.swipeLayoutBoard.setRefreshing(true);
            if (currentMode.equals(BOARD_FREE)) presenter.getFreeBoardArticles();
            else presenter.getInfoBoardArticles();
            binding.swipeLayoutBoard.setRefreshing(false);
        });
    }

    private void setBoard(){
        currentMode = getIntent().getStringExtra("mode");

        if (currentMode.equals(BOARD_FREE)) {
            binding.textBuildingBoardAnnounce.setText("자유 게시판입니다.");
            binding.buttonFreeBoard.setTextColor(Color.parseColor("#252525"));
            binding.buttonInfoBoard.setTextColor(Color.parseColor("#80252525"));
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout_board, fragmentFreeBoard, BOARD_FREE)
                    .commit();
        } else if (currentMode.equals(BOARD_INFO)) {
            binding.textBuildingBoardAnnounce.setText("n 강의동 공지 게시판입니다.");
            binding.buttonInfoBoard.setTextColor(Color.parseColor("#252525"));
            binding.buttonFreeBoard.setTextColor(Color.parseColor("#80252525"));

            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout_board, fragmentInfoBoard, BOARD_INFO)
                    .commit();
        }


    }

    private void switchBoard(){
        Log.d(TAG, "switchBoard: "+currentMode);
        if (currentMode.equals(BOARD_FREE)) {
            binding.textBuildingBoardAnnounce.setText("n 강의동 공지 게시판입니다.");
            binding.buttonFreeBoard.setTextColor(Color.parseColor("#80252525"));
            binding.buttonInfoBoard.setTextColor(Color.parseColor("#252525"));
            currentMode = BOARD_INFO;

            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout_board, fragmentInfoBoard, BOARD_INFO)
                    .commit();

        } else if (currentMode.equals(BOARD_INFO)) {
            binding.textBuildingBoardAnnounce.setText("자유 게시판입니다.");
            binding.buttonInfoBoard.setTextColor(Color.parseColor("#80252525"));
            binding.buttonFreeBoard.setTextColor(Color.parseColor("#252525"));
            currentMode = BOARD_FREE;

            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout_board, fragmentFreeBoard, BOARD_FREE)
                    .commit();
        }
    }

    public void getInfoBoardArticles(){
        presenter.getInfoBoardArticles();
    }

    public void getFreeBoardArticles(){
        presenter.getFreeBoardArticles();
    }

    @Override
    public void onSuccess(List<? extends BoardPreview> articles) {
        if (currentMode.equals(BOARD_INFO)) fragmentInfoBoard.listArticles(presenter.convertToInfoBoard(articles));
        else fragmentFreeBoard.listArticles(presenter.convertToFreeBoard(articles));
    }

    @Override
    public void onFailed() {

    }

    public static final String BOARD_FREE = "BOARD_FREE";
    public static final String BOARD_INFO = "BOARD_INFO";
}