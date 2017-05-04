package com.deppon.foss.esb.oa;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.esb.server.SampleDto;

/**
 * 模拟的针对OA的webservice服务端；
 * ESB的header信息需要通过通过用的interceptor进行操作；
 * 具体的配置请参考spring配置的cxf interceptor；
 * 
 * @author zhengwl
 *
 */
public class OAServiceImpl implements IOAService {
	private static Logger logger = LogManager.getLogger(OAServiceImpl.class);
	
	public String process(SampleDto sample){
		
		logger.info(sample.getCode());
		
		// 处理业务
		
		return sample.getId();
	}
}
