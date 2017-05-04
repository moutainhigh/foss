//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.5-2 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2013.11.13 时间 10:35:56 AM CST 
//


package com.deppon.itsm.module.remote.foss.shared.domain;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.deppon.foss.remote.itsm.domain.questioninfo package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SendQuestionInfoRequest_QNAME = new QName("http://www.deppon.com/foss/remote/itsm/domain/questionInfo", "SendQuestionInfoRequest");
    private final static QName _SendQuestionInfoResponse_QNAME = new QName("http://www.deppon.com/foss/remote/itsm/domain/questionInfo", "SendQuestionInfoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.deppon.foss.remote.itsm.domain.questioninfo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SendQuestionInfoResponse }
     * 
     */
    public SendQuestionInfoResponse createSendQuestionInfoResponse() {
        return new SendQuestionInfoResponse();
    }

    /**
     * Create an instance of {@link SendQuestionInfoRequest }
     * 
     */
    public SendQuestionInfoRequest createSendQuestionInfoRequest() {
        return new SendQuestionInfoRequest();
    }

    /**
     * Create an instance of {@link SendQuestionInfoProcessResult }
     * 
     */
    public SendQuestionInfoProcessResult createSendQuestionInfoProcessResult() {
        return new SendQuestionInfoProcessResult();
    }

    /**
     * Create an instance of {@link QuestionAttachment }
     * 
     */
    public QuestionAttachment createQuestionAttachment() {
        return new QuestionAttachment();
    }

    /**
     * Create an instance of {@link QuestionInfo }
     * 
     */
    public QuestionInfo createQuestionInfo() {
        return new QuestionInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendQuestionInfoRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.deppon.com/foss/remote/itsm/domain/questionInfo", name = "SendQuestionInfoRequest")
    public JAXBElement<SendQuestionInfoRequest> createSendQuestionInfoRequest(SendQuestionInfoRequest value) {
        return new JAXBElement<SendQuestionInfoRequest>(_SendQuestionInfoRequest_QNAME, SendQuestionInfoRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendQuestionInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.deppon.com/foss/remote/itsm/domain/questionInfo", name = "SendQuestionInfoResponse")
    public JAXBElement<SendQuestionInfoResponse> createSendQuestionInfoResponse(SendQuestionInfoResponse value) {
        return new JAXBElement<SendQuestionInfoResponse>(_SendQuestionInfoResponse_QNAME, SendQuestionInfoResponse.class, null, value);
    }

}
