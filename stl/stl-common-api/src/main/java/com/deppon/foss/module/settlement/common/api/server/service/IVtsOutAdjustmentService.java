package com.deppon.foss.module.settlement.common.api.server.service;

import com.deppon.foss.module.settlement.common.api.shared.domain.VtsOutAdjustmentEntity;

/**
 * 整车费用调整service
 * @author 340403 foss
 * @createTime 2016年9月20日 下午3:18:20
 */
public interface IVtsOutAdjustmentService {
	/**
	 * 插入
	 * @author 340403foss
	 * @createTime 2016年9月20日 下午3:18:41
	 * @param vtsOutAdjustmentEntity
	 * @return
	 */
	public int insert(VtsOutAdjustmentEntity vtsOutAdjustmentEntity);
}
