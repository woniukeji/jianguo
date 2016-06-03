package com.haibin.haibin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.Response;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class AddActivity extends Activity {
     EditText etName;
     ImageView imageView;
    Spinner spCategory;
    EditText etPrice;
    EditText etDanwei;
    EditText etCreatDate;
   EditText etSaveDate;
    EditText etSize;
    EditText etNickname;
    EditText etDescribe;
    Button btnPost;
    private Handler mHandler = new Myhandler(this);
    private Context context = AddActivity.this;
    String fileName="";
    File imgFile;
    String token;
    MyAdapter me;
    List<CategoryBean.DataEntity.ListChaoClassEntity> listChaoClass=new ArrayList<CategoryBean.DataEntity.ListChaoClassEntity>();
    int categoryId=0;
    private SweetAlertDialog downLoadDialog;

    @OnClick(R.id.btn_post)
    public void onClick() {
    }

    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AddActivity authActivity = (AddActivity) reference.get();
            switch (msg.what) {
                case 0:
                    CategoryBean baseBean = (CategoryBean) msg.obj;
                    authActivity.listChaoClass.addAll(baseBean.getData().getList_chao_class());
                    authActivity.me.notifyDataSetChanged();
                    authActivity.categoryId= authActivity.listChaoClass.get(0).getId();
                    authActivity.token=baseBean.getQiniu_token();
//                    authActivity.spCategory.
                    //手动保存认证状态 防止未重新登录情况下再次进入该界面
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(authActivity,ErrorMessage,Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    String Message = (String) msg.obj;
                    Toast.makeText(authActivity,Message,Toast.LENGTH_LONG).show();
                    break;
                case 3:
                default:
                    break;
            }
        }


    }



    private boolean mVisible;
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
//        ButterKnife.bind(this);
        etName= (EditText) findViewById(R.id.et_phone_name);
        etPrice= (EditText) findViewById(R.id.et_price);
        etDanwei= (EditText) findViewById(R.id.et_danwei);
        etCreatDate= (EditText) findViewById(R.id.et_creat_date);
        etSaveDate= (EditText) findViewById(R.id.et_save_date);
        etNickname= (EditText) findViewById(R.id.et_nickname);
        etDescribe= (EditText) findViewById(R.id.et_describe);
        btnPost= (Button) findViewById(R.id.btn_post);
        imageView= (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiImageSelectorActivity.startSelect(AddActivity.this, 0, 1, 0);
            }
        });
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileName.equals("")){
                    Toast.makeText(context,"图片为空",Toast.LENGTH_LONG).show();
                    return;
                }
                 downLoadDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                downLoadDialog.setTitleText("正在上传");
                downLoadDialog.show();
                QiNiu.upLoadQiNiu(context, fileName, imgFile,token);
                String url1="http://7xlell.com2.z0.glb.qiniucdn.com/"+fileName;
                PostTask postTask = new PostTask(true,etName.getText().toString(),url1,
                      etPrice.getText().toString(),
                        etCreatDate.getText().toString(),etSaveDate.getText().toString(),etNickname.getText().toString(),etDescribe.getText().toString());
                postTask.execute();
            }
        });
        spCategory = (Spinner) findViewById(R.id.sp_category);

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

               categoryId=listChaoClass.get(pos).getId();
                Toast.makeText(AddActivity.this, "你点击的是:"+categoryId, 2000).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        me=new MyAdapter(AddActivity.this,listChaoClass) ;
        spCategory.setAdapter(me);
        PostTask postTask = new PostTask(false);
        postTask.execute();
    }
    /**
     * 自定义适配器类
     * @author jiangqq  <a href=http://blog.csdn.net/jiangqq781931404></a>
     *
     */
    public class MyAdapter extends BaseAdapter {
        private List<CategoryBean.DataEntity.ListChaoClassEntity> mList;
        private Context mContext;

        public MyAdapter(Context pContext, List<CategoryBean.DataEntity.ListChaoClassEntity> pList) {
            this.mContext = pContext;
            this.mList = pList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        /**
         * 下面是重要代码
         */
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);
            convertView=_LayoutInflater.inflate(R.layout.category_item, null);
            TextView textView= (TextView) convertView.findViewById(R.id.tv_item);
            textView.setText(mList.get(position).getName());
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    categoryId=mList.get(position).getId();
//                }
//            });
            return convertView;
        }
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    public class PostTask extends AsyncTask<Void, Void, Void> {

        private String name;
        private String image;
        private String price;
        private String p_date;
        private String shelf_life;
        private String alias;
        private String describe;
        private boolean isPost;
//

        PostTask(boolean isPost, String name, String image, String price, String p_date, String shelf_life, String alias, String describe) {
            this.name = name;
            this.image = image;
            this.price = price;
            this.p_date = p_date;
            this.shelf_life = shelf_life;
            this.alias = alias;
            this.describe = describe;
            this.isPost = isPost;
        }

        PostTask(boolean isPost) {
            this.isPost = isPost;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                if (isPost) {
                    postRealName();
                } else
                    getCategory();

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
                    .url("http://101.200.197.237:8080/I_chao_commodity_Insert_Servlet")
                    .addParams("only", only)
                    .addParams("name", name)
                    .addParams("image", image)
                    .addParams("price", price)
                    .addParams("p_date", p_date)
                    .addParams("shelf_life", shelf_life)
                    .addParams("alias", alias)
                    .addParams("describe", describe)
                    .addParams("chao_class_id", categoryId+"")
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
                            message.what = 2;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean response) {
                            if (response.getCode().equals("200")) {
//                                SPUtils.setParam(AuthActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                                Message message = new Message();
                                message.obj = response.getMessage();
                                message.what = 1;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = response.getMessage();
                                message.what = 2;
                                mHandler.sendMessage(message);
                            }
                        }


                    });
        }

        /**
         * postInfo
         */
        public void getCategory() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url("http://101.200.197.237:8080/I_chao_class_Select_Servlet")
                    .addParams("only", only)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<CategoryBean>() {
                        @Override
                        public CategoryBean parseNetworkResponse(Response response) throws Exception {
                            String string = response.body().string();
                            CategoryBean baseBean = new Gson().fromJson(string, new TypeToken<CategoryBean>() {
                            }.getType());
                            return baseBean;
                        }

                        @Override
                        public void onError(Call call, Exception e) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = 0;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(CategoryBean baseBean) {
                            if (baseBean.getCode().equals("200")) {
//                                SPUtils.setParam(AuthActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                                Message message = new Message();
                                message.obj = baseBean;
                                message.what = 0;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = 1;
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
                 fileName = CommonUtils.generateFileName() + choosePic;
                Uri imgSource = Uri.fromFile(imgFile);
                imageView.setImageURI(imgSource);
//                BitmapUtils.compressImage(imgFile.getAbsolutePath(),10000);
                BitmapUtils.compressBitmap(imgFile.getAbsolutePath(), 360, 360);
            }
        }
    }
}
