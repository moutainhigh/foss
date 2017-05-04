package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.CustomerCouponEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;

/**
 * 
* @Description:晚到补差价接口
* @author hebo 
* @date 2015-5-30 下午4:18:05
 */
public interface ICompensateSpreadService extends IService{
	
	/**
	 * 
	* @Description:提供中转调用，存放补差价信息
	* @author hbhk 
	* @date 2015-5-30 下午4:14:50 
	  @param wayBillNo 
	 */
	void calculateSpread(String... wayBillNos);
	
	/**
	 * 
	* @Description:自动推送补差价信息到CRM生成优惠券信息并返回
	* @author hbhk 
	* @date 2015-5-30 下午5:37:11
	 */
	void autoSendSMSAndGetCoupon();
	
	/**
	 * 
	* @Description:获取客户优惠券
	* @author hbhk 
	* @date 2015-6-1 上午10:01:34 
	  @return
	 */
	List<CustomerCouponEntity> getCustomerCoupon(String customerCode);
	/**
	 * 
	* @Description:封装计算费用的查询条件
	* @author hbhk 
	* @date 2015-6-9 下午4:19:35 
	  @param bean
	  @param transportType
	  @return
	 */
	GuiQueryBillCalculateDto getQueryParamCollection(WaybillEntity bean,String transportType) ;
	
	
	

}
