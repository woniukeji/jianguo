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

import com.bumptech.glide.Glide;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.entity.JobDetails;
import com.woniukeji.jianguo.eventbus.AttentionCollectionEvent;
import com.woniukeji.jianguo.activity.partjob.JobDetailActivity;
import com.woniukeji.jianguo.widget.CircleImageView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;;

public class AttentionAdapter extends RecyclerView.Adapter<AttentionAdapter.ViewHolder> {

    private final List<JobDetails.TMerchantEntity> mValues;
    private final Context mContext;
    public static final int NORMAL = 1;
    public static final int IS_FOOTER = 2;
    private AnimationDrawable mAnimationDrawable;
    private boolean isFooterChange = false;

    public AttentionAdapter(List<JobDetails.TMerchantEntity> items, Context context) {
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
                View VoteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_merchant, parent, false);
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mValues.size() <1) {
            holder.itemView.setVisibility(View.GONE);
        }
        if (mValues.size() == position) {
            if (mValues.size()>4){
                mmswoon(holder);
                holder.itemView.setVisibility(View.VISIBLE);
            }
        } else {
            final JobDetails.TMerchantEntity merchantEntity = mValues.get(position);

            //等待数据设置
            // 1=月结，2=周结，3=日结，4=小时结
            holder.businessName.setText(merchantEntity.getName());
            holder.tvJobCount.setText(merchantEntity.getJob_count()+"个职位在招");
            Glide.with(mContext).load(merchantEntity.getName_image())
                    .placeholder(R.mipmap.icon_head_defult)
                    .error(R.mipmap.icon_head_defult)
                    .into(holder.userHead);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, JobDetailActivity.class);
                    intent.putExtra("job", merchantEntity.getId());
//                    intent.putExtra("merchant",job.getMerchant_id());
//                    intent.putExtra("money",job.getMoney());
//                    intent.putExtra("count", job.getCount()+"/"+job.getSum());
                    mContext.startActivity(intent);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final SweetAlertDialog dialog=  new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
                    dialog.setTitleText("确定要删除该商家?")
                            .setConfirmText("确定")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    AttentionCollectionEvent event=new AttentionCollectionEvent();
                                    event.tMerchantEntity=merchantEntity;
                                    event.position=position;
                                    EventBus.getDefault().post(event);
                                    dialog.dismissWithAnimation();
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

        @BindView(R.id.user_head) CircleImageView userHead;
        @BindView(R.id.business_name) TextView businessName;
        @BindView(R.id.tv_job_count) TextView tvJobCount;
//        @BindView(R.id.tv_enroll_num) TextView tvEnrollNum;
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
