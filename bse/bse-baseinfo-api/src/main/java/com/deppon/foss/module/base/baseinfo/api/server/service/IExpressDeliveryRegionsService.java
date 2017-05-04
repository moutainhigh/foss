package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryRegionsEntity;

/**
 * 快递派送区域的service接口
 */
public interface IExpressDeliveryRegionsService {
	/**
	 *<P>添加<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午1:39:46
	 * @param entity
	 * @return
	 */
	ExpressDeliveryRegionsEntity addExpressDeliveryRegions(ExpressDeliveryRegionsEntity entity);
	/**
	 *<P>作废<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午1:40:41
	 * @param entity
	 * @return
	 */
	ExpressDeliveryRegionsEntity deleteExpressDeliveryRegions(ExpressDeliveryRegionsEntity entity);
	/**
	 *<P>更新<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午1:41:52
	 * @param entity
	 * @return
	 */
	ExpressDeliveryRegionsEntity updateExpressDeliveryRegions(ExpressDeliveryRegionsEntity entity);
	/**
	 *<P>根据code来获取Name<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午4:02:46
	 * @param code
	 * @return
	 */
	String queryexpressDeliveryRegionsNameByCode(String code);
	/**
	 *<P>根据条件分页查询<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午4:34:56
	 * @param entity
	 * @return
	 */
	List<ExpressDeliveryRegionsEntity> queryExpressDeliveryRegionsEntities(ExpressDeliveryRegionsEntity entity,int start,int limit);
	/**
	 *<P>查询总数<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午4:42:51
	 * @param entity
	 * @return
	 */
	long queryCount(ExpressDeliveryRegionsEntity entity);
	/**
	 *<P>通过编码查询实体<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午4:50:07
	 * @param code
	 * @return
	 */
	ExpressDeliveryRegionsEntity queryExpressDeliveryRegionsByCode(String code);
	/**
	 *<P>查询行政区域的根节点<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午5:48:44
	 * @return
	 */
	List<ExpressDeliveryRegionsEntity> queryRoot();
	/**
	 *<P>根据上级行政区域编码查询<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-18上午8:59:37
	 * @param parentDistrictCode
	 * @return
	 */
	List<ExpressDeliveryRegionsEntity> queryByParentDistrictCode(String parentDistrictCode);
	/**
	 *<P>根据上级code，查询下级子节点的最大编码<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-24上午11:07:28
	 * @param parentDistrictCode
	 * @return
	 */
	String queryMaxCodeChildRegions(String parentDistrictCode);
	/**
	 *<P>根据codes批量作废区域<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-26下午4:22:32
	 * @param codes
	 * @param deleteUser
	 */
	int deleteRegionsByCodes(String[] codes, String deleteUser);
	/**
	 * 特殊方法(用于同步作废行政区域)
	 */
	/**
	 *<P>同步作废上级区域，并且作废子区域<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-28上午10:01:47
	 * @param codes
	 * @param deleteUser
	 * @return
	 */
	int syncDeleteExpressDeliveryRegions(String[] codes,String deleteUser);
	/**
	 * 特殊方法，用于公共选择器，
	 */
	/**
	 *<P>分页查询实体列表，加上界别节点，<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-8上午10:14:30
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	List<ExpressDeliveryRegionsEntity> queryRegionsListAttachDegreeName(ExpressDeliveryRegionsEntity entity,int start,int limit);
	/**
	 * <P>
	 * 新增优化规则（新增或修改时调用）：最低一级TOWN_STREET_AGENCY（镇街道办事处）节点派送属性新增/修改后
	 * 按规则修改对应区县/市级派送属性
	 * <P>
	 * @author :187862-dujunhui
	 * @date : 2014-6-28 上午11:33:26
	 * @param entity
	 * @return
	 */
	ExpressDeliveryRegionsEntity optimizeParentDeliveryNature(ExpressDeliveryRegionsEntity entity);
	/**
	 * <P>
	 * 新增优化规则（在做删除操作时调用，不同于新增和修改时所调用的方法）：最低一级
	 * TOWN_STREET_AGENCY（镇街道办事处）节点被删除后
	 * 按规则修改对应区县/市级派送属性
	 * <P>
	 * @author :187862-dujunhui
	 * @date : 2014-6-30 下午1:54:13
	 * @param entity
	 * @return
	 */
	ExpressDeliveryRegionsEntity optimizeParentDeliveryNatureWhenDel(ExpressDeliveryRegionsEntity entity);
	
	/**
	 * 根据上级区域编码查询
	 * @param entity
	 * @return
	 */
	List<ExpressDeliveryRegionsEntity> queryRegions(ExpressDeliveryRegionsEntity entity);
}
