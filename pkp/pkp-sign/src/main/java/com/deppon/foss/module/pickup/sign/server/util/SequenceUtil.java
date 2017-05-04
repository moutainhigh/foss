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
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/util/SequenceUtil.java
 * 
 * FILE NAME        	: SequenceUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.deppon.foss.framework.server.context.WebApplicationContextHolder;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SysNumberEntity;
import com.deppon.foss.module.pickup.sign.server.service.impl.SysNumberService;

/**
 * <p>
 * 获得流水号<br />
 * </p>
 * 
 * @title SequenceUtil.java
 * @package com.deppon.foss.module.pickup.sign.server.util
 * @author ibm-lizhiguo
 * @version 0.1 2012-12-4
 */
public class SequenceUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(SequenceUtil.class);
	/**
	 * 
	 * <p>获得流水号<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-4
	 * @param argKey
	 * @return
	 * int
	 */
	public static synchronized String getNextId(String argKey) {
		int result = 0;
		SysNumberService sysNumberService = (SysNumberService)WebApplicationContextHolder.getWebApplicationContext().getBean("sysNumberService");
		
		Long resultStr = sysNumberService.getNextID(argKey);
		
		//从Spring容器中请求事务管理器，用PlatformTransactionManager  
        //类型的引用指向它  
        PlatformTransactionManager tm =  
               (PlatformTransactionManager)WebApplicationContextHolder.getWebApplicationContext().getBean("transactionManager");
        //TransactionDefinition对象代表着事务的定义，即事务的传播行为，  
        //隔离级别和是否可读等属性。DefaultTransactionDefinition是此  
        //接口的默认实现，给上述属性指定了默认值。如传播行为是PROPAGATION_REQUIRED，  
        //只读为false等（可参见Spring api文档）  
        TransactionDefinition def = new DefaultTransactionDefinition();
        //TransactionStatus对象代表着事务的状态。以下代码根据传入的事务定义  
        //对象返回事务并启动事务  
        TransactionStatus status = (TransactionStatus)tm.getTransaction(def);
        try{
		if (resultStr != null) {
			sysNumberService.updateSysNumber(argKey);
			result = resultStr.intValue();
			result++;
		} else {
			result++;
			SysNumberEntity entity = new SysNumberEntity();
			entity.setSeq(result);
			entity.setDescrip(argKey);
			sysNumberService.insertSysNumber(entity);
		}
        }catch (Exception e) { 
        	LOGGER.error("error", e);
            //回滚事务  
            tm.rollback(status);
         } 
        //提交事务  
        tm.commit(status);
		return String.valueOf(result);
	}
}