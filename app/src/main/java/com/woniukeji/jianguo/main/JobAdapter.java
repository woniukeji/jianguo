package com.woniukeji.jianguo.main;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liulishuo.magicprogresswidget.MagicProgressCircle;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.entity.Job;
import com.woniukeji.jianguo.widget.AnimTextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {

    private final List<Job> mValues;
    private final Context mContext;
    private View mHeaderView;
    public static final int IS_HEADER = 0;
    public static final int NORMAL = 1;
    public static final int IS_FOOTER = 2;
    private AnimationDrawable mAnimationDrawable;
    private boolean isFooterChange = false;

    public JobAdapter(List<Job> items, Context context) {
        mValues = items;
        mContext = context;
    }

    public void addHeaderView(View headerView) {
        mHeaderView = headerView;
    }

    public void setFooterChange(boolean isChange) {
        isFooterChange = isChange;
    }

    public void mmswoon(ViewHolder holder) {
        if (isFooterChange) {
            holder.loading.setText("已加载全部");
        } else {
            holder.loading.setText("加载中...");
            holder.animLoading.setVisibility(View.VISIBLE);
//            holder.animLoading.setBackgroundResource(R.drawable.loading_footer);
//            mAnimationDrawable = (AnimationDrawable) holder.animLoading.getBackground();
//            mAnimationDrawable.start();
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder holder = null;
        switch (viewType) {
            case IS_HEADER:
                holder = new ViewHolder(mHeaderView, IS_HEADER);
                return holder;
            case NORMAL:
                View VoteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_jobitem, parent, false);
                holder = new ViewHolder(VoteView, NORMAL);
                return holder;

            case IS_FOOTER:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_footer, parent, false);
                holder = new ViewHolder(view, IS_FOOTER);
                return holder;

            default:
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (position == 0) {
            return;
        } else if (mValues.size() < 5) {
            holder.itemView.setVisibility(View.GONE);
        } else if (mValues.size() + 1 == position) {
            mmswoon(holder);
            holder.itemView.setVisibility(View.VISIBLE);
        } else {
            final Job job = mValues.get(position - 1);
            AnimatorSet set = new AnimatorSet();
            ObjectAnimator.ofFloat(holder.demoMpc, "percent", 0, 80 / 100f);
            set.setDuration(600);
            set.setInterpolator(new AccelerateInterpolator());
            set.start();
            //等待数据设置

        }
    }

    @Override
    public int getItemCount() {
        return mValues.size() > 0 ? mValues.size() + 2 : 0;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == mValues.size() + 1) {
            return IS_FOOTER;
        } else if (position == 0) {
            return IS_HEADER;
        } else {
            return NORMAL;
        }
    }

    static

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.user_head) ImageView userHead;
        @InjectView(R.id.business_name) TextView businessName;
        @InjectView(R.id.img_pay) ImageView imgPay;
        @InjectView(R.id.img_date) ImageView imgDate;
        @InjectView(R.id.img_local) ImageView imgLocal;
        @InjectView(R.id.tv_sex) TextView tvSex;
        @InjectView(R.id.demo_mpc) MagicProgressCircle demoMpc;
        @InjectView(R.id.demo_tv) AnimTextView demoTv;
        @InjectView(R.id.rl_progess) RelativeLayout rlProgess;
        @InjectView(R.id.tv_enroll_num) TextView tvEnrollNum;
        @InjectView(R.id.tv_wages) TextView tvWages;
        @InjectView(R.id.rl_job) RelativeLayout rlJob;
        private ImageView animLoading;
        private TextView loading;

        public ViewHolder(View view, int type) {
            super(view);

            switch (type) {
                case IS_HEADER:
                    break;
                case NORMAL:
                    ButterKnife.inject(this, view);
                    break;
                case IS_FOOTER:
                    animLoading = (ImageView) view.findViewById(R.id.anim_loading);
                    loading = (TextView) view.findViewById(R.id.tv_loading);
                    break;
            }
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }


}
