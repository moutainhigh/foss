/**
 *  initial comments.
 */
/*******************************************************************************
 * 
 * 
 * 
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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-airfreight
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/server/service/impl/AirQueryModifyPickupbillService.java
 *  
 *  FILE NAME          :AirQueryModifyPickupbillService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 SR-1	操作员打开界面：
1、	空运总调默认为当前操作人所在部门，根据用户数据权限可以修改，不可为空；

2、	到达网点默认为空；

3、	制单起始时间默认为前一天23:59:59，截止时间默认为当天23:59:59，不为空，最大时间段为30天；

4、	目的站默认为空，输入范围为空运线路信息中的城市名称；

5、	航空公司默认为全部，来源于航空公司二字代码；

6、	正单号默认为空；

7、	默认不执行查询操作；


SR-2	点击查询按钮，执行查询操作，默认按制单时间降序排列；


SR-3	点击重置按钮，重置查询条件为默认值；


SR-4	点击操作列中的编辑控件，可对合大票清单进行修改，修改规则：
1、	只有同录入人相同部门的人员才可修改；

2、	对账前可修改，对账后不能修改，需要调用结算接口；



SR-5	航空公司和正单号不可修改，其他字段修改规则同新增规则，无特殊要求；

 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.agency.api.server.service.IAirJointTicketService;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirQueryModifyPickupbillDao;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTransPickupBillDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirChangeInventoryService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirDispatchUtilService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirQueryModifyPickupbillService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTransPickupBillService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillToCubcService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IPushAirPickUpInfoService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillLogEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirRevisebillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirRevisebillToCubcEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTranDataCollectionEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillTempEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.SerialEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirPickupbillDetailsavenDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.exception.AirTransPickupBillException;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirChangeInventoryException;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestBatchResult;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestResponse;
import com.deppon.foss.module.transfer.common.api.server.service.IFossToCubcService;
import com.deppon.foss.module.transfer.common.api.shared.define.CUBCGrayContants;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferDictionaryConstants;
import com.deppon.foss.module.transfer.common.api.shared.dto.GrayParameterDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcValidationAirJoinTicketResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseExternalBillDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.CUBCGrayUtil;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 查询修改合大票service实现 
 * @author 099197-foss-zhoudejun
 * @date 2012-12-18 上午9:18:01
 * 
 */
public class AirQueryModifyPickupbillService implements
		IAirQueryModifyPickupbillService {
	/**
	 * 日志
	 */
	private static final Logger LOGGER=LoggerFactory.getLogger(AirQueryModifyPickupbillService.class);
	/**
	 * 注入查询修改合大票dao
	 */
	private IAirQueryModifyPickupbillDao airQueryModifyPickupbillDao;
	
	/**
	 *  变更清单service
	 */
	private IAirChangeInventoryService airChangeInventoryService;
	
	/**
	 * 结算合票预收应付接口
	 */
	private IAirJointTicketService airJointTicketService;
	
	/**
	 * 新增合大票service
	 */
	private IAirTransPickupBillService airTransPickupBillService;
	
	/**
	 * 运单签收service
	 */
	IWaybillSignResultService waybillSignResultService;
	
	/** 查找空运总调 service*/
	private IAirDispatchUtilService airDispatchUtilService;
	
	/**
	 * 运单Service
	 */
	private IWaybillManagerService waybillManagerService;
	
	/** 注入中转提货清单dao */
	private IAirTransPickupBillDao airTransPickupBillDao;
	
	/**FOSS推送数据至OPP Service**/
	private IPushAirPickUpInfoService pushAirPickUpInfoService;
	
	/**灰度工具类*/
	private CUBCGrayUtil cubcUtil;
	
	public void setCubcUtil(CUBCGrayUtil cubcUtil) {
		this.cubcUtil = cubcUtil;
	}

	/** FOSS异步推送数据至CUBC Service*/
	private IAirWaybillToCubcService airWaybillToCubcService;
	public void setAirWaybillToCubcService(
			IAirWaybillToCubcService airWaybillToCubcService) {
		this.airWaybillToCubcService = airWaybillToCubcService;
	}
	
	/**
	 * 365455
	 */
	private IFossToCubcService fossToCubcService;
	
	public void setFossToCubcService(IFossToCubcService fossToCubcService) {
		this.fossToCubcService = fossToCubcService;
	}
	/**
	 * 修改、保存、删除 合大票清单 
	 * @param irTranDataCollectionEntity 需要修改的合票清单
	 * @param airPickupbillDetailList 新合大票合票清单明细
	 * @return (0或1)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-18 上午9:03:53
	 */
	@Override
	@Transactional
	public int modifySaveDeleteAirPickupbillDetails(AirTranDataCollectionEntity airTranDataCollectionEntity,
		List<AirPickupbillDetailEntity> airPickupbillDetailList) {
		//modify by liuzhaowei 2013-06-25 begin 对运单的操作判断放到后台进行判断，原先在前台做判断有问题，导致有重复运单（在前台对运单先删除，在添加）
		
		Long startTime=new Date().getTime();

		if(airTranDataCollectionEntity == null){
			throw new TfrBusinessException(
					AirfreightConstants.AIRFREIGHT_EXCEPTION_AIRPICKUPBILL_ISNULL);
		}
		if(CollectionUtils.isEmpty(airPickupbillDetailList)){
			throw new TfrBusinessException(
					AirfreightConstants.AIRFREIGHT_EXCEPTION_AIRPICKUPBILLDETAIL);
		}
		LOGGER.info("修改合大票 {"+airTranDataCollectionEntity.getAirWaybillNo()+"} 开始");
		
		LOGGER.info("======================修改合大票清单开始时间："+new Date()+"==========================");
		
     //BUG-56921 零担&快递集成环境-修改合大票清单界面一个未签收的单据可以同时添加在多个合大票清单里。
	 //由于存在不是正单的运单一也可以一起做进和票，取消限制 
		/*List<String> waybillNos=pointsSingleJointTicketService.queryWaybillNosByAirWaybillId(airTranDataCollectionEntity.getAirWaybillNo());
		
		for(int i=0;i<airPickupbillDetailList.size();i++)
		{
			//判断明细中的正单号是否与当前的正单号相同
			 if(!waybillNos.contains(airPickupbillDetailList.get(i).getWaybillNo()))
			{
				throw new TfrBusinessException("运单："+airPickupbillDetailList.get(i).getWaybillNo()
						+"不能添加在合票："
						+airTranDataCollectionEntity.getAirWaybillNo()+" 中");
			}
			
		}*/
		
		//航空正单号
		String airWaybillNo = airTranDataCollectionEntity.getAirWaybillNo();
		//合大票id
		String airPickupbillId = airTranDataCollectionEntity.getAirPickupbillId();
		//操作类型  提交Y/暂存N
		String status = airTranDataCollectionEntity.getStatus();
		
		if(StringUtils.isEmpty(status)){
			throw new TfrBusinessException("获取提交状态值失败,请重新尝试");
		}
		
		//合大票新增运单明细
		List<AirPickupbillDetailEntity> addPickupDetailList = new ArrayList<AirPickupbillDetailEntity>();
		//合大票删除运单明细
		List<String> deletePickupDetailList = new ArrayList<String>();
		//删除列表,用于调用结算接口
		List<String> stlDeleteList = new ArrayList<String>();
		//修改列表,用于调用结算接口
		List<AirPickupbillDetailEntity> stlModifyList = new ArrayList<AirPickupbillDetailEntity>();
		//新增列表,用于调用结算接口
		List<AirPickupbillDetailEntity> stlAddList = new ArrayList<AirPickupbillDetailEntity>();		
		//根据正单号获取原始合大票清单
		AirPickupbillEntity oldAirPickupbillEntity = airTransPickupBillService.queryAirPickupbillEntity(airWaybillNo);
		//原合大票提交状态
		String oldStatus = oldAirPickupbillEntity.getStatus();
		boolean flag = false;
		//表示从暂存变为提交
		if(FossConstants.NO.equals(oldStatus) && FossConstants.YES.equals(status)){
			flag = true;
		}
		
		//新合大票清单信息
		AirPickupbillEntity newAirPickupbillEntity = new AirPickupbillEntity();
		//给新合大票清单赋值
		BeanUtils.copyProperties(oldAirPickupbillEntity, newAirPickupbillEntity);
		//设置修改后的件数
		newAirPickupbillEntity.setWaybillQtyTotal(airTranDataCollectionEntity.getBillNoTotal());
		//设置修改后的流水号数量
		newAirPickupbillEntity.setAirPickQtyTotal(airTranDataCollectionEntity.getAirPickQtyTotal());
		//设置计费重量
		newAirPickupbillEntity.setGrossWeightTotal(airTranDataCollectionEntity.getGrossWeightTotal());
	
		newAirPickupbillEntity.setDeliverFeeTotal(airTranDataCollectionEntity.getDeliverFeeTotal());
		newAirPickupbillEntity.setArrivalFeeTotal(airTranDataCollectionEntity.getArrivalFeeTotal());
		newAirPickupbillEntity.setCollectionFeeTotal(airTranDataCollectionEntity.getCollectionFeeTotal());
		//设置到达部门
		newAirPickupbillEntity.setDestOrgCode(airTranDataCollectionEntity.getDestOrgCode());
		//设置到达部门名称
		newAirPickupbillEntity.setDestOrgName(airTranDataCollectionEntity.getDestOrgName());
		//设置修改时间
		newAirPickupbillEntity.setModifyTime(new Date());
		//设置修改人
		newAirPickupbillEntity.setModifyUserCode(FossUserContext.getCurrentInfo().getEmpCode());
		//设置修改人名称
		newAirPickupbillEntity.setModifyUserName(FossUserContext.getCurrentInfo().getEmpName());
		//2013-07-25 BUG-47499 	合大票清单修改后时间不更新  
		// author :wqh
		newAirPickupbillEntity.setFlightDate(airTranDataCollectionEntity.getTransferDate());
		newAirPickupbillEntity.setStatus(status);
		
		//修改合大票主表信息
		LOGGER.info("修改合大票 {"+newAirPickupbillEntity.getAirWaybillNo()+"} 主表信息 开始...."+new Date());
		airQueryModifyPickupbillDao.modifyAirPickupbill(newAirPickupbillEntity);
		LOGGER.info("修改合大票 {"+newAirPickupbillEntity.getAirWaybillNo()+"} 主表信息 结束...."+new Date());
		
		Map<String,AirPickupbillDetailEntity> oldDetailMap = new HashMap<String, AirPickupbillDetailEntity>();
		Map<String,AirPickupbillDetailEntity> newDetailMap = new HashMap<String, AirPickupbillDetailEntity>();
		//此次合大票操作相关运单，包含在前台新增，修改的运单（用于查询这些运单是否已签收）
		Map<String,String> signWaybillNoMap = new HashMap<String, String>();
		List<String> detailWaybillNoList = new ArrayList<String>();
		//此次合大票操作在前台删除的运单（用于查询这些运单是否已签收）
		List<String> deleteWaybillNoList = new ArrayList<String>();
		//合大票原始运单明细信息
		List<AirPickupbillDetailEntity> oldPickupbillDetailList = queryAirPickupbillDetailListByPrimaryId(airPickupbillId);
	
		
		//把newList转换成map
		for(AirPickupbillDetailEntity newDetailEntity : airPickupbillDetailList){
			newDetailMap.put(newDetailEntity.getId(), newDetailEntity);
			//此次合大票操作新增和修改的运单
			detailWaybillNoList.add(newDetailEntity.getWaybillNo());
		}
		//把list转换成map
		for(AirPickupbillDetailEntity oldDetailEntity : oldPickupbillDetailList){
			//把oldList转换成map
			oldDetailMap.put(oldDetailEntity.getId(), oldDetailEntity);
			//如果新list明细里不包含oldList里的合票明细，说明该明细被删除
			if(!newDetailMap.containsKey(oldDetailEntity.getId())){
				//中转删除集合
				deletePickupDetailList.add(oldDetailEntity.getId());
				//调用结算 删除集合
				stlDeleteList.add(oldDetailEntity.getWaybillNo());
				//此次合大票操作删除的运单
				deleteWaybillNoList.add(oldDetailEntity.getWaybillNo());
			}
		}
		List<String> cubcOrfossWBs = new ArrayList<String>();
		for(AirPickupbillDetailEntity newDetailEntity : airPickupbillDetailList){
			cubcOrfossWBs.add(newDetailEntity.getWaybillNo());
			//如果oldList明细里不包含newList里的合票明细，说明该明细新增
			if(!oldDetailMap.containsKey(newDetailEntity.getId())){
				newDetailEntity.setAirWaybillNo(airWaybillNo);
				//中转的新增运单集合
				addPickupDetailList.add(newDetailEntity);
				//调用结算 新增集合
				stlAddList.add(newDetailEntity);
			}
		}
		//add 2013-07-03 修改合大票时如果删除的运单已经签收则报错，不允许删除已签收的运单.如果修改的运单有已经签收的则在前台不允许修改运单信息，在后台也不会调用结算接口。
		//modify 311396 wwb 2016-9-9 14:29:45 对已提交的合大票校验，暂存提交的允许删除签收运单
		if(deleteWaybillNoList.size() > 0 && !FossConstants.NO.equals(oldStatus)){
			//查询已删除运单是否已签收
			LOGGER.info("修改合大票 信息 调签收接口开始时间："+new Date());

			List<String> delWaybillNoSignList = waybillSignResultService.queryWaybillSignResultWaybillNos(deleteWaybillNoList);
			
			LOGGER.info("修改合大票 信息 调签收接口结束时间："+new Date());

			if(delWaybillNoSignList != null && delWaybillNoSignList.size() > 0){
				//拼接已经签收的运单号
				StringBuffer delSignNos = new StringBuffer();
				for(int i = 0;i<delWaybillNoSignList.size();i++){
					delSignNos.append(delWaybillNoSignList.get(i)).append(",");
				}
				throw new TfrBusinessException("修改合大票不能删除已签收的运单！" + delSignNos.substring(0,delSignNos.length()-1));
			}
		}
		if(!FossConstants.NO.equals(oldStatus)){
			//已提交合票判断修改签收
			if(detailWaybillNoList.size() > 0){
				//查询修改运单是否已签收 
				LOGGER.info("修改合大票 信息2  调签收接口开始时间："+new Date());
				
				List<String> waybillNoSignList = waybillSignResultService.queryWaybillSignResultWaybillNos(detailWaybillNoList);
				LOGGER.info("修改合大票 信息2  调签收接口开始时间："+new Date());
				
				for(int j=0;j<waybillNoSignList.size();j++){
					signWaybillNoMap.put(waybillNoSignList.get(j), waybillNoSignList.get(j));
				}
				
			}
		}
		//修改变更日志
		List<AirRevisebillDetailEntity> airRevisebillDetailList = new ArrayList<AirRevisebillDetailEntity>();
		//根据合票明细list查询符合条件的运单原始信息,对已提交状态合票处理
		if(!FossConstants.NO.equals(oldStatus)){
			stlModifyList = getOriginalAirPickupbillList(airPickupbillDetailList,airRevisebillDetailList,signWaybillNoMap);
		}
		
		
		//处理新增合大票明细赋值 ，以及对应的大票清单id
		addPickupDetailList = addList(addPickupDetailList,airPickupbillId);
		//如果新增合大票清单不为空，则更新变更清单日志
		if(!CollectionUtils.isEmpty(addPickupDetailList)){
			//批量新增合大票明细list 
			LOGGER.info("批量新增合大票明细list 开始时间："+new Date());

			//@date 2016-05-21 新增合大票清单流水
			//@author 269701--lln
			addPickupDetailList=addSerialList(addPickupDetailList);
			airQueryModifyPickupbillDao.addAirPickupbillDetailList(addPickupDetailList);
			//对已提交的合票操作
			if(!FossConstants.NO.equals(oldStatus)){
				//处理变更清单logger日志
				List<AirRevisebillDetailEntity> addAirRevisebillDetailList = 
						addaddAirRevisebillDetailList(addPickupDetailList,airPickupbillId);
				//保存变更清单logger
				airChangeInventoryService.addaddAirRevisebillDetailList(addAirRevisebillDetailList);
				
				LOGGER.info("批量新增合大票明细list 结束时间："+new Date());
			}

		}
		
		//修改合大票清单明细数据
		LOGGER.info("修改合大票清单明细数据 开始时间："+new Date());
		
		airQueryModifyPickupbillDao.modifyAirPickupbillDetailList(airPickupbillDetailList);
		
		LOGGER.info("修改合大票清单明细数据 结束时间："+new Date());

		//判断删除列表非空
		if(!CollectionUtils.isEmpty(deletePickupDetailList)){
			airQueryModifyPickupbillDao.deleteAirPickupbillDetailList(deletePickupDetailList);
			//@date 2016-05-21 批量删除该运单对应的合大票清单流水
			//@author 269701--lln
			if(deleteWaybillNoList.size()>0){
				airQueryModifyPickupbillDao.deleteAirPickupbillSerialList(deletePickupDetailList);
			}
		}
		
		//判断变更明细列表非空,上面已经判断必须是已提交的合票做操作
		if(!CollectionUtils.isEmpty(airRevisebillDetailList)){
			airChangeInventoryService.addaddAirRevisebillDetailList(airRevisebillDetailList);
		}
	
		/**
		 * BUG-51170 运单发更改后还能做合票，导致财务单据不对
		 * @desc 保存合票之前判断运输性质是否为空运
		 * @author wqh 
		 * @date   2013-08-19
		 * 
		 * */
		List<String> waybillNos=new ArrayList<String>();
		
		if(!CollectionUtils.isEmpty(stlAddList)&&stlAddList.size()>0){
			for(int i=0;i<stlAddList.size();i++){
				String waybillNo=stlAddList.get(i).getWaybillNo().trim();
				waybillNos.add(waybillNo);
				/*WaybillEntity waybillEntry=waybillManagerService.queryWaybillBasicByNo(waybillNo);*/
				
				
			}
			if(waybillNos.size()>0){
				LOGGER.info("修改合大票查询运单信息开始时间："+new Date());
				List<WaybillEntity> waybillEntityList=waybillManagerService.queryWaybillBasicByNoList(waybillNos);
				LOGGER.info("修改合大票查询运单信息结束时间："+new Date());

				if(!CollectionUtils.isEmpty(waybillEntityList)&&waybillEntityList.size()>0){
					/**
					   * 增加商务专递产品类型
					   * by wqh
					   * 2015-09-06
					   * */
					  List<String> airProductCodes=new ArrayList<String>();
					  airProductCodes.add(AirfreightConstants.AIR_PROCDUCT_CODE);
					  airProductCodes.add(AirfreightConstants.AIR_PACKAGE_PROCDUCT_CODE);

					for(WaybillEntity waybill:waybillEntityList){
						if(!airProductCodes.contains(waybill.getProductCode())){
							throw new TfrBusinessException("运单："+waybill+" 不是空运运单，删除后再保存合票单据");
						}
					}
				}
				
			}else{
				LOGGER.info("运单不存在！");
				throw new TfrBusinessException("运单不存在！");
			}
			
			
		}
		//311396 wwb 暂存转提交，直接使用原合票直接保存的那种调用
		if(flag){
			/////335284 cubc-gray
			GrayParameterDto parDto_ = new GrayParameterDto();
			parDto_.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
			parDto_.setSourceBillNos( cubcOrfossWBs.toArray(new String[cubcOrfossWBs.size()]) );
			VestResponse vestResponseDto_ = cubcUtil.getUcbcGrayData(parDto_, new Throwable());
			List<String> fossWBs = new ArrayList<String>(), cubcWBs = new ArrayList<String>();
			for (VestBatchResult r : vestResponseDto_.getVestBatchResult()) {
				if (CUBCGrayContants.SYSTEM_CODE_FOSS.equals(r.getVestSystemCode())) {
					fossWBs = r.getVestObject();
				} else {
					cubcWBs = r.getVestObject();
				}
			}
			if (fossWBs.size() > 0) {
				List<AirPickupbillDetailEntity> fList = matchAirList(airPickupbillDetailList, fossWBs);
				try {
					//调用结算预收应付接口
					LOGGER.info("新增合大票清单【调用结算预收应付接口】开始时间："+new Date());
					CurrentInfo currentInfo= FossUserContext.getCurrentInfo();
					String deptCode = FossUserContext.getCurrentDeptCode();
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
					airJointTicketService.addAirJointTicket(newAirPickupbillEntity, fList, new CurrentInfo(currentInfo.getUser(),orgAdministrativeInfoEntity));
					LOGGER.info("新增合大票清单【调用结算预收应付接口】结束时间："+new Date());
				} catch (SettlementException e) {
					LOGGER.info("{新增合票清单调用结算接口出现异常，异常信息：}"+e.getErrorCode());
					throw new TfrBusinessException("{新增合票清单调用结算接口出现异常，异常信息：}"+e.getErrorCode());
				}
			}
			if (cubcWBs.size() > 0) {
				List<AirPickupbillDetailEntity> cList = matchAirList(airPickupbillDetailList, cubcWBs);
				/*
				 * 新增合大票清单至CUBC
				 * @author 316759-foss-RuipengWang
				 */
				try {
					// 同步
					AirRevisebillToCubcEntity airRevisebillToCubcEntity = new AirRevisebillToCubcEntity();
					airRevisebillToCubcEntity.setAirPickupbillEntity(newAirPickupbillEntity);
					airRevisebillToCubcEntity.setStlModifyList(cList);
					String requestStr = JSONObject.toJSONString(airRevisebillToCubcEntity);
					LOGGER.info("推送给CUBC的参数 requestStr = " + requestStr);
					airWaybillToCubcService.pushAddAirRevisebill(requestStr);
				} catch (BusinessException e) {
					throw new AirTransPickupBillException(e.getErrorCode());
				}
			}
		}
		
		//311396 wwb 修改为只发生在已提交的合大票进行提交
		if(!FossConstants.NO.equals(oldStatus)){
			//modify 2013-07-02  结算组提出改造接口,把新增的运单放到addlist中,删除的放到deletelist中,金额发生变化的放到modifylist中
			//调用结算接口，修改空运合大票明细
			//参数1： AirPickupbillEntity 原合大票清单
			//参数2：AirPickupbillEntity 新合大票清单
			//参数3：addedDetails 新增运单明细
			//参数4：modifiedDetails 修改明细(送货费金额发生变化的)
			//参数5：removedDetails 删除运单明细
			//参数6：CurrentInfo 当前用户信息
			if(stlAddList.size() >0 || stlModifyList.size() > 0 || stlDeleteList.size() > 0){
		  		//根据当前部门取空运总调
				String deptCode = FossUserContext.getCurrentDeptCode();
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
				UserEntity user = FossUserContext.getCurrentInfo().getUser();
				//BUG-49261 扑捉调用结算接口时的异常信息
				//@author wqh
				//@date 2013-08-05
				//封装运单List，传给灰度判断
				List <String> wayBillList = new ArrayList<String>();
				//添加运单列表
				for (AirPickupbillDetailEntity addEntity: stlAddList) {
					wayBillList.add(addEntity.getWaybillNo());
				}
				//修改运单列表
				for (AirPickupbillDetailEntity modifyEntity: stlModifyList) {
					wayBillList.add(modifyEntity.getWaybillNo());
				}
				//删除运单列表
				wayBillList.addAll(stlDeleteList);
				// 灰度实体对象封装
				//封装灰度实体，类型是运单
				GrayParameterDto parDto = new GrayParameterDto();
				parDto.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
				parDto.setSourceBillNos((String []) wayBillList.toArray(new String [wayBillList.size()]));
				//调用灰度
				VestResponse vestResponseDto = cubcUtil.getUcbcGrayData(parDto, new Throwable());
				//STL CUBC调用标识
				String fossCubc = "";
				List<String> fossWayBillList = new ArrayList<String>();
				List<String> ucbcWayBillList = new ArrayList<String>();
				List<AirPickupbillDetailEntity> fossAddEntityList = new ArrayList<AirPickupbillDetailEntity>();
				List<AirPickupbillDetailEntity> ucbcAddEntityList = new ArrayList<AirPickupbillDetailEntity>();
				List<AirPickupbillDetailEntity> fossModifyEntityList = new ArrayList<AirPickupbillDetailEntity>();
				List<AirPickupbillDetailEntity> ucbcModifyEntityList = new ArrayList<AirPickupbillDetailEntity>();
				List<String> fossDelEntityList = new ArrayList<String>();
				List<String> ucbcDelEntityList = new ArrayList<String>();
				//分析灰度返回结果集，如果是2条的情况
				if (vestResponseDto.getVestBatchResult().size() > 1) {
					fossCubc = CUBCGrayContants.SYSTEM_CODE_FOSS_CUBC;
				}
				// 循环得到分流的运单号
				for (VestBatchResult vestResult : vestResponseDto.getVestBatchResult()) {
					if (vestResult.getVestSystemCode().equals(CUBCGrayContants.SYSTEM_CODE_FOSS)) {
						fossWayBillList = vestResult.getVestObject();
					} else {
						ucbcWayBillList = vestResult.getVestObject();
					}
				}

				// 解析FOSS分流单号
				for (String wayBillNo : fossWayBillList) {
					// FOSS添加的运单号对应的实体
					for (AirPickupbillDetailEntity addEntity : stlAddList) {
						if (wayBillNo.equals(addEntity.getWaybillNo())) {
							fossAddEntityList.add(addEntity);
						}
					}
					// FOSS修改对应的实体
					for (AirPickupbillDetailEntity modifyEntity : stlModifyList) {
						if (wayBillNo.equals(modifyEntity.getWaybillNo())) {
							fossModifyEntityList.add(modifyEntity);
						}
					}
					// FOSS删除的运单号
					for (String delWayBill : stlDeleteList) {
						if (wayBillNo.equals(delWayBill)) {
							fossDelEntityList.add(delWayBill);
						}
					}

				}
				// 解析CUBC分流单号
				for (String wayBillNo : ucbcWayBillList) {
					// CUBC添加的运单号对应的实体
					for (AirPickupbillDetailEntity addEntity : stlAddList) {
						if (wayBillNo.equals(addEntity.getWaybillNo())) {
							ucbcAddEntityList.add(addEntity);
						}

					}
					// CUBC修改对应的实体
					for (AirPickupbillDetailEntity modifyEntity : stlModifyList) {
						if (wayBillNo.equals(modifyEntity.getWaybillNo())) {
							ucbcModifyEntityList.add(modifyEntity);
						}
					}
					// CUBC删除的运单号
					for (String delWayBill : stlDeleteList) {
						if (wayBillNo.equals(delWayBill)) {
							ucbcDelEntityList.add(delWayBill);
						}
					}
				}
				// 重新赋值
				stlAddList = fossAddEntityList;

				stlModifyList = fossModifyEntityList;

				stlDeleteList = fossDelEntityList;
				

				if (CUBCGrayContants.SYSTEM_CODE_FOSS.equals(vestResponseDto.getVestBatchResult().get(0).getVestSystemCode())
						|| fossCubc.equals(CUBCGrayContants.SYSTEM_CODE_FOSS_CUBC)) {
					try {
						LOGGER.info("修改合大票清单调用结算接口开始时间："+new Date());

						airJointTicketService.modifyAirJointTicketDetail(oldAirPickupbillEntity,
								newAirPickupbillEntity,
								stlAddList,
								stlModifyList,
								stlDeleteList,
								new CurrentInfo(user,orgAdministrativeInfoEntity));
						
						LOGGER.info("修改合大票清单调用结算接口结束时间："+new Date());

					} catch (SettlementException e) {
						LOGGER.error("修改合票清单调用结算接口出现异常，异常信息：" + e.getErrorCode());
						throw new  TfrBusinessException("修改合票清单调用结算接口出现异常:"+e.getErrorCode());
					}
				}
				if (CUBCGrayContants.SYSTEM_CODE_CUBC.equals(vestResponseDto.getVestBatchResult().get(0).getVestSystemCode())
						|| fossCubc.equals(CUBCGrayContants.SYSTEM_CODE_FOSS_CUBC)) {
					// TODO 365455
					LOGGER.info("修改合大票清单调用结算接口开始时间：" + new Date());
					// CurrentInfo currentInfo =new
					// CurrentInfo(user,orgAdministrativeInfoEntity);
					AirPickupbillDetailsavenDto airPickupbillDetailsSaveDto = new AirPickupbillDetailsavenDto();
					// airPickupbillDetailsSaveDto.setOldAirPickupbillEntity(oldAirPickupbillEntity);
					airPickupbillDetailsSaveDto.setAirPickupbillEntity(newAirPickupbillEntity);
					airPickupbillDetailsSaveDto.setStlAddList(ucbcAddEntityList);
					airPickupbillDetailsSaveDto.setStlModifyList(ucbcModifyEntityList);
					airPickupbillDetailsSaveDto.setStlDeleteList(ucbcDelEntityList);
					String requestStr = JSONObject.toJSONString(airPickupbillDetailsSaveDto);
					LOGGER.error("传递给CUBC的参数 requestStr = " + requestStr);
					ResponseExternalBillDto responseDto = fossToCubcService.pushupdateSaveAirPickupAnd(requestStr);
					LOGGER.error("responseDto===" + responseDto);
					// ESB异常
					if (null == responseDto) {
						// 保存异常信息，标注ESB异常，便于查找问题原因
						// 推送失败时，异常信息处理 doto
						throw new TfrBusinessException("ESB出现异常:" + responseDto);
					}else if ("0".equals(responseDto.getResult())) {
						throw new TfrBusinessException("FOSS修改合大票失败，失败原因：" + responseDto.getReason());
					}
					LOGGER.info("修改合大票清单调用结算接口结束时间：" + new Date());
				}

				List<String> addWaybillList=new ArrayList<String>();
				if(!stlAddList.isEmpty()&&stlAddList.size()>0){
					for(int i=0;i<stlAddList.size();i++){
						addWaybillList.add(stlAddList.get(i).getWaybillNo());
					}
					
				}
				//记录修改日志
				addAirPickupBillLog(oldAirPickupbillEntity,stlDeleteList,addWaybillList,TransferDictionaryConstants.NO);
			}
		}
		//提交调用
		if(FossConstants.YES.equals(status)){
			//2016-05-11 合票清单修改 数据同步至OPP
			//@author 269701
			//合票清单信息转换成JOB临时表的数据
			//311396 wwb 如果是暂存转提交就是insert
			convertDataToJobTemp(newAirPickupbillEntity,null, flag ? "INSERT" : "UPDATE");
		}
		Long endTime=new Date().getTime();
		LOGGER.info("======================修改合大票清单结束时间："+new Date()+"==========================");
		LOGGER.info("修改合大票 {"+airTranDataCollectionEntity.getAirWaybillNo()+"} 结束");
		LOGGER.info("本次操作一共用了："+(endTime-startTime));
		return 0;
	}
	
	private List<AirPickupbillDetailEntity> matchAirList(List<AirPickupbillDetailEntity> list, List<String> wbs) {
		List<AirPickupbillDetailEntity> rst = new ArrayList<AirPickupbillDetailEntity>();
		l: for (String wb : wbs) {
			for (AirPickupbillDetailEntity ae : list) {
				if (ae.getWaybillNo() != null && ae.getWaybillNo().equals(wb)) {
					rst.add(ae);
					list.remove(ae);
					continue l;
				}
			}
		}
		return rst;
	}
	/**
	 * 处理新增合大票明细赋值 
	 * @param list 需要赋值主键id和外键id的合大票明细list
	 * @param pickupbillId 合大票清单主键id
	 * @return airPickupbillDetailList 返回赋值完成的list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-18 上午9:37:21
	 */
	private List<AirPickupbillDetailEntity> addList (List<AirPickupbillDetailEntity> list,String pickupbillId){
		List<AirPickupbillDetailEntity> airPickupbillDetailList = new ArrayList<AirPickupbillDetailEntity>();
		//遍历循环
		for (AirPickupbillDetailEntity airPickupbillDetailEntity : list) {
			//设置明细主键
			airPickupbillDetailEntity.setId(UUIDUtils.getUUID());
			//主表id
			airPickupbillDetailEntity.setAirPickupbillId(pickupbillId);
			//创建时间
			airPickupbillDetailEntity.setCreateTime(new Date());
			//币种
			airPickupbillDetailEntity.setCurrencyCode(AirfreightConstants.AIRFREIGHT_CURRENCYCODE);
			//赋值后加入合大票明细列表中
			airPickupbillDetailList.add(airPickupbillDetailEntity);
		}
		return airPickupbillDetailList;
	}
	
	/**
	 * 
	* @description 批量插入合大票运单流水
	* 	如果该运单对应的河大清单流水信息不为空 说明是编辑修改运单后 新增的该运单合票明细
		如果该运单对应的合大票流水信息为空 批量插入该运单对应的所有流水
	* @param addAirPickupbillDetailList
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月21日 下午4:41:31
	 */
	private List<AirPickupbillDetailEntity> addSerialList(List<AirPickupbillDetailEntity> addPickupDetailList){
		//运单流水：A 按运单查合大票流水 B 合票流水 C
		//新增运单流水list
		List<SerialEntity> addSerialList=new ArrayList<SerialEntity>();
		//A-C流水
		List<SerialEntity> serialListAC=new ArrayList<SerialEntity>();
		//根据运单查询航空正单流水
		List<String> airSerialNoList=new ArrayList<String>();
		//返回结果
		List<AirPickupbillDetailEntity> resultAddPickupDetailList=new ArrayList<AirPickupbillDetailEntity>();
				 
		for(AirPickupbillDetailEntity detial:addPickupDetailList){
			//根据运单号校验 该运单号是否已经生成合大票清单流水信息
			AirTransPickupBillDto airTransPickupBillDto =new AirTransPickupBillDto();
			//运单号
			airTransPickupBillDto.setWaybillNo(detial.getWaybillNo());
			//根据运单号查询合大票清单明细表 校验运单是否已经制作过合大票清单
			AirPickupbillDetailEntity airPickUpDetialEntity=airTransPickupBillDao.getAirPickupbillDetailInfo(detial.getWaybillNo());
			if(null==airPickUpDetialEntity){
					//该运单未制作过合大票
					//流水信息=航空正单流水
					//根据运单号 查询航空正单流水信息表 得到流水列表
					airSerialNoList= airTransPickupBillDao.getAirWaySerialListByWaybill(detial.getWaybillNo(),detial.getAirWaybillNo());
					for(int j=0;j<airSerialNoList.size();j++){
						//需要保存的流水信息列表
						SerialEntity serialEntity=new SerialEntity();
						//明细id
						serialEntity.setAirPickUpDetialId(detial.getId());
						serialEntity.setWaybillNo(detial.getWaybillNo());
						//流水id
						serialEntity.setId(UUIDUtils.getUUID());
						serialEntity.setSerialNo(airSerialNoList.get(j));
						addSerialList.add(serialEntity);
				}
			}else{
				//该运单制作过合大票
				//流水信息=运单流水-已制作合大票流水
				serialListAC=airTransPickupBillDao.findLeftSerialForModify(airTransPickupBillDto);
				for(SerialEntity entity:serialListAC){
					//需要保存的流水信息列表
					SerialEntity serialEntity=new SerialEntity();
					//明细id
					serialEntity.setAirPickUpDetialId(detial.getId());
					serialEntity.setWaybillNo(detial.getWaybillNo());
					//流水id
					serialEntity.setId(UUIDUtils.getUUID());
					serialEntity.setSerialNo(entity.getSerialNo());
					addSerialList.add(serialEntity);
				}
			}
			if(addSerialList.size()>0){
				//设置清单件数
				detial.setAirPickQty(addSerialList.size());
				detial.setGoodsQty(addSerialList.size());
			}
			resultAddPickupDetailList.add(detial);
			//新增流水不为空
			if(addSerialList.size()>0){
				airQueryModifyPickupbillDao.addAirPickupbillSerialist(addSerialList);
			}
		}
		return resultAddPickupDetailList;
	}
	/**
	 * 根据运单号list查询符合条件的运单原始信息
	 * @param List 合大票清单明细列表 
	 * @author 099197-foss-zhoudejun
	 * @date 2013-2-25 上午10:40:37
	 */
	private List<AirPickupbillDetailEntity> getOriginalAirPickupbillList(
			List<AirPickupbillDetailEntity> airPickupbillDetailList,
			List<AirRevisebillDetailEntity> airRevisebillDetailList,
			Map<String,String> signWaybillNoMap){
		//运单号列表
		List<String> pickupDetailIdList = new ArrayList<String>();
		//遍历合大票清单明细列表
		for (AirPickupbillDetailEntity airPickupbillDetailEntity : airPickupbillDetailList) {
			//加入合大票明细id
			pickupDetailIdList.add(airPickupbillDetailEntity.getId());
		}
		//根据运单号list查询符合条件的运单原始信息
		List<AirPickupbillDetailEntity> origAirPickupbillDetailList = airQueryModifyPickupbillDao.queryAirPickupbillDetailListByIds(pickupDetailIdList);
		//调用结算    修改列表
		List<AirPickupbillDetailEntity> modifyAirPickupbillDetailList =  new ArrayList<AirPickupbillDetailEntity>();
		//获取当前用户信息
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		//遍历原始送货费列表
		for (AirPickupbillDetailEntity origDetailEntity : origAirPickupbillDetailList) {
			//遍历现有的合大票清单明细列表
			for (AirPickupbillDetailEntity newDetailEntity : airPickupbillDetailList) {
				//原运单号=现有清单列表中的运单号
				if(origDetailEntity.getWaybillNo().equals(newDetailEntity.getWaybillNo())){
					//变更内容
					StringBuffer reviseContent = new StringBuffer();
					if(origDetailEntity.getDeliverFee().compareTo(newDetailEntity.getDeliverFee()) != 0){
						//如果运单送货费和之前不一样并且该运单没有签收,则把新运单信息用放到modifyAirPickupbillDetailList
						if(!signWaybillNoMap.containsKey(origDetailEntity.getWaybillNo())){
							modifyAirPickupbillDetailList.add(newDetailEntity);
						}
						//修改送货费
						reviseContent.append("送货费：" + newDetailEntity.getDeliverFee());
						reviseContent.append("；");
					}
					fixReviseContent(origDetailEntity, newDetailEntity,
							reviseContent);
					
					int length = reviseContent.toString().length();
					if(length >= ConstantsNumberSonar.SONAR_NUMBER_1000){
						throw new AirChangeInventoryException("备注长度过长！");
					}
					if(length > 0){
						//变更明细
						AirRevisebillDetailEntity airRevisebillDetailEntity = new AirRevisebillDetailEntity();
						//设置合大票明细id
						airRevisebillDetailEntity.setAirPickupbillDetailId(newDetailEntity.getAirPickupbillId());
						//中转提货明细id
						airRevisebillDetailEntity.setAirTransPickupbillDetailId(UUIDUtils.getUUID());
						//变更清单ID
						airRevisebillDetailEntity.setId(UUIDUtils.getUUID());
						//创建时间
						airRevisebillDetailEntity.setCreateTime(new Date());
						//操作时间
						airRevisebillDetailEntity.setOperationTime(new Date());
						//当前操作部门
						airRevisebillDetailEntity.setOperationOrgCode(orgAdministrativeInfoEntity.getCode());
						//当前操作部门名称
						airRevisebillDetailEntity.setOperationOrgName(orgAdministrativeInfoEntity.getName());
						//操作人姓名
						airRevisebillDetailEntity.setOperatorName(currentInfo.getEmpName());
						//操作人工号
						airRevisebillDetailEntity.setOperatorCode(currentInfo.getEmpCode());
						//记录
						airRevisebillDetailEntity.setReviseContent(reviseContent.toString());
						//添加单条变更清单至列表中
						airRevisebillDetailList.add(airRevisebillDetailEntity);
					}
				}
			}
		}
		return modifyAirPickupbillDetailList;
	}

	/**
	 * sonar优化 311396 wwb 2016年12月24日10:08:27
	 * @param origDetailEntity
	 * @param newDetailEntity
	 * @param reviseContent
	 */
	private void fixReviseContent(AirPickupbillDetailEntity origDetailEntity,
			AirPickupbillDetailEntity newDetailEntity,
			StringBuffer reviseContent) {
		//修改计费重量
		if(newDetailEntity.getBillingWeight().compareTo(origDetailEntity.getBillingWeight()) != 0){
			reviseContent.append("计费重量：" + newDetailEntity.getBillingWeight());
			reviseContent.append("；");
		}
		//修改件数
		if(newDetailEntity.getGoodsQty().intValue() != origDetailEntity.getGoodsQty().intValue()){
			reviseContent.append("件数：" + newDetailEntity.getGoodsQty());
			reviseContent.append("；");
		}
		////修改收货人
		if(!StringUtils.equals(newDetailEntity.getReceiverName(), origDetailEntity.getReceiverName())){
			if(!newDetailEntity.getReceiverName().equals("") || origDetailEntity.getReceiverName() != null){
				reviseContent.append("收货人：" + newDetailEntity.getReceiverName());
				reviseContent.append("；");
			}
		}
		//修改收货人电话
		if(!StringUtils.equals(newDetailEntity.getReceiverContactPhone(), origDetailEntity.getReceiverContactPhone())){
			if(!newDetailEntity.getReceiverContactPhone().equals("") || origDetailEntity.getReceiverContactPhone() != null){
				reviseContent.append("收货人电话：" + newDetailEntity.getReceiverContactPhone());
				reviseContent.append("；");
			}
		}
		//修改收货人地址
		if(!StringUtils.equals(newDetailEntity.getReceiverAddress(), origDetailEntity.getReceiverAddress())){
			if(!newDetailEntity.getReceiverAddress().equals("") || origDetailEntity.getReceiverAddress() != null){
				reviseContent.append("收货人地址：" + newDetailEntity.getReceiverAddress());
				reviseContent.append("；");
			}
		}
		
		if(!StringUtils.equals(origDetailEntity.getNotes(),newDetailEntity.getNotes())){
			if(!newDetailEntity.getNotes().equals("") || origDetailEntity.getNotes() != null){
				reviseContent.append("备注：" + newDetailEntity.getNotes());
				reviseContent.append("；");
			}
		}
	}
	/**
	 * 处理变更清单logger日志 
	 * @author 099197-foss-zhoudejun
	 * @param addAirPickupbillDetailList 新增合大票清单明细
	 * @param airPickupbillId
	 * @return AirRevisebillDetailEntity
	 * @date 2012-12-19 下午3:06:18
	 */
	private List<AirRevisebillDetailEntity> addaddAirRevisebillDetailList (List<AirPickupbillDetailEntity> addAirPickupbillDetailList,String airPickupbillId){
		List<AirRevisebillDetailEntity> airRevisebillDetailList = new ArrayList<AirRevisebillDetailEntity>();
  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		
		//遍历循环
		for (int i = 0; i < addAirPickupbillDetailList.size(); i++) {
			AirRevisebillDetailEntity airRevisebillDetailEntity = new AirRevisebillDetailEntity();
			//当前用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//设置合票清单明细id
			airRevisebillDetailEntity.setAirPickupbillDetailId(addAirPickupbillDetailList.get(i).getId());
			//设置中转提货清单明细id 
			airRevisebillDetailEntity.setAirTransPickupbillDetailId(UUIDUtils.getUUID());
			//变更清单id
			airRevisebillDetailEntity.setId(UUIDUtils.getUUID());
			//创建时间
			airRevisebillDetailEntity.setCreateTime(new Date());
			//操作时间
			airRevisebillDetailEntity.setOperationTime(new Date());
			//当前部门编码
			airRevisebillDetailEntity.setOperationOrgCode(orgAdministrativeInfoEntity.getCode());
			//当前部门名称
			airRevisebillDetailEntity.setOperationOrgName(orgAdministrativeInfoEntity.getName());
			//用户名
			airRevisebillDetailEntity.setOperatorName(currentInfo.getEmpName());
			//用户工号
			airRevisebillDetailEntity.setOperatorCode(currentInfo.getEmpCode());
			//设置变更内容
			airRevisebillDetailEntity.setReviseContent("(新增)大票清单");
			//加入变更清单明细列表中
			airRevisebillDetailList.add(airRevisebillDetailEntity);
		}
		return airRevisebillDetailList;
	}
	
	/**
	 * 根据运单号查询空运运单费用及出港信息
	 * @param waybillNo 运单号
	 * @return AirPickupbillEntity
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-15 下午2:38:23
	 */
	@Override
	public AirPickupbillEntity queryAirPickupbill(String waybillNo) {
		AirPickupbillEntity entity = airQueryModifyPickupbillDao.queryAirPickupbill(waybillNo);
		if(entity == null){
			LOGGER.info("queryAirPickupbill:根据运单号查询空运运单费用及出港信息为空!");
		}
		return entity;
	}
	
	/**
	 * 根据合大票id获取合大票清单明细 
	 * @param airPickupbillId 合大票Id
	 * @return  List<AirPickupbillDetailEntity> 返回合大票清单明细
	 * @author liuzhaowei
	 * @date 2013-06-25 上午8:58:58
	 */
	@Override
	public List<AirPickupbillDetailEntity> queryAirPickupbillDetailListByPrimaryId(String airPickupbillId){
		return airQueryModifyPickupbillDao.queryAirPickupbillDetailListByPrimaryId(airPickupbillId); 
	}
	
	/**
	 * 根据运单号查询空运运单合票信息  供综合查询显示内部轨迹
	 * @param waybillNo 运单号
	 * @return List<AirPickupbillEntity>
	 * @date 2013-06-15 下午2:38:23
	 */
	@Override
	public List<AirPickupbillEntity> queryAirPickupbillListForViewTrack(String waybillNo) {
		return airQueryModifyPickupbillDao.queryAirPickupbillListForViewTrack(waybillNo);
	}
	
	/**
	 *  根据运单号查询运单是否可以修改（结算信息是否已审核，核销、运单是否已签收）
	 * @param waybillNo 运单号
	 * @author 099197-foss-liuzhaowei
	 * @date 2013-4-23 下午2:38:23
	 */
	@Override
	public void validateAirJointTicketDetail(String waybillNo) {
		
		// 封装灰度实体，类型是运单
		GrayParameterDto parDto = new GrayParameterDto();
		parDto.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
		parDto.setSourceBillNos(new String[] { waybillNo });
		VestResponse vestResponse = cubcUtil.getUcbcGrayData(parDto, new Throwable());

		if (CUBCGrayContants.SYSTEM_CODE_FOSS.equals(vestResponse.getVestBatchResult().get(0).getVestSystemCode())) {
			// 校验结算单据信息是否已审核，核销
			airJointTicketService.validateAirJointTicketDetail(waybillNo);
		} else {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("waybillNo", waybillNo);
			CubcValidationAirJoinTicketResponse cubcValidationVerificationRecResponse = fossToCubcService
					.validateAirJointTicketDetail(paramMap);

			if (null != cubcValidationVerificationRecResponse) {
				if (cubcValidationVerificationRecResponse.isSuccess()) {
					if(cubcValidationVerificationRecResponse.isAudited()&&StringUtils.isNotEmpty(cubcValidationVerificationRecResponse.getReason())){
						throw new TfrBusinessException(
								"调用cubc验证空运合大票明细失败:cubc" + cubcValidationVerificationRecResponse.getReason());
					}
				}else{
					throw new TfrBusinessException(
							"调用cubc验证空运合大票明细失败:cubc" + cubcValidationVerificationRecResponse.getExceptionMsg());
				}
				
			} else {
				throw new TfrBusinessException("调用cubc验证空运合大票明细失败 ");
			}
		}
		
		//校验运单是否已签收
		List<String> waybillNoList = new ArrayList<String>();
		waybillNoList.add(waybillNo);
		//查询已签收运单信息
		List<String> signList = waybillSignResultService.queryWaybillSignResultWaybillNos(waybillNoList);
		if(signList.size() > 0){
			throw new TfrBusinessException("运单已签收，不能修改！");
		}
	}
	
	/**
	 *  接送货做更改单时同步修改合大票明细信息
	 * @param entity 空运合大票明细
	 * @author liuzhaowei
	 * @date 2013-7-1 下午4:38:23
	 */
	@Override
	public void updatePickupDetailFromModifyWaybill(AirPickupbillDetailEntity detailEntity){
		//校验传人参数是否为空
		if(detailEntity == null || detailEntity.getWaybillNo() == null || detailEntity.getWaybillNo().length() == 0){
			throw new TfrBusinessException("修改合大票明细传入运单号为空！");
		}
		//设置送货费为空zwd 20141224
		detailEntity.setDeliverFee(null);
		//更新合大票明细信息
		airQueryModifyPickupbillDao.updatePickupDetailFromModifyWaybill(detailEntity);	
		//@date 2016-06-07
		//@author 269701-lln
		//接送货做更改单时，合大票信息更改同步至OPP系统
		//合票清单信息转换成JOB临时表的数据
		//根据运单号 查询合大票清单号
		List<AirPickupbillEntity> pickUpNoList=airQueryModifyPickupbillDao.queryAirPickupbillListByWaybillNo(detailEntity.getWaybillNo());
		for(AirPickupbillEntity entity:pickUpNoList){
			convertDataToJobTemp(entity,detailEntity.getWaybillNo(),"UPDATE");
		}
	}
	
	
	/**
	 * 删除合票清单
	 * @deprecated:
	 * 1 合票清单中只包含一个运单；若合票清单中包含多个运单，则无法删除，系统提示“合票清单中包含多个运单，不允许删除。”。
	 * 2 检查运单签收状态和财务单据核销状态；若合票清单中的运单已签收，已核销，则无法删除，系统提示“此清单中包含已经签收的运单，不允许删除。
	 * 3 检查运单是否做过中转提货清单；如果是，则无法删除，系统需提示“此清单中运单已生成中转提货清单，不允许删除。请先删除中转提货清单。”
	 * 4 在删除包含运单信息时，执行原有业务规则（调用结算接口等）。如不成功，则不允许删除合票清单，提示语同删除合票清单中的运单的提示语相同。
	 * 5 删除后的正单号可再次做合票清单。
	 * @param entity 空运合大票明细
	 * @author foss-105795-wqh
	 * @date 2014-01-20
	 */
	@Transactional
	public void deleteAirPickupAndDetailByAirPickupId(String id){
		//删除合票清单id
		/*List<String> pickupIdsList=new ArrayList<String>();*/
		//删除合票清单明细ids
		List<String> pickupDetailIdsList=new ArrayList<String>();
		//删除的合票运单明细
		List<String> delWaybillNosList=new ArrayList<String>();
		//校验参数
		if(StringUtil.isEmpty(id)){
			throw new TfrBusinessException("请选择需删除的合票清单");
		}
		
		//查询合大票清单
		AirPickupbillEntity airPickupbillEntity = airTransPickupBillDao.queryAirPickupbillEntityById(id);
		String status = airPickupbillEntity.getStatus();
		
		//根据合票清单id查询明细条数
		List<AirPickupbillDetailEntity> pickupbillDetailList=queryAirPickupbillDetailListByPrimaryId(id);
		if(pickupbillDetailList!=null&&pickupbillDetailList.size()>0){
			//检查删除的合票中是否含有多个明细
			if(pickupbillDetailList.size()>1){
				throw new TfrBusinessException("合票清单中包含多个运单，不允许删除");
			}
			delWaybillNosList.add(pickupbillDetailList.get(0).getWaybillNo());
			//限于已提交合大票(考虑之前状态为空)，校验结算单据信息是否已审核，核销
			if(!FossConstants.NO.equals(status)){
				
				// 封装灰度实体，类型是运单
				GrayParameterDto paramDto = new GrayParameterDto();
				paramDto.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
				paramDto.setSourceBillNos(new String[] { pickupbillDetailList.get(0).getWaybillNo() });
				VestResponse vestResp = cubcUtil.getUcbcGrayData(paramDto, new Throwable());

				if (CUBCGrayContants.SYSTEM_CODE_FOSS
						.equals(vestResp.getVestBatchResult().get(0).getVestSystemCode())) {
					try {
						airJointTicketService.validateAirJointTicketDetail(pickupbillDetailList.get(0).getWaybillNo());

					} catch (SettlementException e) {
						throw new TfrBusinessException("调用结算接口异常：" + e.getErrorCode());
					}
				} else {

					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("waybillNo", pickupbillDetailList.get(0).getWaybillNo());
					CubcValidationAirJoinTicketResponse cubcValidationVerificationRecResponse = fossToCubcService
							.validateAirJointTicketDetail(paramMap);

					if (null != cubcValidationVerificationRecResponse) {
						if (cubcValidationVerificationRecResponse.isSuccess()) {
							if(cubcValidationVerificationRecResponse.isAudited()&&StringUtils.isNotEmpty(cubcValidationVerificationRecResponse.getReason())){
								throw new TfrBusinessException(
										"调用cubc验证空运合大票明细失败:cubc" + cubcValidationVerificationRecResponse.getReason());
							}
						}else{
							throw new TfrBusinessException(
									"调用cubc验证空运合大票明细失败:cubc" + cubcValidationVerificationRecResponse.getExceptionMsg());
						}
						
					} else {
						throw new TfrBusinessException("调用cubc验证空运合大票明细失败 ");
					}
				}
				
				
				// 检查运单签收状态
				List<String> signList = waybillSignResultService.queryWaybillSignResultWaybillNos(delWaybillNosList);
				if(signList.size()>0){
					throw new TfrBusinessException("此清单中包含已经签收的运单:{"+pickupbillDetailList.get(0).getWaybillNo()+"}，不允许删除");
				}
				

				//检查是否做过中转提货
				List<AirTransPickupDetailEntity> airTransPickupDetailEntityList=airTransPickupBillDao.queryAirTransPickupDetailList(airPickupbillEntity.getAirWaybillNo());
				if(airTransPickupDetailEntityList!=null&&airTransPickupDetailEntityList.size()>0){
					throw new TfrBusinessException("此清单中运单已生成中转提货清单，不允许删除。请先删除中转提货清单");

				}
				
				//封装灰度实体，类型是正单
				GrayParameterDto parDto = new GrayParameterDto();
				parDto.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
				parDto.setSourceBillNos((String []) delWaybillNosList.toArray(new String [delWaybillNosList.size()]));
				
				VestResponse vestResponse = cubcUtil.getUcbcGrayData(parDto, new Throwable());
				
				
				//调用结算接口
				if(delWaybillNosList.size() > 0){
			  		//根据当前部门取空运总调
					String deptCode = FossUserContext.getCurrentDeptCode();
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
					UserEntity user = FossUserContext.getCurrentInfo().getUser();
					
					if (CUBCGrayContants.SYSTEM_CODE_FOSS.equals(vestResponse.getVestBatchResult().get(0).getVestSystemCode())) {
						try {
							
							airJointTicketService.modifyAirJointTicketDetail(airPickupbillEntity,
									airPickupbillEntity,
									null,
									null,
									delWaybillNosList,
									new CurrentInfo(user,orgAdministrativeInfoEntity));
							
						} catch (SettlementException e) {
							LOGGER.error("删除合票清单调用结算接口出现异常，异常信息：" + e.getErrorCode());
							throw new  TfrBusinessException("删除合票清单调用结算接口出现异常:"+e.getErrorCode());
						}
					} else {
						// 365455 TODO
						LOGGER.info("删除合大票清单调用结算接口开始时间：" + new Date());
						AirPickupbillDetailsavenDto airPickupbillDetailsSaveDto = new AirPickupbillDetailsavenDto();
						airPickupbillDetailsSaveDto.setAirPickupbillEntity(airPickupbillEntity);
						airPickupbillDetailsSaveDto.setStlDeleteList(delWaybillNosList);
						String requestStr = JSONObject.toJSONString(airPickupbillDetailsSaveDto);
						LOGGER.error("传递给CUBC的参数 requestStr = " + requestStr);
						ResponseExternalBillDto responseDto = fossToCubcService.pushdeleteAirPickupAnd(requestStr);
						LOGGER.error("responseDto===" + responseDto);
						// ESB异常
						if (null == responseDto) {
							// 保存异常信息，标注ESB异常，便于查找问题原因
							// 推送失败时，异常信息处理 doto
							throw new TfrBusinessException("ESB出现异常:" + responseDto);
						} else if ("0".equals(responseDto.getResult())) {
							throw new TfrBusinessException("FOSS删除合大票失败，失败原因：" + responseDto.getReason());
						}
						LOGGER.info("删除合大票清单调用结算接口结束时间：" + new Date());

					}
				}
			}

			pickupDetailIdsList.add(pickupbillDetailList.get(0).getId());
			//删除合票明细
			airQueryModifyPickupbillDao.deleteAirPickupbillDetailList(pickupDetailIdsList);
			//@date 2016-05-21 删除合大票清单流水信息
			//@author 269701
			//删除数据list不为空
			if(pickupDetailIdsList.size()>0){
				airQueryModifyPickupbillDao.deleteAirPickupbillSerialList(pickupDetailIdsList);
			}
			//限制于已提交合大票
			if(!FossConstants.NO.equals(status)){
				//添加合票日志
				addAirPickupBillLog(airPickupbillEntity,delWaybillNosList,null,TransferDictionaryConstants.YES);
				
				//2016-05-11 合票清单修改 数据同步至OPP
				//@author 269701
				//合票清单信息转换成JOB临时表的数据
				convertDataToJobTemp(airPickupbillEntity,pickupbillDetailList.get(0).getWaybillNo(),"DELETE");
			}
		}
		//删除合票清单
		airQueryModifyPickupbillDao.deleteAirPickupBillById(id);

		
	}
	/**
	 * 
	* @description 转换数据 删除合大票信息以及修改合大票信息 数据暂存临时表 等待job推送；
	* @param entity
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月11日 下午8:38:05
	 */
	private void convertDataToJobTemp(AirPickupbillEntity entity,String waybillNo,String operType){
		//FOSS修改合大票信息，同步推送至OPP系统
		//@author 269701-lln
		//@date 2016-05-11
		AirWaybillTempEntity temEntity=new AirWaybillTempEntity();
		//清单id
		temEntity.setAirPickUpId(entity.getId());
		//清单号
		temEntity.setAirPickNo(entity.getAirWaybillNo());
		//单据类型：清单号
		temEntity.setBillType("20");
		//推送中
		temEntity.setPushStatus(FossConstants.NO);
		//运单号
		temEntity.setWaybillNo(waybillNo);
		//新增数据
		temEntity.setOperateStatus(operType);
		LOGGER.info("FOSS合票信息改变，数据暂存临时表 等待job推送；合大票 {"+entity.getAirWaybillNo()+"} 主表信息 开始...."+new Date());
		pushAirPickUpInfoService.addAirPickToTemp(temEntity);
		LOGGER.info("FOSS合票信息改变，数据暂存临时表 等待job推送；合大票 {"+entity.getAirWaybillNo()+"} 主表信息 结束...."+new Date());
	}
	
	/*
	 * @author foss-105795-wqh
	 * @desc 添加合票删除、修改日志
	 * @parms:AirPickupbillEntity 合票实体
	 * @parms:detailWaybillNos 删除运单明细
	 * @parms：addWaybillNos 增加运单明细
	 * @parms：isDelAPU 合票删除标识
	 * */
	private void addAirPickupBillLog(AirPickupbillEntity airPickupbillEntity,List<String> detailWaybillNos,
			List<String> addWaybillNos,String isDelAPU){
		//参数校验
		if(airPickupbillEntity==null){
			LOGGER.error("添加日志失败");
			return;
		}
		AirPickupbillLogEntity airPickupbillLogEntity=new AirPickupbillLogEntity();
		//初始化
		//id
		airPickupbillLogEntity.setId(UUIDUtils.getUUID());
		//合票id
		airPickupbillLogEntity.setAirPickupbillId(airPickupbillEntity.getId());
		//正单号
		airPickupbillLogEntity.setAirWaybillNo(airPickupbillEntity.getAirWaybillNo());
		//操作类型
		String operateType="";
		if(TransferDictionaryConstants.YES.equals(isDelAPU)){
			operateType="删除合票";
		}else{
			operateType="修改合票";
		}
		airPickupbillLogEntity.setOperateType(operateType);
		//操作内容拼接
		StringBuffer operateContent=new StringBuffer("");
		if(detailWaybillNos!=null&&detailWaybillNos.size()>0){
			operateContent.append("删除运单明细");
			for(int i=0;i<detailWaybillNos.size();i++){
				operateContent.append("{"+detailWaybillNos.get(i).toString()+"}");
			}
		}
		if(addWaybillNos!=null&&addWaybillNos.size()>0){
			operateContent.append("添加运单明细");
			for(int i=0;i<addWaybillNos.size();i++){
				operateContent.append("{"+addWaybillNos.get(i).toString()+"}");
			}
		}
		airPickupbillLogEntity.setOperateContent(operateContent.toString());
		//操作人
		EmployeeEntity employee = FossUserContext.getCurrentInfo().getUser().getEmployee();
		if(StringUtil.isNotEmpty(employee.getEmpCode())){
			airPickupbillLogEntity.setOperateCode(employee.getEmpCode());
		}else{
			airPickupbillLogEntity.setOperateCode("null");
		}
		//操作人姓名
		if(StringUtil.isNotEmpty(employee.getEmpCode())){
			airPickupbillLogEntity.setOperateName(employee.getEmpName());
		}else{
			airPickupbillLogEntity.setOperateName("null");
		}
		
		//操作时间
		airPickupbillLogEntity.setOperateTime(new Date());
		//操作人部门code
		if(StringUtil.isNotEmpty(FossUserContext.getCurrentDeptCode())){
			airPickupbillLogEntity.setOpterateOrgCode(FossUserContext.getCurrentDeptCode());
		}else{
			airPickupbillLogEntity.setOpterateOrgCode("null");
		}

		//部门名称
		if(StringUtil.isNotEmpty(FossUserContext.getCurrentDeptName())){
			airPickupbillLogEntity.setOpterateOrgName(FossUserContext.getCurrentDeptName());
		}else{
			airPickupbillLogEntity.setOpterateOrgName("null");
		}
		//添加合票日志
		airQueryModifyPickupbillDao.addAirPickupbillLog(airPickupbillLogEntity);
		
	}
	
	/**
	 * 设置 注入查询修改合大票dao.
	 * @param airQueryModifyPickupbillDao the new 注入查询修改合大票dao
	 */
	public void setAirQueryModifyPickupbillDao(
			IAirQueryModifyPickupbillDao airQueryModifyPickupbillDao) {
		this.airQueryModifyPickupbillDao = airQueryModifyPickupbillDao;
	}

	/**
	 * 设置 变更清单service.
	 * @param airChangeInventoryService the new 变更清单service
	 */
	public void setAirChangeInventoryService(
			IAirChangeInventoryService airChangeInventoryService) {
		this.airChangeInventoryService = airChangeInventoryService;
	}
	/**
	 * 获取变更清单service 
	 * @return
	 */
	public IAirChangeInventoryService getAirChangeInventoryService() {
		return airChangeInventoryService;
	}
	/**
	 * 设置 合票清单service.
	 * @param airJointTicketService the new 合票清单service
	 */
	public void setAirJointTicketService(
			IAirJointTicketService airJointTicketService) {
		this.airJointTicketService = airJointTicketService;
	}
	/**
	 * 设置 中转提货清单service.
	 * @param airTransPickupBillService the new 中转提货清单service
	 */
	public void setAirTransPickupBillService(
			IAirTransPickupBillService airTransPickupBillService) {
		this.airTransPickupBillService = airTransPickupBillService;
	}

	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}
	
	public void setAirDispatchUtilService(
			IAirDispatchUtilService airDispatchUtilService) {
		this.airDispatchUtilService = airDispatchUtilService;
	}
	/**
	 * 运单查询Service
	 * 
	 * @author wqh
	 * @date 2013-08-19
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}


	/**
	 * 设置 注入中转提货清单dao.
	 * @param airTransPickupBillDao the new 注入中转提货清单dao
	 */
	public void setAirTransPickupBillDao(
			IAirTransPickupBillDao airTransPickupBillDao) {
		this.airTransPickupBillDao = airTransPickupBillDao;
	}

	/**
	 * @param pushAirPickUpInfoService : set the property pushAirPickUpInfoService.
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 下午8:31:06
	 * @version V1.0
	 */
	
	public void setPushAirPickUpInfoService(
			IPushAirPickUpInfoService pushAirPickUpInfoService) {
		this.pushAirPickUpInfoService = pushAirPickUpInfoService;
	}
	
}
