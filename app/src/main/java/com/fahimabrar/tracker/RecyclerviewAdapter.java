package com.fahimabrar.tracker;

/**
 * Created by FAHIM on 7/5/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by csa on 3/3/2017.
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyHolder> {

    List<DataModel> recyclerdata;
    Context context;

    public RecyclerviewAdapter(List<DataModel> recyclerdata, Context context) {
        this.recyclerdata = recyclerdata;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view,parent,false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;

    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        DataModel dataModel = recyclerdata.get(position);
        holder.name.setText(dataModel.getTitle());
        holder.id.setText(dataModel.getId());
        Picasso.with(context)
                .load(dataModel.getImage())
                .error( R.drawable.error_image)
                .placeholder( R.drawable.progressbar )
                .into(holder.imageView);
        /*int x[] = {R.color.matColor1,R.color.matColor2,R.color.matColor3,R.color.matColor5,R.color.matColor4,R.color.matColor6,R.color.matColor7,R.color.matColor8,R.color.matColor9,R.color.matColor10};
        int i=0;
        for(int j=0;j<getItemCount();j++){
            if(i==10)
                i=0;
            if(position==j){
                holder.cardView.setBackgroundColor(context.getResources().getColor(x[i]));
                //holder.relativeLayout.setBackgroundColor(context.getResources().getColor(x[i]));
                //System.out.println("color2 "+x[j]);
            }
            i++;
        }*/

    }

    @Override
    public int getItemCount() {
        return recyclerdata.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView name, id;
        ImageView imageView;
        CardView cardView;
        View view;
        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            id = (TextView) itemView.findViewById(R.id.id);
            imageView = itemView.findViewById(R.id.profile_image);
            cardView = itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent = new Intent(context,StudentInfoActivity.class);
                    intent.putExtra("flag","card");
                    intent.putExtra("id",id.getText());
                    context.startActivity(intent);
                }
            });

        }
    }

}