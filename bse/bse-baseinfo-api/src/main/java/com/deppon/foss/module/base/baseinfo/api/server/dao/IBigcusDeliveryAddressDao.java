package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigcusDeliveryAddressEntity;

/**
 * 零担大客户派送地址库 Dao
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2016-5-30 上午9:24:36,content:TODO </p>
 * @author 232607 
 * @date 2016-5-30 上午9:24:36
 * @since
 * @version
 */
public interface IBigcusDeliveryAddressDao {
	/**
	 * 
	 *<P>添加快递派送区域实体<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-14上午9:02:22
	 * @param entity
	 * @return
	 */
	BigcusDeliveryAddressEntity addBigcusDeliveryAddress(BigcusDeliveryAddressEntity entity);
	/**
	 * 
	 *<P>根据code删除实体<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-14上午9:03:56
	 * @param entity
	 * @return
	 */
	BigcusDeliveryAddressEntity deleteBigcusDeliveryAddress(BigcusDeliveryAddressEntity entity);
	/**
	 *<P>更新快递派送区域实体<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-14上午9:05:28
	 * @param entity
	 * @return
	 */
	BigcusDeliveryAddressEntity updateBigcusDeliveryAddress(BigcusDeliveryAddressEntity entity);
	
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
	List<BigcusDeliveryAddressEntity> queryBigcusDeliveryAddressList(BigcusDeliveryAddressEntity entity,int start,int limit);
	/**
	 * 
	 *<P>查询总数<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-14上午9:12:44
	 * @param entity
	 * @return
	 */
	long queryBigcusDeliveryAddressCount(BigcusDeliveryAddressEntity entity);
	/**
	 *<P>根据Code精确查询<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-14上午9:11:04
	 * @param Code
	 * @return
	 */
	BigcusDeliveryAddressEntity queryBigcusDeliveryAddressEntityByCode(String code);
	/**
	 *<P>查询根节点<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午5:51:41
	 * @return
	 */
	List<BigcusDeliveryAddressEntity> queryRoot();
	/**
	 *<P>根据上级行政编码查询<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-18上午9:07:19
	 * @param parentDistrictCode
	 * @return
	 */
	List<BigcusDeliveryAddressEntity> queryBigcusDeliveryAddressByParentDistrictCode(
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
	 * @return
	 */
	int deleteRegionsByCodes(String[] codes);
	
	/**
	 * 查询当前区域上级的城市和区县
	 * @param entity
	 * @return
	 */
	List<BigcusDeliveryAddressEntity> queryCityAndCountyRegions(BigcusDeliveryAddressEntity entity);
	
	/**
	 * 查询当前区域编码下级所有的行政区域
	 * @param code
	 * @return
	 */
	List<BigcusDeliveryAddressEntity> queryDwonRegions(String[] code);
	
	/**
	 * 根据编码查询
	 * @param code
	 * @return
	 */
	List<BigcusDeliveryAddressEntity> queryDeleteRegions(String[] code);
	/**
	 * 根据上级编码查询
	 * @param entity
	 * @return
	 */
	List<BigcusDeliveryAddressEntity> queryRegions(BigcusDeliveryAddressEntity entity);
}
