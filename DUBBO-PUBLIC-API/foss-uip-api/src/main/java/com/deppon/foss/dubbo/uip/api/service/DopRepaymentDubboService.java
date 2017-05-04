package com.deppon.foss.dubbo.uip.api.service;

import java.util.List;

import com.deppon.foss.dubbo.uip.api.define.ReceivableEntity;
/**
 * 根据传入的一或多个运单单号，获取一或多条应收单
 * @author 327090
 * @date 2016-12-1
 */
public interface DopRepaymentDubboService {
	/**
	 * @author 327090
	 * @param waybillNOs
	 * @return
	 */
	public List<ReceivableEntity> queryReceivableBill(List<ReceivableEntity> receivableList);

}
