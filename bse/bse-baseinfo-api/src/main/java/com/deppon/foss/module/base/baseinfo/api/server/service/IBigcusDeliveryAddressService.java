package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigcusDeliveryAddressEntity;


/**
 * 零担大客户派送地址库 Service
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2016-5-30 上午9:25:01,content:TODO </p>
 * @author 232607 
 * @date 2016-5-30 上午9:25:01
 * @since
 * @version
 */
public interface IBigcusDeliveryAddressService {
	/**
	 *<P>添加<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午1:39:46
	 * @param entity
	 * @return
	 */
	BigcusDeliveryAddressEntity addBigcusFromAdRegion(AdministrativeRegionsEntity entity);
	/**
	 *<P>更新<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午1:41:52
	 * @param entity
	 * @return
	 */
	BigcusDeliveryAddressEntity updateBigcusDeliveryAddress(BigcusDeliveryAddressEntity entity);
	/**
	 *<P>根据code来获取Name<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午4:02:46
	 * @param code
	 * @return
	 */
	String querybigcusDeliveryAddressNameByCode(String code);
	/**
	 *<P>根据条件分页查询<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午4:34:56
	 * @param entity
	 * @return
	 */
	List<BigcusDeliveryAddressEntity> queryBigcusDeliveryAddressEntities(BigcusDeliveryAddressEntity entity,int start,int limit);
	/**
	 *<P>查询总数<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午4:42:51
	 * @param entity
	 * @return
	 */
	long queryCount(BigcusDeliveryAddressEntity entity);
	/**
	 *<P>通过编码查询实体<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午4:50:07
	 * @param code
	 * @return
	 */
	BigcusDeliveryAddressEntity queryBigcusDeliveryAddressByCode(String code);
	/**
	 *<P>查询行政区域的根节点<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午5:48:44
	 * @return
	 */
	List<BigcusDeliveryAddressEntity> queryRoot();
	/**
	 *<P>根据上级行政区域编码查询<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-18上午8:59:37
	 * @param parentDistrictCode
	 * @return
	 */
	List<BigcusDeliveryAddressEntity> queryByParentDistrictCode(String parentDistrictCode);
	/**
	 *<P>根据上级code，查询下级子节点的最大编码<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-24上午11:07:28
	 * @param parentDistrictCode
	 * @return
	 */
	String queryMaxCodeChildRegions(String parentDistrictCode);
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
	List<BigcusDeliveryAddressEntity> queryRegionsListAttachDegreeName(BigcusDeliveryAddressEntity entity,int start,int limit);
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
	BigcusDeliveryAddressEntity optimizeParentDeliveryNature(BigcusDeliveryAddressEntity entity);

	

	/**
	 * <p>修改行政区域，连带改零担大客户派送地址库基本字段</p> 
	 * @author 232607 
	 * @date 2016-5-28 上午10:40:42
	 * @param adEntity
	 * @return
	 * @see
	 */
	BigcusDeliveryAddressEntity updateBigcusFromAdRegion(AdministrativeRegionsEntity adEntity);
	int deleteBigcusFromAdRegion(String[] codes);
}
