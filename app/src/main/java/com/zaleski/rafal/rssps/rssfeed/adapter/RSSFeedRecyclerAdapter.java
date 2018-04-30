package com.zaleski.rafal.rssps.rssfeed.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zaleski.rafal.rssps.R;
import com.zaleski.rafal.rssps.model.RSSItemModel;

import java.util.Collections;
import java.util.List;

public class RSSFeedRecyclerAdapter extends RecyclerView.Adapter<RSSFeedViewHolder> {

    private LayoutInflater layoutInflater;

    private RSSItemClickListener rssItemClickListener;

    private List<RSSItemModel> items = Collections.emptyList();

    public RSSFeedRecyclerAdapter(Context context, RSSItemClickListener rssItemClickListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.rssItemClickListener = rssItemClickListener;
    }

    @NonNull
    @Override
    public RSSFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_rssps, parent, false);
        return new RSSFeedViewHolder(view, rssItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RSSFeedViewHolder holder, int position) {
        RSSItemModel rssItemModel = items.get(position);
        holder.bind(rssItemModel);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public void addItems(List<RSSItemModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}

