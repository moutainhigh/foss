package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillChangeMsgEntity;

/**
 * 业务完结消息
 * @author 000123-foss-huangxiaobo
 * @date 2012-12-3 上午10:53:56
 */
public interface IWaybillChangeMsgService extends IService { 
	
	/**
	 * 添加业务完结消息
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-12-3 上午11:06:49
	 * @param entity
	 */
	void addChangeMsg(WaybillChangeMsgEntity entity);
	
	/**
	 * 批量删除业务完结消息
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-12-3 上午11:07:15
	 * @param list 业务完结消息集合
	 */
	void deleteChangeMsgByBatch(List<WaybillChangeMsgEntity> list);

	/**
	 * 查询待处理业务完结消息
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-12-3 上午11:07:39
	 * @param beginTime 开始时间
	 * @param endTime   截止时间
	 * @param limit     限制最大行
	 * @return
	 */
	List<WaybillChangeMsgEntity> queryChangeMsg(Date beginTime, Date endTime, int limit);
	
	/**
	 * 根据运单号查询财务变更信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-5 下午3:47:51
	 * @param waybillNo 运单号
	 * @return
	 */
	List<WaybillChangeMsgEntity> queryChangeMsgByWaybillNO(String waybillNo);
	
}
