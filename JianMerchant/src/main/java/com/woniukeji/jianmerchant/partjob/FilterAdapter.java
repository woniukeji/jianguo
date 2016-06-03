package com.woniukeji.jianmerchant.partjob;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
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
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.Model;
import com.woniukeji.jianmerchant.entity.PublishUser;
import com.woniukeji.jianmerchant.utils.CropCircleTransfermation;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.woniukeji.jianmerchant.widget.CircleImageView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static android.content.ClipData.newIntent;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {

    private final List<PublishUser.ListTUserInfoEntity> mValues;
    private final Context mContext;


    private int mType;
    public static final int NORMAL = 1;
    public static final int IS_FOOTER = 2;

    private AnimationDrawable mAnimationDrawable;
    private boolean isFooterChange = false;
    private String jobid;
    private RecyCallBack mCallBack;

    public FilterAdapter(List<PublishUser.ListTUserInfoEntity> items, Context context, int type,String jobid, RecyCallBack callBack) {
        mValues = items;
        mContext = context;
        mType = type;
        this.jobid = jobid;
        mCallBack = callBack;
    }

    @OnClick({R.id.circleimg_head, R.id.btn_cancel, R.id.btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.circleimg_head:
                break;
            case R.id.btn_cancel:
                break;
            case R.id.btn_confirm:
                break;
        }
    }

    public interface RecyCallBack {
         void RecyOnClick(int login_id, int admit,int position);
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
                View VoteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_filter_item, parent, false);
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
            final PublishUser.ListTUserInfoEntity user = mValues.get(position);
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

//            String date = DateUtils.getTime(Long.valueOf(job.getStart_date()), Long.valueOf(job.getStop_date()));
//            holder.tvDate.setText(date);
//            //性别限制（0=只招女，1=只招男，2=不限男女）
//            holder.tvManagerName.setText(job.getMerchant_id_name());
//            holder.tvChakanBrowse.setText(job.getLook());
//            holder.tvMessage.setText(job.getRemarks());

            if (null!=user.getName()&&!user.getName().equals("0")){
                holder.tvUserName.setText(user.getName());
            }else {
                holder.tvUserName.setText("未填写");
            }


            if (user.getSex_resume()==1){
                holder.imgSex.setImageResource(R.mipmap.icon_man);
            }else {
                holder.imgSex.setImageResource(R.mipmap.icon_woman);
            }
            holder.tvSchoolName.setText(user.getSchool());
            holder.tvFinishJobCount.setText("共完成"+user.getComplete_job()+"次兼职，"+"取消"+user.getCancel_job()+"次兼职");
            holder.tvPublishDate.setText(user.getTime_job());
            holder.tvCredit.setText(" ");
            holder.tvAuthorInfo.setText(user.getTel());
            //user.getRemarks_job()
          

            if (user.getUser_status().equals("0")){
                holder.btnCancel.setText("暂不录取");
                holder.btnConfirm.setText("确认录取");
                holder.btnConfirm.setVisibility(View.VISIBLE);
                holder.btnCancel.setBackgroundResource(R.drawable.button_sign_background_gray);
//            }
//            else if(user.getUser_status().equals("1")){
//                holder.btnCancel.setText("已取消报名");
//                holder.btnCancel.setClickable(false);
//                holder.btnCancel.setBackgroundResource(R.drawable.button_sign_background_gray);
            }else if(user.getUser_status().equals("2")){
                holder.btnCancel.setText("已取消");
                holder.btnCancel.setClickable(false);
                holder.btnCancel.setBackgroundResource(R.drawable.button_sign_background_gray);
            }else if(user.getUser_status().equals("3")){
                holder.btnCancel.setText("取消录取");
                holder.btnConfirm.setText("待确认");
                holder.btnConfirm.setVisibility(View.VISIBLE);
                holder.btnConfirm.setBackgroundResource(R.drawable.button_sign_background_gray);
            }else if(user.getUser_status().equals("4")){
                holder.btnCancel.setText("用户已取消");
                holder.btnCancel.setClickable(false);
                holder.btnCancel.setBackgroundResource(R.drawable.button_sign_background_gray);
            }else if(user.getUser_status().equals("5")){
                holder.btnCancel.setText("取消录取");
                holder.btnConfirm.setVisibility(View.GONE);
            }else if(user.getUser_status().equals("6")){
                holder.btnCancel.setText("用户已取消");
                holder.btnCancel.setClickable(false);
                holder.btnCancel.setBackgroundResource(R.drawable.button_sign_background_gray);
            }else if(user.getUser_status().equals("7")){
                holder.btnCancel.setText("已取消");
                holder.btnCancel.setClickable(false);
                holder.btnCancel.setBackgroundResource(R.drawable.button_sign_background_gray);
            }else if(user.getUser_status().equals("8")){
                holder.btnCancel.setText("工作中");
                holder.btnCancel.setClickable(false);
                holder.btnCancel.setBackgroundResource(R.drawable.button_sign_background_gray);
            }
//            else if(user.getUser_status().equals("9")){
//                holder.btnCancel.setText("催工资");
//            }
            else{
                holder.btnCancel.setText("工作已结束");
                holder.btnCancel.setClickable(false);
                holder.btnCancel.setBackgroundResource(R.drawable.button_sign_background_gray);
            }

            Picasso.with(mContext).load(user.getName_image())
                    .placeholder(R.mipmap.icon_head_defult)
                    .error(R.mipmap.icon_head_defult)
                    .transform(new CropCircleTransfermation())
                    .into(holder.circleimgHead);
            holder.tvAuthorInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + user.getTel()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
            holder.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int type=0;
                    if (user.getUser_status().equals("0")){
                        type=2;
                    }else if(user.getUser_status().equals("3")){
                        type=2;
                    }
                    else if(user.getUser_status().equals("5")){
                        type=7;
                    }
                    else if(user.getUser_status().equals("9")){
                        type=12;
                    }
                    else if(user.getUser_status().equals("10")){
                        type=12;
                    }
                    else {
                        return;
                    }
//                    else if(user.getUser_status().equals("11")){
//                        type=12;
//                    }
                    mCallBack.RecyOnClick(user.getLogin_id(),type,position);
                }
            });
            holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int type=0;
                    if (user.getUser_status().equals("0")){
                        type=3;
                    }else {
                        return;
                    }
                    mCallBack.RecyOnClick(user.getLogin_id(),type,position);
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

        @InjectView(R.id.circleimg_head) CircleImageView circleimgHead;
        @InjectView(R.id.tv_user_name) TextView tvUserName;
        @InjectView(R.id.img_sex) ImageView imgSex;
        @InjectView(R.id.ll_user_info) LinearLayout llUserInfo;
        @InjectView(R.id.tv_school_name) TextView tvSchoolName;
        @InjectView(R.id.tv_finish_job_count) TextView tvFinishJobCount;
        @InjectView(R.id.tv_publish_date) TextView tvPublishDate;
        @InjectView(R.id.tv_credit_info) TextView tvCredit;
        @InjectView(R.id.tv_author_info) TextView tvAuthorInfo;
        @InjectView(R.id.btn_cancel) Button btnCancel;
        @InjectView(R.id.btn_confirm) Button btnConfirm;
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
