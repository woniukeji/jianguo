package com.woniukeji.jianmerchant.entity;

/**
 * Created by Administrator on 2016/7/21.
 */
public class RegionBean {
    String region;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    boolean isSelect = false;

    public RegionBean(String region,int id) {
        this.region = region;
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public String toString() {
        return "RegionBean{" +
                "region='" + region + '\'' +
                ", isSelect=" + isSelect +
                '}';
    }


}
