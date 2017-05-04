package com.deppon.foss.module.transfer.platform.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockSaturationReportEntity;


/**
* @description 库存饱和度数据监控报表Service
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年5月13日 下午3:12:15
*/
public interface IStockSaturationReportService extends IService {
	
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
	List<StockSaturationReportEntity> queryStockSaturationReport(StockSaturationReportEntity pojo,int start,int limit);
	
	/**
	* @description 仓库饱和度数据监控报表查询 的总记录数（即全国外场的总个数，无需参数）
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月14日 上午8:29:42
	*/
	long queryStockSaturationReportCount();
	
	
	/**
	* @description 导出
	* @param pojo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月14日 上午8:48:58
	*/
	InputStream  exportStockSaturationReport(StockSaturationReportEntity pojo);
}
