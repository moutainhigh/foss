package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;


public class ThroughExpressPathEntity extends BaseEntity {

	/**
	 * 快递直达包走货路径
	 * 
	 */
	private static final long serialVersionUID = -7087337328279280649L;

	private String packageNo;//包号
	
	private String unbindOrgCode;//解包部门code	
	
	private String unbindOrgName;//解包部门名称
	
	private String createOrgCode;//建包部门code
	
	private String createOrgName;//建包部门名称
	
	private BigDecimal weight;//总重量
	
	private BigDecimal volume;//总体积
	
	private BigDecimal waybillQty;//票数
	
	private int serialQty;//件数
	
    private String status;//状态
	
	private String packagePath;//路由描述
	
	private String createUserCode;//创建人code
	
	private String modifyUserCode;//修改人code

	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}


	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
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

	public int getSerialQty() {
		return serialQty;
	}

	public void setSerialQty(int serialQty) {
		this.serialQty = serialQty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
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

	public String getUnbindOrgCode() {
		return unbindOrgCode;
	}

	public void setUnbindOrgCode(String unbindOrgCode) {
		this.unbindOrgCode = unbindOrgCode;
	}

	public String getUnbindOrgName() {
		return unbindOrgName;
	}

	public void setUnbindOrgName(String unbindOrgName) {
		this.unbindOrgName = unbindOrgName;
	}
	
	
}
