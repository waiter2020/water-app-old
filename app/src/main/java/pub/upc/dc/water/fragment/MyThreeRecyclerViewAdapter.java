package pub.upc.dc.water.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import pub.upc.dc.water.R;
import pub.upc.dc.water.UserActivity;
import pub.upc.dc.water.data.AppData;
import pub.upc.dc.water.fragment.ThreeFragment.OnListFragmentInteractionListener;
import pub.upc.dc.water.utils.Action;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyThreeRecyclerViewAdapter extends RecyclerView.Adapter<MyThreeRecyclerViewAdapter.ViewHolder> {

    private final OnListFragmentInteractionListener mListener;


    public MyThreeRecyclerViewAdapter( OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_three, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mIdView.setText(AppData.getUser().getUsername());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) v.getContext();
                activity.startActivity(new Intent(v.getContext(), UserActivity.class));
            }
        });

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Action.logout(v.getContext());
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final Button button;
        public final RelativeLayout relativeLayout;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.text_myname);
            button = (Button) view.findViewById(R.id.exit_account);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.btn_changeHead);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }
}
