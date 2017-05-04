package com.deppon.foss.module.transfer.platform.api.server.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.shared.domain.GoodsDistributionEntity;

/**
 * 转运场货量流动分布
 * @author 200978
 * 2015-3-10
 */
public interface IGoodsDistributionService extends IService{

	/**
	 * 统计转运场货量流动分布   到达货量，出发货量，实际流入量，实际流出货量，货台库存货量
	 * @Author 200978
	 * 2015-3-10
	 * @param staDate
	 */
	void statisticGoodsDistribution(Date statisticDate);
	
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
	
	/**
	 * 查询给定部门code所属外场
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param code
	 * @return
	 */
	Map<String, String> queryParentTfrCtrCode(String code);
	
	/**
	 * 查询给定部门code所对应的经营本部
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param code
	 * @return
	 */
	Map<String, String> queryOperationDeptCode(String code);
	
	/**
	 * 生成导出文件名称
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param fileName
	 * @return
	 * @throws TfrBusinessException
	 */
	String encodeFileName(String fileName) throws TfrBusinessException;
	
	/**
	 * 按日 转运场货量流动分布 导出 
	 * @Author 200978
	 * 2015-3-13
	 * @param goodsDistributionEntity
	 * @return
	 * @throws TfrBusinessException
	 */
	InputStream goodsDistributionByDayExport(GoodsDistributionEntity goodsDistributionEntity) throws TfrBusinessException;
	
	/**
	 * 按月均 转运场货量流动分布 导出 
	 * @Author 200978
	 * 2015-3-13
	 * @param goodsDistributionEntity
	 * @return
	 * @throws TfrBusinessException
	 */
	InputStream goodsDistributionByMonthExport(GoodsDistributionEntity goodsDistributionEntity) throws TfrBusinessException;
	
}
