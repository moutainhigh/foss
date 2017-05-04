package com.deppon.foss.module.pickup.pricing.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.request.WsGuiQueryBillCalculateDubboRequest;
import com.deppon.foss.module.pickup.waybill.shared.response.QueryGuiBillPriceDubboResponse;

/**
 * CC调用FOSS的价格计算
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:316759,date:2017-2-28 下午3:49:53,content:1.快递价格计算. 2.零担价格计算 </p>
 * @author 316759 
 * @date 2017-2-28 下午3:49:53
 * @since
 * @version
 */
public interface IBillCaculateDubboService {
	
	/**
	 * 快递价格查询
	 * @author 316759 
	 * @date 2017-2-28 下午4:18:32
	 * @param request
	 * @return
	 * @see
	 */
	public QueryGuiBillPriceDubboResponse queryGuiExpressBillPrice(WsGuiQueryBillCalculateDubboRequest request);
	
	/**
	 * 零担价格查询 
	 * @author 316759 
	 * @date 2017-2-28 下午4:19:39
	 * @param request
	 * @return
	 * @see
	 */
	public QueryGuiBillPriceDubboResponse queryGuiBillPrice(WsGuiQueryBillCalculateDubboRequest request);

}
