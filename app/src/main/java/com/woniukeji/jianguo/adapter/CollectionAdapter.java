package com.woniukeji.jianguo.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
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
import com.woniukeji.jianguo.eventbus.AttentionCollectionEvent;
import com.woniukeji.jianguo.activity.partjob.JobDetailActivity;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.widget.AnimTextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {

    private final List<Jobs.ListTJobEntity> mValues;
    private final Context mContext;
    public static final int NORMAL = 1;
    public static final int IS_FOOTER = 2;
    private AnimationDrawable mAnimationDrawable;
    private boolean isFooterChange = false;

    public CollectionAdapter(List<Jobs.ListTJobEntity> items, Context context) {
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
        if (mValues.size() <1) {
            holder.itemView.setVisibility(View.GONE);
        }
        if (mValues.size() == position) {
            if (mValues.size()>4){
                mmswoon(holder);
                holder.itemView.setVisibility(View.VISIBLE);
            }
        } else {
            final Jobs.ListTJobEntity job = mValues.get(position);
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
                type="/月";
            }else if(job.getTerm()==1){
                holder.tvWages.setText(money+"/周");
                type="/周";
            }else if(job.getTerm()==2){
                holder.tvWages.setText(money+"/日");
                type="/日";
            }else if(job.getTerm()==3){
                holder.tvWages.setText(money+"/时");
                type="/时";
            }else if(job.getTerm()==4){
                holder.tvWages.setText(money+"/次");
                type="/次";
            }else if(job.getTerm()==5){
                holder.tvWages.setText("义工");
                type="义工";
            }else if(job.getTerm()==6){
                holder.tvWages.setText("面议");
                type="面议";
            }
            //等待数据设置
           // 1=月结，2=周结，3=日结，4=小时结
            //结算方式（0=月结，1=周结，2=日结，3=旅行）
            if (job.getMode()==0){
                holder.tvPayMethod.setText("月结");
            }else if(job.getMode()==1){
                holder.tvPayMethod.setText("周结");
            }else if(job.getMode()==2){
                holder.tvPayMethod.setText("日结");
            }else if(job.getMode()==3){
                holder.tvPayMethod.setText("旅行");
            }else if(job.getMode()==4){
                holder.tvPayMethod.setText("完工结");
            }
            if (job.getMax()==1){
                holder.imgType.setVisibility(View.VISIBLE);
                holder.imgType.setImageResource(R.mipmap.cq);
            }else{
                holder.imgType.setVisibility(View.GONE);
            }

            holder.businessName.setText(job.getName());
            holder.tvLocation.setText(job.getAddress());
            String date=DateUtils.getTime(Long.valueOf(job.getStart_date()),Long.valueOf(job.getStop_date()));
            holder.tvDate.setText(date);
            //性别限制（0=只招女，1=只招男，2=不限男女）
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
                    .into(holder.userHead);
            holder.tvLook.setText(job.getLook()*7+"");
            int count=job.getCount();//已有人数
            int sum=job.getSum();//总人数
            if (sum<=10){
                sum=sum+5;
            }else if(sum>10){
                sum= (int) (sum*1.4);
            }
            if (sum-count>0) {
                if (job.getStatus()==0){
                    holder.imgStatus.setImageResource(R.mipmap.start);
                    holder.tvStart.setText("正在招募");
                    holder.tvSurplus.setVisibility(View.VISIBLE);
                    SpannableStringBuilder spannableStringBuilder1 = new SpannableStringBuilder("仅剩"+(sum-count)+"个名额");
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);
                    spannableStringBuilder1.setSpan(foregroundColorSpan, 2, spannableStringBuilder1.length()-3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    holder.tvSurplus.setText(spannableStringBuilder1);
                }else {
                    holder.imgStatus.setImageResource(R.mipmap.finish);
                    holder.tvStart.setText("已经招满");
                    holder.tvSurplus.setVisibility(View.GONE);
                }
            }else {
                holder.imgStatus.setBackgroundResource(R.mipmap.finish);
                holder.tvStart.setText("已经招满");
                holder.tvSurplus.setVisibility(View.GONE);
            }
            if (type.equals("面议")||type.equals("义工")){
            }else {
//                type=Integer.valueOf(String.valueOf(job.getMoney()))+ type;
            }
            final String finalType = type;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, JobDetailActivity.class);
//                    intent.putExtra("job",job.getId());
//                    intent.putExtra("merchant",job.getMerchant_id());
//                    intent.putExtra("money",job.getMoney());
//                    intent.putExtra("count", job.getCount()+"/"+job.getSum());
                    intent.putExtra("job",job.getId());
                    intent.putExtra("jobbean",job);
                    intent.putExtra("merchant",job.getMerchant_id());
                    intent.putExtra("money", finalType);
                    intent.putExtra("count", job.getCount()+"/"+job.getSum());
                    intent.putExtra("mername", job.getName());
                    mContext.startActivity(intent);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final SweetAlertDialog dialog=  new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
                    dialog.setTitleText("确定要删除该收藏?")
                            .setConfirmText("确定")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    AttentionCollectionEvent event=new AttentionCollectionEvent();
                                    event.listTJob=job;
                                    EventBus.getDefault().post(event);
                                    dialog.dismiss();
                                }
                            })
                            .setCancelText("取消")
                            .show();
                    return false;
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

        @BindView(R.id.user_head) ImageView userHead;
        @BindView(R.id.business_name) TextView businessName;
        @BindView(R.id.img_pay) ImageView imgPay;
        @BindView(R.id.img_date) ImageView imgDate;
        @BindView(R.id.img_local) ImageView imgLocal;
        @BindView(R.id.img_sex) ImageView imgSex;
        @BindView(R.id.img_type) ImageView imgType;
        @BindView(R.id.rl_progess) RelativeLayout rlProgess;
        @BindView(R.id.tv_look) TextView tvLook;
//        @BindView(R.id.tv_enroll_num) TextView tvEnrollNum;
        @BindView(R.id.imgv_emoji) ImageView imgStatus;
        @BindView(R.id.tv_surplus) TextView tvSurplus;
        @BindView(R.id.tv_start) TextView tvStart;
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
