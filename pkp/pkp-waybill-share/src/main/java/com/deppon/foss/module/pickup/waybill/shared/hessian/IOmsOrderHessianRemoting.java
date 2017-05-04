package com.deppon.foss.module.pickup.waybill.shared.hessian;

import com.deppon.foss.framework.service.IHessianService;
import com.deppon.foss.module.pickup.waybill.shared.domain.OmsOrderEntity;

/**
 * 
 * OMS系统订单服务接口(Hessian)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sangwenhao,date:2016-08-03 下午6:49:41, </p>
 * @author foss-sangwenhao
 * @date 2016-08-03 下午6:49:41
 * @since
 * @version
 */
public interface IOmsOrderHessianRemoting extends IHessianService {

	/**
     * <p>根据订单号查询订单</p> 
     * @author foss-sangwenhao
     * @date 2016-08-03 下午6:49:41
     * @param orderNo 订单号
     * @return
     * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IOmsOrderHessianRemoting#queryOmsOrderByWaybillNo(String waybillNo)
     */
	OmsOrderEntity queryOmsOrderByOrderNo(String orderNo) ;
	
	/**
     * <p>根据运单号查询订单</p> 
     * @author foss-sangwenhao
     * @date 2016-08-03 下午6:49:41
     * @param waybillNo 运单号
     * @return
     * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IOmsOrderHessianRemoting#queryOmsOrderByOrderNo(String orderNo)
     */
	OmsOrderEntity queryOmsOrderByWaybillNo(String waybillNo) ;
	
}
