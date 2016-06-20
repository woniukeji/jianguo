package com.woniukeji.jianmerchant.partjob;

import android.content.Context;
import android.content.Intent;
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

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.Model;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.SPUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PartJobManagerAdapter extends RecyclerView.Adapter<PartJobManagerAdapter.ViewHolder> {

    private final List<Model.ListTJobEntity> mValues;
    private final Context mContext;

    private int mType;
    public static final int NORMAL = 1;
    public static final int IS_FOOTER = 2;

    private AnimationDrawable mAnimationDrawable;
    private boolean isFooterChange = false;
    private String name;
    private RecyCallBack mCallBack;

    public PartJobManagerAdapter(List<Model.ListTJobEntity> items, Context context, int type, RecyCallBack callBack) {
        mValues = items;
        mContext = context;
        mType = type;
        name = (String) SPUtils.getParam(mContext, Constants.USER_INFO, Constants.USER_NAME, "");
        mCallBack = callBack;
    }

    public interface RecyCallBack {
        public void RecyOnClick(int job_id, int merchant_id, int position);
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
                View VoteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.admit_jobitem, parent, false);
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
            final Model.ListTJobEntity job = mValues.get(position);
            holder.tvTitle.setText(job.getName());
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

            String date = DateUtils.getTime(Long.valueOf(job.getStart_date()), Long.valueOf(job.getStop_date()));
            holder.tvDate.setText(date);
            //性别限制（0=只招女，1=只招男，2=不限男女）
            holder.tvManagerName.setText(job.getMerchant_id_name());
            holder.tvChakanBrowse.setText(job.getLook());
            holder.tvMessage.setText(job.getRemarks());

            if (job.getHot()==1){
                holder.imgType.setImageResource(R.mipmap.icon_hot);
            }else if(job.getHot()==2){
                holder.imgType.setImageResource(R.mipmap.icon_jing);
            }else if(job.getHot()==3){
                holder.imgType.setImageResource(R.mipmap.icon_travel);
            }
            holder.tvCount.setText(job.getCount()+"/"+job.getSum());
            if (job.getTerm()==0){//0=月结，1=周结，2=日结，3=小时结，4=次，5=义工
                holder.tvWages.setText(job.getMoney()+"/月");
            }else if(job.getTerm()==1){
                holder.tvWages.setText(job.getMoney()+"/周");
            }else if(job.getTerm()==2){
                holder.tvWages.setText(job.getMoney()+"/日");
            }else if(job.getTerm()==3){
                holder.tvWages.setText(job.getMoney()+"/小时");
            }else if(job.getTerm()==4){
                holder.tvWages.setText(job.getMoney()+"/次");
            }else if(job.getTerm()==5){
                holder.tvWages.setText("义工");
            }else if(job.getTerm()==6){
                holder. tvWages.setText("面议");
            }




                if (job.getStatus()==0){
                holder.btnAdmitAction.setText("录取中");}
            else if(job.getStatus()==1){
                    holder.btnAdmitAction.setText("已招满");
            }else if(job.getStatus()==2){
                holder.btnAdmitAction.setText("工作中");
            }else if(job.getStatus()==3){
                holder.btnAdmitAction.setText("去结算");
            }else if(job.getStatus()==4){
                holder.btnAdmitAction.setText("去评价");
            }else if(job.getStatus()==5){
                holder.btnAdmitAction.setText("已完成");
            }else if(job.getStatus()==6){
                holder.btnAdmitAction.setText("已下架");
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, JobItemDetailActivity.class);
                    intent.putExtra("job", job);
                    intent.putExtra("merchantid", job.getMerchant_id());
                    mContext.startActivity(intent);
                }
            });
            holder.btnAdmitAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(mContext, PublishDetailActivity.class);
//                    intent.putExtra("job", job);
//                    intent.putExtra("type", "old");
//                    mContext.startActivity(intent);
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

        @InjectView(R.id.tv_merchant_name) TextView tvTitle;
        @InjectView(R.id.img_type) ImageView imgType;
        @InjectView(R.id.tv_manager_name) TextView tvManagerName;
        @InjectView(R.id.tv_wages) TextView tvWages;
        @InjectView(R.id.tv_date) TextView tvDate;
        @InjectView(R.id.ll_publish_time) LinearLayout llPublishTime;
        @InjectView(R.id.tv_chakan_browse) TextView tvChakanBrowse;
        @InjectView(R.id.ll_browse) LinearLayout llBrowse;
        @InjectView(R.id.tv_human) TextView tvHuman;
        @InjectView(R.id.tv_count) TextView tvCount;
        @InjectView(R.id.ll_publisher) LinearLayout llPublisher;
        @InjectView(R.id.tv_message) TextView tvMessage;
        @InjectView(R.id.btn_admit_action) Button btnAdmitAction;
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
