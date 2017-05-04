package com.deppon.pda.bdm.module.foss.accept.server.dao;

import com.deppon.pda.bdm.module.foss.accept.shared.domain.PositionEntity;

/**
 * pdam经纬度数据操作接口
 * @author 245960
 *
 */
public interface IPdamPositionDao {
	/**
	 * 保存经纬度数据
	 */
	public void savePosition(PositionEntity position);
}
