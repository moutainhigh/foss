package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 接驳点对应营业部关系子实体
 * @author 132599-ShenWeiHua
 *
 * @date 2015-4-14 上午10:06:57
 */
public class AcceptPointSalesChildrenDeptEntity extends BaseEntity{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 接驳点编码
	 */
	private String acceptPointCode;
	
	/**
	 * 营业区编码
	 */
	private String smallRegion;
	
	/**
	 * 营业部编码
	 */
	private String salesDepartmentCode;
	
	/**
	 * 是否启用
	 */
	private String status;
	
	/**
	 * 是否删除
	 */
	private String active;
	
	/**
	 * 营业区名称
	 */
	private String smallRegionName;
	
	/**
	 * 营业部名称
	 */
	private String salesDepartmentName;

	public String getAcceptPointCode() {
		return acceptPointCode;
	}

	public void setAcceptPointCode(String acceptPointCode) {
		this.acceptPointCode = acceptPointCode;
	}

	public String getSmallRegion() {
		return smallRegion;
	}

	public void setSmallRegion(String smallRegion) {
		this.smallRegion = smallRegion;
	}

	public String getSalesDepartmentCode() {
		return salesDepartmentCode;
	}

	public void setSalesDepartmentCode(String salesDepartmentCode) {
		this.salesDepartmentCode = salesDepartmentCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getSmallRegionName() {
		return smallRegionName;
	}

	public void setSmallRegionName(String smallRegionName) {
		this.smallRegionName = smallRegionName;
	}

	public String getSalesDepartmentName() {
		return salesDepartmentName;
	}

	public void setSalesDepartmentName(String salesDepartmentName) {
		this.salesDepartmentName = salesDepartmentName;
	}
	
	
}
