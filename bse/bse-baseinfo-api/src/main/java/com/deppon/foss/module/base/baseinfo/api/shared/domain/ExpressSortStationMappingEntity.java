package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 快递分拣目的站映射实体
 * @author 130566
 *
 */
public class ExpressSortStationMappingEntity extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4232221707430369660L;
	//外场
	private String outfieldCode;
	//外场名称
	private String outfieldName;
	//零担库区编码
	private String  warehouseAreaCode;
	//零担库区名称
	private String  warehouseAreaName;
	//快递目的站
	private String destinationCode;
	//快递目的站名称
	private String destinationName;
	//是否启用
	private String active;
	//备注
	private String remark;
	//虚拟编码
	private String virtualCode;
	//版本号
	private long versionNo;
	
	public String getOutfieldCode() {
		return outfieldCode;
	}
	public void setOutfieldCode(String outfieldCode) {
		this.outfieldCode = outfieldCode;
	}
	public String getOutfieldName() {
		return outfieldName;
	}
	public void setOutfieldName(String outfieldName) {
		this.outfieldName = outfieldName;
	}
	public String getWarehouseAreaCode() {
		return warehouseAreaCode;
	}
	public void setWarehouseAreaCode(String warehouseAreaCode) {
		this.warehouseAreaCode = warehouseAreaCode;
	}
	public String getWarehouseAreaName() {
		return warehouseAreaName;
	}
	public void setWarehouseAreaName(String warehouseAreaName) {
		this.warehouseAreaName = warehouseAreaName;
	}
	public String getDestinationCode() {
		return destinationCode;
	}
	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getVirtualCode() {
		return virtualCode;
	}
	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}
	public long getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}
	
	
	
}
