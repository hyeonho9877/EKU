package com.kyonggi.eku.view.board.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kyonggi.eku.databinding.ActivityBoardSearchBinding;
import com.kyonggi.eku.model.BoardPreview;
import com.kyonggi.eku.presenter.board.BoardSearchPresenter;
import com.kyonggi.eku.utils.callbacks.OnResponseListeners;

import java.util.List;

public class ActivityFreeBoardSearch extends AppCompatActivity implements OnResponseListeners {
    private static final String TAG = "ActivityFreeBoardSearch";
    private ActivityBoardSearchBinding binding;
    private BoardSearchPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new BoardSearchPresenter(this, this);
        initListeners();
    }

    private void initListeners() {
        binding.editTextSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String keyword = binding.editTextSearch.getText().toString();
                if (keyword.length() != 0 && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    Log.d(TAG, "onKey: "+keyword);
                }
                return true;
            }
        });
    }


    @Override
    public void onSuccess(List<? extends BoardPreview> articles, String purpose) {

    }

    @Override
    public void onFailed() {
        Toast.makeText(this, "게시글 조회에 실패하였습니다.", Toast.LENGTH_SHORT).show();
    }
}
