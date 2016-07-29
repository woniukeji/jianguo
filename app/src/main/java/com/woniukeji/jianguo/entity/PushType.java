package com.woniukeji.jianguo.entity;

/**
 * Created by Administrator on 2016/6/28.
 */

public class PushType {
    private String type;
    /**
     * html_url : www.baidu.com
     */

    private String html_url;

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    private int job_id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }
}
