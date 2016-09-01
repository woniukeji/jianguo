package com.woniukeji.jianguo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.entity.DrawMoney;
import com.woniukeji.jianguo.activity.partjob.JobDetailActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

public class WallteOutAdapter extends RecyclerView.Adapter<WallteOutAdapter.ViewHolder> {

    private final List<DrawMoney.DataEntity.ListTUserMoneyoutEntity> mValues;
    private final Context mContext;
    public static final int NORMAL = 1;
    public static final int IS_FOOTER = 2;



    private AnimationDrawable mAnimationDrawable;
    private boolean isFooterChange = false;

    public WallteOutAdapter(List<DrawMoney.DataEntity.ListTUserMoneyoutEntity> items, Context context) {
        mValues = items;
        mContext = context;
    }


    public void setFooterChange(boolean isChange) {
        isFooterChange = isChange;
    }

    public void mmswoon(ViewHolder holder) {
        if (isFooterChange) {
            holder.loading.setText("已加载全部");
        } else {
            holder.loading.setText("已加载全部");
            holder.animLoading.setVisibility(View.GONE);
//            holder.animLoading.setBackgroundResource(R.drawable.loading_footer);
//            mAnimationDrawable = (AnimationDrawable) holder.animLoading.getBackground();
//            mAnimationDrawable.start();
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder holder = null;
        switch (viewType) {
            case NORMAL:
                View VoteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallte_out_item, parent, false);
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
        if (mValues.size() < 1) {
            holder.itemView.setVisibility(View.GONE);
        }
        if (mValues.size() == position) {
            if (mValues.size() > 4) {
                mmswoon(holder);
                holder.itemView.setVisibility(View.VISIBLE);
            }
        } else {
            final DrawMoney.DataEntity.ListTUserMoneyoutEntity job = mValues.get(position);


            //等待数据设置

            if (job.getStatus()==1){
                 if (job.getType()==0){
                     holder.tvStatus.setText("已转出到支付宝");
                 }else if(job.getType()==1){
                     holder.tvStatus.setText("已转出到银行卡");
                 }else if(job.getType()==2){
                     holder.tvStatus.setText(job.getRemarks());
                 }
            }else {
                holder.tvStatus.setText("结算中");
            }

            holder.tvOutDate.setText(job.getTime());
            holder.tvOutWages.setText("-"+job.getMoney());
//
//            holder.businessName.setText(job.getName());
//            holder.tvLocation.setText(job.getAddress());
//            String date = DateUtils.getTime(Long.valueOf(job.getStart_date()), Long.valueOf(job.getStop_date()));
//            holder.tvDate.setText(date);
//            //性别限制（0=只招女，1=只招男，2=不限男女）
//            if (job.getLimit_sex() == 0) {
//                holder.imgSex.setImageResource(R.mipmap.icon_man);
//            } else if (job.getLimit_sex() == 1) {
//                holder.imgSex.setImageResource(R.mipmap.icon_man);
//            }
//            holder.imgSex.setVisibility(View.GONE);


//            Picasso.with(mContext).load(job.getJob_image())
//                    .placeholder(R.mipmap.icon_head_defult)
//                    .error(R.mipmap.icon_head_defult)
//                    .transform(new CropCircleTransfermation())
//                    .into(holder.userHead);
//            holder.tvWallteWages.setText("+" + job.getReal_money());
//            holder.businessName.setText(job.getJob_name());
//            if (job.getRemarks().equals("") || job.getRemarks() == null || job.getRemarks().equals("null")) {
//                holder.tvContentRemark.setText("无备注信息");
//            } else {
//                holder.tvContentRemark.setText(job.getRemarks());
//            }
//            holder.tvWorkDate.setText(DateUtils.getTime(Long.valueOf(job.getJob_start()), Long.valueOf(job.getJob_stop())));
            //动画
//            float count = job.getCount();//已有人数
//            float sum = job.getSum();
//            holder.demoTv.setText(job.getCount() + "/" + job.getSum());
//            final float score = count / sum * 100;
//            AnimatorSet set = new AnimatorSet();
//            set.playTogether(
//                    ObjectAnimator.ofFloat(holder.demoMpc, "percent", 0, score / 100f)
////                    ObjectAnimator.ofFloat(holder.demoTv, "score", 0, score)
//            );
//            set.setDuration(800);
//            set.setInterpolator(new AccelerateInterpolator());
//            set.start();

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, JobDetailActivity.class);
                    intent.putExtra("job", job.getId());
//                    intent.putExtra("merchant", job.getMerchant_id());
//                    intent.putExtra("money", job.getMoney());
//                    intent.putExtra("count", job.getCount() + "/" + job.getSum());
                    mContext.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mValues.size() > 0 ? mValues.size() + 1 : 0;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == mValues.size()) {
            return IS_FOOTER;
        } else {
            return NORMAL;
        }
    }

    static

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_method_name) TextView tvMethodName;
        @BindView(R.id.tv_status) TextView tvStatus;
        @BindView(R.id.tv_content_remark) TextView tvContentRemark;
        @BindView(R.id.rl_status) RelativeLayout rlStatus;
        @BindView(R.id.tv_out_date) TextView tvOutDate;
        @BindView(R.id.rl_date) RelativeLayout rlDate;
        @BindView(R.id.tv_out_wages) TextView tvOutWages;
        @BindView(R.id.rl_top) RelativeLayout rlTop;
        @BindView(R.id.rl_job) RelativeLayout rlJob;
        private ImageView animLoading;
        private TextView loading;

        public ViewHolder(View view, int type) {
            super(view);

            switch (type) {
                case NORMAL:
                    ButterKnife.bind(this, view);
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
