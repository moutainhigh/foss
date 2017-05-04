package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;

/**
 * 
 * 线下汇代收货款给客户服务
 * 
 * @author dp-zengbinwen
 * @date 2012-10-20 下午1:56:00
 */
public interface IBillPayCODOfflineService extends IService {

	/**
	 * 资金部汇款界面查询合计总条数总金额，冻结总条数总金额
	 * @author foss-guxinhua
	 * @date 2013-5-6 下午3:47:07
	 * @param endSignDate
	 * @param codTypes
	 * @param banks
	 * @param publicPrivateFlag
	 * @return
	 * @throws SettlementException
	 */
	CODDto queryBillCODPayableSum(Date endSignDate, List<String> codTypes,
			List<String> banks, String publicPrivateFlag)
			throws SettlementException;
	
	/**
	 * 
	 * 资金部汇款界面查询【代收货款状态不能为空，截止签收日期、银行、对公对私标志可为空】
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-29 下午4:46:21
	 */
	List<CODDto> queryBillCODPayable(Date endSignDate, List<String> codTypes,
			List<String> banks, String publicPrivateFlag, int offset, int limit,String sortProperty,String sortDirection)
			throws SettlementException;
	
	/**
	 * 批量处理导出代收货款数据.
	 * @author foss-guxinhua
	 * @date 2013-5-8 下午6:15:10
	 * @param entityIds
	 * @param currentInfo
	 * @return CODDto(包括批次号,codPayableDto集合)
	 * @throws SettlementException
	 */
	CODDto doWithExportBillCODAll(Date endSignDate,
			List<String> codTypes, List<String> banks,
			String publicPrivateFlag, CurrentInfo currentInfo)
			throws SettlementException;

	/**
	 * 处理导出代收货款数据
	 * @author foss-guxinhua
	 * @date 2013-5-9 上午10:26:56
	 * @param entityIds
	 * @param currentInfo
	 * @return CODDto(包括批次号,codPayableDto集合)
	 * @throws SettlementException
	 */
	CODDto doWithExportBillCOD(List<String> entityIds, CurrentInfo currentInfo) throws SettlementException;
}
