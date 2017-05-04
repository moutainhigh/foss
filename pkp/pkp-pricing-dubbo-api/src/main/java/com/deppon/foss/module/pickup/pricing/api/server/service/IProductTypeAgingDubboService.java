package com.deppon.foss.module.pickup.pricing.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.request.GetLoadLineDubboRequest;
import com.deppon.foss.module.pickup.waybill.shared.response.GetGoodsTypeDubboResponse;
import com.deppon.foss.module.pickup.waybill.shared.response.GetLoadLineDubboResponse;

/**
 * 类型时效接口
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Foss-316759-wangruipeng,date:2017-3-29 下午9:12:57,content:类型时效接口 </p>
 * @author Foss-316759-wangruipeng 
 * @date 2017-3-29 下午9:12:57
 * @since
 * @version
 */
public interface IProductTypeAgingDubboService {
	
	/**
	 * 获取货物类型
	 * <p>获取货物类型</p> 
	 * @author Foss-316759-wangruipeng 
	 * @date 2017-3-29 下午9:30:36
	 * @return 货物类型列表
	 * @see
	 */
	public GetGoodsTypeDubboResponse getGoodsType();

	/**
	 * 获取预配路线以及预计出发到达时间
	 * <p>获取预配路线以及预计出发到达时间</p> 
	 * @author Foss-316759-wangruipeng 
	 * @date 2017-3-30 上午11:01:43
	 * @param bean 产品时效查询参数
	 * @return 是否存在产品时效
	 * @see
	 */
	public GetLoadLineDubboResponse getLoadLine(GetLoadLineDubboRequest getLoadLineDubboRequest);

}
