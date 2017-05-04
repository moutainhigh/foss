//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.5-2 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2016.09.07 时间 09:30:21 AM CST 
//


package com.deppon.esb.inteface.domain.dop;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RuleDetailEntity complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="RuleDetailEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="receiveAddress" type="{http://www.deppon.com/esb/inteface/domain/dop}AddressEntity"/>
 *         &lt;element name="sendAddress" type="{http://www.deppon.com/esb/inteface/domain/dop}AddressEntity"/>
 *         &lt;element name="textValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RuleDetailEntity", propOrder = {
    "receiveAddress",
    "sendAddress",
    "textValue",
    "numValue"
})
public class RuleDetailEntity
    implements Serializable
{

    private final static long serialVersionUID = 11082011L;
    @XmlElement(required = true)
    protected AddressEntity receiveAddress;
    @XmlElement(required = true)
    protected AddressEntity sendAddress;
    @XmlElement(required = true)
    protected String textValue;
    @XmlElement(required = true)
    protected String numValue;

    /**
     * 获取receiveAddress属性的值。
     * 
     * @return
     *     possible object is
     *     {@link AddressEntity }
     *     
     */
    public AddressEntity getReceiveAddress() {
        return receiveAddress;
    }

    /**
     * 设置receiveAddress属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link AddressEntity }
     *     
     */
    public void setReceiveAddress(AddressEntity value) {
        this.receiveAddress = value;
    }

    /**
     * 获取sendAddress属性的值。
     * 
     * @return
     *     possible object is
     *     {@link AddressEntity }
     *     
     */
    public AddressEntity getSendAddress() {
        return sendAddress;
    }

    /**
     * 设置sendAddress属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link AddressEntity }
     *     
     */
    public void setSendAddress(AddressEntity value) {
        this.sendAddress = value;
    }

    /**
     * 获取textValue属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextValue() {
        return textValue;
    }

    /**
     * 设置textValue属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextValue(String value) {
        this.textValue = value;
    }

    /**
     * 获取numValue属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumValue() {
        return numValue;
    }

    /**
     * 设置numValue属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumValue(String value) {
        this.numValue = value;
    }

}
