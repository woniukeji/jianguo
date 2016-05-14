package com.woniukeji.jianguo.mine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.Resume;
import com.woniukeji.jianguo.eventbus.HeadImgEvent;
import com.woniukeji.jianguo.utils.ActivityManager;
import com.woniukeji.jianguo.utils.BitmapUtils;
import com.woniukeji.jianguo.utils.CommonUtils;
import com.woniukeji.jianguo.utils.CropCircleTransfermation;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.FileUtils;
import com.woniukeji.jianguo.utils.MD5Coder;
import com.woniukeji.jianguo.utils.QiNiu;
import com.woniukeji.jianguo.utils.SPUtils;
import com.woniukeji.jianguo.widget.CircleImageView;
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
import de.greenrobot.event.EventBus;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by invinjun on 2016/3/7.
 */
public class ResumeActivity extends BaseActivity {
    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.img_head) CircleImageView imgHead;
    @InjectView(R.id.et_real_name) EditText etRealName;
    @InjectView(R.id.rb_girl) RadioButton rbGirl;
    @InjectView(R.id.rb_boy) RadioButton rbBoy;
    @InjectView(R.id.rg_sex) RadioGroup rgSex;
    @InjectView(R.id.img) ImageView img;
    @InjectView(R.id.tv_birthday) TextView tvBirthday;
    @InjectView(R.id.rl_birthday) RelativeLayout rlBirthday;
    @InjectView(R.id.tv_shoes) TextView tvShoes;
    @InjectView(R.id.rl_shoes) RelativeLayout rlShoes;
    @InjectView(R.id.tv_clothse) TextView tvClothse;
    @InjectView(R.id.rl_clothse) RelativeLayout rlClothse;
    @InjectView(R.id.tv_tall) TextView tvTall;
    @InjectView(R.id.rl_tall) RelativeLayout rlTall;
    @InjectView(R.id.rb_yes) RadioButton rbYes;
    @InjectView(R.id.rb_no) RadioButton rbNo;
    @InjectView(R.id.rg_student) RadioGroup rgStudent;
    @InjectView(R.id.tv_school) TextView tvSchool;
    @InjectView(R.id.rl_school) RelativeLayout rlSchool;
    @InjectView(R.id.tv_date) TextView tvDate;
    @InjectView(R.id.rl_date) RelativeLayout rlDate;
    @InjectView(R.id.check_button) Button checkButton;
    @InjectView(R.id.tv_necessary_name) TextView tvNecessaryName;
    @InjectView(R.id.tv_necessary_nickname) TextView tvNecessaryNickname;
    @InjectView(R.id.et_nick_name) EditText etNickName;
    @InjectView(R.id.tv_necessary_sex) TextView tvNecessarySex;
    @InjectView(R.id.tv_necessary_date) TextView tvNecessaryDate;
    @InjectView(R.id.img_go) ImageView imgGo;
    @InjectView(R.id.tv_necessary_school) TextView tvNecessarySchool;
    @InjectView(R.id.img_edit) TextView tvEdit;
    private int MSG_POST_SUCCESS = 0;
    private int MSG_POST_FAIL = 1;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                if (save){
                    new SweetAlertDialog(ResumeActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("是否保存修改？")
                            .setConfirmText("确定")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    String name = etRealName.getText().toString().trim();
                                    if (chaeckContent()) {
                                        PostTask postTask = new PostTask(true, String.valueOf(loginId), name, etNickName.getText().toString(), url2, tvSchool.getText().toString().trim(),
                                                date, sex, tall, student,birDate,
                                                shoes, clothse);
                                        postTask.execute();
                                    }

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

                break;
            case R.id.img_head:
                //单选多选,requestCode,最多选择数，单选模式
                MultiImageSelectorActivity.startSelect(ResumeActivity.this, 0, 1, 0);
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
                TimePickerPopuWin pickerPopup3 = new TimePickerPopuWin(context, mHandler, 4);
                pickerPopup3.showShareWindow();
                pickerPopup3.showAtLocation(ResumeActivity.this.getLayoutInflater().inflate(R.layout.activity_resume, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.img_edit:
                if (save){
                    save=false;
                    String name = etRealName.getText().toString().trim();
                    if (chaeckContent()) {
                        PostTask postTask = new PostTask(true, String.valueOf(loginId), name, etNickName.getText().toString(), url2, tvSchool.getText().toString().trim(),
                                date, sex, tall, student,birDate,
                                shoes, clothse);
                        postTask.execute();
                        tvEdit.setText("编辑");
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
                    }
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
                }

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
        ButterKnife.inject(this);
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
        tvEdit.setOnClickListener(this);
        etRealName.setFocusableInTouchMode(false);
        etNickName.setFocusableInTouchMode(false);
        imgBack.setOnClickListener(this);
        rbNo.setClickable(false);
        rbYes.setClickable(false);
        rbGirl.setClickable(false);
        rbBoy.setClickable(false);
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
            Picasso.with(context).load(userResum.getName_image())
                    .placeholder(R.mipmap.icon_head_defult)
                    .error(R.mipmap.icon_head_defult)
                    .transform(new CropCircleTransfermation())
                    .into(imgHead);
        } else {
        Picasso.with(context).load("http//null")
                .placeholder(R.mipmap.icon_head_defult)
                .error(R.mipmap.icon_head_defult)
                .transform(new CropCircleTransfermation())
                .into(imgHead);
    }

    }

    @Override
    public void initData() {
        loginId = (int) SPUtils.getParam(context, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
        PostTask postTask = new PostTask(false, String.valueOf(loginId), null, null, null, null, null, null, null, null, null, null, null);
        postTask.execute();
    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(ResumeActivity.this);
    }


    public class PostTask extends AsyncTask<Void, Void, Void> {
        private final boolean isPost;
        private final String loginId;
        private final String name;
        private final String nickName;
        private final String name_image;
        private final String school;
        private final String intoschool_date;
        private final String sex;
        private final String height;
        private final String student;
        private final String birth_date;
        private final String shoe_size;
        private final String clothing_size;

//

        /**
         * 传参：login_id		登录ID
         * 传参：name		姓名
         * 传参：name_image	头像
         * 传参：school		学校
         * 传参：intoschool_date	入校时间
         * 传参：sex		性别（0=女，1=男）
         * 传参：height		身高（int型）
         * 传参：student		学生（int型：0=不是学生，1=是学生）
         * 传参：birth_date	出生日期
         * 传参：shoe_size		鞋码
         * 传参：clothing_size	服装尺码
         */
        PostTask(boolean isPost, String loginId, String name, String nickName, String name_image,
                 String school, String intoschool_date, String sex, String height,
                 String student, String birth_date, String shoe_size, String clothing_size
        ) {
            this.isPost = isPost;
            this.loginId = loginId;
            this.name = name;
            this.nickName = nickName;
            this.name_image = name_image;
            this.school = school;
            this.intoschool_date = intoschool_date;
            this.sex = sex;
            this.height = height;
            this.student = student;
            this.birth_date = birth_date;
            this.shoe_size = shoe_size;
            this.clothing_size = clothing_size;

        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                if (isPost) {
                    QiNiu.upLoadQiNiu(context, MD5Coder.getQiNiuName(fileName), file);
                    postResume();
                } else
                    getResume();

            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//
        }


        /**
         * postInfo
         * 传参：login_id		登录ID
         * 传参：name		姓名
         * 传参：name_image	头像
         * 传参：school		学校
         * 传参：intoschool_date	入校时间
         * 传参：sex		性别（0=女，1=男）
         * 传参：height		身高（int型）
         * 传参：student		学生（int型：0=不是学生，1=是学生）
         * 传参：birth_date	出生日期
         * 传参：shoe_size		鞋码
         * 传参：clothing_size	服装尺码
         */
        public void postResume() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.CHANGE_RESUME)
                    .addParams("only", only)
                    .addParams("login_id", loginId)
                    .addParams("name", name)
                    .addParams("nickname", nickName)
                    .addParams("school", school)
                    .addParams("height", height)
                    .addParams("student", student)
                    .addParams("name_image", name_image)
                    .addParams("intoschool_date", intoschool_date)
                    .addParams("birth_date", birth_date)
                    .addParams("shoe_size", shoe_size)
                    .addParams("clothing_size", clothing_size)
                    .addParams("sex", sex)
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
//                                SPUtils.setParam(AuthActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                                Message message = new Message();
                                message.obj = baseBean;
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

        /**
         * postInfo
         */
        public void getResume() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.GET_RESUME)
                    .addParams("only", only)
                    .addParams("login_id", loginId)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<Resume>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<Resume>>() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // 处理你自己的逻辑 ....
                imgFile = new File(path.get(0));
                String choosePic = path.get(0).substring(path.get(0).lastIndexOf("."));
                fileName = Constants.IMG_PATH + CommonUtils.generateFileName() + choosePic;
                Uri imgSource = Uri.fromFile(imgFile);
                imgHead.setImageURI(imgSource);
                file = new File(Environment.getExternalStorageDirectory() + "/"+fileName+".png");
                FileUtils.copyfile(imgFile,file,true);
                BitmapUtils.compressBitmap(file.getAbsolutePath(), 300, 300);
                url2 = "http://7xlell.com2.z0.glb.qiniucdn.com/" + MD5Coder.getQiNiuName(fileName);
            }
        } else if (requestCode == 1) {
            tvBirthday.setText(data.getStringExtra("date"));
        } else if (requestCode == 2) {
            tvDate.setText(data.getStringExtra("date"));
        } else if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                tvSchool.setText(data.getStringExtra("school"));
            }

        }


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
                                String name = etRealName.getText().toString().trim();
                                if (chaeckContent()) {
                                    PostTask postTask = new PostTask(true, String.valueOf(loginId), name, etNickName.getText().toString(), url2, tvSchool.getText().toString().trim(),
                                            date, sex, tall, student,birDate,
                                            shoes, clothse);
                                    postTask.execute();
                                }

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
}
