package com.zaleski.rafal.rssps.network.rss;

import com.zaleski.rafal.rssps.model.RSSModel;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface PortalSpozywczyRSS {

    @GET("portalspozywczy.xml")
    Single<RSSModel> getItems();
}
