package com.deppon.foss.module.pickup.pricing.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;


public interface IPriceCrmOrderService extends IService {
    /**
     * 
     * <p>优惠劵验证</p> 
     * @author foss-sunrui
     * @date 2012-11-15 下午4:47:50
     * @param couponInfo
     * @return
     * @see
     */
    CouponInfoResultDto validateCoupon(CouponInfoDto couponInfo);
}
