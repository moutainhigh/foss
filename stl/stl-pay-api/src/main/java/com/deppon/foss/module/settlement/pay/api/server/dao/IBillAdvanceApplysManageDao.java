/**
 * Copyright 2013 STL TEAM
 */
/*
 * PROJECT NAME: stl-pay-api
 * PACKAGE NAME: com.deppon.foss.module.settlement.pay.api.server.dao
 * FILE    NAME: IBillAdvanceApplysManageDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceResultDto;



/**
 * 预收单管理Dao
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-26 上午9:01:18
 */
public interface IBillAdvanceApplysManageDao {
	
	/**
	 *查询预收单
	 * @author 095793-foss-LiQin
	 * @date 2012-11-2 下午4:18:48
	 */
	List<BillAdvancedPaymentEntity>queryBillAdvancePayApplys(BillAdvanceDto billAdvanceApplysManageDto,int start,int limit);

	
	/**
	 * 查询预付单总条数
	 * @author 095793-foss-LiQin
	 * @date 2012-12-4 下午1:32:10
	 */
	BillAdvanceResultDto queryCountBillAdvancePayApplys(BillAdvanceDto billAdvanceApplysManageDto);
	
	
	/**
	 * 查询导出预付单总条数
	 * @author 095793-foss-LiQin
	 * @date 2012-12-6 下午1:20:03
	 */
	List<BillAdvancedPaymentEntity>queryExportBillAdvancePayApplys(BillAdvanceDto billAdvanceDto);
}
