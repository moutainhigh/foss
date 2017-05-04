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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/server/service/impl/UninputLdpExternalBillService.java
 * 
 *  FILE NAME     :UninputLdpExternalBillService.java
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
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-partialline
 * PACKAGE NAME: com.deppon.foss.module.transfer.partialline.server.service.impl
 * FILE    NAME: UninputPartiallineService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.partialline.server.service.impl;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IDpjzSignInMsgDao;
import com.deppon.foss.module.transfer.partialline.api.server.service.IDpjzSignInMsgService;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.DpjzSignInDetialBillEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.FossToDopCheckResultResponse;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.DpjzSignInDetailDto;
import com.deppon.foss.module.transfer.partialline.api.shared.exception.ExternalBillException;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 家装送装明细及签收确认信息Service
 * 
 * @author foss-lln
 * @date 2015-12-05 上午9:18:36
 */
public class DpjzSignInMsgService implements IDpjzSignInMsgService {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DpjzSignInMsgService.class);
	
	/**
	 * 家装送装明细及签收确认信息Dao
	 */
	private IDpjzSignInMsgDao dpjzSignInMsgDao;

	/**
	 * FOSS接收DOP推送的家装送装明细及签收确认信息
	 */
	@Override
	public void addDpjzSignResultForTfr(DpjzSignInDetialBillEntity dpjzSignEntity) {
		
		if(dpjzSignEntity!=null){
			/**
			 * 校验该运单，DOP是否已经推送过：
			 * 		1、已经推送，但是未审核，之前的数据作废 active=N;新数据active=Y
			 * 		2、已经推送，但是已经审核同意，新数据active='N'；
			 *      3、已经推送，审核未同意时，老数据active='N'，新数据active=‘Y’
			 *      4、没有推送过的数据 新数据active='Y'
			 */
			//查询出 DOP已经推送过来，并且有效的数据
			List<DpjzSignInDetailDto> entity=dpjzSignInMsgDao.queryWayBillNoIsExist(dpjzSignEntity);
			if(null!=entity && entity.size()>0){
				for(DpjzSignInDetailDto dto:entity){
					//已经推送，但是已经审核同意，新数据active='N'
					if(StringUtils.equals(dto.getStatus(), PartiallineConstants.PARTIALLINE_AUDITSTATUS_PASS)){
						dpjzSignEntity.setActive(FossConstants.NO);
						//新数据审核状态是status= 未审核
						dpjzSignEntity.setStatus(PartiallineConstants.PARTIALLINE_AUDITSTATUS_UNCOMMITTED);
						
						//审核未同意时，老数据active='N'，新数据active=‘Y’
					}else if(StringUtils.equals(dto.getStatus(), PartiallineConstants.PARTIALLINE_AUDITSTATUS_NOTPASS)){
						//新数据为active='Y'
						dpjzSignEntity.setActive( FossConstants.YES);
						//新数据审核状态是status= 未审核
						dpjzSignEntity.setStatus(PartiallineConstants.PARTIALLINE_AUDITSTATUS_UNCOMMITTED);
						//变更已经推送的数据
						//老数据有效性 active=‘N’
						dpjzSignInMsgDao.updateDpjzSignInStatus(dto.getId(),FossConstants.NO);
						
						//已经推送，但是未审核，之前的数据作废 active=N;新数据active=Y
					}else if(StringUtils.equals(dto.getStatus(), PartiallineConstants.PARTIALLINE_AUDITSTATUS_UNCOMMITTED)){
						//新数据active=Y
						dpjzSignEntity.setActive(FossConstants.YES);
						//新数据审核状态是status= 未审核
						dpjzSignEntity.setStatus(PartiallineConstants.PARTIALLINE_AUDITSTATUS_UNCOMMITTED);
						
						//老数据 active='N'
						//老数据有效性为N
						dpjzSignInMsgDao.updateDpjzSignInStatus(dto.getId(),FossConstants.NO);
						
					}
				}
			}else{
				//没有推送过数据,直接插入
				dpjzSignEntity.setCheckOpinion("DOP新推送数据"+DateUtils.convert(new Date(),DateUtils.DATE_TIME_FORMAT));
				//审核状态是 未审核
				dpjzSignEntity.setStatus(PartiallineConstants.PARTIALLINE_AUDITSTATUS_UNCOMMITTED);
			}
			//生成ID
			dpjzSignEntity.setId(UUIDUtils.getUUID());
			//保存数据
			dpjzSignInMsgDao.insert(dpjzSignEntity);
		}else{
			 throw new ExternalBillException("FOSS接收DOP推送的家装送装明细及签收确认信息为空");
		}

	}

	/**
	 *
	 * 查询家装送装明细及签收确认信息
	 * 查询规则
	 * 1、若输入框中运单号信息和起止时间冲突，以输入框中输入信息为准； 
	 * 	  （输入框中运单号需在foss储存表中存在、非已中止/已作废、且属于家装类运单的）
	 * 2、若运单号为空查询条件同时满足以下
	 * 		（1）FOSS接收到的信息存储的时间在起止时间内；
	 * 		（2）运单收货部门为当前登陆人所在营业部；
	 * 		（3）非已中止/已作废，且当前标记“特殊增值服务”的家装类运单(DOP限制)
	 * @author foss-lln-269701
	 * @date 2015-12-05 上午9:18:36
	 */ 
	@Override
	public List<DpjzSignInDetailDto> querydpjzSignInDetailBills(
			DpjzSignInDetailDto detailDto, int limit, int start) {
		return dpjzSignInMsgDao.querydpjzSignInDetailBills(detailDto, limit, start);
	}
	
	/**
	 * 查询家装送装明细及签收确认信息总条数
	 * 
	 * @author foss-lln--269701
	 * @date 2015-12-05  上午11:00:11
	 */
	@Override
	public Long querydpjzSignInDetailBillsCount(DpjzSignInDetailDto detailDto) {
		return dpjzSignInMsgDao.querydpjzSignInDetailBillsCount(detailDto);
	}
	
	/**
	 * 核对 德邦家装送装信息以及签收确认
	 * 修改状态--同意
	 * @author foss-lln--269701
	 * @date 2015-12-05  上午11:00:11
	 */
	@Override
	public int dpjzSignInDetialCheckPass(DpjzSignInDetialBillEntity entity) {
		//最后操作人--当前登录人name
		entity.setLastOperUser(FossUserContext.getCurrentInfo().getEmpName());
		//最后操作人--当前登录人code
		entity.setLastOperUserCode(FossUserContext.getCurrentInfo().getEmpCode());
		//核对意见--同意
		entity.setStatus(PartiallineConstants.PARTIALLINE_AUDITSTATUS_PASS);
		//最后操作时间
		entity.setLastOperTime(new Date());
		//反写核对结果给DOP
		checkMsgToDop(entity);
		return dpjzSignInMsgDao.dpjzSignInDetialCheck(entity);
	}
	/**
	 * 核对 德邦家装送装信息以及签收确认
	 * 修改状态--不同意
	 * @author foss-lln--269701
	 * @date 2015-12-05  上午11:00:11
	 */
	@Override
	public int dpjzSignInDetialCheckNotPass(DpjzSignInDetialBillEntity entity) {
		//最后操作人--当前登录人name
		entity.setLastOperUser(FossUserContext.getCurrentInfo().getEmpName());
		//最后操作人--当前登录人code
		entity.setLastOperUserCode(FossUserContext.getCurrentInfo().getEmpCode());
		//最后操作时间
		entity.setLastOperTime(new Date());
		//核对意见--不同意
		entity.setStatus(PartiallineConstants.PARTIALLINE_AUDITSTATUS_NOTPASS);
		
		//反写核对结果给DOP
		checkMsgToDop(entity);
		return dpjzSignInMsgDao.dpjzSignInDetialCheck(entity);
	}
	
	/**
	 * 自动审核 德邦家装送装信息
	 */
	@Override
	public int updateDpjzSignInMsgWaybill(DpjzSignInDetailDto entity) {
		//最后审核人--自动
		entity.setLastOperUser("自动");
		//最后审核人code--自动
		entity.setLastOperUserCode("自动");
		//自动审核为 同意
		entity.setStatus(PartiallineConstants.PARTIALLINE_AUDITSTATUS_PASS);
		//审核意见为自动
		entity.setCheckOpinion("自动审核");
		//最后操作时间
		entity.setLastOperTime(new Date());
		
		DpjzSignInDetialBillEntity dpEntity=new DpjzSignInDetialBillEntity();
		//dopId
		dpEntity.setDopId(entity.getDopId());
		//运单号
		dpEntity.setWaybillNo(entity.getWaybillNo());
		//审核状态
		dpEntity.setStatus(entity.getStatus());
		//审核意见
		dpEntity.setCheckOpinion(entity.getCheckOpinion());
		//最后操作人
		dpEntity.setLastOperUserCode(entity.getLastOperUserCode());
		//最后操作时间
		dpEntity.setLastOperTime(entity.getLastOperTime());
		
		//反写核对结果给DOP
		checkMsgToDop(dpEntity);
		return dpjzSignInMsgDao.updateDpjzSignInMsgWaybill(entity);
	}
	 	
	/**
	 * 反写核对结果给DOP
	 * @param entity
	 */
	private void checkMsgToDop(DpjzSignInDetialBillEntity entity){
		//FOSS审核结果给DOP
		FossToDopCheckResultResponse response=new FossToDopCheckResultResponse();
		//dopId
		response.setUniqueRequestId(entity.getDopId());
		//运单号
		response.setWaybillNo(entity.getWaybillNo());
		//审核状态
		//如果是审核同意 给DOP传Y 否则传N
		if(StringUtils.equals(entity.getStatus(), PartiallineConstants.PARTIALLINE_AUDITSTATUS_PASS)){
			response.setAuditStatus("Y");
		}else{
			response.setAuditStatus("N");
		}
		//审核意见
		response.setAuditOpinion(entity.getCheckOpinion());
		//最后审核人
		response.setAuditBy(entity.getLastOperUserCode());
		
		System.out.println("========"+entity.getLastOperTime());
		//最后审核时间
		response.setAuditTime(DateUtils.convert(entity.getLastOperTime(),DateUtils.DATE_TIME_FORMAT));
		System.out.println("========"+response.getAuditTime());
		
		LOGGER.info("调用DOP接口同步德邦家装送装信息运单号："+entity.getWaybillNo()+"DOP唯一请求ID："+entity.getDopId()+"信息开始！");
		//List<DpjzSignInDetialBillEntity> list=new ArrayList<DpjzSignInDetialBillEntity>();
		//list.add(entity);
		try{
			String requestStr = JSONObject.fromObject(response).toString();
			
			FossToDopService.getInstatce().checkResultData(requestStr);
	    	
	    	LOGGER.info("调用DOP接口同步德邦家装送装信息运单号："+entity.getWaybillNo()+"DOP唯一请求ID："+entity.getDopId()+"信息结束！");
		}catch (Exception e) {
			LOGGER.error("调用DOP接口同步德邦家装送装信息运单号："+entity.getWaybillNo()+"DOP唯一请求ID："+entity.getDopId()+"信息失败："+e.getStackTrace().toString());
			LOGGER.error("核对德邦家装送装信息出错："+ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	/**
	 * 收到DOP推送的信息后，给收货部门发送在线通知;
	 * 根据运单号查询运单表 获取收货部门信息
	 */
	@Override
	public DpjzSignInDetailDto queryDpjzReceiveMsgWaybill(String waybillNo){
		//获取查询结果
		List<DpjzSignInDetailDto> list=dpjzSignInMsgDao.queryDpjzReceiveMsgWaybill(waybillNo);
		// 防止存在多条数据，取第一条数据记录
		return list.get(0);
	}
	
	/**
	 * @param dpjzSignInMsgDao the dpjzSignInMsgDao to set
	 */
	public void setDpjzSignInMsgDao(IDpjzSignInMsgDao dpjzSignInMsgDao) {
		this.dpjzSignInMsgDao = dpjzSignInMsgDao;
	}
	
}