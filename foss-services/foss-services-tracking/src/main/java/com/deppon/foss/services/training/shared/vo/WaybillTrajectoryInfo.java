package com.deppon.foss.services.training.shared.vo;

import java.util.Date;

public class WaybillTrajectoryInfo {
	
	private String waybillNo;
    
    private Date preArriveTime;
    
    private String operateDept;
    
    private String operateDeptCode;
    
    private String operateDeptCityCode;
    
    private String operateDeptCityName;
    
    private String operateType;
    private String operateContent;
    
    private Date operateTime;
    
    private String operator;
    private int operatePartsNumber;
    private String billNo;
    private String licensePlateNo;
    private String remarks;
    private String serialData;
    private String nextDept;
    private String nextDeptName;
    private String nextDeptCityNum;
    private String nextDeptCityName;
    private String destinationDept;
    private String destinationDeptName;
    private String destinationDeptCityNum;
    private String destinationDeptCityName;
    
    private Date predictArriveNextDeptTime;
    private String dispatcher;
    private String dispatcherPhone;
    private String signer;
    private String dispatchFailReason;
    private Date dispatchAgainTime;

    /**
     * Gets the value of the waybillNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWaybillNo() {
        return waybillNo;
    }

    /**
     * Sets the value of the waybillNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWaybillNo(String value) {
        this.waybillNo = value;
    }

    /**
     * Gets the value of the preArriveTime property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getPreArriveTime() {
        return preArriveTime;
    }

    /**
     * Sets the value of the preArriveTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setPreArriveTime(Date value) {
        this.preArriveTime = value;
    }

    /**
     * Gets the value of the operateDept property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperateDept() {
        return operateDept;
    }

    /**
     * Sets the value of the operateDept property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperateDept(String value) {
        this.operateDept = value;
    }

    /**
     * Gets the value of the operateDeptCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperateDeptCode() {
        return operateDeptCode;
    }

    /**
     * Sets the value of the operateDeptCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperateDeptCode(String value) {
        this.operateDeptCode = value;
    }

    /**
     * Gets the value of the operateDeptCityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperateDeptCityCode() {
        return operateDeptCityCode;
    }

    /**
     * Sets the value of the operateDeptCityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperateDeptCityCode(String value) {
        this.operateDeptCityCode = value;
    }

    /**
     * Gets the value of the operateDeptCityName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperateDeptCityName() {
        return operateDeptCityName;
    }

    /**
     * Sets the value of the operateDeptCityName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperateDeptCityName(String value) {
        this.operateDeptCityName = value;
    }

    /**
     * Gets the value of the operateType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperateType() {
        return operateType;
    }

    /**
     * Sets the value of the operateType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperateType(String value) {
        this.operateType = value;
    }

    /**
     * Gets the value of the operateContent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperateContent() {
        return operateContent;
    }

    /**
     * Sets the value of the operateContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperateContent(String value) {
        this.operateContent = value;
    }

    /**
     * Gets the value of the operateTime property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getOperateTime() {
        return operateTime;
    }

    /**
     * Sets the value of the operateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setOperateTime(Date value) {
        this.operateTime = value;
    }

    /**
     * Gets the value of the operator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Sets the value of the operator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperator(String value) {
        this.operator = value;
    }

    /**
     * Gets the value of the operatePartsNumber property.
     * 
     */
    public int getOperatePartsNumber() {
        return operatePartsNumber;
    }

    /**
     * Sets the value of the operatePartsNumber property.
     * 
     */
    public void setOperatePartsNumber(int value) {
        this.operatePartsNumber = value;
    }

    /**
     * Gets the value of the billNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * Sets the value of the billNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillNo(String value) {
        this.billNo = value;
    }

    /**
     * Gets the value of the licensePlateNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicensePlateNo() {
        return licensePlateNo;
    }

    /**
     * Sets the value of the licensePlateNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicensePlateNo(String value) {
        this.licensePlateNo = value;
    }

    /**
     * Gets the value of the remarks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets the value of the remarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarks(String value) {
        this.remarks = value;
    }

    /**
     * Gets the value of the serialData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialData() {
        return serialData;
    }

    /**
     * Sets the value of the serialData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialData(String value) {
        this.serialData = value;
    }

    /**
     * Gets the value of the nextDept property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNextDept() {
        return nextDept;
    }

    /**
     * Sets the value of the nextDept property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNextDept(String value) {
        this.nextDept = value;
    }

    /**
     * Gets the value of the nextDeptName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNextDeptName() {
        return nextDeptName;
    }

    /**
     * Sets the value of the nextDeptName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNextDeptName(String value) {
        this.nextDeptName = value;
    }

    /**
     * Gets the value of the nextDeptCityNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNextDeptCityNum() {
        return nextDeptCityNum;
    }

    /**
     * Sets the value of the nextDeptCityNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNextDeptCityNum(String value) {
        this.nextDeptCityNum = value;
    }

    /**
     * Gets the value of the nextDeptCityName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNextDeptCityName() {
        return nextDeptCityName;
    }

    /**
     * Sets the value of the nextDeptCityName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNextDeptCityName(String value) {
        this.nextDeptCityName = value;
    }

    /**
     * Gets the value of the destinationDept property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationDept() {
        return destinationDept;
    }

    /**
     * Sets the value of the destinationDept property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationDept(String value) {
        this.destinationDept = value;
    }

    /**
     * Gets the value of the destinationDeptName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationDeptName() {
        return destinationDeptName;
    }

    /**
     * Sets the value of the destinationDeptName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationDeptName(String value) {
        this.destinationDeptName = value;
    }

    /**
     * Gets the value of the destinationDeptCityNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationDeptCityNum() {
        return destinationDeptCityNum;
    }

    /**
     * Sets the value of the destinationDeptCityNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationDeptCityNum(String value) {
        this.destinationDeptCityNum = value;
    }

    /**
     * Gets the value of the destinationDeptCityName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationDeptCityName() {
        return destinationDeptCityName;
    }

    /**
     * Sets the value of the destinationDeptCityName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationDeptCityName(String value) {
        this.destinationDeptCityName = value;
    }

    /**
     * Gets the value of the predictArriveNextDeptTime property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getPredictArriveNextDeptTime() {
        return predictArriveNextDeptTime;
    }

    /**
     * Sets the value of the predictArriveNextDeptTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setPredictArriveNextDeptTime(Date value) {
        this.predictArriveNextDeptTime = value;
    }

    /**
     * Gets the value of the dispatcher property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDispatcher() {
        return dispatcher;
    }

    /**
     * Sets the value of the dispatcher property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDispatcher(String value) {
        this.dispatcher = value;
    }

    /**
     * Gets the value of the dispatcherPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDispatcherPhone() {
        return dispatcherPhone;
    }

    /**
     * Sets the value of the dispatcherPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDispatcherPhone(String value) {
        this.dispatcherPhone = value;
    }

    /**
     * Gets the value of the signer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSigner() {
        return signer;
    }

    /**
     * Sets the value of the signer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSigner(String value) {
        this.signer = value;
    }

    /**
     * Gets the value of the dispatchFailReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDispatchFailReason() {
        return dispatchFailReason;
    }

    /**
     * Sets the value of the dispatchFailReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDispatchFailReason(String value) {
        this.dispatchFailReason = value;
    }

    /**
     * Gets the value of the dispatchAgainTime property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getDispatchAgainTime() {
        return dispatchAgainTime;
    }

    /**
     * Sets the value of the dispatchAgainTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setDispatchAgainTime(Date value) {
        this.dispatchAgainTime = value;
    }

}
