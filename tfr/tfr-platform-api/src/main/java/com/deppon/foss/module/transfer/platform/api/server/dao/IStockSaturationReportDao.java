package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.StockSaturationReportEntity;



/**
* @description 仓库饱和度数据监控报表Dao
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年4月24日 下午2:11:08
*/
public interface IStockSaturationReportDao {
	
	/**
	* @description 仓库饱和度数据监控报表查询
	* @param queryDateA
	* @param queryDateB
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月13日 上午9:59:34
	*/
	List<StockSaturationReportEntity> queryStockSaturationReport(String queryDateA,String queryDateB,int start,int limit);
	
	
	/**
	* @description 仓库饱和度数据监控报表查询 的总记录数（即全国外场的总个数，无需参数）
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月14日 上午8:29:42
	*/
	int queryStockSaturationReportCount();
}
