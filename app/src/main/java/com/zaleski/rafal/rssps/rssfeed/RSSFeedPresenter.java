package com.zaleski.rafal.rssps.rssfeed;

import com.zaleski.rafal.rssps.model.RSSItemModel;
import com.zaleski.rafal.rssps.network.RSSPSDataSource;
import com.zaleski.rafal.rssps.presenter.Presenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

class RSSFeedPresenter extends Presenter<RSSFeedView> {

    private RSSPSDataSource dataSource;

    private CompositeDisposable disposables = new CompositeDisposable();

    RSSFeedPresenter() {
        dataSource = new RSSPSDataSource();
    }

    @Override
    protected void onAttachView(RSSFeedView view) {
        super.onAttachView(view);
        this.view.initRecyclerAdapter();
        getRssFromDataSource();
    }

    @Override
    protected void onDetachView() {
        super.onDetachView();
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    public void onRefresh() {
        getRssFromDataSource();
    }

    public void onItemClick(RSSItemModel rssItemModel) {
        this.view.intentToBrowser(rssItemModel.getLink());
    }

    private void getRssFromDataSource() {
        disposables.add(dataSource.getRssItems()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RSSDataSourceObserver())
        );
    }

    private class RSSDataSourceObserver extends DisposableSingleObserver<List<RSSItemModel>> {
        @Override
        public void onSuccess(List<RSSItemModel> items) {
            RSSFeedPresenter.this.view.setRecyclerItems(items);
            RSSFeedPresenter.this.view.stopRefreshing();
            RSSFeedPresenter.this.view.dissmisErrorSnackbar();
        }

        @Override
        public void onError(Throwable e) {
            RSSFeedPresenter.this.view.stopRefreshing();
            RSSFeedPresenter.this.view.showErrorSnackbar();
        }
    }
}
