package com.deppon.foss.module.pickup.waybill.shared.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.PublishPriceInfoDubboDto;

/**
 * 查询公布价返回参数
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:316759,date:2017-2-23 下午5:20:00,content:查询公布价返回参数 </p>
 * @author 316759 
 * @date 2017-2-23 下午5:20:00
 * @since
 * @version
 */
public class QueryPublishPriceDubboResponse implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private List<PublishPriceInfoDubboDto> publishPriceInfos;

	public List<PublishPriceInfoDubboDto> getPublishPriceInfos() {
		if(publishPriceInfos == null){
			publishPriceInfos = new ArrayList<PublishPriceInfoDubboDto>();
		}
		return publishPriceInfos;
	}

	public void setPublishPriceInfos(List<PublishPriceInfoDubboDto> publishPriceInfos) {
		this.publishPriceInfos = publishPriceInfos;
	}

}
