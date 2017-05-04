package com.deppon.foss.module.transfer.stockchecking.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.transfer.stockchecking.api.server.service.IStReportService;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferDetailEntity;
import com.deppon.foss.module.transfer.stockchecking.test.BaseTestCase;
import com.deppon.foss.util.DateUtils;

public class StReportServiceTest extends BaseTestCase {
	@Autowired
	private IStReportService stReportService;
	
	@Test
	public void testExecuteStReportTask(){
		stReportService.executeStReportTask(DateUtils.convert("2013-01-10 01:00:00", "yyyy-MM-dd HH:mm:ss"),
											DateUtils.convert("2013-02-01 12:00:00", "yyyy-MM-dd HH:mm:ss"),
											0, 1);
	}
	
//	@Test
	public void testExecuteStOAErrorTask(){
		stReportService.executeStOAErrorTask(DateUtils.convert("2013-11-01 12:00:00", "yyyy-MM-dd HH:mm:ss"), 0, 1);
	}
	
//	@Test
	public void testQueryStDifferDetailEntityList(){
		stReportService.queryStDifferDetailEntityList("", "", "");
	}
	
//	@Test
	public void testUpdateReportDetailList(){
		StDifferDetailEntity detail = new StDifferDetailEntity();
		detail.setId("ff180068-3445-46eb-a07d-7a4240ef3d43");
		detail.setStDifferReportId("719ae006-41ef-43a1-bc59-d1ea29bb186f");
		detail.setHandleStatus("DONE");
		
		stReportService.updateReportDetail(detail, "", "");
	}
}