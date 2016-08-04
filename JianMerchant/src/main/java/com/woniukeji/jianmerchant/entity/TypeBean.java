package com.woniukeji.jianmerchant.entity;

/**
 * Created by Administrator on 2016/7/22.
 */
public class TypeBean {
    private String type;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private boolean isSelect;
    public TypeBean(String type,int id) {

        this.type = type;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public String toString() {
        return "TypeBean{" +
                "type='" + type + '\'' +
                ", isSelect=" + isSelect +
                '}';
    }
}
