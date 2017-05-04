package com.deppon.foss.module.transfer.unload.api.server.dao;

import java.util.List;


import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAOrderSerialNoDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAOrderTaskBillEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAOrderTaskEntity;

public interface IPDAOrderTaskDao {

	/**
	 * pda刷新点单任务
	 * @author 272681-foss-chenlei
	 * @date 2016-01-25
	 */
	List<PDAOrderTaskEntity> refreshOrderTaskByPDA(String orgCode,String operator);

	/**
	 * pda任务更新,根据点单任务编号 获得点单任务明细以及总重量，总体积，总票数，总件数
	 * @author 272681-foss-chenlei
	 * @date 2016-01-25
	 */
	List<PDAOrderTaskBillEntity> queryOrderTaskDetailByOrderTaskNo(
			String orderTaskNo);

	/**
	 * pda获取流水明细,根据点单任务明细id
	 * @author 272681-foss-chenlei
	 * @date 2016-01-25
	 */
	List<PDAOrderSerialNoDetailEntity> queryPDAOrderTaskSerialNoListByBillNo(
			String id);

}
