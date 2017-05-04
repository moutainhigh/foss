/**
 * Copyright 2013 STL TEAM
 */
/*
 * PROJECT NAME: stl-pay-api
 * PACKAGE NAME: com.deppon.foss.module.settlement.pay.api.server.service
 * FILE    NAME: IReportCashRecPayInService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionRptEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInDDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInDResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.UpdateCashIncomerptDto;

/**
 * 缴款报表服务
 * 
 * @author 095793-foss-LiQin
 * @date 2012-12-3 下午2:44:00
 */
public interface IReportCashRecPayInService extends IService {

	/**
	 * 查询现金收入明细
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-3 下午4:56:06
	 */
	BillCashRecPayInDResultDto queryReportCashRecPayInD(
			BillCashRecPayInDDto billCashRecDDto, CurrentInfo cInfo,int start,int limit);

	/**
	 * 导出现金收入（缴款）报表
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-13 上午10:31:40
	 */
	HSSFWorkbook exportCashRecPayIn(BillCashRecPayInDDto cashRecPayDIn,
			CurrentInfo cInfo);

	/**
	 * 每天定时生成所有网点的现金收入报表及明细信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-12-15 下午5:18:07
	 */
	void createAllReportCashRecPayIn(Date beginDate, Date endDate);

	/**
	 * 实时生成单个网点的现金收入报表及明细信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-12-15 下午5:18:07
	 */
	void createOneReportCashRecPayIn(Date beginDate, Date endDate,
			String orgCode, Date currentTime);

	/**
	 * 每天定时查询网点前一天的现金营业收入
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-12-18 下午4:57:54
	 */
	List<CashCollectionRptEntity> queryUploadCashAllAmount(Date beginDate,
			Date endDate);

	/**
	 * 查询部门累计未交款金额(营业款金额和预收款金额)
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-19 下午3:03:34
	 */
	BillCashRecPayInResultDto queryCashinComerptDebtAmount(CashCollectionRptEntity entity);
	
	/**
	 *  更新现金盘点缴款
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-7 下午5:22:45
	 * @param dto
	 * @return
	 */
	Map<String ,Object> updateCashIncomerptForProcessor(UpdateCashIncomerptDto dto);
	
	/**
	 * 
	 *查询流水号是否已经付过款
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-9-2 下午3:06:09 
	 * @param serialNo
	 * @return
	 */
	boolean isExistSerino(String serialNO);
}
