package com.woniukeji.jianguo.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseFragment;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.User;
import com.woniukeji.jianguo.main.MainActivity;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {
    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.title) TextView title;
    @InjectView(R.id.title_bar) RelativeLayout titleBar;
    @InjectView(R.id.img_head) ImageView imgHead;
    @InjectView(R.id.name) TextView name;
    @InjectView(R.id.school) TextView school;
    @InjectView(R.id.phone) TextView phone;
    @InjectView(R.id.lin_info) LinearLayout linInfo;
    @InjectView(R.id.account) RelativeLayout account;
    @InjectView(R.id.or_img) ImageView orImg;
    @InjectView(R.id.img) ImageView img;
    @InjectView(R.id.point_img) ImageView pointImg;
    @InjectView(R.id.ll_money) LinearLayout llMoney;
    @InjectView(R.id.ll_real_name) LinearLayout llRealName;
    private Handler mHandler = new Myhandler(this.getActivity());
    private Context context = this.getContext();

    @OnClick({R.id.ll_money, R.id.ll_real_name,R.id.img_back, R.id.title, R.id.img_head, R.id.school, R.id.phone, R.id.account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_money:
                break;
            case R.id.ll_real_name:
                Intent intent = new Intent(getActivity().getApplicationContext(), AuthActivity.class);
//                intent.putExtra("")
                startActivity(intent);
                break;
            case R.id.img_back:
                break;
            case R.id.title:
                break;
            case R.id.img_head:
                break;
            case R.id.school:
                break;
            case R.id.phone:
                break;
            case R.id.account:
                Intent intentRe = new Intent(getActivity().getApplicationContext(), ResumeActivity.class);
//                intent.putExtra("")
                startActivity(intentRe);
                break;
        }
    }


    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity mainActivity = (MainActivity) reference.get();
            switch (msg.what) {
                case 0:
                    BaseBean<User> user = (BaseBean<User>) msg.obj;
                    Intent intent = new Intent(mainActivity, MainActivity.class);
//                    intent.putExtra("user", user);
                    mainActivity.startActivity(intent);
                    mainActivity.finish();
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(mainActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:

                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(mainActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_mine, container, false);
        ButterKnife.inject(this, view);
        return view;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
