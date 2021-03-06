package com.woniukeji.jianguo.main;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liulishuo.magicprogresswidget.MagicProgressCircle;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.entity.Jobs;
import com.woniukeji.jianguo.partjob.JobDetailActivity;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.widget.AnimTextView;
import com.woniukeji.jianguo.widget.CircleImageView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

public class HomeJobAdapter extends RecyclerView.Adapter<HomeJobAdapter.ViewHolder> {

    private final List<Jobs.ListTJobEntity> mValues;
    private final Context mContext;
    private View mHeaderView;
    public static final int IS_HEADER = 0;
    public static final int NORMAL = 1;
    public static final int IS_FOOTER = 2;
    private AnimationDrawable mAnimationDrawable;
    private boolean isFooterChange = false;

    public HomeJobAdapter(List<Jobs.ListTJobEntity> items, Context context) {
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
            holder.loading.setText("已加载全部 ");
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
        }
        if (mValues.size() <1) {
            holder.itemView.setVisibility(View.GONE);
        }
        if (mValues.size() + 1 == position) {
            if (mValues.size()>4){
                mmswoon(holder);
                holder.itemView.setVisibility(View.VISIBLE);
            }
        } else {
            final Jobs.ListTJobEntity job = mValues.get(position-1);
            String money = job.getMoney();
            if(money.indexOf(".") > 0){
                //正则表达
                money = money.replaceAll("0+?$", "");//去掉后面无用的零
                money = money.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
            }
            // 期限（0=月结，1=周结，2=日结，3=小时结，4=次，5=义工
            String type="";
            if (job.getTerm()==0){
                holder.tvWages.setText(money+"/月");
                type="元/月";
            }else if(job.getTerm()==1){
                holder.tvWages.setText(money+"元/周");
                type="元/周";
            }else if(job.getTerm()==2){
                holder.tvWages.setText(money+"元/日");
                type="元/日";
            }else if(job.getTerm()==3){
                holder.tvWages.setText(money+"元/时");
                type="/时";
            }else if(job.getTerm()==4){
                holder.tvWages.setText(money+"元/次");
                type="元/次";
            }else if(job.getTerm()==5){
                holder.tvWages.setText("义工");
                type="义工";
            }else if(job.getTerm()==6){
                holder.tvWages.setText("面议");
                type="面议";
            }
            //结算方式（0=月结，1=周结，2=日结，3=旅行）
            if (job.getMode()==0){
                holder.tvPayMethod.setText("月结");
            }else if(job.getMode()==1){
                holder.tvPayMethod.setText("周结");
            }else if(job.getMode()==2){
                holder.tvPayMethod.setText("日结");
            }else {
                holder.tvPayMethod.setText("旅行");
            }


            holder.businessName.setText(job.getName());
            holder.tvLocation.setText(job.getAddress());
            String date= DateUtils.getTime(Long.valueOf(job.getStart_date()),Long.valueOf(job.getStop_date()));
            holder.tvDate.setText(date);

            if (job.getMax()==1){
                holder.imgType.setVisibility(View.VISIBLE);
                holder.imgType.setImageResource(R.mipmap.cq);
            }else{
                holder.imgType.setVisibility(View.GONE);
            }

            if (job.getStatus()!=0){
                holder.imgPast.setVisibility(View.VISIBLE);
            }else {
                holder.imgPast.setVisibility(View.GONE);
            }
            //性别限制（0=只招女，1=只招男，2=不限男女）
            if (job.getLimit_sex()==0||job.getLimit_sex()==30){
                holder.imgSex.setImageResource(R.mipmap.icon_woman);
            }else if(job.getLimit_sex()==1||job.getLimit_sex()==31){
                holder.imgSex.setImageResource(R.mipmap.icon_man);
            }else
                holder.imgSex.setImageResource(R.mipmap.icon_xingbie);


            Glide.with(mContext).load(job.getName_image())
                    .placeholder(R.mipmap.icon_head_defult)
                    .error(R.mipmap.icon_head_defult)
//                    .transform(new CropCircleTransfermation())
                    .into(holder.userHead);

            //动画
            float count=job.getCount();//已有人数
            float sum=job.getSum();
            holder.demoTv.setText(job.getCount()+"/"+job.getSum() );
            if (job.getCount()>=job.getSum()){
                holder.demoTv.setText("已招满");
            }
            final float score = count/sum*100;
            AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    ObjectAnimator.ofFloat(holder.demoMpc, "percent", 0, score / 100f)
//                    ObjectAnimator.ofFloat(holder.demoTv, "score", 0, score)
            );
            set.setDuration(800);
            set.setInterpolator(new AccelerateInterpolator());
            set.start();
            if (type.equals("面议")||type.equals("义工")){

            }else {
                type=money+ type;
            }
            final String finalType = type;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, JobDetailActivity.class);
                    intent.putExtra("job",job.getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size() > 0 ? mValues.size() + 2 : 2;
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

        @BindView(R.id.user_head) CircleImageView userHead;
        @BindView(R.id.business_name) TextView businessName;
        @BindView(R.id.img_pay) ImageView imgPay;
        @BindView(R.id.img_date) ImageView imgDate;
        @BindView(R.id.img_local) ImageView imgLocal;
        @BindView(R.id.img_sex) ImageView imgSex;
        @BindView(R.id.img_type) ImageView imgType;
        @BindView(R.id.img_pass) ImageView imgPast;
        @BindView(R.id.demo_mpc) MagicProgressCircle demoMpc;
        @BindView(R.id.demo_tv) AnimTextView demoTv;
        @BindView(R.id.rl_progess) RelativeLayout rlProgess;
        @BindView(R.id.tv_enroll_num) TextView tvEnrollNum;
        @BindView(R.id.tv_wages) TextView tvWages;
        @BindView(R.id.rl_job) RelativeLayout rlJob;
        @BindView(R.id.tv_pay_method) TextView tvPayMethod;
        @BindView(R.id.tv_date) TextView tvDate;
        @BindView(R.id.tv_location) TextView tvLocation;
        private ImageView animLoading;
        private TextView loading;

        public ViewHolder(View view, int type) {
            super(view);

            switch (type) {
                case IS_HEADER:
                    break;
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
