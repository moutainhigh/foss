package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.commons.lang.StringUtils;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAllPriceReginDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;

public class CommonAllPriceRegionDao extends  SqlSessionDaoSupport implements
ICommonAllPriceReginDao {
	// 价格区域
	/** The Constant PRICING_REGION_NAMESPACE. */
	private static final String PRICING_REGION_NAMESPACE = "foss.bse.bse-baseinfo.allpricingRegion.";
	// 根据条件查询区域
	/** The Constant SEARCH_REGION. */
	private static final String SEARCH_REGION = "searchRegionByCondition";
	// 根据条件查询区域个数
	/** The Constant COUNT_REGION. */
	private static final String COUNT_REGION = "countRegionByCondition";

	/**
	 * 根据条件查询价格区域.
	 *
	 * @param regionEntity the region entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author lifanghong
	 * @date 2013-08-21 上午10:32:53
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPriceRegionDao#searchRegionByCondition

(com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity,
	 * int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceRegionEntity> searchRegionByCondition(
			PriceRegionEntity regionEntity, int start, int limit) {
		// 设置分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		String address=null;
		//根据区域类型分辨空运区域、增值区域、到达区域、否则默认查询出发区域
		if(StringUtils.equals(PricingConstants.PRICING_REGION_AIR, regionEntity.getPriceRegionFlag())){
			address = "searchAirRegionByCondition";
		}else if(StringUtils.equals(DictionaryValueConstants.VALUEADD_REGION, regionEntity.getPriceRegionFlag())){
			address = "searchValueAddRegionByCondition";
		}else if(StringUtils.equals(DictionaryValueConstants.ARRIVE_REGION, regionEntity.getPriceRegionFlag())){
			address = "searchArriveRegionByCondition";
		}else{
			address = SEARCH_REGION; 
		}
		List<PriceRegionEntity> regionEntityList = getSqlSession().selectList(
				PRICING_REGION_NAMESPACE + address, regionEntity,
				rowBounds);
		return regionEntityList;
	}

	/**
	 * 查询总条数.
	 *
	 * @param regionEntity the region entity
	 * @return the long
	 * @author lifanghong
	 * @date 2013-08-21 上午10:32:53
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPriceRegionDao#countRegionByCondition

(com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity)
	 */
	@Override
	public Long countRegionByCondition(PriceRegionEntity regionEntity) {
		String address=null;
		
		//根据区域类型分辨空运区域、增值区域、到达区域、否则默认查询出发区域
		if(StringUtils.equals(PricingConstants.PRICING_REGION_AIR, regionEntity.getPriceRegionFlag())){
			address = "countAirRegionByCondition";
		}else if(StringUtils.equals(DictionaryValueConstants.VALUEADD_REGION, regionEntity.getPriceRegionFlag())){
			address = "countValueAddRegionByCondition";
		}else if(StringUtils.equals(DictionaryValueConstants.ARRIVE_REGION, regionEntity.getPriceRegionFlag())){
			address = "countArriveRegionByCondition";
		}else{
			address = COUNT_REGION; 
		}
		Object obj = getSqlSession().selectOne(PRICING_REGION_NAMESPACE + address, regionEntity);
		if (null==obj) {
			return 0L;
		}else{
			return (Long) obj;
		}
	}}
