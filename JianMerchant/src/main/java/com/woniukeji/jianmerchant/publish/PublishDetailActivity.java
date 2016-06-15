package com.woniukeji.jianmerchant.publish;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.CityCategory;
import com.woniukeji.jianmerchant.entity.JobDetails;
import com.woniukeji.jianmerchant.entity.Jobs;
import com.woniukeji.jianmerchant.entity.Model;
import com.woniukeji.jianmerchant.entity.PickType;
import com.woniukeji.jianmerchant.utils.BitmapUtils;
import com.woniukeji.jianmerchant.utils.CommonUtils;
import com.woniukeji.jianmerchant.utils.CropCircleTransfermation;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.MD5Coder;
import com.woniukeji.jianmerchant.utils.QiNiu;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.woniukeji.jianmerchant.widget.CircleImageView;
import com.woniukeji.jianmerchant.widget.DatePickerPopuWin;
import com.woniukeji.jianmerchant.widget.TimePickerPopuWin;
import com.woniukeji.jianmerchant.widget.TypePickerPopuWin;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.Response;

public class PublishDetailActivity extends BaseActivity {


    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 9;
    @InjectView(R.id.img_job) CircleImageView imgJob;
    @InjectView(R.id.tv_loc) TextView tvLoc;
    @InjectView(R.id.split) ImageView split;
    @InjectView(R.id.img) ImageView img;
    @InjectView(R.id.tv_publish_location) TextView tvPublishLocation;
    @InjectView(R.id.rl_location) RelativeLayout rlLocation;
    @InjectView(R.id.tv_lev) TextView tvLev;
    @InjectView(R.id.level_split) ImageView levelSplit;
    @InjectView(R.id.level_img) ImageView levelImg;
    @InjectView(R.id.tv_hot) TextView tvHot;
    @InjectView(R.id.rl_part_level) RelativeLayout rlPartClass;
    @InjectView(R.id.tv_cate) TextView tvCate;
    @InjectView(R.id.categorysplit) ImageView categorysplit;
    @InjectView(R.id.img_category) ImageView imgCategory;
    @InjectView(R.id.tv_category) TextView tvCategory;
    @InjectView(R.id.rl_category) RelativeLayout rlCategory;
    @InjectView(R.id.tv_ti) TextView tvTi;
    @InjectView(R.id.titlesplit) ImageView titlesplit;
    @InjectView(R.id.et_title) EditText etTitle;
    @InjectView(R.id.rl_title) RelativeLayout rlTitle;
    @InjectView(R.id.tv_pay) TextView tvPay;
    @InjectView(R.id.paysplit) ImageView paysplit;
    @InjectView(R.id.img_title) ImageView imgTitle;
    @InjectView(R.id.tv_pay_method) TextView tvPayMethod;
    @InjectView(R.id.rl_pay_method) RelativeLayout rlPayMethod;
    @InjectView(R.id.tv_wa) TextView tvWa;
    @InjectView(R.id.wagesplit) ImageView wagesplit;
    @InjectView(R.id.tv_wages_method) TextView tvWagesMethod;
    @InjectView(R.id.et_wages) EditText etWages;
    @InjectView(R.id.rl_wages) RelativeLayout rlWages;
    @InjectView(R.id.tv_s) TextView tvS;
    @InjectView(R.id.sex_split) ImageView sexSplit;
    @InjectView(R.id.img_sex) ImageView imgSex;
    @InjectView(R.id.tv_sex) TextView tvSex;
    @InjectView(R.id.rl_sex) RelativeLayout rlSex;
    @InjectView(R.id.tv_cou) TextView tvCou;
    @InjectView(R.id.count_split) ImageView countSplit;
    @InjectView(R.id.et_boy_count) EditText etBoyCount;
    @InjectView(R.id.tv_boy_unit) TextView tvBoyUnit;
    @InjectView(R.id.et_girl_count) EditText etGirlCount;
    @InjectView(R.id.rl_limits) RelativeLayout rlLimits;
    @InjectView(R.id.count_split1) ImageView countSplit1;
    @InjectView(R.id.et_count) EditText etCount;
    @InjectView(R.id.rl_no_limits) RelativeLayout rlNoLimits;
    @InjectView(R.id.rl_count) RelativeLayout rlCount;
    @InjectView(R.id.tv_pos) TextView tvPos;
    @InjectView(R.id.position_split) ImageView positionSplit;
    @InjectView(R.id.img_loc) ImageView imgLoc;
    @InjectView(R.id.tv_position) TextView tvPosition;
    @InjectView(R.id.et_detail_position) EditText etDetailPosition;
    @InjectView(R.id.rl_position) RelativeLayout rlPosition;
    @InjectView(R.id.tv_da) TextView tvDa;
    @InjectView(R.id.datesplit) ImageView datesplit;
    @InjectView(R.id.tv_date_start) TextView tvDateStart;
    @InjectView(R.id.tv_center) TextView tvCenter;
    @InjectView(R.id.tv_date_end) TextView tvDateEnd;
    @InjectView(R.id.rl_date) RelativeLayout rlDate;
    @InjectView(R.id.tv_tim) TextView tvTim;
    @InjectView(R.id.timesplit) ImageView timesplit;
    @InjectView(R.id.tv_time_start) TextView tvTimeStart;
    @InjectView(R.id.tv_time_center) TextView tvTimeCenter;
    @InjectView(R.id.tv_time_end) TextView tvTimeEnd;
    @InjectView(R.id.rl_time) RelativeLayout rlTime;
    @InjectView(R.id.tv_coll) TextView tvColl;
    @InjectView(R.id.collectionsplit) ImageView collectionsplit;
    @InjectView(R.id.et_collection_position) EditText etCollectionPosition;
    @InjectView(R.id.rl_collection_position) RelativeLayout rlCollectionPosition;
    @InjectView(R.id.tv_collti) TextView tvCollti;
    @InjectView(R.id.colltimesplit) ImageView colltimesplit;
    @InjectView(R.id.img_collection_time) ImageView imgCollectionTime;
    @InjectView(R.id.et_collection_time) EditText etCollectionTime;
    @InjectView(R.id.rl_collection_time) RelativeLayout rlCollectionTime;
    @InjectView(R.id.tv_t) TextView tvT;
    @InjectView(R.id.telsplit) ImageView telsplit;
    @InjectView(R.id.et_tel) EditText etTel;
    @InjectView(R.id.rl_tel) RelativeLayout rlTel;
    @InjectView(R.id.et_work_content) EditText etWorkContent;
    @InjectView(R.id.et_work_require) EditText etWorkRequire;
    @InjectView(R.id.btn_preview) Button btnPreview;
    @InjectView(R.id.btn_save) Button btnSave;
    @InjectView(R.id.btn_publish) Button btnPublish;
    @InjectView(R.id.btn_change) Button btnChange;
    @InjectView(R.id.ll_change) LinearLayout llChange;
    @InjectView(R.id.ll_publish) LinearLayout llPublish;
    //顺序对应不能改变，否则id和服务器不同步
    private String[] partHot = new String[]{"短期", "长期", "实习生", "旅行"};
    private String[] payMethods = new String[]{"月结", "周结", "日结", "旅行"};
    private String[] wagesMethods = new String[]{"元/月", "元/周", "元/天", "元/小时", "元/次", "义工","面议"};
    private String[] sexs = new String[]{"仅限女", "仅限男", "不限男女", "男女各需"};
    private String[] second = new String[]{"00","05","10","15","20","25","30","35","40","45","50","55"};
    BaseBean<CityCategory> cityCategoryBaseBean;
    private JobDetails.TMerchantEntity merchantInfo = new JobDetails.TMerchantEntity();
    private JobDetails.TJobInfoEntity jobinfo = new JobDetails.TJobInfoEntity();
    private int MSG_POST_SUCCESS = 0;
    private int MSG_POST_FAIL = 1;
    private int MSG_PHONE_SUCCESS = 2;
    private int MSG_REGISTER_SUCCESS = 3;
    private Handler mHandler = new Myhandler(this);
    private Context context = PublishDetailActivity.this;
    private String city_id = "";// 城市ID

    private String aera_id = "";//地区ID

    private String type_id = "";//兼职类型ID

    private String merchant_id = "";//    商家ID

    private String name = "";//      兼职名称

    private String name_image = "http://v3.jianguojob.com/logo.png";//    兼职图片

    private String start_date = "";//    开始日期

    private String stop_date = "";//  结束日期

    private String address = "";//        地址

    private String mode;//   结算方式（0=月结，1=周结，2=日结，3=旅行）

    private String money;//        钱

    private String term = "2";//    期限（0=月结，1=周结，2=日结，3=小时结，4=次，5=义工）给默认值

    private String limit_sex = "2";//         性别限制（0=只招女，1=只招男，2=不限男女）

    private String sum;//        总人数
    private String girlsum;//
    private String hot;//        热门（0=普通，1=热门，2=精品，3=旅行）

    private String alike;//        相同（0=一条，时间戳=两条）

    private String tel;//         电话

    private String start_time;//        开始时间

    private String stop_time;//         结束时间

    private String set_place;//        集合地点

    private String set_time;//        集合时间

    private String other;//        其他

    private String work_content;//    工作内容

    private String work_require;//    工作要求
    private Context mContext = PublishDetailActivity.this;
    private int merchantid;
    private SweetAlertDialog sweetAlertDialog;
    private int MSG_GET_SUCCESS = 4;
    private int MSG_GET_FAIL = 5;
    private int MSG_GET_SINGLE_JOB_SUCESS = 6;
    private Model.ListTJobEntity listTJobEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;


        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PublishDetailActivity activity = (PublishDetailActivity) reference.get();
            switch (msg.what) {
                case 0:
                    if (activity.sweetAlertDialog.isShowing()) {
                        activity.sweetAlertDialog.dismiss();
                    }
                    String sucessMessage = "操作成功！";
                    Toast.makeText(activity, sucessMessage, Toast.LENGTH_SHORT).show();
                    activity.finish();
                    break;
                case 1:
                    if (activity.sweetAlertDialog.isShowing()) {
                        activity.sweetAlertDialog.dismiss();
                    }
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(activity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;

                case 4:
                    activity.cityCategoryBaseBean = (BaseBean<CityCategory>) msg.obj;
                    break;
                case 5:
                    String Message = (String) msg.obj;
                    Toast.makeText(activity, Message, Toast.LENGTH_SHORT).show();
                    activity.finish();
                    break;
                case 6:
                    BaseBean<Model> obj = (BaseBean<Model>) msg.obj;
                    activity.listTJobEntity = obj.getData().getT_job();
                    activity.initModleData(activity.listTJobEntity);
                    break;
                case 2:
                    //选择器返回字符
                    String size = (String) msg.obj;
                    int type = msg.arg1;
                    switch (type) {

                        case 7://开始日期
                            activity.tvDateStart.setText(size);
                            activity.start_date = String.valueOf(DateUtils.getLongTime(size));
                            break;
                        case 8:
                            activity.tvDateEnd.setText(size);
                            activity.stop_date = String.valueOf(DateUtils.getLongTime(size));
                            break;
                        case 9:
                            StringBuilder sb1=new StringBuilder();
                            sb1.append(size).insert(2, ":");
                            activity.tvTimeStart.setText(sb1);
                            activity.start_time = size;
                            break;
                        case 10:
                            StringBuilder sb=new StringBuilder();
                            sb.append(size).insert(2, ":");
                            activity.tvTimeEnd.setText(sb);
                            activity.stop_time = size;
                            break;
                        case 11:
                            activity.etCollectionTime.setText(size);
                            activity.set_time = size;
                            break;

                    }
                    break;
                case 3:
                    //选择器返回字符
                    PickType pickType = (PickType) msg.obj;
                    int mType = msg.arg1;
                    switch (mType) {
                        case 0://区域
                            activity.tvPublishLocation.setText(pickType.getPickName());
                            activity.tvPublishLocation.setTextColor(activity.getResources().getColor(R.color.black));
                            activity.tvPosition.setText("");//选择城市的时候区域设置为空
                            activity.tvPosition.setHint("请选择工作地点");
                            activity.tvPosition.setHintTextColor(activity.getResources().getColor(R.color.hint));
                            activity.city_id = pickType.getPickId();
                            break;
                        case 1://兼职级别
                            activity.tvHot.setText(pickType.getPickName());
                            activity.tvHot.setTextColor(activity.getResources().getColor(R.color.black));
                            activity.hot = pickType.getPickId();
                            break;
                        case 2://兼职种类
                            activity.tvCategory.setText(pickType.getPickName());
                            activity.tvCategory.setTextColor(activity.getResources().getColor(R.color.black));
                            activity.type_id = pickType.getPickId();
                            break;
                        case 3://结算方式
                            activity.tvPayMethod.setText(pickType.getPickName());
                            activity.tvPayMethod.setTextColor(activity.getResources().getColor(R.color.black));
                            activity.mode = pickType.getPickId();
                            break;
                        case 4://薪酬
                            activity.tvWagesMethod.setText(pickType.getPickName());
                            activity.term = pickType.getPickId();
                            break;
                        case 5://性别选择
                            activity.tvSex.setText(pickType.getPickName());
                            activity.tvSex.setTextColor(activity.getResources().getColor(R.color.black));
                            if (pickType.getPickName().equals("男女各需")) {
                                activity.rlLimits.setVisibility(View.VISIBLE);
                                activity.rlNoLimits.setVisibility(View.GONE);
                            } else {
                                activity.rlLimits.setVisibility(View.GONE);
                                activity.rlNoLimits.setVisibility(View.VISIBLE);
                            }
                            activity.limit_sex = pickType.getPickId();
                            break;
                        case 6://具体位置
                            activity.tvPosition.setText(pickType.getPickName());
                            activity.tvPosition.setTextColor(activity.getResources().getColor(R.color.black));
                            activity.aera_id = pickType.getPickId();
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @OnClick({R.id.img_job,R.id.btn_change, R.id.rl_location, R.id.rl_part_level, R.id.rl_category, R.id.rl_pay_method, R.id.tv_wages_method, R.id.rl_sex, R.id.rl_position, R.id.tv_date_start, R.id.tv_date_end, R.id.tv_time_start, R.id.tv_time_end, R.id.rl_collection_time, R.id.btn_preview, R.id.btn_save, R.id.btn_publish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_job:
                //单选多选,requestCode,最多选择数，单选模式
                MultiImageSelectorActivity.startSelect(PublishDetailActivity.this, 0, 1, 0);
                break;

            case R.id.rl_location:
                List<PickType> listLoc = new ArrayList<>();
                for (int i = 0; i < cityCategoryBaseBean.getData().getList_t_city2().size(); i++) {
                    PickType pickType = new PickType();
                    pickType.setPickId(String.valueOf(cityCategoryBaseBean.getData().getList_t_city2().get(i).getId()));
                    pickType.setPickName(cityCategoryBaseBean.getData().getList_t_city2().get(i).getCity());
                    listLoc.add(pickType);
                }
                TypePickerPopuWin pickerLoc = new TypePickerPopuWin(context, listLoc, mHandler, 0);
                pickerLoc.showShareWindow();
                pickerLoc.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.rl_part_level:
                List<PickType> listTemp = new ArrayList<>();
                for (int i = 0; i < partHot.length; i++) {
                    PickType pickType = new PickType();
                    pickType.setPickId(String.valueOf(i));
                    pickType.setPickName(partHot[i]);
                    listTemp.add(pickType);
                }
                TypePickerPopuWin pickerPopup = new TypePickerPopuWin(context, listTemp, mHandler, 1);
                pickerPopup.showShareWindow();
                pickerPopup.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.rl_category:
                List<PickType> listCate = new ArrayList<>();
                for (int i = 0; i < cityCategoryBaseBean.getData().getList_t_type().size(); i++) {
                    PickType pickType = new PickType();
                    pickType.setPickId(String.valueOf(cityCategoryBaseBean.getData().getList_t_type().get(i).getId()));
                    pickType.setPickName(cityCategoryBaseBean.getData().getList_t_type().get(i).getType_name());
                    listCate.add(pickType);
                }
                TypePickerPopuWin pickerCategory = new TypePickerPopuWin(context, listCate, mHandler, 2);
                pickerCategory.showShareWindow();
                pickerCategory.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.rl_pay_method:
                List<PickType> listpay = new ArrayList<>();
                for (int i = 0; i < payMethods.length; i++) {
                    PickType pickType = new PickType();
                    pickType.setPickId(String.valueOf(i));
                    pickType.setPickName(payMethods[i]);
                    listpay.add(pickType);
                }
                TypePickerPopuWin pickerPayMethod = new TypePickerPopuWin(context, listpay, mHandler, 3);
                pickerPayMethod.showShareWindow();
                pickerPayMethod.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_wages_method:
                List<PickType> listwage = new ArrayList<>();
                for (int i = 0; i < wagesMethods.length; i++) {
                    PickType pickType = new PickType();
                    pickType.setPickId(String.valueOf(i));
                    pickType.setPickName(wagesMethods[i]);
                    listwage.add(pickType);
                }
                TypePickerPopuWin pickerWagesMethod = new TypePickerPopuWin(context, listwage, mHandler, 4);
                pickerWagesMethod.showShareWindow();
                pickerWagesMethod.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.rl_sex:
                List<PickType> listSex = new ArrayList<>();
                for (int i = 0; i < sexs.length; i++) {
                    PickType pickType = new PickType();
                    pickType.setPickId(String.valueOf(i));
                    pickType.setPickName(sexs[i]);
                    listSex.add(pickType);
                }
                TypePickerPopuWin pickerSex = new TypePickerPopuWin(context, listSex, mHandler, 5);
                pickerSex.showShareWindow();
                pickerSex.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;

            case R.id.rl_position:
                //城市id选择后才能选择区域
                if (city_id != null && !city_id.equals("")) {
                    List<PickType> listAre = new ArrayList<>();
                    for (int i = 0; i < cityCategoryBaseBean.getData().getList_t_city2().get(Integer.parseInt(city_id) - 1).getList_t_area().size(); i++) {
                        PickType pickType = new PickType();
                        pickType.setPickId(String.valueOf(cityCategoryBaseBean.getData().getList_t_city2().get(Integer.parseInt(city_id) - 1).getList_t_area().get(i).getId()));
                        pickType.setPickName(cityCategoryBaseBean.getData().getList_t_city2().get(Integer.parseInt(city_id) - 1).getList_t_area().get(i).getArea_name());
                        listAre.add(pickType);
                    }
                    TypePickerPopuWin pickerAre = new TypePickerPopuWin(context, listAre, mHandler, 6);
                    pickerAre.showShareWindow();
                    pickerAre.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                            Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

                } else
                    showShortToast("请先选择发布区域");
                break;
            case R.id.tv_date_start:
                DatePickerPopuWin pickerPopup1 = new DatePickerPopuWin(context, mHandler, 7);
                pickerPopup1.showShareWindow();
                pickerPopup1.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_date_end:
                DatePickerPopuWin pickerPopup2 = new DatePickerPopuWin(context, mHandler, 8);
                pickerPopup2.showShareWindow();
                pickerPopup2.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_time_start:
                List<String> listSecond = Arrays.asList(second);
                List<String> secondlist = new ArrayList<String>(listSecond);
                List<String> hourlist = new ArrayList<String>();
                for (int i = 1; i < 25; i++) {
                    if (i<10){
                        hourlist.add("0"+String.valueOf(i));
                    }else
                        hourlist.add(String.valueOf(i));
                }
                TimePickerPopuWin pickerTimeS = new TimePickerPopuWin(context, hourlist, secondlist, mHandler, 9);
                pickerTimeS.showShareWindow();
                pickerTimeS.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_time_end:
                List<String> listSecondE = Arrays.asList(second);
                List<String> secondlistE = new ArrayList<String>(listSecondE);
                List<String> hourlistE = new ArrayList<String>();
                for (int i = 1; i < 25; i++) {
                    if (i<10){
                        hourlistE.add("0"+String.valueOf(i));
                    }else
                        hourlistE.add(String.valueOf(i));
                }
                TimePickerPopuWin pickerTimeE = new TimePickerPopuWin(context, hourlistE, secondlistE, mHandler, 10);
                pickerTimeE.showShareWindow();
                pickerTimeE.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.rl_collection_time:
//                DatePickerPopuWin pickerPopup5=new DatePickerPopuWin(context,mHandler,11);
//                pickerPopup5.showShareWindow();
//                pickerPopup5.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
//                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.btn_preview:

                if (CheckStatus()) {
                    jobinfo.setTitle(etTitle.getText().toString());
                    jobinfo.setAddress(tvPublishLocation.getText().toString()+tvPosition.getText().toString()+etDetailPosition.getText().toString());
                    long startDate = DateUtils.getLongTime(tvDateStart.getText().toString());
                    long stopDate = DateUtils.getLongTime(tvDateEnd.getText().toString());
                    jobinfo.setStart_date(startDate);
                    jobinfo.setStop_date(stopDate);
                    jobinfo.setLimit_sex(Integer.valueOf(limit_sex));
                    jobinfo.setSet_place(etCollectionPosition.getText().toString());
                    jobinfo.setTerm(Integer.valueOf(term));
                    jobinfo.setStart_time(start_time);
                    jobinfo.setStop_time(stop_time);
                    jobinfo.setSet_time(set_time);
                    jobinfo.setWork_content(etWorkContent.getText().toString());
                    jobinfo.setWork_require(etWorkRequire.getText().toString());
                    jobinfo.setWages(money + tvWagesMethod.getText().toString());
                    if (limit_sex.equals("3")) {
                        int sum = Integer.valueOf(etGirlCount.getText().toString()) + Integer.valueOf(etBoyCount.getText().toString());
                        jobinfo.setSum(sum + "人");
                    } else {
                        jobinfo.setSum(etCount.getText().toString() + "人");
                    }


                    String merchantName = (String) SPUtils.getParam(mContext, Constants.USER_INFO, Constants.USER_NAME, "");
                    merchantInfo.setName(merchantName);
                    merchantInfo.setName_image(name_image);
                    Intent intent = new Intent(PublishDetailActivity.this, PreviewJobActivity.class);
                    intent.putExtra("jobinfo", jobinfo);
                    intent.putExtra("merchantinfo", merchantInfo);
                    startActivity(intent);
                }

                break;
            case R.id.btn_save:
                if (CheckStatus()) {

                    new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("保存为模板")
                            .setContentEdit("请输入您的模板标题")
                            .setCancelText("取消")
                            .setConfirmText("保存")
                            .showCancelButton(true)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.cancel();
                                    String job_model = sweetAlertDialog.getEditContent();
                                    alike = "0";
                                    if (limit_sex.equals("3")) {
                                        alike = "0";
                                        PostPartInfoTask postPartInfo = new PostPartInfoTask("0", limit_sex, job_model);
                                        postPartInfo.execute();
                                    } else {
                                        alike = "0";
                                        PostPartInfoTask postPartInfo = new PostPartInfoTask(etCount.getText().toString(), limit_sex, job_model);
                                        postPartInfo.execute();
                                    }

                                }
                            })
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.cancel();
                                }
                            })
                            .show();
                }

                break;
            case R.id.btn_publish:

                if (CheckStatus()) {
                    if (limit_sex.equals("3")) {
                        alike = String.valueOf(System.currentTimeMillis());
                        PostPartInfoTask postPartInfoTask = new PostPartInfoTask(etBoyCount.getText().toString(), "31");
                        postPartInfoTask.execute();
                        PostPartInfoTask postPartInfo = new PostPartInfoTask(etGirlCount.getText().toString(), "30");
                        postPartInfo.execute();
                    } else {
                        alike = "0";
                        PostPartInfoTask postPartInfo = new PostPartInfoTask(etCount.getText().toString(), limit_sex);
                        postPartInfo.execute();
                    }
                }
                break;
            case R.id.btn_change:
                //修改
                        if (CheckStatus()) {
                            if (limit_sex.equals("3")) {
                        alike = String.valueOf(System.currentTimeMillis());
                        PostChangeJobTask postPartInfoTask = new PostChangeJobTask(etGirlCount.getText().toString(), "31",String.valueOf(listTJobEntity.getId()));
                        postPartInfoTask.execute();
                        PostChangeJobTask postPartInfo = new PostChangeJobTask(etBoyCount.getText().toString(), "30",String.valueOf(listTJobEntity.getNv_job_id()));
                        postPartInfo.execute();
                    } else {
                        alike = "0";
                        PostChangeJobTask postPartInfo = new PostChangeJobTask(etCount.getText().toString(), limit_sex,String.valueOf(listTJobEntity.getId()));
                        postPartInfo.execute();
                    }
                }
                break;
        }
    }

    private boolean CheckStatus() {
        if (etTitle.getText().toString() == null || etTitle.getText().toString().equals("")) {
            showShortToast("请输入兼职标题");
            return false;
        }else if (etTitle.getText().toString().length()>15) {
            showShortToast("兼职标题请控制在15字以内");
            return false;
        } else if (etDetailPosition.getText().toString() == null || etDetailPosition.getText().toString().equals("")) {
            showShortToast("请输入详细工作地址");
            return false;
        } else if (etCollectionPosition.getText().toString() == null || etCollectionPosition.getText().toString().equals("")) {
            showShortToast("请输入集合地点");
            return false;
        } else if (etCollectionTime.getText().toString() == null || etCollectionTime.getText().toString().equals("")) {
            showShortToast("请输入集合时间");
            return false;
        } else if (etTel.getText().toString() == null || etTel.getText().toString().equals("")) {
            showShortToast("请输入联系电话");
            return false;
        } else if (etWorkContent.getText().toString() == null || etWorkContent.getText().toString().equals("")) {
            showShortToast("请输入工作内容");
            return false;
        } else if (etWorkRequire.getText().toString() == null || etWorkRequire.getText().toString().equals("")) {
            showShortToast("请输入工作要求");
            return false;
        } else if (name_image == null || name_image.equals("")) {
            showShortToast("请设置兼职图片");
            return false;
        } else if (city_id == null || city_id.equals("")) {
            showShortToast("请选择发布城市");
            return false;
        } else if (etWages.getText().toString() == null || etWages.getText().toString().equals("")) {
            showShortToast("请填写薪资");
            return false;
        } else if (aera_id == null || aera_id.equals("")) {
            showShortToast("请选择工作地点");
            return false;
        } else if (mode == null || mode.equals("")) {
            showShortToast("请选择结算方式");
            return false;
        } else if (type_id == null || type_id.equals("")) {
            showShortToast("请选择兼职种类");
            return false;
        } else if (hot == null || hot.equals("")) {
            showShortToast("请选择兼职级别");
            return false;
        } else if (limit_sex == null || limit_sex.equals("")) {
            showShortToast("请选择录取性别");
            return false;
        } else if (start_date == null || start_date.equals("")) {
            showShortToast("请选择工作开始日期");
            return false;
        } else if (stop_date == null || stop_date.equals("")) {
            showShortToast("请选择工作结束日期");
            return false;
        } else if (start_time == null || start_time.equals("")) {
            showShortToast("请选择工作开始时间");
            return false;
        } else if (stop_time == null || stop_time.equals("")) {
            showShortToast("请选择工作结束时间");
            return false;
        } else if (limit_sex.equals("3")) {//男女分别招收
            if (etBoyCount.getText().toString().equals("") || etBoyCount.getText().toString() == null||etBoyCount.getText().toString().equals("0")) {
                showShortToast("请输入男生人数");
                return false;
            }
            if (etGirlCount.getText().toString().equals("") || etGirlCount.getText().toString() == null||etGirlCount.getText().toString().equals("0")) {
                showShortToast("请输入女生人数");
                return false;
            }
        } else if (!limit_sex.equals("3")) {
            if (etCount.getText().toString().equals("") || etCount.getText().toString() == null||etCount.getText().toString().equals("0")) {
                showShortToast("请输入需要录取的人数");
                return false;
            }
        }
        start_time= String.valueOf(DateUtils.getLongTime(tvDateStart.getText().toString()+" "+tvTimeStart.getText().toString(),"yyyy-MM-dd HH:mm"));
        stop_time=String.valueOf(DateUtils.getLongTime(tvDateEnd.getText().toString()+" "+tvTimeEnd.getText().toString(),"yyyy-MM-dd HH:mm"));
       if (DateUtils.getLongTime(tvDateStart.getText().toString()+" "+tvTimeStart.getText().toString(),"yyyy-MM-dd HH:mm")< System.currentTimeMillis()){
           showShortToast("开始时间早于当前时间，请重新选择");
           return false;
       }else if(DateUtils.getLongTime(tvDateEnd.getText().toString()+" "+tvTimeEnd.getText().toString(),"yyyy-MM-dd HH:mm")<DateUtils.getLongTime(tvDateStart.getText().toString()+" "+tvTimeStart.getText().toString(),"yyyy-MM-dd HH:mm")){
           showShortToast("结束时间早于开始时间，请重新选择");
           return false;
       }
        name = etTitle.getText().toString();
        money = etWages.getText().toString();
        address =etDetailPosition.getText().toString();
        set_place = etCollectionPosition.getText().toString();
        set_time = etCollectionTime.getText().toString();
        tel = etTel.getText().toString();
        work_content = etWorkContent.getText().toString();
        work_require = etWorkRequire.getText().toString();
            return true;
    }


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_publish_detail);
        ButterKnife.inject(this);
    }

    @Override
    public void initViews() {
        Intent intent = getIntent();
        String mType = intent.getStringExtra("type");
        if (mType.equals("old")) {
            Model.ListTJobEntity modle = (Model.ListTJobEntity) intent.getSerializableExtra("job");
            initModleData(modle);
        } else if (mType.equals("change")) {
            String jobid = intent.getStringExtra("jobid");
            llPublish.setVisibility(View.GONE);
            llChange.setVisibility(View.VISIBLE);
            GetSingleJobTask getSingleJobTask = new GetSingleJobTask(jobid);
            getSingleJobTask.execute();
        }
        sweetAlertDialog = new SweetAlertDialog(PublishDetailActivity.this, SweetAlertDialog.PROGRESS_TYPE);
    }

    private void initModleData(Model.ListTJobEntity modle) {
        tvPublishLocation.setText(modle.getCity_id_name());
        tvPublishLocation.setTextColor(getResources().getColor(R.color.black));
        city_id = String.valueOf(modle.getCity_id());
        tvPosition.setText(modle.getArea_id_name());
        tvPosition.setTextColor(getResources().getColor(R.color.black));
        aera_id = String.valueOf(modle.getArea_id());
        tvCategory.setText(modle.getType_id_name());
        tvCategory.setTextColor(getResources().getColor(R.color.black));
        type_id = String.valueOf(modle.getType_id());
        etTitle.setText(modle.getName());
        name_image = modle.getName_image();
        Picasso.with(mContext).load(name_image)
                .placeholder(R.mipmap.icon_head_defult)
                .error(R.mipmap.icon_head_defult)
                .transform(new CropCircleTransfermation())
                .into(imgJob);
        tvDateStart.setText(DateUtils.getTime(Long.parseLong(modle.getStart_date()), "yyyy-MM-dd"));
        tvDateEnd.setText(DateUtils.getTime(Long.parseLong(modle.getStop_date()), "yyyy-MM-dd"));
        start_date = modle.getStart_date();
        stop_date = modle.getStop_date();
        tvTimeStart.setText(DateUtils.getHm(Long.parseLong(modle.getInfo_start_time())));
        tvTimeEnd.setText(DateUtils.getHm(Long.parseLong(modle.getInfo_stop_time())));
        start_time = modle.getInfo_start_time();
        stop_time = modle.getInfo_stop_time();
        etDetailPosition.setText(modle.getAddress());
        tvPayMethod.setText(payMethods[modle.getMode()]);
        tvPayMethod.setTextColor(getResources().getColor(R.color.black));
        mode = String.valueOf(modle.getMode());
        etWages.setText(String.valueOf(modle.getMoney()));
        etWages.setTextColor(getResources().getColor(R.color.black));
        tvWagesMethod.setText(wagesMethods[modle.getTerm()]);
        term = String.valueOf(modle.getTerm());
        limit_sex = String.valueOf(modle.getLimit_sex());
        if (limit_sex.equals("30") || limit_sex.equals("31")) {
            limit_sex = "3";
            tvSex.setText("男女各需");
            rlLimits.setVisibility(View.VISIBLE);
            rlNoLimits.setVisibility(View.GONE);
        } else {
            tvSex.setText(sexs[modle.getLimit_sex()]);
            rlLimits.setVisibility(View.GONE);
            rlNoLimits.setVisibility(View.VISIBLE);
        }
        tvSex.setTextColor(getResources().getColor(R.color.black));
        sum = String.valueOf(modle.getSum());
        if (limit_sex.equals("30")) {
            etGirlCount.setText(sum);
        } else if (limit_sex.equals("31")) {
            etBoyCount.setText(sum);
        } else {
            etCount.setText(sum);
        }

        tvHot.setText(partHot[modle.getHot()]);
        hot = String.valueOf(modle.getHot());
        tvHot.setTextColor(getResources().getColor(R.color.black));
        etTel.setText(modle.getInfo_tel());

        etWorkContent.setText(modle.getInfo_work_content());
        etWorkRequire.setText(modle.getInfo_work_require());
        etCollectionPosition.setText(modle.getInfo_set_place());
        etCollectionTime.setText(modle.getInfo_set_time());


    }

    @Override
    public void initListeners() {
        int loginId = (int) SPUtils.getParam(mContext, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
        GetTask getTask = new GetTask(String.valueOf(loginId));
        getTask.execute();
    }

    @Override
    public void initData() {

        merchantid = (int) SPUtils.getParam(mContext, Constants.USER_INFO, Constants.USER_MERCHANT_ID, 0);

    }

    @Override
    public void addActivity() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                }
                return;
            }

        }
    }

    @Override
    protected void onDestroy() {
        ButterKnife.reset(this);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // 处理你自己的逻辑 ....
                File imgFile = new File(path.get(0));
                String choosePic = path.get(0).substring(path.get(0).lastIndexOf("."));
                String fileName = Constants.IMG_PATH + CommonUtils.generateFileName() + choosePic;
                Uri imgSource = Uri.fromFile(imgFile);
                imgJob.setImageURI(imgSource);
                File file = new File(Environment.getExternalStorageDirectory() + "/"+fileName+".png");
                CommonUtils.copyfile(imgFile,file,true);
                BitmapUtils.compressBitmap(file.getAbsolutePath(), 300, 300);
                QiNiu.upLoadQiNiu(context, MD5Coder.getQiNiuName(fileName), file);
                name_image = "http://7xlell.com2.z0.glb.qiniucdn.com/" + MD5Coder.getQiNiuName(fileName);
            }
        } else if (requestCode == 1) {
//            tvBirthday.setText(data.getStringExtra("date"));
        } else if (requestCode == 2) {
//            tvDate.setText(data.getStringExtra("date"));
        } else if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
//                tvSchool.setText(data.getStringExtra("school"));
            }

        }


    }

    public class GetSingleJobTask extends AsyncTask<Void, Void, Void> {
        private final String jobId;

        GetSingleJobTask(String jobId) {
            this.jobId = jobId;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                getSingleJobInfo();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * 获取城市列表和兼职种类
         */
        public void getSingleJobInfo() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.GET_SINGLE_JOB_INFO)
                    .addParams("only", only)
                    .addParams("job_id", jobId)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<Model>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<Model>>() {
                            }.getType());
                            return baseBean;
                        }

                        @Override
                        public void onError(Call call, Exception e) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_GET_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean baseBean) {
                            if (baseBean.getCode().equals("200")) {
                                Message message = new Message();
                                message.obj = baseBean;
                                message.what = MSG_GET_SINGLE_JOB_SUCESS;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_GET_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }

                    });
        }
    }

    public class GetTask extends AsyncTask<Void, Void, Void> {
        private final String loginId;

        GetTask(String loginId) {
            this.loginId = loginId;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                getCityCategory();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * 获取城市列表和兼职种类
         */
        public void getCityCategory() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.GET_CITY_CATEGORY)
                    .addParams("only", only)
                    .addParams("login_id", loginId)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<CityCategory>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<CityCategory>>() {
                            }.getType());
                            return baseBean;
                        }

                        @Override
                        public void onError(Call call, Exception e) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_GET_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean baseBean) {
                            if (baseBean.getCode().equals("200")) {
                                Message message = new Message();
                                message.obj = baseBean;
                                message.what = MSG_GET_SUCCESS;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_GET_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }

                    });
        }
    }

    public class PostPartInfoTask extends AsyncTask<Void, Void, Void> {
        private String mSum;
        private String mSex_limit;
        private String mjob_model = "0";

        PostPartInfoTask(String sum, String Sex_limit) {
            this.mSum = sum;
            this.mSex_limit = Sex_limit;
        }

        PostPartInfoTask(String sum, String Sex_limit, String job_model) {
            this.mSum = sum;
            this.mSex_limit = Sex_limit;
            this.mjob_model = job_model;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                postPartInfo();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            if (!sweetAlertDialog.isShowing()) {
                sweetAlertDialog.setTitleText("提交中");
                sweetAlertDialog.getProgressHelper().setBarColor(R.color.app_bg);
                sweetAlertDialog.show();
            }

            super.onPreExecute();
        }

        /**
         * 提交兼职信息
         */
        public void postPartInfo() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.POST_PART_INFO)
                    .addParams("only", only)
                    .addParams("city_id", city_id)
                    .addParams("aera_id", aera_id)
                    .addParams("type_id", type_id)
                    .addParams("merchant_id", String.valueOf(merchantid))
                    .addParams("name", name)
                    .addParams("name_image", name_image)
                    .addParams("start_date", start_date)
                    .addParams("stop_date", stop_date)
                    .addParams("address", address)
                    .addParams("mode", mode)
                    .addParams("money", money)
                    .addParams("term", term)
                    .addParams("limit_sex", mSex_limit)
                    .addParams("sum", mSum)
                    .addParams("max", hot)
                    .addParams("alike", alike)
                    .addParams("lon", "0")
                    .addParams("lat", "0")
                    .addParams("tel", tel)
                    .addParams("start_time", start_time)
                    .addParams("stop_time", stop_time)
                    .addParams("set_place", set_place)
                    .addParams("set_time", set_time)
                    .addParams("work_content", work_content)
                    .addParams("work_require", work_require)
                    .addParams("job_model", mjob_model)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<Jobs>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<Jobs>>() {
                            }.getType());
                            return baseBean;
                        }

                        @Override
                        public void onError(Call call, Exception e) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_POST_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean baseBean) {
                            if (baseBean.getCode().equals("200")) {
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_POST_SUCCESS;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_POST_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }

                    });
        }
    }

    public class PostChangeJobTask extends AsyncTask<Void, Void, Void> {
        private String mSum;
        private String mSex_limit;
        private String job_id = "0";


        PostChangeJobTask(String sum, String Sex_limit, String job_id) {
            this.mSum = sum;
            this.mSex_limit = Sex_limit;
            this.job_id = job_id;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                postChangeInfo();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            if (!sweetAlertDialog.isShowing()) {
                sweetAlertDialog.setTitleText("修改中");
                sweetAlertDialog.getProgressHelper().setBarColor(R.color.app_bg);
                sweetAlertDialog.show();
            }

            super.onPreExecute();
        }

        /**
         * 修改兼职信息
         */
        public void postChangeInfo() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.POST_JOB_INFO)
                    .addParams("only", only)
                    .addParams("city_id", city_id)
                    .addParams("aera_id", aera_id)
                    .addParams("type_id", type_id)
                    .addParams("merchant_id", String.valueOf(merchantid))
                    .addParams("name", name)
                    .addParams("name_image", name_image)
                    .addParams("start_date", start_date)
                    .addParams("stop_date", stop_date)
                    .addParams("address", address)
                    .addParams("mode", mode)
                    .addParams("money", money)
                    .addParams("term", term)
                    .addParams("limit_sex", mSex_limit)
                    .addParams("sum", mSum)
                    .addParams("hot", hot)
                    .addParams("alike", alike)
                    .addParams("lon", "0")
                    .addParams("lat", "0")
                    .addParams("tel", tel)
                    .addParams("start_time", start_time)
                    .addParams("stop_time", stop_time)
                    .addParams("set_place", set_place)
                    .addParams("set_time", set_time)
                    .addParams("work_content", work_content)
                    .addParams("work_require", work_require)
                    .addParams("job_id", job_id)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean>() {
                            }.getType());
                            return baseBean;
                        }

                        @Override
                        public void onError(Call call, Exception e) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_POST_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean baseBean) {
                            if (baseBean.getCode().equals("200")) {
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_POST_SUCCESS;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_POST_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }

                    });
        }
    }
//自动收起键盘的操作 通过判断当前的view实例是不是edittext来决定
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
