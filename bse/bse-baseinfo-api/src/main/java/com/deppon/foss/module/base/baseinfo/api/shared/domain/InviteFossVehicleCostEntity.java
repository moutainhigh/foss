package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 零担外请车单票费用返回结果
 * 
 * @author 332219-foss
 * @date 2017-03-11 下午2:35:52
 */
public class InviteFossVehicleCostEntity  extends BaseEntity {
	
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -3967231352083822853L;

    /**
    * 组织ID
    */	
    private String id;
    /**
    *事业部编码
    */	
    private String businessCode;
    /**
    *事业部名称
    */	
    private String businessName;
    /**
     *大区编码
     */	
     private String regionalCode;
     /**
     *大区名称
     */	
     private String regionalName;
     /**
      *单票费用上限
      */	
     private double vehicleCostMax;
     /**
      * 是否有效
      */
     private String active;
     /**
      * 创建时间
      */
     private Date createTime;
     /**
      * 修改时间
      */
     private Date updateTime;
     /**
 	 *创建人 
 	 */
 	 private String createUser;
 	 /**
 	  * 创建人名称
 	  */
 	 private String createUserName;
 	 /**
 	  * 修改人
 	  */
 	 private String updateUser;
 	 /**
 	  * 修改人名称
 	  */
 	 private String updateUserName;
    
    /**
  	 *getter
  	 */
	public String getId() {
		return id;
	}
	/**
	 *setter
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 *getter
	 */
	public String getBusinessCode() {
		return businessCode;
	}
	/**
	 *setter
	 */
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	/**
	 *getter
	 */
	public String getBusinessName() {
		return businessName;
	}
	/**
	 *setter
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	/**
	 *getter
	 */
	public String getRegionalCode() {
		return regionalCode;
	}
	/**
	 *setter
	 */
	public void setRegionalCode(String regionalCode) {
		this.regionalCode = regionalCode;
	}
	/**
	 *getter
	 */
	public String getRegionalName() {
		return regionalName;
	}
	/**
	 *setter
	 */
	public void setRegionalName(String regionalName) {
		this.regionalName = regionalName;
	}
	/**
	 *getter
	 */
	public double getVehicleCostMax() {
		return vehicleCostMax;
	}
	/**
	 *setter
	 */
	public void setVehicleCostMax(double vehicleCostMax) {
		this.vehicleCostMax = vehicleCostMax;
	}
	/**
	 *getter
	 */
	public String getActive() {
		return active;
	}
	/**
	 *setter
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 *getter
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 *setter
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 *getter
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 *setter
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 *getter
	 */
	public String getCreateUser() {
		return createUser;
	}
	/**
	 *setter
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	/**
	 *getter
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	/**
	 *setter
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	/**
	 *getter
	 */
	public String getUpdateUser() {
		return updateUser;
	}
	/**
	 *setter
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	/**
	 *getter
	 */
	public String getUpdateUserName() {
		return updateUserName;
	}
	/**
	 *setter
	 */
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
}