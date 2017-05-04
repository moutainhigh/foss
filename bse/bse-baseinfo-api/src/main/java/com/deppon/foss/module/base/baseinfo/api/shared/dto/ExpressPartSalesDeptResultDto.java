package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPartSalesDeptEntity;

/**
 * 快递点部营业部映射关系结果Dto
 * @author foss-qiaolifeng
 * @date 2013-7-25 上午9:41:16
 */
public class ExpressPartSalesDeptResultDto extends ExpressPartSalesDeptEntity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3500819510259430582L;

	/**
	 * 快递点部名称
	 */
	private String partName;
	
	/**
	 * 营业部名称
	 */
	private String salesDeptName;
	
	/**
	 * 快递点部所属快递大区名称
	 */
	private String expressBigRegionName;
	
	/**
	 * 快递点部所属快递大区编码
	 */
	private String expressBigRegionCode;

	/**
	 * 创建人名称
	 */
	private String createUserName;
	
	/**
	 * 修改人名称
	 */
	private String modifyUserName;
	
	/**
	 * 组织标杆编码
	 */
	private String unifiedCode;
	
	public String getUnifiedCode() {
		return unifiedCode;
	}

	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getExpressBigRegionName() {
		return expressBigRegionName;
	}

	public void setExpressBigRegionName(String expressBigRegionName) {
		this.expressBigRegionName = expressBigRegionName;
	}

	public String getExpressBigRegionCode() {
		return expressBigRegionCode;
	}

	public void setExpressBigRegionCode(String expressBigRegionCode) {
		this.expressBigRegionCode = expressBigRegionCode;
	}

	public String getSalesDeptName() {
		return salesDeptName;
	}

	public void setSalesDeptName(String salesDeptName) {
		this.salesDeptName = salesDeptName;
	}
	
}
