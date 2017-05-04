package com.deppon.foss.module.settlement.dubbo.api.dao;

import java.util.List;
import com.deppon.foss.module.settlement.dubbo.api.define.BillReceivableEntity;

/**
 * 应收单Service服务 应收单新增、修改、红冲、查询、审核等公共Service
 * @author 327090
 *
 */
public interface IBillReceivablSelectEntityDao4dubbo {
	
	/**
	 * 根据传入的一到多个来源单号，获取一到多条应收单信息
	 * @param sourceBillNos
	 * @param sourceBillType
	 * @param active
	 * @return
	 */
	public List<BillReceivableEntity> queryBySourceBillNOs(
			List<String> sourceBillNos, String sourceBillType, String active);
}
