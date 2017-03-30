package org.bts.android.musicbox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pedrodelmonte on 28/03/17.
 */

public class MyListAdapter extends ArrayAdapter<Item> {

    private Context mContext;
    private ArrayList<Item> mList;

    public MyListAdapter(Context context, int resource, ArrayList<Item> mList) {
        super(context, resource, mList);

        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = mInflater.inflate(R.layout.list_view_row_layout, null);

        TextView rowBodyTv = (TextView) rowView.findViewById(R.id.tv_title_row_layout);
        rowBodyTv.setText(PlayerActivity.getListItem().get(position).getTitle() + position);

        return rowView;
    }


}
