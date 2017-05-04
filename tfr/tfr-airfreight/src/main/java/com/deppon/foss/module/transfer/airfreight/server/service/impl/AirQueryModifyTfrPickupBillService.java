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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-airfreight
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/server/service/impl/AirQueryModifyTfrPickupBillService.java
 *  
 *  FILE NAME          :AirQueryModifyTfrPickupBillService.java
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.agency.api.server.service.IAirTransferService;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirQueryModifyTfrPickupBillDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirChangeInventoryService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirDispatchUtilService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirQueryModifyTfrPickupBillService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirRevisebillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupbillCUBCDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirChangeInventoryException;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestBatchResult;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestResponse;
import com.deppon.foss.module.transfer.common.api.server.service.IFossToCubcService;
import com.deppon.foss.module.transfer.common.api.shared.define.CUBCGrayContants;
import com.deppon.foss.module.transfer.common.api.shared.dto.GrayParameterDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseExternalBillDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.CUBCGrayUtil;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.UUIDUtils;

/**
 * 查询_修改中转提货清单service实现.
 * @author 099197-foss-zhoudejun
 * @date 2012-12-19 下午5:20:18
 * 
 */
public class AirQueryModifyTfrPickupBillService implements
		IAirQueryModifyTfrPickupBillService {
	
	/** 注入查询_修改中转提货清单dao接口. */
	private IAirQueryModifyTfrPickupBillDao airQueryModifyTfrPickupBillDao;
	
	/** 注入变更服务. */
	private IAirChangeInventoryService airChangeInventoryService;
	
	/** 注入结算服务*/
	private IAirTransferService airTransferService;
	
	/** 查找空运总调 service*/
	private IAirDispatchUtilService airDispatchUtilService;
	
	/**
	 * 运单签收service
	 */
	IWaybillSignResultService waybillSignResultService;
	
	
	/**
	 * 运单Service
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**灰度工具类*/
	private CUBCGrayUtil cubcUtil;
	
	public void setCubcUtil(CUBCGrayUtil cubcUtil) {
		this.cubcUtil = cubcUtil;
	}
	/**
	 * 365455
	 */
	private IFossToCubcService fossToCubcService;

	public void setFossToCubcService(IFossToCubcService fossToCubcService) {
		this.fossToCubcService = fossToCubcService;
	}
	/**
	 * 日志
	 */
	private static final Logger LOGGER=LoggerFactory.getLogger(AirQueryModifyTfrPickupBillService.class);
	/**
	 * 根据空运总调、到达网点、制单时间、中转单号、目的站、中转航班
	 * 查询中转提货清单信息.
	 * @param airTransPickupBillDto the air trans pickup bill dto
	 * @param limit the limit
	 * @param start the start
	 * @return AirTransPickupbillEntity
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-19 下午5:15:31
	 */
	@Override
	public List<AirTransPickupbillEntity> queryTfrPickupBill(
			AirTransPickupBillDto airTransPickupBillDto,int limit ,int start) {
		return airQueryModifyTfrPickupBillDao.queryTfrPickupBill(airTransPickupBillDto,limit , start);
	}
	
	/**
	 * 查询中转货清单  综合查询显示运单内部轨迹 
	 * @param waybillNo
	 * @return List<AirTransPickupbillEntity>
	 * @date 2013-06-17 下午5:15:31
	 */
	@Override
	public List<AirTransPickupbillEntity> queryTfrPickupBillListForViewTrack(String waybillNo) {
		return airQueryModifyTfrPickupBillDao.queryTfrPickupBillListForViewTrack(waybillNo);
	}

	/**
	 * 根据中转提货清单id查询中转提货明细信息.
	 * @param airTransferPickUpBillId 中转提货清单主键id
	 * @return AirTransPickupDetailEntity 中转提货明细list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-20 下午1:11:33
	 */
	@Override
	public List<AirTransPickupDetailEntity> queryTfrPickupBillDetail(
			String airTransferPickUpBillId) {
		return airQueryModifyTfrPickupBillDao.queryTfrPickupBillDetail(airTransferPickUpBillId);
	}
	
	/**
	 * 根据运单号查询同一(目的站、到达网点、中转航班、中转日期).
	 * @param airTransPickupBillDto the air trans pickup bill dto
	 * @return AirPickupbillDetailEntity
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-20 下午4:07:20
	 */
	@Override
	public List<AirPickupbillDetailEntity> queryToAddTfrPickupBillDetail(
			AirTransPickupBillDto airTransPickupBillDto) {
		return airQueryModifyTfrPickupBillDao.queryToAddTfrPickupBillDetail(airTransPickupBillDto);
	}
	
	/**
	 * 修改、保存、新增(中转提货清单明细、变更logger日志).
	 * @param modifyTransPickupDetailList the modify trans pickup detail list
	 * @param addirTransPickupDetailList the addir trans pickup detail list
	 * @param delTransPickupDetailList 需要删除掉运单明细list
	 * @param callStlTransPickupDetailList 需要调用结算的明细list
	 * @param modifyNotesMaps 修改变更日志
	 * @param airTransferPickUpBillId the air transfer pick up bill id
	 * @return (0或1)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-21 上午11:34:49
	 */
	@Override
	@Transactional
	public int modifyOrSaveTfrPickupBillDetail(
			List<AirTransPickupDetailEntity> transPickupDetailList,
			String airTransferPickUpBillId) {
		//modify by liuzhaowei 2013-07-09 begin 对运单的操作判断放到后台进行判断，原先在前台做判断有问题，导致有重复运单（在前台对运单先删除，在添加）
		if(CollectionUtils.isEmpty(transPickupDetailList)){
			throw new TfrBusinessException(
					AirfreightConstants.AIRFREIGHT_EXCEPTION_AIRPICKUPBILLDETAIL);
		}
		
		LOGGER.info("修改中转提货清单明细开始时间："+new Date());
		
		/**
		 * BUG-51170 运单发更改后还能做合票，导致财务单据不对
		 * @desc 保存合票之前判断运输性质是否为空运
		 * @author wqh 
		 * @date   2013-08-19
		 * 
		 * */
		
		if(!CollectionUtils.isEmpty(transPickupDetailList)&&transPickupDetailList.size()>0){
			/**
			   * 增加商务专递产品类型
			   * by wqh
			   * 2015-09-06
			   * */
			  List<String> airProductCodes=new ArrayList<String>();
			  airProductCodes.add(AirfreightConstants.AIR_PROCDUCT_CODE);
			  airProductCodes.add(AirfreightConstants.AIR_PACKAGE_PROCDUCT_CODE);
			  
			for(int i=0;i<transPickupDetailList.size();i++){
				String waybillNo=transPickupDetailList.get(i).getWaybillNo();
				
				WaybillEntity waybillEntry=waybillManagerService.queryWaybillBasicByNo(waybillNo);
				if(!airProductCodes.contains(waybillEntry.getProductCode())){
					throw new TfrBusinessException("运单："+waybillNo+" 不是空运运单，删除后再保存合票单据");
				}
			}
		}
		
		//中转提货清单新增运单明细
		List<AirTransPickupDetailEntity> addTransPickupDetailList = new ArrayList<AirTransPickupDetailEntity>();
		//中转提货清单删除运单明细
		List<String> deleteTransPickupDetailList = new ArrayList<String>();
		//删除列表,用于调用结算接口
		List<String> stlDeleteList = new ArrayList<String>();
		//修改列表,用于调用结算接口
		List<AirTransPickupDetailEntity> stlModifyList = new ArrayList<AirTransPickupDetailEntity>();
		//新增列表,用于调用结算接口
		List<AirTransPickupDetailEntity> stlAddList = new ArrayList<AirTransPickupDetailEntity>();
		 
		//根据中转提货清单id获取中转提货清单
		AirTransPickupbillEntity airTransPickupbillEntity = queryAirTransPickupbillEntityById(airTransferPickUpBillId);
		//航空正单号
		String airWaybillNo = airTransPickupbillEntity.getAirWaybillNo();
		
		Map<String,AirTransPickupDetailEntity> oldDetailMap = new HashMap<String, AirTransPickupDetailEntity>();
		Map<String,AirTransPickupDetailEntity> newDetailMap = new HashMap<String, AirTransPickupDetailEntity>();
		//此次中转操作相关运单，包含在前台新增，修改的运单（用于查询这些运单是否已签收）
		List<String> detailWaybillNoList = new ArrayList<String>();
		//中转原始运单信息
		List<AirTransPickupDetailEntity> oldTransPickupbillDetailList = queryTfrPickupBillDetail(airTransferPickUpBillId);
		//把newList转换成map
		for(AirTransPickupDetailEntity newDetailEntity : transPickupDetailList){
			newDetailMap.put(newDetailEntity.getId(), newDetailEntity);
			//此次中转操作新增和修改的运单
			detailWaybillNoList.add(newDetailEntity.getWaybillNo());
		}
		//把list转换成map
		for(AirTransPickupDetailEntity oldDetailEntity : oldTransPickupbillDetailList){
			//把oldList转换成map
			oldDetailMap.put(oldDetailEntity.getId(), oldDetailEntity);
			//如果新list明细里不包含oldList里的合票明细，说明该明细被删除
			if(!newDetailMap.containsKey(oldDetailEntity.getId())){
				//中转删除集合
				deleteTransPickupDetailList.add(oldDetailEntity.getId());
				//调用结算 删除集合
				stlDeleteList.add(oldDetailEntity.getWaybillNo());
				//添加此次中转操作删除的运单
				detailWaybillNoList.add(oldDetailEntity.getWaybillNo());
			}
		}
		for(AirTransPickupDetailEntity newDetailEntity : transPickupDetailList){
			//如果oldList明细里不包含newList里的合票明细，说明该明细新增
			if(!oldDetailMap.containsKey(newDetailEntity.getId())){
				newDetailEntity.setAirWaybillNo(airWaybillNo);
				//中转的新增运单集合
				addTransPickupDetailList.add(newDetailEntity);
				//调用结算 新增集合
				stlAddList.add(newDetailEntity);
			}
		}
		//如果修改的运单有已经签收的则不允许修改运单信息
		if(detailWaybillNoList.size() > 0){
			//查询已删除运单是否已签收
			List<String> delWaybillNoSignList = waybillSignResultService.queryWaybillSignResultWaybillNos(detailWaybillNoList);
			if(delWaybillNoSignList != null && delWaybillNoSignList.size() > 0){
				//拼接已经签收的运单号
				StringBuffer delSignNos = new StringBuffer();
				for(int i = 0;i<delWaybillNoSignList.size();i++){
					delSignNos.append(delWaybillNoSignList.get(i)).append(",");
				}
				throw new TfrBusinessException("存在已签收的运单！" + delSignNos.substring(0,delSignNos.length()-1));
			}
		}
		//修改变更日志
		List<AirRevisebillDetailEntity> airRevisebillDetailList = new ArrayList<AirRevisebillDetailEntity>();
		//根据合票明细list查询符合条件的运单原始信息
		stlModifyList = getOriginalAirPickupbillList(transPickupDetailList,oldTransPickupbillDetailList,airRevisebillDetailList);
		
		
		//校验需要新增的中转提货清单明细list是否为空
		if(!CollectionUtils.isEmpty(addTransPickupDetailList)){
			List<AirTransPickupDetailEntity> addList = returnAirTransPickupDetailList(addTransPickupDetailList,airTransferPickUpBillId);
			airQueryModifyTfrPickupBillDao.addAirTransPickupDetailList(addList);
		}
		
		//修改合大票清单明细数据
		if(!CollectionUtils.isEmpty(transPickupDetailList)){
			airQueryModifyTfrPickupBillDao.modifyAirTransPickupDetailList(transPickupDetailList);
		}
		
		//判断删除列表非空
		if(!CollectionUtils.isEmpty(deleteTransPickupDetailList)){
			airQueryModifyTfrPickupBillDao.delTransPickupDetailList(deleteTransPickupDetailList);
		}
		
		//判断变更明细列表非空
		if(!CollectionUtils.isEmpty(airRevisebillDetailList)){
			airChangeInventoryService.addaddAirRevisebillDetailList(airRevisebillDetailList);
		}

		
		//校验结算list是否为空
		if(stlAddList.size() >0 || stlModifyList.size() > 0 || stlDeleteList.size() > 0){
			//根据当前部门取空运总调
			String deptCode = FossUserContext.getCurrentDeptCode();
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
			UserEntity user = FossUserContext.getCurrentInfo().getUser();
			//封装运单List，传给灰度判断
			List<String> wayBillList = new ArrayList<String>();
			//添加运单列表
			for (AirTransPickupDetailEntity addEntity: stlAddList) {
				wayBillList.add(addEntity.getWaybillNo());
			}
			//修改运单列表
			for (AirTransPickupDetailEntity modifyEntity: stlModifyList) {
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
			List<String> cubcWayBillList = new ArrayList<String>();
			List<AirTransPickupDetailEntity> fossAddEntityList = new ArrayList<AirTransPickupDetailEntity>();
			List<AirTransPickupDetailEntity> ucbcAddEntityList = new ArrayList<AirTransPickupDetailEntity>();
			List<AirTransPickupDetailEntity> fossModifyEntityList = new ArrayList<AirTransPickupDetailEntity>();
			List<AirTransPickupDetailEntity> ucbcModifyEntityList = new ArrayList<AirTransPickupDetailEntity>();
			List<String> fossDelEntityList = new ArrayList<String>();
			List<String> ucbcDelEntityList = new ArrayList<String>();
			//分析灰度返回结果集，如果是2条的情况
			if (vestResponseDto.getVestBatchResult().size() > 1) {
				fossCubc = CUBCGrayContants.SYSTEM_CODE_FOSS_CUBC;
			}
				
			//循环得到分流的运单号
			for (VestBatchResult vestResult : vestResponseDto.getVestBatchResult()) {
				 if (vestResult.getVestSystemCode().equals(CUBCGrayContants.SYSTEM_CODE_FOSS)) {
					 fossWayBillList = vestResult.getVestObject();
				 } else {
					 cubcWayBillList = vestResult.getVestObject();
				 }
			}
			
			// 解析FOSS分流单号
			for (String wayBillNo : fossWayBillList) {
				//FOSS添加的运单号对应的实体
				for (AirTransPickupDetailEntity addEntity: stlAddList) {
					if (wayBillNo.equals(addEntity.getWaybillNo())) {
						fossAddEntityList.add(addEntity);
					}
				}
				//FOSS修改对应的实体
				for (AirTransPickupDetailEntity modifyEntity: stlModifyList) {
					if (wayBillNo.equals(modifyEntity.getWaybillNo())) {
						fossModifyEntityList.add(modifyEntity);
					}
				}
				//FOSS删除的运单号
				for (String delWayBill : stlDeleteList) {
					if (wayBillNo.equals(delWayBill)) {
						fossDelEntityList.add(delWayBill);
					}
				}
				
			}
			// 解析CUBC分流单号
			for (String wayBillNo : cubcWayBillList) {
				//CUBC添加的运单号对应的实体
				for (AirTransPickupDetailEntity addEntity: stlAddList) {
					if (wayBillNo.equals(addEntity.getWaybillNo())) {
						ucbcAddEntityList.add(addEntity);
					}
					
				}
				//CUBC修改对应的实体
				for (AirTransPickupDetailEntity modifyEntity: stlModifyList) {
					if (wayBillNo.equals(modifyEntity.getWaybillNo())) {
						ucbcModifyEntityList.add(modifyEntity);
					}
				}
				//CUBC删除的运单号
				for (String delWayBill : stlDeleteList) {
					if (wayBillNo.equals(delWayBill)) {
						ucbcDelEntityList.add(delWayBill);
					}
				}
			}
			//重新赋值
			stlAddList = fossAddEntityList;
					
			stlModifyList = fossModifyEntityList;
			
			stlDeleteList = fossDelEntityList;

			if (CUBCGrayContants.SYSTEM_CODE_FOSS.equals(vestResponseDto.getVestBatchResult().get(0).getVestSystemCode())
					|| fossCubc.equals(CUBCGrayContants.SYSTEM_CODE_FOSS_CUBC)) {
				LOGGER.info("修改中转提货清单明细调结算接口开始时间："+new Date());

				try {
					airTransferService.modifyAirTransferDetail(
							airTransPickupbillEntity, 
							airTransPickupbillEntity, 
							stlAddList, 
							stlModifyList, 
							stlDeleteList, 
							new CurrentInfo(user,orgAdministrativeInfoEntity));
					
					LOGGER.info("修改中转提货清单明细调结算接口结束时间："+new Date());
				} catch (SettlementException e) {
					LOGGER.error("修改中转提货清单调用结算接口出现异常，异常信息：" + e.getErrorCode());
					throw new  TfrBusinessException("修改中转提货清单调用结算接口出现异常:"+e.getErrorCode());
				}
			}
			

			if (CUBCGrayContants.SYSTEM_CODE_CUBC.equals(vestResponseDto.getVestBatchResult().get(0).getVestSystemCode())
					|| fossCubc.equals(CUBCGrayContants.SYSTEM_CODE_FOSS_CUBC)) {
				//365455 修改中转提货清单
				LOGGER.info("修改中转提货清单明细调结算接口开始时间："+new Date());
				AirTransPickupbillCUBCDto airTransPickupbillCUBCDto=new AirTransPickupbillCUBCDto();
				airTransPickupbillCUBCDto.setAirTransPickupbillEntity(airTransPickupbillEntity);
				airTransPickupbillCUBCDto.setAirTransPickupDetailEntities(ucbcModifyEntityList);
				airTransPickupbillCUBCDto.setStlAddList(ucbcAddEntityList);
				airTransPickupbillCUBCDto.setStlDeleteList(ucbcDelEntityList);
				String requestStr=JSONObject.toJSONString(airTransPickupbillCUBCDto);
				 LOGGER.error("传递给CUBC的参数 requestStr = "+requestStr);
				   ResponseExternalBillDto responseDto = fossToCubcService.pushupdateairTransfer(requestStr);
				   LOGGER.error("responseDto===" + responseDto);
					//ESB异常
					if(null == responseDto) {
						//保存异常信息，标注ESB异常，便于查找问题原因
						//推送失败时，异常信息处理  doto
						throw new  TfrBusinessException("网络异常：未接收到cubc返回信息");
					} else if("0".equals(responseDto.getResult())){
						throw new  TfrBusinessException("FOSS修改中转提货清单失败，失败原因："+responseDto.getReason());
					}
				
				LOGGER.info("修改中转提货清单明细调结算接口结束，时间："+new Date());
			}
		}
		LOGGER.info("修改中转提货清单明细结束时间："+new Date());
			
		return 0;
	}
	
	/**
	 * 根据运单号list查询符合条件的运单原始信息
	 * @param List 合大票清单明细列表 
	 * @author 099197-foss-zhoudejun
	 * @date 2013-2-25 上午10:40:37
	 */
	private List<AirTransPickupDetailEntity> getOriginalAirPickupbillList(
			List<AirTransPickupDetailEntity> airTransPickupbillDetailList,
			List<AirTransPickupDetailEntity> oldTransPickupbillDetailList,
			List<AirRevisebillDetailEntity> airRevisebillDetailList){
		//调用结算    修改列表
		List<AirTransPickupDetailEntity> modifyAirTransPickupbillDetailList =  new ArrayList<AirTransPickupDetailEntity>();
		//获取当前用户信息
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		//遍历原始送货费列表
		for (AirTransPickupDetailEntity origDetailEntity : oldTransPickupbillDetailList) {
			//遍历现有的合大票清单明细列表
			for (AirTransPickupDetailEntity newDetailEntity : airTransPickupbillDetailList) {
				//原运单号=现有清单列表中的运单号
				if(origDetailEntity.getWaybillNo().equals(newDetailEntity.getWaybillNo())){
					//变更内容
					StringBuffer reviseContent = new StringBuffer();
					if(origDetailEntity.getDeliverFee().compareTo(newDetailEntity.getDeliverFee()) != 0){
						//如果运单送货费和之前不一样,则把新运单信息用放到modifyAirPickupbillDetailList
						modifyAirTransPickupbillDetailList.add(newDetailEntity);
						//修改送货费
						reviseContent.append("送货费：" + newDetailEntity.getDeliverFee());
						reviseContent.append("；");
					}
					fixReviseContent(origDetailEntity, newDetailEntity,
							reviseContent);
					
					int length = reviseContent.length();
					if(length >= ConstantsNumberSonar.SONAR_NUMBER_1000){
						throw new AirChangeInventoryException("备注长度过长！");
					}
					if(length > 0){
						//变更明细
						AirRevisebillDetailEntity airRevisebillDetailEntity = new AirRevisebillDetailEntity();
						//设置合大票明细id
						airRevisebillDetailEntity.setAirPickupbillDetailId(newDetailEntity.getAirTransferPickupbillId());
						//中转提货明细id
						airRevisebillDetailEntity.setAirTransPickupbillDetailId(newDetailEntity.getAirTransferPickupbillId());
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
		return modifyAirTransPickupbillDetailList;
	}

	/**
	 * sonar优化 wwb 311396 2016年12月24日10:15:41
	 * @param origDetailEntity
	 * @param newDetailEntity
	 * @param reviseContent
	 */
	private void fixReviseContent(AirTransPickupDetailEntity origDetailEntity,
			AirTransPickupDetailEntity newDetailEntity,
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
			if(!newDetailEntity.getNotes() .equals("") || origDetailEntity.getNotes() != null){
				reviseContent.append("备注：" + newDetailEntity.getNotes());
				reviseContent.append("；");
			}
		}
	}
	
	/**
	 * 处理新增中转提货清单明细list.
	 * @param addirTransPickupDetailList the addir trans pickup detail list
	 * @param airTransferPickUpBillId the air transfer pick up bill id
	 * @return the list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-21 下午3:26:57
	 */
	private List<AirTransPickupDetailEntity> returnAirTransPickupDetailList(List<AirTransPickupDetailEntity> addirTransPickupDetailList,String airTransferPickUpBillId) {
		List<AirTransPickupDetailEntity> resultList = new ArrayList<AirTransPickupDetailEntity>();
		for (AirTransPickupDetailEntity airTransPickupDetailEntity : addirTransPickupDetailList) {
			airTransPickupDetailEntity.setId(UUIDUtils.getUUID());
			airTransPickupDetailEntity.setAirTransferPickupbillId(airTransferPickUpBillId);
			resultList.add(airTransPickupDetailEntity);
		}
		return resultList;
	}
	
	/**
	 * 根据中转提货清单id查询中转提过货清单
	 * @param airTransferPickUpBillId id
	 * @return AirTransPickupbillEntity 返回中转提货清单
	 * @author liuzhaowei
	 * @date 2013-07-09 上午9:40:31
	 */
	@Override
	public AirTransPickupbillEntity queryAirTransPickupbillEntityById(String airTransferPickUpBillId){
		return airQueryModifyTfrPickupBillDao.queryAirTransPickupbillEntityById(airTransferPickUpBillId);
	}
	
	/**
	 * 根据中转提货清单id删除中转提过货清单
	 * 中转提货清单中只包含一个运单；若清单中包含多个运单，则无法删除，系统提示“此中转清单中包含多个运单，不允许删除。”。
     * 检查运单签收状态和财务单据核销状态；若清单中的运单已签收，已核销，则无法删除，系统提示“此清单中包含已经签收的运单，不允许删除。”。
     * 检查包含的运单经过中转的次数；如果经过经过2个以上（包含2个）中转，则无法删除，系统需提示“此清单中运单已经过多次中转，不允许删除。”；
     * 在删除包含运单信息时，执行原有业务规则（调用结算接口等）。如不成功，则不允许删除清单，提示语同合票清单中的运单的提示语相同。
     * 删除后的正单号可再次做清单。
	 * @param airTransferPickUpBillId id
	 * @return void
	 * @author 105795-foss-wqh
	 * @date 2014-01-13
	 */
	@Transactional
	public void deleteTfrAirPickupbillById(String airTransferPickUpBillId){
		//校验参数
		if(StringUtil.isEmpty(airTransferPickUpBillId)){
			throw new TfrBusinessException("中转提货清单不存在");
		}
		//根据中转提货清单id获取中转提货清单
		AirTransPickupbillEntity airTransPickupbillEntity = queryAirTransPickupbillEntityById(airTransferPickUpBillId);
		/*检查中转提货清单中是否包含多个运单
		*/
		 //1 根据中转提货清单id查询中转提货清单明细
		List<AirTransPickupDetailEntity> detailList=queryTfrPickupBillDetail(airTransferPickUpBillId);
		if(detailList.size()>1){
			throw new TfrBusinessException("此中转清单:{"+airTransPickupbillEntity.getAirTransferPickupbillNo()+"}中包含多个运单，不允许删除");

		}
		/*
		 * 检查运单签收状态和财务单据核销状态；若清单中的运单已签收，已核销，则无法删除，系统提示“此清单中包含已经签收的运单，不允许删除
		 * */
		 List<String> signWaybills=new ArrayList<String>();
		 signWaybills.add(detailList.get(0).getWaybillNo());
		 List<String> delWaybillNoSignList = waybillSignResultService.queryWaybillSignResultWaybillNos(signWaybills);
         if(delWaybillNoSignList.size()>0){
 			throw new TfrBusinessException("此中转清单:{"+airTransPickupbillEntity.getAirTransferPickupbillNo()+"}中包含已签收的运单:{"+signWaybills.get(0)+"}，不允许删除");

         }
		 //运单是否中转多次
        List<AirTransPickupbillEntity> airTransPickupbillList =airQueryModifyTfrPickupBillDao.queryAirTransPickupbillByWaybillNo(detailList.get(0).getWaybillNo());
		if(airTransPickupbillList.size()>1){
 			throw new TfrBusinessException("此中转清单:{"+airTransPickupbillEntity.getAirTransferPickupbillNo()+"}中的运单:{"+signWaybills.get(0)+"}，已经经过多次中转，不允许删除");

		}
		
		//封装灰度实体，类型是正单
		GrayParameterDto parDto = new GrayParameterDto();
		parDto.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
		parDto.setSourceBillNos((String []) signWaybills.toArray(new String [signWaybills.size()]));
		VestResponse vestResponse = cubcUtil.getUcbcGrayData(parDto, new Throwable());
		
		if (CUBCGrayContants.SYSTEM_CODE_FOSS.equals(vestResponse.getVestBatchResult().get(0).getVestSystemCode())) {
			//调用结算接口
			//根据当前部门取空运总调
			String deptCode = FossUserContext.getCurrentDeptCode();
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
			UserEntity user = FossUserContext.getCurrentInfo().getUser();
			try {
				airTransferService.modifyAirTransferDetail(
						airTransPickupbillEntity, 
						airTransPickupbillEntity, 
						null, 
						null, 
						signWaybills, 
						new CurrentInfo(user,orgAdministrativeInfoEntity));
			} catch (SettlementException e) {
				LOGGER.error("删除合票清单调用结算接口出现异常，异常信息：" + e.getErrorCode());
				throw new  TfrBusinessException("删除中转提货清单调用结算接口出现异常:"+e.getErrorCode());
			}
		} else {
			//TODO 365455
			LOGGER.info("删除中转提货清单明细调结算接口开始时间："+new Date());
			AirTransPickupbillCUBCDto airTransPickupbillCUBCDto=new AirTransPickupbillCUBCDto();
			airTransPickupbillCUBCDto.setAirTransPickupbillEntity(airTransPickupbillEntity);
			airTransPickupbillCUBCDto.setStlDeleteList(signWaybills);
			String requestStr=JSONObject.toJSONString(airTransPickupbillCUBCDto);
			ResponseExternalBillDto responseDto = fossToCubcService.pushdeleteairTransfer(requestStr);
			LOGGER.error("responseDto===" + responseDto);
				//ESB异常
			if(null == responseDto) {
				//保存异常信息，标注ESB异常，便于查找问题原因
				//推送失败时，异常信息处理  doto
				throw new  TfrBusinessException("ESB出现异常:"+responseDto);
			}else if ("0".equals(responseDto.getResult())){
				throw new  TfrBusinessException("FOSS中转提货清单删除失败，失败原因："+responseDto.getReason());
			}
			LOGGER.info("根据中转提货清单id删除中转提过货清单接口结束，  时间："+new Date());
		}
        
		if(detailList!=null&&detailList.size()>0){
			List<String> ids=new ArrayList<String>();
			ids.add(detailList.get(0).getId());
			//删除中转提货清单明细
			airQueryModifyTfrPickupBillDao.delTransPickupDetailList(ids);
			
		}
		//删除中转提货清单
		airQueryModifyTfrPickupBillDao.deleteTfrAirPickupbillById(airTransferPickUpBillId);

		
	}
	
	
	
	/**
	 * 设置 注入查询_修改中转提货清单dao接口.
	 * @param airQueryModifyTfrPickupBillDao the new 注入查询_修改中转提货清单dao接口
	 */
	public void setAirQueryModifyTfrPickupBillDao(
			IAirQueryModifyTfrPickupBillDao airQueryModifyTfrPickupBillDao) {
		this.airQueryModifyTfrPickupBillDao = airQueryModifyTfrPickupBillDao;
	}
	
	/**
	 * 设置 注入变更服务.
	 * @param airChangeInventoryService the new 注入变更服务
	 */
	public void setAirChangeInventoryService(
			IAirChangeInventoryService airChangeInventoryService) {
		this.airChangeInventoryService = airChangeInventoryService;
	}

	public void setAirTransferService(IAirTransferService airTransferService) {
		this.airTransferService = airTransferService;
	}

	public void setAirDispatchUtilService(
			IAirDispatchUtilService airDispatchUtilService) {
		this.airDispatchUtilService = airDispatchUtilService;
	}
	
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
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

	
	
}