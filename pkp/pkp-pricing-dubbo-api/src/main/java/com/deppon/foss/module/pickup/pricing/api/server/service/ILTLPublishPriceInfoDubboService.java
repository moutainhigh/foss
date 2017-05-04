package com.deppon.foss.module.pickup.pricing.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.request.QueryPublishPriceDubboRequest;
import com.deppon.foss.module.pickup.waybill.shared.response.QueryPublishPriceDubboResponse;

/**
 * 查询公布价
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:316759,date:2017-3-1 下午3:52:40,content:提供给OMS调用 </p>
 * @author 316759 
 * @date 2017-3-1 下午3:52:40
 * @since
 * @version
 */
public interface ILTLPublishPriceInfoDubboService {
	
	/**
	 * <p>查询公布价，提供给OMS调用</p> 
	 * @author 316759 
	 * @date 2017-3-1 下午3:53:11
	 * @param request
	 * @return
	 * @see
	 */
	public QueryPublishPriceDubboResponse queryPublishPrice(QueryPublishPriceDubboRequest request);

}
