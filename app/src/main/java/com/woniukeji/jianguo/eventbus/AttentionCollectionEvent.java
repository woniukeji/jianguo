package com.woniukeji.jianguo.eventbus;

import com.woniukeji.jianguo.entity.JobDetails;
import com.woniukeji.jianguo.entity.Jobs;

/**
 * Created by invinjun on 2016/3/28.
 */
public class AttentionCollectionEvent {
    public JobDetails.TMerchantEntity tMerchantEntity;
    public Jobs.ListTJobEntity listTJob;
    public int position;
}
