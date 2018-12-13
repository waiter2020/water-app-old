package pub.upc.dc.water.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pub.upc.dc.water.R;
import pub.upc.dc.water.bean.WaterCondition;
import pub.upc.dc.water.fragment.WaterConditionFragment.OnListFragmentInteractionListener;


import java.text.SimpleDateFormat;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link pub.upc.dc.water.bean.WaterCondition} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyWaterConditionRecyclerViewAdapter extends RecyclerView.Adapter<MyWaterConditionRecyclerViewAdapter.ViewHolder> {

    private final List<WaterCondition> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");

    public MyWaterConditionRecyclerViewAdapter(List<WaterCondition> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_watercondition, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf(mValues.get(position).getWatermeterId()));
        holder.mContentView.setText(String.valueOf(mValues.get(position).getVolumn()));
        holder.startTime.setText(simpleDateFormat.format(mValues.get(position).getStartDate()));
        holder.endTime.setText(simpleDateFormat.format(mValues.get(position).getEndDate()));


        holder.timeUse.setText(getTime(mValues.get(position).getTimeUse()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    private String getTime(Long s){
        long hour,minute;
        s/=1000;
        hour = s/3600;
        minute = (s - hour*3600)/60;
        s = s-hour*300-minute*60;
        String time="";
        if (hour>0){
            time=hour+"小时"+minute+"分"+s+"秒";
        }else if (minute>0){
            time=minute+"分"+s+"秒";
        }else {
            time=s+"秒";
        }
        return time;
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView startTime;
        public final TextView endTime;
        public final TextView timeUse;
        public WaterCondition mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            startTime= (TextView) view.findViewById(R.id.start_time);
            endTime = (TextView) view.findViewById(R.id.end_time);
            timeUse = (TextView) view.findViewById(R.id.time_use);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
