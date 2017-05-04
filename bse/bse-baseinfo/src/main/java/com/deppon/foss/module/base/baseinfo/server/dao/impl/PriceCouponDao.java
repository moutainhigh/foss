package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPriceCouponDao;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PriceCouponDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 降价发券方案操作类
 * @author dujunhui-187862
 * @date 2014-10-8 上午11:08:26
 * @since
 * @version
 */
public class PriceCouponDao extends SqlSessionDaoSupport implements IPriceCouponDao {
	
	private String namespace = "foss.bse.bse-baseinfo.priceCouponEntityMapper.";
	
	/**
	 * <p>根据条件查询价格折扣方案明细</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-20 下午5:29:26
	 * @param dto
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceCouponDto> selectPriceCouponByCodition(PriceCouponDto dto) {
		return getSqlSession().selectList(namespace+"selectPriceCouponByCodition", dto);	
	}

	/**
	 * <p>根据条件查询价格折扣方案明细分页</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-8 上午11:08:26
	 * @param dto
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceCouponDto> selectPriceCouponByCodition(
			PriceCouponDto dto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return getSqlSession().selectList(namespace+"selectPriceCouponByCodition", dto, rowBounds);	
	}

	/**
	 * <p>根据条件查询价格折扣方案明细总数</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-8 上午11:08:26
	 * @param dto
	 * @return
	 * @see
	 */
	@Override
	public Long countPriceCouponByCodition(PriceCouponDto dto) {
		return (Long)getSqlSession().selectOne(namespace+"countPriceCouponByCodition", dto);	
	}

	/**
	 * <p>根据条件查询价格折扣方案全信息</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-25 下午4:14:26
	 * @param dto
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceCouponDto> selectPriceCouponAllByCodition(PriceCouponDto dto) {
		return getSqlSession().selectList(namespace+"selectPriceCouponAllByCodition", dto);	
	}
	/**
	 * @Description: 根据计费规则主键查询折扣明细
	 * @author dujunhui-187862
	 * @date 2014-10-29 下午4:26:42
	 * @param priceValuationId
	 * @return
	 * @version V1.0
	 */
	@Override
	public PriceCouponDto selectPriceCouponItemByValuationId(
			String priceValuationId) {
		return (PriceCouponDto)getSqlSession().selectOne(namespace+"selectCouponItemByValuationId", priceValuationId);	
	}
	/**
	 * @Description: 根据接送货参数查询折扣明细
	 * @author dujunhui-187862
	 * @date 2014-11-10 上午8:40:27
	 * @param 
	 * @return
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PriceCouponDto selectPriceCouponByConditionToQuery(String deptOrgId,
			String arrvOrgId,String productCode,BigDecimal weight,BigDecimal volume,
			String isPickUp,String calType,Date billTime) {
		Map<Object,Object> map=new HashMap<Object,Object>();
		map.put("deptOrgId", deptOrgId);
		map.put("arrvOrgId", arrvOrgId);
		map.put("productCode", productCode);
		if(DictionaryValueConstants.BILLINGWAY_WEIGHT.equals(calType)){
			map.put("weight", weight);
			map.put("volume", null);
		}
		if(DictionaryValueConstants.BILLINGWAY_VOLUME.equals(calType)){
			map.put("weight", null);
			map.put("volume", volume);
		}
		map.put("isPickUp", isPickUp);
		map.put("billTime", billTime);
		map.put("active", FossConstants.ACTIVE);
		List<PriceCouponDto> list=getSqlSession().
				selectList(namespace+"selectPriceCouponByCoditionToQuery", map);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);	
		}else{
			return null;
		}
	}
}