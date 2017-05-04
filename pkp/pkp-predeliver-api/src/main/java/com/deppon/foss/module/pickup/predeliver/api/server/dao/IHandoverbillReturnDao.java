package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VisibleHandBillReturnEntity;

/**
 * 待排运单退回dao
 * @author 239284
 *
 */
public interface IHandoverbillReturnDao {

	/**
	 * 是否已经存在退回记录
	 * @param 运单号
	 * @return 退回原因ID集合
	 */
	List<String> queryIsWaitWaybillReturn(String waybillNo);
	 
	 /**
	  * 新增待排运单退回记录表
	  * @param entity 退回原因信息
	  */
	void addWaitWaybillReturn(VisibleHandBillReturnEntity entity);
	
	 /**
	  * 更新待排运单退回记录表
	  * @param entity 退回原因信息
	  */
	void updateWaitWaybillReturn(VisibleHandBillReturnEntity entity);
	
	/**
	 * 根据运单号查询退回记录列表
	 * @author foss-sunyanfei
	 * 2015-8-12 下午 15:45:12
	 * @param waybillNo 运单号
	 * @return 退回信息列表
	 */
	List<VisibleHandBillReturnEntity> queryWaitWaybillReturnListByWaybillNo(VisibleHandBillReturnEntity entity);
	
}
