package com.deppon.foss.module.transfer.management.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;


/**
* @description 对账单汇总的 总和
* @version 1.0
* @author 14022-foss-songjie
* @update 2013年11月26日 下午1:49:13
*/
public class BillListCheckStaDto implements Serializable {

	
	/**
	* @fields serialVersionUID
	* @author 14022-foss-songjie 
	* @update 2013年11月26日 上午11:16:34
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	* @fields fuelFeeTotal
	* @author 14022-foss-songjie 
	* @update 2013年11月26日 上午11:23:45
	* @version V1.0
	*/
	private BigDecimal fuelFeeTotal;
	
	/**
	* @fields fuelFeeSellTotal
	* @author 14022-foss-songjie 
	* @update 2013年11月26日 上午11:23:48
	* @version V1.0
	*/
	private BigDecimal fuelFeeSellTotal;
	
	/**
	* @fields fuelFeeSellAfterTotal
	* @author 14022-foss-songjie 
	* @update 2013年11月26日 上午11:23:50
	* @version V1.0
	*/
	private BigDecimal fuelFeeSellAfterTotal;
	
	/**
	* @fields roadTollFeeTotal
	* @author 14022-foss-songjie 
	* @update 2013年11月26日 上午11:23:52
	* @version V1.0
	*/
	private BigDecimal roadTollFeeTotal;
	
	/**
	* @fields roadTollFeeSellTotal
	* @author 14022-foss-songjie 
	* @update 2013年11月26日 上午11:23:55
	* @version V1.0
	*/
	private BigDecimal roadTollFeeSellTotal;
	
	/**
	* @fields roadTollFeeSellAfterTotal
	* @author 14022-foss-songjie 
	* @update 2013年11月26日 上午11:23:58
	* @version V1.0
	*/
	private BigDecimal roadTollFeeSellAfterTotal;
	
	
	/**
	* @description 获取燃油费总和 
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 上午11:24:46
	*/
	public BigDecimal getFuelFeeTotal() {
		return fuelFeeTotal;
	}

	
	/**
	* @description 设置燃油费总和
	* @param fuelFeeTotal
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 上午11:24:50
	*/
	public void setFuelFeeTotal(BigDecimal fuelFeeTotal) {
		this.fuelFeeTotal = fuelFeeTotal;
	}

	
	/**
	* @description 获取燃油费优惠金额总和
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 上午11:25:33
	*/
	public BigDecimal getFuelFeeSellTotal() {
		return fuelFeeSellTotal;
	}

	
	/**
	* @description 设置燃油费优惠金额总和
	* @param fuelFeeSellTotal
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 上午11:25:36
	*/
	public void setFuelFeeSellTotal(BigDecimal fuelFeeSellTotal) {
		this.fuelFeeSellTotal = fuelFeeSellTotal;
	}

	
	/**
	* @description 获取燃油费优惠后金额总和 
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 上午11:26:10
	*/
	public BigDecimal getFuelFeeSellAfterTotal() {
		return fuelFeeSellAfterTotal;
	}

	
	/**
	* @description 设置燃油费优惠后金额总和 
	* @param fuelFeeSellAfterTotal
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 上午11:26:13
	*/
	public void setFuelFeeSellAfterTotal(BigDecimal fuelFeeSellAfterTotal) {
		this.fuelFeeSellAfterTotal = fuelFeeSellAfterTotal;
	}

	
	/**
	* @description 获取路桥费总和
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 上午11:26:52
	*/
	public BigDecimal getRoadTollFeeTotal() {
		return roadTollFeeTotal;
	}

	
	/**
	* @description 设置路桥费总和
	* @param roadTollFeeTotal
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 上午11:26:55
	*/
	public void setRoadTollFeeTotal(BigDecimal roadTollFeeTotal) {
		this.roadTollFeeTotal = roadTollFeeTotal;
	}

	
	/**
	* @description 获取路桥费优惠金额总和
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 上午11:27:21
	*/
	public BigDecimal getRoadTollFeeSellTotal() {
		return roadTollFeeSellTotal;
	}

	
	/**
	* @description 设置路桥费优惠金额总和
	* @param roadTollFeeSellTotal
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 上午11:27:46
	*/
	public void setRoadTollFeeSellTotal(BigDecimal roadTollFeeSellTotal) {
		this.roadTollFeeSellTotal = roadTollFeeSellTotal;
	}

	
	/**
	* @description 获取路桥费优惠后总和
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 上午11:28:02
	*/
	public BigDecimal getRoadTollFeeSellAfterTotal() {
		return roadTollFeeSellAfterTotal;
	}

	
	/**
	* @description 设置路桥费优惠后总和
	* @param roadTollFeeSellAfterTotal
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 上午11:28:25
	*/
	public void setRoadTollFeeSellAfterTotal(BigDecimal roadTollFeeSellAfterTotal) {
		this.roadTollFeeSellAfterTotal = roadTollFeeSellAfterTotal;
	}
}
