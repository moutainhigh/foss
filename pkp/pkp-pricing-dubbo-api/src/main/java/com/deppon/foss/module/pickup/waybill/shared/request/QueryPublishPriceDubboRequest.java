package com.deppon.foss.module.pickup.waybill.shared.request;

import java.io.Serializable;

/**
 * 
 * 查询公布价传入参数
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:316759,date:2017-2-23 下午5:19:44,content:查询公布价传入参数 </p>
 * @author 316759 
 * @date 2017-2-23 下午5:19:44
 * @since
 * @version
 */
public class QueryPublishPriceDubboRequest implements Serializable {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String departureCity;

    private String destinationCity;

	public String getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

}
