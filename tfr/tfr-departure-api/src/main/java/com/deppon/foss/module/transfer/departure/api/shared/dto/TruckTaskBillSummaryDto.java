package com.deppon.foss.module.transfer.departure.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 车辆任务运单统计信息Dto
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:163580,date:2014-5-7 下午2:10:22 </p>
 * @author 163580
 * @date 2014-5-7 下午2:10:22
 * @since
 * @version
 */
public class TruckTaskBillSummaryDto implements Serializable {
	private static final long serialVersionUID = 2949424426164455323L;
	
	/**运输性质**/
	private String productName;
	
	/**总票数**/
	private Integer waybillQtyTotal;
	
	/**总重量**/
	private BigDecimal weightTotal;

	/**总体积**/
	private BigDecimal volumeTotal;



	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getWaybillQtyTotal() {
		return waybillQtyTotal;
	}

	public void setWaybillQtyTotal(Integer waybillQtyTotal) {
		this.waybillQtyTotal = waybillQtyTotal;
	}

	public BigDecimal getWeightTotal() {
		return weightTotal;
	}

	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}

	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}

	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}

}
