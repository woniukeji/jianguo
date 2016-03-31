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
import com.woniukeji.jianmerchant.entity.PickType;
import com.woniukeji.jianmerchant.widget.time.PickerSize;
import com.woniukeji.jianmerchant.widget.time.PickerType;

import java.util.ArrayList;
import java.util.List;


/**
 * @author yangle
 * @data: 2015-08-14
 * @version: V1.0
 */

public class TypePickerPopuWin extends PopupWindow implements OnClickListener {




    private Context context;
    private List<PickType> stringList=new ArrayList<PickType>();
    private PickType pickType;
    private Handler mHandler;
    private int type;//
    private PickerType packer;
    private TextView tvNo;
    private TextView tvOk;
    //分享相关
    //黑色背景


    public TypePickerPopuWin(Context context, List<PickType> list, Handler handler, int mType) {
        this.context = context;
        this.stringList = list;
        this.mHandler=handler;
        this.type=mType;
    }

    public void showShareWindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.type_popwindow_picker, null);

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
        packer= (PickerType) view.findViewById(R.id.pcker2);
        packer.setData(stringList);
        pickType=stringList.get(stringList.size()/2);
    }

    public void initListener() {
        tvNo.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        packer.setOnSelectListener(new PickerType.onSelectListener() {
            @Override
            public void onSelect(PickType Type) {
                pickType=Type;
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
                message.obj=pickType;
                message.what=3;
                mHandler.sendMessage(message);
                dismiss();
                break;
        }
    }
}
