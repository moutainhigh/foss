/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.ReModifyRouteEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;

/**
 * @author 026123-foss-lifengteng
 *
 */
public interface IWaybillRfcTodoJobService {

	/**
	 *异步生成代办
     * 
	 */
	void prepareSendTodo();
	
	/**
	 *异步生成单个代办
     * 
	 */
	void sendSingleTodo(PendingTodoEntity pendingTodoEntity);
	
	
	/**
	 * 另一个job 定期扫描所有没有生成走货路径的代办 进行补充处理
	 */
	void handlerTodoJob();

	/**
	 * 自动处理单个代办
	 * @param labeledGoodTodoEntity2
	 */
	void handleSingleTodo(LabeledGoodTodoEntity labeledGoodTodoEntity2);

	/**
	 * 单个待办重新入库
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-24 15:45:05
	 * @param labeledGoodTodoEntity
	 */
	void handleSingleRestockTodo(LabeledGoodTodoEntity labeledGoodTodoEntity);

	/**
	 * 单个待办重新入库
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-24 15:45:05
	 * @param labeledGoodTodoEntity
	 */
	void handleRestockTodo();
	
	/**
	 * 单个调整入库
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-24 17:20:39
	 * 调整入库
	 */
	void modifyPathDetailAtInstock();
	
	/**
	 * 单个调整入库
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-24 17:20:39
	 * @param reModifyRouteEntity
	 */
	void modifySinglePathDetailAtInstock(ReModifyRouteEntity reModifyRouteEntity);

	/**
	 * 根据运单和库存进行库存和走货路径的调整
	 * 1、运单在库存中，直接进行走货路径的调整，若走货路径不存在，则新增一条调整失败的记录在pkp.t_srv_re_modify_route表中，此时的failreason不为N
	 * 2、运单不在库存中，每个流水号新增一条记录pkp.t_srv_re_modify_route，由后台Job定期执行
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-26 14:51:13
	 * @param transportPath
	 * @param waybill 
	 */
	void updateWaybillPathDetail(TransportPathEntity transportPath, WaybillEntity waybill);
	
	/**
	 * 根据JobId更新一定数据的数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-11 15:18:31
	 */
	int updateReModifyRouteByJobId(String jobId, int updateNum);
}
