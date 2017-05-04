package com.deppon.foss.dubbo.ptp.api.service;

import com.deppon.foss.dubbo.ptp.api.define.UpdateWithholdStatusEntity;

public interface UpdateWithholdStatusService {

	/**
	 * 修改应收单扣款状态。调用代码请手动捕获SettlementException异常并获取异常的errCode字段
	 *
	 * @param entity
	 *            应收单实体，需要传入三个字段：WayBillNo、BillType、WithholdStatus
	 * @author 335284
	 * @since 2016.11.17
	 * @return 结果，请判断isSuccess字段
	 */
	UpdateWithholdStatusEntity updateWithholdStatus(UpdateWithholdStatusEntity entity);
}