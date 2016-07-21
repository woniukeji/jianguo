package com.woniukeji.jianguo.mine;

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

import com.avos.avoscloud.okhttp.FormEncodingBuilder;
import com.avos.avoscloud.okhttp.Request;
import com.avos.avoscloud.okhttp.RequestBody;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.base.MyApiEndpointInterface;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.School;
import com.woniukeji.jianguo.entity.User;
import com.woniukeji.jianguo.utils.ActivityManager;
import com.woniukeji.jianguo.utils.DateUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class SchoolActivity extends BaseActivity {
    @BindView(R.id.et_search) EditText etSearch;
    @BindView(R.id.btn_clear_search_text) Button btnClearSearchText;
    @BindView(R.id.layout_clear_search_text) LinearLayout layoutClearSearchText;
    @BindView(R.id.ll_search) LinearLayout llSearch;
    @BindView(R.id.lv_search) ListView lvSearch;
    private Handler mHandler = new Myhandler(this);
    private Context context = SchoolActivity.this;
    private int MSG_POST_SUCCESS = 0;
    private int MSG_POST_FAIL = 1;
    private List<School.tschool> tschools=new ArrayList<School.tschool>();
    private Adapter adapter;




    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SchoolActivity schoolActivity = (SchoolActivity) reference.get();
            switch (msg.what) {
                case 0:
//                    if (null != schoolActivity.pDialog) {
//                    schoolActivity.pDialog.dismiss();
//                }
                    BaseBean<School> schoolBaseBean = (BaseBean) msg.obj;
                    schoolActivity.tschools.clear();
                    schoolActivity.tschools.addAll(schoolBaseBean.getData().getList_t_school());
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
        setContentView(R.layout.activity_school);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
         adapter=new Adapter();
        lvSearch.setAdapter(adapter);
        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String schoolTemp=tschools.get(i).getName();
                Intent intent=getIntent();
                intent.putExtra("school",schoolTemp);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
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
        ActivityManager.getActivityManager().addActivity(SchoolActivity.this);
    }

    @Override
    public void onClick(View view) {

    }

    private  class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return tschools.size();
        }

        @Override
        public Object getItem(int i) {
            return tschools.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            School.tschool schItem = tschools.get(i);
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
            holder.tvSchool.setText(schItem.getName());
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
                    .url(Constants.GET_SCHOOL)
                    .addParams("only", only)
                    .addParams("name", school)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<School>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response,int id) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<School>>() {
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

    }
}
