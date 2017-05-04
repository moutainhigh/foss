package com.deppon.foss.module.transfer.oa.server.ws;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.deppon.foss.module.transfer.oa.server.domain.LostReportEntityRequest;

public interface ICRMFossQMSService {
	/**
	 * crm传递给FOSS运单号丢货上报
	 * @author 362917
	 * @date 2016-11-11 14:31:11
	 */
	@RequestMapping(value = "getWayBillToReport", method = RequestMethod.POST)
	void getWayBillToReport(LostReportEntityRequest lostReportEntityRequest) ;
}
