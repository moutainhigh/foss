package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 库位实体类
 * 
 * @author chengang
 * @version 1.0
 * @created 2012-09-23
 */
public class StoreEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 所属部门
	 */
	private String deptCode;
	/**
	 * 库位编码
	 */
	private String storeNumber;
	/**
	 * 库位类型
	 */
	private String storeLocType;
	/**
	 * 库位名称
	 */
	private String storeName;
	/**
	 * 操作标记
	 */
	private String operFlag;
	/**
	 * 上一次更新时间
	 */
	private String updTime;
	/**
	 * 版本号
	 */
	private String version;
	/**
	 * 是否生效
	 */
	private String isActive;
	/**
	 * 生效时间
	 */
	private String activeTime;
	
	/**
	 * 星号code
	 */
	private String asterisk_code;
	
	/**
	 * 到达网点
	 */
	private String ladingStatCode;
	/**
	 * 货物类型
	 */
	private String goodstype;
	
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getStoreNumber() {
		return storeNumber;
	}
	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}
	public String getStoreLocType() {
		return storeLocType;
	}
	public void setStoreLocType(String storeLocType) {
		this.storeLocType = storeLocType;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getOperFlag() {
		return operFlag;
	}
	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}

	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUpdTime() {
		return updTime;
	}
	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}
	public String getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}
	public String getAsterisk_code() {
		return asterisk_code;
	}
	public void setAsterisk_code(String asterisk_code) {
		this.asterisk_code = asterisk_code;
	}
	public String getLadingStatCode() {
		return ladingStatCode;
	}
	public void setLadingStatCode(String ladingStatCode) {
		this.ladingStatCode = ladingStatCode;
	}
	public String getGoodstype() {
		return goodstype;
	}
	public void setGoodstype(String goodstype) {
		this.goodstype = goodstype;
	}
	
}
