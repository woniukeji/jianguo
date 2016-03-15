package com.woniukeji.jianguo.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.widget.time.PickerDateView;
import com.woniukeji.jianguo.widget.time.PickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class TimePickerActivity extends Activity  {
    @InjectView(R.id.year_pv) PickerDateView yearPv;
    @InjectView(R.id.month_pv) PickerView monthPv;
    @InjectView(R.id.day_pv) PickerView dayPv;
    @InjectView(R.id.rl_pick) RelativeLayout rlPick;
    @InjectView(R.id.button) Button button;
    private List<String> years = new ArrayList<String>();
    private List<String> months = new ArrayList<String>();
    private List<String> bigDays = new ArrayList<String>();//big
    private List<String> littleDays = new ArrayList<String>();//little
    private List<String> days_two = new ArrayList<String>();//2月
    private boolean Leap = false;
    private String yearStr="1998";
    private String monthStr="07";
    private String dayStr="16";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        ButterKnife.inject(this);
        initListeners();
        initDate();
    }


    public void initListeners() {

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
                String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
                String[] months_little = {"4", "6", "9", "11"};
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




    @OnClick(R.id.button)
    public void onClick() {
        Intent it = this.getIntent();
        it.putExtra("date", yearStr + "-" + monthStr + "-" + dayStr);
        setResult(1, it);
        finish();
    }
}
