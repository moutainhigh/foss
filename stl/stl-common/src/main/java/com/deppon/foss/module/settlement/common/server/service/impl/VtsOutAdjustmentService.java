package com.deppon.foss.module.settlement.common.server.service.impl;

import com.deppon.foss.module.settlement.common.api.server.dao.IVtsOutAdjustmentDao;
import com.deppon.foss.module.settlement.common.api.server.service.IVtsOutAdjustmentService;
import com.deppon.foss.module.settlement.common.api.shared.domain.VtsOutAdjustmentEntity;

public class VtsOutAdjustmentService implements IVtsOutAdjustmentService{
    //注入dao
	private IVtsOutAdjustmentDao vtsOutAdjustmentDao;
	
	/**
	 * 插入
	 * @author 340403 foss
	 * @createTime 2016年9月20日 下午3:18:41
	 * @param vtsOutAdjustmentEntity
	 * @return
	 */
	public int insert(VtsOutAdjustmentEntity vtsOutAdjustmentEntity){
		return vtsOutAdjustmentDao.insert(vtsOutAdjustmentEntity);
	}

	public IVtsOutAdjustmentDao getVtsOutAdjustmentDao() {
		return vtsOutAdjustmentDao;
	}

	public void setVtsOutAdjustmentDao(IVtsOutAdjustmentDao vtsOutAdjustmentDao) {
		this.vtsOutAdjustmentDao = vtsOutAdjustmentDao;
	}

}
