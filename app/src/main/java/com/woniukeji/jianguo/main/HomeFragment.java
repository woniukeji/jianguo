package com.woniukeji.jianguo.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseFragment;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.Jobs;
import com.woniukeji.jianguo.entity.User;
import com.woniukeji.jianguo.utils.LogUtils;
import com.woniukeji.jianguo.utils.PicassoLoader;
import com.woniukeji.jianguo.widget.FixedRecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.page.OnPageClickListener;
import cn.lightsky.infiniteindicator.page.Page;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class HomeFragment extends BaseFragment implements  ViewPager.OnPageChangeListener,OnPageClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    @InjectView(R.id.list) FixedRecyclerView recycleList;
    @InjectView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private View headerView;
    private HomeJobAdapter adapter;
    private List<Jobs> jobs=new ArrayList<Jobs>();
    private ViewPager vp;
    private LinearLayout ll;
    private ArrayList<Page> pageViews;
    private InfiniteIndicator mAnimCircleIndicator;
    private InfiniteIndicator mAnimLineIndicator;
    private Handler mHandler = new Myhandler(this.getActivity());
    private Context context=this.getActivity();



    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity mainActivity = (MainActivity) reference.get();
            switch (msg.what) {
                case 0:
                    BaseBean<User> user = (BaseBean<User>) msg.obj;
                    Intent intent = new Intent(mainActivity, MainActivity.class);
//                    intent.putExtra("user", user);
                    mainActivity.startActivity(intent);
                    mainActivity.finish();
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(mainActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:

                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(mainActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HomeFragment newInstance(int columnCount) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jobitem_list, container, false);
// Set the adapter

        ButterKnife.inject(this, view);
        headerView = inflater.inflate(R.layout.home_header_view, container, false);
        initData();
        mAnimCircleIndicator = (InfiniteIndicator)headerView.findViewById(R.id.indicator_default_circle);
        mAnimCircleIndicator.setImageLoader(new PicassoLoader());
        mAnimCircleIndicator.addPages(pageViews);
        mAnimCircleIndicator.setPosition(InfiniteIndicator.IndicatorPosition.Center_Bottom);
        mAnimCircleIndicator.setOnPageChangeListener(this);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);

        adapter = new HomeJobAdapter(jobs,getActivity());
        adapter.addHeaderView(headerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//设置布局管理器
        recycleList.setLayoutManager(mLayoutManager);
//设置adapter
        recycleList.setAdapter(adapter);
//设置Item增加、移除动画
        recycleList.setItemAnimator(new DefaultItemAnimator());
//添加分割线
//        recycleList.addItemDecoration(new RecyclerView.ItemDecoration() {
//        });
//        recycleList.addItemDecoration(new DividerItemDecoration(
//                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });
        return view;
    }
    private void initData() {
        pageViews = new ArrayList<>();
        pageViews.add(new Page("A ", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/a.jpg",this));
        pageViews.add(new Page("B ", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/b.jpg",this));
        pageViews.add(new Page("C ", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/c.jpg",this));
        pageViews.add(new Page("D ", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/d.jpg",this));

    }
    @Override
    public void onResume() {
        for (int i = 0; i < 10; i++) {
            jobs.add(new Jobs());
        }
        adapter.notifyDataSetChanged();
        mAnimCircleIndicator.start();
        LogUtils.i("fragment",":onDestroy");
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.i("fragment",":onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        mAnimCircleIndicator.stop();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageClick(int position, Page page) {

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        LogUtils.i("fragment",":onDestroyView");
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
    }
}
