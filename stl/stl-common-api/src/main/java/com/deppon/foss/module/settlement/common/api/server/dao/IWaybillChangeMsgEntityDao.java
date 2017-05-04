package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillChangeMsgEntity;


/**
 * 财务变更消息DAO
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-3 上午11:38:16
 * @since
 * @version
 */
public interface IWaybillChangeMsgEntityDao {
	
	/**
	 * 新加完结消息
	 * @author ibm-zhuwei
	 * @date 2012-12-24 下午8:16:49
	 * @param item 财务变更消息实体
	 * @return
	 */
	int addChangeMsg(WaybillChangeMsgEntity item);
	
	/**
	 * 批量删除完结消息
	 * @author ibm-zhuwei
	 * @date 2012-12-24 下午8:17:10
	 * @param list 财务变更消息列表
	 * @return
	 */
	int deleteByBatch(List<WaybillChangeMsgEntity> list);

	/**
	 * 查询待处理业务完结消息
	 * @author ibm-zhuwei
	 * @date 2012-12-24 下午8:17:46
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @param limit 行限制
	 * @return
	 */
	List<WaybillChangeMsgEntity> queryChangeMsg(Date beginTime, Date endTime, int limit);
	
	/**
	 * 根据运单号查询财务变更信息
	 * @author ibm-zhuwei
	 * @date 2012-12-24 下午8:18:23
	 * @param waybillNo 运单号
	 * @return
	 */
	List<WaybillChangeMsgEntity> queryChangeMsgByWaybillNO(String waybillNo);

  
}
