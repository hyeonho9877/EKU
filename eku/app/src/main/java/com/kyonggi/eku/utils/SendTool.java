package com.kyonggi.eku.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

public final class SendTool {
    private static final String TAG = "SendTool";
    private static final String baseUrl = "https://eku.kro.kr";
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();

    public static void request(String url, Handler handler) throws NullPointerException {

        Request request = new Request.Builder()
                .url(baseUrl + url)
                .post(RequestBody.create(new byte[0]))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                handler.sendMessage(Message.obtain(handler, CONNECTION_FAILED));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                handler.sendMessage(Message.obtain(handler, response.code(), response.body().string()));
            }
        });
    }

    public static void requestForPost(int bodyType, String url, HashMap<String, Object> params, Handler handler) throws IOException, NullPointerException {

        if (bodyType == APPLICATION_JSON) {
            requestForJson(url, params, handler);
            return;
        }

        FormBody.Builder builder = new FormBody.Builder();

        if (params != null) {
            for (String key : params.keySet()) {
                String value = (String) params.get(key);
                builder.add(key, value);
            }
        }

        FormBody body = builder.build();

        Request request = new Request.Builder()
                .url(baseUrl + url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                handler.sendMessage(Message.obtain(handler, CONNECTION_FAILED));

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                handler.sendMessage(Message.obtain(handler, response.code(), response.body().string()));
            }
        });
    }

    public static void requestForJson(String url, HashMap<String, Object> params, Handler handler) throws NullPointerException {

        String jsonObject = gson.toJson(params);

        MediaType JSON = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(jsonObject, JSON);

        Request request = new Request.Builder()
                .url(baseUrl + url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                handler.sendMessage(Message.obtain(handler, CONNECTION_FAILED));

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                handler.sendMessage(Message.obtain(handler, response.code(), response.body().string()));
            }
        });
    }

    public static void requestForJson2(String url, Object obj, Handler handler) throws NullPointerException {

        String jsonObject = gson.toJson(obj);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(jsonObject, JSON);

        Request request = new Request.Builder()
                .url(baseUrl + url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                handler.sendMessage(Message.obtain(handler, CONNECTION_FAILED));

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                handler.sendMessage(Message.obtain(handler, response.code(), response.body().string()));
            }
        });
    }

    public static void requestForMultiPart(String url, Uri uri, ContentResolver contentResolver, Handler handler) {

        MultipartBody.Part part = getRequestBody(uri, "img", contentResolver);
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart(part)
                .build();

        Request request = new Request.Builder()
                .addHeader("Accept-Language", "ko-KR,ko;q=0.9,ja-JP;q=0.8,ja;q=0.7,en-US;q=0.6,en;q=0.5")
                .addHeader("Accept-Encoding", "gzip")
                .url(baseUrl + url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                handler.sendMessage(Message.obtain(handler, CONNECTION_FAILED));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                handler.sendMessage(Message.obtain(handler, response.code(), response.body().string()));
            }
        });
    }

    public static MultipartBody.Part getRequestBody(final Uri uri, String name, final ContentResolver contentResolver) {
        final Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null) {
            if(cursor.moveToNext()) {
                @SuppressLint("Range") final String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                RequestBody requestBody = new RequestBody() {
                    @Override
                    public MediaType contentType() {
                        return MediaType.parse(contentResolver.getType(uri));
                    }

                    @Override
                    public void writeTo(BufferedSink sink) {
                        try {
                            sink.writeAll(Okio.source(contentResolver.openInputStream(uri)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                cursor.close();
                return MultipartBody.Part.createFormData(name, displayName, requestBody);
            } else {
                cursor.close();
                return null;
            }
        } else {
            return null;
        }
    }


    public static <T> T parseToSingleEntity(String targetText, Class<T> t) {
        return gson.fromJson(targetText, t);
    }

    public static <T> List<T> parseToList(String targetText, Class<T[]> t) {
        return Arrays.asList(gson.fromJson(targetText, t));
    }

    public static List<String> parseToString(String targetText){
        Type type = new TypeToken<List<String>>(){}.getType();
        return gson.fromJson(targetText, type);
    }

    public static void requestForTimeTable(String url, JSONArray params, Handler handler) throws NullPointerException, JSONException {
        JSONObject jO = new JSONObject();
        jO.put("list",params);
        String jsonObject = jO.toString();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(jsonObject, JSON);


        Request request = new Request.Builder()
                .url(baseUrl+url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                handler.sendMessage(Message.obtain(handler, CONNECTION_FAILED));

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                handler.sendMessage(Message.obtain(handler, response.code(), response.body().string()));
            }
        });
    }
    public static void downForTimeTable(String url, JSONObject params, Handler handler) throws NullPointerException, JSONException {


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(String.valueOf(params), JSON);
        Request request = new Request.Builder()
                .url(baseUrl+url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                handler.sendMessage(Message.obtain(handler, CONNECTION_FAILED));

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                handler.sendMessage(Message.obtain(handler, response.code(), response.body().string()));
            }
        });
    }

    public static final int CONNECTION_FAILED = -1;
    public static final int HTTP_OK = 200;
    public static final int HTTP_INTERNAL_SERVER_ERROR = 500;
    public static final int HTTP_BAD_REQUEST = 400;
    public static final int APPLICATION_JSON = 0x1;
    public static final int POST_PARAM = 0x2;
}
