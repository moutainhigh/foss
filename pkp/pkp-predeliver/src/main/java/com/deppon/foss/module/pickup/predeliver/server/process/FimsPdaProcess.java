package com.deppon.foss.module.pickup.predeliver.server.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;

public class FimsPdaProcess implements ICallBackProcess {

	protected final static Logger LOG = LoggerFactory.getLogger(FimsPdaProcess.class);
	@Override
	public void callback(Object response) throws ESBException {
		LOG.info("提供给发票系统的异步消息现在已经回复");
	}

	@Override
	public void errorHandler(Object errorResponse) throws ESBException {
		LOG.info("提供给发票系统的异步消息现在存在 异常");
	}

}
