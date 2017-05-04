package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;

/**
 * 
 * 推送运单相关信息至OMS的公共方法
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:272311,date:2016-8-13 上午9:26:37,content:TODO </p>
 * @author 272311-sangwenhao
 * @date 2016-8-13 上午9:26:37
 * @since
 * @version
 */
public interface ISysWaybillInfoToOmsService {
	/**
	 * 
	 * <p>推送运单号至OMS</p> 
	 * @author 272311-sangwenhao 
	 * @date 2016-8-13 上午9:29:21
	 * @return
	 * @see
	 */
	boolean pushWaybillNoToOMS(WaybillDto waybillDto);

}
