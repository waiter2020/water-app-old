package pub.upc.dc.water.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import pub.upc.dc.water.R;
import pub.upc.dc.water.bean.EquipmentInfo;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link EquipmentInfo} and makes a call to the
 * specified {@link ItemFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<EquipmentInfo> mValues;
    private final ItemFragment.OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<EquipmentInfo> items, ItemFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.mIdView.setText(mValues.get(position).getEquipId());
        holder.mContentView.setText(String.valueOf(mValues.get(position).getName()));
        holder.equipWaterUse.setText(String.format("%.1f",mValues.get(position).getWaterUsage()));

        EquipmentInfo equipmentInfo = mValues.get(position);

        if (equipmentInfo.isOnline()){
            if(!equipmentInfo.isOpen()&&equipmentInfo.getEquipState()!=1&&equipmentInfo.getEquipState()!=2){
                holder.equipState.setText("关总阀");
            }else {
                switch (equipmentInfo.getEquipState()){
                    case 0:
                        holder.equipState.setText("正常");
                        break;
                    case 1:
                        holder.equipState.setText("小漏失");
                        break;
                    case 2:
                        holder.equipState.setText("大漏失");
                        break;
                    case 5:
                        holder.equipState.setText("正常");
                        break;
                }
            }
        }else {
            holder.equipState.setText("离线");
        }

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

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView equipWaterUse;
        public final TextView equipState;

        public EquipmentInfo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.equip_id);
            mContentView = (TextView) view.findViewById(R.id.equip_name);
            equipWaterUse = (TextView) view.findViewById(R.id.equip_water_use);
            equipState = (TextView) view.findViewById(R.id.equip_state);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'"+" '" + equipWaterUse.getText() + "'"+" '" + equipState.getText() + "'";
        }

    }
}
