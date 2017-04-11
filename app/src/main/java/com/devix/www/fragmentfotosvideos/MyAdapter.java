package com.devix.www.fragmentfotosvideos;
/*
 *Created by Carlos Anguiano on 10/04/17.
 *For more info contact : c.joseanguiano@gmail.com
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private String[] mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTextView;
        public ImageView mImageView;

        public MyViewHolder(View view) {
            super(view);
            mCardView = (CardView) view.findViewById(R.id.card_view);
            mTextView = (TextView) view.findViewById(R.id.tv_text);
            mImageView = (ImageView)view.findViewById(R.id.iv_image);
        }
    }

    public MyAdapter(String[] mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        holder.mTextView.setText(mDataset[position]);
        holder.mImageView.setImageResource(R.drawable.logo_slogan);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
