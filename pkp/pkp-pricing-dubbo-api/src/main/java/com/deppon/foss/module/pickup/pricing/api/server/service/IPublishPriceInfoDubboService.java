package com.deppon.foss.module.pickup.pricing.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.request.QueryPublishPriceDubboRequest;
import com.deppon.foss.module.pickup.waybill.shared.response.QueryPublishPriceDubboResponse;

/**
 * 查询公布价
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:316759,date:2017-2-23 下午4:42:54,content:查询公布价 </p>
 * @author 316759 
 * @date 2017-2-23 下午4:42:54
 * @since
 * @version
 */
public interface IPublishPriceInfoDubboService {
	
	/**
	 * 
	 * <p>查询公布价</p> 
	 * @author 316759 
	 * @date 2017-2-23 下午5:03:18
	 * @param request
	 * @return
	 * @see
	 */
	public QueryPublishPriceDubboResponse queryPublishPrice(QueryPublishPriceDubboRequest request);

}
