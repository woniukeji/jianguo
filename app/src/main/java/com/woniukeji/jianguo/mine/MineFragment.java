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

import com.squareup.picasso.Picasso;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseFragment;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.User;
import com.woniukeji.jianguo.main.MainActivity;
import com.woniukeji.jianguo.utils.CropCircleTransfermation;
import com.woniukeji.jianguo.utils.LogUtils;
import com.woniukeji.jianguo.utils.SPUtils;

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
    @InjectView(R.id.ll_wallte_realname) LinearLayout llWallteRealname;
    @InjectView(R.id.img_enter) ImageView imgEnter;
    @InjectView(R.id.credit) RelativeLayout credit;
    @InjectView(R.id.rl_evaluation) RelativeLayout rlEvaluation;
    @InjectView(R.id.ll_collect) LinearLayout llCollect;
    @InjectView(R.id.rl_point) RelativeLayout rlPoint;
    @InjectView(R.id.rl_feedback) RelativeLayout rlFeedback;
    @InjectView(R.id.rl_setting) RelativeLayout rlSetting;
    private Handler mHandler = new Myhandler(this.getActivity());
    private Context context = getActivity();


    @OnClick({R.id.ll_money,R.id.account, R.id.ll_real_name,R.id.credit, R.id.rl_evaluation, R.id.ll_collect, R.id.rl_point, R.id.rl_feedback, R.id.rl_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.credit:
                break;
            case R.id.rl_evaluation:
                Intent intentEvluation = new Intent(getActivity().getApplicationContext(), EvaluationActivity.class);
                startActivity(intentEvluation);
                break;
            case R.id.ll_collect:
                Intent intentColl = new Intent(getActivity().getApplicationContext(), CollectActivity.class);
                startActivity(intentColl);
                break;
            case R.id.rl_point:
                break;
            case R.id.rl_feedback:
                Intent intentFeed = new Intent(getActivity().getApplicationContext(), FeedBackActivity.class);
                startActivity(intentFeed);
                break;
            case R.id.rl_setting:
                Intent intentSet = new Intent(getActivity().getApplicationContext(), SettingActivity.class);
                startActivity(intentSet);
                break;
            case R.id.ll_money:
                break;
            case R.id.ll_real_name:
                Intent intent = new Intent(getActivity().getApplicationContext(), AuthActivity.class);
                startActivity(intent);
                break;
            case R.id.account:
                Intent intentRe = new Intent(getActivity().getApplicationContext(), ResumeActivity.class);
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
    public void onStart() {
        String nick = (String) SPUtils.getParam(getActivity(), Constants.USER_INFO, Constants.SP_NICK, "");
        String schoolStr = (String) SPUtils.getParam(getActivity(), Constants.USER_INFO, Constants.SP_SCHOOL, "");
        String tel = (String) SPUtils.getParam(getActivity(), Constants.USER_INFO, Constants.SP_TEL, "");
        String img= (String)SPUtils.getParam(getActivity(), Constants.USER_INFO,Constants.SP_IMG,"");
        imgBack.setVisibility(View.GONE);
        name.setText(nick);
        school.setText(schoolStr);
        phone.setText(tel);
        Picasso.with(getActivity()).load(img)
                .placeholder(R.mipmap.icon_head_defult)
                .error(R.mipmap.icon_head_defult)
                .transform(new CropCircleTransfermation())
                .into(imgHead);
        super.onStart();
        LogUtils.i("fragment","mine:onstart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i("fragment","mine:onresum");
    }

    @Override
    public void onDestroy() {
        LogUtils.i("fragment","mine:ondestroy");
        super.onDestroy();
    }
}
