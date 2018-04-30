package com.zaleski.rafal.rssps.presenter;

public class Presenter<V extends View> {

    protected V view;

    protected void onAttachView(V view) {
        this.view = view;
    }

    protected void onDetachView() {
        this.view = null;
    }
}