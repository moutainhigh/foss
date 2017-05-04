package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WeixinSendDto;

public interface IWeixinSendService {
	
	/**
	 * <p>需要你直接封装参数去传递</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月3日 11:18:12
	 * @param dto
	 * @param weixinType
	 * @return
	 */
	ResultDto sysnWeixinInfoForWebSiteDirectly(WeixinSendDto dto, String weixinType);
	
	/**
	 * <p>通过传递过来的参数，进行封装，然后发送过去</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月3日 11:18:12
	 * @param dto
	 * @param weixinType
	 * @return
	 */
	ResultDto sysnWeixinInfoForWebSiteUnDirectly(WeixinSendDto dto, String weixinType);
	
	ResultDto pullWeixinInfoWithWaybillInfo(WeixinSendDto dto, WaybillEntity waybillEntity, String weixinType);
}