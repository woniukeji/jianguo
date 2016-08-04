package com.haibin.qiaqia.cart;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haibin.qiaqia.R;
import com.haibin.qiaqia.base.BaseFragment;
import com.haibin.qiaqia.base.Constants;
import com.haibin.qiaqia.entity.Goods;
import com.haibin.qiaqia.entity.ListChaoCommodity;
import com.haibin.qiaqia.http.HttpMethods;
import com.haibin.qiaqia.http.ProgressSubscriber;
import com.haibin.qiaqia.http.SubscriberOnNextListener;
import com.haibin.qiaqia.utils.ArithUtil;
import com.haibin.qiaqia.utils.SPUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link } subclass.
 */
public class CartFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.recyclerview)
    XRecyclerView recyclerview;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_cart)
    TextView tvCart;
    @BindView(R.id.rl_header)
    RelativeLayout rlHeader;
    @BindView(R.id.cart_sum)
    TextView cartSum;
    @BindView(R.id.cart_submit)
    TextView cartSubmit;
    @BindView(R.id.cart_delete)
    ImageView cartDelete;
    private CartAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    public List<ListChaoCommodity> listChaoCommodities = new ArrayList<ListChaoCommodity>();
    SubscriberOnNextListener<Goods> SubListener;

    private int clickNum = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    public void initView() {
        adapter = new CartAdapter(getActivity(), listChaoCommodities);
        mLayoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        recyclerview.setLayoutManager(mLayoutManager);
        //设置adapter
        recyclerview.setAdapter(adapter);
        //设置Item增加、移除动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        cartSum.setOnClickListener(this);
        cartDelete.setOnClickListener(this);

        adapter.setOnGoodsListener(new CartAdapter.OnGoodsAMLitener() {
            @Override
            public void minusGoods(int postion) {
                ListChaoCommodity currentGood = listChaoCommodities.get(postion);
                currentGood.setCount(currentGood.getCount() - 1);
                refreshAdapter();
            }

            @Override
            public void addGoods(int postion) {
                ListChaoCommodity currentGood = listChaoCommodities.get(postion);
                currentGood.setCount(currentGood.getCount() + 1);
                refreshAdapter();
            }

            @Override
            public void deleteGoods(int postion) {

            }
        });
        ;
    }

    public void initData() {
        int loginId = (int) SPUtils.getParam(getActivity(), Constants.USER_INFO, Constants.INFO_ID, 0);
        SubListener = new SubscriberOnNextListener<Goods>() {
            @Override
            public void onNext(Goods goodsHttpResult) {
                listChaoCommodities.addAll(goodsHttpResult.getListChaoCommodity());
                adapter.notifyDataSetChanged();
                setCountMoney();
                Toast.makeText(getActivity(), "获取成功", Toast.LENGTH_LONG).show();
            }
        };
        HttpMethods.getInstance().getCarInfo(new ProgressSubscriber<Goods>(SubListener, getActivity()), String.valueOf(loginId));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cart_delete:
                boolean over = isOver(clickNum);
                if (over){
                    adapter.isShowChexkBox(true);
                    adapter.notifyDataSetChanged();
                    clickNum ++;
                }else{
                    adapter.isShowChexkBox(false);
                    adapter.notifyDataSetChanged();
                    clickNum ++;

                }
                break;
            case R.id.cart_submit:
                break;
            default:
                break;
        }
    }

    private void setCountMoney() {
        double money = countMoney();
        cartSum.setText(Html.fromHtml("合计:" + "<font color='#E34C50'>" + "￥" + money + "</font>"));
    }

    private void refreshAdapter() {
        adapter.notifyDataSetChanged();
        setCountMoney();
    }

    private double countMoney() {
        double sumMoney = 0;
        for (int i = 0; i < listChaoCommodities.size(); i++) {
            ListChaoCommodity data = listChaoCommodities.get(i);
            double sum = mul(data.getPrice(), data.getCount());
            sumMoney = ArithUtil.add(sumMoney, sum);
        }
        return sumMoney;
    }

    public static double mul(double v1, int v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    public boolean isOver(int num){
        if (num % 2 == 0)
            return  true;
        else
            return false;
    }

    @Override
    public void onStop() {
        super.onStop();
        clickNum = 0;
    }
}
