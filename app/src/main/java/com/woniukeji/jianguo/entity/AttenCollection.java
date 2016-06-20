package com.woniukeji.jianguo.entity;

import java.util.List;

/**
 * Created by invinjun on 2016/3/25.
 */
public class AttenCollection {
    private List<JobDetails.TMerchantEntity> list_t_merchant;
    private List<JobDetails.TJobInfoEntity> list_t_job;

    public List<JobDetails.TMerchantEntity> getList_t_merchant() {
        return list_t_merchant;
    }

    public void setList_t_merchant(List<JobDetails.TMerchantEntity> list_t_merchant) {
        this.list_t_merchant = list_t_merchant;
    }

    public List<JobDetails.TJobInfoEntity> getList_t_job() {
        return list_t_job;
    }

    public void setList_t_job(List<JobDetails.TJobInfoEntity> list_t_job) {
        this.list_t_job = list_t_job;
    }
}
