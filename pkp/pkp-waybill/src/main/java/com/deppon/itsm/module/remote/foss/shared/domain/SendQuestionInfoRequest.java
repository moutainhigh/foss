//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.5-2 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2013.11.13 时间 10:35:56 AM CST 
//


package com.deppon.itsm.module.remote.foss.shared.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * FOSS将问题消息传送给IT服务台
 * 
 * <p>SendQuestionInfoRequest complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SendQuestionInfoRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="QuestionInfo" type="{http://www.deppon.com/foss/remote/itsm/domain/questionInfo}QuestionInfo" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SendQuestionInfoRequest", propOrder = {
    "questionInfo"
})
public class SendQuestionInfoRequest {

    @XmlElement(name = "QuestionInfo", required = true)
    protected List<QuestionInfo> questionInfo;

    public void setQuestionInfo(List<QuestionInfo> questionInfo) {
		this.questionInfo = questionInfo;
	}

	/**
     * Gets the value of the questionInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the questionInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQuestionInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QuestionInfo }
     * 
     * 
     */
    public List<QuestionInfo> getQuestionInfo() {
        if (questionInfo == null) {
            questionInfo = new ArrayList<QuestionInfo>();
        }
        return this.questionInfo;
    }

}
