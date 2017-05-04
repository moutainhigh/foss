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
package com.deppon.foss.module.pickup.oa.server.jms;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.foss.module.pickup.predeliver.server.process.AbandonGoodsApplicationOaResult;



/**
 * 
 * oa审批结果回调接口
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * @author 105089-FOSS-administrator
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class AbandonGoodsOAService implements IProcess {
	
	
	/**
	 * 调用业务方法 审批结果业务逻辑
	 */
	private AbandonGoodsApplicationOaResult abandonGoodsApplicationOaResult;

	
	/**
	 * 处理异常  应该是不需要处理的 因为不可能去改变弃货的工作流状态
	 */
	public Object errorHandler(Object request) throws ESBBusinessException {
		
		//没有逻辑
		return null;
	}
	
	
	/**
	 * 将弃货开运单到大区办公室时查看弃货工作流审批结果
	 */
	@Transactional
	public Object process(Object request) throws ESBBusinessException {
		return abandonGoodsApplicationOaResult.process(request);
	}


	/**
	 * @return the abandonGoodsApplicationOaResult
	 */
	public AbandonGoodsApplicationOaResult getAbandonGoodsApplicationOaResult() {
		return abandonGoodsApplicationOaResult;
	}


	/**
	 * @param abandonGoodsApplicationOaResult the abandonGoodsApplicationOaResult to set
	 */
	public void setAbandonGoodsApplicationOaResult(
			AbandonGoodsApplicationOaResult abandonGoodsApplicationOaResult) {
		this.abandonGoodsApplicationOaResult = abandonGoodsApplicationOaResult;
	}
	
	
	
	

}