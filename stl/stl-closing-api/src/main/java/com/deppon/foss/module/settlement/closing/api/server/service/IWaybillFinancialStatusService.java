package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.WaybillFinancialStatusEntity;

/**
 * 财务完结状态状态服务
 * @author 000123-foss-huangxiaobo
 * @date 2012-12-3 上午11:16:56
 */
public interface IWaybillFinancialStatusService extends IService {

	/**
	 * 批量添加财务完结状态
	 * @author ibm-zhuwei
	 * @date 2012-12-3 上午11:14:43
	 */
	void addByBatch(List<WaybillFinancialStatusEntity> list);

	/**
	 * 批量更新财务完结状态
	 * @author ibm-zhuwei
	 * @date 2012-12-3 上午11:15:41
	 */
	void updateStatusByBatch(List<WaybillFinancialStatusEntity> list);

	/**
	 * 批量删除财务完结状态
	 * @author ibm-zhuwei
	 * @date 2012-12-5 下午2:00:58
	 */
	void deleteByBatch(List<WaybillFinancialStatusEntity> list);

	/**
	 * 通过运单查询财务完结状态
	 * @author ibm-zhuwei
	 * @date 2012-12-5 下午2:36:29
	 */
	List<WaybillFinancialStatusEntity> queryByWaybillNos(List<String> waybillNos);
	
}
