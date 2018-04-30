package com.zaleski.rafal.rssps.rssfeed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zaleski.rafal.rssps.R;
import com.zaleski.rafal.rssps.model.RSSItemModel;
import com.zaleski.rafal.rssps.rssfeed.adapter.RSSFeedRecyclerAdapter;
import com.zaleski.rafal.rssps.rssfeed.adapter.RSSItemClickListener;

import java.util.List;

public class RSSFeedActivity extends AppCompatActivity implements RSSFeedView, RSSItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private final RSSFeedPresenter presenter = new RSSFeedPresenter();

    private RSSFeedRecyclerAdapter recyclerAdapter;

    private Snackbar errorSnackbar;

    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_feed);
        recyclerView = findViewById(R.id.rss_feed_recycler_view);
        swipeRefreshLayout = findViewById(R.id.main_swipe_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        presenter.onAttachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetachView();
    }

    @Override
    public void initRecyclerAdapter() {
        recyclerAdapter = new RSSFeedRecyclerAdapter(this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.decorator_recycler_item));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void setRecyclerItems(List<RSSItemModel> items) {
        recyclerAdapter.addItems(items);
    }

    @Override
    public void onItemClick(RSSItemModel rssItemModel) {
        presenter.onItemClick(rssItemModel);
    }

    @Override
    public void stopRefreshing() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        presenter.onRefresh();
    }

    @Override
    public void showErrorSnackbar() {
        errorSnackbar = Snackbar.make(findViewById(R.id.main_swipe_to_refresh),
                R.string.snackbar_error_message,
                Snackbar.LENGTH_INDEFINITE);
        errorSnackbar.setAction(R.string.snack_bar_error_action, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRefresh();
                errorSnackbar.dismiss();
            }
        });
        errorSnackbar.show();
    }

    @Override
    public void dissmisErrorSnackbar() {
        if (errorSnackbar != null && errorSnackbar.isShown()) {
            errorSnackbar.dismiss();
        }
    }

    @Override
    public void intentToBrowser(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}
