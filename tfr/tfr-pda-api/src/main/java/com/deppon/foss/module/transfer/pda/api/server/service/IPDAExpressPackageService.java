/*
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.server.service
 * FILE    NAME: ExpressPackageService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressPackageDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressPackageDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressPackageScanDetailDto;

/**
 * 快递包Service
 * @author dp-duyi
 * @date 2013-7-22 上午8:58:43
 */
public interface IPDAExpressPackageService {
	/**查询未完成包任务*/
	public List<ExpressPackageDto> queryUnFinishedPackage(String userCode,Date queryStartTime,Date queryEndTime);
	/**创建包任务*/
	public String createPackage(String packageNo,String origOrgCode,String arriveOrgCode,String userCode,String deviceNo,Date createTime,String expressPackageType);
	/**刷新包任务*/
	public List<ExpressPackageDetailDto> refrushPackageDetail(String packageNo);
	/**多货校验*/
	public ExpressPackageDetailDto moreGoodsVerify(String packageNo,String wayBillNo,String serialNo);
	/**扫描*/
	public void scan(ExpressPackageScanDetailDto scanDto);
	/**取消包任务*/
	public void cancelPackage(String packageNo,String deviceNo,String operatorCode,Date cancelTime);
	/**提交包任务*/
	public void submitPackage(String packageNo,String deviceNo,String operatorCode,Date cancelTime);

	/**---------直达包---------------*/
	/**根据运单号下拉所有的到达部门，返回当前部门以后的所有部门给PDA并按路由号排序*/
	public List<String> obtainThroughPackArriveOrgCode(String packageNo,String waybillNo,String serialNo,String currentOrgCode,String packageType);
	
	/**根据包号下拉直达包明细*/
	public List<ExpressPackageDetailDto> refrushThroughPackageDetail(String packageNo);
		
	/**
	 * com.deppon.foss.module.transfer.pda.api.server.service
	 * desc: 快递空运刷新包明细
	 * param:packageNo
	 * List<ExpressPackageDetailDto>
	 * wqh
	 * 2015年8月11日上午10:54:29
	 */
	public List<ExpressPackageDetailDto> refrushAirPackageDetail(String packageNo);
	
	/**
	 * 建空运直达包刷新包明细
	 * com.deppon.foss.module.transfer.pda.api.server.service
	 * @param packageNo
	 * rc
	 * 2016年1月23日
	 * @return 包明细ExpressPackageDetailDto
	 */
	public List<ExpressPackageDetailDto> resreshAirThroughPackageDetail(String packageNo);
	
	
	
}
