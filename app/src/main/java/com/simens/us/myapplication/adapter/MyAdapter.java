package com.simens.us.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simens.us.myapplication.Constance.Constance;
import com.simens.us.myapplication.R;
import com.simens.us.myapplication.Utils.KumaLog;
import com.simens.us.myapplication.data.DeviceInfo;
import com.simens.us.myapplication.data.DropBoaxCommonDTO;
import com.simens.us.myapplication.data.MainDeviceData;

import java.util.ArrayList;

/**
 * Created by Kuma on 2017-12-16.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private ArrayList<DeviceInfo> mDataset;

    private static OnItemClickListener mListener;

    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;

    private boolean mBFooterState = false;

    public interface OnItemClickListener {
        void onItemClick(DeviceInfo data);
        void onUpdateClick();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTextViewProduct;
        public TextView mTvDestination;
        public TextView mTvMakeDate;
        public TextView mTvMakeDateTitle;
        public TextView mTvStatus;
        public ItemViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mTextViewProduct = (TextView)view.findViewById(R.id.tv_product_name);
            mTvDestination = (TextView)view.findViewById(R.id.tv_destination);
            mTvMakeDate = (TextView)view.findViewById(R.id.tv_make_date);
            mTvMakeDateTitle = (TextView)view.findViewById(R.id.tv_make_date_title);
            mTvStatus = (TextView)view.findViewById(R.id.tv_status);

        }

        @Override
        public void onClick(View view) {
            KumaLog.d("getAdapterPosition >> " + getAdapterPosition());
            if( mListener != null ) {
                mListener.onItemClick((DeviceInfo)mTextViewProduct.getTag());
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<DeviceInfo> myDataset, OnItemClickListener listener) {
        mDataset = myDataset;
        this.mListener = listener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public void setDataList(ArrayList<DeviceInfo> myDataset) {
        mDataset = myDataset;
        this.notifyDataSetChanged();
    }
    public void  removeFooterView(){
        KumaLog.i("++++++++++++++++++++ removeFooterView +++++++++++++++++++++++++");
        mBFooterState = false;
        this.notifyDataSetChanged();
    }

    public void  showFooter(){
        mBFooterState = true;
        this.notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
       RecyclerView.ViewHolder holder;
        View view;

        // create a new view
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.my_view, parent, false);
//        // set the view's size, margins, paddings and layout parameters
//        ViewHolder vh = new ViewHolder(v);
        if (viewType == TYPE_FOOTER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false);
            holder = new FooterViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_view, parent, false);
            holder = new ItemViewHolder(view);
        }

        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        if (getItemViewType(position) == TYPE_FOOTER) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
        } else if (getItemViewType(position) == TYPE_ITEM) {
            // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            DeviceInfo info = mDataset.get(position);
            String strModel =  "";

            itemViewHolder.mTextViewProduct.setTag(info);

            if(info.getKind().equals("M"))  {
                strModel = "[장비] " +info.getModelName() + " ( " +   info.getModelVersion() + " )";
            } else if(info.getKind().equals("P"))  {
                strModel = "[프로브] " +info.getModelName() ;
            } else {
                strModel = "[악세사리] " +info.getModelName();
            }

            itemViewHolder.mTextViewProduct.setText(strModel);
            if(info.getKind().equals("M"))  {
                itemViewHolder.mTvMakeDateTitle.setText("제조일 : " );
            } else {
                itemViewHolder.mTvMakeDateTitle.setText("제조년 : " );
            }
            itemViewHolder.mTvMakeDate.setText(info.getMakeDate());

            itemViewHolder.mTvStatus.setText(Constance.changeStringToState(info.getState()));
            DropBoaxCommonDTO data = Constance.getDestinationData(Integer.parseInt(info.getDemoPk()));
            KumaLog.d("  itemViewHolder.mTvDestination getName  >>>  " + data.getName());
            itemViewHolder.mTvDestination.setText(data.getName());
        }


    }

    private boolean  hasFooter(){

        return mBFooterState;
    }
    @Override
    public int getItemViewType(int position) {
        if (hasFooter()  && position == getItemCount() - 1)
            return TYPE_FOOTER;
        else
            return TYPE_ITEM;
    }

//    {"pk":1,"kind":"M","serialNo":"10001","modelPk":1,"demoPk":0,"osPk":1,"state":"N","makeDate":"2019-11-09",
//            "message":"","insertTime":"2019-11-09 00:37:15","updateTime":"2019-11-11 11:08:21","modelName":"NX2 Elite","modelVersion":"VA11","modelColor":"#A748FF"}
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        KumaLog.i("++++++++++++++++++++ getItemCount hasFooter() +++++++++++++++++++++++++" + hasFooter());

        return mDataset.size() + (hasFooter() ? 1 : 0);
    }

    class FooterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public FooterViewHolder(View footerView) {
            super(footerView);
            footerView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            KumaLog.d("onUpdateClick >> " );
            if( mListener != null ) {
                mListener.onUpdateClick();
            }
        }
    }
}