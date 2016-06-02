package com.example.abduljama.naimotion.Updates;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abduljama.naimotion.R;
import com.example.abduljama.naimotion.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdatesFragment extends Fragment {


    RecyclerView recyclerView;
    UpdatesAdapter adapter;

    public UpdatesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View  x = inflater.inflate(R.layout.fragment_updates, container, false);
        recyclerView = (RecyclerView)x.findViewById(R.id.rv1);
        adapter = new UpdatesAdapter(getActivity(),getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SpaceItemDecoration decoration = new SpaceItemDecoration(16);
        recyclerView.addItemDecoration(decoration);
        return x ;
    }

    private List<UpdateModel> getData() {
        List<UpdateModel> data = new ArrayList<>() ;
        int [] images  ={  R.drawable.riots , R.drawable.marathon , R.drawable.mombasa };
        String [] titles = { " Riots in the CBD ",
                " Uhuru Highway closed due to Marathon",
                " Truck overturns  along Mombasa Road" };
        String  time [] = {" 2 mins  Ago",  "3hrs Ago" , "2 days Ago"};
         for ( int  i  = 0 ;  i  < titles.length; i++ ) {
         UpdateModel current  =  new UpdateModel() ;
            current.title =  titles[i];
            current.image= images[i];
            current.setDesc(getResources().getString(R.string.sampletext));
            current.time= time[i];
            data.add(current);
        }
        return data;

    }

}
