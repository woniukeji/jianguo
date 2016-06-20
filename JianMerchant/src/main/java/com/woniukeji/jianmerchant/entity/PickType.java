package com.woniukeji.jianmerchant.entity;

import java.io.Serializable;

/**
 * Created by invinjun on 2016/3/30.
 * 用于选择器需要返回对应id和string的时候例如城市 选择海南返回 pickType对象，包括海南name和id
 */
public class PickType implements Serializable{
    private String pickName;
    private String PickId;

    public String getPickName() {
        return pickName;
    }

    public void setPickName(String pickName) {
        this.pickName = pickName;
    }

    public String getPickId() {
        return PickId;
    }

    public void setPickId(String pickId) {
        PickId = pickId;
    }
}
