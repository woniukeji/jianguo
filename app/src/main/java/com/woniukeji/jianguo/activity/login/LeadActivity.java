package com.woniukeji.jianguo.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.adapter.PageAdapter;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.activity.main.MainActivity;
//import com.woniukeji.jianguo.utils.PicassoLoader;
import com.woniukeji.jianguo.utils.SPUtils;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class LeadActivity extends Activity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.indicator_default_circle) CircleIndicator indicatorDefaultCircle;
    @BindView(R.id.btn_enter) Button btnEnter;
//    private InfiniteIndicator mAnimCircleIndicator;
//    private ArrayList<Page> pageViews = new ArrayList<>();
    private int[] drawables = new int[]{R.mipmap.lead1, R.mipmap.lead2, R.mipmap.lead3};
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        ButterKnife.bind(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


//    @Override
//    public void onPageClick(int position, Page page) {
//
//    }
//
//    private void initBannerData() {
//        for (int i = 0; i < 3; i++) {
//            pageViews.add(new Page(String.valueOf(String.valueOf(i)), drawables[i]));
//        }
//        mAnimCircleIndicator.addPages(pageViews);
//    }

    @Override
    protected void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        indicatorDefaultCircle = (CircleIndicator) findViewById(R.id.indicator_default_circle);
        ViewPager viewpager = (ViewPager)findViewById(R.id.viewpager);
        PageAdapter mPageAdapter=new PageAdapter(drawables.length,drawables);
        viewpager.setAdapter(mPageAdapter);
        indicatorDefaultCircle.setViewPager(viewpager);
//        indicatorDefaultCircle.setImageLoader(new PicassoLoader());
//
//        mAnimCircleIndicator.setPosition(InfiniteIndicator.IndicatorPosition.Center_Bottom);
//        mAnimCircleIndicator.setOnPageChangeListener(this);
//
//        mAnimCircleIndicator.isStopScrollWhenTouch();
//        mAnimCircleIndicator.scrollOnce();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
//        mAnimCircleIndicator.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        initBannerData();
//        mAnimCircleIndicator.start();
//        mAnimCircleIndicator.start();
    }

    @Override
    protected void onStop() {
//        mAnimCircleIndicator.stop();
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @OnClick(R.id.btn_enter)
    public void onClick() {
        SPUtils.setParam(LeadActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "1");
        startActivity(new Intent(LeadActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position > 1) {
            btnEnter.setVisibility(View.VISIBLE);
//            mAnimCircleIndicator.stop();
        } else {
            btnEnter.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (position > 1) {
            btnEnter.setVisibility(View.VISIBLE);
//            mAnimCircleIndicator.stop();
        } else {
            btnEnter.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Lead Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

}
