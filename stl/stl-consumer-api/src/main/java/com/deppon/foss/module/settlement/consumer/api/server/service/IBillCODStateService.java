package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.CODVo;

/**
 * 
 * 冻结代收货款服务
 * 
 * @author dp-zengbinwen
 * @date 2012-10-17 下午6:40:24
 */
public interface IBillCODStateService extends IService {

	
	/**
	 * 符合条件的全部代收货款资金部冻结
	 * @author foss-guxinhua
	 * @date 2013-5-7 下午1:40:41
	 * @param endSignDate
	 * @param codTypes
	 * @param banks
	 * @param publicPrivateFlag
	 * @return
	 * @throws SettlementException
	 */
	CODVo fundFreezeCODAll(Date endSignDate,
			List<String> codTypes, List<String> banks, String publicPrivateFlag, CurrentInfo currentInfo);
	
	/**
	 * 
	 * 资金部冻结代收货款
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-17 下午6:42:15
	 * @return 存在更改单，冻结失败的运单号List<String>,冻结成功的List<String>
	 */
	CODVo fundFreezeCOD(List<String> entityIds, CurrentInfo currentInfo)
			throws SettlementException;

	/**
	 * 
	 * 释放冻结代收货款
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-17 下午6:42:47
	 */
	void fundReleaseCOD(List<String> entityIds, CurrentInfo currentInfo)
			throws SettlementException;
	
	/**
	 * 符合条件的全部代收货款释放冻结.
	 * @author foss-guxinhua
	 * @date 2013-5-10 上午9:39:28
	 * @param endSignDate
	 * @param codTypes
	 * @param banks
	 * @param publicPrivateFlag
	 * @param currentInfo
	 * @throws SettlementException
	 */
	void fundReleaseCODAll(Date endSignDate,
			List<String> codTypes, List<String> banks, String publicPrivateFlag, CurrentInfo currentInfo)
			throws SettlementException;

}
