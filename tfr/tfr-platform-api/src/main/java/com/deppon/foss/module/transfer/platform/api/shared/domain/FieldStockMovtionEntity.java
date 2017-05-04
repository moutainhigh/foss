/**
 * 
 */
package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @author 105795
 *
 */
public class FieldStockMovtionEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8246898483342378501L;

	//外场名称
	private String transferCenterName;
	
	//外场编码
	private String transferCenterCode;
    /**
     * 到达未卸
     * */
	//到达未卸票数
	private int arrivedVotes;
	//到达未卸重量
	private BigDecimal arrivedWeight;
	//到达未卸体积
	private BigDecimal arrivedVolume;

	
	/**
	 * 卸车中
	 * */
	//卸车中票数
	private int unloadingVotes;
	//卸车中重量
	private BigDecimal unloadingWeight;
	//卸车中体积
	private BigDecimal unloadingVolume;
	
	/**
	 *待叉区库存
	 * */
	//待叉区库存票数
	private int trayVotes;
	//待叉区库存重量
	private BigDecimal trayWeight;
	//待叉区库存体积
	private BigDecimal trayVolume;

	/**
	 *包装库区库存
	 * */
	//包装库区库存票数
	private int packagingVotes;

	//包装库区库存重量
	private BigDecimal packagingWeight;

	//包装库区库存体积
	private BigDecimal packagingVolume;

	/**
	 *零担中转库区库存
	 * */
	//零担中转库区库存票数
	private int stockVotes;

	//零担中转库区库存重量
	private BigDecimal stockWeight;

	//零担中转库区库存体积
	private BigDecimal stockVolume;

	/**
	 *零担派送库区库存
	 * */
	//零担派送库区库存票数
	private int deliverStockVotes;

	//零担派送库区库存重量
	private BigDecimal deliverStockWeight;

	//零担派送库区库存体积
	private BigDecimal deliverStockVolume;

	/**
	 *快递中转库区库存
	 * */
	//快递中转库区库存票数
	private int expressStockVotes;

	//快递中转库区库存重量
	private BigDecimal expressStockWeight;

	//快递中转库区库存体积
	private BigDecimal expressStockVolume;

	/**
	 *快递派送库区库存
	 * */
	//快递派送库区库存票数
	private int expressDeliverStockVotes;

	//快递派送库区库存重量
	private BigDecimal expressDeliverStockWeight;

	//快递派送库区库存体积
	private BigDecimal expressDeliverStockVolume;

	/**
	 *装车
	 * */
	//装车票数
	private int loadedVotes;

	//装车重量
	private BigDecimal loadedWeight;

	//装车体积
	private BigDecimal loadedVolume;

	/**
	 * @return the transferCenterName
	 */
	public String getTransferCenterName() {
		return transferCenterName;
	}

	/**
	 * @param transferCenterName the transferCenterName to set
	 */
	public void setTransferCenterName(String transferCenterName) {
		this.transferCenterName = transferCenterName;
	}

	/**
	 * @return the transferCenterCode
	 */
	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	/**
	 * @param transferCenterCode the transferCenterCode to set
	 */
	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	/**
	 * @return the arrivedVotes
	 */
	public int getArrivedVotes() {
		return arrivedVotes;
	}

	/**
	 * @param arrivedVotes the arrivedVotes to set
	 */
	public void setArrivedVotes(int arrivedVotes) {
		this.arrivedVotes = arrivedVotes;
	}

	/**
	 * @return the arrivedWeight
	 */
	public BigDecimal getArrivedWeight() {
		return arrivedWeight;
	}

	/**
	 * @param arrivedWeight the arrivedWeight to set
	 */
	public void setArrivedWeight(BigDecimal arrivedWeight) {
		this.arrivedWeight = arrivedWeight;
	}

	/**
	 * @return the arrivedVolume
	 */
	public BigDecimal getArrivedVolume() {
		return arrivedVolume;
	}

	/**
	 * @param arrivedVolume the arrivedVolume to set
	 */
	public void setArrivedVolume(BigDecimal arrivedVolume) {
		this.arrivedVolume = arrivedVolume;
	}

	/**
	 * @return the unloadingVotes
	 */
	public int getUnloadingVotes() {
		return unloadingVotes;
	}

	/**
	 * @param unloadingVotes the unloadingVotes to set
	 */
	public void setUnloadingVotes(int unloadingVotes) {
		this.unloadingVotes = unloadingVotes;
	}

	/**
	 * @return the unloadingWeight
	 */
	public BigDecimal getUnloadingWeight() {
		return unloadingWeight;
	}

	/**
	 * @param unloadingWeight the unloadingWeight to set
	 */
	public void setUnloadingWeight(BigDecimal unloadingWeight) {
		this.unloadingWeight = unloadingWeight;
	}

	/**
	 * @return the unloadingVolume
	 */
	public BigDecimal getUnloadingVolume() {
		return unloadingVolume;
	}

	/**
	 * @param unloadingVolume the unloadingVolume to set
	 */
	public void setUnloadingVolume(BigDecimal unloadingVolume) {
		this.unloadingVolume = unloadingVolume;
	}

	/**
	 * @return the trayVotes
	 */
	public int getTrayVotes() {
		return trayVotes;
	}

	/**
	 * @param trayVotes the trayVotes to set
	 */
	public void setTrayVotes(int trayVotes) {
		this.trayVotes = trayVotes;
	}

	/**
	 * @return the trayWeight
	 */
	public BigDecimal getTrayWeight() {
		return trayWeight;
	}

	/**
	 * @param trayWeight the trayWeight to set
	 */
	public void setTrayWeight(BigDecimal trayWeight) {
		this.trayWeight = trayWeight;
	}

	/**
	 * @return the trayVolume
	 */
	public BigDecimal getTrayVolume() {
		return trayVolume;
	}

	/**
	 * @param trayVolume the trayVolume to set
	 */
	public void setTrayVolume(BigDecimal trayVolume) {
		this.trayVolume = trayVolume;
	}

	/**
	 * @return the packagingVotes
	 */
	public int getPackagingVotes() {
		return packagingVotes;
	}

	/**
	 * @param packagingVotes the packagingVotes to set
	 */
	public void setPackagingVotes(int packagingVotes) {
		this.packagingVotes = packagingVotes;
	}

	/**
	 * @return the packagingWeight
	 */
	public BigDecimal getPackagingWeight() {
		return packagingWeight;
	}

	/**
	 * @param packagingWeight the packagingWeight to set
	 */
	public void setPackagingWeight(BigDecimal packagingWeight) {
		this.packagingWeight = packagingWeight;
	}

	/**
	 * @return the packagingVolume
	 */
	public BigDecimal getPackagingVolume() {
		return packagingVolume;
	}

	/**
	 * @param packagingVolume the packagingVolume to set
	 */
	public void setPackagingVolume(BigDecimal packagingVolume) {
		this.packagingVolume = packagingVolume;
	}

	/**
	 * @return the stockVotes
	 */
	public int getStockVotes() {
		return stockVotes;
	}

	/**
	 * @param stockVotes the stockVotes to set
	 */
	public void setStockVotes(int stockVotes) {
		this.stockVotes = stockVotes;
	}

	/**
	 * @return the stockWeight
	 */
	public BigDecimal getStockWeight() {
		return stockWeight;
	}

	/**
	 * @param stockWeight the stockWeight to set
	 */
	public void setStockWeight(BigDecimal stockWeight) {
		this.stockWeight = stockWeight;
	}

	/**
	 * @return the stockVolume
	 */
	public BigDecimal getStockVolume() {
		return stockVolume;
	}

	/**
	 * @param stockVolume the stockVolume to set
	 */
	public void setStockVolume(BigDecimal stockVolume) {
		this.stockVolume = stockVolume;
	}

	/**
	 * @return the deliverStockVotes
	 */
	public int getDeliverStockVotes() {
		return deliverStockVotes;
	}

	/**
	 * @param deliverStockVotes the deliverStockVotes to set
	 */
	public void setDeliverStockVotes(int deliverStockVotes) {
		this.deliverStockVotes = deliverStockVotes;
	}

	/**
	 * @return the deliverStockWeight
	 */
	public BigDecimal getDeliverStockWeight() {
		return deliverStockWeight;
	}

	/**
	 * @param deliverStockWeight the deliverStockWeight to set
	 */
	public void setDeliverStockWeight(BigDecimal deliverStockWeight) {
		this.deliverStockWeight = deliverStockWeight;
	}

	/**
	 * @return the deliverStockVolume
	 */
	public BigDecimal getDeliverStockVolume() {
		return deliverStockVolume;
	}

	/**
	 * @param deliverStockVolume the deliverStockVolume to set
	 */
	public void setDeliverStockVolume(BigDecimal deliverStockVolume) {
		this.deliverStockVolume = deliverStockVolume;
	}

	/**
	 * @return the expressStockVotes
	 */
	public int getExpressStockVotes() {
		return expressStockVotes;
	}

	/**
	 * @param expressStockVotes the expressStockVotes to set
	 */
	public void setExpressStockVotes(int expressStockVotes) {
		this.expressStockVotes = expressStockVotes;
	}

	/**
	 * @return the expressStockWeight
	 */
	public BigDecimal getExpressStockWeight() {
		return expressStockWeight;
	}

	/**
	 * @param expressStockWeight the expressStockWeight to set
	 */
	public void setExpressStockWeight(BigDecimal expressStockWeight) {
		this.expressStockWeight = expressStockWeight;
	}

	/**
	 * @return the expressStockVolume
	 */
	public BigDecimal getExpressStockVolume() {
		return expressStockVolume;
	}

	/**
	 * @param expressStockVolume the expressStockVolume to set
	 */
	public void setExpressStockVolume(BigDecimal expressStockVolume) {
		this.expressStockVolume = expressStockVolume;
	}

	/**
	 * @return the expressDeliverStockVotes
	 */
	public int getExpressDeliverStockVotes() {
		return expressDeliverStockVotes;
	}

	/**
	 * @param expressDeliverStockVotes the expressDeliverStockVotes to set
	 */
	public void setExpressDeliverStockVotes(int expressDeliverStockVotes) {
		this.expressDeliverStockVotes = expressDeliverStockVotes;
	}

	/**
	 * @return the expressDeliverStockWeight
	 */
	public BigDecimal getExpressDeliverStockWeight() {
		return expressDeliverStockWeight;
	}

	/**
	 * @param expressDeliverStockWeight the expressDeliverStockWeight to set
	 */
	public void setExpressDeliverStockWeight(BigDecimal expressDeliverStockWeight) {
		this.expressDeliverStockWeight = expressDeliverStockWeight;
	}

	/**
	 * @return the expressDeliverStockVolume
	 */
	public BigDecimal getExpressDeliverStockVolume() {
		return expressDeliverStockVolume;
	}

	/**
	 * @param expressDeliverStockVolume the expressDeliverStockVolume to set
	 */
	public void setExpressDeliverStockVolume(BigDecimal expressDeliverStockVolume) {
		this.expressDeliverStockVolume = expressDeliverStockVolume;
	}

	/**
	 * @return the loadedVotes
	 */
	public int getLoadedVotes() {
		return loadedVotes;
	}

	/**
	 * @param loadedVotes the loadedVotes to set
	 */
	public void setLoadedVotes(int loadedVotes) {
		this.loadedVotes = loadedVotes;
	}

	/**
	 * @return the loadedWeight
	 */
	public BigDecimal getLoadedWeight() {
		return loadedWeight;
	}

	/**
	 * @param loadedWeight the loadedWeight to set
	 */
	public void setLoadedWeight(BigDecimal loadedWeight) {
		this.loadedWeight = loadedWeight;
	}

	/**
	 * @return the loadedVolume
	 */
	public BigDecimal getLoadedVolume() {
		return loadedVolume;
	}

	/**
	 * @param loadedVolume the loadedVolume to set
	 */
	public void setLoadedVolume(BigDecimal loadedVolume) {
		this.loadedVolume = loadedVolume;
	}

	

}
