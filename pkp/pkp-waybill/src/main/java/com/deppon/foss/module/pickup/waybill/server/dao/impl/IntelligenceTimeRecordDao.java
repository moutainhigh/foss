package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.Date;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IIntelligenceTimeRecordDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.IntelligenceBillTimeGather;
import com.deppon.foss.util.UUIDUtils;


/**
 * @项目：智能开单项目
 * @功能：插入时间统计数据
 * @author:218371-foss-zhaoyanjun
 * @date:2016-05-20上午10:45
 */
public class IntelligenceTimeRecordDao extends iBatis3DaoImpl implements IIntelligenceTimeRecordDao{
	private static final String NAMESPACE="foss.pkp.intelligenceTimeRecord.";
	//插入统计时间
	@Override
	public int insertTimeRecord(IntelligenceBillTimeGather ibtg) {
		// TODO Auto-generated method stub
		ibtg.setId(UUIDUtils.getUUID());
		ibtg.setDate(new Date());
		return this.getSqlSession().insert(NAMESPACE+"insertRecord",ibtg);
	}
}
