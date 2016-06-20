package com.woniukeji.jianmerchant.affordwages;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.eventbus.PayPassWordEvent;
import com.woniukeji.jianmerchant.utils.SPUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by invinjun on 2016/4/19.
 */
public class Mdialog extends Dialog {

        private Context context = null;

        private EditText passEText;
        private TextView titleText;
        private Button okBtn;
        private Button cancelBtn;

        public static Mdialog createBuilder(Context context) {
            return new Mdialog(context);
        }

        public Mdialog(Context context) {
            this(context, R.style.myDialogTheme);
            this.context = context;

        }

        public Mdialog(Context context, boolean cancelable,
                                OnCancelListener cancelListener) {

            super(context, cancelable, cancelListener);
            this.context = context;
        }

        public Mdialog(Context context, int theme) {

            super(context, theme);
            this.context = context;
            setDialogContentView();
        }

        /**
         * 设置dialog里面的view
         */
        private void setDialogContentView()
        {

            // 加载自己定义的布局
            View view = LayoutInflater.from(context).inflate(R.layout.save_dialog, null);
            titleText = (TextView) view.findViewById(R.id.title_text);
            passEText = (EditText) view.findViewById(R.id.et_pass);
            okBtn = (Button) view.findViewById(R.id.ok_btn);
            cancelBtn = (Button) view.findViewById(R.id.cancel_btn);


            final String password = (String) SPUtils.getParam(context, Constants.USER_INFO, Constants.USER_PAY_PASS, "");
            cancelBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Mdialog.this.dismiss();

                }
            });
            okBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (passEText.getText().toString().equals(password)){
                        PayPassWordEvent payPassWordEvent=new PayPassWordEvent();
                        payPassWordEvent.isCorrect=true;
                        EventBus.getDefault().post(payPassWordEvent);
                        Mdialog.this.dismiss();
                    }else {
//                        PayPassWordEvent payPassWordEvent=new PayPassWordEvent();
//                        payPassWordEvent.isCorrect=false;
//                        EventBus.getDefault().post(payPassWordEvent);
                        Toast.makeText(context,"密码输入错误，请重新输入",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            setContentView(view);

        }

        /**
         * 设置ok按钮监听
         */
        public void setOKOnClickListener(View.OnClickListener listener){
            okBtn.setOnClickListener(listener);
        }

        /**
         * 设置显示的文字内容（传入string）
         * @param msg
         */
        public Mdialog setMsg(String msg) {

//            if (null != msgText) {
//                msgText.setText(msg);
//            }
            return this;
        }

        /**
         * 设置显示的文字内容（传入resId）
         */
        public Mdialog setMsg(int resId) {

//            if (null != msgText) {
//                msgText.setText(context.getString(resId));
//            }
            return this;
        }

        /**
         * 设置显示的标题（传入string）
         */
        public Mdialog setAlertTitle(String t) {

            if (null != titleText) {
                titleText.setText(t);
            }
            return this;
        }

        /**
         * 设置显示的标题（传入resId）
         */
        public Mdialog setAlertTitle(int resId) {

            if (null != titleText) {
                titleText.setText(context.getString(resId));
            }

            return this;

        }

}
