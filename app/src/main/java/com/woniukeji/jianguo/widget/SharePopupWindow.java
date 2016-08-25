package com.woniukeji.jianguo.widget;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.JobInfo;
import com.woniukeji.jianguo.entity.RxJobDetails;
import com.woniukeji.jianguo.entity.Jobs;
import com.woniukeji.jianguo.utils.LogUtils;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;


/**
 * @data: 2015-4-21
 * @version: V1.0
 */

public class SharePopupWindow extends PopupWindow implements OnClickListener {

    private Context context;
    private ImageView wechatCircle;
    private ImageView wechatFriends;
    private ImageView sina;
    private ImageView qzone;
    private Handler mHandler;
    private SharedPreferences SearchSp;
    private String accessToken;
    private String userId;
    private String videoId;
    private ImageView share_dialog_close;
    private RelativeLayout ll_subscribe_share;
    private RelativeLayout rl_red_bg;
    private String date;
    private String wage;
    private String linkUrl= Constants.JIANGUO_TEST2+"Html_Job_Id_Servlet";
    private String jobid;
    JobInfo jobinfo;
    //分享相关

    public SharePopupWindow(Context cx, Handler handler, String jobId, JobInfo jobinfo, String date, String wage) {
        this.context = cx;
        this.mHandler = handler;
        this.jobid=jobId;
        this.jobinfo=jobinfo;
        this.date=date;
        this.wage=wage;
    }

    public void showShareWindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.share_popupwindow, null);
        //三个类别的分享
        wechatCircle = (ImageView) view.findViewById(R.id.weichat_circle_share_icon);
        wechatFriends = (ImageView) view.findViewById(R.id.weichat_friends_share_icon);
        sina = (ImageView) view.findViewById(R.id.sina_share_icon);
        qzone= (ImageView) view.findViewById(R.id.qzon_share_icon);

        share_dialog_close = (ImageView) view.findViewById(R.id.share_dialog_close);
        ll_subscribe_share = (RelativeLayout) view.findViewById(R.id.ll_subscribe_share);
        rl_red_bg = (RelativeLayout) view.findViewById(R.id.rl_red_bg);
        //取消分享
        //添加点击事件
        share_dialog_close.setOnClickListener(this);
        ll_subscribe_share.setOnClickListener(this);
        wechatCircle.setOnClickListener(this);
        wechatFriends.setOnClickListener(this);
        rl_red_bg.setOnClickListener(this);
        sina.setOnClickListener(this);
        qzone.setOnClickListener(this);
        SearchSp = context.getSharedPreferences("userInfo", context.MODE_PRIVATE);
        userId = SearchSp.getString("userId", "0");
        accessToken = SearchSp.getString("accessToken", "0");

        // 添加布局
        this.setContentView(view);
        // 设置SharePopupWindow宽度
        this.setWidth(LayoutParams.FILL_PARENT);
        // 设置SharePopupWindow高度
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置setFocusable可获取焦点
        this.setFocusable(true);
        // 设置setFocusable动画风格
//        this.setAnimationStyle(R.style.AnimBottom);
        //画背景
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置背景
        this.setBackgroundDrawable(dw);

//        SocializeListeners.SnsPostListener mSnsPostListener  = new SocializeListeners.SnsPostListener() {
//
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onComplete( SHARE_MEDIA platform, int stCode,
//                                    SocializeEntity entity) {
//                if (stCode == 200) {
//                    Toast.makeText(context,"分享成功", Toast.LENGTH_SHORT)
//                            .show();
//                } else {
//                    Toast.makeText(context,
//                            "分享失败 : error code : " + stCode, Toast.LENGTH_SHORT)
//                            .show();
//                }
//            }
//        };
//        mController.registerListener(mSnsPostListener);

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weichat_friends_share_icon:
//                platform = "2";
                Wechat.ShareParams sp = new Wechat.ShareParams();
                //任何分享类型都需要title和text
                //the two params of title and text are required in every share type
                sp.setTitle("小伙伴给您分享了一条兼职！");
                // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//                momentsp.setTitleUrl("http://sharesdk.cn");
                // text是分享文本，所有平台都需要这个字段
                sp.setText(jobinfo.getJob_name()+"\n"+wage+"\n"+jobinfo.getAddress());
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                // url仅在微信（包括好友和朋友圈）中使用
                sp.imageUrl = jobinfo.getJob_image();
                sp.setUrl(linkUrl+"?job_id="+jobid);

                sp.shareType = Platform.SHARE_WEBPAGE;
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                wechat.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        LogUtils.e("share","分享成功");
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        LogUtils.e("share","分享失败throwable"+throwable.toString());
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        LogUtils.e("share","quxiaqo");
                    }
                });
                wechat.share(sp);
                dismiss();
                break;
            case R.id.weichat_circle_share_icon:
//                platform = "3";
                WechatMoments.ShareParams momentsp = new WechatMoments.ShareParams();
                //任何分享类型都需要title和text
                //the two params of title and text are required in every share type
                momentsp.setTitle("小伙伴给您分享了一条兼职！");
                // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//                momentsp.setTitleUrl("http://sharesdk.cn");
                // text是分享文本，所有平台都需要这个字段
                momentsp.setText(jobinfo.getJob_name()+"\n"+wage+"\n"+jobinfo.getAddress());
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                // url仅在微信（包括好友和朋友圈）中使用
                momentsp.imageUrl =jobinfo.getJob_image();
                momentsp.setUrl(linkUrl+"?job_id="+jobid);
                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//                momentsp.setComment("我是测试评论文本");
//                momentsp.shareType = Platform.SHARE_IMAGE;
//                momentsp.shareType=Platform.SHARE_APPS;
//                momentsp.setTitleUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.woniukeji.jianguo");
//                momentsp.imageUrl = "http://7xlell.com2.z0.glb.qiniucdn.com/01678d545b4de3f2ba858ae90a1cce21";

                momentsp.shareType = Platform.SHARE_WEBPAGE;
                Platform wechatmom = ShareSDK.getPlatform(WechatMoments.NAME);
                wechatmom.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        LogUtils.e("share","分享成功");
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        LogUtils.e("share","分享成功");
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        LogUtils.e("share","成功");
                    }
                });
                wechatmom.share(momentsp);
                dismiss();
                break;
            case R.id.sina_share_icon:
//                platform = "1";
                QQ.ShareParams qqsp = new QQ.ShareParams();
                qqsp.setText("兼果科技");
                qqsp.title = "小伙伴给您分享了一条兼职！";
                qqsp.text =jobinfo.getJob_name()+"\n"+wage+"\n"+jobinfo.getAddress();
                qqsp.setImageUrl(jobinfo.getJob_image());
//                qqsp.setUrl(linkUrl+"?job_id="+jobid);
                qqsp.setTitleUrl(linkUrl+"?job_id="+jobid);
                Platform qq = ShareSDK.getPlatform(QQ.NAME);

                qq.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        LogUtils.e("share","成功");
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        LogUtils.e("share","失败");
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        LogUtils.e("share","成功");
                    }
                }); // 设置分享事件回调
// 执行图文分享
                qq.share(qqsp);
                dismiss();
                break;
            case R.id.qzon_share_icon:
//                platform = "1";
                QZone.ShareParams qz = new QZone.ShareParams();
                qz.setTitle("小伙伴给您分享了一条兼职！");
                qz.setTitleUrl(linkUrl+"?job_id="+jobid);
                qz.setText(jobinfo.getJob_name()+"\n"+wage+"\n"+jobinfo.getAddress());
                qz.setImageUrl(jobinfo.getJob_image());

                Platform qzp = ShareSDK.getPlatform(QZone.NAME);

                qzp.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        LogUtils.e("share","成功");
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        LogUtils.e("share","失败");
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        LogUtils.e("share","取消");
                    }
                }); // 设置分享事件回调
// 执行图文分享
                qzp.share(qz);
                dismiss();
                break;
            case R.id.share_dialog_close:
                dismiss();
                break;
            case R.id.rl_red_bg:
                dismiss();
                break;
            case R.id.ll_subscribe_share:
                dismiss();
                break;
            default:
                break;
        }
    }










}
