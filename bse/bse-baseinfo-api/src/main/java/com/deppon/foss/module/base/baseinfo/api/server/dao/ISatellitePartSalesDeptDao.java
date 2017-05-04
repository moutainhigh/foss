package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SatellitePartSalesDeptEntity;

/**
 * 卫星点部与营业部关系接口
 * @author 130566
 *
 */
public interface ISatellitePartSalesDeptDao {
	/**
	 *<P>添加单挑卫星点部营业部关系<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-25下午3:28:02
	 * @param entity
	 * @return
	 */
	SatellitePartSalesDeptEntity addSatellitePartSales(SatellitePartSalesDeptEntity entity);
	/**
	 *<P>根据ID作废关系信息<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-25下午3:33:35
	 * @param entity
	 * @return
	 */
	int deleteSatellitePartSalesById(SatellitePartSalesDeptEntity entity);
	
	/**
	 *<P>根据条件分页查询实体<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-25下午3:40:30
	 * @param satelliteDeptCode
	 * @param salesDeptcode
	 * @return
	 */
	List<SatellitePartSalesDeptEntity> querySatellitePartSalesList(SatellitePartSalesDeptEntity entity, int start, int limit);
	/**
	 *<P>查询总数<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-26上午11:05:17
	 * @param entity
	 * @return
	 */
	long querySatellitePartSalesCount(SatellitePartSalesDeptEntity entity);
	/**
	 *<P>根据营业部编码查询实体列表<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-25下午3:43:13
	 * @param salesDeptCode
	 * @return
	 */
	List<SatellitePartSalesDeptEntity> querySatellitePartSalesListbySalesCode(String salesDeptCode);
	/**
	 *<P>根据卫星点部编码查询实体<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-25下午3:45:42
	 * @param satelliteDeptCode
	 * @return
	 */
	SatellitePartSalesDeptEntity querySatellitePartSalesbySatelliteCode(String satelliteDeptCode);
	/**
	 * 
	 *<P>根据卫星点部编码或、营业部编码动态作废实体<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-4-1下午2:23:41
	 * @param deleteEntity
	 */
	SatellitePartSalesDeptEntity deleteSatellitePartSales(SatellitePartSalesDeptEntity deleteEntity);
}
