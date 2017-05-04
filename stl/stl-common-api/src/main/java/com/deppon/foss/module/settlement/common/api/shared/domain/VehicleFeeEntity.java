package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description 提供给TPS的参数实体
 * @author 269044
 * @date 2015-10-28
 */
public class VehicleFeeEntity implements Serializable  {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1557793546280704826L;

	/**
	 * 车次号-应付单中的来源单号
	 */
	private String trainNumber ;
	
	/**
	 * 车价-来源单号对应的首款和尾款之和
	 */
	private BigDecimal carPrice;

	/**
	 * @return  trainNumber
	 */
	public String getTrainNumber() {
		return trainNumber;
	}

	/**
	 * @param  trainNumber
	 */
	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}

	/**
	 * @return  carPrice
	 */
	public BigDecimal getCarPrice() {
		return carPrice;
	}
	
	/**
	 * @param  carPrice
	 */
	public void setCarPrice(BigDecimal carPrice) {
		this.carPrice = carPrice;
	}

}


