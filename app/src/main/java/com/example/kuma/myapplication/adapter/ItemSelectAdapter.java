package com.simens.us.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simens.us.myapplication.R;
import com.simens.us.myapplication.Utils.KumaLog;
import com.simens.us.myapplication.data.SelectItemInfo;

import java.util.ArrayList;
import java.util.List;

public class ItemSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;

    private List<SelectItemInfo> data;

    private static OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onAddClick(SelectItemInfo data);
        void onRemoveClick(SelectItemInfo data);
    }

    public ItemSelectAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        Context context = parent.getContext();
        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18 * dp);
        int subItemPaddingTopAndBottom = (int) (5 * dp);
        switch (type) {
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_header, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                return header;
            case CHILD:

                LayoutInflater inflaterChild = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflaterChild.inflate(R.layout.list_item_child, parent, false);
                ListChildViewHolder child = new ListChildViewHolder(view);

                return child;
        }
        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final SelectItemInfo item = data.get(position);
        switch (item.type) {
            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                itemController.refferalItem = item;
                itemController.header_title.setText(item.categoryName);
                if (item.invisibleChildren == null) {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                } else {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
                }
                itemController.btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.invisibleChildren == null) {
                            item.invisibleChildren = new ArrayList<SelectItemInfo>();
                            int count = 0;
                            int pos = data.indexOf(itemController.refferalItem);
                            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
                                item.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
                        } else {
                            int pos = data.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (SelectItemInfo info : item.invisibleChildren) {
                                data.add(index, info);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                            item.invisibleChildren = null;
                        }
                    }
                });
                break;
            case CHILD:
                final ListChildViewHolder itemChild = (ListChildViewHolder) holder;
                data.get(position).position = position;
                itemChild.ll_child.setTag(data.get(position));

                itemChild.child_title.setText(data.get(position).serialNo);
                if( data.get(position).checked ) {
                    itemChild.ll_child.setBackgroundColor(Color.parseColor("#FFDEA9"));
                } else {
                    itemChild.ll_child.setBackgroundColor(Color.parseColor("#ffffff"));
                }

                itemChild.ll_child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SelectItemInfo info = (SelectItemInfo) view.getTag();
                        int posi = info.position;
                        try {
                            if( !info.checked ) {
                                info.checked = true;
                                notifyItemChanged(posi);
                                view.setBackgroundColor(Color.parseColor("#ffffff"));

                                if( mListener != null ) {
                                    mListener.onAddClick(info);
                                }
                            } else {
                                info.checked = false;
                                notifyItemChanged(posi);
                                view.setBackgroundColor(Color.parseColor("#FFDEA9"));

                                if( mListener != null ) {
                                    mListener.onRemoveClick(info);
                                }
                            }
                        } catch (Exception e) {
                            KumaLog.e(e.toString());
                            e.printStackTrace();
                        }

                    }
                });
                break;
        }

    }

    public void setData(List<SelectItemInfo> data) {
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title;
        public ImageView btn_expand_toggle;
        public SelectItemInfo refferalItem;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            header_title = (TextView) itemView.findViewById(R.id.header_title);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
        }
    }

    private static class ListChildViewHolder extends RecyclerView.ViewHolder {
        public TextView child_title;
        public LinearLayout ll_child;
        public SelectItemInfo childInfo;

        public ListChildViewHolder(View itemView) {
            super(itemView);
            child_title = (TextView) itemView.findViewById(R.id.child_title);
            ll_child = (LinearLayout) itemView.findViewById(R.id.ll_child);
        }
    }
}
