package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.IStockSaturationReportDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockSaturationReportEntity;

public class StockSaturationReportDaoImpl extends iBatis3DaoImpl implements IStockSaturationReportDao {
	/** mapper 文件命名空间*/
	public static final String NAME_SPACE = "foss.platform.stock.saturation.report.";


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
	@SuppressWarnings("unchecked")
	@Override
	public List<StockSaturationReportEntity> queryStockSaturationReport(
			String queryDateA, String queryDateB,int start,int limit) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("querydatea", queryDateA);
		map.put("querydateb", queryDateB);
		if(start<=0 && limit <=0){
			return this.getSqlSession().selectList(NAME_SPACE+"queryStockSaturationReport",map);
		}else{
			RowBounds rowBounds = new RowBounds(start, limit);
			return this.getSqlSession().selectList(NAME_SPACE+"queryStockSaturationReport",map,rowBounds);
		}
	}

	/**
	* @description 仓库饱和度数据监控报表查询 的总记录数（即全国外场的总个数，无需参数）
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月14日 上午8:29:42
	*/
	@Override
	public int queryStockSaturationReportCount() {
		return (Integer) this.getSqlSession().selectOne(NAME_SPACE+"queryStockSaturationReportCount");
	}
	
	

}
