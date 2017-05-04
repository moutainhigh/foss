package com.deppon.foss.module.pickup.waybill.server.hessian;

import javax.annotation.Resource;

import com.deppon.foss.framework.server.deploy.hessian.annotation.Remote;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOmsOrderService;
import com.deppon.foss.module.pickup.waybill.shared.domain.OmsOrderEntity;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IOmsOrderHessianRemoting;

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
@Remote
public class OmsOrderHessianRemoting implements IOmsOrderHessianRemoting {
	
	@Resource
	private IOmsOrderService omsOrderService ;
	
	/**
     * <p>根据订单号查询订单</p> 
     * @author foss-sangwenhao
     * @date 2016-08-03 下午6:49:41
     * @param orderNo 订单号
     * @return
     * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IOmsOrderHessianRemoting#queryOmsOrderByWaybillNo(String waybillNo)
     */
	@Override
	public OmsOrderEntity queryOmsOrderByOrderNo(String orderNo) {
		return omsOrderService.queryOmsOrderByOrderNo(orderNo) ;
	}

	/**
     * <p>根据运单号查询订单</p> 
     * @author foss-sangwenhao
     * @date 2016-08-03 下午6:49:41
     * @param waybillNo 运单号
     * @return
     * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IOmsOrderHessianRemoting#queryOmsOrderByOrderNo(String orderNo)
     */
	@Override
	public OmsOrderEntity queryOmsOrderByWaybillNo(String waybillNo) {
		return omsOrderService.queryOmsOrderByWaybillNo(waybillNo);
	}

}
