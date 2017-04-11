package com.devix.www.fragmentfotosvideos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FotoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FotoFragment extends Fragment {


    public FotoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_recycler_view);
        recyclerView.setHasFixedSize(true);
        MyAdapter adapter = new MyAdapter(new String[]{"Foto One", "Foto Two", "Foto Three"});
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;

    }

}
