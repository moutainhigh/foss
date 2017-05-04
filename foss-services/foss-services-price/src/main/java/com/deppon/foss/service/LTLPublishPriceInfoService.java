package com.deppon.foss.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.deppon.foss.productservice.CommonException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceExpressService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PublicPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultPriceDto;
import com.deppon.foss.module.pickup.waybill.shared.request.QueryPublishPriceRequest;
import com.deppon.foss.module.pickup.waybill.shared.response.ExpressPrice;
import com.deppon.foss.module.pickup.waybill.shared.response.GeneralPrice;
import com.deppon.foss.module.pickup.waybill.shared.response.LttRate;
import com.deppon.foss.module.pickup.waybill.shared.response.ProductAging;
import com.deppon.foss.module.pickup.waybill.shared.response.PublishPriceInfo;
import com.deppon.foss.module.pickup.waybill.shared.response.QueryPublishPriceResponse;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 价格时效查询，提供给oms调用
 * @author 305082
 *
 */
@Controller
public class LTLPublishPriceInfoService {

    /**
     * 日志
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
	 * ESB_RESULT_CODE
	 */
	public static final String ESB_RESULT_CODE = "ESB-ResultCode";

    // 客户信息Service接口
	@Resource
    private IPublishPriceService publishPriceService;
    
    /** 产品 Service. */
	@Resource
	private IProductService productService;

	@Resource
	private IPublishPriceExpressService publishPriceExpressService;

	
	public void setPublishPriceService(IPublishPriceService publishPriceService) {
		this.publishPriceService = publishPriceService;
	}
	
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public void setPublishPriceExpressService(
			IPublishPriceExpressService publishPriceExpressService) {
		this.publishPriceExpressService = publishPriceExpressService;
	}



	@RequestMapping(value = "/queryPublishPrice", method = RequestMethod.POST)
	@ResponseBody
	public QueryPublishPriceResponse queryPublishPrice(@RequestBody QueryPublishPriceRequest request ,
			HttpServletResponse response) throws CommonException {
		
		response.setHeader(ESB_RESULT_CODE, String.valueOf(1));
		//出发城市
		String startCityCode = request.getDepartureCity();
		//到达城市
		String destinationCityCode = request.getDestinationCity();
		//返回参数
		QueryPublishPriceResponse publishPriceResponse = new QueryPublishPriceResponse();
		
		try {
			List<PublicPriceDto> publicPriceDtos = publishPriceService.queryPublishPriceDetailByCity(startCityCode, destinationCityCode, new Date());
			//普通公布价
			if(CollectionUtils.isNotEmpty(publicPriceDtos)) {
				for (int i = 0; i < publicPriceDtos.size(); i++) {
					PublicPriceDto dto = publicPriceDtos.get(i);
					PublishPriceInfo info = new PublishPriceInfo();
					info.setProductCode(dto.getProductCode());
					publishPriceResponse.getPublishPriceInfos().add(info);
				}
			}
			return publishPriceResponse;
		} catch (Exception e) {

			logger.info(startCityCode+":"+destinationCityCode+":"+"oms调用价格时效接口"+e.getMessage()+"栈信息"+ExceptionUtils.getFullStackTrace(e));
 
		}
		return publishPriceResponse;
		
	}
}
