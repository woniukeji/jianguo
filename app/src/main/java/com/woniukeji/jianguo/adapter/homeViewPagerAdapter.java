package com.woniukeji.jianguo.adapter;

/**
 * Created by invinjun on 2016/3/7.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 轮播图的adapter
 */
class homeViewPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<View> imageViewList;
    homeViewPagerAdapter(Context mContext, ArrayList<View> list){
        context=mContext;
        imageViewList=list;
    }

    @Override
    public int getCount() {
        return imageViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        if (view == object) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View iv = imageViewList.get(position);
//            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(context, Recommend.class);
//                context.startActivity(intent);
            }
        });
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
