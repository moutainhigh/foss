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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/BillFreightToCollectQueryService.java
 * 
 * FILE NAME        	: BillFreightToCollectQueryService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IBillFreightToCollectQueryDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillFreightToCollectQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillFreightToCollectQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillFreightToCollectResultDto;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;

/**
 * 到付清查Service实现
 * 
 * @author foss-zhangxiaohui
 * @date Oct 29, 2012 4:16:19 PM
 */
public class BillFreightToCollectQueryService implements IBillFreightToCollectQueryService {
	
	/**
	 * 日志打印对象声明
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(BillFreightToCollectQueryService.class);
	
	/**
	 * 到付清查Dao
	 */
	private IBillFreightToCollectQueryDao billFreightToCollectQueryDao;
	
	/**
	 * 库存状态Service
	 */
    private IStockService  stockService;
    
    /**
	 * 综合业务组织service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 通过业务日期查询到付清查
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 4:18:19 PM
	 * @param offset
	 * @param start
	 * @param billFreightToCollectQueryDto
	 * @param cInfo
	 * @return
	 */
	@Override
	public List<BillFreightToCollectResultDto> queryBillByBusinessDate(int offset,int start,
			BillFreightToCollectQueryDto billFreightToCollectQueryDto,CurrentInfo cInfo) {
		//Service执行的Log
		LOGGER.info("entering service, DateType: " + billFreightToCollectQueryDto.getSelectDateType());
		//验证输入参数
		this.validateInputParameters(billFreightToCollectQueryDto);	
		
		//设置大区小区
		this.setBusinessRegionCodeList(billFreightToCollectQueryDto);
		
		//设置数据权限
		billFreightToCollectQueryDto.setEmpCode(cInfo.getEmpCode());
		
		//获取日期类型从而用不同的SQL去查询
		String str = billFreightToCollectQueryDto.getSelectDateType();
		
		//获取日期类型从而用不同的SQL去查询,否则抛出异常
		if(!SettlementConstants.TAB_DATE_TYPE_FOR_BUSINESS.equals(str)){
			throw new SettlementException("传入的日期类型异常");
		}
		
		List<BillFreightToCollectResultDto>  list=billFreightToCollectQueryDao.queryBillByBusinessDate(offset,start,billFreightToCollectQueryDto);
		
		//String stockStatus=billFreightToCollectQueryDto.getStockStatus();		//Service执行结束打印日志
		LOGGER.debug("successfully exit service, DateType: " + billFreightToCollectQueryDto.getSelectDateType());
		
		//返回查询结果
		return list;
	}
	
	/**
	 * 调用中转接口，查询货物的库存状态
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-3-25 下午3:16:23
	 * @param listRece
	 * @return
	 */
	private List<BillFreightToCollectResultDto> getListResults(List<BillReceivableEntity> listRece,String stockSts){
		List<BillFreightToCollectResultDto> list = new ArrayList<BillFreightToCollectResultDto>();
		if(CollectionUtils.isNotEmpty(listRece)){
			for(BillReceivableEntity entity:listRece){
				BillFreightToCollectResultDto dto=new BillFreightToCollectResultDto();
				BeanUtils.copyProperties(entity, dto);
				if(StringUtils.isNotBlank(entity.getWaybillNo())
						//来源单据类型为：运单
						&&SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL.equals(entity.getSourceBillType())
						){
					//调用中转接口，查询货物的库存状态
					//调用中转接口，查询货物的库存状态
					String stockStatus=stockService.queryWaybillStockStatus(entity.getWaybillNo(), 
							entity.getGoodsQtyTotal()!=null?entity.getGoodsQtyTotal().intValue():0,
							entity.getDestOrgCode());
					dto.setStockStatus(stockStatus);//设置库存状态
					
					if(StringUtils.isNotEmpty(stockSts)){
						if(StringUtils.isBlank(stockStatus)|| stockStatus.equals(stockSts)){
							list.add(dto);
						}
					}else{
						list.add(dto);
					}
					
				}
			}
		}
		return list;
	}
	
	/** 
	 * 通过记账日期查询到付清查
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 10, 2012 11:46:32 AM
	 */
	@Override
	public List<BillFreightToCollectResultDto> queryBillByAccountDate(int offset,int start,BillFreightToCollectQueryDto billFreightToCollectQueryDto, CurrentInfo cInfo) {
		//Service执行的Log
				LOGGER.info("entering service, DateType: " + billFreightToCollectQueryDto.getSelectDateType());
		//验证输入参数
		this.validateInputParameters(billFreightToCollectQueryDto);
		
		//设置大区小区
		this.setBusinessRegionCodeList(billFreightToCollectQueryDto);
		
		//设置数据权限
		billFreightToCollectQueryDto.setEmpCode(cInfo.getEmpCode());
		
		//获取日期类型从而用不同的SQL去查询
		String str = billFreightToCollectQueryDto.getSelectDateType();
		
		//获取日期类型从而用不同的SQL去查询,否则抛出异常
		if(!SettlementConstants.TAB_DATE_TYPE_FOR_ACCOUNT.equals(str)){
			throw new SettlementException("传入的日期类型异常");
		}
		
		//赋值给声明的List
		List<BillFreightToCollectResultDto> listRece= billFreightToCollectQueryDao.queryBillByAccountDate(offset,start,billFreightToCollectQueryDto);
		//Service执行结束打印日志
		LOGGER.debug("successfully exit service, DateType: " + billFreightToCollectQueryDto.getSelectDateType());
		
		
		//返回查询结果
		return listRece;
	}
	
	/**
	 * 根据业务日期查询数据库中记录总条数，总金额核销总金额，未核销总金额
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 4:18:19 PM
	 */
	@Override
	public BillFreightToCollectQueryDto queryTotalAmountByBusinessDate(BillFreightToCollectQueryDto billFreightToCollectQueryDto, CurrentInfo cInfo) {
		//Service执行的Log
		LOGGER.info("entering service, DateType: " + billFreightToCollectQueryDto.getSelectDateType());
		//声明返回页面显示的对象实体
		BillFreightToCollectQueryDto dto = null;
		
		//验证输入参数
		this.validateInputParameters(billFreightToCollectQueryDto);	
		
		//设置大区小区
		this.setBusinessRegionCodeList(billFreightToCollectQueryDto);
		
		//设置数据权限
		billFreightToCollectQueryDto.setEmpCode(cInfo.getEmpCode());
		
		
		//声明一个变量去存放页面所选择的日期类型
		String str = billFreightToCollectQueryDto.getSelectDateType();
		//日期类型为业务日期执行操作
		if(SettlementConstants.TAB_DATE_TYPE_FOR_BUSINESS.equals(str)){
			//赋值给声明的实体对象
			dto =  billFreightToCollectQueryDao.queryTotalAmountByBusinessDate(billFreightToCollectQueryDto);
		}
		//如果日期类型为异常的常量则抛出异常
		else{
			throw new SettlementException("传入的日期类型异常");
		}
		//Service执行结束打印日志
		LOGGER.debug("successfully exit service, DateType: " + billFreightToCollectQueryDto.getSelectDateType());
		//返回查询结果
		return dto;
	}
	
	/**
	 * 根据记账日期查询数据库中记录总条数，总金额核销总金额，未核销总金额
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 4:18:19 PM
	 */
	@Override
	public BillFreightToCollectQueryDto queryTotalAmountByAccountDate(BillFreightToCollectQueryDto billFreightToCollectQueryDto,CurrentInfo cInfo) {
		//Service执行的Log
		LOGGER.info("entering service, DateType: " + billFreightToCollectQueryDto.getSelectDateType());
		//声明返回页面显示的对象实体
		BillFreightToCollectQueryDto dto = null;
		
		//验证输入参数
		this.validateInputParameters(billFreightToCollectQueryDto);
		
		//设置大区小区
		this.setBusinessRegionCodeList(billFreightToCollectQueryDto);
				
		//设置数据权限
		billFreightToCollectQueryDto.setEmpCode(cInfo.getEmpCode());
		
		//声明一个变量去存放页面所选择的日期类型
		String str = billFreightToCollectQueryDto.getSelectDateType();
		//日期类型为记账日期执行操作
		if(SettlementConstants.TAB_DATE_TYPE_FOR_ACCOUNT.equals(str)){
			//赋值给声明的Dto对象
			dto =  billFreightToCollectQueryDao.queryTotalAmountByAccountDate(billFreightToCollectQueryDto);
		}
		//如果日期类型为异常的常量则抛出异常
		else{
			throw new SettlementException("传入的日期类型异常");
		}
		//Service执行结束打印日志
		LOGGER.debug("successfully exit service, DateType: " + billFreightToCollectQueryDto.getSelectDateType());
		//返回查询结果
		return dto;
	}
	
	/**
	 * 验证输入参数
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 26, 2012 4:18:19 PM
	 */
	private void validateInputParameters(BillFreightToCollectQueryDto billFreightToCollectQueryDto){
		// 输入参数非空校验
		if (billFreightToCollectQueryDto == null) {
			//参数Dto为空则抛出异常
			throw new SettlementException("传入的到付清查实体不能为空");
		}

		
		if (null==billFreightToCollectQueryDto.getStartDate()) {
			//开始日期为空则抛出异常
			throw new SettlementException("开始日期不能为空");
		}
		
		// 结束日期非空校验
		if (billFreightToCollectQueryDto.getEndDate() == null) {
			//结束日期为空则抛出异常
			throw new SettlementException("结束日期不能为空");
		}
		
		// 结束日期非空校验
		if (StringUtils.isBlank(billFreightToCollectQueryDto.getDeptType())) {
			//结束日期为空则抛出异常
			throw new SettlementException("部门类型不能为空！");
		}
				
		//判断开始日期是否小于结束日期
		if(billFreightToCollectQueryDto.getStartDate() != null && billFreightToCollectQueryDto.getEndDate() != null){
			Date startDate = DateUtils.truncate(billFreightToCollectQueryDto.getStartDate(),Calendar.SECOND);
			Date endDate = DateUtils.truncate(billFreightToCollectQueryDto.getEndDate(),Calendar.SECOND);
			//判断开始日期是否在结束日期之后
			if(startDate.after(endDate)){
				//开始日期在结束日期之后则抛出异常
				throw new SettlementException("开始日期大于结束日期！");
			}
		}
	}
	
	/**
	 * 设置大区小区
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-4-27 上午10:03:04
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	private void setBusinessRegionCodeList(BillFreightToCollectQueryDto billFreightToCollectQueryDto) {

		// 初始化待设置当前登录用户下属部门List
		List<String> targetList = new ArrayList<String>();

		// 如果界面输入的部门存在
		if (StringUtils.isNotBlank(billFreightToCollectQueryDto.getDepartment())) {
			// 如果输入部门不为空，直接将大区小区下所网点编码设为空
			targetList = null;
			// 如果输入的部门不存在，小区存在
		} else if (StringUtils.isNotBlank(billFreightToCollectQueryDto.getSmallArea())) {
			// 根据小区编码，获取该小区下属营业部部门信息
			List<OrgAdministrativeInfoEntity> list = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoEntityAllSubByCode(billFreightToCollectQueryDto.getSmallArea());
			// 如果营业部门信息不为空，获取其全部部门编码
			if (CollectionUtils.isNotEmpty(list)) {
				for (OrgAdministrativeInfoEntity entity : list) {
					targetList.add(entity.getCode());
				}
			}
			// 如果输入的部门、小区不存在，大区存在
		} else if (StringUtils.isNotBlank(billFreightToCollectQueryDto.getBigArea())) {
			// 根据大区编码，获取该大区下属营业部部门信息
			List<OrgAdministrativeInfoEntity> list = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoEntityAllSubByCode(billFreightToCollectQueryDto.getBigArea());
			// 如果营业部门信息不为空，获取其全部部门编码
			if (CollectionUtils.isNotEmpty(list)) {
				for (OrgAdministrativeInfoEntity entity : list) {
					targetList.add(entity.getCode());
				}
			}
		} 

		// 设置查询条件
		billFreightToCollectQueryDto.setOrgCodeInArea(targetList);
		
		//当单据类型为空时，查询除小票应收、始发应收单据		
		if(StringUtils.isEmpty(billFreightToCollectQueryDto.getBillType())){
			List <String>billTypeList=new ArrayList<String>();
			billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);
			billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);
			billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE);
			billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE);
			billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY);
			billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD);
			billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_LAND_STOWAGE);
			billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD);
			billFreightToCollectQueryDto.setBillTypeList(billTypeList);
		}
	}
	
	/**
	 * @param billFreightToCollectQueryDao
	 */
	public void setBillFreightToCollectQueryDao(IBillFreightToCollectQueryDao billFreightToCollectQueryDao) {
		this.billFreightToCollectQueryDao = billFreightToCollectQueryDao;
	}

	
	/**
	 * @param stockService the stockService to set
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	
	/**
	 * @set
	 * @param orgAdministrativeInfoComplexService
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		/*
		 *@set
		 *@this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService
		 */
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
}
