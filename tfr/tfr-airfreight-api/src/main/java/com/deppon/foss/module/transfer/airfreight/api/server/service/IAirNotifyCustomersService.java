package com.deppon.foss.module.transfer.airfreight.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirNotifyCustomersSmsInfo;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirNotifyCustomersDto;

public interface IAirNotifyCustomersService extends IService {

	/**
	 * 主界面查询
	 * @param airNotifyCustomersDto
	 * @param start
	 * @param limit
	 * @return
	 */
	List<AirNotifyCustomersDto> queryAirNotifyCustomers(
			AirNotifyCustomersDto airNotifyCustomersDto, int start,
			int limit);

	/**
	 * 主界面查询--统计条数
	 * @param airNotifyCustomersDto
	 * @return
	 */
	long queryAirNotifyCustomersCount(AirNotifyCustomersDto airNotifyCustomersDto);
	
	/**
	 * 查询通知信息和运单信息
	 * @param airNotifyCustomersDto
	 * @return
	 */
	AirNotifyCustomersDto queryAirNotifyWaybillInfo(AirNotifyCustomersDto conditionDto);
	
	/**
	 * 根据运单号查询正单   得到到达网点
	 * @param waybillNo
	 * @return
	 */
	AirWaybillEntity queryAirwaybillBywaybillNo(String waybillNo);

	/**
	 * 获取发送的时间
	 * @return
	 */
	String getInformationReceiveTimeRange();

	/**
	 * 发送短信息
	 * @param estimatedPickupTime
	 * @param airNotifyCustomersDto
	 */
	/*
	void autoSendMessageDetail(String estimatedPickupTime,
			AirNotifyCustomersDto airNotifyCustomersDto);*/
	
	/**
	 * 保存空运通知客户信息  
	 * @param airNotifyCustomersSmsInfoList
	 */
	
	/**
	 * 批量通知客户
	 * @param airNotifyCustomersSmsInfoList
	 */

	/**
	 * 获取短信内容
	 * @param airNotifyCustomersSmsInfo
	 * @return
	 */
	
	/**
	 * 更新空运通知客户信息里面的通知情况
	 */
	int updateAirNotifyCustomersSmsInfoNoticeResult(AirNotifyCustomersSmsInfo airNCSmsInfo);
	
	/**
	 * 通过JOB来定时更新空运通知客户表和实际承运信息表的通知状态
	 */
	void airNotifyCustomersJobRun();
	
	/**
	 * 
	 * 更新空运通知客户信息
	 */
	int updateAirNotifyCustomersSmsInfo(AirNotifyCustomersSmsInfo airNCSmsInfo);
	
	/**
	 * 查询空运通知客户信息
	 */
	AirNotifyCustomersSmsInfo queryAirNotifyCustomersSmsInfo(AirNotifyCustomersSmsInfo airNCSmsInfo);
}