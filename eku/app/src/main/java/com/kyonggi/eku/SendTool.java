package com.kyonggi.eku;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SendTool {

    /*
     *
     * 제목
     * 샌드 툴
     * 기능
     * okhttp3 Request=> 웹에서 처리한거를=> 당신의 Handler로 보냅니다. 끝
     * */
    OkHttpClient client = new OkHttpClient();
    JSONObject jsonInput = new JSONObject();
    Object send;
    Handler mhandler;
    SendTool(Handler handler){
        mhandler=handler;

    }


    /*
        SendTool 준비물
        사용할 Activity에
        1. SendTool 객체를 선언하세요
        SendTool sendTool = new SendTool(Handler);

        2. Handler 객체를 선언하세요.
        Handler handler = new Handler(){
                    public void handleMessage(@NonNull Message msg){
                        switch(msg.what){
                            case 0:
                                *** responseResult값에 당신이 request한 값에 따라
                                서버에서 전송한 body값이 전송됩니다!!***
                                String responseResult=(String)msg.obj;
                                ****여기에 당신이 사용할 textView나 기타 UI변경 등을 넣어주세요!****
                                ex) textView.setText(responseResult);
                        }
                    }
        };

        3. sendTool 전송할 파라미터를 해쉬맵을 선언해서 아래와 같이 넣으세요 JSON을 만들기 위해서임
        HashMap<String,String> temp = new HashMap<>();
        temp.put("email","yas5@kyonggi.ac.kr");
        temp.put("password","dkssud");

        4.아래 다음과 같이 onClick이나 onCreate등 아무데나 request요청할 곳에다가 이 코드를 쓰시면
        전송되고 Handler 객체에 쓰신대로 작동됩니다!
        sendTool.request("http://115.85.182.126:8080/signIn","POST",temp);


        간단히 작동법을 알려드리면
        1. 당신이 보내고 싶은 내용을 해쉬맵에 넣는다.(3)
        2. 핸들러 객체를 선언한 뒤에 responseResult값을 처리할 데이터를
        responseResult아래에 넣는다 (2)
        3. sendTool객체를 선언한다.
        4. sendTool.request를 쓰시면 쓴대로 날아갑니다.
        5. 그러고나서 요청이 이제 Handler객체의 responseResult값으로 들어오니까 그다음에는 그값으로 알아서 하세요

        모르겠다? -> cameratest.activity와 activity_test_json을 참고해주세요

    *
    */
    public void requestJSON(String detailURL, String getPost, HashMap<String,String> contents) throws IOException, JSONException {
        final Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                postServerJSON(detailURL, getPost, contents);
            }
        });
        th.start();
        try{
            th.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }






    public void request(String detailURL, String getPost, HashMap<String,String> contents) throws IOException, JSONException {
        final Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                postServer(detailURL, getPost, contents);
            }
        });
        th.start();
        try{
            th.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
     *  매개변수
     *  URL --> 서버가 써다랄고 하는곳
     *  Post --> get post 백에서 요구하는 사항
     *  contents --> 학번, 고유번호 key value인 HashMap
     *  lecture --> 강의, ArrayList
     */
    public void requestLectureTest(String detailURL, String getPost, HashMap<String,String> contents, HashMap<String, ArrayList<String>> lecture) throws IOException, JSONException {
        final Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                postServerLecture(detailURL, getPost, contents,lecture);
            }
        });
        th.start();
        try{
            th.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void postServerLecture(String detailURL, String getPost, HashMap<String,String> contents,HashMap<String, ArrayList<String>> lecture){
        try {
            for (String key : contents.keySet()) {
                jsonInput.put(key, contents.get(key));
            }
            for(String key : lecture.keySet()){
                jsonInput.put(key,lecture.get(key));
            }
            Request request = new Request.Builder()
                    .addHeader("key", "Content-Type")
                    .addHeader("value", "application/json")
                    .addHeader("description", "")
                    .url(detailURL)
                    .post(RequestBody.create(MediaType.parse("application/json"), jsonInput.toString()))
                    .build();

            Response response = client.newCall(request).execute();

            Log.i("request : ", request.toString());
            //후에 JSON객체로 변환가능
            String message = response.body().string();

            mhandler.sendMessage(Message.obtain(mhandler,0,message));


        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void requestDoodle(String detailURL, String getPost, String minor) throws IOException, JSONException {
        final Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                postDoodle(detailURL, getPost, minor);
            }
        });
        th.start();
        try{
            th.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void postDoodle(String detailURL, String getPost, String minor){
        Request request =null;
        try {
                RequestBody formBody = new FormBody.Builder()
                        .add("minor",minor)
                        .build();

                request = new Request.Builder()
                        .url(detailURL)
                        .post(formBody)
                        .build();

            Response response = client.newCall(request).execute();
            Log.i("request : ", request.toString());
            //후에 JSON객체로 변환가능
            String message = response.body().string();
            mhandler.sendMessage(Message.obtain(mhandler,0,message));


        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    private void postServer(String detailURL, String getPost, HashMap<String,String> contents){
        Request request =null;
        try {
            if(getPost.equals("get")||getPost.equals("GET"))
            {
                int i=0;
                String getURL = detailURL+"?";
                for (String key : contents.keySet()) {
                    if(i!=0){
                        getURL=getURL+"&";
                        i++;
                    }
                    getURL=getURL+key+"=";
                    getURL=getURL+(String)contents.get(key);
                }
                request = new Request.Builder()
                        .addHeader("key", "Content-Type")
                        .addHeader("value", "application/json")
                        .addHeader("description", "")
                        .url(getURL)
                        .get()
                        .build();
            }
            else{
                for (String key : contents.keySet()) {
                    jsonInput.put(key, contents.get(key));
                }
                request = new Request.Builder()
                        .addHeader("key", "Content-Type")
                        .addHeader("value", "application/json")
                        .addHeader("description", "")
                        .url(detailURL)
                        .post(RequestBody.create(MediaType.parse("application/json"), jsonInput.toString()))
                        .build();
            }

            Response response = client.newCall(request).execute();
            Log.i("request : ", request.toString());
            //후에 JSON객체로 변환가능
            String message = response.body().string();
            mhandler.sendMessage(Message.obtain(mhandler,0,message));


        } catch (Exception e) {
            e.printStackTrace();

        }

    }



    private void postServerJSON(String detailURL, String getPost, HashMap<String,String> contents){
        try {
            for (String key : contents.keySet()) {
                jsonInput.put(key, contents.get(key));
            }
            Request request = new Request.Builder()
                    .addHeader("key", "Content-Type")
                    .addHeader("value", "application/json")
                    .addHeader("description", "")
                    .url(detailURL)
                    .post(RequestBody.create(MediaType.parse("application/json"), jsonInput.toString()))
                    .build();

            Response response = client.newCall(request).execute();

            Log.i("request : ", request.toString());
            //후에 JSON객체로 변환가능
            JSONObject message = new JSONObject(response.body().string());
            mhandler.sendMessage(Message.obtain(mhandler,0,message));
        } catch (Exception e) {
            e.printStackTrace();

        }

    }


   /* private void postServer(String detailURL, String getPost, HashMap<String,String> contents){
        try {
            for (String key : contents.keySet()) {
                jsonInput.put(key, contents.get(key));
            }
            Request request = new Request.Builder()
                    .addHeader("key", "Content-Type")
                    .addHeader("value", "application/json")
                    .addHeader("description", "")
                    .url(detailURL)
                    .post(RequestBody.create(MediaType.parse("application/json"), jsonInput.toString()))
                    .build();

            Response response = client.newCall(request).execute();

            Log.i("request : ", request.toString());
            //후에 JSON객체로 변환가능
            String message = response.body().string();

            mhandler.sendMessage(Message.obtain(mhandler,0,message));


        } catch (Exception e) {
            e.printStackTrace();

        }

    }



    private void postServerJSON(String detailURL, String getPost, HashMap<String,String> contents){
        try {
            for (String key : contents.keySet()) {
                jsonInput.put(key, contents.get(key));
            }
            Request request = new Request.Builder()
                    .addHeader("key", "Content-Type")
                    .addHeader("value", "application/json")
                    .addHeader("description", "")
                    .url(detailURL)
                    .post(RequestBody.create(MediaType.parse("application/json"), jsonInput.toString()))
                    .build();

            Response response = client.newCall(request).execute();

            Log.i("request : ", request.toString());
            //후에 JSON객체로 변환가능
            JSONObject message = new JSONObject(response.body().string());
            mhandler.sendMessage(Message.obtain(mhandler,0,message));
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
*/

}













