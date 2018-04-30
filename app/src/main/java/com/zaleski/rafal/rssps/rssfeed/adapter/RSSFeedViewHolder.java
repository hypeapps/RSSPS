package com.zaleski.rafal.rssps.rssfeed.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zaleski.rafal.rssps.R;
import com.zaleski.rafal.rssps.model.RSSItemModel;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

class RSSFeedViewHolder extends RecyclerView.ViewHolder {

    private TextView title;

    private TextView description;

    private TextView publishDate;

    private ImageView thumbnail;

    private RSSItemClickListener rssItemClickListener;

    private View itemView;

    RSSFeedViewHolder(View itemView, final RSSItemClickListener rssItemClickListener) {
        super(itemView);
        this.itemView = itemView;
        this.rssItemClickListener = rssItemClickListener;
        this.title = itemView.findViewById(R.id.text_view_item_rssps_title);
        this.description = itemView.findViewById(R.id.text_view_item_rssps_description);
        this.publishDate = itemView.findViewById(R.id.text_view_item_rssps_publish_date);
        this.thumbnail = itemView.findViewById(R.id.image_view_item_rssps_thumbnail);
    }

    void bind(final RSSItemModel item) {
        loadThumbnail(item.getImage());
        setOnClickListener(item);
        setDate(item.getPublishDate());
        this.title.setText(item.getTitle());
        this.description.setText(item.getDescription());
    }

    @SuppressLint("SetTextI18n")
    private void setDate(String date) {
        DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.US);
        try {
            Date dateparsed = formatter.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateparsed);
            String[] monthNames = new DateFormatSymbols(new Locale("pl")).getMonths();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", new Locale("pl"));
            this.publishDate.setText(calendar.get(Calendar.DAY_OF_MONTH) + " " +
                    monthNames[calendar.get(Calendar.MONTH)] + " " +
                    calendar.get(Calendar.YEAR) + " " + timeFormat.format(dateparsed));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setOnClickListener(final RSSItemModel item) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rssItemClickListener.onItemClick(item);
            }
        });
    }

    private void loadThumbnail(String url) {
        if (url != null) {
            thumbnail.setVisibility(View.VISIBLE);
            Glide.with(itemView)
                    .load(url)
                    .into(thumbnail);
            title.setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, itemView.getResources().getDisplayMetrics()), 0, 0, 0);
            title.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 62, itemView.getResources().getDisplayMetrics());
        } else {
            title.setPadding(0, 0, 0, 0);
            title.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            thumbnail.setVisibility(View.GONE);
        }
    }
}
