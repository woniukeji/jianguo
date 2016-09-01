package com.woniukeji.jianguo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by Administrator on 2016/8/26.
 */

    public class PageAdapter extends PagerAdapter {

        private final Random random = new Random();
        private int mSize;
        private int[] mDrawables;
        public PageAdapter() {
            mSize = 5;
        }

        public PageAdapter(int count,int[] drawables) {
            mSize = count;
            mDrawables=drawables;
        }

        @Override public int getCount() {
            return mSize;
        }

        @Override public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView((View) object);
        }

        @Override public Object instantiateItem(ViewGroup view, int position) {
            ImageView imageView=new ImageView(view.getContext());
            imageView.setImageResource(mDrawables[position]);
//            textView.setText(String.valueOf(position + 1));
//            textView.setBackgroundColor(0xff000000 | random.nextInt(0x00ffffff));
//            textView.setGravity(Gravity.CENTER);
//            textView.setTextColor(Color.WHITE);
//            textView.setTextSize(48);
            view.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            return imageView;
        }

        public void addItem() {
            mSize++;
            notifyDataSetChanged();
        }

        public void removeItem() {
            mSize--;
            mSize = mSize < 0 ? 0 : mSize;

            notifyDataSetChanged();
        }
}
