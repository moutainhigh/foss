/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-sms-itf
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/smsitf/esb/client/SMSValidateResultNotificationProcessTest.java
 * 
 * FILE NAME        	: SMSValidateResultNotificationProcessTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.smsitf.esb.client;

import java.util.Date;

//import javax.jms.Destination;
//import javax.jms.MessageProducer;
//import javax.jms.Session;
//import javax.jms.TextMessage;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.deppon.esb.core.jms.ESBJMSSessionFactory;
import com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity;
import com.deppon.foss.util.UUIDUtils;
/**
 * 用来测试发送"短信信息"到短信平台
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-22 下午1:38:03</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-22 下午1:38:03
 * @since
 * @version
 */
@ContextConfiguration(locations = {
	"classpath*:com/deppon/foss/module/base/smsitf/server/META-INF/springTest.xml",
	"classpath*:com/deppon/foss/module/base/smsitf/server/META-INF/spring.xml",
	"classpath*:com/deppon/foss/module/base/common/server/META-INF/spring.xml"
})
public class SMSValidateResultNotificationProcessTest extends AbstractJUnit4SpringContextTests{

    private static final Logger logger = LoggerFactory.getLogger(SMSValidateResultNotificationProcessTest.class);
    
    @Autowired
    private ISMSSendLogService smsSendLogService;
    
    @Ignore
    @Test
    public void testCallback() {
	logger.info("****************** SMS Start: ESB Monitor Service Start ******************");
        
	SMSSendLogEntity smsSendLog = new SMSSendLogEntity();
	smsSendLog.setMobile("13700000000");//13916380806
	smsSendLog.setContent("此短信是FOSS项目部综合管理系统开发组，短信测试平台测试短信！");
	smsSendLog.setSenddept("FOSS项目部");
        smsSendLog.setSender("正式测试用户");
        smsSendLog.setMsgtype("YWLX66666666666666");
        smsSendLog.setMsgsource("XTLY888888888888888");
        smsSendLog.setUnionId(UUIDUtils.getUUID());
        smsSendLog.setWaybillNo("DP-4689154-KD-0005");
        smsSendLog.setServiceType("1");
        smsSendLog.setSendTime(new Date());
        //测试
	smsSendLogService.sendSMS(smsSendLog);
//	try {
//	    synchronized (smsSendLogService) {
//		smsSendLogService.wait();
//	    }
//	} catch (InterruptedException e) {
//	    logger.error("短信发送结果回调失败", e);
//	}
	logger.info("****************** SMS End: ESB Monitor Service Start ******************");
    }

    @Ignore
    @Test
    public void testCallback2() throws Exception{
	String queueName = "QU_FOSS_REQUEST_COM_IN";
	String backServiceCode = "ESB_FOSS2ESB_VOICEMESSAGE";
	int expPattern = 3; // 单向

//	Session session = ESBJMSSessionFactory.getInstance().getSession("false");
//	Destination destination = session.createQueue(queueName);
//	MessageProducer producer = session.createProducer(destination);
//	TextMessage message = session.createTextMessage();
//	message.setStringProperty("BackServiceCode", backServiceCode);
//	message.setIntProperty("ExchangePattern", expPattern);
//	message.setStringProperty("Version", "0.0.1-SNAPSHOT");
//	message.setStringProperty("BusinessId", "555555555");
//	message.setStringProperty("ESBServiceCode","ESB_FOSS2ESB_VOICEMESSAGE");
//	message.setText("测试内容");
//	producer.send(message);
    }
}
