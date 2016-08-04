package com.woniukeji.jianguo.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.liulishuo.magicprogresswidget.MagicProgressCircle;
import com.woniukeji.jianguo.R;

import cn.dreamtobe.percentsmoothhandler.ISmoothTarget;

/**
 * Created by invinjun on 2016/4/19.
 */
public class CircleProDialog extends Dialog {

        private Context context = null;

        private MagicProgressCircle progressBar;
        private AnimTextView tvProgress;
        private TextView tvTitle;

        public static CircleProDialog createBuilder(Context context) {
            return new CircleProDialog(context);
        }

        public CircleProDialog(Context context) {
            this(context, R.style.DialogTheme);
            this.context = context;
            setDialogContentView();
        }


        public CircleProDialog(Context context, int theme) {
            super(context, theme);
            this.context = context;

        }

        /**
         * 设置dialog里面的view
         */
        private void setDialogContentView()
        {
            // 加载自己定义的布局
            View view = LayoutInflater.from(context).inflate(R.layout.circle_pro_dialog, null);
            setContentView(view);
            progressBar = (MagicProgressCircle) view.findViewById(R.id.progress_bar);
            tvProgress = (AnimTextView) view.findViewById(R.id.tv_progress);
            tvTitle= (TextView) view.findViewById(R.id.tv_title);
        }

        /**
         * 设置显示的文字内容（传入float）
         * @param progress
         */
        public CircleProDialog setMsg(double progress) {
            if (null != tvProgress) {
                progressBar.setSmoothPercent((float) progress);
                tvProgress.setText((int) Math.ceil(progress * 100)+"%");
            }
            return this;
        }

        /**
         * 设置显示的文字内容（传入resId）
         */
        public CircleProDialog setMsg(String title) {
            if (null != tvTitle) {
                tvTitle.setText(title);
            }
            return this;
        }

        /**
         * 设置显示的标题（传入string）
         */
        public CircleProDialog setAlertTitle(String t) {

            return this;
        }

        /**
         * 设置显示的标题（传入resId）
         */
        public CircleProDialog setAlertTitle(int resId) {
            return this;
        }
    private float getIncreasedPercent(ISmoothTarget target) {
        float increasedPercent = target.getPercent() + 0.1f;
        return Math.min(1, increasedPercent);
    }

}
