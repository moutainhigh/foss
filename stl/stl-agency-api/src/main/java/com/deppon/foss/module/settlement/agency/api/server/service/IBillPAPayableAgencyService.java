package com.deppon.foss.module.settlement.agency.api.server.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.shared.dto.BillPayableAgencyDto;

/**
 * 查询_审核_作废空运其它应收Service
 * @author foss-guxinhua
 * @date 2012-10-24 下午4:16:50
 * @since
 * @version
 */
public interface IBillPAPayableAgencyService extends IService{
	
	
    /**
	 * 空运其他管理分页查询
	 * @author foss-guxinhua
	 * @date 2012-11-1 下午2:05:54
	 * @param billPayableAgencyDto
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	BillPayableAgencyDto queryPAPayablePage(BillPayableAgencyDto billPayableAgencyDto,int start, int limit,CurrentInfo currentInfo);
	
	/**
	 * 空运其他应收单导出
	 * @author foss-guxinhua 
	 * @date 2012-12-27 上午9:46:15
	 * @param billReceivableAgencyDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	HSSFWorkbook PAPayableListExport(BillPayableAgencyDto billPayableAgencyDto,
			CurrentInfo currentInfo);
	
	/**
	 * 空运其他应付管理作废
	 * @author foss-guxinhua
	 * @date 2012-11-8 上午9:45:15
	 * @param billPayableAgencyDto
	 * @return
	 * @see
	 */
	BillPayableAgencyDto writeBackPAOtherBillPayable(List<BillPayableAgencyDto> billPayableAgencyDtos,
			BillPayableAgencyDto billPayableAgencyDto,CurrentInfo currentInfo);
	
    /**
     * 空运应收其他管理的审核 操作
     * @author foss-guxinhua
     * @date 2012-10-29 下午2:53:07
     * @param billPayableEntityListFrom
     * @param billPayableEntityListAll
     * @return
     * @see
     */
	BillPayableAgencyDto auditPAOtherBillPayable(List<BillPayableAgencyDto> billPayableAgencyDtos,
    		BillPayableAgencyDto billPayableAgencyDto,CurrentInfo currentInfo);
    
    /**
	 * 反审核空运其他应收单
	 * @author foss-guxinhua
	 * @date 2012-11-6 上午9:49:41
	 * @param billPayableAgencyDto
	 * @return
	 * @see
	 */
    BillPayableAgencyDto reverseAuditPAOtherBillPayable(List<BillPayableAgencyDto> billPayableAgencyDtos,
    		BillPayableAgencyDto billPayableAgencyDto,CurrentInfo currentInfo);
    
    /**
     * 新增空运其它应收款
     * @author foss-guxinhua
     * @date 2012-10-29 下午5:44:42
     * @see
     */
    void addBillPayable(BillPayableAgencyDto billPayableAgencyDto,CurrentInfo currentInfo);
}
