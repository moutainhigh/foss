package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.CustomerCouponEntity;

public interface ICustomerCouponDao {
	/**
	 * 
	* @Description: 绑定客户与优惠券关系
	* @author hbhk 
	* @date 2015-6-9 下午2:26:29 
	  @param entity
	  @return
	 */
     int insert(CustomerCouponEntity entity);
     /**
      * 
     * @Description: 查询客户与优惠券关系
     * @author hbhk 
     * @date 2015-6-9 下午2:26:47 
       @param entity
       @param start
       @param size
       @return
      */
     List<CustomerCouponEntity> queryCustomerCouponList(CustomerCouponEntity entity,int start,int size);
     /**
      * 
     * @Description: 客户使用优惠券
     * @author hebo 
     * @date 2015-6-9 下午2:27:25 
       @param customerCode
       @param couponCode
       @return
      */
     int useCustomerCoupon(String customerCode,String couponCode);
}

