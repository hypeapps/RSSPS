package com.zaleski.rafal.rssps.rssfeed;

import com.zaleski.rafal.rssps.model.RSSItemModel;
import com.zaleski.rafal.rssps.presenter.View;

import java.util.List;

interface RSSFeedView extends View {

    void initRecyclerAdapter();

    void intentToBrowser(String url);

    void setRecyclerItems(List<RSSItemModel> items);

    void showErrorSnackbar();

    void stopRefreshing();

    void dissmisErrorSnackbar();
}
