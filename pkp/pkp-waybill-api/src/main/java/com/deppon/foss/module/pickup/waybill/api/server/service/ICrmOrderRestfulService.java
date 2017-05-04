package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderQueryDto;

public interface ICrmOrderRestfulService extends IService {

    /**
     * 
     * <p>查询CRM系统订单</p> 
     * @author foss-sunrui
     * @date 2012-11-13 下午2:57:13
     * @param query
     * @return
     * @see
     */
    CrmOrderInfoDto searchOrder(CrmOrderQueryDto query);
    
    /**
     * 
     * <p>导入CRM系统订单</p> 
     * @author foss-sunrui
     * @date 2012-11-13 下午2:57:26
     * @param orderNumber
     * @return
     * @see
     */
    CrmOrderDetailDto importOrder(String orderNumber);

	
	/**
	 * 查询快递网上订单
	 * @author 026123-foss-lifengteng
	 * @date 2013-10-9 下午2:33:31
	 */
	CrmOrderInfoDto searchExpressOrder(CrmOrderQueryDto query);

	
	/**
	 * 处理订单界面-受理订单操作查询订单
	 * @author 026123-foss-lifengteng
	 * @date 2013-10-9 下午2:33:31
	 */
	public boolean searchOrder(String orderNumber);
}
