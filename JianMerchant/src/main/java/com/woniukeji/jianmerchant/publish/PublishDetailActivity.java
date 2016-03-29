package com.woniukeji.jianmerchant.publish;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.User;
import com.woniukeji.jianmerchant.utils.BitmapUtils;
import com.woniukeji.jianmerchant.utils.CommonUtils;
import com.woniukeji.jianmerchant.utils.MD5Coder;
import com.woniukeji.jianmerchant.utils.QiNiu;
import com.woniukeji.jianmerchant.widget.CircleImageView;
import com.woniukeji.jianmerchant.widget.SizePickerPopuWin;
import com.woniukeji.jianmerchant.widget.TimePickerPopuWin;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

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
    @InjectView(R.id.tv_level) TextView tvLevel;
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
    @InjectView(R.id.tv_wages) EditText tvWages;
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
    @InjectView(R.id.tv_collection_time) TextView tvCollectionTime;
    @InjectView(R.id.rl_collection_time) RelativeLayout rlCollectionTime;
    @InjectView(R.id.tv_t) TextView tvT;
    @InjectView(R.id.telsplit) ImageView telsplit;
    @InjectView(R.id.tv_tel) EditText tvTel;
    @InjectView(R.id.rl_tel) RelativeLayout rlTel;
    @InjectView(R.id.et_work_content) EditText etWorkContent;
    @InjectView(R.id.et_work_require) EditText etWorkRequire;
    @InjectView(R.id.btn_preview) Button btnPreview;
    @InjectView(R.id.btn_save) Button btnSave;
    @InjectView(R.id.btn_publish) Button btnPublish;
    private String[] partLevel=new String[]{"热门","精品","旅行","日结"};
    private String[] payMethods=new String[]{"日结","周结","月结","次"};
    private String[] wagesMethods=new String[]{"小时","天","周","次"};
    private String[] sexs=new String[]{"不限男女","仅限男","仅限女","限制男女数"};

    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private int MSG_PHONE_SUCCESS = 2;
    private int MSG_REGISTER_SUCCESS = 3;
    private Handler mHandler = new Myhandler(this);
    private Context context = PublishDetailActivity.this;
    private String fileName="";


    @OnClick({R.id.img_job, R.id.rl_location, R.id.rl_part_level, R.id.rl_category, R.id.rl_pay_method, R.id.tv_wages_method, R.id.rl_sex, R.id.tv_position, R.id.rl_position, R.id.tv_date_start, R.id.tv_date_end, R.id.tv_time_start, R.id.tv_time_end, R.id.rl_collection_time, R.id.btn_preview, R.id.btn_save, R.id.btn_publish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_job:
                //单选多选,requestCode,最多选择数，单选模式
                MultiImageSelectorActivity.startSelect(PublishDetailActivity.this, 0, 1, 0);
                break;
//            case R.id.rl_location:
//                TimePickerPopuWin pickerPopup1=new TimePickerPopuWin(context,mHandler,3);
//                pickerPopup1.showShareWindow();
//                pickerPopup1.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_resume, null),
//                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//                break;
//            case R.id.rl_location:
//                List<String> listShoes=new ArrayList<String>();
//                for (int i = 35; i < 52; i++) {
//                    listShoes.add(String.valueOf(i));
//                }
//                SizePickerPopuWin pickerPopupWindow=new SizePickerPopuWin(context,listShoes,mHandler,0);
//                pickerPopupWindow.showShareWindow();
//                pickerPopupWindow.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_resume, null),
//                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//                break;
            case R.id.rl_location:
                break;
            case R.id.rl_part_level:
                List<String> listTemp= Arrays.asList(partLevel);
                List<String> listCloth=new ArrayList<String>(listTemp);
                SizePickerPopuWin pickerPopup=new SizePickerPopuWin(context,listCloth,mHandler,1);
                pickerPopup.showShareWindow();
                pickerPopup.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.rl_category:
                break;
            case R.id.rl_pay_method:

                List<String> listpay= Arrays.asList(payMethods);
                List<String> paylist=new ArrayList<String>(listpay);
                SizePickerPopuWin pickerPayMethod=new SizePickerPopuWin(context,paylist,mHandler,3);
                pickerPayMethod.showShareWindow();
                pickerPayMethod.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_wages_method:
                List<String> listwage= Arrays.asList(wagesMethods);
                List<String> wagelist=new ArrayList<String>(listwage);
                SizePickerPopuWin pickerWagesMethod=new SizePickerPopuWin(context,wagelist,mHandler,4);
                pickerWagesMethod.showShareWindow();
                pickerWagesMethod.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.rl_sex:
                List<String> listsex= Arrays.asList(sexs);
                List<String> sexlist=new ArrayList<String>(listsex);
                SizePickerPopuWin pickerSex=new SizePickerPopuWin(context,sexlist,mHandler,5);
                pickerSex.showShareWindow();
                pickerSex.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_position:
                break;
            case R.id.rl_position:
                break;
            case R.id.tv_date_start:
                TimePickerPopuWin pickerPopup1=new TimePickerPopuWin(context,mHandler,7);
                pickerPopup1.showShareWindow();
                pickerPopup1.showAtLocation(PublishDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_publish_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_date_end:
                break;
            case R.id.tv_time_start:
                break;
            case R.id.tv_time_end:
                break;
            case R.id.rl_collection_time:
                break;
            case R.id.btn_preview:
                break;
            case R.id.btn_save:
                break;
            case R.id.btn_publish:
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
            PublishDetailActivity activity = (PublishDetailActivity) reference.get();
            switch (msg.what) {
                case 0:
                    BaseBean<User> user = (BaseBean<User>) msg.obj;
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(activity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //选择器返回字符
                    String size= (String) msg.obj;
                    int type=msg.arg1;
                    switch (type){
                        case 0://区域
                            activity.tvPublishLocation.setText(size);
                            activity.tvPublishLocation.setTextColor(activity.getResources().getColor(R.color.black));
                            break;
                        case 1://兼职级别
                            activity.tvLevel.setText(size);
                            activity.tvLevel.setTextColor(activity.getResources().getColor(R.color.black));
                            break;
                        case 2://兼职种类
                            activity.tvCategory.setText(size);
                            break;
                        case 3://结算方式
                            activity.tvPayMethod.setText(size);
                            activity.tvPayMethod.setTextColor(activity.getResources().getColor(R.color.black));
                            break;
                        case 4://薪酬
                            activity.tvWagesMethod.setText(size);
                            break;
                        case 5://性别选择
                            activity.tvSex.setText(size);
                            break;
                        case 6://具体位置
                            activity.tvPosition.setText(size);
                            break;
                        case 7://开始日期
                            activity.tvDateStart.setText(size);
                            break;
                        case 8:
                            activity.tvDateEnd.setText(size);
                            break;
                        case 9:
                            activity.tvTimeStart.setText(size);
                            break;
                        case 10:
                            activity.tvTimeEnd.setText(size);
                            break;
                        case 11:
                            activity.tvCollectionTime.setText(size);
                            break;

                    }
                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(activity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_publish_detail);
        ButterKnife.inject(this);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {
        // Here, thisActivity is the current activity
//        if (ContextCompat.checkSelfPermission(PublishDetailActivity.this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(PublishDetailActivity.this,
//                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
//
//                // Show an expanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else {
//
//                // No explanation needed, we can request the permission.
//
//                ActivityCompat.requestPermissions(PublishDetailActivity.this,
//                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//
//                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }
//        }

    }

    @Override
    public void initData() {

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

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
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
                fileName = Constants.IMG_PATH + CommonUtils.generateFileName() + choosePic;
                Uri imgSource =  Uri.fromFile(imgFile);
                imgJob.setImageURI(imgSource);
                BitmapUtils.compressBitmap(imgFile.getAbsolutePath(), 300, 300);
                QiNiu.upLoadQiNiu(context, MD5Coder.getQiNiuName(fileName), imgFile);
            }
        }else if(requestCode == 1){
//            tvBirthday.setText(data.getStringExtra("date"));
        }else if(requestCode == 2){
//            tvDate.setText(data.getStringExtra("date"));
        }else if(requestCode == 3){
            if (resultCode == RESULT_OK) {
//                tvSchool.setText(data.getStringExtra("school"));
            }

        }


    }
}
