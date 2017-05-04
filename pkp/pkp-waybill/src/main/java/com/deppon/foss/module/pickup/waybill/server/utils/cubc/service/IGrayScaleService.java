/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.server.utils.cubc.service;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.RequestDO;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.TradeDO;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestResponse;

/**
 * 
    * @ClassName: IGrayScaleService
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author 323098
    * @date 2017年1月1日
    *
 */
public interface IGrayScaleService {
	/**
	 * 
	    * @Title: vestAscription
	    * @Description: 该方法用于传递运单号
		* @Date: 2017年1月1日
	    * @param requestDO
	    * @return VestResponse
	    * @throws Exception
	 */
	public VestResponse vestAscription(RequestDO requestDO) throws Exception;
	/**
	 * 
	    * @Title: vestBySourceBillNo
	    * @Description: TODO(这里用一句话描述这个方法的作用)
		* @Date: 2017年1月1日
	    * @param requestDO
	    * @return VestResponse
	    * @throws Exception
	 */
	VestResponse vestBySourceBillNo(RequestDO requestDO) throws Exception;
	/**
	 * 
	    * @Title: vestAscription
	    * @Description: 该方法用于传递客户编码
		* @Date: 2017年1月1日
	    * @param requestDO
	    * @return VestResponse
	    * @throws Exception
	 */
	VestResponse vestByCustomer(RequestDO requestDO) throws Exception;
	
	List<TradeDO> grayQueryOrderByList(String parmaters) throws Exception;
	
	List<TradeDO> grayQueryOrderByNo(String parmaters) throws Exception;
}
