package com.woniukeji.jianguo.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.User;
import com.woniukeji.jianguo.main.MainActivity;

import java.lang.ref.WeakReference;

/**
 * Created by invinjun on 2016/3/7.
 */
public class ResumeActivity extends BaseActivity {
    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private int MSG_PHONE_SUCCESS = 2;
    private int MSG_REGISTER_SUCCESS = 3;
    private Handler mHandler=new Myhandler(this);
    private Context context= ResumeActivity.this;
    private static class Myhandler extends Handler{
        private WeakReference<Context> reference;
        public Myhandler(Context context){
            reference=new WeakReference<>(context);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ResumeActivity resumeActivity= (ResumeActivity) reference.get();
            switch (msg.what) {
                case 0:
                    BaseBean<User> user = (BaseBean<User>) msg.obj;
                    resumeActivity.showShortToast("登录成功！");
                    Intent intent=new Intent(resumeActivity,MainActivity.class);
//                    intent.putExtra("user",user);
                    break;
                case 1:
                    String ErrorMessage= (String) msg.obj;
                    Toast.makeText(resumeActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:

                    break;
                case 3:
                    String sms= (String) msg.obj;
                    Toast.makeText(resumeActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_resume);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {

    }
}
