package com.kyonggi.eku.presenter.board;

import static com.kyonggi.eku.view.board.activity.ActivityBoard.BOARD_FREE;
import static com.kyonggi.eku.view.board.activity.ActivityBoard.BOARD_INFO;
import static com.kyonggi.eku.view.board.activity.ActivityBoard.INIT;
import static com.kyonggi.eku.view.board.activity.ActivityBoard.LOAD_OLD;
import static com.kyonggi.eku.view.board.activity.ActivityBoard.LOAD_RECENT;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartReader;
import okhttp3.ResponseBody;

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
        SendTool.requestForJson("/board/info/image.list", request, getArticleHandler(BOARD_INFO, INIT));
    }

    public void getFreeBoardArticles() {
        SendTool.request("/board/free/lists", getArticleHandler(BOARD_FREE, INIT));
    }

    private Handler getArticleHandler(String board, String purpose) {
        return new Handler(Looper.getMainLooper()) {
            public void handleMessage(@NonNull Message msg) {
                int code = msg.what;
                ResponseBody response = (ResponseBody) msg.obj;
                if (code == SendTool.HTTP_OK) {
                    switch (board) {
                        case BOARD_FREE:
                            try {
                                ArrayList<FreeBoardPreview> freeBoardPreviews = new ArrayList<>(SendTool.parseToList(response.string(), FreeBoardPreview[].class));
                                listener.onFreeBoardSuccess(freeBoardPreviews, purpose);
                            } catch (IOException e) {
                                listener.onFreeBoardSuccess(Collections.emptyList(), purpose);
                            }
                            break;
                        case BOARD_INFO:
                            try {
                                MultipartReader multipartReader = new MultipartReader(response);
                                MultipartReader.Part articleResponse = multipartReader.nextPart();
                                InputStream articleInputStream = articleResponse.body().inputStream();
                                InputStreamReader articleReader = new InputStreamReader(articleInputStream, StandardCharsets.UTF_8);
                                JsonElement articles = JsonParser.parseReader(articleReader);
                                articleReader.close();
                                articleInputStream.close();
                                List<InfoBoardPreview> infoBoardPreviews = new ArrayList<>(SendTool.parseToList(articles.toString(), InfoBoardPreview[].class));

                                MultipartReader.Part imageResponse = multipartReader.nextPart();
                                InputStreamReader imageReader = new InputStreamReader(imageResponse.body().inputStream());
                                JsonObject images = JsonParser.parseReader(imageReader).getAsJsonObject();
                                for (String key : images.keySet()) {
                                    JsonArray imageList = images.get(key).getAsJsonArray();
                                    if (!imageList.isEmpty()) {
                                        JsonElement rawImage = imageList.get(0);
                                        byte[] decodedImage = Base64.decode(rawImage.getAsString(), Base64.DEFAULT);
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
                                        infoBoardPreviews.stream().filter(preview -> preview.getId().equals(Long.parseLong(key))).forEach(article -> article.setRepresentativeImage(bitmap));
                                    }
                                }
                                imageReader.close();
                                listener.onInfoBoardSuccess(infoBoardPreviews, purpose);
                            } catch (IOException e) {
                                Log.d(TAG, "handleMessage: " + e.getMessage());
                            }
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
        SendTool.requestForJson("/board/info/recent", request, getArticleHandler(BOARD_INFO, LOAD_RECENT));
    }

    public void loadMoreInfoArticles(long no, String building) {
        HashMap<String, Object> request = new HashMap<>();
        request.put("id", no);
        request.put("lectureBuilding", building);
        SendTool.requestForJson("/board/info/load", request, getArticleHandler(BOARD_INFO, LOAD_OLD));
    }

    public void loadMoreFreeArticles(long id) {
        HashMap<String, Object> request = new HashMap<>();
        request.put("id", id);
        SendTool.requestForJson("/board/free/load", request, getArticleHandler(BOARD_FREE, LOAD_OLD));
    }

    public void updateFreeBoard(long id) {
        HashMap<String, Object> request = new HashMap<>();
        request.put("id", id);
        SendTool.requestForJson("/board/free/recent", request, getArticleHandler(BOARD_FREE, LOAD_RECENT));
    }

    public boolean isAuthenticated() {
        return userInformation.fromPhoneVerify(context);
    }

    public void signIn() {
        Intent intent = new Intent(context, ActivitySignIn.class);
        context.startActivity(intent);
    }

    public void writeInfoBoard() {
        if (isAuthenticated()) {
            Intent intent = new Intent(context, WriteAnnounce.class);
            context.startActivity(intent);
        } else {
            Intent intent = new Intent(context, ActivitySignIn.class);
            context.startActivity(intent);
        }

    }

    public void writeFreeBoard() {
        if (isAuthenticated()) {
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
        SendTool.requestForJson("/board/info/search", param, getArticleHandler(BOARD_INFO, INIT));
    }

    public void searchFreeBoard(String keyword) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("keyword", keyword);
        SendTool.requestForJson("/board/free/search", param, getArticleHandler(BOARD_FREE, INIT));
    }

    public void loadMoreInfoArticles(long no, String building, String keyword) {
        HashMap<String, Object> request = new HashMap<>();
        request.put("id", no);
        request.put("keyword", keyword);
        request.put("lectureBuilding", building);
        SendTool.requestForJson("/board/info/search/load", request, getArticleHandler(BOARD_INFO, LOAD_OLD));
    }

    public void loadMoreFreeArticles(Long id, String keyword) {
        HashMap<String, Object> request = new HashMap<>();
        request.put("id", id);
        request.put("keyword", keyword);
        SendTool.requestForJson("/board/free/search/load", request, getArticleHandler(BOARD_FREE, LOAD_OLD));
    }

    public void loadAllInfoArticles() {
        SendTool.request("/board/info/all", getArticleHandler(BOARD_INFO, INIT));
    }

    public void loadMoreAllInfo(long id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        SendTool.requestForJson("/board/info/all/load", params, getArticleHandler(BOARD_INFO, LOAD_OLD));
    }
}
