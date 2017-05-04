package com.deppon.foss.module.pickup.waybill.api.server.dao;

import com.deppon.foss.module.pickup.waybill.shared.domain.IntelligenceBillTimeGather;
/**
 * @项目：智能开单项目
 * @功能：插入时间统计数据
 * @author:218371-foss-zhaoyanjun
 * @dateL2016-05-20上午10:43
 */
public interface IIntelligenceTimeRecordDao {
	//插入时间统计数据
	int insertTimeRecord(IntelligenceBillTimeGather ibtg);
}
