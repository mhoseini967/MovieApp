package ir.mohamadhoseini.movieapp.adapters;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.mohamadhoseini.movieapp.R;
import ir.mohamadhoseini.movieapp.models.MediaCollection;
import ir.mohamadhoseini.movieapp.models.MediaCollectionType;
import ir.mohamadhoseini.movieapp.models.MovieCollection;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private final Activity mContext;
    private List<MediaCollection> list ;

    public CardAdapter(List<MediaCollection> data_list_card, Activity context) {
        mContext = context;
        this.list = data_list_card;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View v = null;

        // add multiply  type of viewholder
        switch (viewType) {
            case MediaCollectionType.COLLECTION_MOVIE_HORIZONTAL:
                v = LayoutInflater.from(mContext).inflate(R.layout.row_movie_colections, parent, false);
                break;
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //Use Classe Binder for Bind ViewHolders by Type
        switch (list.get(position).getType()){
            case MediaCollectionType.COLLECTION_MOVIE_HORIZONTAL:
                new MoveCollectionBinder(holder.layout,mContext , list.get(position)).init();
                break;

        }

            //animate(holder);
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(int position, MovieCollection data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    public void remove(MovieCollection data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());

    }

    public void updateCardList(List<MovieCollection> newlist) {
        list.clear();
        list.addAll(newlist);
        this.notifyDataSetChanged();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {

        View layout;
        ViewHolder(View v) {
            super(v);
            layout = v.findViewById(R.id.layout_cv);

        }
    }

    public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(mContext, R.anim.scale_up);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }

}