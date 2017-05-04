package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.GoodsDistributionEntity;

/**
 * 货量流动分布dao
 * @author 200978
 * 2015-3-10
 */
public interface IGoodsDistributionDao {
	
	/**
	 * 在（startDate,statisticDate]之间查询转运场货量分布情况 
	 * @Author 200978
	 * 2015-3-10
	 * @param startDate
	 * @param statisticDate
	 * @return
	 */
	List<GoodsDistributionEntity> statisticGoodsDistribution(Date startDate,Date statisticDate);
	
	/**
	 * 保存转运场货量流动分布统计记录
	 * @Author 200978
	 * 2015-3-10
	 * @param goodsDistributionEntity
	 */
	void saveGoodsDistribution(GoodsDistributionEntity goodsDistributionEntity);
	
	/**
	 * 根据当前部门查询 他对应的经营本部
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-24
	 * @param code
	 * @return
	 */
	GoodsDistributionEntity queryOperationDeptCodeByCurrentCode(String code);
	
	/**
	 * 根据经营本部+转运场+查询时间 进行查询转运场货量流动分布  按日查询
	 * @Author 200978
	 * 2015-3-12
	 * @param entity
	 * @return
	 */
	List<GoodsDistributionEntity> queryGoodsDistributionByDay(GoodsDistributionEntity entity);
	
	/**
	 * 根据经营本部+转运场+查询时间 进行查询转运场货量流动分布  按月查询
	 * @Author 200978
	 * 2015-3-12
	 * @param entity
	 * @return
	 */
	List<GoodsDistributionEntity> queryGoodsDistributionByMonth(GoodsDistributionEntity entity);

}
