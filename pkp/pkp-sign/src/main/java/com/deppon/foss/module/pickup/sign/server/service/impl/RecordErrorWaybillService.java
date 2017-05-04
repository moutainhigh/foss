package com.deppon.foss.module.pickup.sign.server.service.impl;

import com.deppon.foss.module.pickup.sign.api.server.dao.IRecordErrorWaybillDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IRecordErrorWaybillService;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorImportantWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorWaybillDto;

/***
 * @clasaName:com.deppon.foss.module.pickup.sign.server.service.impl.RecordErrorWaybillService
 * @author: foss-yuting
 * @description: foss记录内物短少差错 上报OA
 * @date:2014年12月5日 下午15:59:21
 */
public class RecordErrorWaybillService implements IRecordErrorWaybillService {

	private IRecordErrorWaybillDao recordErrorWaybillDao;
	/***
	 * @clasaName:com.deppon.foss.module.pickup.sign.api.server.service.IRecordErrorWaybillService.recordErrorWaybillReportOA
	 * @author: foss-yuting
	 * @description: foss记录内物短少差错 上报OA
	 * @date:2014年12月8日 下午08:38:21
	 */
	@Override
	public void recordErrorWaybillReportOA(RecordErrorWaybillDto recordErrorWaybillDto) {
		recordErrorWaybillDao.insertEntity(recordErrorWaybillDto);
	}
	
	/**
	 * @author 306548-foss-honglujun
	 * @param recordErrorWaybillDao
	 * foss记录重大货物异常自动上报信息 OA
	 */
	@Override
	public void recordErrorImportantWaybillReportOA(RecordErrorImportantWaybillDto recordErrorImportantWaybillDto) {
		recordErrorWaybillDao.insertImportantEntity(recordErrorImportantWaybillDto);
	}
	
	public void setRecordErrorWaybillDao(IRecordErrorWaybillDao recordErrorWaybillDao) {
		this.recordErrorWaybillDao = recordErrorWaybillDao;
	}
}
