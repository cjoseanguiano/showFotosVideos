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
import android.widget.Toast;

import com.devix.www.fragmentfotosvideos.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class MediaRVAdapter extends RecyclerView.Adapter<MediaListRowHolder> {

    private List<DataPictures> itemList;
    private Context mContext;


    public MediaRVAdapter(Context context, List<DataPictures> itemList) {
        this.itemList = itemList;
        this.mContext = context;
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
                Toast.makeText(mContext, "Tomar valor " + i, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != itemList ? itemList.size() : 0);
    }
}