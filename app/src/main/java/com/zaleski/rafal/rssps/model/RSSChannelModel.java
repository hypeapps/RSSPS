package com.zaleski.rafal.rssps.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "channel", strict = false)
public class RSSChannelModel {

    @ElementList(name = "item", inline = true)
    private List<RSSItemModel> items;

    public List<RSSItemModel> getItems() {
        return items;
    }

}
