package com.woniukeji.jianguo.main;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.entity.PushMessage;
import com.woniukeji.jianguo.utils.CropCircleTransfermation;
import com.woniukeji.jianguo.utils.DateUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PushMessageAdapter extends RecyclerView.Adapter<PushMessageAdapter.ViewHolder> {

    private final List<PushMessage.DataEntity.ListTPushEntity> mValues;
    private final Context mContext;
    public static final int NORMAL = 1;
    public static final int IS_FOOTER = 2;



    private AnimationDrawable mAnimationDrawable;
    private boolean isFooterChange = false;

    public PushMessageAdapter(List<PushMessage.DataEntity.ListTPushEntity> items, Context context) {
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
                View VoteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.push_msg_item, parent, false);
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
            final PushMessage.DataEntity.ListTPushEntity message = mValues.get(position);

         holder.tvTitle.setText(message.getTitle());
            holder.tvDate.setText(message.getTime());
            holder.tvContent.setText("消息详情："+message.getContent());
            if (message.getType()==0){
                //报名
                holder.tvPs.setText("备注：请到\"我的—兼职管理\"中查看详情");
            }else{
                holder.tvPs.setText("备注：请到\"我的—我的钱包\"中查看详情");
            }






            //等待数据设置
            // 1=月结，2=周结，3=日结，4=小时结
//            if (job.getTerm() == 0) {
//                holder.tvPayMethod.setText("月结");
//                holder.tvWages.setText(job.getMoney() + "/月");
//            } else if (job.getTerm() == 1) {
//                holder.tvPayMethod.setText("周结");
//                holder.tvWages.setText(job.getMoney() + "/周");
//            } else if (job.getTerm() == 2) {
//                holder.tvPayMethod.setText("日结");
//                holder.tvWages.setText(job.getMoney() + "/日");
//            } else {
//                holder.tvPayMethod.setText("小时结");
//                holder.tvWages.setText(job.getMoney() + "/小时");
//            }
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


//            Picasso.with(mContext).load(message.getJob_image())
//                    .placeholder(R.mipmap.icon_head_defult)
//                    .error(R.mipmap.icon_head_defult)
//                    .transform(new CropCircleTransfermation())
//                    .into(holder.userHead);
//            holder.tvWallteWages.setText("+" + message.getReal_money());
//            holder.businessName.setText(message.getJob_name());
//            if (job.getRemarks().equals("") || message.getRemarks() == null || job.getRemarks().equals("null")) {
//                holder.tvContentRemark.setText("无备注信息");
//            } else {
//                holder.tvContentRemark.setText(job.getRemarks());
//            }
//            holder.tvWorkDate.setText(DateUtils.getTime(Long.valueOf(job.getJob_start()), Long.valueOf(job.getJob_stop())));

//            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//                    final SweetAlertDialog dialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
//                    dialog.setTitleText("确定要删除该收藏?")
//                            .setConfirmText("确定")
//                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                    AttentionCollectionEvent event = new AttentionCollectionEvent();
////                                    event.listTJob = job;
//                                    EventBus.getDefault().post(event);
//                                    dialog.dismiss();
//                                }
//                            })
//                            .setCancelText("取消")
//                            .show();
//                    return false;
//                }
//            });
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

        @InjectView(R.id.tv_title) TextView tvTitle;
        @InjectView(R.id.tv_date) TextView tvDate;
        @InjectView(R.id.tv_content) TextView tvContent;
        @InjectView(R.id.tv_ps) TextView tvPs;
        @InjectView(R.id.rl_job) LinearLayout rlJob;
        private ImageView animLoading;
        private TextView loading;

        public ViewHolder(View view, int type) {
            super(view);

            switch (type) {
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