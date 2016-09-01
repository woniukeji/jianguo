package com.woniukeji.jianguo.activity.mine;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.widget.time.PickerDateView;
import com.woniukeji.jianguo.widget.time.PickerView;

import java.util.ArrayList;
import java.util.List;

public class TimePickerPopuWin extends PopupWindow implements View.OnClickListener {

    private final Context context;
    private final Handler mHandler;
    private final int type;
    private List<String> years = new ArrayList<String>();
    private List<String> months = new ArrayList<String>();
    private List<String> bigDays = new ArrayList<String>();//big
    private List<String> littleDays = new ArrayList<String>();//little
    private List<String> days_two = new ArrayList<String>();//2月
    private boolean Leap = false;
    private String yearStr="1998";
    private String monthStr="07";
    private String dayStr="16";
    private PickerDateView yearPv;
    private PickerView monthPv;
    private PickerView dayPv;
    private TextView tvNo;
    private TextView tvOk;
    public TimePickerPopuWin(Context context, Handler handler, int mType) {
        this.context = context;
        this.mHandler=handler;
        this.type=mType;

    }
    public void showShareWindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.time_picker_popu, null);

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
        tvNo= (TextView) view.findViewById(R.id.tv_no);
        tvOk= (TextView) view.findViewById(R.id.tv_ok);
        yearPv= (PickerDateView) view.findViewById(R.id.year_pv);
        monthPv= (PickerView) view.findViewById(R.id.month_pv);
        dayPv= (PickerView) view.findViewById(R.id.day_pv);

    }
    public void initListeners() {
        tvNo.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        yearPv.setOnSelectListener(new PickerDateView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                yearStr = text.substring(0, 4);
                if ((Integer.valueOf(yearStr) % 4 == 0 && Integer.valueOf(yearStr) % 100 != 0) || Integer.valueOf(yearStr) % 400 == 0)
                    Leap = true;
                else {
                    Leap = false;
                }
            }
        });
        monthPv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                monthStr = text.substring(0, 2);
                switch (Integer.valueOf(monthStr)) {
                    case 1:
                        dayPv.setData(bigDays);
                        break;
                    case 2:
                        if (Leap) {
                            if (days_two.size() == 29) {
                                days_two.remove("29");
                            }
                        } else {
                            if (days_two.size() < 29) {
                                days_two.add(28, "29");
                            }
                        }
                        dayPv.setData(days_two);
                        break;
                    case 3:
                        dayPv.setData(bigDays);
                        break;
                    case 4:
                        dayPv.setData(littleDays);
                        break;
                    case 5:
                        dayPv.setData(bigDays);
                        break;
                    case 6:
                        dayPv.setData(littleDays);
                        break;
                    case 7:
                        dayPv.setData(bigDays);
                        break;
                    case 8:
                        dayPv.setData(bigDays);
                        break;
                    case 9:
                        dayPv.setData(littleDays);
                        break;
                    case 10:
                        dayPv.setData(bigDays);
                        break;
                    case 11:
                        dayPv.setData(littleDays);
                        break;
                    case 12:
                        dayPv.setData(bigDays);
                        break;
                }
            }
        });

        dayPv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                dayStr = text.substring(0, 2);
            }
        });

    }


    private void initDate() {

        for (int i = 1980; i < 2016; i++) {
            years.add("" + i + " 年");
        }
        for (int i = 1; i < 13; i++) {
            if (i < 10) {
                months.add("0" + i + " 月");
            } else

                months.add("" + i + " 月");
        }
        for (int i = 1; i < 32; i++) {
            bigDays.add(i < 10 ? "0" + i + " 日" : "" + i + " 日");
        }
        for (int i = 1; i < 31; i++) {
            littleDays.add(i < 10 ? "0" + i + " 日" : "" + i + " 日");
        }
        for (int i = 1; i < 30; i++) {
            days_two.add(i < 10 ? "0" + i + " 日" : "" + i + " 日");
        }
        yearPv.setData(years);
        monthPv.setData(months);
        dayPv.setData(bigDays);

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
                message.obj=yearStr + "-" + monthStr + "-" + dayStr;
                message.what=2;
                mHandler.sendMessage(message);
                dismiss();
                break;
        }
    }
}
