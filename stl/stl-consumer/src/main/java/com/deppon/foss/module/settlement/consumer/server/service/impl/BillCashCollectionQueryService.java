/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-consumer
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/BillCashCollectionQueryService.java
 * 
 * FILE NAME        	: BillCashCollectionQueryService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IBillCashCollectionQueryDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillCashCollectionQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCashCollectionQueryDto;

/**
 * 查询现金收款单Service实现
 * 
 * @author foss-zhangxiaohui
 * @date Nov 2, 2012 4:04:49 PM
 */

public class BillCashCollectionQueryService implements IBillCashCollectionQueryService {

	/**
	 * 日志打印对象声明
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(BillCashCollectionQueryService.class);
	
	/**
	 * 查询现金收款单Dao
	 */
	private IBillCashCollectionQueryDao billCashCollectionQueryDao;
	
	/**
	 * 通过业务日期查询现金收款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Nov 2, 2012 4:04:52 PM
	 */
	@Override
	public List<BillCashCollectionEntity> queryBillCashCollectionByBusinessDate(int offset,int start,BillCashCollectionQueryDto billCashCollectionQueryDto,CurrentInfo cInfo) {
		//声明返回页面显示的对象实体List
		List<BillCashCollectionEntity> list = null;		
		//验证输入的参数
		this.validateInputParameters(billCashCollectionQueryDto);
		//获得用户数据查询权限信息
		billCashCollectionQueryDto.setEmpCode(cInfo.getEmpCode());
		//Service执行的Log
		LOGGER.debug("entering service");
		//声明一个变量去存放页面所选择的日期类型
		String str = billCashCollectionQueryDto.getSelectDateType();
		//日期类型为业务日期执行操作
		if(SettlementConstants.TAB_DATE_TYPE_FOR_BUSINESS.equals(str)){
			//赋值到list以返回页面显示
			list = billCashCollectionQueryDao.queryBillCashCollectionByBusinessDate(offset,start,billCashCollectionQueryDto);
		} 
		//如果不是业务日期则抛出异常
		else {
			throw new SettlementException("传入的日期类型异常");
		}
		//Service执行结束打印日志
		LOGGER.debug("successfully exit service");
		//返回查询结果
		return list;
	}

	/** 
	 * 通过记账日期查询现金收款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 4, 2012 9:10:21 AM
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.IBillCashCollectionQueryService#queryBillCashCollectionByBusinessDate(int, int, com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCashCollectionQueryDto)
	 */
	@Override
	public List<BillCashCollectionEntity> queryBillCashCollectionByAccountDate(int offset,int start,BillCashCollectionQueryDto billCashCollectionQueryDto,CurrentInfo cInfo) {
		//声明返回页面显示的对象实体List
		List<BillCashCollectionEntity> list = null;
		//验证输入的参数
		this.validateInputParameters(billCashCollectionQueryDto);
		//获得用户数据查询权限信息
		billCashCollectionQueryDto.setEmpCode(cInfo.getEmpCode());
		//Service执行的Log
		LOGGER.debug("entering service, DateType: " + billCashCollectionQueryDto.getSelectDateType());
		//声明一个变量去存放页面所选择的日期类型
		String str = billCashCollectionQueryDto.getSelectDateType();
		//日期类型为记账日期执行操作
		if(SettlementConstants.TAB_DATE_TYPE_FOR_ACCOUNT.equals(str)){
			//赋值并返回页面显示
			list = billCashCollectionQueryDao.queryBillCashCollectionByAccountDate(offset,start,billCashCollectionQueryDto);
		} 
		//如果不是记账日期则抛出异常
		else {
			throw new SettlementException("传入的日期类型异常");
		}
		//Service执行结束打印日志
		LOGGER.debug("successfully exit service, DateType: " + billCashCollectionQueryDto.getSelectDateType());
		//返回查询结果
		return list;
	}
	
	/**
	 * 根据业务日期查询数据库中记录总条数，总金额
	 * 
	 * @author foss-zhangxiaohui
	 * @date Nov 6, 2012 10:57:09 AM
	 */
	@Override
	public BillCashCollectionQueryDto queryTotalAmountByBusinessDate(BillCashCollectionQueryDto billCashCollectionQueryDto,CurrentInfo cInfo) {
		//声明返回页面显示的对象实体List
		BillCashCollectionQueryDto dto = null;
		//验证输入的参数
		this.validateInputParameters(billCashCollectionQueryDto);
		//获得用户数据查询权限信息
		billCashCollectionQueryDto.setEmpCode(cInfo.getEmpCode());
		//Service执行的Log
		LOGGER.debug("entering service, DateType: " + billCashCollectionQueryDto.getSelectDateType());
		//声明一个变量去存放页面所选择的日期类型
		String str = billCashCollectionQueryDto.getSelectDateType();
		//日期类型为记账日期时执行操作
		if(SettlementConstants.TAB_DATE_TYPE_FOR_BUSINESS.equals(str)){
			//赋值查询结果返回页面显示
			dto = billCashCollectionQueryDao.queryTotalAmountByBusinessDate(billCashCollectionQueryDto);
		}
		//声明一个变量去存放页面所选择的日期类型
		else{
			throw new SettlementException("传入的日期类型异常");
		}
		//Service执行结束打印日志
		LOGGER.debug("entering service, DateType: " + billCashCollectionQueryDto.getSelectDateType());
		return dto;
	}
	
	/**
	 * 根据记账日期查询数据库中记录总条数，总金额
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 10, 2012 10:57:09 AM
	 */
	@Override
	public BillCashCollectionQueryDto queryTotalAmountByAccountDate(BillCashCollectionQueryDto billCashCollectionQueryDto,CurrentInfo cInfo) {
		//声明返回页面显示的对象实体List
		BillCashCollectionQueryDto dto = null;
		//验证输入的参数
		this.validateInputParameters(billCashCollectionQueryDto);
		//获得用户数据查询权限信息
		billCashCollectionQueryDto.setEmpCode(cInfo.getEmpCode());
		//Service执行的Log
		LOGGER.debug("entering service, DateType: " + billCashCollectionQueryDto.getSelectDateType());
		//声明一个变量去存放页面所选择的日期类型
		String str = billCashCollectionQueryDto.getSelectDateType();
		//日期类型为记账日期时执行操作
		if(SettlementConstants.TAB_DATE_TYPE_FOR_ACCOUNT.equals(str)){
			//查询结果赋值给Dto返回页面显示
			dto = billCashCollectionQueryDao.queryTotalAmountByAccountDate(billCashCollectionQueryDto);
		}
		//如果日期类型为异常常量则抛出异常
		else{
			throw new SettlementException("传入的日期类型异常");
		}
		//Service执行结束打印日志
		LOGGER.debug("entering service, DateType: " + billCashCollectionQueryDto.getSelectDateType());
		//返回结果
		return dto;
	}
	
	/**
	 * 验证输入参数
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 26, 2012 10:57:09 AM
	 */
	private void validateInputParameters(BillCashCollectionQueryDto billCashCollectionQueryDto){
		// 输入参数非空校验
		if (billCashCollectionQueryDto == null) {
			//如果参数Dto为空则抛出异常
			throw new SettlementException("传入的现金收款单实体不能为空");
		}
		// 开始日期非空校验
		if (billCashCollectionQueryDto.getStartDate() == null) {
			//开始日期为空则抛出异常
			throw new SettlementException("开始日期不能为空");
		}
		// 结束日期非空校验
		if (billCashCollectionQueryDto.getEndDate() == null) {
			//结束日期为空则抛出异常
			throw new SettlementException("结束日期不能为空");
		}
		//判断开始日期是否小于结束日期
		if(billCashCollectionQueryDto.getStartDate() != null && billCashCollectionQueryDto.getEndDate() != null){
			Date startDate = DateUtils.truncate(billCashCollectionQueryDto.getStartDate(),Calendar.SECOND);
			Date endDate = DateUtils.truncate(billCashCollectionQueryDto.getEndDate(),Calendar.SECOND);
			//判断开始日期是否在结束日期之后
			if(startDate.after(endDate)){
				//开始日期在结束日期之后则抛出异常
				throw new SettlementException("开始日期大于结束日期！");
			}
		}
	}
	
	
	
	

	/** 
	 * 按运单查询现金收款单（小票和运单）
	 * @author 095793-foss-LiQin
	 * @date 2013-4-3 上午9:30:59
	 * @param billCashCollectionQueryDto
	 * @param cInfo
	 * @return
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.IBillCashCollectionQueryService#queryBillCashCollectionByWaybillNO(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCashCollectionQueryDto, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public List<BillCashCollectionEntity> queryBillCashCollectionByWaybillNO(
			BillCashCollectionQueryDto billCashDto,
			CurrentInfo cInfo) {
			
			//职员编码			
			billCashDto.setEmpCode(cInfo.getEmpCode());
			List<BillCashCollectionEntity> queryCashList=billCashCollectionQueryDao.queryBillCashCollectionByWaybillNo(billCashDto);
			
		return queryCashList;
	}
	
	
	
	/**
	 * @param billCashCollectionQueryDao
	 */
	public void setBillCashCollectionQueryDao(
			IBillCashCollectionQueryDao billCashCollectionQueryDao) {
		this.billCashCollectionQueryDao = billCashCollectionQueryDao;
	}
}
