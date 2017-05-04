package com.deppon.pda.bdm.module.foss.unload.shared.domain.kuaidi;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.pda.api.shared.dto.SortingScanDto;


/**
 * 查询补码返回实体类
 * @author 245955
 * @date 2015-08-13
 * @version 1.0
 * @since
 */
public class QrySortingResModel {
	/**
	 * 运单号
	 */
	private String wblCode;
	/**
	 * 到达部门编号
	 */
	private String targetOrgCode;
	/**
	 * 目的部门
	 */
	private String deryCrgAddress;
	/**
	 * 补码时间
	 */
	private Date complementDate;
	/**
	 * 提货网点编码
	 */
	private String reachOrgCode;
	/**
	 * 提货网点名称
	 */
	private String reachOrgName;
	/**
	 * 是否补码
	 */
	private String isBeAddCode;
	
	/**
	 * 最终到达外场
	 */
	private String FinalOutDept;
	
	/**
	 * 下一部门
	 */
	private String nextOrgCode;
	
	/**
	 * 产品类型
	 */
	private String productCode;
	
	//是否电子面单 author:245960 Date:2015-06-29
	private String beEWaybill;
	
	private String cityShort;
	
	private List<SortingScanDto> SevenReturnList;
	
	public List<SortingScanDto> getSevenReturnList() {
		return SevenReturnList;
	}
	public void setSevenReturnList(List<SortingScanDto> sevenReturnList) {
		SevenReturnList = sevenReturnList;
	}
	public String getCityShort() {
		return cityShort;
	}
	public void setCityShort(String cityShort) {
		this.cityShort = cityShort;
	}
	
	public String getBeEWaybill() {
		return beEWaybill;
	}
	public void setBeEWaybill(String beEWaybill) {
		this.beEWaybill = beEWaybill;
	}
	public String getFinalOutDept() {
		return FinalOutDept;
	}
	public void setFinalOutDept(String finalOutDept) {
		FinalOutDept = finalOutDept;
	}
	public String getWblCode() {
		return wblCode;
	}
	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}
	
	public String getTargetOrgCode() {
		return targetOrgCode;
	}
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}
	public String getDeryCrgAddress() {
		return deryCrgAddress;
	}
	public void setDeryCrgAddress(String deryCrgAddress) {
		this.deryCrgAddress = deryCrgAddress;
	}
	public Date getComplementDate() {
		return complementDate;
	}
	public void setComplementDate(Date complementDate) {
		this.complementDate = complementDate;
	}
	public String getReachOrgCode() {
		return reachOrgCode;
	}
	public void setReachOrgCode(String reachOrgCode) {
		this.reachOrgCode = reachOrgCode;
	}
	public String getReachOrgName() {
		return reachOrgName;
	}
	public void setReachOrgName(String reachOrgName) {
		this.reachOrgName = reachOrgName;
	}
	public String getIsBeAddCode() {
		return isBeAddCode;
	}
	public void setIsBeAddCode(String isBeAddCode) {
		this.isBeAddCode = isBeAddCode;
	}
	public String getNextOrgCode() {
		return nextOrgCode;
	}
	public void setNextOrgCode(String nextOrgCode) {
		this.nextOrgCode = nextOrgCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
}
