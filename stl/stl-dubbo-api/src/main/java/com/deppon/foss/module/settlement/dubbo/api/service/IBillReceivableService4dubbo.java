package com.deppon.foss.module.settlement.dubbo.api.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.dubbo.api.define.BillReceivableEntity;

/**
 * 应收单服务
 * 
 * @author 099995-foss-wujiangtao
 * 
 */
public interface IBillReceivableService4dubbo extends IService {
	/**
	 * 修改应收单扣款状态
	 *
	 * @author 099995-foss-hemingyu
	 * @date 2016-01-08 上午10:59:38
	 * @param entity
	 *
	 */
	void updateBillReceivableWithholdStatus(BillReceivableEntity entity);

	/**
	 * @author 099995-foss-hemingyu
	 * @date 2016-01-14 上午15:47:38
	 * @param wayBillNo
	 *            运单号
	 * @param billType
	 *            应收类型
	 */
	BillReceivableEntity selectByWayBillNoAndBillType(String wayBillNo, String billType);
}
