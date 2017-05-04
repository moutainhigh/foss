package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

public class RookieWaybillJBDEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7364613722820665L;
	/**
	 * <p>发货省名字</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	private String startProvinceName;
	/**
	 * 发货市名字
	 */
	private String startCityName;
	/**
	 * 发货区名字
	 */
	private String startCountyName;
	/**
	 * 发货省Code
	 */
	private String startProvinceCode;
	/**
	 * 发货市CODE
	 */
	private String startCityCode;
	/**
	 * 发货区CODE
	 */
	private String startCountyCode;
	/**
	 * 收货省名字
	 */
	private String reachProvinceName;
	/**
	 * 收货市名字
	 */
	private String reachCityName;
	/**
	 * 收货区名字
	 */
	private String reachCountyName;
	/**
	 * 收货省code
	 */
	private String reachProvinceCode;
	/**
	 * 收货市code
	 */
	private String reachCityCode;
	/**
	 * 收货区code
	 */
	private String reachCountyCode;
	/**
	 * 发货地址
	 */
	private String startAddress;
	/**
	 * 收货地址
	 */
	private String reachAddress;
	/**
	 * 大头笔或集包地
	 */
	private String bigHeadOrJBD;
	/**
	 * 大头笔或集包地类型 0大头笔  1集包地
	 */
	private String type;
	/**
	 * 集包地编码 
	 */
	private String jbdCode;
	
	private String virtualSiteCode;
	/**
	 * 版本号
	 */
	private long versionNo;
	/**
	 * 是否有效
	 */
	private String  active;
	/**
	 * 中转场code
	 */
	private String transferCode;
	/**
	 * 大头笔或集包地类型 0大头笔  1集包地
	 */
	private String typeCode;
	
	/**
	 * get set方法
	 */
	public String getStartProvinceName() {
		return startProvinceName;
	}

	public void setStartProvinceName(String startProvinceName) {
		this.startProvinceName = startProvinceName;
	}

	public String getStartCityName() {
		return startCityName;
	}

	public void setStartCityName(String startCityName) {
		this.startCityName = startCityName;
	}

	public String getStartCountyName() {
		return startCountyName;
	}

	public void setStartCountyName(String startCountyName) {
		this.startCountyName = startCountyName;
	}

	public String getStartProvinceCode() {
		return startProvinceCode;
	}

	public void setStartProvinceCode(String startProvinceCode) {
		this.startProvinceCode = startProvinceCode;
	}

	public String getStartCityCode() {
		return startCityCode;
	}

	public void setStartCityCode(String startCityCode) {
		this.startCityCode = startCityCode;
	}

	public String getStartCountyCode() {
		return startCountyCode;
	}

	public void setStartCountyCode(String startCountyCode) {
		this.startCountyCode = startCountyCode;
	}

	public String getReachProvinceName() {
		return reachProvinceName;
	}

	public void setReachProvinceName(String reachProvinceName) {
		this.reachProvinceName = reachProvinceName;
	}

	public String getReachCityName() {
		return reachCityName;
	}

	public void setReachCityName(String reachCityName) {
		this.reachCityName = reachCityName;
	}

	public String getReachCountyName() {
		return reachCountyName;
	}

	public void setReachCountyName(String reachCountyName) {
		this.reachCountyName = reachCountyName;
	}

	public String getReachProvinceCode() {
		return reachProvinceCode;
	}

	public void setReachProvinceCode(String reachProvinceCode) {
		this.reachProvinceCode = reachProvinceCode;
	}

	public String getReachCityCode() {
		return reachCityCode;
	}

	public void setReachCityCode(String reachCityCode) {
		this.reachCityCode = reachCityCode;
	}

	public String getReachCountyCode() {
		return reachCountyCode;
	}

	public void setReachCountyCode(String reachCountyCode) {
		this.reachCountyCode = reachCountyCode;
	}

	public String getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	public String getReachAddress() {
		return reachAddress;
	}

	public void setReachAddress(String reachAddress) {
		this.reachAddress = reachAddress;
	}



	

	public String getBigHeadOrJBD() {
		return bigHeadOrJBD;
	}

	public void setBigHeadOrJBD(String bigHeadOrJBD) {
		this.bigHeadOrJBD = bigHeadOrJBD;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public String getVirtualSiteCode() {
		return virtualSiteCode;
	}

	public void setVirtualSiteCode(String virtualSiteCode) {
		this.virtualSiteCode = virtualSiteCode;
	}

	public long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getTransferCode() {
		return transferCode;
	}

	public void setTransferCode(String transferCode) {
		this.transferCode = transferCode;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getJbdCode() {
		return jbdCode;
	}

	public void setJbdCode(String jbdCode) {
		this.jbdCode = jbdCode;
	}
}
