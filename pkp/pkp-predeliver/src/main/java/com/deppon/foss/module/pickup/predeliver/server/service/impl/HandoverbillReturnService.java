package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.server.dao.IHandoverbillReturnDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IHandoverbillReturnService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VisibleHandBillReturnEntity;

/**
 * 待排运单退回service实现
 * @author 239284
 *
 */

public class HandoverbillReturnService implements IHandoverbillReturnService {

	private IHandoverbillReturnDao  handoverbillReturnDao;
	
	
	/**
	 * 是否已经存在退回记录
	 * @param 运单号
	 * @return 退回原因ID集合
	 */
	public  List<String> queryIsWaitWaybillReturn(String waybillNo) {
		return handoverbillReturnDao.queryIsWaitWaybillReturn(waybillNo);
	}

	 /**
	  * 新增待排运单退回记录表
	  * @param entity 退回原因信息
	  */
	public void addWaitWaybillReturn(VisibleHandBillReturnEntity entity) {
		handoverbillReturnDao.addWaitWaybillReturn(entity);
	}

	 /**
	  * 更新待排运单退回记录表
	  * @param entity 退回原因信息
	  */
	public void updateWaitWaybillReturn(VisibleHandBillReturnEntity entity) {
		handoverbillReturnDao.updateWaitWaybillReturn(entity);
	}

	
	public void setHandoverbillReturnDao(
			IHandoverbillReturnDao handoverbillReturnDao) {
		this.handoverbillReturnDao = handoverbillReturnDao;
	}
	
}
