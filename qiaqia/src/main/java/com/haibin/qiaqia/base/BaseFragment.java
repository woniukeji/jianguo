package com.haibin.qiaqia.base;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haibin.qiaqia.utils.LogUtils;

/**
 * Fragment
 *
 * @author invinjun
 */
public abstract class BaseFragment extends Fragment {
        protected BaseActivity mActivity;
    /**
     * onAttach
     *这个时候 activity已经传进来了
     * 获得activity的传递的值
     *就可以进行 与activity的通信里
    */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity) context;
    }
    /**
    *onCreat只调用一次
    *实例化变量 只执行一次
    */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
/**
*当系统用到fragment的时候 fragment就要返回他的view，越快越好，所以尽量在这里不要做耗时操作，比如从数据库加载大量数据显示listview，当然线程还是可以的。
*@author invinjun
*created at 2016/6/6 15:06
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    /**
     *这个方法主要是初始化那些你需要你的父Activity或者Fragment的UI已经被完整初始化才能初始化的元素。
     *@author invinjun
     *created at 2016/6/6 15:07
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        LogUtils.i("fragment","onDestroyView_Fragment");
//        if(view!=null){
//            ((ViewGroup)view.getParent()).removeView(view);
//        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

//获取宿主Activity
    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }

}
