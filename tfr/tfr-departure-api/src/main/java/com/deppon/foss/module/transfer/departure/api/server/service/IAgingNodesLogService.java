package com.deppon.foss.module.transfer.departure.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.departure.api.shared.domain.AgingNodesLogEntity;
import com.deppon.foss.module.transfer.departure.api.shared.vo.AgingNodesLogVo;

public interface IAgingNodesLogService extends IService{
	/**
	 * 查询时效节点修改日志
	 * @author foss-heyongdong
	 * @data 2014年4月11日 14:42:09
	 * @param billNo
	 * @return list
	 */
	List<AgingNodesLogEntity> queryAgingNodesLog(String billNo);
	
	/**
	 * 修改时效节点并保存修改日志
	 * @author foss-heyongdong
	 * @data 2014年4月21日 13:44:29
	 * @param agingNodesLogVo
	 * @return String
	 */
	String saveAgingNodesLog(AgingNodesLogVo agingNodesLogVo);
	
	/**
	 * 更具单号查询同一个车辆任务下的相关联单号
	 * @author foss-heyongdong
	 * @date 2014年4月29日 09:19:20
	 * @param billNo
	 * @return String
	 */
	String queryRelationbillNos(String billNo);
}
