/**
 * 
 */
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @author niuly
 *
 */
public class MakeUpWaybillEntity extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 2649176027498993342L;
	
	//运单号
	private String waybillNo;
	//重量
	private BigDecimal weight;
	//体积
	private BigDecimal volume;
	//件数
	private BigDecimal quantity;
	//补录时间
	private Date makeUpTime;
	//是否已处理
//	private String isDone;
	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * @return the weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	/**
	 * @return the volume
	 */
	public BigDecimal getVolume() {
		return volume;
	}
	/**
	 * @param volume the volume to set
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	
	/**
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the makeUpTime
	 */
	public Date getMakeUpTime() {
		return makeUpTime;
	}
	/**
	 * @param makeUpTime the makeUpTime to set
	 */
	public void setMakeUpTime(Date makeUpTime) {
		this.makeUpTime = makeUpTime;
	}
	/**
	 * @return the isDone
	 */
//	public String getIsDone() {
//		return isDone;
//	}
//	/**
//	 * @param isDone the isDone to set
//	 */
//	public void setIsDone(String isDone) {
//		this.isDone = isDone;
//	}
	
}
