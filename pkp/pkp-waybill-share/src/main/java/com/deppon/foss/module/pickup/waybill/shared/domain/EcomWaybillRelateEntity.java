package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * @author Foss-105888-Zhangxingwang
 *
 */
public class EcomWaybillRelateEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5844586919084315625L;

	//ID
    private String id;
    
    //原始订单号
    private String orignalOrderNo;

    //运单号
    private String waybillNo;

    //重量
    private BigDecimal weight;

    //体积
    private BigDecimal volume;
    
    private Integer piece;

    //订单号
    private String orderNo;

    //创建时间
    private Date createTime;

    //修改时间
    private Date modifyTime;

    //创建人工号
    private String createUserCode;

    //创建组织
    private String createOrgCode;
    
    //修改人工号
    private String modifyUserCode;

    //修改组织
    private String modifyOrgCode;

    //运单类型
    private String waybillType;

    //是否子母件
    private String isPicPackage;

    //是否有效
    private String active;
    
    private String taskId;
    
    private String effective;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrignalOrderNo() {
		return orignalOrderNo;
	}

	public void setOrignalOrderNo(String orignalOrderNo) {
		this.orignalOrderNo = orignalOrderNo;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public Integer getPiece() {
		return piece;
	}

	public void setPiece(Integer piece) {
		this.piece = piece;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getModifyOrgCode() {
		return modifyOrgCode;
	}

	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}

	public String getWaybillType() {
		return waybillType;
	}

	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}

	public String getIsPicPackage() {
		return isPicPackage;
	}

	public void setIsPicPackage(String isPicPackage) {
		this.isPicPackage = isPicPackage;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getEffective() {
		return effective;
	}

	public void setEffective(String effective) {
		this.effective = effective;
	}
	
	
}
