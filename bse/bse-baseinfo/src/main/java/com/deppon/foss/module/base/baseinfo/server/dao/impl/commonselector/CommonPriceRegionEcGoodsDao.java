package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPriceRegionEcGoodsDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.util.define.FossConstants;
/**
 * 公共查询组件--首续重价格区域查询dao实现.
 */
public class CommonPriceRegionEcGoodsDao  extends SqlSessionDaoSupport
implements ICommonPriceRegionEcGoodsDao{
	// 价格区域
	private static final String PRICING_REGION_NAMESPACE = "foss.bse.bse-baseinfo.pricingRegionEcGoods.";
	// 根据条件查询区域
	private static final String SEARCH_REGION_EC_GOODS = "searchRegionEcGoodsByCondition";
	// 根据条件查询区域个数
	private static final String COUNT_REGION_EC_GOODS = "countRegionEcGoodsByCondition";
	
	/**
	 * 根据条件查询大票价格区域.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceRegionEntity> searchRegionEcGoodsByCondition(PriceRegionEntity regionEntity, int start, int limit) {
			RowBounds rowBounds = new RowBounds(start, limit);
			String address=null;
			if(StringUtils.equals(FossConstants.YES, regionEntity.getAirPriceFlag())){
				address = "searchAirRegionByCondition";
			}else{
				address = SEARCH_REGION_EC_GOODS;
			}
			List<PriceRegionEntity> regionEntityList = getSqlSession().selectList(
					PRICING_REGION_NAMESPACE + address, regionEntity,
					rowBounds);
			return regionEntityList;
	}
	
	/**
	 * 查询总条数.
	 */
	@Override
	public Long countRegionEcGoodsByCondition(PriceRegionEntity regionEntity) {
		String address=null;
		if(StringUtils.equals(FossConstants.YES, regionEntity.getAirPriceFlag())){
			address = "countAirRegionByCondition";
		}else{
			address = COUNT_REGION_EC_GOODS;
		} 
		Object obj = getSqlSession().selectOne(
				PRICING_REGION_NAMESPACE + address, regionEntity);
		if (null==obj) {
			return 0L;
		}else{
			return (Long) obj;
		}
	}

}
