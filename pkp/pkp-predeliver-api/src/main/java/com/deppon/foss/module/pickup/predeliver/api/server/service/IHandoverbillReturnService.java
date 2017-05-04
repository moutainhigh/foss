package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VisibleHandBillReturnEntity;

/**
 * 待排运单退回service
 * @author 239284
 *
 */
public interface IHandoverbillReturnService extends IService {

	
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
	
}
