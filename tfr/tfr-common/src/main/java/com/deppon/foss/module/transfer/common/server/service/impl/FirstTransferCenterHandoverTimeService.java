package com.deppon.foss.module.transfer.common.server.service.impl;

import java.util.Date;
import com.deppon.foss.module.transfer.common.api.server.dao.IFirstTransferCenterHandoverTimeDao;
import com.deppon.foss.module.transfer.common.api.server.service.IFirstTransferCenterHandoverTimeService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;

public class FirstTransferCenterHandoverTimeService implements IFirstTransferCenterHandoverTimeService{

	/**
	* @fields tfrNotifyDao
	* @author 283250-foss-liuyi
	* @update 2016年4月26日 下午3:12:37
	* @version V1.0
	*/
	private IFirstTransferCenterHandoverTimeDao firstTransferCenterHandoverTimeDao;
	
	@Override
	public Date getFirstTransferCenterHandoverTime(String wayBillNo) {
		if (wayBillNo == null || "".equals(wayBillNo)) {
			throw new TfrBusinessException("运单号为空，无法获取时间");
		}
		Date handoverTime = firstTransferCenterHandoverTimeDao.getFirstTransferCenterHandoverTime(wayBillNo);

		if (handoverTime == null || "".equals(handoverTime)) {
			throw new TfrBusinessException("系统没有找到对应的记录，无法获取时间");
		}
		return handoverTime;
	}

	public void setFirstTransferCenterHandoverTimeDao(
			IFirstTransferCenterHandoverTimeDao firstTransferCenterHandoverTimeDao) {
		this.firstTransferCenterHandoverTimeDao = firstTransferCenterHandoverTimeDao;
	}
}
