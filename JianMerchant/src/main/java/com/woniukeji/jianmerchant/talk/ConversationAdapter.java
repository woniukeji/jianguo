package com.woniukeji.jianmerchant.talk;

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

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMSingleMessageQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.squareup.picasso.Picasso;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.utils.CropCircleTransfermation;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.LogUtils;
import com.woniukeji.jianmerchant.widget.CircleImageView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> {

    private final List<AVIMConversation> mValues;
    private final Context mContext;
    private final int loginId;

    private View mHeaderView;
    public static final int IS_HEADER = 0;
    public static final int NORMAL = 1;
    public static final int IS_FOOTER = 2;
    private AnimationDrawable mAnimationDrawable;
    private boolean isFooterChange = false;

    public ConversationAdapter(List<AVIMConversation> items, Context context,int loginid) {
        mValues = items;
        mContext = context;
        loginId=loginid;
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
            holder.loading.setText("加载中...");
            holder.animLoading.setVisibility(View.VISIBLE);
//            holder.animLoading.setBackgroundResource(R.drawable.loading_footer);
//            mAnimationDrawable = (AnimationDrawable) holder.animLoading.getBackground();
//            mAnimationDrawable.start();
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder holder = null;
        switch (viewType) {
//            case IS_HEADER:
//                holder = new ViewHolder(mHeaderView, IS_HEADER);
//                return holder;
            case NORMAL:
                View VoteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.talk_conversation_item, parent, false);
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
//        if (position == 0) {
//            return;
//        } else if (mValues.size() < 5) {
//            holder.itemView.setVisibility(View.GONE);
//        } else if (mValues.size() + 1 == position) {
//            mmswoon(holder);
//            holder.itemView.setVisibility(View.VISIBLE);
//        } else {
        final AVIMConversation conversation = mValues.get(position);

        conversation.getAttribute(Constants.CREAT_NAME);
        if(conversation.getCreator().equals(String.valueOf(loginId))){
            holder.tvNick.setText((String)conversation.getAttribute(Constants.OTHER_NAME));
            Picasso.with(mContext).load((String)conversation.getAttribute(Constants.OTHER_IMG))
                    .placeholder(R.drawable.default_head)
                    .error(R.drawable.default_head)
                    .transform(new CropCircleTransfermation())
                    .into( holder.circleTalkHead);
        }else{
            holder.tvNick.setText((String)conversation.getAttribute(Constants.CREAT_NAME));
            Picasso.with(mContext).load((String)conversation.getAttribute(Constants.CREAT_IMG))
                    .placeholder(R.drawable.default_head)
                    .error(R.drawable.default_head)
                    .transform(new CropCircleTransfermation())
                    .into( holder.circleTalkHead);
        }

        conversation.getLastMessage(new AVIMSingleMessageQueryCallback() {
            @Override
            public void done(AVIMMessage avimMessage, AVIMException e) {
                if (null != avimMessage) {
                    AVIMTextMessage avimTextMessage = (AVIMTextMessage) avimMessage;
                        String avatorImag= (String) avimTextMessage.getAttrs().get("avatar");
                    String nickName = (String) avimTextMessage.getAttrs().get("nickname");
                    holder.tvContent.setText(avimTextMessage.getText());
                    avimTextMessage.getTimestamp();
                    String date= DateUtils.getDate(avimTextMessage.getTimestamp());
                    holder.tvDate.setText(date);

                }
//                    avimTextMessage.getAttrs().get("avator");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(mContext, ChatActivity.class);
                        String conid=conversation.getConversationId();
                        intent.putExtra("mConversationId",conid);
                        mContext.startActivity(intent);
                    }
                });
            }
        });
//            String userId = ConversationHelper.otherIdOfConversation(conversation);
//            String avatar = ThirdPartUserUtils.getInstance().getUserAvatar(userId);
        //等待数据设置

//        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
//        return mValues.size() > 0 ? mValues.size() + 2 : 0;
    }


    @Override
    public int getItemViewType(int position) {
//        if (position == mValues.size() + 1) {
//            return IS_FOOTER;
//        } else
//        if (position == 0) {
//            return IS_HEADER;
//        } else {
//            return NORMAL;
//        }
        return NORMAL;
    }

    static

    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.circle_talk_head) CircleImageView circleTalkHead;
        @InjectView(R.id.tv_nick) TextView tvNick;
        @InjectView(R.id.tv_content) TextView tvContent;
        @InjectView(R.id.tv_date) TextView tvDate;
        @InjectView(R.id.rl_school) RelativeLayout rlSchool;

        private ImageView animLoading;
        private TextView loading;

        public ViewHolder(View view, int type) {
            super(view);

            switch (type) {
                case IS_HEADER:
                    break;
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
