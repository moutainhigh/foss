package com.deppon.foss.module.transfer.airfreight.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirNotifyCustomersSmsInfo;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirNotifyCustomersDto;



public interface IAirNotifyCustomersDao {


	/**
	 * 根据运单号查询正单   得到到达网点
	 * @param waybillNo
	 * @return
	 */
	List<AirWaybillEntity> queryAirwaybillBywaybillNo(String waybillNo);
	
	/**
	 * 运单号
	 * @param conditionDto
	 * @return
	 */
	List<AirNotifyCustomersDto> queryAirNotifyCustomersByWaybillNo(AirNotifyCustomersDto conditionDto,int start,int limit);
	long queryAirNotifyCustomersByWaybillNoCount(AirNotifyCustomersDto conditionDto);
  
	/**
	 * 正单号
	 * @param conditionDto
	 * @return
	 */
	List<AirNotifyCustomersDto> queryAirNotifyCustomersByAirWaybillNo(AirNotifyCustomersDto conditionDto,int start,int limit);
    long queryAirNotifyCustomersByAirWaybillNoCount(AirNotifyCustomersDto conditionDto);
	/**
	 * 默认查询
	 * @param conditionDto
	 * @return
	 */
	List<AirNotifyCustomersDto> queryAirNotifyCustomersByCommon(AirNotifyCustomersDto conditionDto,int start,int limit);
    long queryAirNotifyCustomersByCommonCount(AirNotifyCustomersDto conditionDto);

    /**
     * 添加
     * @param airNotifyCustomersSmsInfo
     * @return
     */
    int addAirNotifyCustomers(AirNotifyCustomersSmsInfo airNotifyCustomersSmsInfo);
    
    /**
	 * 更新空运通知客户信息
	 */
	int updateAirNotifyCustomersSmsInfo(AirNotifyCustomersSmsInfo airNCSmsInfo);
	
	/**
	 * 更新空运通知客户信息里面的通知情况
	 */
	int updateAirNotifyCustomersSmsInfoNoticeResult(AirNotifyCustomersSmsInfo airNCSmsInfo);
	
	/**
	 * 查询空运通知客户信息
	 */
	AirNotifyCustomersSmsInfo queryAirNotifyCustomersSmsInfo(AirNotifyCustomersSmsInfo airNCSmsInfo);

	/**
	 * 查询空运通知客户信息   当天的数据
	 * @param airNCSmsInfo
	 * @return
	 */
	List<AirNotifyCustomersSmsInfo> queryAirNotifyCustomersSmsInfoList(AirNotifyCustomersSmsInfo airNCSmsInfo);
}