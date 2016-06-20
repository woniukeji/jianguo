package com.woniukeji.jianmerchant.widget;


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

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.widget.time.PickerSize;

import java.util.ArrayList;
import java.util.List;


/**
 * @author yangle
 * @data: 2015-08-14
 * @version: V1.0
 */

public class TimePickerPopuWin extends PopupWindow implements OnClickListener {




    private Context context;
    private List<String> stringList1=new ArrayList<String>();
    private List<String> stringList2=new ArrayList<String>();
    private String textStr2;
    private String textStr1;
    private Handler mHandler;
    private int type;//三种 鞋码 衣服 身高
    private TextView tvNo;
    private TextView tvOk;
    private PickerSize packer1;
    private PickerSize packer2;
    //分享相关
    //黑色背景


    public TimePickerPopuWin(Context context, List<String> list1,List<String> list2, Handler handler, int mType) {
        this.context = context;
        this.stringList1 = list1;
        this.stringList2 = list2;
        this.mHandler=handler;
        this.type=mType;
    }

    public void showShareWindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.time_popwindow_picker, null);

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
        tvNo= (TextView) view.findViewById(R.id.tv_no);
        tvOk= (TextView) view.findViewById(R.id.tv_ok);
        packer1= (PickerSize) view.findViewById(R.id.pcker1);
        packer2= (PickerSize) view.findViewById(R.id.pcker2);
        packer1.setData(stringList1);
        textStr1=stringList1.get(stringList1.size()/2);
        packer2.setData(stringList2);
        textStr2=stringList2.get(stringList2.size()/2);
    }

    public void initListener() {
        tvNo.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        packer1.setOnSelectListener(new PickerSize.onSelectListener() {
            @Override
            public void onSelect(String text) {
                textStr1=text;
            }
        });
        packer2.setOnSelectListener(new PickerSize.onSelectListener() {
            @Override
            public void onSelect(String text) {
                textStr2=text;
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
                message.obj=textStr1+textStr2;
                message.what=2;
                mHandler.sendMessage(message);
                dismiss();
                break;
        }
    }
}
