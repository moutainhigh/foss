//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.5-2 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2016.10.13 时间 06:45:56 PM CST 
//


package com.deppon.esb.inteface.domain.foss2ptp;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 推送运单号给PTP合伙人明细
 * 
 * <p>ToPtpBatchDeductItem complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ToPtpBatchDeductItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="waybillNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="partnerOrgCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="operatorCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="operatorName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ToPtpBatchDeductItem", propOrder = {
    "waybillNo",
    "partnerOrgCode",
    "operatorCode",
    "operatorName",
    "inStockTime"
})
public class ToPtpBatchDeductItem
    implements Serializable
{

    private final static long serialVersionUID = 11082011L;
    @XmlElement(required = true)
    protected String waybillNo;
    @XmlElement(required = true)
    protected String partnerOrgCode;
    @XmlElement(required = true)
    protected String operatorCode;
    @XmlElement(required = true)
    protected String operatorName;
    @XmlElement(required = true)
    protected Date inStockTime;

    /**
     * 获取waybillNo属性的值。
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
     * 设置waybillNo属性的值。
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
     * 获取partnerOrgCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerOrgCode() {
        return partnerOrgCode;
    }

    /**
     * 设置partnerOrgCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerOrgCode(String value) {
        this.partnerOrgCode = value;
    }

    /**
     * 获取operatorCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperatorCode() {
        return operatorCode;
    }

    /**
     * 设置operatorCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperatorCode(String value) {
        this.operatorCode = value;
    }

    /**
     * 获取operatorName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperatorName() {
        return operatorName;
    }

	/**
     * 设置operatorName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperatorName(String value) {
        this.operatorName = value;
    }
    
    /**
     * 获取inStockTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getInStockTime() {
		return inStockTime;
	}
    
    /**
     * 设置inStockTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
	public void setInStockTime(Date inStockTime) {
		this.inStockTime = inStockTime;
	}
}
