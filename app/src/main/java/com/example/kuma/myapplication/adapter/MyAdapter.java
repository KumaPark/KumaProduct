package com.example.kuma.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kuma.myapplication.R;
import com.example.kuma.myapplication.Utils.KumaLog;
import com.example.kuma.myapplication.data.DeviceInfo;
import com.example.kuma.myapplication.data.MainDeviceData;

import java.util.ArrayList;

/**
 * Created by Kuma on 2017-12-16.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<DeviceInfo> mDataset;

    private static OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(DeviceInfo data);
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mTextView = (TextView)view.findViewById(R.id.textview);
        }

        @Override
        public void onClick(View view) {
            KumaLog.d("getAdapterPosition >> " + getAdapterPosition());
            if( mListener != null ) {
                mListener.onItemClick((DeviceInfo)mTextView.getTag());
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<DeviceInfo> myDataset, OnItemClickListener listener) {
        mDataset = myDataset;
        this.mListener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String strModel =  "";
        if( !TextUtils.isEmpty(mDataset.get(position).getVersion())) {
            strModel = mDataset.get(position).getProductCode() + " ( " + mDataset.get(position).getVersion() + " )";
        }  else {
            strModel = mDataset.get(position).getProductCode();
        }
        holder.mTextView.setTag(mDataset.get(position));
        holder.mTextView.setText(strModel);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}