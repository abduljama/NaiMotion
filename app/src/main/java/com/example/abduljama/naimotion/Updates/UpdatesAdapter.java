package com.example.abduljama.naimotion.Updates;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abduljama.naimotion.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by abduljama on 5/25/16.
 */
public class UpdatesAdapter extends RecyclerView.Adapter<UpdatesAdapter.MyViewHolder>  {


    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public LayoutInflater inflater;
    private Context context;

    private List<String> visibleObjects;
    private List<String> allObjects;

    List<UpdateModel> data =  Collections.emptyList();

    public UpdatesAdapter(Context context, List<UpdateModel> data) {
        inflater = LayoutInflater.from(context);
        this.data = data ;
        this.context=context;


    }
    public void setItemList(List<UpdateModel> data){
        this.data = data;
        notifyItemRangeChanged(0,data.size());

    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.d("Method One ","Oncreate  ViewHolder  has  been  called");
        View view =  inflater.inflate(R.layout.updates_layout,parent,false );
        MyViewHolder holder =  new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //      Log.d("Method two ", "OnBindViewHolder has been  Called ");
        UpdateModel current = data.get(position);
        holder.title.setText(current.getTitle());
        holder.content.setText(current.getDesc());
        holder.time.setText(current.getTime());
        holder.imageView.setImageResource(current.getImage());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, content , time  ;
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            content     = (TextView)itemView.findViewById(R.id.desc);
            imageView  = (ImageView) itemView.findViewById(R.id.image);
            time = (TextView)itemView.findViewById(R.id.timeAgo);




        }


    }
}
