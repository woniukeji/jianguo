package com.woniukeji.jianmerchant.partjob;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.affordwages.PassWordActivity;
import com.woniukeji.jianmerchant.base.BaseFragment;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.Jobs;
import com.woniukeji.jianmerchant.publish.PublishActivity;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class PartJobFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.img_share) ImageView imgShare;
    @InjectView(R.id.img_top) ImageView imgTop;
    @InjectView(R.id.img_fabu) ImageView imgFabu;
    @InjectView(R.id.publish) RelativeLayout publish;
    @InjectView(R.id.img_gaunli) ImageView imgGaunli;
    @InjectView(R.id.management) RelativeLayout management;
    @InjectView(R.id.img_money) ImageView imgMoney;
    @InjectView(R.id.money_management) RelativeLayout moneyManagement;
    @InjectView(R.id.img_person) ImageView imgPerson;
    @InjectView(R.id.manpower) RelativeLayout manpower;
    @InjectView(R.id.rl_null) RelativeLayout rlNull;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int MSG_GET_SUCCESS = 0;
    private int MSG_GET_FAIL = 1;
    private int MSG_DELETE_SUCCESS = 5;
    private int MSG_DELETE_FAIL = 6;
    private Handler mHandler = new Myhandler(this.getActivity());
    private Context mContext = this.getActivity();
    private int loginId;
    private int delePosition;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.publish, R.id.management, R.id.money_management, R.id.manpower})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.publish:
                startActivity(new Intent(getActivity(), PublishActivity.class));
                break;
            case R.id.management:
                startActivity(new Intent(getActivity(), PartJobManagerActivity.class));
                break;
            case R.id.money_management:
                startActivity(new Intent(getActivity(), PassWordActivity.class));
                break;
            case R.id.manpower:
                break;
        }
    }


    private class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:

                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(getActivity(), ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(getActivity(), sms, Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    String mes = (String) msg.obj;
                    Toast.makeText(getActivity(), mes, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part_job, container, false);
        ButterKnife.inject(this, view);
        initview();
        return view;

    }

    private void initview() {
        tvTitle.setText("首页");
        imgBack.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Context context) {
        loginId = (int) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_USERID, 0);
//        GetTask getTask = new GetTask(String.valueOf(loginId));
//        getTask.execute();
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
