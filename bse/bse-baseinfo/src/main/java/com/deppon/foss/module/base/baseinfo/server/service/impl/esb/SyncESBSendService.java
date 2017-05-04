package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEsbErrorLoggingDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncESBSendService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EsbErrorLogging;

/** 
 * @author  313353-qiupeng
 * @date 创建时间：2016-8-3 上午9:59:17 
 * @version 1.0 
 **/
public class SyncESBSendService implements ISyncESBSendService{
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SyncESBSendService.class);
	
	@Autowired  
    private TaskExecutor taskExecutor;
	
	@Autowired
	private IEsbErrorLoggingDao esbErrorLoggingDao;

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	/**
	 * 真正发送消息的方法
	 * @param header 消息头
	 * @param request 消息请求
	 */
	@Override
	public void sendESBMessage(AccessHeader header, Object request){
		EsbErrorLogging entity = new EsbErrorLogging();
		entity.setRequestTime(new Date());
		long startTime = System.currentTimeMillis();
		try {
			ESBJMSAccessor.asynReqeust(header, request);
		} catch (ESBException e) {
			entity.setRequestSystem("ESB");
			entity.setMethodName(header.getEsbServiceCode());
			entity.setMethodDesc(header.getBusinessDesc1());
			entity.setErrorMessage(e.getMessage());
			entity.setResponseTime(System.currentTimeMillis()-startTime);
			entity.setCreateTime(new Date());
			esbErrorLoggingDao.addErrorMessage(entity);
			LOGGER.error("ESB消息发送失败：" + e.getMessage());
		}
	}
	
	/**
	 * 创建线程发送消息
	 * @param syncESBSendService 发送esb消息的服务
	 * @param header 消息头
	 * @param request 消息请求
	 */
	@Override
	public void createThreadToSendESB(ISyncESBSendService syncESBSendService, AccessHeader header, Object request){
		SyncESBSendService service = (SyncESBSendService)syncESBSendService;
		SyncESBSendService.SendESBMessageExcutor excutor = 
				service.new SendESBMessageExcutor(header, request);
		taskExecutor.execute(excutor);
	}
	
	/**
	 * 发送消息的线程内部类
	 * @author 313353
	 *
	 */
	public class SendESBMessageExcutor extends Thread{
		private AccessHeader header;
		
		private Object request;
		
		public SendESBMessageExcutor(AccessHeader header, Object request){
			this.header = header;
			this.request = request;
		}
		
		@Override
		public void run(){
			sendESBMessage(header, request);
		}
	}
}
