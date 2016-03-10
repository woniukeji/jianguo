package com.woniukeji.jianguo.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.woniukeji.jianguo.entity.RealName;
import com.woniukeji.jianguo.login.QuickLoginActivity;
import com.woniukeji.jianguo.utils.BitmapUtils;
import com.woniukeji.jianguo.utils.CommonUtils;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.FileUtils;
import com.woniukeji.jianguo.utils.MD5Coder;
import com.woniukeji.jianguo.utils.QiNiu;
import com.woniukeji.jianguo.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.Response;

public class AuthActivity extends BaseActivity {
    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView title;
    @InjectView(R.id.img_front) ImageView imgFront;
    @InjectView(R.id.tv_front) TextView tvFront;
    @InjectView(R.id.img_opposite) ImageView imgOpposite;
    @InjectView(R.id.tv_opposite) TextView tvOpposite;
    @InjectView(R.id.ll_top) LinearLayout llTop;
    @InjectView(R.id.img_phone) ImageView imgPhone;
    @InjectView(R.id.img) ImageView img;
    @InjectView(R.id.et_phone_auth) EditText etPhoneAuth;
    @InjectView(R.id.et_real_name) EditText etRealName;
    @InjectView(R.id.et_id) EditText etId;
    @InjectView(R.id.or_img) ImageView orImg;
    @InjectView(R.id.rb_man) RadioButton rbMan;
    @InjectView(R.id.rb_woman) RadioButton rbWoman;
    @InjectView(R.id.check_button) Button checkButton;
    @InjectView(R.id.tv_notic2) TextView tvNotic2;
    @InjectView(R.id.tv_notic3) TextView tvNotic3;
    @InjectView(R.id.rl_phone) RelativeLayout rlPhone;
    private int MSG_POST_SUCCESS = 0;
    private int MSG_POST_FAIL = 1;
    private int MSG_GET_SUCCESS = 2;
    private int MSG_GET_FAIL = 3;
    private Handler mHandler = new Myhandler(this);
    private Context context = AuthActivity.this;
    private File imgFile;
    private String fileName;
    private String fileName2;
    private File imgFile2;
    private int loginId;
    private int status;
    private String tel;
    private String sex="1";
    public SweetAlertDialog pDialog ;
    @OnClick({R.id.rl_phone,R.id.img_back, R.id.img_front, R.id.tv_front, R.id.img_opposite, R.id.tv_opposite, R.id.rb_man, R.id.rb_woman, R.id.check_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_phone:
                startActivity(new Intent(context, QuickLoginActivity.class));
//                finish();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_front:
                //单选多选
                MultiImageSelectorActivity.startSelect(AuthActivity.this, 0, 1, 0);
                break;
            case R.id.tv_front:
                break;
            case R.id.img_opposite:
                MultiImageSelectorActivity.startSelect(AuthActivity.this, 1, 1, 0);
                break;
            case R.id.tv_opposite:
                break;
            case R.id.rb_man:
                sex="1";
                rbMan.setChecked(true);
                rbWoman.setChecked(false);
                break;
            case R.id.rb_woman:
                sex="0";
                rbMan.setChecked(false);
                rbWoman.setChecked(true);
                break;
            case R.id.check_button:
                String phone = etPhoneAuth.getText().toString().trim();
                String name = etRealName.getText().toString().trim();
                String id = etId.getText().toString().trim();

                if (fileName == null || fileName2 == null || fileName.equals("") || fileName2.equals("")) {
                    showShortToast("请先上传身份证图片！");
                    break;
                }
                if (name==null||name.equals("")){
                    showShortToast("请填写真实姓名");
                    break;
                } else if (id==null||id.equals("")){
                    showShortToast("请填写身份证号码");
                    break;
            }

            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("请稍后...");
            pDialog.setCancelable(false);
            pDialog.show();

                QiNiu.upLoadQiNiu(context, MD5Coder.getQiNiuName(fileName), imgFile);
                QiNiu.upLoadQiNiu(context, MD5Coder.getQiNiuName(fileName2), imgFile2);
                String url1="http://7xlell.com2.z0.glb.qiniucdn.com/"+MD5Coder.getQiNiuName(fileName);
                String url2="http://7xlell.com2.z0.glb.qiniucdn.com/"+MD5Coder.getQiNiuName(fileName2);
                PostTask postTask=new PostTask(true,String.valueOf(loginId),url1,url2,name,id,sex);
                postTask.execute();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }



    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AuthActivity authActivity = (AuthActivity) reference.get();
            switch (msg.what) {
                case 0:
                    if (null!=authActivity.pDialog){
                        authActivity.pDialog.dismiss();
                    }
                    BaseBean baseBean = (BaseBean) msg.obj;
                    //手动保存认证状态 防止未重新登录情况下再次进入该界面
                    SPUtils.setParam(authActivity,Constants.SP_LOGIN,Constants.SP_STATUS,3);
                    authActivity.showShortToast("提交成功");
                    break;
                case 1:
                    if (null!=authActivity.pDialog){
                        authActivity.pDialog.dismiss();
                    }
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(authActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    if (null!=authActivity.pDialog){
                        authActivity.pDialog.dismiss();
                    }
                    BaseBean<RealName> realNameBaseBean = (BaseBean<RealName>) msg.obj;
                    authActivity.showShortToast("获取实名信息成功");
                    authActivity.setInf(realNameBaseBean.getData());
                    break;
                case 3:
                    if (null!=authActivity.pDialog){
                        authActivity.pDialog.dismiss();
                    }
                    String sms = (String) msg.obj;
                    Toast.makeText(authActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }


    }
    private void setInf(RealName realName) {
        etId.setText(realName.getT_user_realname().getId_number());
        etRealName.setText(realName.getT_user_realname().getRealname());
        if (realName.getT_user_realname().getSex().equals("0")){
            rbMan.setChecked(false);
            rbWoman.setChecked(true);
        }else {
            rbMan.setChecked(true);
            rbWoman.setChecked(false);
        }
        Picasso.with(context).load(realName.getT_user_realname().getFront_image()).placeholder(R.mipmap.img_zhengmian).error(R.mipmap.img_zhengmian).into(imgFront);
        Picasso.with(context).load(realName.getT_user_realname().getBehind_image()).placeholder(R.mipmap.img_fanmian).error(R.mipmap.img_fanmian).into(imgOpposite);

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_auth);
        ButterKnife.inject(this);
    }

    @Override
    public void initViews() {
        FileUtils.newFolder(Constants.IMG_PATH);
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        title.setText("实名认证");
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        tel = (String) SPUtils.getParam(context, Constants.SP_LOGIN, Constants.SP_TEL, "0");
        loginId = (int) SPUtils.getParam(context, Constants.SP_LOGIN, Constants.SP_USERID, 0);
        status = (int) SPUtils.getParam(context, Constants.SP_LOGIN, Constants.SP_STATUS, 0);
        if (tel.equals("0")) {
            etPhoneAuth.setText("请认证手机号");
            rlPhone.setClickable(true);
        } else {
            etPhoneAuth.setText(tel + " (已绑定)");
            etPhoneAuth.setFocusable(false);
            etPhoneAuth.setFocusableInTouchMode(false);
            etPhoneAuth.setClickable(false);
            rlPhone.setClickable(false);
        }


        if (status==2){//已经认证 可以查询信息
            PostTask postTask=new PostTask(false,String.valueOf(loginId),null,null,null,null,null);
            postTask.execute();
            checkButton.setText("审核通过");
            checkButton.setClickable(false);
            checkButton.setFocusable(false);
            etRealName.setClickable(false);
            etRealName.setFocusable(false);
            etRealName.setFocusableInTouchMode(false);
            etId.setClickable(false);
            etId.setFocusable(false);
            etId.setFocusableInTouchMode(false);
            rbMan.setClickable(false);
            rbWoman.setClickable(false);
        }else if(status==3){//审核中
            checkButton.setText("正在审核");
            checkButton.setClickable(false);
            checkButton.setFocusable(false);
            PostTask postTask=new PostTask(false,String.valueOf(loginId),null,null,null,null,null);
            postTask.execute();
            etRealName.setClickable(false);
            etRealName.setFocusable(false);
            etRealName.setFocusableInTouchMode(false);
            etId.setClickable(false);
            etId.setFocusable(false);
            etId.setFocusableInTouchMode(false);
            rbMan.setClickable(false);
            rbWoman.setClickable(false);
        }else{

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
                imgFront.setImageURI(imgSource);
//                BitmapUtils.compressImage(imgFile.getAbsolutePath(),10000);
                BitmapUtils.compressBitmap(imgFile.getAbsolutePath(), 480, 360);
            }
        }
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // 处理你自己的逻辑 ....
                imgFile2 = new File(path.get(0));
                String choosePic = path.get(0).substring(path.get(0).lastIndexOf("."));
                fileName2 = Constants.IMG_PATH + CommonUtils.generateFileName() + choosePic;
                Uri imgSource = Uri.fromFile(imgFile2);
                imgOpposite.setImageURI(imgSource);
//                BitmapUtils.compressImage(imgFile.getAbsolutePath(),10000);
                Bitmap bitmap = BitmapUtils.compressBitmap(imgFile2.getAbsolutePath(), 480, 360);
                BitmapUtils.saveBitmap(bitmap, imgFile2);
            }
        }
    }



    public class PostTask extends AsyncTask<Void, Void, Void> {

        private final String loginId;
        private final String frontImage;
        private final String behindImage;
        private final String realname;
        private final String idNumber;
        private final String sex;
        private final boolean isPost;
//

        PostTask(boolean isPost,String loginId, String frontImage, String behindImage, String realname, String idNumber, String sex) {
            this.loginId = loginId;
            this.frontImage = frontImage;
            this.behindImage = behindImage;
            this.realname = realname;
            this.idNumber = idNumber;
            this.sex = sex;
            this.isPost = isPost;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                if (isPost){
                    postRealName();
                }else
                    getRealName();

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
         */
        public void postRealName() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.POST_REAL_NAME)
                    .addParams("only", only)
                    .addParams("login_id", loginId)
                    .addParams("front_image", frontImage)
                    .addParams("behind_image", behindImage)
                    .addParams("realname", realname)
                    .addParams("id_number", idNumber)
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
        public void getRealName() {
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
                    .execute(new Callback<BaseBean<RealName>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<RealName>>() {
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
}
