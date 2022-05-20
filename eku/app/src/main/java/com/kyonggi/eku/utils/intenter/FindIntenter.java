package com.kyonggi.eku.utils.intenter;

import android.content.Context;
import android.content.Intent;

import com.kyonggi.eku.LectureDetail;
import com.kyonggi.eku.MainBoard;
import com.kyonggi.eku.ScheduleTable;
import com.kyonggi.eku.WriteAnnounce;
import com.kyonggi.eku.WriteFreeCommunity;


/**
 *
 * FindIntenter
 *
 * 예) 다른 창에서 계정관리를 눌렀을 때
 * 인텐트를 보낼때 즉 다른 창으로 보낼 때 전 창을 기억하고 싶을경우
 * intent.putExtra("address",클래스명을 String으로 ex)"MainBoard")
 *
 * 그러면 이제 새 창이 열렸을 때 과거창을 되돌아가려면
 * FindIntenter.findIntent(getApplicationContext,getIntent);
 * 로 intent객체를 되돌아 받을 수 있습니다.
 *
 * intent = FindIntenter.findIntent(getApplicationContext,getIntent);
 * startActivity(intent);
 * finish();
 * 해주시면 될 것 같습니다!
 */
public final class FindIntenter {
    public static Intent findIntent(Context getApplicationContext,Intent prevIntent) {
        String prevReturn = prevIntent.getStringExtra("address");
        if (prevReturn == null)
        {
            prevReturn ="";
        }
        Intent intent =null;
        switch(prevReturn){
            case "ScheduleTable":
                intent= new Intent(getApplicationContext, ScheduleTable.class);
                break;
            case "WriteAnnounce":
                intent= new Intent(getApplicationContext, WriteAnnounce.class);
                break;
            case "WriteFreeCommunity":
                intent= new Intent(getApplicationContext, WriteFreeCommunity.class);
                break;
            case "LectureDetail":
                intent= new Intent(getApplicationContext, LectureDetail.class);
                break;
            case "MainBoard":
            default:
                intent= new Intent(getApplicationContext, MainBoard.class);
                break;
        }
        return intent;
    }
}
