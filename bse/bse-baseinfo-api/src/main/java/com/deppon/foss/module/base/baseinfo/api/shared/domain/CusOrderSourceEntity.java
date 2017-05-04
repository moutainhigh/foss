package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * CRM行业、客户等级、订单来源信息实体类
 * @author dujunhui-187862
 * @date 2014-9-25 上午2:18:42
 * @since
 * @version
 */
public class CusOrderSourceEntity extends BaseEntity{
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -5334586593969830563L;

	 /**
     * 二级行业编码
     */
    private String secDegreeProfessionCode;
    
    /**
     * 二级行业名称
     */
    private String secDegreeProfessionName;
    
    /**
     * 对应一级行业编码
     */
    private String firDegreeProfessionCode;
    
    /**
     * 对应一级行业名称
     */
    private String firDegreeProfessionName;
    
    /*
     * 客户等级编码
     */
    private String customerDegreeCode;
    
    /*
     * 客户等级名称
     */
    private String customerDegreeName;
    
    /*
     * 订单来源编码
     */
    private String orderSourceCode;
    
    /*
     * 订单来源名称
     */
    private String orderSourceName;
    
    /**
     * 有效性
     */
    private String active;
    
    /**
     * 版本号
     */
    private Long versionNo;
    
    /*
     * 传入类型（CRM行业、客户等级、订单来源类型）
     */
    private String importPattern;
    
    
    /**
     * 以下为属性的get、set方法
     */
	public String getSecDegreeProfessionName() {
		return secDegreeProfessionName;
	}

	public String getSecDegreeProfessionCode() {
		return secDegreeProfessionCode;
	}

	public void setSecDegreeProfessionCode(String secDegreeProfessionCode) {
		this.secDegreeProfessionCode = secDegreeProfessionCode;
	}

	public void setSecDegreeProfessionName(String secDegreeProfessionName) {
		this.secDegreeProfessionName = secDegreeProfessionName;
	}

	public String getFirDegreeProfessionCode() {
		return firDegreeProfessionCode;
	}

	public void setFirDegreeProfessionCode(String firDegreeProfessionCode) {
		this.firDegreeProfessionCode = firDegreeProfessionCode;
	}

	public String getFirDegreeProfessionName() {
		return firDegreeProfessionName;
	}

	public void setFirDegreeProfessionName(String firDegreeProfessionName) {
		this.firDegreeProfessionName = firDegreeProfessionName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	public String getCustomerDegreeCode() {
		return customerDegreeCode;
	}

	public void setCustomerDegreeCode(String customerDegreeCode) {
		this.customerDegreeCode = customerDegreeCode;
	}

	public String getCustomerDegreeName() {
		return customerDegreeName;
	}

	public void setCustomerDegreeName(String customerDegreeName) {
		this.customerDegreeName = customerDegreeName;
	}

	public String getOrderSourceCode() {
		return orderSourceCode;
	}

	public void setOrderSourceCode(String orderSourceCode) {
		this.orderSourceCode = orderSourceCode;
	}

	public String getOrderSourceName() {
		return orderSourceName;
	}

	public void setOrderSourceName(String orderSourceName) {
		this.orderSourceName = orderSourceName;
	}

	public String getImportPattern() {
		return importPattern;
	}

	public void setImportPattern(String importPattern) {
		this.importPattern = importPattern;
	}

}
