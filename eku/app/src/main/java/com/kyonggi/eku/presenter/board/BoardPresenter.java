package com.kyonggi.eku.presenter.board;

import static com.kyonggi.eku.view.board.activity.ActivityBoard.BOARD_FREE;
import static com.kyonggi.eku.view.board.activity.ActivityBoard.BOARD_INFO;
import static com.kyonggi.eku.view.board.activity.ActivityBoard.INIT;
import static com.kyonggi.eku.view.board.activity.ActivityBoard.LOAD_OLD;
import static com.kyonggi.eku.view.board.activity.ActivityBoard.LOAD_RECENT;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.kyonggi.eku.UserInformation;
import com.kyonggi.eku.WriteAnnounce;
import com.kyonggi.eku.WriteFreeCommunity;
import com.kyonggi.eku.model.FreeBoardPreview;
import com.kyonggi.eku.model.InfoBoardPreview;
import com.kyonggi.eku.utils.SendTool;
import com.kyonggi.eku.utils.callbacks.OnBoardResponseListeners;
import com.kyonggi.eku.view.board.activity.ActivityFreeBoardSearch;
import com.kyonggi.eku.view.board.activity.ActivityInfoBoardSearch;
import com.kyonggi.eku.view.signIn.ActivitySignIn;

import java.util.ArrayList;
import java.util.HashMap;

public class BoardPresenter {
    private static final String TAG = "FreeBoardPresenter";
    private final Context context;
    private final OnBoardResponseListeners listener;
    private UserInformation userInformation;
    private Handler handler;

    public BoardPresenter(Context context, OnBoardResponseListeners listener) {
        this.context = context;
        this.listener = listener;
        userInformation = new UserInformation(context);
    }

    public void getInfoBoardArticles(String buildingNumber) {
        HashMap<String, Object> request = new HashMap<>();
        request.put("lectureBuilding", buildingNumber);
        SendTool.requestForJson("/board/info/lists", request, getHandler(BOARD_INFO, INIT));
    }

    public void getFreeBoardArticles() {
        SendTool.request("/board/free/lists", getHandler(BOARD_FREE, INIT));
    }

    private Handler getHandler(String board, String purpose) {
        return new Handler(Looper.getMainLooper()) {
            public void handleMessage(@NonNull Message msg) {
                int code = msg.what;
                String response = (String) msg.obj;
                if (code == SendTool.HTTP_OK) {
                    switch (board) {
                        case BOARD_FREE:
                            ArrayList<FreeBoardPreview> freeBoardPreviews = new ArrayList<>(SendTool.parseToList(response, FreeBoardPreview[].class));
                            listener.onFreeBoardSuccess(freeBoardPreviews, purpose);
                            break;
                        case BOARD_INFO:
                            ArrayList<InfoBoardPreview> infoBoardPreviews = new ArrayList<>(SendTool.parseToList(response, InfoBoardPreview[].class));
                            listener.onInfoBoardSuccess(infoBoardPreviews, purpose);
                            break;
                    }
                } else {
                    listener.onFailed();
                }
            }
        };
    }

    public void updateInfoBoard(long id, String building) {
        HashMap<String, Object> request = new HashMap<>();
        request.put("id", id);
        request.put("lectureBuilding", building);
        SendTool.requestForJson("/board/info/recent", request, getHandler(BOARD_INFO, LOAD_RECENT));
    }

    public void loadMoreInfoArticles(long no, String building) {
        HashMap<String, Object> request = new HashMap<>();
        request.put("id", no);
        request.put("lectureBuilding", building);
        SendTool.requestForJson("/board/info/load", request, getHandler(BOARD_INFO, LOAD_OLD));
    }

    public void loadMoreFreeArticles(long id) {
        HashMap<String, Object> request = new HashMap<>();
        request.put("id", id);
        SendTool.requestForJson("/board/free/load", request, getHandler(BOARD_FREE, LOAD_OLD));
    }

    public void updateFreeBoard(long id) {
        HashMap<String, Object> request = new HashMap<>();
        request.put("id", id);
        SendTool.requestForJson("/board/free/recent", request, getHandler(BOARD_FREE, LOAD_RECENT));
    }

    public boolean isAuthenticated() {
        return userInformation.fromPhoneVerify(context);
    }

    public void signIn() {
        Intent intent = new Intent(context, ActivitySignIn.class);
        context.startActivity(intent);
    }

    public void writeInfoBoard() {
        if (isAuthenticated()){
            Intent intent = new Intent(context, WriteAnnounce.class);
            context.startActivity(intent);
        } else {
            Intent intent = new Intent(context, ActivitySignIn.class);
            context.startActivity(intent);
        }

    }

    public void writeFreeBoard() {
        if (isAuthenticated()){
            Intent intent = new Intent(context, WriteFreeCommunity.class);
            context.startActivity(intent);
        } else {
            Intent intent = new Intent(context, ActivitySignIn.class);
            context.startActivity(intent);
        }
    }

    public void search(String currentMode, String buildingNumber) {
        if (currentMode.equals(BOARD_FREE)) {
            Intent intent = new Intent(context, ActivityFreeBoardSearch.class);
            intent.putExtra("buildingNumber", buildingNumber);
            context.startActivity(intent);
        } else {
            Intent intent = new Intent(context, ActivityInfoBoardSearch.class);
            intent.putExtra("buildingNumber", buildingNumber);
            context.startActivity(intent);
        }
    }

    public void searchInfoBoard(String keyword, String building) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("keyword", keyword);
        param.put("lectureBuilding", building);
        SendTool.requestForJson("/board/info/search", param, getHandler(BOARD_INFO, INIT));
    }

    public void searchFreeBoard(String keyword) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("keyword", keyword);
        SendTool.requestForJson("/board/free/search", param, getHandler(BOARD_FREE, INIT));
    }

    public void loadMoreInfoArticles(long no, String building, String keyword) {
        HashMap<String, Object> request = new HashMap<>();
        request.put("id", no);
        request.put("keyword", keyword);
        request.put("lectureBuilding", building);
        SendTool.requestForJson("/board/info/search/load", request, getHandler(BOARD_INFO, LOAD_OLD));
    }

    public void loadMoreFreeArticles(Long id, String keyword) {
        HashMap<String, Object> request = new HashMap<>();
        request.put("id", id);
        request.put("keyword", keyword);
        SendTool.requestForJson("/board/free/search/load", request, getHandler(BOARD_FREE, LOAD_OLD));
    }

    public void loadAllInfoArticles(){
        SendTool.request("/board/info/all", getHandler(BOARD_INFO, INIT));
    }

    public void loadMoreAllInfo(long id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        SendTool.requestForJson("/board/info/all/load", params, getHandler(BOARD_INFO, LOAD_OLD));
    }
}
