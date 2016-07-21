package com.woniukeji.jianguo.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
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
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.squareup.picasso.Picasso;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.RealName;
import com.woniukeji.jianguo.login.BindPhoneActivity;
import com.woniukeji.jianguo.utils.ActivityManager;
import com.woniukeji.jianguo.utils.BitmapUtils;
import com.woniukeji.jianguo.utils.CommonUtils;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.EditCheckUtil;
import com.woniukeji.jianguo.utils.FileUtils;
import com.woniukeji.jianguo.utils.LogUtils;
import com.woniukeji.jianguo.utils.MD5Coder;
import com.woniukeji.jianguo.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.Response;

public class AuthActivity extends BaseActivity {
    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView title;
    @BindView(R.id.img_front) ImageView imgFront;
    @BindView(R.id.tv_not1) TextView tvNot1;
    @BindView(R.id.tv_not2) TextView tvNot2;
    @BindView(R.id.img_opposite) ImageView imgOpposite;
    @BindView(R.id.ll_top) LinearLayout llTop;
    @BindView(R.id.img_phone) ImageView imgPhone;
    @BindView(R.id.img) ImageView img;
    @BindView(R.id.tv_phone_auth) TextView etPhoneAuth;
    @BindView(R.id.et_real_name) EditText etRealName;
    @BindView(R.id.et_id) EditText etId;
    @BindView(R.id.img_pass) ImageView imgPass;
    @BindView(R.id.rb_man) RadioButton rbMan;
    @BindView(R.id.rb_woman) RadioButton rbWoman;
    @BindView(R.id.check_button) Button checkButton;
    @BindView(R.id.rl_phone) RelativeLayout rlPhone;
    private int MSG_POST_SUCCESS = 0;
    private int MSG_POST_FAIL = 1;
    private int MSG_GET_SUCCESS = 2;
    private int MSG_GET_FAIL = 3;
    private File imgFile;
    private String fileName;
    private String fileName2;
    private File imgFile2;
    private int loginId;
    private int status;
    private String tel;
    private String sex="1";
    public SweetAlertDialog pDialog ;

    private Handler mHandler = new Myhandler(this);
    private Context context = AuthActivity.this;
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
                    SPUtils.setParam(authActivity,Constants.LOGIN_INFO,Constants.SP_STATUS,3);
                    authActivity.showShortToast("提交成功");
                    authActivity.finish();
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
//                    authActivity.showShortToast("获取实名信息成功");
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
        etRealName.setText(realName.getT_user_realname().getRealname());
        if (realName.getT_user_realname().getSex().equals("0")){
            rbMan.setChecked(false);
            rbWoman.setChecked(true);
        }else {
            rbMan.setChecked(true);
            rbWoman.setChecked(false);
        }
          SPUtils.setParam(context,Constants.LOGIN_INFO,Constants.SP_STATUS,realName.getT_user_realname().getStatus());
        if (realName.getT_user_realname().getStatus()==2){//已经认证 可以查询信息
//            PostTask postTask=new PostTask(false,String.valueOf(loginId),null,null,null,null,null);
//            postTask.execute();
            imgPass.setVisibility(View.VISIBLE);
            tvNot1.setText("您已认证通过");
            tvNot2.setText("为保证您的信息安全，兼果已为您隐藏个人信息");
            checkButton.setText("审核通过");
            checkButton.setBackgroundResource(R.color.gray);
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
            imgFront.setClickable(false);
            imgOpposite.setClickable(false);
            String id=realName.getT_user_realname().getId_number();
            etId.setText(id.substring(0,id.length()-6)+"******");
        }else if(realName.getT_user_realname().getStatus()==3){//审核中
            checkButton.setText("正在审核");
            checkButton.setClickable(false);
            checkButton.setFocusable(false);
            checkButton.setBackgroundResource(R.color.gray);
//            PostTask postTask=new PostTask(false,String.valueOf(loginId),null,null,null,null,null);
//            postTask.execute();
            etRealName.setClickable(false);
            etRealName.setFocusable(false);
            etRealName.setFocusableInTouchMode(false);
            etId.setClickable(false);
            etId.setFocusable(false);
            etId.setFocusableInTouchMode(false);
            rbMan.setClickable(false);
            rbWoman.setClickable(false);
            imgFront.setClickable(false);
            imgOpposite.setClickable(false);
            String id=realName.getT_user_realname().getId_number();
            etId.setText(id.substring(0,id.length()-6)+"******");
            Picasso.with(context).load(realName.getT_user_realname().getFront_image()).placeholder(R.mipmap.icon_fanmian).error(R.mipmap.icon_fanmian).into(imgFront);
            Picasso.with(context).load(realName.getT_user_realname().getBehind_image()).placeholder(R.mipmap.icon_zhengmian).error(R.mipmap.icon_zhengmian).into(imgOpposite);

        }else if(realName.getT_user_realname().getStatus()==4){//未通过
            checkButton.setText("重新审核");
            etId.setText("");
//            Picasso.with(context).load(realName.getT_user_realname().getFront_image()).placeholder(R.mipmap.icon_fanmian).error(R.mipmap.icon_fanmian).into(imgFront);
//            Picasso.with(context).load(realName.getT_user_realname().getBehind_image()).placeholder(R.mipmap.icon_zhengmian).error(R.mipmap.icon_zhengmian).into(imgOpposite);
            checkButton.setBackgroundResource(R.drawable.button_background_login);
//            PostTask postTask=new PostTask(false,String.valueOf(loginId),null,null,null,null,null);
//            postTask.execute();
        }
    }
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
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


    }

    @Override
    protected void onStart() {
        super.onStart();
        tel = (String) SPUtils.getParam(context, Constants.LOGIN_INFO, Constants.SP_TEL, "0");
        loginId = (int) SPUtils.getParam(context, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
//        showShortToast("用户id： "+loginId);
        status = (int) SPUtils.getParam(context, Constants.LOGIN_INFO, Constants.SP_STATUS, 0);
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
        getRealName(String.valueOf(loginId));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this);
    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(AuthActivity.this);
    }

    @OnClick({R.id.rl_phone,R.id.img_back, R.id.img_front, R.id.img_opposite,  R.id.rb_man, R.id.rb_woman, R.id.check_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_phone:
                startActivity(new Intent(context, BindPhoneActivity.class));
//                finish();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_front:
                //单选多选
                Intent intent=new Intent(AuthActivity.this,PicTipActivity.class);
                intent.putExtra("front",true);
                startActivityForResult(intent,2);
//                MultiImageSelectorActivity.startSelect(AuthActivity.this, 0, 1, 0);
                break;
            case R.id.img_opposite:
                Intent intent1=new Intent(AuthActivity.this,PicTipActivity.class);
                intent1.putExtra("front",false);
                startActivityForResult(intent1,3);

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
                }else if(!EditCheckUtil.IDCardValidate(id)){
                    showShortToast("身份证号码不正确");
                    break;
                }

                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("请稍后...");
                pDialog.setCancelable(false);
                pDialog.show();
               upLoadQiNiu(context, MD5Coder.getQiNiuName(fileName), imgFile,1,name,id);
                break;
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try{
            super.onRestoreInstanceState(savedInstanceState);
        }catch(Exception e){
            savedInstanceState = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        try{
            super.onRestoreInstanceState(outState);
        }catch(Exception e){
            outState = null;
        }

    }

    public  void upLoadQiNiu(final Context context, String key, File imgFile, final int position, final String name, final String id) {
        String commonUploadToken = (String) SPUtils.getParam(context, Constants.LOGIN_INFO, Constants.SP_QNTOKEN, "");
        // 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(imgFile, key, commonUploadToken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //  res 包含hash、key等信息，具体字段取决于上传策略的设置。
                        LogUtils.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                        if (position==1){
                            upLoadQiNiu(context, MD5Coder.getQiNiuName(fileName2), imgFile2,2,name,id);
                        }
                        if (position==2){
                            if (res!=null){
                                String url1="http://7xlell.com2.z0.glb.qiniucdn.com/"+MD5Coder.getQiNiuName(fileName);
                                String url2="http://7xlell.com2.z0.glb.qiniucdn.com/"+MD5Coder.getQiNiuName(fileName2);
//                                PostTask postTask=new PostTask(true,String.valueOf(loginId),url1,url2,name,id,sex);
//                                postTask.execute(String.valueOf(loginId),url1,url2,name,id,sex);
                                postRealName(String.valueOf(loginId),url1,url2,name,id,sex);
                            }else {
                                pDialog.dismiss();
                                showShortToast("上传失败，请重试！");
                            }

                        }
                    }
                }, null);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //ADW: sometimes on rotating the phone, some widgets fail to restore its states.... so... damn.

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // 处理你自己的逻辑 ....
                imgFile = new File(path.get(0));
                String choosePic = path.get(0).substring(path.get(0).lastIndexOf("."));
                fileName = Constants.IMG_PATH + CommonUtils.generateFileName() + choosePic;
                Uri imgSource = Uri.fromFile(imgFile);
//                imgFront.setImageURI(imgSource);
                Bitmap bitmap=BitmapUtils.compressBitmap(imgFile.getAbsolutePath(),1080, 720);
                imgFront.setImageBitmap(bitmap);
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
//                imgOpposite.setImageURI(imgSource);
                Bitmap bitmap=BitmapUtils.compressBitmap(imgFile2.getAbsolutePath(),1080, 720);
                imgOpposite.setImageBitmap(bitmap);
//                BitmapUtils.compressBitmap(imgFile.getAbsolutePath(),1080, 720);
//                BitmapUtils.compressImage(imgFile.getAbsolutePath(),10000);

//                Bitmap bitmap = BitmapUtils.compressBitmap(imgFile2.getAbsolutePath(),true, 1080, 720);
//                Bitmap bitmap = BitmapUtils.compressImage(imgFile2.getAbsolutePath(),null, false,1000,);
//                BitmapUtils.saveBitmap(bitmap, imgFile2);
            }
        }
        if (requestCode==2){//弹出提示 提示返回 然后跳转拍照界面 正面
            if (resultCode == RESULT_OK) {
                MultiImageSelectorActivity.startSelect(AuthActivity.this, 0, 1, 0);
            }
        }
        if (requestCode==3){//弹出提示 提示返回 然后跳转拍照界面 背面
            if (resultCode == RESULT_OK) {
                MultiImageSelectorActivity.startSelect(AuthActivity.this, 1, 1, 0);
            }
        }
    }
        /**
         * postInfo
         * @param id
         * @param sex
         */
        public void postRealName(String loginId, String frontImage, String behindImage, String realname, String id, String sex) {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.POST_REAL_NAME)
                    .addParams("only", only)
                    .addParams("login_id", loginId)
                    .addParams("front_image", frontImage)
                    .addParams("behind_image", behindImage)
                    .addParams("realname", realname)
                    .addParams("id_number", id)
                    .addParams("sex", sex)
                    .build()
                    .connTimeOut(100000)
                    .readTimeOut(100000)
                    .writeTimeOut(100000)
                    .execute(new Callback<BaseBean>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response,int id) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean>() {
                            }.getType());
                            return baseBean;
                        }

                        @Override
                        public void onError(Call call, Exception e,int id) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_POST_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean baseBean,int id) {
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
        public void getRealName(String loginId) {
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
                        public BaseBean parseNetworkResponse(Response response,int id) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<RealName>>() {
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
//                                SPUtils.setParam(AuthActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
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
