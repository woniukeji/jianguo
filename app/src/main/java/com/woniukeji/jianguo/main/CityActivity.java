package com.woniukeji.jianguo.main;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.CityBannerEntity;
import com.woniukeji.jianguo.eventbus.CityEvent;
import com.woniukeji.jianguo.leanmessage.ImTypeMessageEvent;
import com.woniukeji.jianguo.utils.ActivityManager;
import com.woniukeji.jianguo.utils.DateUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;

public class CityActivity extends BaseActivity {

    @InjectView(R.id.et_search) EditText etSearch;
    @InjectView(R.id.btn_clear_search_text) Button btnClearSearchText;
    @InjectView(R.id.layout_clear_search_text) LinearLayout layoutClearSearchText;
    @InjectView(R.id.ll_search) LinearLayout llSearch;
    @InjectView(R.id.lv_search) ListView lvSearch;
    private Handler mHandler = new Myhandler(this);
    private Context context = CityActivity.this;
    private int MSG_POST_SUCCESS = 0;
    private int MSG_POST_FAIL = 1;
    private List<CityBannerEntity.ListTCityEntity> listTCityEntities =new ArrayList<CityBannerEntity.ListTCityEntity>();
    private Adapter adapter;


    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CityActivity schoolActivity = (CityActivity) reference.get();
            switch (msg.what) {
                case 0:
//                    if (null != schoolActivity.pDialog) {
//                    schoolActivity.pDialog.dismiss();
//                }
                    BaseBean<CityBannerEntity> schoolBaseBean = (BaseBean) msg.obj;
                    schoolActivity.listTCityEntities.clear();
                    schoolActivity.listTCityEntities.addAll(schoolBaseBean.getData().getList_t_city());
                    schoolActivity.showShortToast("查询成功");
                    schoolActivity.adapter.notifyDataSetChanged();
                    break;
                case 1:
//                    if (null != authActivity.pDialog) {
//                        authActivity.pDialog.dismiss();
//                    }
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(schoolActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;


                default:
                    break;
            }
        }


    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_city);
        ButterKnife.inject(this);
    }

    @Override
    public void initViews() {
         adapter=new Adapter();
        lvSearch.setAdapter(adapter);
        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CityBannerEntity.ListTCityEntity city = listTCityEntities.get(i);
                sendEvent(city);
            }
        });
    }
    private void sendEvent(CityBannerEntity.ListTCityEntity mCity) {
        CityEvent event = new CityEvent();
        event.city = mCity;
        EventBus.getDefault().post(event);
        finish();
    }
    @Override
    public void initListeners() {
        //搜索按键 模糊查询
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                GetTask getTask = new GetTask(etSearch.getText().toString());
                getTask.execute();
            }
        });
    }

    @Override
    public void initData() {
        GetTask getTask = new GetTask("");
        getTask.execute();

    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(CityActivity.this);
    }

    @Override
    public void onClick(View view) {

    }

    private  class Adapter extends BaseAdapter {

        private CityBannerEntity.ListTCityEntity Item;

        @Override
        public int getCount() {

            return listTCityEntities.size();
        }

        @Override
        public Object getItem(int i) {
            return listTCityEntities.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            Item = listTCityEntities.get(i);
            ViewHolder holder;
            LayoutInflater layoutInflater = LayoutInflater.from(context);;
            if (convertView==null) {
                convertView = layoutInflater.inflate(R.layout.school_item,null);
                holder=new ViewHolder();
                holder.tvSchool= (TextView) convertView.findViewById(R.id.tv_school);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvSchool.setText(Item.getCity());
            return convertView;
        }
         class ViewHolder{
            TextView tvSchool;

        }
    }

    public class GetTask extends AsyncTask<Void, Void, Void> {

        private final String school;
//

        GetTask(String school) {
            this.school = school;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
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
        public void getRealName() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.GET_CITY)
                    .addParams("only", only)
                    .addParams("name", school)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<CityBannerEntity>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<CityBannerEntity>>() {
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

    }
}
