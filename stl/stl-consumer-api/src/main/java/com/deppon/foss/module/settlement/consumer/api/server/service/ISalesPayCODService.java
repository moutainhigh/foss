package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.CODVo;

/**
 * 营业部代收货款客户账号状态控制
 * 
 * @author dp-zengbinwen
 * @date 2012-10-15 上午11:25:43
 */
public interface ISalesPayCODService extends IService {

	/**
	 * 
	 * 出发申请查询代收货款【应付部门、代收货款状态、分页号都不允许为空】
	 * 
	 * @author dp-zengbinwen
	 * @param salesPayCODVO 
	 * @date 2012-10-29 下午4:38:42
	 */
	List<CODDto> queryStartApplyBillCOD(CurrentInfo currentInfo,
			List<String> statuses, int offset, int limit)
			throws SettlementException;
	/**
	 * @author 218392 zhangyongxue 2015-11-09 16:09:00
	 * 代收货款账号管理查询优化需求
	 */
	List<CODDto> queryStartApplyBillCODZyx(CurrentInfo currentInfo,
			List<String> statuses, int offset, int limit ,CODVo salesPayCODVO)
			throws SettlementException;

	/**
	 * 
	 * 出发申请查询代收货款大小【应付部门、代收货款状态都不允许为空】
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-29 下午4:38:42
	 */
	int queryStartApplyBillCODSize(CurrentInfo currentInfo, List<String> statuses)
			throws SettlementException;
	
	/**
	 * @author 218392 zhangyongxue
	 * @param salesPayCODVO 
	 * @date 2015-11-09 下午4:38:42
	 */
	int queryStartApplyBillCODSizeZyx(CurrentInfo currentInfo, List<String> statuses, CODVo salesPayCODVO)
			throws SettlementException;


	/**
	 * 
	 * 营业部冻结代收货款
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-29 上午10:17:54
	 */
	void freezeBillPayCOD(String[] entityIds, CurrentInfo currentInfo)
			throws SettlementException;

	/**
	 * 
	 * 获取客户的银行账号
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-29 上午11:54:35
	 */
	List<CusAccountEntity> getBillPayCODBankAccounts(String customerCode)
			throws SettlementException;

	/**
	 * 
	 * 修改代收货款账号
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-29 下午2:00:10
	 */
	void changeBillPayCODBankAccounts(CODEntity entity, CurrentInfo currentInfo)
			throws SettlementException;

	/**
	 * 获得客户存在应收账款的代收款客户信息
	 * @author guxinhua
	 * @date 2013-4-9 下午5:53:15
	 * @param codDtoList
	 * @return
	 * @throws SettlementException
	 */
	List<CODDto> queryCODDtoCheckReceivableUnAmount(List<CODDto> codDtoList)
			throws SettlementException;	
	
	/**
	 * 
	 * 营业部审核代收货款
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-30 下午4:24:45
	 */
	void auditBillPayCOD(List<String> entityIds, CurrentInfo currentInfo)
			throws SettlementException;

	/**
	 * 
	 * 代收货款经理审核同意
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-10-30 下午5:01:45
	 */
	void agreeChangeBankAccounts(List<String> entityIds,
			CurrentInfo currentInfo, String password)
			throws SettlementException;

	/**
	 * 
	 * 代收货款经理审核拒绝
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-10-30 下午5:04:50
	 */
	void denyChangeBankAccounts(List<String> entityIds,
			CurrentInfo currentInfo, String denyDesc)
			throws SettlementException;
	
	/**
	 * 根据银行编码查询是否支持即日退
	 * 
	 * @author guxinhua
	 * @param 
	 * @date 2013-1-30 上午10:47:56
	 * @return
	 */
	boolean checkBankIntraDayTypeByBankEntity(BankEntity entity);
}
