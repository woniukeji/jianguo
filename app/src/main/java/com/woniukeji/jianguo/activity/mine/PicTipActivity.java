package com.woniukeji.jianguo.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.woniukeji.jianguo.R;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

public class PicTipActivity extends Activity {

    @BindView(R.id.top) TextView top;
    @BindView(R.id.mid) TextView mid;
    @BindView(R.id.btn_iknow) Button btnIknow;
    boolean front;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_tip);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        front=intent.getBooleanExtra("front",true);

    }

    @OnClick(R.id.btn_iknow)
    public void onClick() {
         if (front){
//             Intent intent=getIntent();
//             intent.putExtra("TYPE","1");
             setResult(RESULT_OK);
             finish();
         }else {
             setResult(RESULT_OK);
             finish();
         }

    }
}
