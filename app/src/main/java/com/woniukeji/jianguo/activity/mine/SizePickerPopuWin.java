package com.woniukeji.jianguo.activity.mine;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.widget.time.PickerSize;

import java.util.ArrayList;
import java.util.List;


/**
 * @author yangle
 * @data: 2015-08-14
 * @version: V1.0
 */

public class SizePickerPopuWin extends PopupWindow implements OnClickListener {




    private Context context;
    private List<String> stringList=new ArrayList<String>();
    private String textStr;
    private Handler mHandler;
    private int type;//三种 鞋码 衣服 身高分别是 0 00 000
    private PickerSize packer;
    private TextView tvNo;
    private TextView tvOk;
    //分享相关
    //黑色背景


    public SizePickerPopuWin(Context context, List<String> list , Handler handler, int mType) {
        this.context = context;
        this.stringList = list;
        this.mHandler=handler;
        this.type=mType;

    }

    public void showShareWindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_picker, null);

        initView(view);
        initListener();

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


    }

    public void initView(View view) {
        tvNo = (TextView) view.findViewById(R.id.tv_no);
        tvOk = (TextView) view.findViewById(R.id.tv_ok);
        packer = (PickerSize) view.findViewById(R.id.pcker);
        packer.setData(stringList);
        if (type == 0) {
            textStr = "43";
        } else if (type == 1) {
            textStr = "XL";
        } else if (type == 2) {
            textStr = "167cm";
        } else {
            textStr ="2013";
        }
    }

    public void initListener() {
        tvNo.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        packer.setOnSelectListener(new PickerSize.onSelectListener() {
            @Override
            public void onSelect(String text) {
                textStr=text;
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_no:
                dismiss();
                break;
            case R.id.tv_ok:
                Message message=new Message();
                message.arg1=type;
                message.obj=textStr;
                message.what=2;
                mHandler.sendMessage(message);
                dismiss();
                break;
        }
    }
}
