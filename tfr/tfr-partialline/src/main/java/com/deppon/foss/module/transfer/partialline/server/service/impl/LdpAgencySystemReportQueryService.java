/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-partialline
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 落地配外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/server/service/impl/LdpAgencySystemReportQueryService.java
 * 
 *  FILE NAME     :LdpAgencySystemReportQueryService.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * * 
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 ******************************************************************************/

package com.deppon.foss.module.transfer.partialline.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.partialline.api.server.dao.ILdpAgencySystemReportQueryDao;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpAgencySystemReportQueryService;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpAgencySystemReportQueryDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpAgencySystemReportResultDto;
import com.deppon.foss.module.transfer.partialline.api.shared.exception.ExternalBillException;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 落地配全盘报表Service接口实现
 * 
 * @author ibm-liuzhaowei
 * @date 2013-07-30 下午3:30:16
 */
public class LdpAgencySystemReportQueryService implements ILdpAgencySystemReportQueryService {

	/**
	 * 日志打印对象声明
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LdpAgencySystemReportQueryService.class);

	/**
	 * 落地配全盘代理报表Dao
	 */
	private ILdpAgencySystemReportQueryDao ldpAgencySystemReportQueryDao;
	
	/**
	 * 查询运单入到达部门日期的
	 */
	private IStockService stockService;
	
	/**
	 * 落地配全盘报表Service接口实现--根据Dto查询应收单
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-30 下午3:40:16
	 * @return
	 */
	@Override
	public List<LdpAgencySystemReportResultDto> queryLdpAgencySystemReportByDto(int offset, int start, LdpAgencySystemReportQueryDto dto, CurrentInfo cInfo) {
		// Service执行的Log
		LOGGER.debug("entering service queryLdpAgencySystemReportByDto");
		
		// 传入对象非空判断
		if (null == dto) {
			// 如果没有值则抛出异常
			throw new ExternalBillException("传入参数不能为空");
		}
		if(StringUtils.isNotBlank(dto.getWaybillNo())){
			LdpAgencySystemReportQueryDto tempDto = new LdpAgencySystemReportQueryDto();
			tempDto.setWaybillNo(dto.getWaybillNo());
			dto = tempDto;
		}else{
			// 验证输入参数
			this.validateInputParameters(dto);
			dto.setLastLoadOrgCode(dto.getArriveOrgCode());
		}

		dto.setEmpCode(cInfo.getEmpCode());
		//运单的交接单类型为：落地配
		dto.setProductType(PartiallineConstants.HANDOVER_TYPE_LDP_HANDOVER);
		dto.setActive(FossConstants.ACTIVE);
		
		// 执行查询操作
		List<LdpAgencySystemReportResultDto> list = ldpAgencySystemReportQueryDao.queryLdpAgencySystemReportByDto(offset, start, dto);
		
		// 返回查询结果
		return queryResultDtoArriveTime(list);
	}
	

	/**
	 * 落地配全盘报表Service接口实现--根据Dto查询总记录条数
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-30 下午3:40:16
	 * @return
	 */
	@Override
	public long queryTotalRecordsInDBByDto(LdpAgencySystemReportQueryDto dto, CurrentInfo cInfo) {
		// Service执行的Log
		LOGGER.debug("entering service queryTotalRecordsInDBByDto");
		
		// 传入对象非空判断
		if (null == dto) {
			// 如果没有值则抛出异常
			throw new ExternalBillException("传入参数不能为空");
		}
		if(StringUtils.isNotBlank(dto.getWaybillNo())){
			LdpAgencySystemReportQueryDto tempDto = new LdpAgencySystemReportQueryDto();
			tempDto.setWaybillNo(dto.getWaybillNo());
			dto = tempDto;
		}else{
			// 验证输入参数
			this.validateInputParameters(dto);
			dto.setLastLoadOrgCode(dto.getArriveOrgCode());
		}
		
		dto.setEmpCode(cInfo.getEmpCode());
		//运单的交接单类型为：落地配
		dto.setProductType(PartiallineConstants.HANDOVER_TYPE_LDP_HANDOVER);
		dto.setActive(FossConstants.ACTIVE);
		
		// 返回查询结果
		return ldpAgencySystemReportQueryDao.queryTotalRecordsInDBByDto(dto);
	}

	/**
	 * 验证查询的条件
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-30 下午3:40:16
	 */
	private void validateInputParameters(LdpAgencySystemReportQueryDto dto) {
		// 传入对象非空判断
		if (null == dto) {
			// 如果没有值则抛出异常
			throw new ExternalBillException("传入参数不能为空");
		}
		// 开始日期非空校验
		if (null == dto.getStartBusinessDate()) {
			// 开始日期为空则抛出异常
			throw new ExternalBillException("开始业务日期不能为空");
		}
		// 结束日期非空校验
		if (null == dto.getEndBusinessDate()) {
			// 结束日期为空则抛出异常
			throw new ExternalBillException("结束业务日期不能为空");
		}
		// 判断开始日期是否小于结束日期
		if (dto.getStartBusinessDate() != null
				&& dto.getEndBusinessDate() != null) {
			Date startDate = DateUtils.truncate(dto.getStartBusinessDate(),
					Calendar.SECOND);
			Date endDate = DateUtils.truncate(dto.getEndBusinessDate(),
					Calendar.SECOND);
			if (startDate.after(endDate)) {
				throw new ExternalBillException("开始业务日期大于结束业务日期！");
			}
		}
		// 判断开始日期是否小于结束日期
		if (dto.getHandOverStartTime() != null
				&& dto.getHandOverEndTime() != null) {
			Date startDate = DateUtils.truncate(dto.getHandOverStartTime(),
					Calendar.SECOND);
			Date endDate = DateUtils.truncate(dto.getHandOverEndTime(),
					Calendar.SECOND);
			if (startDate.after(endDate)) {
				throw new ExternalBillException("开始外发时间大于结束外发时间！");
			}
		}
	}
	
	/**
	 * 根据运单号查询，运单入到达部门的时间
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-30 下午3:40:16
	 * @param list
	 * @return
	 */
	private List<LdpAgencySystemReportResultDto> queryResultDtoArriveTime(List<LdpAgencySystemReportResultDto> list){
		//判断参数是否为空
		if(CollectionUtils.isNotEmpty(list)){
			//声明实例一个list对象
			List<LdpAgencySystemReportResultDto> resultDtos=new ArrayList<LdpAgencySystemReportResultDto>();
			for(LdpAgencySystemReportResultDto dto:list){
				
				//调用中转接口
				List<InOutStockEntity> outStocks=this.stockService.queryInStockInfo(dto.getWaybillNo(), null, 
						dto.getLastLoadOrgCode(),//到达部门编码
						dto.getBillDate());
				if(CollectionUtils.isNotEmpty(outStocks)){
					InOutStockEntity outStockEntity=outStocks.get(outStocks.size()-1);
					dto.setArriveTime(outStockEntity.getInOutStockTime());
				}
				resultDtos.add(dto);
			}
			return resultDtos;
		}
		return list;
	}

	/**
	 * @param agencySystemReportQueryDao
	 */
	public void setLdpAgencySystemReportQueryDao(
			ILdpAgencySystemReportQueryDao ldpAgencySystemReportQueryDao) {
		this.ldpAgencySystemReportQueryDao = ldpAgencySystemReportQueryDao;
	}

	
	/**
	 * @param stockService the stockService to set
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

}
