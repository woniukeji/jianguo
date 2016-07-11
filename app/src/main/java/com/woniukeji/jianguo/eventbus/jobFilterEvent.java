package com.woniukeji.jianguo.eventbus;

/**
 * Created by invinjun on 2015/10/12.
 * 如果首页一点切换城市或者定位地点之后确定切换 此时发送event通知partjobfragment更改cityid和相关区域id
 */
public class JobFilterEvent {
    public int position;
    public String cityId;
}
