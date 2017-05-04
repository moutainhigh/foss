package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.util.define.FossConstants;

/**
 * 快递点部营业部映射关系查询Dto
 * @author foss-qiaolifeng
 * @date 2013-7-25 上午9:10:51
 */
public class ExpressPartSalesDeptQueryDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1289671348205486919L;
	
	/**
	 * 有效状态
	 */
	@SuppressWarnings("unused")
	private static final String ACTIVE_YES = FossConstants.YES;
	
	/**
	 * 快递点部名称
	 */
	private String expressPartName;
	
	/**
	 * 快递点部编码
	 */
	private String expressPartCode;
	
	/**
	 * 营业部名称
	 */
	private String salesDeptName;
	
	/**
	 * 营业部编码
	 */
	private String salesDeptCode;
	
	/**
	 * 营业部拼音
	 */
	private String salesDeptPinyin;
	
	/**
	 * 省份编码
	 */
	private String provCode;
	
	/**
	 * 城市编码
	 */
	private String cityCode;

	/**
	 * 县区编码
	 */
	private String countyCode;
	
	/**
	 * 已选ID集合
	 */
	private String selectedIds;
		
	/**
	 * 是否有效
	 */
	private String active;
	
	/**
	 * 选择编码集合字符串
	 */
	private String selectedCodes;
	
	/**
	 * 选择编码集合List
	 */
	private List<String> selectedCodeList;
	
	/**
	 * 选择ID集合List
	 */
	private List<String> selectedIdList;
	
	/**
	 * 营业部扩展实体数据列表
	 */
	private List<ExpressSaleDepartmentResultDto> saleDepartmentResultDtoList;
	
	/**
	 * 快递点部营业部关系数据dto
	 */
	private ExpressPartSalesDeptResultDto expressPartSalesDeptResultDto;
	
	/**
	 * 业务时间
	 */
	private Date businessTime;
	
	/**
	 * 立即激活或者立即终止时间
	 */
	private Date activeOpeTime;
	
	public Date getActiveOpeTime() {
		return activeOpeTime;
	}

	public void setActiveOpeTime(Date activeOpeTime) {
		this.activeOpeTime = activeOpeTime;
	}

	public Date getBusinessTime() {
		return businessTime;
	}

	public void setBusinessTime(Date businessTime) {
		this.businessTime = businessTime;
	}

	public List<ExpressSaleDepartmentResultDto> getSaleDepartmentResultDtoList() {
		return saleDepartmentResultDtoList;
	}

	public void setSaleDepartmentResultDtoList(
			List<ExpressSaleDepartmentResultDto> saleDepartmentResultDtoList) {
		this.saleDepartmentResultDtoList = saleDepartmentResultDtoList;
	}

	public ExpressPartSalesDeptResultDto getExpressPartSalesDeptResultDto() {
		return expressPartSalesDeptResultDto;
	}

	public void setExpressPartSalesDeptResultDto(
			ExpressPartSalesDeptResultDto expressPartSalesDeptResultDto) {
		this.expressPartSalesDeptResultDto = expressPartSalesDeptResultDto;
	}

	public String getSelectedCodes() {
		return selectedCodes;
	}

	public void setSelectedCodes(String selectedCodes) {
		this.selectedCodes = selectedCodes;
	}

	public List<String> getSelectedCodeList() {
		return selectedCodeList;
	}

	public void setSelectedCodeList(List<String> selectedCodeList) {
		this.selectedCodeList = selectedCodeList;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getExpressPartName() {
		return expressPartName;
	}

	public void setExpressPartName(String expressPartName) {
		this.expressPartName = expressPartName;
	}

	public String getExpressPartCode() {
		return expressPartCode;
	}

	public void setExpressPartCode(String expressPartCode) {
		this.expressPartCode = expressPartCode;
	}

	public String getSalesDeptName() {
		return salesDeptName;
	}

	public void setSalesDeptName(String salesDeptName) {
		this.salesDeptName = salesDeptName;
	}

	public String getSalesDeptCode() {
		return salesDeptCode;
	}

	public void setSalesDeptCode(String salesDeptCode) {
		this.salesDeptCode = salesDeptCode;
	}

	public String getSalesDeptPinyin() {
		return salesDeptPinyin;
	}

	public void setSalesDeptPinyin(String salesDeptPinyin) {
		this.salesDeptPinyin = salesDeptPinyin;
	}

	public String getProvCode() {
		return provCode;
	}

	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}

	public List<String> getSelectedIdList() {
		return selectedIdList;
	}

	public void setSelectedIdList(List<String> selectedIdList) {
		this.selectedIdList = selectedIdList;
	}
	

}
