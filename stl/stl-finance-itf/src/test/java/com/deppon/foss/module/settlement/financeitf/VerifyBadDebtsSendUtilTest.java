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
 * PROJECT NAME	: stl-finance-itf
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/financeitf/VerifyBadDebtsSendUtilTest.java
 * 
 * FILE NAME        	: VerifyBadDebtsSendUtilTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.financeitf;

import java.io.InputStream;

//import javax.jms.Destination;
//import javax.jms.MessageProducer;
//import javax.jms.Session;
//import javax.jms.TextMessage;

import org.apache.log4j.lf5.util.StreamUtils;
import org.junit.Ignore;

import com.deppon.esb.core.jms.ESBJMSSessionFactory;

public class VerifyBadDebtsSendUtilTest {

	@Ignore
	public static void main(String[] args) throws Exception {
		sendVerifyBadDebtsResult2FOSS();
	}

	private static void sendVerifyBadDebtsResult2FOSS() throws Exception {
		String queueName = "QU_FOSS_REQUEST_COM_IN";
		String backServiceCode = "FOSS_ESB2FOSS_VERIFY_BAD_DEBTS";
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
//		message.setStringProperty("businessId", "5032123499");
//		message.setStringProperty("esbServiceCode",
//				"ESB_FIN_SELF2ESB_VERIFY_BAD_DEBTS");
//		message.setText(body);
//		producer.send(message);
	}

	private static String getCODPayResultText() throws Exception {
		String path = "com/deppon/foss/module/settlement/financeitf/FOSS_VERIFY_BAD_DEBTS_REQUEST.xml";
		InputStream in = ClassLoader.getSystemClassLoader()
				.getResourceAsStream(path);
		String text = new String(StreamUtils.getBytes(in), "UTF-8");
		return text;
	}
}
