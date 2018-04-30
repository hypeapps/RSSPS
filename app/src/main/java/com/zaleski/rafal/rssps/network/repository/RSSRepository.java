package com.zaleski.rafal.rssps.network.repository;

import com.zaleski.rafal.rssps.model.RSSItemModel;

import java.util.List;

import io.reactivex.Single;

public interface RSSRepository {

    Single<List<RSSItemModel>> getRssItems();
}
