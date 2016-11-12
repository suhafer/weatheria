package com.betalogika.weatheria;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.betalogika.weatheria.databinding.CardviewHomeBinding;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private ArrayList<Forecast> mDataset;
    private int[] listPosition;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardviewHomeBinding binding;

        public ViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }

        public void bind(Forecast forecast) {
            binding.setForecast(forecast);
        }
    }

    public HomeAdapter(ArrayList<Forecast> myDataset) {
        mDataset        = myDataset;
        listPosition    = new int[mDataset.size()];
    }

    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_home, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (listPosition[position] != position) {
            animate(holder);
            listPosition[position] = position;
        }
        holder.bind(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.bounce);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }
}