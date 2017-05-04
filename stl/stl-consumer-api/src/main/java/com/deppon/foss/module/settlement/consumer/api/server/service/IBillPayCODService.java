package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;

/**
 * 代收货款服务
 * 
 * @author dp-zengbinwen
 * @date 2012-10-12 下午5:15:10
 */
public interface IBillPayCODService extends IService {

	/**
	 * 新增代收货款
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-26 下午3:24:46
	 */
	void addBillCOD(WaybillPickupInfoDto waybill, CurrentInfo currentInfo) throws SettlementException;

	/**
	 * 
	 * 作废代收货款,运单
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-10-31 下午3:58:46
	 */
	void cancelBillCOD(CODEntity codEntity, CurrentInfo currentInfo)
			throws SettlementException;

	/**
	 * 
	 * 审核空运代收货款
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-12 下午5:34:29
	 */
	void auditAirBillCOD(List<String> entityIds, CurrentInfo currentInfo)
			throws SettlementException;

	/**
	 * 
	 * 反审核空运代收货款
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-13 下午4:58:32
	 */
	void antiAuditBillCOD(List<String> entityIds, CurrentInfo currentInfo)
			throws SettlementException;
	
	/**
	 * 
	 * 审核快递代理代收货款
	 * ISSUE-3389 小件业务
	 * @author guxinhua
	 * @date 2012-10-12 下午5:34:29
	 */
	void auditLandBillCOD(List<String> entityIds, CurrentInfo currentInfo)
			throws SettlementException;

	/**
	 * 
	 * 反审核快递代理代收货款
	 * ISSUE-3389 小件业务
	 * @author guxinhua
	 * @date 2012-11-13 下午4:58:32
	 */
	void antiAuditLandBillCOD(List<String> entityIds, CurrentInfo currentInfo)
			throws SettlementException;
}
