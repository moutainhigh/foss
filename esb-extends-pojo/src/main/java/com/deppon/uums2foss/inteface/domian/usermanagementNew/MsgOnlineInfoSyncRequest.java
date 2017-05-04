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
 * 目前同家居安装供应商的合作仅线下，一线操作量大，没有系统化，无法满足现有业务，需要实现同家居安装供应商系统对接，故家装模块需要将供应商传入的提货信息从DOP推送至FOSS系统；
 * 			
 * 
 * <p>MsgOnlineInfoSyncRequest complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="MsgOnlineInfoSyncRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MsgOnlineList" type="{http://www.deppon.com/esb/inteface/domain/dop}MsgOnlineInfo" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MsgOnlineInfoSyncRequest", propOrder = {
    "msgOnlineList"
})
public class MsgOnlineInfoSyncRequest
    implements Serializable
{
    private final static long serialVersionUID = 11082011L;
    @XmlElement(name = "MsgOnlineList", required = true)
    protected List<MsgOnlineInfo> msgOnlineList;

    /**
     * Gets the value of the msgOnlineList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the msgOnlineList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMsgOnlineList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MsgOnlineInfo }
     * 
     * 
     */
    public List<MsgOnlineInfo> getMsgOnlineList() {
        if (msgOnlineList == null) {
            msgOnlineList = new ArrayList<MsgOnlineInfo>();
        }
        return this.msgOnlineList;
    }
}
