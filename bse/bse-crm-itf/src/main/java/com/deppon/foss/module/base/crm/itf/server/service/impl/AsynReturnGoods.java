package com.deppon.foss.module.base.crm.itf.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IReturnGoodsRequestEntityService;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.ReturnGoodsRequestEntity;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.ReturnGoodsResponseEntity;

/**
 * @author 何世贵
 * @E-mail: heshigui@deppon.com
 * @date 创建时间：2016-5-20 下午4:18:47
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class AsynReturnGoods implements IProcess {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AsynReturnGoods.class);

	private IReturnGoodsRequestEntityService returnGoodsRequestEntityService;

	/**
	 * 业务互斥锁服务.
	 */
	private IBusinessLockService businessLockService;


	public void setReturnGoodsRequestEntityService(
			IReturnGoodsRequestEntityService returnGoodsRequestEntityService) {
		this.returnGoodsRequestEntityService = returnGoodsRequestEntityService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	@Override
	public Object errorHandler(Object arg0) throws ESBBusinessException {
		return null;
	}

	@Override
	public Object process(Object returnBill) throws ESBBusinessException {
		long s = System.currentTimeMillis();
		ReturnGoodsResponseEntity response = new ReturnGoodsResponseEntity();
		MutexElement mutex =null;
		try {
			LOGGER.info("crm同步返货处理工单returnBill"+returnBill);
			ReturnGoodsRequestEntity request = (ReturnGoodsRequestEntity) returnBill;

			if (request == null) {
				response.setIfSuccess(false);
				response.setErrorMsg("实体类不能为空");
				return response;
			}
			com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity entity = new com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity(
					request);

			mutex = new MutexElement(String.valueOf(entity.getDealnumber()),
					"CRM_RETURN_GOODS_Dealnumber",
					MutexElementType.CRM_RETURN_GOODS_Dealnumber);

			LOGGER.info("crm同步返货处理工单[" + entity.getOriWaybill() + "]["
					+ entity.getDealnumber() + "]至foss");
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutex,
					NumberConstants.ZERO);
			if (result) {
				LOGGER.info("成功加锁：" + mutex.getBusinessNo());
				LOGGER.info("foss异步处理crm上报返货工单[" + entity.getOriWaybill()
						+ "][" + entity.getDealnumber() + "]所耗的时间:"
						+ (System.currentTimeMillis() - s));
				returnGoodsRequestEntityService
						.updateReturnGoodsRequestEntity(entity);
				response.setIfSuccess(true);
			} else {
				LOGGER.info("失败加锁：" + mutex.getBusinessNo());
				response.setIfSuccess(false);
				response.setErrorMsg("加锁失败");
				return response;
			}
			return response;
		} catch (BusinessException e) {
			response.setIfSuccess(false);
			response.setErrorMsg(e.getMessage());
			return response;
		} finally {
			// 解业务锁
			//313353 sonar
			if(null != mutex){
				LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			}
			businessLockService.unlock(mutex);
			if(null != mutex){
				LOGGER.info("完成解锁：" + mutex.getBusinessNo());
			}

		}

	}

}
