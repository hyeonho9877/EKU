package com.kyonggi.eku;

import androidx.appcompat.app.AppCompatActivity;


public class cameraTest extends AppCompatActivity {

    /**
     * SendTool 더미코드
     * SendTool 사용법이 적혀있습니다 ^^

    Button btnCamera;

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_json);
        Button button = (Button) findViewById(R.id.buttonTest);
        TextView textView =(TextView) findViewById(R.id.textView9);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler(){
                    public void handleMessage(@NonNull Message msg){
                        switch(msg.what){
                            case 0:
                                String responseResult=(String)msg.obj;
                                Log.i("Response : ", responseResult);
                                textView.setText(responseResult);

                        }
                    }
                };

                HashMap<String,String> temp = new HashMap<>();
                temp.put("email","yas5@kyonggi.ac.kr");
                temp.put("password","dkssud");


                try {
                    SendTool.request("http://www.eku.kro.kr/signIn","POST",temp);
                } catch (IOException e) {
                    Toast.makeText(getBaseContext(),"서버에러!",Toast.LENGTH_SHORT);
                    e.printStackTrace();
                } catch (JSONException e) {
                    Toast.makeText(getBaseContext(),"JSON에러!",Toast.LENGTH_SHORT);
                    e.printStackTrace();
                }

            }
        });

    }
    */



}