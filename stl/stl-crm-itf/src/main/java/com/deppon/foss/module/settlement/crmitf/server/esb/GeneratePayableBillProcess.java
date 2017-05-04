/**
 * Copyright 2013 STL TEAM
 * PROJECT NAME	: stl-crm-itf
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/crmitf/server/esb/GeneratePayableBillProcess.java
 * FILE NAME        	: GeneratePayableBillProcess.java
 * AUTHOR			: FOSS结算系统开发组
 * HOME PAGE		: http://www.deppon.com
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 */

package com.deppon.foss.module.settlement.crmitf.server.esb;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;

/**
 * 生成理赔、服务补救、退运费应付单.
 *
 * @author 046644-foss-zengbinwen
 * @date 2012-11-20 上午10:19:22
 */
public class GeneratePayableBillProcess implements IProcess {

	/** 日志. */
	private final Logger logger = LogManager.getLogger(getClass());

	/** CRM应付单服务. */
	//private ICRMPayableBillService crmPayableBillService;

	/**
	 * Sets the crm payable bill service.
	 *
	 * @param crmPayableBillService the new crm payable bill service
	 */
	/*public void setCrmPayableBillService(
			ICRMPayableBillService crmPayableBillService) {
		this.crmPayableBillService = crmPayableBillService;
	}*/

	 
	/**
	 * 接收结果，生成理赔应付单.
	 *
	 * @param obj the obj
	 * @return the object
	 * @throws ESBBusinessException the eSB business exception
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-25 下午2:36:37
	 * @see com.deppon.esb.core.process.IProcess#process(java.lang.Object)
	 * update by 231434-foss 2015-12-03  该接口已在不知何时被废，故注释，以免被误导
	 */
	@Override
	public Object process(Object obj) throws ESBBusinessException {
//		try {
//			// 获取参数和用户信息
//			ClaimsPayBillGenerateRequest request = (ClaimsPayBillGenerateRequest) obj;
//			//获取crm用户
//			CurrentInfo currentInfo = SettlementUtil.getCRMCurrentInfo();
//			//记录日志
//			logger.info("生成理赔应付单开始:"+request.getBillNo());
//			// 新增应付单
//			crmPayableBillService.addCRMBillPayable(request, currentInfo);
//			//记录日志
//			logger.info("生成理赔应付单结束:"+request.getBillNo());
//		//异常处理
//		} catch (BusinessException be) {
//			//记录日志
//			logger.error(be.getMessage(), be);
//			throw new ESBBusinessException(be);
//		}catch(Exception e){
//			//记录日志
//			logger.error(e.getMessage(), e);
//			throw new ESBBusinessException(e);
//		}
		return null;
	}

	 
	/**
	 * 异常处理
	 * @param req the req
	 * @return the object
	 * @throws ESBBusinessException the eSB business exception
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-25 下午2:39:03
	 * @see com.deppon.esb.core.process.IProcess#errorHandler(java.lang.Object)
	 */
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		//记录日志
		logger.error("生成理赔、服务补救、退运费应付单出错:" + req);
		return null;
	}

}
