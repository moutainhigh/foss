package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;


/**
 * 异常出库接口
 * @author foss-qiaolifeng
 * @date 2012-11-8 上午9:39:45
 */
public interface IOutWarehouseExceptionService extends IService{

	/**
	 * 异常出库红冲操作
	 * @author foss-qiaolifeng
	 * @date 2012-11-8 上午9:44:08
	 *  param waybillNo 运单号,
	 *        expType 异常类型
	 */
	void outWarehouseException(String waybillNo,String expType,CurrentInfo cInfo);
}
