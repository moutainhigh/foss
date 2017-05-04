package com.deppon.foss.module.pickup.waybill.server.service.impl;

import com.deppon.foss.module.pickup.waybill.api.server.dao.IIntelligenceTimeRecordDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IIntelligenceTimeRecordService;
import com.deppon.foss.module.pickup.waybill.shared.domain.IntelligenceBillTimeGather;

public class IntelligenceTimeRecordService implements IIntelligenceTimeRecordService{
	//IntelligenceTimeRecordDao层
	IIntelligenceTimeRecordDao intelligenceTimeRecordDao;
	
	@Override
	public int insertTimeRecord(IntelligenceBillTimeGather ibtg) {
		// TODO Auto-generated method stub
		return intelligenceTimeRecordDao.insertTimeRecord(ibtg);
	}

	public IIntelligenceTimeRecordDao getIntelligenceTimeRecordDao() {
		return intelligenceTimeRecordDao;
	}

	public void setIntelligenceTimeRecordDao(
			IIntelligenceTimeRecordDao intelligenceTimeRecordDao) {
		this.intelligenceTimeRecordDao = intelligenceTimeRecordDao;
	}
}
