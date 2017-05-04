package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.WaybillFinancialStatusEntity;


/**
 * 财务完结-运单财务状态实体DAO
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-3 上午11:39:26
 * @since
 * @version
 */
public interface IWaybillFinancialStatusEntityDao {

	/**
	 * 批量添加财务完结状态
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-12-3 上午11:14:43
	 */
	void addByBatch(List<WaybillFinancialStatusEntity> list);
	
	/**
	 * 批量更新财务完结状态
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-12-3 上午11:15:41
	 */
	void updateStatusByBatch(List<WaybillFinancialStatusEntity> list);

	/**
	 * 批量删除财务完结状态
	 * @author ibm-zhuwei
	 * @date 2012-12-5 下午2:00:58
	 */
	int deleteByBatch(List<WaybillFinancialStatusEntity> list);
	
	/**
	 * 通过运单查询财务完结状态
	 * @author ibm-zhuwei
	 * @date 2012-12-5 下午2:36:29
	 */
	List<WaybillFinancialStatusEntity> queryByWaybillNos(List<String> waybillNos);
	
}
