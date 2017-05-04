package com.deppon.foss.module.pickup.pricing.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.WaybillRegionInfoDto;

/**
 * 
 * FOSS同步到PTP的接口
 * 
 * @author 265475	 DP-Foss-zoushengli
 * 
 * @date 2016-1-18 下午11:35:08
 * 
 */
public interface IWaybillRegionImpPushService  extends IService {
	/**
	 * FOSS向DTD推送价格出发区域
	 * @param requestDto
	 * @return
	 */
	public String pushWaybillHomeInfo(WaybillRegionInfoDto requestDto);
	
	/**
	 * FOSS向DTD推送价格到达区域
	 * @param requestDto
	 * @return
	 */
	public String pushArriveWaybillHomeInfo(WaybillRegionInfoDto requestDto);
	
	/**
	 * FOSS向DTD推送产品定义
	 * @param requestDto
	 * @return
	 */
	public String pushProductWaybillHomeInfo(WaybillRegionInfoDto requestDto,String url);
}
