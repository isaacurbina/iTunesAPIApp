package com.mac.isaac.itunesapiapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mac.isaac.itunesapiapp.R;
import com.mac.isaac.itunesapiapp.pojos.Result;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ResultViewHolder>  {

    List<Result> results;
    Context context;

    public RVAdapter(Context context, List<Result> results){
        this.context = context;
        this.results = results;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item, parent, false);
        ResultViewHolder cvh = new ResultViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int i) {
        holder.cv_song.setText(results.get(i).getTrackName());
        holder.cv_artist.setText(results.get(i).getArtistName());
        holder.cv_collection.setText(results.get(i).getCollectionName());
        holder.cv_genre.setText(results.get(i).getPrimaryGenreName());
        holder.cv_price.setText(results.get(i).getTrackPrice().toString());
        Glide.with(context)
                .load(results.get(i).getArtworkUrl100())
                .into(holder.cv_image);
        //Log.i("GLIDE", results.get(i).getArtworkUrl100());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void addResult(Result result) {
        results.add(result);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addResultList(List<Result> list) {
        if (results == null)
            results = list;
        else
            results.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {
        CardView card_view;
        ImageView cv_image;
        TextView cv_song;
        TextView cv_artist;
        TextView cv_collection;
        TextView cv_genre;
        TextView cv_price;

        ResultViewHolder(View itemView) {
            super(itemView);
            card_view = (CardView)itemView.findViewById(R.id.card_view);
            cv_image = (ImageView)itemView.findViewById(R.id.cv_image);
            cv_song = (TextView)itemView.findViewById(R.id.cv_song);
            cv_artist = (TextView)itemView.findViewById(R.id.cv_artist);
            cv_collection = (TextView)itemView.findViewById(R.id.cv_collection);
            cv_genre = (TextView)itemView.findViewById(R.id.cv_genre);
            cv_price = (TextView)itemView.findViewById(R.id.cv_price);
        }

    }

}
