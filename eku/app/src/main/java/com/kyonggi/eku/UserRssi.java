package com.kyonggi.eku;

import com.minew.beacon.BeaconValueIndex;
import com.minew.beacon.MinewBeacon;

import java.util.Comparator;

public class UserRssi implements Comparator<MinewBeacon> {

    /*
     * 블루투스 Legacy
     * 지우지 마세요 ㅠㅠ
     *
     * */
    @Override
    public int compare(MinewBeacon minewBeacon, MinewBeacon t1) {
        float floatValue1 = minewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_RSSI).getFloatValue();
        float floatValue2 = t1.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_RSSI).getFloatValue();
        if(floatValue1<floatValue2){
            return 1;
        }else if(floatValue1==floatValue2){
            return 0;
        }else {
            return -1;
        }
    }
}
