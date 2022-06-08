package com.kyonggi.eku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kyonggi.eku.UserInformation;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kyonggi.eku.presenter.board.BoardPresenter;
import com.kyonggi.eku.utils.adapters.FreeCommunityCommentAdapter;
import com.kyonggi.eku.CommentReloadTool;
import com.kyonggi.eku.FreeCommunityCommentItem;
import com.kyonggi.eku.PreferenceManagers;
import com.kyonggi.eku.R;
import com.kyonggi.eku.utils.SendTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DetailFreeCommunity extends AppCompatActivity {

    private static final String TAG = "DetailFreeCommunity";
    /**
     * 자유게시판 세부화면
     */

    long backKeyPressedTime;

    private final int MODIFY_MODE = 10;
    private final int VIEW_MODE = 11;

    private final int DETAIL_BOARD_REQUEST = 0;
    private final int DETAIL_COMMENT_WRITE_REQUEST = 1;
    private final int DETAIL_BOARD_MODIFY_REQUEST = 2;
    private final int DETAIL_BOARD_DELETE_REQUEST = 3;

    private int mode = VIEW_MODE;

    ArrayList<FreeCommunityCommentItem> arrayList;
    FreeCommunityCommentAdapter freeCommunityCommentAdapter;
    TextView tv_title;
    TextView tv_content;
    TextView tv_time;
    TextView tv_writer;

    EditText et_title;
    EditText et_content;
    Button btn_edit_submit;
    Button btn_edit_cancle;
    LinearLayout edit_layout;

    EditText et_comment;
    ImageButton btn_comment_submit;

    Toolbar toolbar;
    ListView listView;
    String id_text = "201713924";

    String writer_id = "";
    String board_id = "";
    UserInformation userInformation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_free_community);
        Log.d(TAG, "onCreate: " + getIntent().getStringExtra("id"));

        listView = (ListView) findViewById(R.id.detail_Free_comment_listview);
        toolbar = (Toolbar) findViewById(R.id.detail_Free_toolbar);
        tv_title = (TextView) findViewById(R.id.detail_Free_title);
        tv_content = (TextView) findViewById(R.id.detail_Free_content);
        tv_time = (TextView) findViewById(R.id.detail_Free_time);
        tv_writer = (TextView) findViewById(R.id.detail_Free_writer);
        et_title = (EditText) findViewById(R.id.detail_Free_edit_title);
        et_content = (EditText) findViewById(R.id.detail_Free_edit_content);

        btn_edit_submit = (Button) findViewById(R.id.detail_Free_edit_btn_submit);
        btn_edit_cancle = (Button) findViewById(R.id.detail_Free_edit_btn_cancle);
        edit_layout = (LinearLayout) findViewById(R.id.detail_Free_edit_button_layout);

        et_comment = (EditText) findViewById(R.id.detail_Free_Write_Comment);
        btn_comment_submit = (ImageButton) findViewById(R.id.detail_Free_Write_Comment_button);

        Intent intent = getIntent();
        board_id = intent.getStringExtra("id");
        userInformation = new UserInformation();
        id_text = userInformation.fromPhoneStudentNo(getApplicationContext());


        arrayList = new ArrayList<FreeCommunityCommentItem>();
        CommentReloadTool commentReloadTool = new CommentReloadTool() {
            @Override
            public void reload() {
                getBoardDetail(board_id, DETAIL_BOARD_REQUEST);
            }
        };

        freeCommunityCommentAdapter = new FreeCommunityCommentAdapter(arrayList, commentReloadTool, board_id);
        listView.setAdapter(freeCommunityCommentAdapter);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getBoardDetail(board_id, DETAIL_BOARD_REQUEST);


        btn_comment_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_comment = et_comment.getText().toString();
                if (str_comment.length() <= 0) {
                    Toast.makeText(DetailFreeCommunity.this, "글자를 0자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();

                }
                getBoardDetail(board_id, str_comment, DETAIL_COMMENT_WRITE_REQUEST);
            }
        });

        btn_edit_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode == MODIFY_MODE) {
                    mode = VIEW_MODE;


                    tv_title.setVisibility(View.VISIBLE);
                    tv_content.setVisibility(View.VISIBLE);
                    et_title.setVisibility(View.GONE);
                    et_content.setVisibility(View.GONE);
                    edit_layout.setVisibility(View.GONE);
                    getBoardDetail(board_id, DETAIL_BOARD_MODIFY_REQUEST);
                }
            }
        });

        btn_edit_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_title.setVisibility(View.VISIBLE);
                tv_content.setVisibility(View.VISIBLE);
                et_title.setVisibility(View.GONE);
                et_content.setVisibility(View.GONE);
                edit_layout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail_free_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (id_text.trim().equals(writer_id.trim())) {
            switch (item.getItemId()) {
                case R.id.detail_Free_menu_modify:
                    Toast.makeText(getApplicationContext(), "수정", Toast.LENGTH_LONG).show();
                    if (mode == VIEW_MODE) {
                        mode = MODIFY_MODE;

                        et_title.setText(tv_title.getText().toString());
                        et_content.setText(tv_content.getText().toString());

                        tv_title.setVisibility(View.INVISIBLE);
                        tv_content.setVisibility(View.INVISIBLE);
                        et_title.setVisibility(View.VISIBLE);
                        et_content.setVisibility(View.VISIBLE);
                        edit_layout.setVisibility(View.VISIBLE);
                    }

                    return true;
                case R.id.detail_Free_menu_delete:
                    dialog_delete();
                    return true;
                default:

                    return super.onOptionsItemSelected(item);
            }
        } else {
            dialog_not_access();
            return super.onOptionsItemSelected(item);
        }
    }

    public void addItem(String id, String writer, String comment, String time) {
        FreeCommunityCommentItem freeCommunityCommentItem = new FreeCommunityCommentItem(id, writer, comment, time);
        arrayList.add(0, freeCommunityCommentItem);
        freeCommunityCommentAdapter.notifyDataSetChanged();
    }

    public void getBoardDetail(String id, int mode) {
        getBoardDetail(id, null, mode);
    }

    public void getBoardDetail(String id, String comment, int mode) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonBodyObj = new JSONObject();
        String url = "";
        if (mode == DETAIL_BOARD_REQUEST) {
            try {
                jsonBodyObj.put("id", Integer.parseInt(id));
                Log.d(TAG, "getBoardDetail: " + id);
                url = "https://www.eku.kro.kr/board/free/view";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (mode == DETAIL_COMMENT_WRITE_REQUEST) {
            try {
                jsonBodyObj.put("content", comment);
                jsonBodyObj.put("articleId", Integer.parseInt(id));
                jsonBodyObj.put("writer", id_text);
                url = "https://www.eku.kro.kr/comment/free/write";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (mode == DETAIL_BOARD_MODIFY_REQUEST) {
            try {
                String title = et_title.getText().toString();
                String content = et_content.getText().toString();

                jsonBodyObj.put("id", Integer.parseInt(id));
                jsonBodyObj.put("title", title);
                jsonBodyObj.put("content", content);
                jsonBodyObj.put("building", "");

                url = "https://www.eku.kro.kr/board/free/update";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (mode == DETAIL_BOARD_DELETE_REQUEST) {
            try {
                jsonBodyObj.put("id", Integer.parseInt(id));
                url = "https://www.eku.kro.kr/board/free/delete";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        final String requestBody = String.valueOf(jsonBodyObj.toString());

        if (mode == DETAIL_BOARD_MODIFY_REQUEST || mode == DETAIL_BOARD_DELETE_REQUEST) {
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (mode == DETAIL_BOARD_MODIFY_REQUEST) {
                                getBoardDetail(board_id, DETAIL_BOARD_REQUEST);
                            } else if (mode == DETAIL_BOARD_DELETE_REQUEST) {
                                finish();
                                /*
                                Intent intent = new Intent(getApplicationContext(), BoardPresenter.class);
                                startActivity(intent);
                                 */
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }

                @Override
                public byte[] getBody() {
                    try {
                        if (requestBody != null && requestBody.length() > 0 && !requestBody.equals("")) {
                            return requestBody.getBytes("utf-8");
                        } else {
                            return null;
                        }
                    } catch (UnsupportedEncodingException uee) {
                        return null;
                    }
                }
            };

            request.setShouldCache(false);
            queue.add(request);
        } else {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (mode == DETAIL_BOARD_REQUEST) {
                                try {
                                    arrayList.clear();
                                    String title = response.getString("title");
                                    String content = response.getString("content");
                                    String writer = response.getString("department") + " " + response.getString("writerNo");
                                    String time = response.getString("time");
                                    JSONArray jsonArray = response.getJSONArray("commentList");
                                    for (int x = 0; x < jsonArray.length(); x++) {
                                        JSONObject comment_obj = jsonArray.getJSONObject(x);

                                        String comment_content = comment_obj.getString("content");
                                        String comment_writtenTime = comment_obj.getString("writtenTime");
                                        String comment_writer = comment_obj.getString("writer");
                                        String comment_id = comment_obj.getString("fid");

                                        addItem(comment_id, comment_writer, comment_content, comment_writtenTime);

                                    }

                                    writer_id = response.getString("writerNo");

                                    tv_title.setText(title);
                                    tv_content.setText(content);
                                    writer = writer.substring(2,3);
                                    tv_writer.setText(writer);
                                    tv_time.setText(time);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (mode == DETAIL_COMMENT_WRITE_REQUEST) {
                                getBoardDetail(board_id, DETAIL_BOARD_REQUEST);
                                et_comment.setText("");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }

                @Override
                public byte[] getBody() {
                    try {
                        if (requestBody != null && requestBody.length() > 0 && !requestBody.equals("")) {
                            return requestBody.getBytes("utf-8");
                        } else {
                            return null;
                        }
                    } catch (UnsupportedEncodingException uee) {
                        return null;
                    }
                }
            };

            request.setShouldCache(false);
            queue.add(request);
        }
    }

    public void dialog_delete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("게시글을 삭제");
        builder.setMessage("삭제하시겠습니까? ");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getBoardDetail(board_id, DETAIL_BOARD_DELETE_REQUEST);
                        Toast.makeText(getApplicationContext(),"삭제되었습니다.",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    public void dialog_not_access() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("작성자가 아닙니다.");
        builder.setMessage("게시글을 삭제 및 수정 하실수 없습니다.");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

}