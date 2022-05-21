package com.kyonggi.eku.presenter.board;

import static com.kyonggi.eku.view.board.activity.ActivityBoard.BOARD_FREE;
import static com.kyonggi.eku.view.board.activity.ActivityBoard.BOARD_INFO;
import static com.kyonggi.eku.view.board.activity.ActivityBoard.INIT;
import static com.kyonggi.eku.view.board.activity.ActivityBoard.LOAD_OLD;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.kyonggi.eku.model.BoardPreview;
import com.kyonggi.eku.model.FreeBoardPreview;
import com.kyonggi.eku.model.InfoBoardPreview;
import com.kyonggi.eku.utils.SendTool;
import com.kyonggi.eku.utils.callbacks.OnResponseListeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BoardSearchPresenter {
    private static final String TAG = "BoardSearchPresenter";
    private final Context context;
    private final OnResponseListeners listener;

    public BoardSearchPresenter(Context context, OnResponseListeners listener) {
        this.context = context;
        this.listener = listener;
    }

    public void searchInfoBoard(String keyword, String building){
        HashMap<String, Object> param = new HashMap<>();
        param.put("keyword", keyword);
        param.put("lectureBuilding", building);
        SendTool.requestForJson("/board/info/search", param, getHandler(BOARD_INFO, INIT));
    }

    public void searchFreeBoard(String keyword){
        HashMap<String, Object> param = new HashMap<>();
        param.put("keyword", keyword);
        SendTool.requestForJson("/board/free/search", param, getHandler(BOARD_FREE, INIT));
    }

    private Handler getHandler(String board, String purpose) {
        return new Handler(Looper.getMainLooper()) {
            public void handleMessage(@NonNull Message msg) {
                int code = msg.what;
                String response = (String) msg.obj;
                if (code == SendTool.HTTP_OK) {
                    switch (board) {
                        case BOARD_FREE:
                            List<FreeBoardPreview> freeBoardPreviews = SendTool.parseToList(response, FreeBoardPreview[].class);
                            listener.onSuccess(freeBoardPreviews, purpose);
                            break;
                        case BOARD_INFO:
                            List<InfoBoardPreview> infoBoardPreviews = SendTool.parseToList(response, InfoBoardPreview[].class);
                            listener.onSuccess(infoBoardPreviews, purpose);
                    }
                } else {
                    listener.onFailed();
                }
            }
        };
    }

    public List<InfoBoardPreview> convertToInfoBoard(List<? extends BoardPreview> source) {
        ArrayList<InfoBoardPreview> list = new ArrayList<>();
        for (BoardPreview preview : source) {
            list.add((InfoBoardPreview) preview);
        }
        return list;
    }

    public List<FreeBoardPreview> convertToFreeBoard(List<? extends BoardPreview> source) {
        ArrayList<FreeBoardPreview> list = new ArrayList<>();
        for (BoardPreview preview : source) {
            list.add((FreeBoardPreview) preview);
        }
        return list;
    }

    public void loadMoreInfoArticles(long no, String building) {
        HashMap<String, Object> request = new HashMap<>();
        request.put("id", no);
        request.put("lectureBuilding", building);
        SendTool.requestForJson("/board/info/search/load", request, getHandler(BOARD_INFO, LOAD_OLD));
    }
}
