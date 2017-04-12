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


public class FotoFragment extends Fragment {
    private Cursor cursor;
    private int columnIndex;
    private static final String TAG = "RecyclerViewExample";

    private List<DataPictures> mediaList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MediaRVAdapter adapter;
    String type = "";


    public FotoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type = "images";
        new MediaAsyncTask().execute(type);
    }

    private void parseAllImages(String type) {
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    null);

            int size = cursor.getCount();
            if (size == 0) {

            } else {

                while (cursor.moveToNext()) {

                    int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    String path = cursor.getString(file_ColumnIndex);
                    String fileName = path.substring(path.lastIndexOf("/") + 1, path.length());
                    DataPictures mediaFileInfo = new DataPictures();
                    mediaFileInfo.setFilePath(path);
//                    mediaFileInfo.setFileName(fileName);Visualizar nombre archivo
                    mediaFileInfo.setFileType(type);
                    mediaList.add(mediaFileInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_blank, container, false);
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
                if (type.equalsIgnoreCase("images")) {
                    parseAllImages(type);
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
