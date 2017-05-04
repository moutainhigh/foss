package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.domain.GrandGoodAbnormalEntity;

public interface IGrandGoodAbnormalService {
	
	/**
	 * 重大异常货物处理
	 * @param jsonObj
	 * @return
	 */
	public String grandGoodAbnormalHandle(String grandGoodAbnormalJson);
	
	/**
	 * 查询重大货物异常
	 * @param waybillNo
	 * @return
	 */
	public GrandGoodAbnormalEntity queryGrandGoodAbnormal(String waybillNo );

	/**
	 * 更新重大货物异常
	 * @param grangGood
	 */
	public void updateGrandGoodAbnormal(GrandGoodAbnormalEntity grangGood);
	
	/**
	 * 新增重大货物异常信息
	 * @param grangGood
	 * @author Foss-PanGuoYang
	 */
	void insertGrandGoodAbnormal(GrandGoodAbnormalEntity grangGood);
}
