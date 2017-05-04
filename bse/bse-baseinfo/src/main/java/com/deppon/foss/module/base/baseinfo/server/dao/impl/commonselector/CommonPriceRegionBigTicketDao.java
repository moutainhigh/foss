package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPriceRegionBigTicketDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.util.define.FossConstants;
/**
 * 公共查询组件--大票价格区域查询dao实现.
 *
 * @author shenweihua
 * @date 2014-07-3 上午10:31:00
 */
public class CommonPriceRegionBigTicketDao  extends SqlSessionDaoSupport
implements ICommonPriceRegionBigTicketDao{
	// 价格区域
	/** The Constant PRICING_REGION_NAMESPACE. */
	private static final String PRICING_REGION_NAMESPACE = "foss.bse.bse-baseinfo.pricingRegionBigTicket.";
	// 根据条件查询区域
	/** The Constant SEARCH_REGION. */
	private static final String SEARCH_REGION_BIG_TICKET = "searchRegionBigTicketByCondition";
	// 根据条件查询区域个数
	/** The Constant COUNT_REGION. */
	private static final String COUNT_REGION_BIG_TICKET = "countRegionBigTicketByCondition";
	
	/**
	 * 根据条件查询大票价格区域.
	 *
	 * @param regionEntity the region entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author shenweihua
	 * @date 2014-07-3 上午10:32:53
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceRegionEntity> searchRegionBigTicketByCondition(
			PriceRegionEntity regionEntity, int start, int limit) {
		// 设置分页条件
			RowBounds rowBounds = new RowBounds(start, limit);
			String address=null;
			if(StringUtils.equals(FossConstants.YES, regionEntity.getAirPriceFlag())){
				address = "searchAirRegionByCondition";
			}else{
				address = SEARCH_REGION_BIG_TICKET;
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
	 * @author shenweihua
	 * @date 2014-07-3 上午10:32:53
	 */
	@Override
	public Long countRegionBigTicketByCondition(PriceRegionEntity regionEntity) {
		String address=null;
		if(StringUtils.equals(FossConstants.YES, regionEntity.getAirPriceFlag())){
			address = "countAirRegionByCondition";
		}else{
			address = COUNT_REGION_BIG_TICKET;
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
