package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.PriceCouponDto;

public interface IPriceCouponDao {
	
	/**
	 * <p>根据条件查询价格折扣方案明细</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-20 下午5:28:26
	 * @param dto
	 * @return
	 * @see
	 */
	List<PriceCouponDto> selectPriceCouponByCodition(PriceCouponDto dto);
	
	/**
	 * <p>根据条件查询价格折扣方案明细分页</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-8 下午11:07:35
	 * @param dto
	 * @return
	 * @see
	 */
	List<PriceCouponDto> selectPriceCouponByCodition(PriceCouponDto dto, int start, int limit);
	
	/**
	 * <p>根据条件查询价格折扣方案明细总数</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-8 下午11:07:35
	 * @param dto
	 * @return
	 * @see
	 */
	Long countPriceCouponByCodition(PriceCouponDto dto);
	
	/**
	 * <p>根据条件查询价格折扣方案全信息</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-25 下午4:15:26
	 * @param dto
	 * @return
	 * @see
	 */
	List<PriceCouponDto> selectPriceCouponAllByCodition(PriceCouponDto dto);
	
	/**
	 * @Description: 根据计费规则主键查询折扣明细
	 * @author dujunhui-187862
	 * @date 2014-10-29 下午4:26:41
	 * @param priceValuationId
	 * @return
	 * @version V1.0
	 */
	PriceCouponDto selectPriceCouponItemByValuationId(String priceValuationId);
	
	/**
	 * @Description: 根据接送货参数查询折扣明细
	 * @author dujunhui-187862
	 * @date 2014-11-10 上午8:39:44
	 * @param 
	 * @return
	 * @version V1.0
	 */
	public PriceCouponDto selectPriceCouponByConditionToQuery(String deptOrgId,
			String arrvOrgId,String productCode,BigDecimal weight,BigDecimal volume,
			String isPickUp,String calType,Date billTime);
}