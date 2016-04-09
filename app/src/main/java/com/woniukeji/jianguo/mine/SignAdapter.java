package com.woniukeji.jianguo.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.entity.Jobs;
import com.woniukeji.jianguo.partjob.JobDetailActivity;
import com.woniukeji.jianguo.utils.CropCircleTransfermation;
import com.woniukeji.jianguo.utils.DateUtils;

import java.security.PublicKey;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignAdapter extends RecyclerView.Adapter<SignAdapter.ViewHolder> {

    private final List<Jobs.ListTJobEntity> mValues;
    private final Context mContext;



    private int mType;
    public static final int NORMAL = 1;
    public static final int IS_FOOTER = 2;

    private AnimationDrawable mAnimationDrawable;
    private boolean isFooterChange = false;
    private RecyCallBack mCallBack;

    public SignAdapter(List<Jobs.ListTJobEntity> items, Context context, int type, RecyCallBack callBack) {
        mValues = items;
        mContext = context;
        mType = type;
        mCallBack = callBack;
    }


    public interface RecyCallBack {
        void RecyOnClick(int login_id, int admit, int position);
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
                View VoteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sign_jobitem, parent, false);
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
        if (mValues.size() < 1) {
            holder.itemView.setVisibility(View.GONE);
        }
        if (mValues.size() == position) {
            if (mValues.size() > 4) {
                mmswoon(holder);
                holder.itemView.setVisibility(View.VISIBLE);
            }
        } else {
            final Jobs.ListTJobEntity jobEntity = mValues.get(position);
//            if (mType == 0) {
//                holder.tvTitle.setText(job.getName());
//                holder.llMuban.setVisibility(View.GONE);
//                holder.imgHis.setVisibility(View.VISIBLE);
//            } else {
//                holder.tvTitle.setText(job.getModel_name());
//                holder.llMuban.setVisibility(View.VISIBLE);
//                holder.imgHis.setVisibility(View.GONE);
//            }

//            String date = job.getRegedit_time().substring(5, 11);

            String date = DateUtils.getTime(Long.valueOf(jobEntity.getStart_date()), Long.valueOf(jobEntity.getStop_date()));
            holder.tvWorkDate.setText(date);
            holder.businessName.setText(jobEntity.getName());
            if (jobEntity.getTerm()==0){
                holder.tvJobWages.setText(jobEntity.getMoney()+"/月");
            }else if (jobEntity.getTerm()==1){
                holder.tvJobWages.setText(jobEntity.getMoney()+"/周");
            }else if (jobEntity.getTerm()==2){
                holder.tvJobWages.setText(jobEntity.getMoney()+"/日");
            }else if (jobEntity.getTerm()==3){
                holder.tvJobWages.setText(jobEntity.getMoney()+"/时");
            }else if (jobEntity.getTerm()==4){
                holder.tvJobWages.setText(jobEntity.getMoney()+"/次");
            }else if (jobEntity.getTerm()==5){
                holder.tvJobWages.setText("义工");
            }

            if (jobEntity.getUser_status().equals("0")){
                holder.btnCancelActn.setText("取消报名");
            }else if(jobEntity.getUser_status().equals("1")){
                holder.btnCancelActn.setText("已取消报名");
                holder.btnCancelActn.setClickable(false);
                holder.btnCancelActn.setBackgroundResource(R.drawable.button_sign_background_gray);
            }else if(jobEntity.getUser_status().equals("2")){
                holder.btnCancelActn.setText("暂不录取");
                holder.btnCancelActn.setClickable(false);
                holder.btnCancelActn.setBackgroundResource(R.drawable.button_sign_background_gray);
            }else if(jobEntity.getUser_status().equals("3")){
                holder.btnCancelActn.setText("取消参加");
                holder.btnConfirmActn.setText("确定参加");
                holder.btnConfirmActn.setVisibility(View.VISIBLE);
                holder.btnConfirmActn.setBackgroundResource(R.drawable.button_sign_background_red);
            }else if(jobEntity.getUser_status().equals("4")){
                holder.btnCancelActn.setText("我已取消");
                holder.btnCancelActn.setClickable(false);
                holder.btnCancelActn.setBackgroundResource(R.drawable.button_sign_background_gray);
            }else if(jobEntity.getUser_status().equals("5")){
                holder.btnCancelActn.setText("取消报名");
            }else if(jobEntity.getUser_status().equals("6")){
                holder.btnCancelActn.setText("我已取消");
                holder.btnCancelActn.setClickable(false);
                holder.btnCancelActn.setBackgroundResource(R.drawable.button_sign_background_gray);
            }else if(jobEntity.getUser_status().equals("7")){
                holder.btnCancelActn.setText("暂不录取");
                holder.btnCancelActn.setClickable(false);
                holder.btnCancelActn.setBackgroundResource(R.drawable.button_sign_background_gray);
            }else if(jobEntity.getUser_status().equals("8")){
                holder.btnCancelActn.setText("工作中");
                holder.btnCancelActn.setClickable(false);
                holder.btnCancelActn.setBackgroundResource(R.drawable.button_sign_background_gray);
            }else if(jobEntity.getUser_status().equals("9")){
                holder.btnCancelActn.setText("催工资");
            }else if(jobEntity.getUser_status().equals("10")){
                holder.btnCancelActn.setText("已催工资");
                holder.btnCancelActn.setClickable(false);
            }else if(jobEntity.getUser_status().equals("11")){
                holder.btnCancelActn.setText("去评价");
            }else if(jobEntity.getUser_status().equals("12")){
                holder.btnCancelActn.setText("已完成");
                holder.btnCancelActn.setClickable(false);
                holder.btnCancelActn.setBackgroundResource(R.drawable.button_sign_background_gray);
            }
//
//
//
            Picasso.with(mContext).load(jobEntity.getName_image())
                    .placeholder(R.mipmap.icon_head_defult)
                    .error(R.mipmap.icon_head_defult)
                    .transform(new CropCircleTransfermation())
                    .into(holder.userHead);
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(mContext, JobDetailActivity.class);
////                    intent.putExtra("job", job);
////                    intent.putExtra("merchantid", job.getMerchant_id());
//                    mContext.startActivity(intent);
//                }
//            });
            holder.btnCancelActn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     int type = 0;
                    if (jobEntity.getUser_status().equals("0")){
                        type=1;
                    }
//                    else if(jobEntity.getUser_status().equals("1")){
//                        return;
//                    }
//                    else if(jobEntity.getUser_status().equals("2")){
//                        return;
//                    }
                    else if(jobEntity.getUser_status().equals("3")){
                        type=4;
                    }
//                    else if(jobEntity.getUser_status().equals("4")){
//
//                    }
                    else if(jobEntity.getUser_status().equals("5")){
                        type=6;
                    }
//                    else if(jobEntity.getUser_status().equals("6")){
//                    }
//                    else if(jobEntity.getUser_status().equals("7")){
//
//                    }
//                    else if(jobEntity.getUser_status().equals("8")){
//
//                    }
                    else if(jobEntity.getUser_status().equals("9")){
                        type=10;
                    }
//                    else if(jobEntity.getUser_status().equals("10")){
//
//                    }
                    else if(jobEntity.getUser_status().equals("11")){
                       type=12;
                    }
                    else {
                        return;
                    }

                    mCallBack.RecyOnClick(jobEntity.getId(),type,position);
                }
            });
            holder.btnConfirmActn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallBack.RecyOnClick(jobEntity.getId(),5,position);
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

        @InjectView(R.id.user_head) ImageView userHead;
        @InjectView(R.id.business_name) TextView businessName;
        @InjectView(R.id.tv_job_wages) TextView tvJobWages;

        @InjectView(R.id.img_pay) ImageView imgPay;
        @InjectView(R.id.tv_work_date) TextView tvWorkDate;
        @InjectView(R.id.rl_top) RelativeLayout rlTop;
        @InjectView(R.id.btn_cancel_actn) Button btnCancelActn;
        @InjectView(R.id.btn_confirm_actn) Button btnConfirmActn;
        @InjectView(R.id.ll_button) LinearLayout llButton;
        @InjectView(R.id.rl_job) RelativeLayout rlJob;
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
