package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.GrayCustomerEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.GrayCustomerDto;

/**
 * 更新灰名单
 * 
 *  @author 269044-zhurongrong
 *  @date 2016-04-12
 */
public interface IGrayCustomerDao {
	
	/**
	 * 查询灰名单数据
	 * 
	 * @author 269044-zhurongrong
	 * @date 2016-04-15
	 */
	List<GrayCustomerEntity> queryGrayCustomerList(int start, int limit);
	
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
	 * 根据客户编码删除灰名单
	 *
	 * @author 269044-zhurongrong
	 * @date 2016-04-18
	 */
	int deleteGrayCustomerByCustomerCode(GrayCustomerDto grayCustomerDto);
	
	/**
	 * 添加灰名单信息
	 * 
	 * @author 269044-zhurongrong
	 * @date 2016-04-18
	 */
	int addGrayCustomer(GrayCustomerEntity grayCustomerCode);
	
	/**
	 * 查询灰名单信息总条数
	 * 
	 * @author 269044-zhurongrong
	 * @dare 2016-04-28
	 */
	int countQueryGrayCustomer();

}
