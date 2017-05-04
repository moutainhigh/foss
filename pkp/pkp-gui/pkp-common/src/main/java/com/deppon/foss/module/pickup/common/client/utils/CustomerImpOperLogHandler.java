/**
 *  initial comments.
 */
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.common.client.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.pickup.common.client.dto.CustomerOperaLoggerDto;



/**
 * 客户重点操作日志管理类
 * @author hujinyang
 * @date 2015-12-10 上午08:01:47
 * @version
 */
public class CustomerImpOperLogHandler{
	private static Map<String, CustomerOperaLoggerDto> map = new HashMap<String, CustomerOperaLoggerDto>();
	
	public static CustomerOperaLoggerDto setLogger(String waybillNo){
		if(!StringUtils.isBlank(waybillNo)){
			if(map.containsKey(waybillNo)){
				return map.get(waybillNo);
			}else{
				CustomerOperaLoggerDto  cus = new CustomerOperaLoggerDto();
				cus.setWaybillNo(waybillNo);
				map.put(waybillNo, cus);
				return cus;
			}
		}else{
			String threadId = Thread.currentThread().getId()+"";
			CustomerOperaLoggerDto  cus = new CustomerOperaLoggerDto();
			cus.setWaybillNo(threadId);
			map.put(threadId, cus);
			return cus;
		}
	}

}

