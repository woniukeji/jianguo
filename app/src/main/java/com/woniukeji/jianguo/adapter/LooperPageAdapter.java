package com.woniukeji.jianguo.adapter;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.woniukeji.jianguo.entity.CityBannerEntity;
import com.woniukeji.jianguo.listener.PageClickListener;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/8/26.
 */

    public class LooperPageAdapter extends PagerAdapter {

        private final Random random = new Random();
        private List<CityBannerEntity.ListTBannerEntity> mBannerss;
        public LooperPageAdapter() {
        }
        private PageClickListener mPageClickListener;

    public LooperPageAdapter(PageClickListener pageClickListener, List<CityBannerEntity.ListTBannerEntity> banners) {
            mBannerss=banners;
            mPageClickListener=pageClickListener;
        }

        @Override public int getCount() {
            return mBannerss.size();
        }

        @Override public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView((View) object);
        }

        @Override public Object instantiateItem(ViewGroup view, final int position) {
            ImageView imageView=new ImageView(view.getContext());
            view.addView(imageView, RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            Glide.with(view.getContext())
                    .load(mBannerss.get(position).getImage())
                    .centerCrop()
                    .into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPageClickListener.onPageClickListener(mBannerss.get(position).getUrl());
                }
            });

            return imageView;
        }


}
