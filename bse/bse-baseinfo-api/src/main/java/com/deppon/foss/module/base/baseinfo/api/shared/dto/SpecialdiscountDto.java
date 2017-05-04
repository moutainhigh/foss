package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//@XmlAccessorType(XmlAccessType.FIELD)
public class SpecialdiscountDto  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1601771080286447904L;
	//客户编码	
    private String custCode;
    //特惠组名称
    private String specialGroupName;
    //特惠组编码
    private String specialGroupCode;
    //申请通过时间
    private Date proposeTime;
    //退出时间
    private Date quitTime;    
    //产品类型
    private String productType;
    //是否有效
    private String active;
    
    private BigDecimal crmId;
          
    public BigDecimal getCrmId() {
		return crmId;
	}

	public void setCrmId(BigDecimal crmId) {
		this.crmId = crmId;
	}
	
   
    public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getSpecialGroupName() {
		return specialGroupName;
	}

	public void setSpecialGroupName(String specialGroupName) {
		this.specialGroupName = specialGroupName;
	}

	public String getSpecialGroupCode() {
		return specialGroupCode;
	}

	public void setSpecialGroupCode(String specialGroupCode) {
		this.specialGroupCode = specialGroupCode;
	}

	public Date getProposeTime() {
		return proposeTime;
	}

	public void setProposeTime(Date proposeTime) {
		this.proposeTime = proposeTime;
	}

	public Date getQuitTime() {
		return quitTime;
	}

	public void setQuitTime(Date quitTime) {
		this.quitTime = quitTime;
	}


}