package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.IntelligenceBillTimeGather;

/**
 *@项目：智能开单项目
 *@author:218371-foss-zhaoyanjun
 *@date:2016-05-19下午18:24
 */
public interface IIntelligenceTimeRecordService extends IService{
	//保存开单的时间记录
	int insertTimeRecord(IntelligenceBillTimeGather ibtg);
}
