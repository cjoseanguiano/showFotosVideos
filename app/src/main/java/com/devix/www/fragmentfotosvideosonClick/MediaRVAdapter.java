package com.devix.www.fragmentfotosvideosonClick;
/*
 *Created by Carlos Anguiano on 11/04/17.
 *For more info contact : c.joseanguiano@gmail.com
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devix.www.fragmentfotosvideos.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class MediaRVAdapter extends RecyclerView.Adapter<MediaRVAdapter.MediaListRowHolder> {

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(int position);
    }

    private Fragment mfragment;
    private List<DataPictures> itemList;
    private Context mContext;

    public class MediaListRowHolder extends RecyclerView.ViewHolder {
        //    private final View view;
        public View view;
        protected ImageView thumbnail;
        protected TextView title;

        public MediaListRowHolder(View view) {
            super(view);
            this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            this.title = (TextView) view.findViewById(R.id.title);
            this.view = view;
//        this.view = view;
//        view.setOnLongClickListener(this);
//        view.setOnClickListener(this);
        }
/*
    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "position = " + getPosition(), Toast.LENGTH_SHORT).show();

    }*/

        public void dysplay(String text, boolean isSelected) {
//        textView.setText(text);
            thumbnail.setImageResource(R.drawable.logo_slogan);
            dysplay(isSelected);
        }

        public void dysplay(boolean isSelected) {
            thumbnail.setBackgroundResource(isSelected ? R.drawable.logo_slogan : R.drawable.logo_slogan);
        }

/*    @Override
    public boolean onLongClick(View v) {
//        Toast.makeText(v.getContext(), "position onLongClick= " + getPosition(), Toast.LENGTH_SHORT).show();

        return false;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "position onClick= " + getPosition(), Toast.LENGTH_SHORT).show();

    }*/
    }

    public MediaRVAdapter(Context context, List<DataPictures> itemList) {
        this.itemList = itemList;
        this.mContext = context;
    }

    public MediaRVAdapter(Fragment fragment) {
        mfragment = fragment;
    }

    @Override
    public MediaListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
        MediaListRowHolder mh = new MediaListRowHolder(v);

        return mh;
    }

    @Override
    public void onBindViewHolder(MediaListRowHolder mediaListRowHolder, final int i) {
        try {
            DataPictures item = itemList.get(i);

//            mediaListRowHolder.title.setText(Html.fromHtml(item.getFileName())); Visualizar nombre archivo
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
        mediaListRowHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "XD " + i, Toast.LENGTH_SHORT).show();
//                Toast.makeText(v.getContext(), "position = " + getPosition(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
//        return (null != itemList ? itemList.size() : 0);
        return itemList.size();
    }
}