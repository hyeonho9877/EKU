package com.kyonggi.eku;


import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minew.beacon.BeaconValueIndex;
import com.minew.beacon.MinewBeacon;

import java.util.ArrayList;
import java.util.List;


public class BeaconListAdapter extends RecyclerView.Adapter<BeaconListAdapter.MyViewHolder> {
    /**
     * 리사이클뷰를 확장한 어댑터
     */
    private List<MinewBeacon> mMinewBeacons = new ArrayList<>();

    /**
     *
     *  뷰 1개를 리턴함
     *  main_item의 뷰 한개를 리턴하게됨
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.main_item, null);
        return new MyViewHolder(view);
    }

    /**
     *
     *  holder로 받아온 MyViewHolder 객체의 데이터를 저장한다.
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setDataAndUi(mMinewBeacons.get(position));
    }

    /**

     * 아마 주변에 있는 비콘들의 개수를 반환해 주는 듯?
     */
    @Override
    public int getItemCount() {
        if (mMinewBeacons != null) {
            return mMinewBeacons.size();
        }
        return 0;
    }

    /**
     *
     * 클래스 변수에다가 지금 불러온 객체를 저장하는 메소드
     */
    public void setData(List<MinewBeacon> minewBeacons) {
        this.mMinewBeacons = minewBeacons;

//        notifyItemRangeChanged(0,minewBeacons.size());
        notifyDataSetChanged();

    }

    /**
     *
     * 리스트뷰에 있는것을 업데이트하는 듯?
     */
    public void setItems(List<MinewBeacon> newItems) {
//        validateItems(newItems);


        int startPosition = 0;
        int preSize = 0;
        if (this.mMinewBeacons != null) {
            preSize = this.mMinewBeacons.size();

        }
        if (preSize > 0) {
            this.mMinewBeacons.clear();
            notifyItemRangeRemoved(startPosition, preSize);
        }
        this.mMinewBeacons.addAll(newItems);
        notifyItemRangeChanged(startPosition, newItems.size());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        /**
         * ViewHolder의 확장판
         * ViewHolder는 그 우리가 밑으로 슥슥 내릴때 보는 화면을 많이 생성하지 않고
         * 화면에 표시 되는 만큼만 생성해서 관리함
         * 즉 야구장가면 점수판이 바뀌잖슴? 그거인듯?
         *
         **/
        private final TextView mDevice_mac;
        //연결된 기계의 맥주소
        private MinewBeacon mMinewBeacon;
        // 비콘
        private final TextView mDevice_temphumi;
        // 템프 후미..? 이건 모르겠네
        private final TextView mDevice_name;
        // 기계이름
        private final TextView mDevice_uuid;
        // 기계 uuid
        private final TextView mDevice_other;
        // 기타..

        public MyViewHolder(View itemView) {
            super(itemView);
            //itemview의 생성자 그대로 쓰고
            //아이템 뷰의 객체를 변수로 저장해놓음
            mDevice_name = (TextView) itemView.findViewById(R.id.device_name);
            mDevice_uuid = (TextView) itemView.findViewById(R.id.device_uuid);
            mDevice_other = (TextView) itemView.findViewById(R.id.device_other);
            mDevice_temphumi = (TextView) itemView.findViewById(R.id.device_temphumi);
            mDevice_mac = (TextView) itemView.findViewById(R.id.device_mac);

        }

        /**
         * 비컨컨의 배터리 온도 습도을 가지고 오는듯?
         *
         */
        public void setDataAndUi(MinewBeacon minewBeacon) {

            mMinewBeacon = minewBeacon;
            mDevice_name.setText(mMinewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Name).getStringValue());
            mDevice_mac.setText(mMinewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_MAC).getStringValue());
            mDevice_uuid.setText("UUID:" + mMinewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_UUID).getStringValue());
            String battery = mMinewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_BatteryLevel).getStringValue();
            int batt = Integer.parseInt(battery);
            if (batt > 100) {
                batt = 100;
            }

            String format = String.format("Major:%s Minor:%s Rssi:%s Battery:%s",
                    mMinewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Major).getStringValue(),
                    mMinewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Minor).getStringValue(),
                    mMinewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_RSSI).getStringValue(),
                    batt + "");
            mDevice_other.setText(format);

            if (mMinewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Humidity).getFloatValue() == 0 &&
                    mMinewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Temperature).getFloatValue() == 0) {
                mDevice_temphumi.setVisibility(View.GONE);
            } else {
                mDevice_temphumi.setVisibility(View.VISIBLE);
                String temphumi = String.format("Temperature:%s ℃   Humidity:%s ",
                        mMinewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Temperature).getFloatValue() + "",
                        mMinewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Humidity).getFloatValue() + "");

                mDevice_temphumi.setText(temphumi + "%");
            }

        }
    }
}
