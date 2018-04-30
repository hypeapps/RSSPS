package com.zaleski.rafal.rssps.network;

import com.zaleski.rafal.rssps.model.RSSItemModel;
import com.zaleski.rafal.rssps.model.RSSModel;
import com.zaleski.rafal.rssps.network.repository.RSSRepository;
import com.zaleski.rafal.rssps.network.rss.PortalSpozywczyRSS;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RSSPSDataSource implements RSSRepository {

    private PortalSpozywczyRSS portalSpozywczyRSS;

    public RSSPSDataSource() {
        portalSpozywczyRSS = new Retrofit.Builder()
                .baseUrl("http://www.portalspozywczy.pl/rss/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
                .create(PortalSpozywczyRSS.class);
    }

    @Override
    public Single<List<RSSItemModel>> getRssItems() {
        return portalSpozywczyRSS.getItems()
                .map(new Function<RSSModel, List<RSSItemModel>>() {
                    @Override
                    public List<RSSItemModel> apply(RSSModel rssModel) throws Exception {
                        return rssModel.getRssChannelModel().getItems();
                    }
                });
    }
}
