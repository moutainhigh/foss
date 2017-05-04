package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.AddWSCWaybillProcessEntity;
import com.deppon.foss.module.pickup.waybill.shared.vo.WSCWaybillProcessVo;
/**
 * 
 * @author 350909    郭倩云
 *主要与数据库表PKP.T_SRV_ADD_ASYN_WAYBILL交互
 */
public interface IWSCWaybillProcessService {
	/**
	 * @author 350909    郭倩云
	 * 将生成财务单据所需要的运单信息和代刷卡数据相关信息插入到数据库表PKP.T_SRV_ADD_ASYN_WAYBILL
	 */
	void addWSCWaybillProcessEntity(String requestType, String jsonString);
	/**
	 * @author 350909    郭倩云
	 * 根据jobId将生成财务单据所需要的运单信息和代刷卡数据相关信息从数据库表PKP.T_SRV_ADD_ASYN_WAYBILL查询出来
	 */
	List<AddWSCWaybillProcessEntity> queryInfoByJobId(String jobId);
	/**
	 * @author 350909    郭倩云
	 * 假如线程执行失败,根据jobId将相关数据的jobId更新成N/A
	 */
	int updateJobIdToNA(WSCWaybillProcessVo wSCWaybillProcessVo);

}
