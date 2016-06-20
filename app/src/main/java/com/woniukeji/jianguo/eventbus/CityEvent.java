package com.woniukeji.jianguo.eventbus;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.woniukeji.jianguo.entity.CityBannerEntity;

/**
 * Created by invinjun on 2015/10/12.
 */
public class CityEvent {
    public CityBannerEntity.ListTCityEntity city;
    public boolean isGPS=false;
}
