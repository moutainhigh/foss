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
 * <p>QuestionInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="QuestionInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="questionId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="questionEmpcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="questionSystem" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="questionType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="questionDetail" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="questionComFrom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="questionDealEmp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="alternateField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="alternateField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="alternateField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="questionAttachments" type="{http://www.deppon.com/foss/remote/itsm/domain/questionInfo}questionAttachment" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuestionInfo", propOrder = {
    "questionId",
    "questionEmpcode",
    "questionSystem",
    "questionType",
    "questionDetail",
    "questionComFrom",
    "questionDealEmp",
    "alternateField1",
    "alternateField2",
    "alternateField3",
    "questionAttachments"
})
public class QuestionInfo {

    @XmlElement(required = true)
    protected String questionId;
    @XmlElement(required = true)
    protected String questionEmpcode;
    @XmlElement(required = true)
    protected String questionSystem;
    @XmlElement(required = true)
    protected String questionType;
    @XmlElement(required = true)
    protected String questionDetail;
    @XmlElement(required = true)
    protected String questionComFrom;
    protected String questionDealEmp;
    protected String alternateField1;
    protected String alternateField2;
    protected String alternateField3;
    protected List<QuestionAttachment> questionAttachments;

    /**
     * 获取questionId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuestionId() {
        return questionId;
    }

    /**
     * 设置questionId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuestionId(String value) {
        this.questionId = value;
    }

    /**
     * 获取questionEmpcode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuestionEmpcode() {
        return questionEmpcode;
    }

    /**
     * 设置questionEmpcode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuestionEmpcode(String value) {
        this.questionEmpcode = value;
    }

    /**
     * 获取questionSystem属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuestionSystem() {
        return questionSystem;
    }

    /**
     * 设置questionSystem属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuestionSystem(String value) {
        this.questionSystem = value;
    }

    /**
     * 获取questionType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuestionType() {
        return questionType;
    }

    /**
     * 设置questionType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuestionType(String value) {
        this.questionType = value;
    }

    /**
     * 获取questionDetail属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuestionDetail() {
        return questionDetail;
    }

    /**
     * 设置questionDetail属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuestionDetail(String value) {
        this.questionDetail = value;
    }

    /**
     * 获取questionComFrom属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuestionComFrom() {
        return questionComFrom;
    }

    /**
     * 设置questionComFrom属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuestionComFrom(String value) {
        this.questionComFrom = value;
    }

    /**
     * 获取questionDealEmp属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuestionDealEmp() {
        return questionDealEmp;
    }

    /**
     * 设置questionDealEmp属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuestionDealEmp(String value) {
        this.questionDealEmp = value;
    }

    /**
     * 获取alternateField1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlternateField1() {
        return alternateField1;
    }

    /**
     * 设置alternateField1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlternateField1(String value) {
        this.alternateField1 = value;
    }

    /**
     * 获取alternateField2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlternateField2() {
        return alternateField2;
    }

    /**
     * 设置alternateField2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlternateField2(String value) {
        this.alternateField2 = value;
    }

    /**
     * 获取alternateField3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlternateField3() {
        return alternateField3;
    }

    /**
     * 设置alternateField3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlternateField3(String value) {
        this.alternateField3 = value;
    }

    /**
     * Gets the value of the questionAttachments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the questionAttachments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQuestionAttachments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QuestionAttachment }
     * 
     * 
     */
    public List<QuestionAttachment> getQuestionAttachments() {
        if (questionAttachments == null) {
            questionAttachments = new ArrayList<QuestionAttachment>();
        }
        return this.questionAttachments;
    }

	public void setQuestionAttachments(List<QuestionAttachment> questionAttachments) {
		this.questionAttachments = questionAttachments;
	}

}
