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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devix.www.fragmentfotosvideos.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class MediaRVAdapter extends RecyclerView.Adapter<MediaListRowHolder> {

    private List<DataPictures> itemList;
    private Context mContext;
    //    public List<Integer> selectedPositions;
    CustomItemClickListener listener;


    public MediaRVAdapter(Context context, List<DataPictures> itemList, CustomItemClickListener listener) {
        this.itemList = itemList;
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    public MediaListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
        final MediaListRowHolder mh = new MediaListRowHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(MediaRVAdapter.this, v, mh.getPosition());

            }
        });
        return mh;
    }

    @Override
    public void onBindViewHolder(final MediaListRowHolder mediaListRowHolder, final int i) {
        final DataPictures item = itemList.get(i);

        try {

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
 /*       mediaListRowHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Tomar valor " + i, Toast.LENGTH_SHORT).show();
//                item.setSelected(!(item.isSelected()));
//                mediaListRowHolder.view.setBackgroundColor(item.isSelected() ? Color.BLUE : Color.TRANSPARENT);
                    listener.onItemClick(v, i);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return (null != itemList ? itemList.size() : 0);
    }

}