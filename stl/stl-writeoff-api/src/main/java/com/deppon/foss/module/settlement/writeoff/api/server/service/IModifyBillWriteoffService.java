/**
 * Copyright 2013 STL TEAM
 */
/*
 * PROJECT NAME: stl-writeoff-api
 * PACKAGE NAME: com.deppon.foss.module.settlement.writeoff.api.server.service
 * FILE    NAME: IWriteOffAndReverseWaybillChange.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.settlement.writeoff.api.server.service;

import java.io.ByteArrayInputStream;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillWriteoffDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillWriteoffResultDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.ModifyBillPrintInfoDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.WaybillChangeRfcPrintDto;

/**
 * 查询 核销_反核销_更改单
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-30 下午5:09:06
 */
public interface IModifyBillWriteoffService extends IService {

	/**
	 * 查询待审核的财务类更改单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-10-30 下午5:15:06
	 * @param modifyBillWriteoffDto,limit,start
	 * 			cInfo
	 * 			更改单参数Dto,条数,序号
	 *  		当前登录用户
	 * @return BillWriteoffResultDto
	 * 			更改单返回dto
	 */
	 BillWriteoffResultDto queryModifyBill(
			BillWriteoffDto modifyBillWriteoffDto,int limit,int start,CurrentInfo currentInfo);

	/**
	 * 核销更改单 核销通过、核销不通过
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-10-30 下午5:15:11
	 * @param modifyBillWriteoffDto
	 * 			cInfo
	 * 			更改单参数Dto
	 *  		当前登录用户
	 * @return BillWriteoffResultDto
	 * 			更改单返回dto
	 */
	 BillWriteoffResultDto writeoffModifyBill(
			BillWriteoffDto modifyBillWriteoffDto,CurrentInfo cInfo);

	/**
	 * 
	 * 反核销更改单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-1 上午8:27:53
	 * @param modifyBillWriteoffDto
	 * 			cInfo
	 * 			更改单参数Dto
	 *  		当前登录用户
	 * @return BillWriteoffResultDto
	 * 			更改单返回dto
	 */
	 BillWriteoffResultDto reverseModifyBill(
			BillWriteoffDto modifyBillWriteoffDto,CurrentInfo cInfo);
	 
	 

		
	
	/**
	 * 根据更改单id，查询更改单，并打印
	 * @author 095793-foss-LiQin
	 * @date 2013-4-17 下午1:42:06
	 * @return
	 */
	WaybillChangeRfcPrintDto queryWaybillRfcPrintDtoByRfcid(String waybillChangeId);
	
	
    /**
     * 
     * <p>更新打印次数记录</p> 
     * @author foss-李琴
     * @date 2013-04-24 下午4:54:16
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKeySelective(String waybillChangeId,ModifyBillPrintInfoDto dto);
    
    
    
    /**
     * 查询更改单打印次数
     * @author 095793-foss-LiQin
     * @date 2013-4-25 下午5:17:01
     * @param waybillRFCId
     * @param waybillNo
     * @return
     */
    int queryPrintTimesByWaybillRFCId(String waybillRFCId,String waybillNo);
    
    /**
     * 分批导出更改单
     * @author 045738-foss-maojianqiang
     * @date 2013-4-25 下午5:17:01
     * @param waybillRFCId
     * @param waybillNo
     * @return
     */
    ByteArrayInputStream exportBillForBatch(final BillWriteoffDto modifyDto,CurrentInfo currentInfo,final String conpath);
}
