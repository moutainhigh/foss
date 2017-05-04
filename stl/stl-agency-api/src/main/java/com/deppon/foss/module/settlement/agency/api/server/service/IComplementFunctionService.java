package com.deppon.foss.module.settlement.agency.api.server.service;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.shared.dto.SettlementComplementBillDto;

/**
 * 补码功能财务接口调用.
 *
 * @author foss-guxinhua
 * @date 2013-7-29 下午2:40:03
 */
public interface IComplementFunctionService {
	
	/**
	 * 补码功能财务接口调用：如果运单存在应收虚拟网点的到付运费或者代收货款费用，且最终网点信息为自有网点，则需要调用结算接口，
	 * 红冲虚拟网点的应收到付运费或代收货款费用，生成应收最新提货网点的到付运费或代收货款费用。.
	 *
	 * @param dto the dto
	 * @param currentInfo the current info
	 * @author foss-guxinhua
	 * @date 2013-7-29 下午2:40:03
	 */
	void complementFunctionWriteBackAndCreateReceivable(SettlementComplementBillDto dto,CurrentInfo currentInfo);
	
	/**
	 * 快递补码
	 * 补码功能财务接口调用：如果运单存在应收虚拟网点的到付运费或者代收货款费用，且最终网点信息为自有网点，则需要调用结算接口，
	 * 红冲虚拟网点的应收到付运费或代收货款费用，生成应收最新提货网点的到付运费或代收货款费用。
	 * @author foss-231434-bieyexiong
	 * @date 2016-6-4 下午2:48
	 */
	void ecsComplementFunctionWriteBackAndCreateReceivable(SettlementComplementBillDto dto,CurrentInfo currentInfo);
	
	/**
	 * 校验是否可以补码
	 * 1、存在多条相同类型应收、应付单，不允许补码
	 * 2、应收、应付已核销金额大于0，不允许补码（始发应收除外）
	 * 3、应收、应付已确认对账单不允许补码（始发应收除外）
	 * 4、应付单已审核，不允许补码
	 * 5、应收单已锁定，不允许补码
	 * 6、现金收款单记账日期超过30天，不允许补码
	 * @param foss-231434-bieyexiong
	 * @date 2016-6-01 下午3:27
	 */
	String checkComplementFunction(SettlementComplementBillDto dto);
}
