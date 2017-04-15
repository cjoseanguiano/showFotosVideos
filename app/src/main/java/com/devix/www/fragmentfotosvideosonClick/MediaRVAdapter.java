package com.devix.www.fragmentfotosvideosonClick;
/*
 *Created by Carlos Anguiano on 11/04/17.
 *For more info contact : c.joseanguiano@gmail.com
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.devix.www.fragmentfotosvideos.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class MediaRVAdapter extends RecyclerView.Adapter<MediaRVAdapter.MediaListRowHolder> {

    private List<DataPictures> itemList;
    public AdapterView.OnItemClickListener mClickListner;
    private Context mContext;
    private DataPictures model;
    private static RecyclerViewClickListener mListener;

    public MediaRVAdapter(Context context, List<DataPictures> itemList, RecyclerViewClickListener mListener) {
        this.itemList = itemList;
        this.mContext = context;
        this.mListener = mListener;
    }


    @Override
    public MediaListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
        final MediaListRowHolder mh = new MediaListRowHolder(v);

        return mh;
    }

    @Override
    public void onBindViewHolder(final MediaListRowHolder mediaListRowHolder, final int i) {

        model = itemList.get(i);
        mediaListRowHolder.thumbnail.setBackgroundColor(i);
        try {
            final DataPictures item = itemList.get(i);

            Uri uri = Uri.fromFile(new File(item.getFilePath()));
            if (item.getFileType().equalsIgnoreCase("video")) {
                Bitmap bmThumbnail = ThumbnailUtils.
                        extractThumbnail(ThumbnailUtils.createVideoThumbnail(item.getFilePath(),
                                MediaStore.Video.Thumbnails.FULL_SCREEN_KIND), 90, 60);
                if (bmThumbnail != null) {
                    mediaListRowHolder.thumbnail.setImageBitmap(bmThumbnail);
                }
            } else if (item.getFileType().equalsIgnoreCase("audio")) {
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(item.getFilePath());
                try {
                    if (mmr != null) {
                        byte[] art = mmr.getEmbeddedPicture();
                        Bitmap bmp = BitmapFactory.decodeByteArray(art, 0, art.length);
                        if (bmp != null) {
                            bmp = ThumbnailUtils.extractThumbnail(bmp, 90, 60);
                            mediaListRowHolder.thumbnail.setImageBitmap(bmp);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Picasso.with(mContext).load(uri)
                        .error(R.drawable.logo_slogan)
                        .placeholder(R.drawable.logo_slogan)
                        .centerCrop()
                        .resize(90, 60)
                        .into(mediaListRowHolder.thumbnail);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return (null != itemList ? itemList.size() : 0);
    }


    public class MediaListRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View view;
        protected ImageView thumbnail;
        protected TextView title;


        public MediaListRowHolder(View view) {
            super(view);
            this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            this.title = (TextView) view.findViewById(R.id.title);
//        this.view = view;
            view.setOnClickListener(this);

        }

        public void dysplay(String text, boolean isSelected) {
            title.setText(text);
            dysplay(isSelected);
        }

        public void dysplay(boolean isSelected) {
            title.setBackgroundResource(isSelected ? R.drawable.logo_slogan : R.drawable.logo_slogan);
        }

        @Override
        public void onClick(View v) {
            /*if (mClickListner != null) {
                model.setSelected(!model.isSelected());
                System.out.println("Valor ");
                Log.e("A", "Intento " + getAdapterPosition());

                if (model.isSelected()) thumbnail.setBackgroundColor(Color.GREEN);
                else thumbnail.setBackgroundColor(Color.RED);

                Log.e(MediaRVAdapter.this.toString(), "A" + getAdapterPosition());
                mClickListner.onItemClick((AdapterView<?>) itemList, v, getAdapterPosition(), MediaRVAdapter.this.getItemId(getPosition()));
            }*/
            mListener.recyclerViewListClicked(v, getLayoutPosition());
            Log.e("A", "Intento " + getAdapterPosition());

        }
    }

}