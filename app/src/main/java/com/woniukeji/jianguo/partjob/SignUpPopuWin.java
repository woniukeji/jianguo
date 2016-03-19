package com.woniukeji.jianguo.partjob;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.widget.time.PickerDateView;
import com.woniukeji.jianguo.widget.time.PickerView;

import butterknife.InjectView;

public class SignUpPopuWin extends PopupWindow implements View.OnClickListener {

    private final Context context;
    private final Handler mHandler;
    RelativeLayout mRlRootView;
    TextView mTvInfo;
    TextView mTvWage;
    TextView mTvWorkLocation;
    TextView mTvWorkDate;
    TextView mTvWorkTime;
    TextView mTvCollectionSites;
    TextView mTvCollectionTime;
    TextView mTvSex;
    TextView mTvPayMethod;
    TextView mTvOther;
    Button mTvOk;
    ImageView mImgOff;


    public SignUpPopuWin(Context context, Handler handler, int mType) {
        this.context = context;
        this.mHandler=handler;

    }
    public void showShareWindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_signup, null);

        initView(view);
        initListeners();
        initDate();
        // 添加布局
        this.setContentView(view);
        // 设置SharePopupWindow宽度
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        // 设置SharePopupWindow高度
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置setFocusable可获取焦点
        this.setFocusable(true);
        // 设置setFocusable动画风格
//        this.setAnimationStyle(R.style.AnimBottom);
        //画背景
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置背景
        this.setBackgroundDrawable(dw);


    }
    public void initView(View view) {
            mRlRootView = (RelativeLayout) view.findViewById(R.id.rl_root_view);
            mTvInfo = (TextView) view.findViewById(R.id.tv_info);
            mTvWage = (TextView) view.findViewById(R.id.tv_wage);
            mTvWorkLocation = (TextView) view.findViewById(R.id.tv_work_location);
            mTvWorkDate = (TextView) view.findViewById(R.id.tv_work_date);
            mTvWorkTime = (TextView) view.findViewById(R.id.tv_work_time);
            mTvCollectionSites = (TextView) view.findViewById(R.id.tv_collection_sites);
            mTvCollectionTime = (TextView) view.findViewById(R.id.tv_collection_time);
            mTvSex = (TextView) view.findViewById(R.id.tv_sex);
            mTvPayMethod = (TextView) view.findViewById(R.id.tv_pay_method);
            mTvOther = (TextView) view.findViewById(R.id.tv_other);
            mTvOk = (Button) view.findViewById(R.id.tv_ok);
        mImgOff= (ImageView) view.findViewById(R.id.img_off);

    }
    public void initListeners() {
        mTvOk.setOnClickListener(this);
        mImgOff.setOnClickListener(this);
    }


    private void initDate() {



    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_off:
                dismiss();
                break;
            case R.id.tv_ok:
                Message message=new Message();
//                message.arg1=type;
                message.obj="报名成功";
                message.what=4;
                mHandler.sendMessage(message);
                dismiss();
                break;
        }
    }
}
