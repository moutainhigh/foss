package com.deppon.foss.module.transfer.common.test.service;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.test.BaseTestCase;
import com.deppon.foss.util.DateUtils;

public class TfrCommonServiceTest extends BaseTestCase{
	@Autowired
	private ITfrCommonService tfrCommonService;
	
//	@Test
	public void testGenerateSerialNumberDC/**订舱编号*/(){
		String sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.DC);
		sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.DC);
		sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.DC);
		sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.DC);
		sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.DC);
		String dateFormat = DateUtils.convert(Calendar.getInstance().getTime(), "yyyyMMdd");
		String expectSn = dateFormat + "-000005";
		Assert.assertEquals(sn, expectSn);
	}
	
//	@Test
	public void testGenerateSerialNumberQC/**清仓任务*/(){
		String sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.QC, "GS00002");
		logger.debug("清仓任务SN:	" + sn);
	}
	
	@Test
	public void testGenerateSerialNumberCY/**清仓差异报告*/(){
		String sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.CY, "042012120300001");
		logger.debug("清仓差异报告SN:" + sn);
	}
//	@Test
	public void testGenerateSerialNumberHP/**合票号*/(){
		String sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.HP);
		logger.debug("合票号SN:" + sn);
	}
	
//	@Test
	public void testGenerateSerialNumberPSZC/**派送装车任务*/(){
		String sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.PSZC);
		logger.debug("派送装车SN:" + sn);
	}
	
//	@Test
	public void testGenerateSerialNumberZC/**装车任务*/(){
		String sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.ZC, "GS00002");
		logger.debug("装车SN:" + sn);
	}
	
//	@Test
	public void testShowSerialNumberDC/**订舱编号*/(){
		String sn = tfrCommonService.showSerialNumber(TfrSerialNumberRuleEnum.DC);
		sn = tfrCommonService.showSerialNumber(TfrSerialNumberRuleEnum.DC);
		sn = tfrCommonService.showSerialNumber(TfrSerialNumberRuleEnum.DC);
		sn = tfrCommonService.showSerialNumber(TfrSerialNumberRuleEnum.DC);
		sn = tfrCommonService.showSerialNumber(TfrSerialNumberRuleEnum.DC);
		String dateFormat = DateUtils.convert(Calendar.getInstance().getTime(), "yyyyMMdd");
		String expectSn = dateFormat + "-000001";
		Assert.assertEquals(sn, expectSn);
	}
	
//	@Test
	public void testGenerateSerialNumberZDJJD/**正单交接单编号*/(){
		String sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.ZDJJD);
		sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.ZDJJD);
		sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.ZDJJD);
		sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.ZDJJD);
		sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.ZDJJD);
		String expectSn = "00000005";
		Assert.assertEquals(sn, expectSn);
	}
	
//	@Test
	public void testGenerateSerialNumberZZ/**中转编号*/(){
		String sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.ZZ);
		sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.ZZ);
		sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.ZZ);
		sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.ZZ);
		sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.ZZ);
		String expectSn = "zz0000005";
		Assert.assertEquals(sn, expectSn);
	}
	
//	@Test
	public void testGenerateSerialNumberYC/**约车编号*/(){
		String sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.YC);
		sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.YC);
		sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.YC);
		sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.YC);
		sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.YC);
		
		String expectSn = "Y5";
		Assert.assertEquals(sn, expectSn);
	}
	
//	@Test
	public void testGenerateSerialNumberWBQ/**无标签运单号*/(){
		String sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.WBQ);
		sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.WBQ);
		sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.WBQ);
		sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.WBQ);
		sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.WBQ);
		
		String expectSn = "w00000005";
		Assert.assertEquals(sn, expectSn);
	}
	
//	@Test
	public void testGenerateSerialNumberPZCC/**配载车次号*/(){
		String sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.PZCC, "W040002060401", "W060002070701", "20121204", "N");
		logger.info(sn);
	}
	
//	@Test
	public void testGenerateSerialNumberJJD/**交接单*/(){
		String snShow = tfrCommonService.showSerialNumber(TfrSerialNumberRuleEnum.JJD);
		String sn = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.JJD);
		Assert.assertEquals(snShow, sn);
	}
	
//	@Test
	public void testQueryLastExecutedJobInfo(){
		TfrJobProcessEntity jobProcessEntity = tfrCommonService.queryLastExecutedJobInfo(TfrJobBusinessTypeEnum.ST_REPORT, DateUtils.convert("2012-11-30 12:00:00", "yyyy-MM-dd HH:mm:ss"), 0, 3);
		
	}
	
}