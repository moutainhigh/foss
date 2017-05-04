package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryRegionsEntity;

/**
 * 快递派送区域的Dao接口
 * @author 130566
 *
 */
public interface IExpressDeliveryRegionsDao {
	/**
	 * 
	 *<P>添加快递派送区域实体<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-14上午9:02:22
	 * @param entity
	 * @return
	 */
	ExpressDeliveryRegionsEntity addExpressDeliveryRegions(ExpressDeliveryRegionsEntity entity);
	/**
	 * 
	 *<P>根据code删除实体<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-14上午9:03:56
	 * @param entity
	 * @return
	 */
	ExpressDeliveryRegionsEntity deleteExpressDeliveryRegions(ExpressDeliveryRegionsEntity entity);
	/**
	 *<P>更新快递派送区域实体<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-14上午9:05:28
	 * @param entity
	 * @return
	 */
	ExpressDeliveryRegionsEntity updateExpressDeliveryRegions(ExpressDeliveryRegionsEntity entity);
	
	/**
	 * 以下是查询操作
	 */
	/**
	 * 
	 *<P>根据Entity动态查询<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-14上午9:10:03
	 * @param entity
	 * @param start
	 * @param end
	 * @return
	 */
	List<ExpressDeliveryRegionsEntity> queryExpressDeliveryRegionsList(ExpressDeliveryRegionsEntity entity,int start,int limit);
	/**
	 * 
	 *<P>查询总数<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-14上午9:12:44
	 * @param entity
	 * @return
	 */
	long queryExpressDeliveryRegionsCount(ExpressDeliveryRegionsEntity entity);
	/**
	 *<P>根据Code精确查询<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-14上午9:11:04
	 * @param Code
	 * @return
	 */
	ExpressDeliveryRegionsEntity queryExpressDeliveryRegionsEntityByCode(String code);
	/**
	 *<P>查询根节点<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午5:51:41
	 * @return
	 */
	List<ExpressDeliveryRegionsEntity> queryRoot();
	/**
	 *<P>根据上级行政编码查询<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-18上午9:07:19
	 * @param parentDistrictCode
	 * @return
	 */
	List<ExpressDeliveryRegionsEntity> queryExpressDeliveryRegionsByParentDistrictCode(
			String parentDistrictCode);
	/**
	 *<P>根据上级编码查询，下级中子节点最大编码<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-24上午11:10:40
	 * @param parentDistrictCode
	 * @return
	 */
	String queryMaxCodeChildRegions(String parentDistrictCode);
	/**
	 *<P>执行批量作废操作<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-26下午4:40:14
	 * @param codes
	 * @param deleteUser
	 * @return
	 */
	int deleteRegionsByCodes(String[] codes, String deleteUser);
	
	/**
	 * 查询当前区域上级的城市和区县
	 * @param entity
	 * @return
	 */
	List<ExpressDeliveryRegionsEntity> queryCityAndCountyRegions(ExpressDeliveryRegionsEntity entity);
	
	/**
	 * 查询当前区域编码下级所有的行政区域
	 * @param code
	 * @return
	 */
	List<ExpressDeliveryRegionsEntity> queryDwonRegions(String[] code);
	
	/**
	 * 根据编码查询
	 * @param code
	 * @return
	 */
	List<ExpressDeliveryRegionsEntity> queryDeleteRegions(String[] code);
	/**
	 * 根据上级编码查询
	 * @param entity
	 * @return
	 */
	List<ExpressDeliveryRegionsEntity> queryRegions(ExpressDeliveryRegionsEntity entity);
}
