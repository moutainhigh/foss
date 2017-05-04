package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPrintStarDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPrintStarEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressPrintStarException;
import com.deppon.foss.module.base.baseinfo.server.util.LineUtils;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * TODO(实现ExpressPrintStar的Service接口)
 * @author 187862-dujunhui
 * @date 2014-5-21 下午5:49:23
 * @since
 * @version
 */
public class ExpressPrintStarService implements IExpressPrintStarService {
	
	private IExpressPrintStarDao expressPrintStarDao;
	private IProductService productService;
	
	//private IProductService productService;

	/**
	 * @param expressPrintStarDao the expressPrintStarDao to set
	 */
	public void setExpressPrintStarDao(IExpressPrintStarDao expressPrintStarDao) {
		this.expressPrintStarDao = expressPrintStarDao;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	/** 
	 * <p>TODO(添加)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午5:49:23
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService#addExpressPrintStar(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPrintStarEntity)
	 */
	@Override
	public ExpressPrintStarEntity addExpressPrintStar(
			ExpressPrintStarEntity entity) {
		// TODO Auto-generated method stub
		if (entity == null) {
		    return null;
		}
		
		// 如果目的站编码有值，则需要校验同一目的站的库区不能同时有卡普和混装
		if (StringUtils.isNotBlank(entity.getArriveRegionCode())) {
		    List<ExpressPrintStarEntity> list = queryExpressPrintStarListByArriveRegion(entity.getOrganizationCode(), entity.getArriveRegionCode());
			if(CollectionUtils.isNotEmpty(list)){
				for(ExpressPrintStarEntity queryEntity:list){
					if (queryEntity != null
							&& (StringUtils.equals(queryEntity.getGoodsAreaType(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_COMMON)
									|| StringUtils.equals(queryEntity.getGoodsAreaType(), entity.getGoodsAreaType())
									||StringUtils.equals(entity.getGoodsAreaType(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_COMMON))) {
						throw new ExpressPrintStarException(ExpressPrintStarException.EXPRESSPRINTSTAR_TYPE_EXIST);
		    }
				}
			}
		}
		
		// 同一外场下,库区编码不能重复
		ExpressPrintStarEntity queryCodeEntity = queryExpressPrintStarByCode(entity.getOrganizationCode(), entity.getGoodsAreaCode());
//		if (queryCodeEntity != null) {
//		    throw new ExpressPrintStarException(ExpressPrintStarException.EXPRESSPRINTSTAR_CODE_EXIST);
//		}
		
		// 如果是唯一性货区，需要判断不能重复录入
		if (isUniqueGoodsAreaType(entity.getGoodsAreaType())) {
		    List<ExpressPrintStarEntity> list = querySimpleExpressPrintStarListByType(entity.getOrganizationCode(), entity.getGoodsAreaType());
		    if (CollectionUtils.isNotEmpty(list)) {
			    throw new ExpressPrintStarException(ExpressPrintStarException.EXPRESSPRINTSTAR_TYPE_UNIQUE_EXIST);
		    }
		}
		queryCodeEntity = expressPrintStarDao.addExpressPrintStar(entity);
		return queryCodeEntity;
	}
	
	/**
     * 
     * <p>判断是否唯一性（一个外场只能有一个的）库区</p> 
     * @author 187862
     * @date 2014-5-21 下午5:49:23
     * @param goodsAreaType
     * @return
     * @see
     */
    private boolean isUniqueGoodsAreaType(String goodsAreaType){
	return StringUtils.equals(goodsAreaType, DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION)
		|| StringUtils.equals(goodsAreaType, DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE)
		|| StringUtils.equals(goodsAreaType, DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING)
		|| StringUtils.equals(goodsAreaType, DictionaryValueConstants.BSE_GOODSAREA_TYPE_OTHER)
		|| StringUtils.equals(goodsAreaType, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION)
		|| StringUtils.equals(goodsAreaType, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
    }

	/** 
	 * <p>TODO(删除)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午5:49:23
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService#deleteExpressPrintStar(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPrintStarEntity)
	 */
	@Override
	public ExpressPrintStarEntity deleteExpressPrintStar(
			ExpressPrintStarEntity entity) {
		// TODO Auto-generated method stub
		if (entity == null) {
		    return null;
		}
		ExpressPrintStarEntity delEntity = expressPrintStarDao.deleteExpressPrintStar(entity);
		return delEntity;
	}

	/** 
	 * <p>TODO(更新)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午5:49:23
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService#updateExpressPrintStar(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPrintStarEntity)
	 */
	@Override
	public ExpressPrintStarEntity updateExpressPrintStar(
			ExpressPrintStarEntity entity) {
		// TODO Auto-generated method stub
		if (entity == null) {
		    return null;
		}

		// 如果目的站编码有值，则需要校验同一目的站的库区不能同时有卡普和混装
		if (StringUtils.isNotBlank(entity.getArriveRegionCode())) {
		    // 找出现有该目的站的库区列表
		    List<ExpressPrintStarEntity> list = queryExpressPrintStarListByArriveRegion(entity.getOrganizationCode(), entity.getArriveRegionCode());
		    if (CollectionUtils.isNotEmpty(list)) {
			for (ExpressPrintStarEntity queryEntityByRegion : list) {
			    // 如果是该库区自己就不用判断了
			    if (queryEntityByRegion == null || StringUtils.equals(entity.getVirtualCode(), queryEntityByRegion.getVirtualCode())) {
				continue;
			    }
			    // 如果两个中有一个是混装库区，或者这两个库区类型相同，则触发违规
			    if (StringUtils.equals(entity.getGoodsAreaType(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_COMMON)
				    || StringUtils.equals(queryEntityByRegion.getGoodsAreaType(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_COMMON)
				    || StringUtils.equals(queryEntityByRegion.getGoodsAreaType(), entity.getGoodsAreaType())) {
				throw new ExpressPrintStarException(ExpressPrintStarException.EXPRESSPRINTSTAR_TYPE_EXIST);
			    }
			}
		    }
		}
		// 同一外场下,库区编码不能重复
		ExpressPrintStarEntity queryEntityByCode = queryExpressPrintStarByCode(entity.getOrganizationCode(), entity.getGoodsAreaCode());
//		if (queryEntityByCode != null && !StringUtils.equals(entity.getVirtualCode(), queryEntityByCode.getVirtualCode())) {
//		    throw new ExpressPrintStarException(ExpressPrintStarException.EXPRESSPRINTSTAR_CODE_EXIST);
//		}
		
		// 如果是唯一性货区，需要判断不能重复录入
		if (isUniqueGoodsAreaType(entity.getGoodsAreaType())) {
		    List<ExpressPrintStarEntity> list = querySimpleExpressPrintStarListByType(entity.getOrganizationCode(), entity.getGoodsAreaType());
		    if (CollectionUtils.isNotEmpty(list) && !StringUtils.equals(list.get(0).getVirtualCode(), entity.getVirtualCode())) {
			throw new ExpressPrintStarException(ExpressPrintStarException.EXPRESSPRINTSTAR_TYPE_UNIQUE_EXIST);
		    }
		}
		queryEntityByCode = expressPrintStarDao.updateExpressPrintStar(entity);
		return queryEntityByCode;
	}
		
	/**
	 * 
	 * <p>按目的站查找库区</p> 
	 * @author 187862
	 * @date 2014-5-21 下午6:11:24
	 * @param organizationCode
	 * @param arriveRegionCode
	 * @return
	 * @see
	 */
	private List<ExpressPrintStarEntity> queryExpressPrintStarListByArriveRegion(String organizationCode, String arriveRegionCode) {
    List<ExpressPrintStarEntity> list = new ArrayList<ExpressPrintStarEntity>();
	if(StringUtils.isBlank(organizationCode) || StringUtils.isBlank(arriveRegionCode)) {
		return list;
		}
	//查找数据库
	if (CollectionUtils.isEmpty(list)) {
		ExpressPrintStarEntity entity = new ExpressPrintStarEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setOrganizationCode(organizationCode);
		entity.setArriveRegionCode(arriveRegionCode);
		// 取出所有符合的库区，包括卡货，普货和混装库区等.
		list = queryExpressPrintStarByCondition(entity, 0, Integer.MAX_VALUE);
		}
		return list;
	}

	/** 
	 * <p>TODO(根据虚拟编号查询)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午5:49:24
	 * @param virtualCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService#queryExpressPrintStarByVirtualCode(java.lang.String)
	 */
	@Override
	public ExpressPrintStarEntity queryExpressPrintStarByVirtualCode(
			String virtualCode) {
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(virtualCode)) {
		    return null;
		}
		
		ExpressPrintStarEntity entity = expressPrintStarDao.queryExpressPrintStarByVirtualCode(virtualCode);
		return entity;
	}

	/** 
	 * <p>TODO(根据条件分页查询)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午5:49:24
	 * @param entity
	 * @param start
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService#queryExpressPrintStarByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPrintStarEntity, int, int)
	 */
	@Override
	public List<ExpressPrintStarEntity> queryExpressPrintStarByCondition(
			ExpressPrintStarEntity entity, int start, int limit) {
		// TODO Auto-generated method stub
		return expressPrintStarDao.queryExpressPrintStarByCondition(entity, start, limit);
	}

	/** 
	 * <p>TODO(根据条件查询，参数含用户名和部门名)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午5:49:24
	 * @param entity
	 * @param start
	 * @param limit
	 * @param userCode
	 * @param deptCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService#queryExpressPrintStarByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPrintStarEntity, int, int, java.lang.String, java.lang.String)
	 */
	@Override
	public List<ExpressPrintStarEntity> queryExpressPrintStarByCondition(
			ExpressPrintStarEntity entity, int start, int limit,
			String userCode, String deptCode) {
		// TODO Auto-generated method stub
		return expressPrintStarDao.queryExpressPrintStarByCondition(entity, start, limit);
	}

	/** 
	 * <p>TODO(根据条件查询返回结果数)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午5:49:24
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService#countExpressPrintStarByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPrintStarEntity)
	 */
	@Override
	public long countExpressPrintStarByCondition(ExpressPrintStarEntity entity) {
		// TODO Auto-generated method stub
		return expressPrintStarDao.countExpressPrintStarByCondition(entity);
	}

	/** 
	 * <p>TODO(根据条件查询，参数含用户名和部门名)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午5:49:24
	 * @param entity
	 * @param userCode
	 * @param deptCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService#countExpressPrintStarByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPrintStarEntity, java.lang.String, java.lang.String)
	 */
	@Override
	public long countExpressPrintStarByCondition(ExpressPrintStarEntity entity,
			String userCode, String deptCode) {
		// TODO Auto-generated method stub
		return expressPrintStarDao.countExpressPrintStarByCondition(entity);
	}

	/** 
	 * <p>TODO(根据组织编号查询)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午5:49:24
	 * @param organizationCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService#queryExpressPrintStarListByOrganizationCode(java.lang.String)
	 */
	@Override
	public List<ExpressPrintStarEntity> queryExpressPrintStarListByOrganizationCode(
			String organizationCode) {
		// TODO Auto-generated method stub
		return querySimpleExpressPrintStarListByOrganizationCode(organizationCode);
	}

	/** 
	 * <p>TODO(根据组织编号查询)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午5:49:24
	 * @param organizationCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService#querySimpleExpressPrintStarListByOrganizationCode(java.lang.String)
	 */
	@Override
	public List<ExpressPrintStarEntity> querySimpleExpressPrintStarListByOrganizationCode(
			String organizationCode) {
		// TODO Auto-generated method stub
		List<ExpressPrintStarEntity> resultList = new ArrayList<ExpressPrintStarEntity>();
		if (StringUtils.isBlank(organizationCode)) {
		    return resultList;
		}
		
		resultList = expressPrintStarDao.queryExpressPrintStarListByOrganizationCode(organizationCode, null);
		return resultList;
	}

	/** 
	 * <p>TODO(根据外场编码和库区编码取库区名称)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午5:49:24
	 * @param organizationCode
	 * @param code
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService#queryNameByCode(java.lang.String, java.lang.String)
	 */
	@Override
	public String queryNameByCode(String organizationCode, String code) {
		// TODO Auto-generated method stub
		ExpressPrintStarEntity entity = queryExpressPrintStarByCode(organizationCode, code);
		if (entity == null) {
		    return null;
		}
		return entity.getGoodsAreaName();
	}

	/** 
	 * <p>TODO(根据外场编码和库区编码查询)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午5:49:24
	 * @param organizationCode
	 * @param code
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService#queryExpressPrintStarByCode(java.lang.String, java.lang.String)
	 */
	@Override
	public ExpressPrintStarEntity queryExpressPrintStarByCode(
			String organizationCode, String code) {
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(organizationCode) || StringUtils.isBlank(code)) {
		    return null;
		}
		ExpressPrintStarEntity c = new ExpressPrintStarEntity();
		c.setActive(FossConstants.ACTIVE);
		c.setOrganizationCode(organizationCode);
		c.setGoodsAreaCode(code);
		List<ExpressPrintStarEntity> list = expressPrintStarDao.queryExpressPrintStarByCondition(c, 0, 1);
		ExpressPrintStarEntity entity= CollectionUtils.isEmpty(list) ? null : list.get(0);

		return entity;
	}

	/** 
	 * <p>TODO(根据外场部门编码和目的站部门编码查询货区编码)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午5:49:24
	 * @param organizationCode
	 * @param arriveRegionCode
	 * @return
	 * @deprecated 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService#queryCodeByArriveRegionCode(java.lang.String, java.lang.String)
	 */
	@Override
	public String queryCodeByArriveRegionCode(String organizationCode,
			String arriveRegionCode) {
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(organizationCode) || StringUtils.isBlank(arriveRegionCode)) {
		    return null;
		}
		ExpressPrintStarEntity c = new ExpressPrintStarEntity();
		c.setActive(FossConstants.ACTIVE);
		c.setOrganizationCode(organizationCode);
		c.setArriveRegionCode(arriveRegionCode);
		// 只取1笔记录
		List<ExpressPrintStarEntity> list = queryExpressPrintStarByCondition(c, 0, NumberConstants.NUMERAL_ONE);
		if (CollectionUtils.isEmpty(list)) {
		    return null;
		}
		return list.get(0).getGoodsAreaCode();
	}

	/** 
	 * <p>TODO(根据库区类型查询)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午5:49:24
	 * @param organizationCode
	 * @param goodsAreaType
	 * DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION 异常货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE  贵重物品货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING   包装货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_OTHER     偏线货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION   驻地派送货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_COMMON    混装货区
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService#queryExpressPrintStarListByType(java.lang.String, java.lang.String)
	 */
	@Override
	public List<ExpressPrintStarEntity> queryExpressPrintStarListByType(
			String organizationCode, String goodsAreaType) {
		// TODO Auto-generated method stub
		return querySimpleExpressPrintStarListByType(organizationCode, goodsAreaType);
	}

	/** 
	 * <p>TODO(根据库区类型查询)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午5:49:24
	 * @param organizationCode
	 * @param goodsAreaType
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService#querySimpleExpressPrintStarListByType(java.lang.String, java.lang.String)
	 */
	@Override
	public List<ExpressPrintStarEntity> querySimpleExpressPrintStarListByType(
			String organizationCode, String goodsAreaType) {
		// TODO Auto-generated method stub
		List<ExpressPrintStarEntity> resultList = new ArrayList<ExpressPrintStarEntity> ();
		if (StringUtils.isBlank(organizationCode)) {
		    return resultList;
		}
		return expressPrintStarDao.queryExpressPrintStarListByOrganizationCode(organizationCode, goodsAreaType);
	}

	/** 
	 * <p>TODO(根据虚拟编码和修改用户删除)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午5:49:24
	 * @param virtualCodes
	 * @param modifyUser
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService#deleteExpressPrintStars(java.util.List, java.lang.String)
	 */
	@Override
	public int deleteExpressPrintStars(List<String> virtualCodes,
			String modifyUser) {
		// TODO Auto-generated method stub
		if (CollectionUtils.isEmpty(virtualCodes) || StringUtils.isBlank(modifyUser)) {
		    return FossConstants.FAILURE;
		}
		int result = expressPrintStarDao.deleteExpressPrintStars(virtualCodes, modifyUser);
		
		return result;
	}

	/** 
	 * <p>TODO(根据目的站查询编码)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午5:49:24
	 * @param organizationCode
	 * @param arriveRegionCode
	 * @param productCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService#queryCodeByArriveRegionCode(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String queryCodeByArriveRegionCode(String organizationCode,
			String arriveRegionCode, String productCode) {
		// TODO Auto-generated method stub
		ExpressPrintStarEntity entity = null;
		
		//boolean flag=PricingConstants.directDetermineIsExpressByProductCode(productCode);
		boolean flag=productService.onlineDetermineIsExpressByProductCode(productCode, new Date());
    	if(flag){
    		entity = this.queryExpressPrintStarByTransCenterCode(organizationCode, productCode);
    	}else{
    		 entity = queryExpressPrintStarByArriveRegionCode(organizationCode, arriveRegionCode);
    	}
	    return entity == null ? null : entity.getGoodsAreaCode();
	}

	/** 
	 * <p>TODO(根据目的站查询)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午5:49:24
	 * @param organizationCode
	 * @param arriveRegionCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService#queryExpressPrintStarByArriveRegionCode(java.lang.String, java.lang.String)
	 */
	@Override
	public ExpressPrintStarEntity queryExpressPrintStarByArriveRegionCode(
			String organizationCode, String arriveRegionCode) {
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(organizationCode) || StringUtils.isBlank(arriveRegionCode)) {
		    return null;
		}
		// 查找符合目的站条件的库区列表
		List<ExpressPrintStarEntity> list = queryExpressPrintStarListByArriveRegion(organizationCode, arriveRegionCode);

		// 如果没有就返回null
		if (CollectionUtils.isEmpty(list)) {
		    return null;
		}
		// 如果找不到，则返回第一个
		return list.get(0);
	}

	/** 
	 * <p>TODO(根据虚拟编号查询实体)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午5:49:24
	 * @param virtualCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService#searchExpressPrintStarEntityByVirtualCode(java.lang.String)
	 */
	@Override
	public ExpressPrintStarEntity searchExpressPrintStarEntityByVirtualCode(
			String virtualCode) {
		// TODO Auto-generated method stub
		//非空验证
		if(StringUtils.isBlank(virtualCode)){
			return null;
		}
		return expressPrintStarDao.queryExpressPrintStarByVirtualCode(virtualCode);
	}

	/** 
	 * <p>TODO(根据中转中心编码查询实体)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午5:49:24
	 * @param orgCode
	 * @param productCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService#queryExpressPrintStarByTransCenterCode(java.lang.String, java.lang.String)
	 */
	@Override
	public ExpressPrintStarEntity queryExpressPrintStarByTransCenterCode(
			String orgCode, String productCode) {
		// TODO Auto-generated method stub
		//根据外场编码查询货区
		List<ExpressPrintStarEntity> list = expressPrintStarDao
				.queryExpressPrintStarByTransCenterCode(orgCode);

		// 如果还是没有就返回null
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		// 如果没有productCode，则给第一个符合的库区
		if (StringUtils.isBlank(productCode)) {
			return list.get(0);
		}
		ProductEntity product = new ProductEntity();
		ExpressPrintStarEntity fastEntity = null, 
								commonEntiy = null, 
								nomalEntity = null;
		//boolean flag=PricingConstants.directDetermineIsExpressByProductCode(productCode);
		boolean flag=productService.onlineDetermineIsExpressByProductCode(productCode, new Date());
		// 如果是卡航类型的，找混装或卡货库区
		//313353 sonar
		if (LineUtils.isFast(product.getPriority())) {
			return this.sonarSplitOne(commonEntiy, fastEntity, flag, list);
		}
		
		// 如果是普货类型的，找混装或普货货区
		//313353 sonar
		if (LineUtils.isCommon(product.getPriority())) {
			return this.sonarSplitTwo(commonEntiy, nomalEntity, flag, list);
		}
				
		// 如果找不到，则返回第一个
		return list.get(0);
	}

	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private ExpressPrintStarEntity sonarSplitOne(
			ExpressPrintStarEntity commonEntiy,
			ExpressPrintStarEntity fastEntity, boolean flag,
			List<ExpressPrintStarEntity> list) {
		for (ExpressPrintStarEntity entity : list) {
			if (flag) {
				if (StringUtils.equals(entity.getGoodsAreaType(),
						DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXPRESS)) {// 快递货区
					return entity;
				} else {
					if (StringUtils.equals(entity.getGoodsAreaType(),
							DictionaryValueConstants.BSE_GOODSAREA_TYPE_FAST)) {
						fastEntity = entity;
					} else if (StringUtils.equals(entity.getGoodsAreaType(),
							DictionaryValueConstants.BSE_GOODSAREA_TYPE_COMMON)) {
						commonEntiy = entity;
					}
				}

			}
		}
		if (null != fastEntity) {
			return fastEntity;
		} else if (null != commonEntiy) {
			return commonEntiy;
		} else {
			return null;
		}
	}

	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private ExpressPrintStarEntity sonarSplitTwo(
			ExpressPrintStarEntity commonEntiy,
			ExpressPrintStarEntity nomalEntity, boolean flag,
			List<ExpressPrintStarEntity> list) {
		for (ExpressPrintStarEntity entity : list) {
			if (flag) {
				if (StringUtils.equals(entity.getGoodsAreaType(),
						DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXPRESS)) {// 快递货区
					return entity;
				} else {
					if (StringUtils.equals(entity.getGoodsAreaType(),
							DictionaryValueConstants.BSE_GOODSAREA_TYPE_COMMON)) {
						commonEntiy = entity;
					} else if (StringUtils.equals(entity.getGoodsAreaType(),
							DictionaryValueConstants.BSE_GOODSAREA_TYPE_NORMAL)) {
						nomalEntity = entity;
					}
				}

			}
		}
		if (null != commonEntiy) {
			return commonEntiy;
		} else if (null != nomalEntity) {
			return nomalEntity;
		} else {
			return null;
		}
	}
	
}
