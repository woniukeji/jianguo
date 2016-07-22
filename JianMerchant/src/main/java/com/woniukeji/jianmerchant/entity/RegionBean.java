package com.woniukeji.jianmerchant.entity;

/**
 * Created by Administrator on 2016/7/21.
 */
public class RegionBean {
    String region;
    boolean isSelect = false;

    public RegionBean(String region) {
        this.region = region;
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
