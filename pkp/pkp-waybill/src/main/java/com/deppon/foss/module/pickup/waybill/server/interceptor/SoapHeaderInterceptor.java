/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/interceptor/SoapHeaderInterceptor.java
 * 
 * FILE NAME        	: SoapHeaderInterceptor.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.interceptor;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * Soap消息拦截，修改Soap消息头信息
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-11-15 上午11:19:33,content:TODO </p>
 * @author foss-sunrui
 * @date 2012-11-15 上午11:19:33
 * @since
 * @version
 */
public class SoapHeaderInterceptor extends AbstractSoapInterceptor {
	/**
	 * 日志
	 */
    private static final Logger logger = Logger.getLogger(SoapHeaderInterceptor.class);
    /**
     * esb 接口 uri
     */
    /**
     * esb 的service code
     */
    private String serviceCode = "";
    /**
     * 构造方法
     */
    public SoapHeaderInterceptor() {
    	super(Phase.WRITE);
    }
    /**
     * 构造方法
     */
    public SoapHeaderInterceptor(String serviceCode) {
    	super(Phase.WRITE);
    	this.serviceCode = serviceCode;
    }
    
    /**
     * 处理信息
     */
    public void handleMessage(SoapMessage message) throws Fault {
    	logger.info("==================SoapMessage =" + message);
    	QName qname = new QName("RequestSOAPHeader");//qname
    	Document doc = DOMUtils.createDocument();//生成xml文件对象
    	// version
    	Element version = doc.createElement("version");//生成version 节点
    	version.setTextContent("1");//设置节点的值为1
    	// businessId
    	Element businessId = doc.createElement("businessId");//生成businessId 节点
    	businessId.setTextContent("ORDER");//设置节点的值为ORDER
    	// requestId
    	Element requestId = doc.createElement("requestId");//生成requestId 节点
    	requestId.setTextContent(UUIDUtils.getUUID());//设置节点的值为uuid
    	//uuid
    	Element sourceSystem = doc.createElement("sourceSystem");//生成sourceSystem 节点
    	sourceSystem.setTextContent("FOSS");//设置节点的值为FOSS
    	
    	Element targetSystem = doc.createElement("targetSystem");//生成targetSystem 节点
    	targetSystem.setTextContent("CRM");//设置节点的值为CRM
    	
    	Element esbServiceCode = doc.createElement("esbServiceCode");//生成esbServiceCode 节点
    	esbServiceCode.setTextContent(serviceCode);//设置节点的值为service_Code
    	
    	Element messageFormat = doc.createElement("messageFormat");//生成messageFormat 节点
    	messageFormat.setTextContent("SOAP");//设置节点的值为SOAP
    	
    	
    	//Element root = doc.createElement("soap:Header"); 
    	Element root = doc.createElementNS(nameURI, "esbHeader");//生成esbHeader 节点
    	root.appendChild(version);//将子节点加入节点
    	root.appendChild(businessId);//将子节点加入节点
    	root.appendChild(requestId);//将子节点加入节点
    	root.appendChild(sourceSystem);//将子节点加入节点
    	root.appendChild(targetSystem);//将子节点加入节点
    	root.appendChild(esbServiceCode);//将子节点加入节点
    	root.appendChild(messageFormat);//将子节点加入节点
    	
    	SoapHeader head = new SoapHeader(qname, root);//创建header
    	List<Header> headers = message.getHeaders();//得到heads列表
    	headers.add(head);//加入head
    	logger.info(">>>>>header<<<<<<<");
    }
    private static String nameURI = "http://www.deppon.com/esb/header";
}