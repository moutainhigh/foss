//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.5-2 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.12.21 时间 03:33:36 PM CST 
//


package com.deppon.uums2foss.inteface.domian.usermanagementNew;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 是否一票多件信息
 * 
 * <p>OneticketornotSyncResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="OneticketornotSyncResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ifSuccess" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="errorMsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OneticketornotSyncResponse", propOrder = {
    "ifSuccess",
    "errorMsg"
})
public class OneticketornotSyncResponse
    implements Serializable
{

    private final static long serialVersionUID = 11082011L;
    @XmlElement(required = true)
    protected String ifSuccess;
    @XmlElement(required = true)
    protected String errorMsg;

    /**
     * 获取ifSuccess属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIfSuccess() {
        return ifSuccess;
    }

    /**
     * 设置ifSuccess属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIfSuccess(String value) {
        this.ifSuccess = value;
    }

    /**
     * 获取errorMsg属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 设置errorMsg属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMsg(String value) {
        this.errorMsg = value;
    }

}
