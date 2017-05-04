package com.deppon.foss.module.settlement.agency.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.shared.dto.SettlementExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto;


/**
 * 
 * 偏线外发单录入、修改、审核、反审核、作废服务
 * @author 099995-foss-wujiangtao
 * @date 2012-10-23 下午6:09:34
 * @since
 * @version
 */
public interface IVehicleAgencyExternalService {

	/**
	 * 新增外发单服务
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-23 下午6:11:34
	 * @param dto    有中转传入数据【运单号、外发单号、外发代理编码/名称、金额等信息】
	 * @param currentInfo
	 * @see
	 */
	void addExternalBill(SettlementExternalBillDto dto,CurrentInfo currentInfo);
	
	/**
	 * 
	 * 修改外发单服务
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-23 下午6:17:43
	 * @param dto
	 * @param currentInfo
	 * @see
	 */
	void modifyExternalBill(SettlementExternalBillDto dto,CurrentInfo currentInfo);
	
	/**
	 * 审核外发单服务
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-23 下午6:18:38
	 * @param externalBills
	 * @param currentInfo
	 * @see
	 */
	void auditExternalBill(List<SettlementExternalBillDto> externalBills,CurrentInfo currentInfo);
	
	/**
	 * 作废外发单服务
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-23 下午6:19:20
	 * @param externalBills
	 * @param currentInfo
	 * @see
	 */
	void disableExternalBill(List<SettlementExternalBillDto> externalBills,CurrentInfo currentInfo);
	
	/**
	 *反审核外发服务
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-23 下午6:19:54
	 * @param externalBills
	 * @param currentInfo
	 * @see
	 */
	void reverseAuditExternalBill(List<SettlementExternalBillDto> externalBills,CurrentInfo currentInfo);
	
	/**
	 * <p>查询运单号对应有效偏线外发单代理编号 处理为键值对的形式</p> 
	 * @author 105762
	 * @date 2014-3-4 上午9:38:23
	 * @param waybillNos
	 * @return
	 * @see
	 */
	Map<String,List<ExternalBillDto>> getWaybillPartialAgencyCode(List<String> waybillNos);
}
