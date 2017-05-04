package com.deppon.foss.module.pickup.sign.api.server.service;

import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorImportantWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorWaybillDto;

/***
 * @clasaName:com.deppon.foss.module.pickup.sign.api.server.service.IRecordErrorWaybillService
 * @author: foss-yuting
 * @description: foss记录内物短少差错 上报OA
 * @date:2014年12月5日 下午15:59:21
 */
public interface IRecordErrorWaybillService {

	/***
	 * @clasaName:com.deppon.foss.module.pickup.sign.api.server.service.IRecordErrorWaybillService.recordErrorWaybillReportOA
	 * @author: foss-yuting
	 * @description: foss记录内物短少差错 上报OA
	 * @date:2014年12月8日 下午08:38:21
	 */
	void recordErrorWaybillReportOA(RecordErrorWaybillDto recordErrorWaybillDto);

	/**
	 * @author 306548-foss-honglujun
	 * foss记录重大货物异常自动上报信息 OA
	 */
	void recordErrorImportantWaybillReportOA(RecordErrorImportantWaybillDto recordErrorImportantWaybillDto);

}
