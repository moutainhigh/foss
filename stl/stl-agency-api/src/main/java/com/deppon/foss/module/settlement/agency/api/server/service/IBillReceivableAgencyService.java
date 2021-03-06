package com.deppon.foss.module.settlement.agency.api.server.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.shared.dto.BillReceivableAgencyDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;

/**
 * 查询_审核_作废空运其它应收Service
 * @author foss-pengzhen
 * @date 2012-10-24 下午4:16:50
 * @since
 * @version
 */
public interface IBillReceivableAgencyService extends IService{
	
	
    /**
	 * 空运其他管理分页查询
	 * @author jefferson
	 * @date 2012-11-1 下午2:05:54
	 * @param billReceivableAgencyDto
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	BillReceivableAgencyDto queryAirReceivablePage(BillReceivableAgencyDto billReceivableAgencyDto,int start, int limit,CurrentInfo currentInfo);
	
	
	 /**
		 * 根据航空正单号查询应收单信息njs
		 * @author 
		 * @date 2012-11-1 下午2:05:54
		 * @param billReceivableAgencyDto
		 * @param start
		 * @param limit
		 * @return
		 * @see
		 */
	public AirWaybillEntity queryAirReceivableCusName(String airWaybillNo,int start, int limit,CurrentInfo currentInfo);
	
	/**
	 * njs
	 */
	
	public List<AirPickupbillEntity> queryAiragencyCusName(String waybillNo, int start, int limit,CurrentInfo currentInfo);
	
	
	/**
	 * 空运其他应收单导出
	 * @author foss-pengzhen 
	 * @date 2012-12-27 上午9:46:15
	 * @param billReceivableAgencyDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	HSSFWorkbook airReceivableListExport(BillReceivableAgencyDto billReceivableAgencyDto,
			CurrentInfo currentInfo);
	
	/**
	 * 空运其他应收管理作废
	 * @author foss-pengzhen
	 * @date 2012-11-8 上午10:22:31
	 * @param billReceivableAgencyDto
	 * @return
	 * @see
	 */
	BillReceivableAgencyDto writeBackAirOtherBillReceivable (List<BillReceivableAgencyDto> billReceivableAgencyDtos,
			BillReceivableAgencyDto billReceivableAgencyDto,CurrentInfo currentInfo);
	
    /**
     * 空运应收其他管理的审核 操作
     * @author foss-pengzhen
     * @date 2012-10-29 下午2:53:07
     * @param billReceivableEntityListFrom
     * @param billReceivableEntityListAll
     * @return
     * @see
     */
	BillReceivableAgencyDto auditAirOtherBillReceivable(List<BillReceivableAgencyDto> billReceivableAgencyDtos,
    		BillReceivableAgencyDto billReceivableAgencyDto,CurrentInfo currentInfo);
    
    /**
	 * 反审核空运其他应收单
	 * @author foss-pengzhen
	 * @date 2012-11-6 上午9:49:41
	 * @param billReceivableAgencyDto
	 * @return
	 * @see
	 */
	BillReceivableAgencyDto reverseAuditAirOtherBillReceivable(List<BillReceivableAgencyDto> billReceivableAgencyDtos,
    		BillReceivableAgencyDto billReceivableAgencyDto,CurrentInfo currentInfo);
    
    /**
     * 新增空运其它应收款
     * @author foss-pengzhen
     * @date 2012-10-29 下午5:44:42
     * @see
     */
    void addBillReceivable(BillReceivableAgencyDto billReceivableAgencyDto,CurrentInfo currentInfo);
}
