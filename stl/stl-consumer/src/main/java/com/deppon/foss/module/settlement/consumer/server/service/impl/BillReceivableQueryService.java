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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/BillReceivableQueryService.java
 * 
 * FILE NAME        	: BillReceivableQueryService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ICreditMsgService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.CreditMsgEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IBillReceivableQueryDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillReceivableQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillReceivableDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 查询应收单Service实现
 * 
 * @author foss-zhangxiaohui
 * @date Oct 17, 2012 4:49:54 PM
 */
public class BillReceivableQueryService implements IBillReceivableQueryService {

	/**
	 * 日志打印对象声明
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(BillReceivableQueryService.class);
	
	/**
	 * 查询应收单Dao
	 */
	private IBillReceivableQueryDao billReceivableQueryDao;
	
	/**
	 * 注入组织信息接口
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * 通过业务日期查询应收单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 17, 2012 4:50:54 PM
	 */
	@Override
	public List<BillReceivableEntity> queryBillReceivableByBusinessDate(int offset,
			int start, BillReceivableDto billReceivableDto,
			CurrentInfo cInfo) {
		
		List<BillReceivableEntity> list = null;//声明查询结果实体对象List
		this.validateDate(billReceivableDto);//非空输入验证及验证日期
		if(cInfo != null){
		billReceivableDto.setEmpCode(cInfo.getEmpCode());//设置大区小区等数据权限
		 }
		
		LOGGER.debug("entering service, DateType: "+ billReceivableDto.getSelectDateType());// Service执行的Log
		String str = billReceivableDto.getSelectDateType();//声明一个变量去存放页面所选择的日期类型
		
		if (SettlementConstants.TAB_DATE_TYPE_FOR_BUSINESS.equals(str)) {//日期类型为业务日期执行操作			
			list = billReceivableQueryDao.queryBillReceivaebleByBusinessDate(offset, start,billReceivableDto);//执行查询操作
		}else {
			throw new SettlementException("传入的日期类型异常");//如果日期类型为异常的常量则抛出异常
		}
		LOGGER.debug("exiting service, DateType: "+ billReceivableDto.getSelectDateType());	// Service执行结束打印日志	
		return list;//返回查询结果
	}

	/**
	 * 通过记账日期查询应收单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 17, 2012 4:50:54 PM
	 */
	@Override
	public List<BillReceivableEntity> queryBillReceivableByAccountDate(int offset,
			int start, BillReceivableDto billReceivableDto,
			CurrentInfo cInfo) {
		//声明查询结果实体对象List 
		List<BillReceivableEntity> list = null;		
		//非空输入验证及验证日期
		this.validateDate(billReceivableDto);
		//设置大区小区等数据权限
		billReceivableDto.setEmpCode(cInfo.getEmpCode());
		// Service执行的Log
		LOGGER.debug("entering service, DateType: "+ billReceivableDto.getSelectDateType());
		//声明一个变量去存放页面所选择的日期类型
		String str = billReceivableDto.getSelectDateType();
		//日期类型为记账日期执行操作
		if (SettlementConstants.TAB_DATE_TYPE_FOR_ACCOUNT.equals(str)) {		
			//执行查询操作
			list = billReceivableQueryDao.queryBillReceivaebleByAccountDate(offset, start,billReceivableDto);
		} 	
		//如果日期类型为异常的常量则抛出异常
		else {
			throw new SettlementException("传入的日期类型异常");
		}
		// Service执行结束打印日志
		LOGGER.debug("exiting service, DateType: " + billReceivableDto.getSelectDateType());		
		//返回查询结果
		return list;
	}
	
	/**
	 * 查询应收单service--根据业务日期查询数据库中记录总条数，总金额核销总金额，未核销总金额
	 * 
	 * @author foss-zhangxiaohui
	 * @date Nov 6, 2012 10:23:55 AM
	 */
	@Override
	public BillReceivableDto queryTotalAmountByBusinessDate(BillReceivableDto billReceivableDto,
			CurrentInfo cInfo) {
		//声明查询结果实体对象
		BillReceivableDto dto = null;	
		//非空输入验证及验证日期
		this.validateDate(billReceivableDto);
		//设置大区小区等数据权限
		billReceivableDto.setEmpCode(cInfo.getEmpCode());
		// Service执行的Log
		LOGGER.info("entering service, DateType: "+ billReceivableDto.getSelectDateType());
		//声明一个变量去存放页面所选择的日期类型
		String str = billReceivableDto.getSelectDateType();
		//日期类型为业务日期执行查询操作
		if (SettlementConstants.TAB_DATE_TYPE_FOR_BUSINESS.equals(str)){		
			//执行查询操作并赋值
			dto = billReceivableQueryDao.queryTotalAmountByBusinessDate(billReceivableDto);
		}	
		//如果日期类型为异常的常量则抛出异常
		else {
			throw new SettlementException("传入的日期类型异常");
		}
		// Service执行结束打印日志
		LOGGER.debug("exitinging service, DateType: " + billReceivableDto.getSelectDateType());
		//返回查询结果
		return dto;
	}

	/**
	 * 根据条件查询应收单
	 * @param billReceivableDto
	 * @return
	 */
	@Override
	public BillReceivableDto queryDiscountTotalAmountByBusinessDate(BillReceivableDto billReceivableDto) {
		//声明查询结果实体对象
		BillReceivableDto dto = null;
		//非空输入验证及验证日期
		this.validateDate(billReceivableDto);
		// Service执行的Log
		LOGGER.info("entering service, DateType: "+ billReceivableDto.getSelectDateType());
		//声明一个变量去存放页面所选择的日期类型
		String str = billReceivableDto.getSelectDateType();
		//日期类型为业务日期执行查询操作
		if (SettlementConstants.TAB_DATE_TYPE_FOR_BUSINESS.equals(str)){
			//执行查询操作并赋值
			dto = billReceivableQueryDao.queryDsicountTotalAmountByBusinessDate(billReceivableDto);
		}
		//如果日期类型为异常的常量则抛出异常
		else {
			throw new SettlementException("传入的日期类型异常");
		}
		// Service执行结束打印日志
		LOGGER.debug("exitinging service, DateType: " + billReceivableDto.getSelectDateType());
		//返回查询结果
		return dto;
	}

	/**
	 * 查询应收单service--根据记账日期查询数据库中记录总条数，总金额核销总金额，未核销总金额
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 10, 2012 11:56:55 AM
	 */
	@Override
	public BillReceivableDto queryTotalAmountByAccountDate(BillReceivableDto billReceivableDto,
			CurrentInfo cInfo) {
		//声明查询结果实体对象
		BillReceivableDto dto = null;
		//非空输入验证及验证日期
		this.validateDate(billReceivableDto);
		//设置大区小区等数据权限
		billReceivableDto.setEmpCode(cInfo.getEmpCode());
		// Service执行的Log
		LOGGER.info("entering service, DateType: " + billReceivableDto.getSelectDateType());
		//声明一个变量去存放页面所选择的日期类型
		String str = billReceivableDto.getSelectDateType();
		//日期类型为业务日期执行查询操作
		if (SettlementConstants.TAB_DATE_TYPE_FOR_ACCOUNT.equals(str)){	
			//执行查询操作并赋值
			dto = billReceivableQueryDao.queryTotalAmountByAccountDate(billReceivableDto);
		} 
		//如果日期类型为异常的常量则抛出异常
		else {
			throw new SettlementException("传入的日期类型异常");
		}
		// Service执行结束打印日志
		LOGGER.debug("exitinging service, DateType: " + billReceivableDto.getSelectDateType());		
		//返回查询结果
		return dto;
	}
	
	/**
	 * 查询应收单service--验证输入条件及日期
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 26, 2012 11:56:55 AM
	 */
	private void validateDate(BillReceivableDto billReceivableDto){
		// 输入参数非空校验
		if (billReceivableDto == null) {		
			//输入参数为空则抛出异常
			throw new SettlementException("传入的应收单实体不能为空");
		}
		// 开始日期非空校验
		if (billReceivableDto.getStartDate() == null) {		
			//开始日期为空则抛出异常
			throw new SettlementException("开始日期不能为空");
		}	
		// 结束日期非空校验
		if (billReceivableDto.getEndDate() == null) {		
			//结束日期为空则抛出异常
			throw new SettlementException("结束日期不能为空");
		}
		// 判断开始日期是否小于结束日期
		if (billReceivableDto.getStartDate() != null && billReceivableDto.getEndDate() != null) {
			Date startDate = DateUtils.truncate(billReceivableDto.getStartDate(), Calendar.SECOND);
			Date endDate = DateUtils.truncate(billReceivableDto.getEndDate(), Calendar.SECOND);	
			//判断开始日期是否在结束日期之后
			if (startDate.after(endDate)) {	
				//开始日期在结束日期之后则抛出异常
				throw new SettlementException("开始日期大于结束日期！");
			}
		}
		//声明目标部门集合
		List<String> targetLsit = new ArrayList<String>();
		
		//如果部门存在，则获取当前部门与用户所管辖部门的交集
		if (StringUtils.isNotBlank(billReceivableDto.getDepartment())) {
			//则需要查看小区是否选对 或者大区是否选对
			if(StringUtils.isNotBlank(billReceivableDto.getSmallArea())){
				List<String> smallAreaDepts = new ArrayList<String>();
				//调用综合方法查询
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoEntityAllSubByCode(billReceivableDto.getSmallArea());
				//循环部门，获取用户所管辖部门与查询到部门的交集
				for(OrgAdministrativeInfoEntity en: orgList){
					smallAreaDepts.add(en.getCode());
				}
				//如果所选部门与所选小区不符合，则查询不到
				if(!smallAreaDepts.contains(billReceivableDto.getDepartment())){
					throw new SettlementException("所选小区与所选部门不相符，查询不到数据！");
				}
			}else if(StringUtils.isNotBlank(billReceivableDto.getBigArea())){
				List<String> bigAreaDepts = new ArrayList<String>();
				//调用综合方法查询
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoEntityAllSubByCode(billReceivableDto.getBigArea());
				//循环部门，获取用户所管辖部门与查询到部门的交集
				for(OrgAdministrativeInfoEntity en: orgList){
					bigAreaDepts.add(en.getCode());
				}
				//如果所选部门与所选大区不符合，则查询不到
				if(!bigAreaDepts.contains(billReceivableDto.getDepartment())){
					throw new SettlementException("所选大区与所选部门不相符，查询不到数据！");
				}
			}
			targetLsit.add(billReceivableDto.getDepartment());
		//如果部门不存在，小区存在，则获取小区地下所有部门与该用户所管辖部门交集	
		} else if(StringUtils.isNotBlank(billReceivableDto.getSmallArea())){
			//调用综合方法查询
			List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoEntityAllSubByCode(billReceivableDto.getSmallArea());
			//循环部门，获取用户所管辖部门与查询到部门的交集
			for(OrgAdministrativeInfoEntity en: orgList){
				targetLsit.add(en.getCode());
			}
		//如果部门、小区都不存在，而大区存在，	则获取大区底下所有部门与该用户所管辖部门交集	
		}else if(StringUtils.isNotBlank(billReceivableDto.getBigArea())){
			//调用综合方法查询
			List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoEntityAllSubByCode(billReceivableDto.getBigArea());
			//循环部门，获取用户所管辖部门与查询到部门的交集
			for(OrgAdministrativeInfoEntity en: orgList){
				targetLsit.add(en.getCode());
			}
		//如果都不存在，则获取默认用户所管辖部门集合	
		}
		// 设置可查询部门结果集
		billReceivableDto.setDepartments(targetLsit);
	}
	
	private IBillReceivableService billReceivableService;
	
	/**
	 * 财务收支平衡消息 Service
	 */
	private ICreditMsgService creditMsgService;
	/**
	 * 标记、反标记应收单
	 * @author foss-pengzhen
	 * @date 2013-5-21 下午6:10:48
	 * @param billReceivableDto 
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.IBillReceivableQueryService#stampReceivable(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillReceivableDto)
	 */
	@Override
	@Transactional
	public void stampReceivable(String[] receivableNosArray,String stamp,CurrentInfo cInfo){
		//参数异常，不能进行标记操作！
		if(null == receivableNosArray || receivableNosArray.length == 0){
			throw new SettlementException("参数异常，不能进行标记操作！");
		}
		//设置有效的单据
		String active = FossConstants.ACTIVE;
		//声明实例一个应收单集合
		List<String> receivableNos = new ArrayList<String>();
		for(int i = 0; i < receivableNosArray.length; i++){
			if(StringUtils.isNotEmpty(receivableNosArray[i])){
				//添加应收单号集合中
				receivableNos.add(receivableNosArray[i]);
			}
		}
		//从库中获取最新的数据
		List<BillReceivableEntity> billReceivableEntities = billReceivableService.queryByReceivableNOs(receivableNos, active);
		
		if(CollectionUtils.isEmpty(billReceivableEntities)){
			throw new SettlementException("数据已被修改，不能进行标记操作！");
		}
		for(int i = 0; i < billReceivableEntities.size(); i ++){
			BillReceivableEntity billReceivableEntity = billReceivableEntities.get(i);
			// 应收单的付款方式：临欠
			if (!SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT
					.equals(billReceivableEntity.getPaymentType())) {// 临欠
				throw new SettlementException("应收单的付款方式不是临欠，不能进行标记操作！");
			}
			
			if(billReceivableEntity.getUnverifyAmount().compareTo(BigDecimal.ZERO) <= 0){
				throw new SettlementException("请选择未核销金额必须大于0的应收单进行标记操作！");
			}
			
			CreditMsgEntity entity = new CreditMsgEntity();
			entity.setId(UUIDUtils.getUUID());// ID
			entity.setType(SettlementDictionaryConstants.CREDIT_MSG_TYPE__STAMP);// 标记
			entity.setSourceBillNo(billReceivableEntity.getReceivableNo());
			entity.setCode(billReceivableEntity.getReceivableOrgCode());
			entity.setName(billReceivableEntity.getReceivableOrgName());
			entity.setCreditType(SettlementDictionaryConstants.CREDIT_MSG_CREDIT_TYPE__ORG);// 收支平衡类型
			BigDecimal amount = billReceivableEntity.getUnverifyAmount();
			if(StringUtils.equals(stamp,FossConstants.YES)){
				entity.setAmount(amount);
			}else if (StringUtils.equals(stamp,FossConstants.NO)){
				amount = amount.multiply(new BigDecimal(-1));
				entity.setAmount(amount);
			}
			entity.setCreateTime(new Date());
			entity.setStatus(SettlementDictionaryConstants.CREDIT_MSG_STATUS_NOT_EXECUTE);// 状态未执行
			
			creditMsgService.add(entity);// 保存财务收支平衡消息表
		}
		//声明实例一个应收单dto对象
		com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableDto dto = new 
				com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableDto();
		//添加最新的数据到对象中
		dto.setBillReceivables(billReceivableEntities);
		dto.setActive(active);
		dto.setStamp(stamp);
		billReceivableService.updateStampByNumbers(dto, cInfo);
	}
	
	/**
	 * @param billReceivableQueryDao
	 */
	public void setBillReceivableQueryDao(IBillReceivableQueryDao billReceivableQueryDao) {
		this.billReceivableQueryDao = billReceivableQueryDao;
	}

	
	/**
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	
	/**
	 * @param billReceivableService the billReceivableService to set
	 */
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	
	/**
	 * @param creditMsgService the creditMsgService to set
	 */
	public void setCreditMsgService(ICreditMsgService creditMsgService) {
		this.creditMsgService = creditMsgService;
	}
	
	
}
