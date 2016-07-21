package com.woniukeji.jianmerchant.affordwages;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.AffordUser;
import com.woniukeji.jianmerchant.eventbus.UserWagesEvent;
import com.woniukeji.jianmerchant.utils.CropCircleTransfermation;
import com.woniukeji.jianmerchant.utils.LogUtils;
import com.woniukeji.jianmerchant.utils.SPUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

public class CalculateAdapter extends RecyclerView.Adapter<CalculateAdapter.ViewHolder> {

    private final List<AffordUser.ListTUserInfoEntity> mValues;
    private List<Boolean> isSelected;

    private final Context mContext;
    private String money;
    public static final int NORMAL = 1;
    public static final int IS_FOOTER = 2;

    private AnimationDrawable mAnimationDrawable;
    private boolean isFooterChange = false;
    private String name;
    private deleteCallBack deleCallBack;

    public CalculateAdapter(List<AffordUser.ListTUserInfoEntity> items,List<Boolean>  list, Context context, String money, deleteCallBack callBack) {
        mValues = items;
        mContext = context;
        isSelected=list;
        this.money = money;
        name = (String) SPUtils.getParam(mContext, Constants.USER_INFO, Constants.USER_NAME, "");
        deleCallBack = callBack;
    }

    public interface deleteCallBack {
        public void deleOnClick(String money, AffordUser.ListTUserInfoEntity user, int position);
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
        LogUtils.d("谁先调用----","onCreateViewHolder");
        ViewHolder holder = null;
        switch (viewType) {
            case NORMAL:
                View VoteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pay_wages_item, parent, false);
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
        LogUtils.d("谁先调用----","onBindViewHolder");
        if (mValues.size() < 1) {
            holder.itemView.setVisibility(View.GONE);
        }
        if (mValues.size() == position) {
            if (mValues.size() > 4) {
                mmswoon(holder);
                holder.itemView.setVisibility(View.VISIBLE);
            }
        } else {
            final AffordUser.ListTUserInfoEntity user = mValues.get(position);
            if (user.getRealname()!=null&&!user.getRealname().equals("2")){
                holder.userName.setText(user.getName());
            }else {
                holder.userName.setText(user.getName()+"（未实名）");
            }

            holder.tvPhone.setText(user.getTel());
            holder.tvWages.setText(user.getReal_money()+"元");
            Picasso.with(mContext).load(user.getName_image()).transform(new CropCircleTransfermation()).error(R.drawable.default_head).into(holder.imgHead);
            holder.cbUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        isSelected.set(position,true);
                    }else{
                        isSelected.set(position,false);
                    }

                    UserWagesEvent userWagesEvent=new UserWagesEvent();
                    userWagesEvent.userInfoEntity=user;
                    userWagesEvent.isChoose=false;
                    EventBus.getDefault().post(userWagesEvent);
                }
            });

           if (isSelected.get(position)){
               holder.cbUser.setChecked(true);
           }else {
               holder.cbUser.setChecked(false);
           }

//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(mContext, PublishDetailActivity.class);
//                    intent.putExtra("type", "old");
//                    mContext.startActivity(intent);
//                }
//            });
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleCallBack.deleOnClick(money,user,position);
                }
            });
//            holder.btnMubanDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    deleCallBack.deleOnClick(job.getId(),job.getMerchant_id(),position);
//                }
//            });


        }
    }

    @Override
    public int getItemCount() {
        LogUtils.d("谁先调用----","getItemCount");
        return mValues.size() > 0 ? mValues.size() + 1 : 0;
    }


    @Override
    public int getItemViewType(int position) {
        LogUtils.d("谁先调用----","getItemViewType");
        if (position == mValues.size()) {
            return IS_FOOTER;
        } else {
            return NORMAL;
        }
    }

    static

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.img_head) ImageView imgHead;
        @InjectView(R.id.user_name) TextView userName;
        @InjectView(R.id.tv_phone) TextView tvPhone;
        @InjectView(R.id.ll_publish_time) LinearLayout llPublishTime;
        @InjectView(R.id.tv_wages) TextView tvWages;
        @InjectView(R.id.ll_wages) LinearLayout llWages;
        @InjectView(R.id.rl_job) RelativeLayout rlJob;
        @InjectView(R.id.cb_user) CheckBox cbUser;
        @InjectView(R.id.btn_change_item) Button button;


        private ImageView animLoading;
        private TextView loading;

        public ViewHolder(View view, int type) {
            super(view);
            LogUtils.d("谁先调用----","ViewHolder");
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
