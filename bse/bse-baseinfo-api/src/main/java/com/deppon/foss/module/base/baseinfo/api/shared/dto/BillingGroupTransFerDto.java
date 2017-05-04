package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
/**
 * 外场和集中开单组关系DTO
 * @author foss-lifanghong
 * @date 2013-06-02
 * @version 1.0
 */
public class BillingGroupTransFerDto implements Serializable{

    
    /**
     * 
     */
    private static final long serialVersionUID = 7166055801966743012L;

    // 外场编码
    private String transFerCode;
    
    // 外场名称
    private String transFerName;
    // 操作人员工号
    private String modifyUserCode;
    // 集中开单组编码
    private String billingGroupCode;
    
    // 集中开单组名称
    private String billingGroupName;
    public String getTransFerCode() {
		return transFerCode;
	}

	public void setTransFerCode(String transFerCode) {
		this.transFerCode = transFerCode;
	}

	public String getTransFerName() {
		return transFerName;
	}

	public void setTransFerName(String transFerName) {
		this.transFerName = transFerName;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getBillingGroupCode() {
		return billingGroupCode;
	}

	public void setBillingGroupCode(String billingGroupCode) {
		this.billingGroupCode = billingGroupCode;
	}

	public String getBillingGroupName() {
		return billingGroupName;
	}

	public void setBillingGroupName(String billingGroupName) {
		this.billingGroupName = billingGroupName;
	}

	
  
    


}
