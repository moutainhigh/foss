package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.pickup.pricing.api.server.service.ILTLPublishPriceInfoDubboService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceService;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PublicPriceDto;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.PublishPriceInfoDubboDto;
import com.deppon.foss.module.pickup.waybill.shared.request.QueryPublishPriceDubboRequest;
import com.deppon.foss.module.pickup.waybill.shared.response.QueryPublishPriceDubboResponse;
import com.deppon.foss.util.CollectionUtils;

/**
 * 查询公布价
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:316759,date:2017-3-1 下午3:56:03,content:提供给OMS调用</p>
 * 
 * @author 316759
 * @date 2017-3-1 下午3:56:03
 * @since
 * @version
 */
public class LTLPublishPriceInfoDubboService implements ILTLPublishPriceInfoDubboService {

	/**
	 * Logger
	 */
	protected final Logger logger = LoggerFactory.getLogger(LTLPublishPriceInfoDubboService.class);

	/** 客户信息Service接口 */
	@Autowired
	private IPublishPriceService publishPriceService;

	/**
	 * <p>查询公布价，提供给OMS调用</p>
	 * 
	 * @author 316759
	 * @date 2017-3-1 下午3:56:29
	 * @param request
	 * @return
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.ILTLPublishPriceInfoDubboService#queryPublishPrice(com.deppon.foss.module.pickup.waybill.shared.request.QueryPublishPriceDubboRequest)
	 */
	@Override
	public QueryPublishPriceDubboResponse queryPublishPrice(QueryPublishPriceDubboRequest request) {
		// 返回参数
		QueryPublishPriceDubboResponse response = new QueryPublishPriceDubboResponse();
		// 出发城市
		String startCityCode = request.getDepartureCity();
		// 到达城市
		String destinationCityCode = request.getDestinationCity();

		try {
			//判断出发城市编码和到达城市编码是否为省，如果为省，就返回null
			if(publishPriceService.queryPublishPriceDetailisProvince(startCityCode, destinationCityCode)){
				return null;
			}
			List<PublicPriceDto> publicPriceDtos = publishPriceService.queryPublishPriceDetailByCity(startCityCode, destinationCityCode, new Date());
			// 普通公布价
			if (CollectionUtils.isNotEmpty(publicPriceDtos)) {
				for (int i = 0; i < publicPriceDtos.size(); i++) {
					PublicPriceDto dto = publicPriceDtos.get(i);
					PublishPriceInfoDubboDto info = new PublishPriceInfoDubboDto();
					info.setProductCode(dto.getProductCode());
					response.getPublishPriceInfos().add(info);
				}
			}
			return response;
		} catch (Exception e) {

			logger.info(startCityCode + ":" + destinationCityCode + ":" + "oms调用价格时效接口" + e.getMessage() + "栈信息" + ExceptionUtils.getFullStackTrace(e));

		}
		return response;
	}

	public IPublishPriceService getPublishPriceService() {
		return publishPriceService;
	}

	public void setPublishPriceService(IPublishPriceService publishPriceService) {
		this.publishPriceService = publishPriceService;
	}

}
