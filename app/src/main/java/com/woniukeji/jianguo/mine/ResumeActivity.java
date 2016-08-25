package com.woniukeji.jianguo.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.Resume;
import com.woniukeji.jianguo.eventbus.HeadImgEvent;
import com.woniukeji.jianguo.http.HttpMethods;
import com.woniukeji.jianguo.http.ProgressSubscriber;
import com.woniukeji.jianguo.http.SubscriberOnNextListener;
import com.woniukeji.jianguo.utils.ActivityManager;
import com.woniukeji.jianguo.utils.BitmapUtils;
import com.woniukeji.jianguo.utils.GlideCircleTransform;
import com.woniukeji.jianguo.widget.CircleProDialog;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.FileUtils;
import com.woniukeji.jianguo.utils.MD5Coder;
import com.woniukeji.jianguo.utils.SPUtils;
import com.woniukeji.jianguo.widget.CircleImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by invinjun on 2016/3/7.
 */
public class ResumeActivity extends BaseActivity {
    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.img_head) CircleImageView imgHead;
    @BindView(R.id.et_real_name) EditText etRealName;
    @BindView(R.id.rb_girl) RadioButton rbGirl;
    @BindView(R.id.rb_boy) RadioButton rbBoy;
    @BindView(R.id.rg_sex) RadioGroup rgSex;
    @BindView(R.id.img) ImageView img;
    @BindView(R.id.tv_birthday) TextView tvBirthday;
    @BindView(R.id.rl_birthday) RelativeLayout rlBirthday;
    @BindView(R.id.rl_root_view) LinearLayout root;
    @BindView(R.id.tv_shoes) TextView tvShoes;
    @BindView(R.id.rl_shoes) RelativeLayout rlShoes;
    @BindView(R.id.tv_clothse) TextView tvClothse;
    @BindView(R.id.rl_clothse) RelativeLayout rlClothse;
    @BindView(R.id.tv_tall) TextView tvTall;
    @BindView(R.id.rl_tall) RelativeLayout rlTall;
    @BindView(R.id.rb_yes) RadioButton rbYes;
    @BindView(R.id.rb_no) RadioButton rbNo;
    @BindView(R.id.rg_student) RadioGroup rgStudent;
    @BindView(R.id.tv_school) TextView tvSchool;
    @BindView(R.id.rl_school) RelativeLayout rlSchool;
    @BindView(R.id.tv_date) TextView tvDate;
    @BindView(R.id.rl_date) RelativeLayout rlDate;
    @BindView(R.id.check_button) Button checkButton;
    @BindView(R.id.tv_necessary_name) TextView tvNecessaryName;
    @BindView(R.id.tv_necessary_nickname) TextView tvNecessaryNickname;
    @BindView(R.id.et_nick_name) EditText etNickName;
    @BindView(R.id.tv_necessary_sex) TextView tvNecessarySex;
    @BindView(R.id.tv_necessary_date) TextView tvNecessaryDate;
    @BindView(R.id.img_lead) ImageView imgLead;
    @BindView(R.id.tv_necessary_school) TextView tvNecessarySchool;
    @BindView(R.id.img_edit) TextView tvEdit;
    private int MSG_GET_SUCCESS = 4;
    private int MSG_GET_FAIL = 3;
    private Handler mHandler = new Myhandler(this);
    private Context context = ResumeActivity.this;
    private File imgFile;
    private String fileName = "";
    //    private String[] sheoes=new String[]{"35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50"};
    private String[] clothes = new String[]{"S", "M", "L", "XL", "XXL", "XXXL"};
    private int loginId;
    private String sex = "1";
    private String student = "1";
    private String url2;
    private boolean save=false;

    private String date="";
    private String birDate="";
    private String tall="";
    private String shoes="";
    private String clothse="";
    private File file;
    private CircleProDialog circleProDialog;
    SubscriberOnNextListener<String> realSubscriberOnNextListener;
    private String realFilePath="";


    private boolean chaeckContent() {
        if (etRealName.getText().toString().trim() == null || etRealName.getText().toString().trim().equals("")) {
            showShortToast("请填写真实姓名");
            return false;
        } else if (etNickName.getText().toString().trim() == null || etNickName.getText().toString().trim().equals("")) {
            showShortToast("请填写昵称");
            return false;
        } else if (tvBirthday.getText().toString().trim() == null || tvBirthday.getText().toString().trim().equals("")) {
            showShortToast("请填写出生日期");
            return false;
        }
       if (tvDate.getText().toString().trim()==null){
           date="";
       }
        if (tvTall.getText().toString().trim()==null){
            tall="";
        }
        if (tvBirthday.getText().toString().trim()==null){
            birDate="";
        }
        if (tvShoes.getText().toString().trim()==null){
            shoes="";
        }
        if (tvClothse.getText().toString().trim()==null){
            clothse="";
        }
        if(student.equals("1")){
            if (student.equals("1") && tvSchool.getText().toString().trim() == null || tvSchool.getText().toString().trim().equals("")) {
                showShortToast("请填写所在学校");
                return false;
            }
        }
        date=tvDate.getText().toString().trim();
        birDate=tvBirthday.getText().toString().trim();
         shoes=  tvShoes.getText().toString().trim();
        clothse=tvClothse.getText().toString().trim();
        tall= tvTall.getText().toString().trim();
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_root_view:
                showShortToast("如果要修改资料，请点击右上角编辑按钮");
                break;
            case R.id.img_lead:
                    imgLead.setVisibility(View.GONE);
                SPUtils.setParam(ResumeActivity.this, Constants.LOGIN_INFO, Constants.SP_FIRST, 2);
                break;
            case R.id.img_back:
                if (save){
                    new SweetAlertDialog(ResumeActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("是否保存修改？")
                            .setConfirmText("确定")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    PostResume();

                                }
                            })
                            .setCancelText("取消")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            finish();
                        }
                    }).show();
                }else{
                    finish();
                }

                break;
            case R.id.img_head:
//                Intent intent2=new Intent(ResumeActivity.this,PicTipActivity.class);
//                intent2.putExtra("front",false);
//                startActivityForResult(intent2,2);
                CropImage.startPickImageActivity(this,CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE1);
                //单选多选,requestCode,最多选择数，单选模式
//                MultiImageSelectorActivity.startSelect(ResumeActivity.this, 0, 1, 0);
                break;
            case R.id.rb_girl:
                sex = "0";
                break;
            case R.id.rb_boy:
                sex = "1";
                break;
            case R.id.rl_birthday:
                TimePickerPopuWin pickerPopup1 = new TimePickerPopuWin(context, mHandler, 3);
                pickerPopup1.showShareWindow();
                pickerPopup1.showAtLocation(ResumeActivity.this.getLayoutInflater().inflate(R.layout.activity_resume, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.rl_shoes:
                List<String> listShoes = new ArrayList<String>();
                for (int i = 35; i < 52; i++) {
                    listShoes.add(String.valueOf(i));
                }
                SizePickerPopuWin pickerPopupWindow = new SizePickerPopuWin(context, listShoes, mHandler, 0);
                pickerPopupWindow.showShareWindow();
                pickerPopupWindow.showAtLocation(ResumeActivity.this.getLayoutInflater().inflate(R.layout.activity_resume, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.rl_clothse:
                List<String> listTemp = Arrays.asList(clothes);
                List<String> listCloth = new ArrayList<String>(listTemp);
                SizePickerPopuWin pickerPopup = new SizePickerPopuWin(context, listCloth, mHandler, 1);
                pickerPopup.showShareWindow();
                pickerPopup.showAtLocation(ResumeActivity.this.getLayoutInflater().inflate(R.layout.activity_resume, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.rl_tall:
                List<String> listTall = new ArrayList<String>();
                for (int i = 130; i < 210; i++) {
                    listTall.add(String.valueOf(i) + "cm");
                }
                SizePickerPopuWin pickerPopupWin = new SizePickerPopuWin(context, listTall, mHandler, 2);
                pickerPopupWin.showShareWindow();
                pickerPopupWin.showAtLocation(ResumeActivity.this.getLayoutInflater().inflate(R.layout.activity_resume, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.rb_yes:
                student = "1";
                String schoolHtml = "<font color='red'>*</font> 所在学校";
                CharSequence schoolSequence = Html.fromHtml(schoolHtml);
                tvNecessarySchool.setText(schoolSequence);
                break;
            case R.id.rb_no:
                student = "0";
                tvNecessarySchool.setText("所在学校");
                tvSchool.setText("");
                tvDate.setText("");
                break;
            case R.id.rl_school:
                startActivityForResult(new Intent(context, SchoolActivity.class), 3);
                break;
            case R.id.rl_date:
                List<String> listYear = new ArrayList<String>();
                for (int i = 2010; i < 2017; i++) {
                    listYear.add(String.valueOf(i) );
                }
                SizePickerPopuWin pickerPopup3 = new SizePickerPopuWin(context,listYear, mHandler, 4);
                pickerPopup3.showShareWindow();
                pickerPopup3.showAtLocation(ResumeActivity.this.getLayoutInflater().inflate(R.layout.activity_resume, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.img_edit:
                if (save){
                    save=false;
                    PostResume();
                        tvEdit.setText("编辑");
                        root.setClickable(true);
                        imgBack.setClickable(true);
                        imgBack.setFocusable(true);
                        imgHead.setClickable(false);
                        rbGirl.setClickable(false);
                        rbBoy.setClickable(false);
                        rlBirthday.setClickable(false);
                        rlShoes.setClickable(false);
                        rlClothse.setClickable(false);
                        rlTall.setClickable(false);
                        rbYes.setClickable(false);
                        rbNo.setClickable(false);
                        rlSchool.setClickable(false);
                        rlDate.setClickable(false);
                        etRealName.setFocusableInTouchMode(false);
                        etNickName.setFocusableInTouchMode(false);
                        rbNo.setClickable(false);
                        rbYes.setClickable(false);
                        rbGirl.setClickable(false);
                        rbBoy.setClickable(false);
                        imgBack.setClickable(true);
                        imgBack.setOnClickListener(this);
                }else {
                    save=true;
                    tvEdit.setText("保存");
                    imgHead.setOnClickListener(this);
                    rbGirl.setOnClickListener(this);
                    rbBoy.setOnClickListener(this);
                    rlBirthday.setOnClickListener(this);
                    rlShoes.setOnClickListener(this);
                    rlClothse.setOnClickListener(this);
                    rlTall.setOnClickListener(this);
                    rbYes.setOnClickListener(this);
                    rbNo.setOnClickListener(this);
                    rlSchool.setOnClickListener(this);
                    rlDate.setOnClickListener(this);
                    etRealName.setFocusableInTouchMode(true);
                    etNickName.setFocusableInTouchMode(true);
                    rbNo.setClickable(true);
                    rbYes.setClickable(true);
                    rbGirl.setClickable(true);
                    rbBoy.setClickable(true);
                    imgBack.setClickable(true);
                    imgBack.setOnClickListener(this);
                }

                break;
        }
    }

    private void PostResume() {
        if (chaeckContent()) {
            //未选择头像的时候，直接提交头像以前的url，如果更新了头像则上传图片
            if (realFilePath==null||realFilePath.equals("")){
                HttpMethods.getInstance().postResum(new ProgressSubscriber<String>(realSubscriberOnNextListener,context),String.valueOf(loginId),
                        etRealName.getText().toString().trim(), etNickName.getText().toString(), tvSchool.getText().toString().trim(),tall,student,url2,
                        date,  birDate,  shoes, clothse,sex);
            }else {
                circleProDialog = new CircleProDialog(ResumeActivity.this);
                circleProDialog.setCanceledOnTouchOutside(false);
                circleProDialog.setCanceledOnTouchOutside(false);
                circleProDialog.show();
                upLoadQiNiu(context, MD5Coder.getQiNiuName(String.valueOf(loginId)), realFilePath);
            }

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
            ResumeActivity resumeActivity = (ResumeActivity) reference.get();
            switch (msg.what) {
                case 0:
                    BaseBean<Resume> baseBean = (BaseBean<Resume>) msg.obj;
                    resumeActivity.showShortToast("信息修改成功！");
                    SPUtils.setParam(resumeActivity, Constants.LOGIN_INFO, Constants.SP_RESUMM, "1");
                    SPUtils.setParam(resumeActivity, Constants.USER_INFO, Constants.USER_SEX, resumeActivity.sex);
                    SPUtils.setParam(resumeActivity, Constants.USER_INFO, Constants.SP_IMG, resumeActivity.url2);
                    SPUtils.setParam(resumeActivity, Constants.USER_INFO, Constants.SP_NAME,resumeActivity.etRealName.getText().toString().trim() );
                    SPUtils.setParam(resumeActivity, Constants.USER_INFO, Constants.SP_SCHOOL,resumeActivity.tvSchool.getText().toString().trim() );
                    HeadImgEvent headImgEvent=new HeadImgEvent();
                    headImgEvent.ImgUrl=resumeActivity.url2;
                    EventBus.getDefault().post(headImgEvent);
                    resumeActivity.finish();
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(resumeActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //选择器返回字符
                    String size = (String) msg.obj;
                    int type = msg.arg1;
                    switch (type) {
                        case 0:
                            resumeActivity.tvShoes.setText(size);
                            break;
                        case 1:
                            resumeActivity.tvClothse.setText(size);
                            break;
                        case 2:
                            resumeActivity.tvTall.setText(size.substring(0,size.lastIndexOf("cm")));
                            break;
                        case 3:
                            resumeActivity.tvBirthday.setText(size);
                            break;
                        case 4:
                            resumeActivity.tvDate.setText(size);
                            break;
                    }

                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(resumeActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    BaseBean<Resume> resumeBaseBean = (BaseBean) msg.obj;
                    resumeActivity.initResumeInfo(resumeBaseBean.getData().getT_user_resume());
                    break;
                default:
                    break;
            }
        }


    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_resume);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        tvTitle.setText("我的资料");
        String dateHtml = "<font color='red'>*</font> 出生日期";
        CharSequence charSequence = Html.fromHtml(dateHtml);
        tvNecessaryDate.setText(charSequence);
        String nameHtml = "<font color='red'>*</font> 姓名";
        CharSequence nameSequence = Html.fromHtml(nameHtml);
        tvNecessaryName.setText(nameSequence);
        String nickHtml = "<font color='red'>*</font> 昵称";
        CharSequence nickSequence = Html.fromHtml(nickHtml);
        tvNecessaryNickname.setText(nickSequence);
        String sexHtml = "<font color='red'>*</font> 性别";
        CharSequence sexSequence = Html.fromHtml(sexHtml);
        tvNecessarySex.setText(sexSequence);
        String schoolHtml = "<font color='red'>*</font> 所在学校";
        CharSequence schoolSequence = Html.fromHtml(schoolHtml);
        tvNecessarySchool.setText(schoolSequence);
    }

    @Override
    public void initListeners() {

        imgLead.setOnClickListener(this);
        tvEdit.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        root.setOnClickListener(this);
        etRealName.setFocusableInTouchMode(false);
        etNickName.setFocusableInTouchMode(false);
        rbNo.setClickable(false);
        rbYes.setClickable(false);
        rbGirl.setClickable(false);
        rbBoy.setClickable(false);
        realSubscriberOnNextListener=new SubscriberOnNextListener<String>() {
            @Override
            public void onNext(String s) {
                SPUtils.setParam(ResumeActivity.this, Constants.LOGIN_INFO, Constants.SP_RESUMM, "1");
                SPUtils.setParam(ResumeActivity.this, Constants.USER_INFO, Constants.USER_SEX, sex);
                SPUtils.setParam(ResumeActivity.this, Constants.USER_INFO, Constants.SP_IMG,url2);
                SPUtils.setParam(ResumeActivity.this, Constants.USER_INFO, Constants.SP_NAME,etRealName.getText().toString().trim() );
                SPUtils.setParam(ResumeActivity.this, Constants.USER_INFO, Constants.SP_SCHOOL,tvSchool.getText().toString().trim() );
                HeadImgEvent headImgEvent=new HeadImgEvent();
                headImgEvent.ImgUrl=url2;
                EventBus.getDefault().post(headImgEvent);
                Toast.makeText(ResumeActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                 finish();
            }
        };
    }
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
    private void initResumeInfo(Resume.UserResum userResum) {
        url2 = userResum.getName_image();
        etRealName.setText(userResum.getName());
        etNickName.setText(userResum.getNickname());
        tvBirthday.setText(userResum.getBirth_date());
        tvShoes.setText(userResum.getShoe_size());
        tvClothse.setText(userResum.getClothing_size());
        tvTall.setText(userResum.getHeight());
        tvSchool.setText(userResum.getSchool());
        if (userResum.getSchool()==null||userResum.getSchool().equals("")){
            rbNo.setChecked(true);
            student="0";
        }else {
            rbYes.setChecked(true);
            student="1";
        }

        tvDate.setText(userResum.getIntoschool_date());
        if (userResum.getSex().equals("0")) {
            rbGirl.setChecked(true);
            rbBoy.setChecked(false);
        } else {
            rbGirl.setChecked(false);
            rbBoy.setChecked(true);
        }
        if (userResum.getStudent().equals("0")) {
            rbYes.setChecked(false);
            rbNo.setChecked(true);
//            String schoolHtml="<font color='red'>*</font> 所在学校";
//            CharSequence schoolSequence=Html.fromHtml(schoolHtml);
            tvNecessarySchool.setText("所在学校");
            tvDate.setText("");
            tvSchool.setText("");
        } else {
            rbNo.setChecked(false);
            rbYes.setChecked(true);
            String schoolHtml = "<font color='red'>*</font> 所在学校";
            CharSequence schoolSequence = Html.fromHtml(schoolHtml);
            tvNecessarySchool.setText(schoolSequence);
        }
        fileName = userResum.getName_image();

        if (userResum.getName_image() != null && !userResum.getName_image().equals("")) {
            Glide.with(context).load(userResum.getName_image())
                    .placeholder(R.mipmap.icon_head_defult)
                    .error(R.mipmap.icon_head_defult)
                    .transform(new GlideCircleTransform(ResumeActivity.this))
                    .into(imgHead);
        } else {
        Glide.with(context).load("http//null")
                .placeholder(R.mipmap.icon_head_defult)
                .error(R.mipmap.icon_head_defult)
                .transform(new GlideCircleTransform(ResumeActivity.this))
                .into(imgHead);
    }

    }

    @Override
    public void initData() {
        loginId = (int) SPUtils.getParam(context, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
        int First = (int) SPUtils.getParam(ResumeActivity.this, Constants.LOGIN_INFO, Constants.SP_FIRST, 0);
        if (First<2){
            imgLead.setVisibility(View.VISIBLE);
        }
        getResume(String.valueOf(loginId));
    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(ResumeActivity.this);
    }

        /**
         * postInfo
         */
        public void getResume(String loginId) {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.GET_RESUME)
                    .addParams("only", only)
                    .addParams("login_id", loginId)
                    .build()
                    .connTimeOut(6000)
                    .readTimeOut(2000)
                    .writeTimeOut(2000)
                    .execute(new Callback<BaseBean<Resume>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response,int id) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<Resume>>() {
                            }.getType());
                            return baseBean;
                        }

                        @Override
                        public void onError(Call call, Exception e,int id) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_GET_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean baseBean,int id) {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE1 && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
            } else {
                startCropImageActivity(imageUri,CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE1);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE1) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                realFilePath = FileUtils.getRealFilePath(ResumeActivity.this, result.getUri());
                Bitmap bitmap=BitmapUtils.compressBitmap(realFilePath,1080, 720);
                imgHead.setImageBitmap(bitmap);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }

         if (requestCode == 1) {
            tvBirthday.setText(data.getStringExtra("date"));
        } else if (requestCode == 2) {
            tvDate.setText(data.getStringExtra("date"));
        } else if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                tvSchool.setText(data.getStringExtra("school"));
            }

        }
    }
    private void startCropImageActivity(Uri imageUri,int requestCode) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this,requestCode,true);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (save){
                new SweetAlertDialog(ResumeActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("是否保存修改？")
                        .setConfirmText("确定")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                PostResume();
                            }
                        })
                        .setCancelText("取消")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                finish();
                            }
                        }).show();
            }
        }
        return super.onKeyDown(keyCode, event);

    }

    public  void upLoadQiNiu(final Context context, String key, String filePath) {
        String commonUploadToken = (String) SPUtils.getParam(context, Constants.LOGIN_INFO, Constants.SP_QNTOKEN, "");
        // 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(filePath, key, commonUploadToken, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                    url2="http://7xlell.com2.z0.glb.qiniucdn.com/"+key;
                    circleProDialog.dismiss();

                HttpMethods.getInstance().postResum(new ProgressSubscriber<String>(realSubscriberOnNextListener,context),String.valueOf(loginId),
                        etRealName.getText().toString().trim(), etNickName.getText().toString(), tvSchool.getText().toString().trim(),tall,student,url2,
                        date,  birDate,  shoes, clothse,sex);

//                    postRealName(String.valueOf(loginId),url1,url2,name,id,sex);
                /**
                 *(@Query("only") String only, @Query("login_id") String login_id,
                 @Query("name") String name, @Query("nickname") String nickname,
                 @Query("school") String school, @Query("height") String height,
                 @Query("student") String student, @Query("name_image") String name_image,
                 @Query("intoschool_date") String intoschool_date, @Query("birth_date") String birth_date,
                 @Query("shoe_size") String shoe_size, @Query("clothing_size") String clothing_size,
                 @Query("sex") String sex);
                 */
            }
        }, new UploadOptions(null, null, false,
                new UpProgressHandler(){
                    public void progress(String key, double percent){
                        circleProDialog.setMsg(percent);
                    }
                }, null));

    }
}
