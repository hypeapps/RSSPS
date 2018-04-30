package com.zaleski.rafal.rssps.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class RSSModel {

    @Element(name = "channel")
    private RSSChannelModel rssChannelModel;

    @Attribute(required = false)
    private Double version;

    public RSSChannelModel getRssChannelModel() {
        return rssChannelModel;
    }

    public Double getVersion() {
        return version;
    }
}
