package com.woniukeji.jianguo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.activity.main.MainActivity;
import com.woniukeji.jianguo.activity.main.WebViewActivity;
import com.woniukeji.jianguo.entity.PushMessage;
import com.woniukeji.jianguo.activity.mine.AuthActivity;
import com.woniukeji.jianguo.activity.mine.SignUpActivity;
import com.woniukeji.jianguo.activity.partjob.JobDetailActivity;
import com.woniukeji.jianguo.activity.wallte.WalletActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

public class PushMessageAdapter extends RecyclerView.Adapter<PushMessageAdapter.ViewHolder> {

    private final List<PushMessage.ListTPushEntity> mValues;
    private final Context mContext;
    public static final int NORMAL = 1;
    public static final int IS_FOOTER = 2;



    private AnimationDrawable mAnimationDrawable;
    private boolean isFooterChange = false;

    public PushMessageAdapter(List<PushMessage.ListTPushEntity> items, Context context) {
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
            final PushMessage.ListTPushEntity message = mValues.get(position);

         holder.tvTitle.setText(message.getTitle());
            holder.tvDate.setText(message.getTime());
            holder.tvContent.setText("消息详情："+message.getContent());

            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

//                    0=报名，我的兼职
//                    1=钱包
//                    2=实名
//                    3=主页
//                    4=活动H5
                    if (message.getType()==0){
                        mContext.startActivity(new Intent(mContext, SignUpActivity.class));
                    }else if (message.getType()==1){
                        mContext.startActivity(new Intent(mContext, WalletActivity.class));
                    }else if(message.getType()==2){
                        mContext.startActivity(new Intent(mContext, AuthActivity.class));
                    }else if(message.getType()==3){
                        mContext.startActivity(new Intent(mContext, MainActivity.class));
                    }else if(message.getType()==4){
                        Intent intent=new Intent(mContext, WebViewActivity.class);
                        intent.putExtra("url",message.getHtml_url());
                        mContext.startActivity(intent);
                    }else if(message.getType()==5){
                        Intent intent=new Intent(mContext, JobDetailActivity.class);
                        intent.putExtra("job",message.getJob_id());
                        mContext.startActivity(intent);
                    }else {
                        mContext.startActivity(new Intent(mContext, MainActivity.class));
                    }
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

        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.tv_date) TextView tvDate;
        @BindView(R.id.tv_content) TextView tvContent;
        @BindView(R.id.tv_ps) TextView tvPs;
        @BindView(R.id.rl_job) LinearLayout rlJob;
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
