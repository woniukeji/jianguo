package com.haibin.qiaqia.entity;

import java.util.List;

/**
 * Created by cai on 2016/6/25.
 */

public class Market {

    private List<ListMarket> list_chao_class;

    public List<ListMarket> getList_chao_class() {
        return list_chao_class;
    }

    public void setList_chao_class(List<ListMarket> list_chao_class) {
        this.list_chao_class = list_chao_class;
    }

    @Override
    public String toString() {
        return "Market{" +
                "listMarkets=" + list_chao_class.toString() +
                '}';
    }
}
