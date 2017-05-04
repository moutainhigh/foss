/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * PdaDto
 * @author foss-meiying
 * @date 2013-1-12 上午10:09:57
 * @since
 * @version
 */
public class PdaDto implements Serializable  {
	//序列
	private static final long serialVersionUID = -6522770559854851487L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 新的运单号
	 */
	private String newWaybillNo;
	/**
	 * 流水号集合
	 */
	private List<String> serialNos;
	/**
	 * 件数
	 */
	private Integer goodsQty;
	/**
	 * 交接明细id
	 */
	private String stayHandoverDetailId;
	
	/**
	 * Gets the 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	
	/**
	 * Sets the 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	/**
	 * Gets the 流水号集合.
	 *
	 * @return the 流水号集合
	 */
	public List<String> getSerialNos() {
		return serialNos;
	}
	
	/**
	 * Sets the 流水号集合.
	 *
	 * @param serialNos the new 流水号集合
	 */
	public void setSerialNos(List<String> serialNos) {
		this.serialNos = serialNos;
	}
	
	/**
	 * Gets the 件数.
	 *
	 * @return the 件数
	 */
	public Integer getGoodsQty() {
		return goodsQty;
	}
	
	/**
	 * Sets the 件数.
	 *
	 * @param goodsQty the new 件数
	 */
	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}

	/**
	 * Gets the 交接明细id.
	 *
	 * @return the 交接明细id
	 */
	public String getStayHandoverDetailId() {
		return stayHandoverDetailId;
	}

	/**
	 * Sets the 交接明细id.
	 *
	 * @param stayHandoverDetailId the new 交接明细id
	 */
	public void setStayHandoverDetailId(String stayHandoverDetailId) {
		this.stayHandoverDetailId = stayHandoverDetailId;
	}

	/**
	 * Gets the 新的运单号.
	 *
	 * @return the 新的运单号
	 */
	public String getNewWaybillNo() {
		return newWaybillNo;
	}

	/**
	 * Sets the 新的运单号.
	 *
	 * @param newWaybillNo the new 新的运单号
	 */
	public void setNewWaybillNo(String newWaybillNo) {
		this.newWaybillNo = newWaybillNo;
	}
	
	
}