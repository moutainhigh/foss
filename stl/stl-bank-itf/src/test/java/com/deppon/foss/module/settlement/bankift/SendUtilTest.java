/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-bank-itf
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/bankift/SendUtilTest.java
 * 
 * FILE NAME        	: SendUtilTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.bankift;

import com.deppon.esb.core.jms.ESBJMSSessionFactory;

import org.apache.log4j.lf5.util.StreamUtils;

import java.io.InputStream;

//import javax.jms.Destination;
//import javax.jms.MessageProducer;
//import javax.jms.Session;
//import javax.jms.TextMessage;

public class SendUtilTest {

	public static void main(String[] args) throws Exception {
		sendCODPayResult2FOSS();
	}

	private static void sendCODPayResult2FOSS() throws Exception {
		String queueName = "QU_FOSS_REQUEST_COM_IN";
		String backServiceCode = "FOSS_ESB2FOSS_RECEIVE_REFUNDSTATE";
		int expPattern = 3; // 单向
		String body = getCODPayResultText();

//		Session session = ESBJMSSessionFactory.getInstance()
//				.getSession("false");
//		Destination destination = session.createQueue(queueName);
//		MessageProducer producer = session.createProducer(destination);
//		TextMessage message = session.createTextMessage();
//		message.setStringProperty("backServiceCode", backServiceCode);
//		message.setIntProperty("exchangePattern", expPattern);
//		message.setStringProperty("version", "0.0.1-SNAPSHOT");
//		message.setStringProperty("businessId", "50321234");
//		message.setStringProperty("esbServiceCode", "ESB_FIN_BANK2ESB_RECEIVE_REFUNDSTATE");
//		message.setText(body);
//		producer.send(message);
	}

	private static String getCODPayResultText() throws Exception {
		String path = "com/deppon/foss/module/settlement/bankitf/FOSS_RECEIVE_REFUNDSTATE.xml";
		InputStream in = ClassLoader.getSystemClassLoader()
				.getResourceAsStream(path);
		String text = new String(StreamUtils.getBytes(in), "UTF-8");

		in.close();

		return text;
	}
}
