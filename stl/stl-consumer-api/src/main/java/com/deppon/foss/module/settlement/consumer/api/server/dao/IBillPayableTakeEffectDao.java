package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;

/**
 * 生效应付单(装卸费)
 * @author foss-zhangxiaohui
 * @date Dec 4, 2012 3:52:07 PM
 */
public interface IBillPayableTakeEffectDao {
	
	/**
	 * 生效应付单(装卸费)--查询应付单
	 * @author foss-zhangxiaohui
	 * @date Dec 4, 2012 4:09:24 PM
	 */
	List<BillPayableEntity> queryBillPayableLaborFee(String billType, Date startAccountDate,Date endAcclountDate,String effectiveStatus, String active);
}
