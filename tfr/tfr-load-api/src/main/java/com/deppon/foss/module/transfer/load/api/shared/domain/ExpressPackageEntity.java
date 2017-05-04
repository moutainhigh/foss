package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/** 
 * @className: ExpressPackageEntity
 * @author: ShiWei shiwei@outlook.com
 * @description: 快递-包实体
 * @date: 2013-7-17 下午2:57:01
 * 
 */
public class ExpressPackageEntity extends BaseEntity {

	private static final long serialVersionUID = -6672797269568299172L;
	
	/**
	 * 包编号
	 */
	private String packageNo;
	
	/**
	 * 出发部门code
	 */
	private String departOrgCode;
	
	/**
	 * 出发部门name
	 */
	private String departOrgName;
	
	/**
	 * 到达部门name
	 */
	private String arriveOrgName;
	
	/**
	 * 到达部门code
	 */
	private String arriveOrgCode;
	
	/**
	 * 重量
	 */
	private BigDecimal weight;
	
	/**
	 * 体积
	 */
	private BigDecimal volume;
	
	/**
	 * 票数
	 */
	private BigDecimal waybillQty;
	
	/**
	 * 件数
	 */
	private BigDecimal goodsQty;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 创建人code
	 */
	private String createUserCode;
	
	/**
	 * 创建人name
	 */
	private String createUserName;
	
	/**
	 * 建包结束时间
	 */
	private Date endTime;
	/**
	 * 修改时间
	 * */
	private Date modifyTime;
	
	/**
	 * 修改人
	 * */
	private String modifyUserName;
	
	/**
	 * 修改人工号
	 * */
	private String modifyUserCode;
	
	

	/**
	 * 快递包类型  普通/直达
	 * */
	private String expressPackageType;
	
	
	
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDepartOrgName() {
		return departOrgName;
	}

	public void setDepartOrgName(String departOrgName) {
		this.departOrgName = departOrgName;
	}

	public String getArriveOrgName() {
		return arriveOrgName;
	}

	public void setArriveOrgName(String arriveOrgName) {
		this.arriveOrgName = arriveOrgName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public BigDecimal getGoodsQty() {
		return goodsQty;
	}

	public void setGoodsQty(BigDecimal goodsQty) {
		this.goodsQty = goodsQty;
	}

	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

	public String getDepartOrgCode() {
		return departOrgCode;
	}

	public void setDepartOrgCode(String departOrgCode) {
		this.departOrgCode = departOrgCode;
	}

	public String getArriveOrgCode() {
		return arriveOrgCode;
	}

	public void setArriveOrgCode(String arriveOrgCode) {
		this.arriveOrgCode = arriveOrgCode;
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

	public BigDecimal getWaybillQty() {
		return waybillQty;
	}

	public void setWaybillQty(BigDecimal waybillQty) {
		this.waybillQty = waybillQty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExpressPackageType() {
		return expressPackageType;
	}

	public void setExpressPackageType(String expressPackageType) {
		this.expressPackageType = expressPackageType;
	}
	
}
