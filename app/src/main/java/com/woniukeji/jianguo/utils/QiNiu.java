package com.woniukeji.jianguo.utils;

import android.content.Context;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.woniukeji.jianguo.base.Constants;

import org.json.JSONObject;

import java.io.File;
import java.security.KeyStore;

/**
 * Created by invinjun on 2016/3/10.
 */
public class QiNiu {

    public static  void upLoadQiNiu(Context context, String key, File imgFile) {
        String commonUploadToken = (String) SPUtils.getParam(context, Constants.LOGIN_INFO, Constants.SP_QNTOKEN, "");
        // 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(imgFile, key, commonUploadToken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //  res 包含hash、key等信息，具体字段取决于上传策略的设置。
                        LogUtils.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, null);
    }
}
