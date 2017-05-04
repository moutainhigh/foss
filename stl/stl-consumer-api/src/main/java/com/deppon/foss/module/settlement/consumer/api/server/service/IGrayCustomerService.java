package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.GrayCustomerEntity;

/**
 * 更新灰名单
 * 
 *  @author 269044-zhurongrong
 *  @date 2016-04-12
 */
public interface IGrayCustomerService {
	
	/**
	 * 根据客户编码查询客户是否为灰名单客户
	 * 
	 * @author 269044-zhurongrong
	 * @date 2016-04-18
	 */
	GrayCustomerEntity queryGrayCustomerByCustomerCode(String customerCode);
	
	/**
	 * 根据客户编码集合查询灰名单信息
	 * 
	 * @author 269044-zhurongrong
	 * @date 2016-04-20
	 */
	List<GrayCustomerEntity> queryGrayCustomerListByCustomerCodes(List<String> customerCodes);
	
	/**
	 * 添加灰名单信息并将消息传送给ECS
	 * 
	 * @author 269044-zhurongrong
	 * @date 2016-04-21
	 */
	void updateGrayCustomerToECS(List<String> customerCodes);
	
	/**
	 * 分页查询灰名单进行推送
	 * 
	 * @author 269044-zhurongrong
	 * @date 2016-04-28
	 */
	void SyncAllGrayCustomersToECS();
	
	
	
}
