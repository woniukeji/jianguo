package com.woniukeji.jianguo.partjob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.entity.SpinnerEntity;

import java.util.List;

/**
 * Created by invinjun on 2016/4/25.
 */
public class MyAdapter extends BaseAdapter {
    private List<SpinnerEntity> mList;
    private Context mContext;

    public MyAdapter(Context pContext, List<SpinnerEntity> pList) {
        this.mContext = pContext;
        this.mList = pList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    /**
     * 下面是重要代码
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater= LayoutInflater.from(mContext);
        convertView=_LayoutInflater.inflate(R.layout.spinner_list_item, null);
        if(convertView!=null) {
            TextView _TextView1=(TextView)convertView.findViewById(R.id.tv_spinner_item);
            _TextView1.setText(mList.get(position).getName());
        }
        return convertView;
    }
}
