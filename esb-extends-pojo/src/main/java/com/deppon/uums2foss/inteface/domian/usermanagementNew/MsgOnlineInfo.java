//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.5-2 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.09.09 时间 04:50:07 PM CST 
//

package com.deppon.uums2foss.inteface.domian.usermanagementNew;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>MsgOnlineInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="MsgOnlineInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="waybillNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="supplierOrderNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="supplierName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="supplierNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="supplierMobile" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="loadingStation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="loadingStationCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="operateType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="receiveOrg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="receiveOrgCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="payMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="receiveName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="receiveMobile" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="signTime" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="signState" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="signNumber" type="{http://www.w3.org/2001/XMLSchema}Integer"/>
 *         &lt;element name="signRemark" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="judgment" type="{http://www.w3.org/2001/XMLSchema}Integer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MsgOnlineInfo", propOrder = {
    "waybillNo",
    "supplierOrderNo",
    "name",
    "supplierName",
    "supplierNo",
    "supplierMobile",
    "loadingStation",
    "loadingStationCode",
    "operateType",
    "remark",
    "receiveOrg",
    "receiveOrgCode",
    "payMethod",
    "receiveName",
    "receiveMobile",
    "signTime",
    "signState",
    "signNumber",
    "signRemark",
    "judgment"
})
public class MsgOnlineInfo
    implements Serializable
{
    private final static long serialVersionUID = 11082011L;

    //运单号
    protected String waybillNo;

    //供应商订单号
    protected String supplierOrderNo;

    //提货人姓名
    protected String name;

    //提货供应商名称
    protected String supplierName;

    //供应商编码
    protected String supplierNo;

    //提货供应商联系电话
    protected String supplierMobile;

    //提货网点名称
    protected String loadingStation;

    //提货网点编码 
    protected String loadingStationCode;

    //操作类型
    protected int operateType;

    //备注
    protected String remark;

    //收货部门名称
    protected String receiveOrg;

    //收货部门编码
    protected String receiveOrgCode;

    //付款方式
    protected String payMethod;

    //收货联系人
    protected String receiveName;

    //收货联系人电话
    protected String receiveMobile;

    //签收时间
    protected Date signTime;

    //签收状态
    protected String signState;
    
    //签收件数
    protected Integer signNumber;

	//签收备注
    protected String signRemark;

    //标识字段，用来标识DOP传来的信息为提货信息还是签收信息。值为0：表示提货信息；值为1：表示签收信息。
    protected Integer judgment;

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
     * 获取supplierOrderNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplierOrderNo() {
        return supplierOrderNo;
    }

    /**
     * 设置supplierOrderNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplierOrderNo(String value) {
        this.supplierOrderNo = value;
    }

    /**
     * 获取name属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * 获取supplierName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 设置supplierName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplierName(String value) {
        this.supplierName = value;
    }
    
    /**
     * 获取supplierNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 设置supplierNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplierNo(String value) {
        this.supplierNo = value;
    }
    
    /**
     * 获取supplierMobile属性的值。
     * 
     */
    public String getSupplierMobile() {
        return supplierMobile;
    }

    /**
     * 设置supplierMobile属性的值。
     * 
     */
    public void setSupplierMobile(String value) {
        this.supplierMobile = value;
    }

    /**
     * 获取loadingStation属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoadingStation() {
        return loadingStation;
    }

    /**
     * 设置loadingStation属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoadingStation(String value) {
        this.loadingStation = value;
    }

    /**
     * 获取loadingStationCode属性的值。
     * 
     */
    public String getLoadingStationCode() {
        return loadingStationCode;
    }

    /**
     * 设置loadingStationCode属性的值。
     * 
     */
    public void setLoadingStationCode(String value) {
        this.loadingStationCode = value;
    }

    /**
     * 获取operateType属性的值。
     * 
     */
    public int getOperateType() {
        return operateType;
    }

    /**
     * 设置operateType属性的值。
     * 
     */
    public void setOperateType(int value) {
        this.operateType = value;
    }

    /**
     * 获取remark属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置remark属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemark(String value) {
        this.remark = value;
    }

    /**
     * 获取receiveOrg属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiveOrg() {
        return receiveOrg;
    }

    /**
     * 设置receiveOrg属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiveOrg(String value) {
        this.receiveOrg = value;
    }

    /**
     * 获取receiveOrgCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiveOrgCode() {
        return receiveOrgCode;
    }

    /**
     * 设置receiveOrgCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiveOrgCode(String value) {
        this.receiveOrgCode = value;
    }
    /**
     * 获取payMethod属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayMethod() {
        return payMethod;
    }
    
    /**
     * 设置payMethod属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayMethod(String value) {
        this.payMethod = value;
    }

    /**
     * 获取receiveName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiveName() {
        return receiveName;
    }
    
    /**
     * 设置receiveName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiveName(String value) {
        this.receiveName = value;
    }

    /**
     * 获取receiveMobile属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiveMobile() {
        return receiveMobile;
    }
    
    /**
     * 设置receiveMobile属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiveMobile(String value) {
        this.receiveMobile = value;
    }
    
    /**
     * 获取signTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getSignTime() {
        return signTime;
    }
    
    /**
     * 设置signTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignTime(Date value) {
        this.signTime = value;
    }

    /**
     * 获取signState属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignState() {
        return signState;
    }
    
    /**
     * 设置signState属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignState(String value) {
        this.signState = value;
    }
    
    /**
     * 获取signNumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Integer getSignNumber() {
		return signNumber;
	}

    /**
     * 设置signNumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setSignNumber(Integer signNumber) {
		this.signNumber = signNumber;
	}
    
    /**
     * 获取signRemark属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignRemark() {
        return signRemark;
    }
    
    /**
     * 设置signRemark属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignRemark(String value) {
        this.signRemark = value;
    }
    
    /**
     * 获取judgment属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Integer getJudgment() {
        return judgment;
    }
    
    /**
     * 设置judgment属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJudgment(Integer value) {
        this.judgment = value;
    }
}
