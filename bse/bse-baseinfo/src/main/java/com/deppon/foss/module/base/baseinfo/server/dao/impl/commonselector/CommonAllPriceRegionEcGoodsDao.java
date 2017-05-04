package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAllPriceRegionEcGoodsDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
/**
 *公共组件--首续重价格目的区域
 */
public class CommonAllPriceRegionEcGoodsDao extends  SqlSessionDaoSupport implements ICommonAllPriceRegionEcGoodsDao{
	// 价格区域
	private static final String PRICING_REGION_NAMESPACE = "foss.bse.bse-baseinfo.allpricingRegionEcGoods.";
	/**
	 * 根据条件查询区域信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceRegionEntity> searchRegionByCondition(PriceRegionEntity regionEntity, int start, int limit) {
			// 设置分页条件
			RowBounds rowBounds = new RowBounds(start, limit);
			String address=null;
			if(StringUtils.equals(DictionaryValueConstants.ARRIVE_REGION, regionEntity.getPriceRegionFlag())){
				address = "searchArriveRegionEcGoodsByCondition";
			}	
			List<PriceRegionEntity> regionEntityList = getSqlSession().selectList(PRICING_REGION_NAMESPACE + address, regionEntity,rowBounds);
			return regionEntityList;
	}
	
	/**
	 * 根据条件查询区域信息个数
	 */
	@Override
	public Long countRegionByCondition(PriceRegionEntity regionEntity) {
		String address=null;
		if(StringUtils.equals(DictionaryValueConstants.ARRIVE_REGION, regionEntity.getPriceRegionFlag())){
			address = "countArriveRegionEcGoodsByCondition";
		}
		Object obj = getSqlSession().selectOne(PRICING_REGION_NAMESPACE + address, regionEntity);
		if (null==obj) {
			return 0L;
		}else{
			return (Long) obj;
		}
	}

}
