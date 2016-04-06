package com.woniukeji.jianmerchant.publish;

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
import com.woniukeji.jianmerchant.utils.SPUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HistoryJobAdapter extends RecyclerView.Adapter<HistoryJobAdapter.ViewHolder> {

    private final List<Model.ListTJobEntity> mValues;
    private final Context mContext;
    private  String mType;
    public static final int NORMAL = 1;
    public static final int IS_FOOTER = 2;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.tv_date) TextView tvDate;
    @InjectView(R.id.tv_location) TextView tvLocation;
    @InjectView(R.id.ll_publish_time) LinearLayout llPublishTime;
    @InjectView(R.id.tv_human) TextView tvHuman;
    @InjectView(R.id.tv_name) TextView tvName;
    @InjectView(R.id.ll_publisher) LinearLayout llPublisher;
    @InjectView(R.id.tv_enroll_num) TextView tvEnrollNum;
    @InjectView(R.id.btn_muban_use) Button btnMubanUse;
    @InjectView(R.id.btn_muban_delete) Button btnMubanDelete;
    @InjectView(R.id.ll_muban) LinearLayout llMuban;
    @InjectView(R.id.img_his) ImageView imgHis;
    @InjectView(R.id.rl_job) RelativeLayout rlJob;

    private AnimationDrawable mAnimationDrawable;
    private boolean isFooterChange = false;
    private String name;
    private deleteCallBack deleCallBack;

    public HistoryJobAdapter(List<Model.ListTJobEntity> items, Context context, String type, deleteCallBack callBack) {
        mValues = items;
        mContext = context;
        mType=type;
        name= (String) SPUtils.getParam(mContext, Constants.USER_INFO,Constants.SP_NAME,"");
        deleCallBack=callBack;
    }

   public interface deleteCallBack{
        public void deleOnClick(int job_id,int merchant_id,int position);
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
                View VoteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_jobitem, parent, false);
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
            if (mType.equals("0")){
                holder.tvTitle.setText(job.getName());
                holder.llMuban.setVisibility(View.GONE);
                holder.imgHis.setVisibility(View.VISIBLE);
            }else {
                holder.tvTitle.setText(job.getModel_name());
                holder.llMuban.setVisibility(View.VISIBLE);
                holder.imgHis.setVisibility(View.GONE);
            }

            holder.tvName.setText(name);
            String date = job.getRegedit_time().substring(5,11);
            holder.tvDate.setText(date);
            //性别限制（0=只招女，1=只招男，2=不限男女）

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, PublishDetailActivity.class);
                    intent.putExtra("job",job);
                    intent.putExtra("type","old");
                    mContext.startActivity(intent);
                }
            });
            holder.btnMubanUse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, PublishDetailActivity.class);
                    intent.putExtra("job",job);
                    intent.putExtra("type","old");
                    mContext.startActivity(intent);
                }
            });
            holder.btnMubanDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleCallBack.deleOnClick(job.getId(),job.getMerchant_id(),position);
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

        @InjectView(R.id.tv_title) TextView tvTitle;
        @InjectView(R.id.tv_date) TextView tvDate;
        @InjectView(R.id.tv_location) TextView tvLocation;
        @InjectView(R.id.ll_publish_time) LinearLayout llPublishTime;
        @InjectView(R.id.tv_human) TextView tvHuman;
        @InjectView(R.id.tv_name) TextView tvName;
        @InjectView(R.id.ll_publisher) LinearLayout llPublisher;
        @InjectView(R.id.tv_enroll_num) TextView tvEnrollNum;
        @InjectView(R.id.btn_muban_use) Button btnMubanUse;
        @InjectView(R.id.btn_muban_delete) Button btnMubanDelete;
        @InjectView(R.id.ll_muban) LinearLayout llMuban;
        @InjectView(R.id.img_his) ImageView imgHis;
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
