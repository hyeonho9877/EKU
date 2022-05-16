package com.kyonggi.eku.presenter.board;

import static com.kyonggi.eku.view.board.activity.ActivityBoard.BOARD_FREE;
import static com.kyonggi.eku.view.board.activity.ActivityBoard.BOARD_INFO;
import static com.kyonggi.eku.view.board.activity.ActivityBoard.INIT;
import static com.kyonggi.eku.view.board.activity.ActivityBoard.LOAD_OLD;
import static com.kyonggi.eku.view.board.activity.ActivityBoard.LOAD_RECENT;

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
import com.kyonggi.eku.view.board.OnResponseListeners;
import com.kyonggi.eku.view.board.activity.ActivityBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class InfoBoardPresenter {
    private static final String TAG = "FreeBoardPresenter";
    private final Context context;
    private final OnResponseListeners activity;
    private Handler handler;

    public InfoBoardPresenter(Context context, OnResponseListeners activity) {
        this.context = context;
        this.activity = activity;
    }

    public void getInfoBoardArticles(){
        HashMap<String, Object> request = new HashMap<>();
        request.put("page", 0);
        request.put("lecture_building", 8);
        SendTool.requestForJson("/board/info/lists", request, getHandler(BOARD_INFO, INIT));
    }

    public void getFreeBoardArticles(){
        HashMap<String, Object> request = new HashMap<>();
        request.put("page", 0);
        SendTool.requestForJson("/board/free/lists", request, getHandler(BOARD_FREE, INIT));
    }

    private Handler getHandler(String board, String purpose) {
        return new Handler(Looper.getMainLooper()) {
            public void handleMessage(@NonNull Message msg) {
                int code = msg.what;
                String response = (String) msg.obj;
                Log.d(TAG, "handleMessage: " + code);
                switch (code) {
                    case SendTool.CONNECTION_FAILED:
                        Toast.makeText(context, "네트워크 연결에 실패하였습니다.", Toast.LENGTH_LONG).show();
                        break;
                    case SendTool.HTTP_OK:
                        switch (board){
                            case BOARD_FREE:
                                List<FreeBoardPreview> freeBoardPreviews = SendTool.parseToList(response, FreeBoardPreview[].class);
                                Log.d(TAG, "handleMessage: " + freeBoardPreviews);
                                activity.onSuccess(freeBoardPreviews, purpose);
                                break;
                            case BOARD_INFO:
                                List<InfoBoardPreview> infoBoardPreviews = SendTool.parseToList(response, InfoBoardPreview[].class);
                                Log.d(TAG, "handleMessage: " + infoBoardPreviews);
                                activity.onSuccess(infoBoardPreviews, purpose);
                        }

                        break;
                    case SendTool.HTTP_BAD_REQUEST:
                        Log.d(TAG, "handleMessage: ");
                        break;
                    case SendTool.HTTP_INTERNAL_SERVER_ERROR:
                        Toast.makeText(context, "서버 에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
                    default:
                        Log.e(TAG, "handleMessage: Unknown Error");
                        break;
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

    public void updateInfoBoard(long id) {
        HashMap<String, Object> request = new HashMap<>();
        request.put("id", id);
        SendTool.requestForJson("/board/info/recent", request, getHandler(BOARD_INFO, LOAD_RECENT));
    }

    public void loadMoreInfoArticles(long no) {
        HashMap<String, Object> request = new HashMap<>();
        request.put("id", no);
        SendTool.requestForJson("/board/info/load", request, getHandler(BOARD_INFO, LOAD_OLD));
    }
}
