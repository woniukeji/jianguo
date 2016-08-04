package com.woniukeji.jianmerchant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView view = new TextView(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String region = bundle.getString("region");
        int region_id = bundle.getInt("region_id");
        String type = bundle.getString("type");
        int type_id = bundle.getInt("type_id");
        String category = bundle.getString("category");
        int category_id = bundle.getInt("category_id");
        view.setText("测试页面 " +
                "region " +region+
                "region_id " + region_id+
                "type " + type +
                "type_id " + type_id+
                "category " +category+
                "category_id "+category_id);


        setContentView(view);
    }
}
