package com.devix.www.fragmentfotosvideos;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends Fragment {
    private Cursor cursor;
    private int columnIndex;
    private static final String TAG = "RecyclerViewExample";

    private List<DataPictures> mediaList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MediaRVAdapter adapter;
    String type = "";


    public VideoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type = "video";
        new MediaAsyncTask().execute(type);
    }

    private void parseAllVideo(String type) {
        try {
            String name = null;
            String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA,
                    MediaStore.Video.Thumbnails.VIDEO_ID};

            int video_column_index;
            String[] proj = {MediaStore.Video.Media._ID, MediaStore.Video.Media.DATA, MediaStore.Video.Media.DISPLAY_NAME,
                    MediaStore.Video.Media.SIZE};
            Cursor videocursor = getActivity().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    proj, null, null, null);
            int count = videocursor.getCount();
            Log.d("No of video", "" + count);
            for (int i = 0; i < count; i++) {
                DataPictures mediaFileInfo = new DataPictures();
                video_column_index = videocursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
                videocursor.moveToPosition(i);
                name = videocursor.getString(video_column_index);

                mediaFileInfo.setFileName(name);

                int column_index = videocursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                videocursor.moveToPosition(i);
                String filepath = videocursor.getString(column_index);

                mediaFileInfo.setFilePath(filepath);
                mediaFileInfo.setFileType(type);
                mediaList.add(mediaFileInfo);
                // id += " Size(KB):" +
                // videocursor.getString(video_column_index);


            }
            videocursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        return view;
    }

    public class MediaAsyncTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            String type = params[0];
            try {
                mediaList = new ArrayList<>();
                if (type.equalsIgnoreCase("video")) {
                    parseAllVideo(type);
                    result = 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = 0;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == 1) {
                adapter = new MediaRVAdapter(getActivity(), mediaList);
                mRecyclerView.setAdapter(adapter);
            } else {
                Log.e(TAG, "Failed to show list");
            }
        }
    }

}
