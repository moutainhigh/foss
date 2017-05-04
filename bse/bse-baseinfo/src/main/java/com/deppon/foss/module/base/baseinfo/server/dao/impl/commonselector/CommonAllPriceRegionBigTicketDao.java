package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAllPriceRegionBigTicketDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
/**
 *公共组件--大票价格目的区域
 * @author shenweihua
 * @date 2014-07-4 上午9:27:10
 */
public class CommonAllPriceRegionBigTicketDao extends  SqlSessionDaoSupport implements ICommonAllPriceRegionBigTicketDao{
	// 价格区域
	/** The Constant PRICING_REGION_NAMESPACE. */
	private static final String PRICING_REGION_NAMESPACE = "foss.bse.bse-baseinfo.allpricingRegionBigTicket.";
	/**
	 * .
	 * <p>
	 * 根据条件查询区域信息<br/>
	 * （分页） 方法名：searchRegionBigTicketByCondition
	 * </p>
	 * 
	 * @param regionEntity
	 *            查询条件
	 * @param start
	 *            其实查询位置
	 * @param limit
	 *            每页几条
	 * @author shenweihua
	 * @时间 2014-07-4
	 * @since JDK1.6
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceRegionEntity> searchRegionByCondition(
			PriceRegionEntity regionEntity, int start, int limit) {
			// 设置分页条件
			RowBounds rowBounds = new RowBounds(start, limit);
			String address=null;
			if(StringUtils.equals(DictionaryValueConstants.ARRIVE_REGION, regionEntity.getPriceRegionFlag())){
				address = "searchArriveRegionBigTicketByCondition";
			}	
			List<PriceRegionEntity> regionEntityList = getSqlSession().selectList(
					PRICING_REGION_NAMESPACE + address, regionEntity,
					rowBounds);
			return regionEntityList;
	}
	
	/**
	 * .
	 * <p>
	 * 根据条件查询区域信息个数<br/>
	 * 方法名：countRegionBigTicketByCondition
	 * </p>
	 * 
	 * @param regionEntity
	 *            查询条件
	 * @author shenweihua
	 * @时间 2014-07-4
	 * @since JDK1.6
	 */
	@Override
	public Long countRegionByCondition(PriceRegionEntity regionEntity) {
		String address=null;
		if(StringUtils.equals(DictionaryValueConstants.ARRIVE_REGION, regionEntity.getPriceRegionFlag())){
			address = "countArriveRegionBigTicketByCondition";
		}
		Object obj = getSqlSession().selectOne(PRICING_REGION_NAMESPACE + address, regionEntity);
		if (null==obj) {
			return 0L;
		}else{
			return (Long) obj;
		}
	}

}
