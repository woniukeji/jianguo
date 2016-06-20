package com.haibin.qiaqia.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by invinjun on 2016/6/2.
 */

public class Goods {

    @SerializedName("list_chao_commodity")
    @Expose
    private List<ListChaoCommodity> listChaoCommodity = new ArrayList<ListChaoCommodity>();

    /**
     *
     * @return
     * The listChaoCommodity
     */
    public List<ListChaoCommodity> getListChaoCommodity() {
        return listChaoCommodity;
    }

    /**
     *
     * @param listChaoCommodity
     * The list_chao_commodity
     */
    public void setListChaoCommodity(List<ListChaoCommodity> listChaoCommodity) {
        this.listChaoCommodity = listChaoCommodity;
    }

}


