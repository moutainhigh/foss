package com.deppon.foss.module.settlement.agency.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.shared.dto.SettlementLdpExternalBillDto;


/**
 * 
 * 偏线快递代理外发单录入、修改、审核、反审核、作废服务
 * @author guxinhua
 * @date 2013-7-24 下午6:09:34
 * @since
 * @version
 */
public interface IVehicleAgencyExternalLdpService {

	/**
	 * 新增外发单服务
	 * @author guxinhua
	 * @date 2013-7-24 下午6:11:34
	 * @param dto    有中转传入数据【运单号、外发单号、外发代理编码/名称、金额等信息】
	 * @param currentInfo
	 * @see
	 */
	void addExternalBill(SettlementLdpExternalBillDto dto,CurrentInfo currentInfo);
	
	/**
	 * 
	 * 修改外发单服务
	 * @author guxinhua
	 * @date 2013-7-24 下午6:17:43
	 * @param dto
	 * @param currentInfo
	 * @see
	 */
	void modifyExternalBill(SettlementLdpExternalBillDto dto,CurrentInfo currentInfo);
	
	/**
	 * 审核外发单服务
	 * @author guxinhua
	 * @date 2013-7-24 下午6:18:38
	 * @param externalBills
	 * @param currentInfo
	 * @see
	 */
	void auditExternalBill(List<SettlementLdpExternalBillDto> externalBills,CurrentInfo currentInfo);
	
	/**
	 * 作废外发单服务
	 * @author guxinhua
	 * @date 2013-7-24 下午6:19:20
	 * @param externalBills
	 * @param currentInfo
	 * @see
	 */
	void disableExternalBill(List<SettlementLdpExternalBillDto> externalBills,CurrentInfo currentInfo);
	
	/**
	 *反审核外发服务
	 * @author guxinhua
	 * @date 2013-7-24 下午6:19:54
	 * @param externalBills
	 * @param currentInfo
	 * @see
	 */
	void reverseAuditExternalBill(List<SettlementLdpExternalBillDto> externalBills,CurrentInfo currentInfo);
}
