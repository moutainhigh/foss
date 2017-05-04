package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.OptimalPlatformEntity;

public interface IOptimalPlatformDao {
	/**
	 * 插入
	 * @author huyue
	 * @date 2013-4-10 下午7:00:51
	 */
	public int addOptimalPlatform(OptimalPlatformEntity optimalPlatformEntity);
	
	/**
	 * 插入 selective
	 * @author huyue
	 * @date 2013-4-10 下午7:01:04
	 */
	public int addOptimalPlatformSelective(OptimalPlatformEntity optimalPlatformEntity);
	
	/**
	 * 按照主键查询
	 * @author huyue
	 * @date 2013-4-10 下午7:01:19
	 */
	public OptimalPlatformEntity queryByPrimaryKey(String optimalPlatformId);
	
	/**
	 * 按照车辆明细ID查询批量
	 * @author huyue
	 * @date 2013-4-10 下午7:01:28
	 */
	public List<OptimalPlatformEntity> queryListByTruckTaskDetailId(String truckTaskDetailId);
	
	/**
	 * 按照主键删除
	 * @author huyue
	 * @date 2013-4-10 下午7:04:19
	 */
	public int deleteByPrimaryKey(String optimalPlatformId);
	
	/**
	 * 按照车辆明细ID删除
	 * @author huyue
	 * @date 2013-4-10 下午7:04:31
	 */
	public int deleteByTruckTaskDetailId(String truckTaskDetailId);
}
