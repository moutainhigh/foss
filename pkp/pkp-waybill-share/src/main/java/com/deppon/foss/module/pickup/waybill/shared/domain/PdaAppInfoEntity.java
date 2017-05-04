package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PdaAppInfoEntity implements Serializable{
	
	/**
	 * 序列化默认ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
    private String id;
    
    /**
     * 运单号
     */
    private String waybillNo;
    
    /**
     * 总体积
     */
    private BigDecimal goodsVolumeTotal;
    
    /**
     * 总件数
     */
    private BigDecimal goodsWeightTotal;

    /**
  	 * 是否有效(按道理说应该是永远有效，只是作为保留字段)
  	 */
    private String active;
    
    /**
     * 数据入库时间(OMS到FOSS时候会预先插入一条未扫描数据)
     */
    private Date createTime;

    /**
     * 数据库修改时间
     */
    private Date modifyTime;

    /**
     * APP完成任务入Foss时间
     */
    private Date overTaskTime;
    
    /**
     * 是否扫描(Y/N) 
     */
    private String scan;
    
    /**
     * 司机工号
     */
  	private String driverCode;

  	/**
  	 * 车牌号
  	 */
  	private String truckCode;

  	/**
  	 * 任务编码
  	 */
  	private String taskCode;
  	
  	/**
  	 * APP应用点击完成任务时间
  	 */
  	private Date submissionTime;
  	
  	/**
  	 * 总件数
  	 */
  	private Integer goodsQtyTotal;
  	
  	/**
  	 * 是否大件上楼
  	 */
  	private String isBigUp;
  	
  	/**
  	 * 始发部门
  	 */
  	private String originateOrgCode;
  	
  	/**
  	 * 包装信息
  	 */
    private String packInfo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getOverTaskTime() {
		return overTaskTime;
	}

	public void setOverTaskTime(Date overTaskTime) {
		this.overTaskTime = overTaskTime;
	}

	public String getScan() {
		return scan;
	}

	public void setScan(String scan) {
		this.scan = scan;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getTruckCode() {
		return truckCode;
	}

	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public Date getSubmissionTime() {
		return submissionTime;
	}

	public void setSubmissionTime(Date submissionTime) {
		this.submissionTime = submissionTime;
	}

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public String getIsBigUp() {
		return isBigUp;
	}

	public void setIsBigUp(String isBigUp) {
		this.isBigUp = isBigUp;
	}

	public String getOriginateOrgCode() {
		return originateOrgCode;
	}

	public void setOriginateOrgCode(String originateOrgCode) {
		this.originateOrgCode = originateOrgCode;
	}

	public String getPackInfo() {
		return packInfo;
	}

	public void setPackInfo(String packInfo) {
		this.packInfo = packInfo;
	}
    
}