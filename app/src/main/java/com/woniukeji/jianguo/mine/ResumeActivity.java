package com.woniukeji.jianguo.mine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
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
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.Resume;
import com.woniukeji.jianguo.entity.User;
import com.woniukeji.jianguo.main.MainActivity;
import com.woniukeji.jianguo.utils.BitmapUtils;
import com.woniukeji.jianguo.utils.CommonUtils;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.MD5Coder;
import com.woniukeji.jianguo.utils.QiNiu;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by invinjun on 2016/3/7.
 */
public class ResumeActivity extends BaseActivity {
    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.img_head) ImageView imgHead;
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
    private int MSG_POST_SUCCESS = 0;
    private int MSG_POST_FAIL = 1;
    private int MSG_GET_SUCCESS = 2;
    private int MSG_GET_FAIL = 3;
    private Handler mHandler = new Myhandler(this);
    private Context context = ResumeActivity.this;
    private File imgFile;
    private String fileName;


    @OnClick({R.id.img_back, R.id.img_head, R.id.rb_girl, R.id.rb_boy, R.id.rl_birthday, R.id.rl_shoes, R.id.rl_clothse, R.id.rl_tall, R.id.rb_yes, R.id.rb_no, R.id.rl_school, R.id.rl_date, R.id.check_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_head:
                //单选多选
                MultiImageSelectorActivity.startSelect(ResumeActivity.this, 0, 1, 0);
                break;
            case R.id.rb_girl:
                break;
            case R.id.rb_boy:
                break;
            case R.id.rl_birthday:
                break;
            case R.id.rl_shoes:
                break;
            case R.id.rl_clothse:
                break;
            case R.id.rl_tall:
                break;
            case R.id.rb_yes:
                break;
            case R.id.rb_no:
                break;
            case R.id.rl_school:
                break;
            case R.id.rl_date:
                break;
            case R.id.check_button:
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
                    BaseBean<User> user = (BaseBean<User>) msg.obj;
                    resumeActivity.showShortToast("登录成功！");
                    Intent intent = new Intent(resumeActivity, MainActivity.class);
//                    intent.putExtra("user",user);
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(resumeActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:

                    break;
                case 3:
                    String sms = (String) msg.obj;
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
        ButterKnife.inject(this);
    }

    @Override
    public void initViews() {
        tvTitle.setText("我的简历");
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }


    public class PostTask extends AsyncTask<Void, Void, Void> {
        private final boolean isPost;
        private final String loginId;
        private final String name;
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
        PostTask(boolean isPost, String loginId, String name, String name_image,
                 String school, String intoschool_date, String sex, String height,
                 String student, String birth_date, String shoe_size, String clothing_size
        ) {
            this.isPost = isPost;
            this.loginId = loginId;
            this.name = name;
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
                    .url(Constants.POST_RESUME)
                    .addParams("only", only)
                    .addParams("login_id", loginId)
                    .addParams("name", name)
                    .addParams("school", school)
                    .addParams("height", height)
                    .addParams("student", student)
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
//                                SPUtils.setParam(AuthActivity.this, Constants.SP_LOGIN, Constants.SP_TYPE, "0");
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
                    .url(Constants.GET_REAL_NAME)
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
//                                SPUtils.setParam(AuthActivity.this, Constants.SP_LOGIN, Constants.SP_TYPE, "0");
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
                BitmapUtils.compressBitmap(imgFile.getAbsolutePath(), 300, 300);
                QiNiu.upLoadQiNiu(context, MD5Coder.getQiNiuName(fileName), imgFile);
            }
        }

    }

}
