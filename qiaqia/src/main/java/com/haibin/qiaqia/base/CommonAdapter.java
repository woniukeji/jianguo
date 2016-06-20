package com.haibin.qiaqia.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by invinjun on 2016/6/6.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private List<T> list;
    private LayoutInflater inflater;
    private int layoutId;
    protected Context context;
    public CommonAdapter(List<T> list, Context context, int layoutId) {
        this.list = list;
        this.context=context;
        this.inflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
    }





    public abstract void convert(ViewHolder viewHolder, T t);
}
