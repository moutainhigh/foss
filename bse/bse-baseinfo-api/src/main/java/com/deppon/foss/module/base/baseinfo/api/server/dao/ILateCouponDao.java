package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LateCouponEntity;



public interface ILateCouponDao {

	List<LateCouponEntity> queryActivationScheme();

	List<String> querySubSalesDept(List<String> list);

	List<LateCouponEntity> queryLateCouponByCodition(LateCouponEntity entity,
			int start, int limit);

	Long countLateCouponByCodition(LateCouponEntity entity);

	LateCouponEntity addLateCoupon(LateCouponEntity entity);

	long deleteLateCoupon(List<String> lateCouponIds);

	List<LateCouponEntity> queryRepeat(LateCouponEntity entity);

	void activateLateCoupon(String id);

	void stopLateCoupon(String id);

	String getMaxCode();

	LateCouponEntity queryLateCouponById(String id);

	List<LateCouponEntity> findActivationScheme(LateCouponEntity entity);
	
	
	
	
	
}