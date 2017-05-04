package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 客户行业信息实体类
 * @author dujunhui-187862
 * @date 2014-9-25 上午2:18:42
 * @since
 * @version
 */
public class CustomerIndustryEntity extends BaseEntity{
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -5334586593969830563L;

	 /**
     * 二级行业编码
     */
    private String professionCode;
    
    /**
     * 二级行业名称
     */
    private String professionName;
    
    /**
     * 对应一级行业编码
     */
    private String parentProfessionCode;
    
    /**
     * 对应一级行业名称
     */
    private String parentProfessionName;
    
    /**
     * 有效性
     */
    private String active;
    
    /**
     * 版本号
     */
    private Long versionNo;
    
    /**
     * 客户编码--用作查询条件
     */
    private String customerCode;
    
    /**
     * 以下为属性的get、set方法
     */

	public String getActive() {
		return active;
	}

	/**
	 * @return  the professionCode
	 */
	public String getProfessionCode() {
		return professionCode;
	}

	/**
	 * @param professionCode the professionCode to set
	 */
	public void setProfessionCode(String professionCode) {
		this.professionCode = professionCode;
	}

	/**
	 * @return  the professionName
	 */
	public String getProfessionName() {
		return professionName;
	}

	/**
	 * @param professionName the professionName to set
	 */
	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}

	/**
	 * @return  the parentProfessionCode
	 */
	public String getParentProfessionCode() {
		return parentProfessionCode;
	}

	/**
	 * @param parentProfessionCode the parentProfessionCode to set
	 */
	public void setParentProfessionCode(String parentProfessionCode) {
		this.parentProfessionCode = parentProfessionCode;
	}

	/**
	 * @return  the parentProfessionName
	 */
	public String getParentProfessionName() {
		return parentProfessionName;
	}

	/**
	 * @param parentProfessionName the parentProfessionName to set
	 */
	public void setParentProfessionName(String parentProfessionName) {
		this.parentProfessionName = parentProfessionName;
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
	@Override
	public int hashCode() {
		if(professionCode!=null){
			return this.professionCode.hashCode();
		}else{
			return super.hashCode();
		}
	}
	@Override
	public boolean equals(Object obj) {
		if(obj!=null&& obj instanceof CustomerIndustryEntity){
			CustomerIndustryEntity c = (CustomerIndustryEntity) obj;
			if(c.getProfessionCode()!=null
					&&c.getProfessionCode().equals(this.professionCode)){
				return true;
			}
		}
		return false;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
}
