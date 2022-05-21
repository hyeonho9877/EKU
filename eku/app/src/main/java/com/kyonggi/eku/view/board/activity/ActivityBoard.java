package com.kyonggi.eku.view.board.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.kyonggi.eku.R;
import com.kyonggi.eku.databinding.ActivityBoardBinding;
import com.kyonggi.eku.model.BoardPreview;
import com.kyonggi.eku.presenter.board.InfoBoardPresenter;
import com.kyonggi.eku.utils.callbacks.OnResponseListeners;
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
    private String buildingNumber;

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

    }

    private void setBoard(){
        buildingNumber = getIntent().getStringExtra("buildingNumber");
        currentMode = getIntent().getStringExtra("mode");

        if (currentMode.equals(BOARD_FREE)) {
            binding.textBuildingBoardAnnounce.setText("자유 게시판입니다.");
            binding.buttonFreeBoard.setTextColor(Color.parseColor("#252525"));
            binding.buttonInfoBoard.setTextColor(Color.parseColor("#80252525"));
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout_board, fragmentFreeBoard, BOARD_FREE)
                    .commit();
        } else if (currentMode.equals(BOARD_INFO)) {
            String announce = buildingNumber + " 강의동 공지게시판입니다.";
            binding.textBuildingBoardAnnounce.setText(announce);
            binding.buttonInfoBoard.setTextColor(Color.parseColor("#252525"));
            binding.buttonFreeBoard.setTextColor(Color.parseColor("#80252525"));
            String boardName = buildingNumber + " 강의동";
            binding.textBuildingName.setText(boardName);

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
    public void onSuccess(List<? extends BoardPreview> articles, String purpose) {
        binding.animBoardLoading.setVisibility(View.INVISIBLE);
        binding.frameLayoutBoard.setVisibility(View.VISIBLE);
        if (currentMode.equals(BOARD_INFO)) {
            if (purpose.equals(LOAD_RECENT)) fragmentInfoBoard.updateArticles(presenter.convertToInfoBoard(articles));
            else if(purpose.equals(INIT)) fragmentInfoBoard.listArticles(presenter.convertToInfoBoard(articles));
            else fragmentInfoBoard.loadMoreArticles(presenter.convertToInfoBoard(articles));
        }
        else {
            if (purpose.equals(LOAD_RECENT)) fragmentFreeBoard.updateArticles(presenter.convertToFreeBoard(articles));
            else if(purpose.equals(INIT)) fragmentFreeBoard.listArticles(presenter.convertToFreeBoard(articles));
            else fragmentFreeBoard.loadMoreArticles(presenter.convertToFreeBoard(articles));
        }
    }

    @Override
    public void onFailed() {

    }

    public void updateInfoBoard(long id) {
        presenter.updateInfoBoard(id, buildingNumber);
    }

    public void loadMoreInfoArticles(long no) {
        presenter.loadMoreInfoArticles(no, buildingNumber);
    }

    public void updateFreeBoard(long id) {
        presenter.updateFreeBoard(id);
    }

    public void loadMoreFreeArticles(Long id) {
        presenter.loadMoreFreeArticles(id);
    }

    public static final String BOARD_FREE = "BOARD_FREE";
    public static final String BOARD_INFO = "BOARD_INFO";
    public static final String LOAD_RECENT = "LOAD_RECENT";
    public static final String LOAD_OLD = "LOAD_OLD";
    public static final String INIT = "INIT";
}