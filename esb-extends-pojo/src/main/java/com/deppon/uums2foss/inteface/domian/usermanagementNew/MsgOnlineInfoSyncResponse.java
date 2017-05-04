//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.5-2 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.09.09 时间 04:50:07 PM CST 
//

package com.deppon.uums2foss.inteface.domian.usermanagementNew;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 返回结果
 * 
 * <p>MsgOnlineInfoSyncResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="MsgOnlineInfoSyncResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="successCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="failCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="detail" type="{http://www.deppon.com/esb/inteface/domain/dop}ProcessDetail" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MsgOnlineInfoSyncResponse", propOrder = {
    "successCount",
    "failCount",
    "detail"
})
public class MsgOnlineInfoSyncResponse
    implements Serializable
{
    private final static long serialVersionUID = 11082011L;
    protected int successCount;
    protected int failCount;
    @XmlElement(required = true)
    protected List<ProcessDetail> detail;

    /**
     * 获取successCount属性的值。
     * 
     */
    public int getSuccessCount() {
        return successCount;
    }

    /**
     * 设置successCount属性的值。
     * 
     */
    public void setSuccessCount(int value) {
        this.successCount = value;
    }

    /**
     * 获取failCount属性的值。
     * 
     */
    public int getFailCount() {
        return failCount;
    }

    /**
     * 设置failCount属性的值。
     * 
     */
    public void setFailCount(int value) {
        this.failCount = value;
    }

    /**
     * Gets the value of the detail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the detail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDetail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProcessDetail }
     * 
     * 
     */
    public List<ProcessDetail> getDetail() {
        if (detail == null) {
            detail = new ArrayList<ProcessDetail>();
        }
        return this.detail;
    }

    /**
     * 设置detail属性的值。
     * 
     */
    public void setDetail(List<ProcessDetail> detail) {
        this.detail = detail;
    }
}
