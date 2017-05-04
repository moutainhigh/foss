package com.deppon.foss.module.pickup.waybill.api.server.dao;

import com.deppon.foss.module.pickup.waybill.shared.domain.EWaybillMessageEntity;

public interface IEWaybillMessageDao {
	
	/**
	 * @description 根据异常编码获取电子运单失败对应中文信息
	 * @param entity
	 * @return
	 */
	public EWaybillMessageEntity getEWaybillMessageByFailCode(String failCode);
	
	/**
	 * @description 获取运单校验转译信息
	 * @param failCode
	 * @return
	 */
	public String getWVMessageByFailCode(String failCode);

}
