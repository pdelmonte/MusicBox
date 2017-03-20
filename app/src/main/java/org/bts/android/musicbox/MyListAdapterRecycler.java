package org.bts.android.musicbox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by pedrodelmonte on 20/03/17.
 */

public class MyListAdapterRecycler extends RecyclerView.Adapter<MyListAdapterRecycler.ViewHolder> {
    Context mContext;
    ArrayList<Item> itemList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View viewRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row_layout, parent, false);

        viewRow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View whichView) {
                Toast.makeText(mContext, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        ViewHolder viewRowHolder = new ViewHolder(viewRow);
        return viewRowHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.icon_ImgView.setImageResource(this.mContext.getResources()
                .getIdentifier(this.itemList.
                        get(position).getImagePath(), "drawable",this.mContext.getPackageName()));

        holder.title_TxtView.setText(this.itemList.get(position).getTitle());


    }

    @Override
    public int getItemCount() {

        if (itemList != null) {
            return this.itemList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView icon_ImgView;
        public TextView title_TxtView;

        public ViewHolder(View itemView){
            super(itemView);

            this.icon_ImgView = (ImageView) itemView.findViewById(R.id.im_title_row_layout);
            this.title_TxtView = (TextView) itemView.findViewById(R.id.tv_title_row_layout);


        }

    }

    public MyListAdapterRecycler(Context context, ArrayList<Item> objects) {
        this.mContext = context;
        this.itemList = objects;
    }
}
