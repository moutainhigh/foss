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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/server/service/impl/AirUnshippedGoodsService.java
 *  
 *  FILE NAME          :AirUnshippedGoodsService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFlightService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirUnshippedGoodsDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirDispatchUtilService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirUnshippedGoodsService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirUnshippedGoodsEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirUnshippedNoticeEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirUnshippedSerialNoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillSerialNoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirUnshippedGoodsDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * 拉货相关操作
 * @author foss-liuxue(for IBM)
 * @date 2012-11-29 下午8:42:38
 */
public class AirUnshippedGoodsService implements IAirUnshippedGoodsService {

	/**
	 * 拉货操作dao
	 */
	private IAirUnshippedGoodsDao airUnshippedGoodsDao;
	
	/**
	 * 入库接口
	 */
	private IStockService stockService;  
	
	/**
	 * 通知接口
	 */
	private IMessageService messageService; 
	
	/**
	 * 航班信息接口
	 */
	private IFlightService flightService;  
	
	/**
	 * 通知模板接口
	 */
	private ISMSTempleteService sMSTempleteService;
	
	/**
	 * 日志打印
	 */
	private static final Logger LOGGER = LogManager.getLogger(AirUnshippedGoodsService.class);
	
	/**
	 * 获取上级空运总调
	 */
	private IAirDispatchUtilService airDispatchUtilService;
	
	
	/**
	 * 设置 获取上级空运总调.
	 *
	 * @param airDispatchUtilService the new 获取上级空运总调
	 */
	public void setAirDispatchUtilService(
			IAirDispatchUtilService airDispatchUtilService) {
		this.airDispatchUtilService = airDispatchUtilService;
	}
 
	/**
	 * 设置 通知模板接口.
	 *
	 * @param smsTempleteService the new 通知模板接口
	 */
	public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
		this.sMSTempleteService = sMSTempleteService;
	}
	

	/**
	 * 设置 拉货操作dao.
	 *
	 * @param airUnshippedGoodsDao the new 拉货操作dao
	 */
	public void setAirUnshippedGoodsDao(IAirUnshippedGoodsDao airUnshippedGoodsDao) {
		this.airUnshippedGoodsDao = airUnshippedGoodsDao;
	}

	/**
	 * 设置 航班信息接口.
	 *
	 * @param flightService the new 航班信息接口
	 */
	public void setFlightService(IFlightService flightService) {
		this.flightService = flightService;
	}

	/**
	 * 设置 入库接口.
	 *
	 * @param stockService the new 入库接口
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	/**
	 * 设置 通知接口.
	 *
	 * @param messageService the new 通知接口
	 */
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	/**
	 * 查询拉货信息
	 * @author LiuXue
	 * @date 2012-11-29 下午8:42:51
	 */
	@Override
	@Transactional(readOnly = true)
	public List<AirUnshippedGoodsEntity> queryUnshippedGoods(
			AirUnshippedGoodsDto airUnshippedGoodsDto, int start, int limit) {
		//如果選擇的是正单号则根据正单表去关联
		if(StringUtils.equals(AirfreightConstants.AIR_WAY_BILL_NO, airUnshippedGoodsDto.getBillNoType())){
			return airUnshippedGoodsDao.queryAirUnshippedGoodsByAirWaybillNo(airUnshippedGoodsDto, start, limit);
		}else{
			//否则根据运单表关联
			return airUnshippedGoodsDao.queryAirUnshippedGoodsByWaybillNo(airUnshippedGoodsDto, start, limit);
		}
	}

	/**
	 * 获取总记录条数
	 * @author LiuXue
	 * @date 2012-12-3 14:53:32
	 */
	@Override
	@Transactional(readOnly = true)
	public Long getCount(AirUnshippedGoodsDto airUnshippedGoodsDto) {
		//如果選擇的是正单号则根据正单表去关联
		if(StringUtils.equals(AirfreightConstants.AIR_WAY_BILL_NO, airUnshippedGoodsDto.getBillNoType())){
			return airUnshippedGoodsDao.getCountByAirWaybillNo(airUnshippedGoodsDto);
		}else{
			//否则根据运单表关联
			return airUnshippedGoodsDao.getCountByWaybillNo(airUnshippedGoodsDto);
		}
	}

	/**
	 * 根据正单号或运单号查找信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-11 下午2:52:41
	 */
	@Override
	@Transactional(readOnly = true)
	public List<AirWaybillSerialNoEntity> querySerialNoByWaybillNo(
			AirUnshippedGoodsDto airUnshippedGoodsDto) {
		//单号类型
		String billNoType = airUnshippedGoodsDto.getBillNoType();
		List<AirWaybillSerialNoEntity> airWaybillSerialNoList = new ArrayList<AirWaybillSerialNoEntity>();
		//根据单号类型判断根据正单号查询还是根据运单号查询
		if(StringUtils.equals(billNoType, AirfreightConstants.WAY_BILL_NO)){
			//根据代单号获得流水号
			airWaybillSerialNoList= airUnshippedGoodsDao.querySerialNoByWaybillNo(airUnshippedGoodsDto);
		}
		return airWaybillSerialNoList;
	}

	/**
	 * 根据正单号/代单号获取拉货信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-11 下午8:03:04
	 */
	@Override
	@Transactional(readOnly = true)
	public AirUnshippedGoodsEntity queryUnshippedGoodsByBillNo(
			AirUnshippedGoodsDto airUnshippedGoodsDto) {
		String billNoType = airUnshippedGoodsDto.getBillNoType();
		List<AirUnshippedGoodsEntity> airUnshippedGoodsList = new ArrayList<AirUnshippedGoodsEntity>();
		//根据单号类型判断根据正单号查询还是根据运单号查询
		if(StringUtils.equals(billNoType, AirfreightConstants.AIR_WAY_BILL_NO)){
			airUnshippedGoodsList = airUnshippedGoodsDao.queryUnshippedGoodsByAirWaybillNo(airUnshippedGoodsDto);
		}
		//根据运单表关联
		if(StringUtils.equals(billNoType, AirfreightConstants.WAY_BILL_NO)){
			//校验代单是否做过正单
			Long resultCount = airUnshippedGoodsDao.waybillIsValidate(airUnshippedGoodsDto.getBillNo());
			if(resultCount.intValue() < 1){
				throw new TfrBusinessException("当前代单没有生成正单，不能拉货！","");
			}
			airUnshippedGoodsList = airUnshippedGoodsDao.queryUnshippedGoodsByWaybillNo(airUnshippedGoodsDto);
		}
		//没找到信息则抛出异常
		if(airUnshippedGoodsList.size() < 1){
			throw new TfrBusinessException("根据当前单号未找到相应信息！");
		}
		AirUnshippedGoodsEntity airUnshippedGoodsEntity = airUnshippedGoodsList.get(0);
		//给页面拉货件数赋上初始值
		airUnshippedGoodsEntity.setUnshippedGoodsQty(airUnshippedGoodsDao.querySerialNoByWaybillNo(airUnshippedGoodsDto).size());  //初始化拉货件数为运单下所有流水号数
		return airUnshippedGoodsEntity;
	}
	
	/**
	 * 校验签收状态
	 * @author foss-liuxue(for IBM)
	 * @date 2013-2-1 下午5:56:21
	 */
	public boolean validateIsSign(AirUnshippedGoodsDto airUnshippedGoodsDto){
		Long count = airUnshippedGoodsDao.validateIsSign(airUnshippedGoodsDto);
		//如果查询条数大于0，则代表已经签收，返回true
		if(count.intValue() > 0){
			return true;
		}
		return false;
	}

	/**
	 * 录入或修改拉货信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-12 下午4:42:39
	 */
	@Override
	@Transactional
	public int saveOrUpdateAirUnshippedGoods(AirUnshippedGoodsDto airUnshippedGoodsDto) {
		//页面传递的拉货实体
		AirUnshippedGoodsEntity airUnshippedGoodsEntity = airUnshippedGoodsDto.getAirUnshippedGoodsEntity();
		//拉货时间
		airUnshippedGoodsEntity.setUnshippedTime(DateUtils.convert(airUnshippedGoodsDto.getUnshippedTime(),"yyyy-MM-dd HH:mm:ss"));  
		//拉货ID
		String airUnshippedGoodsId = airUnshippedGoodsEntity.getId();
		//制单部门
		String createOrgCode = airUnshippedGoodsDao.queryCreateOrgCodeByBillNo(airUnshippedGoodsDto);
		//用于校验签收状态的参数
		AirUnshippedGoodsDto validateIsSignDto = null;
		//校验制单部门也当前部门是否一致，不一致则抛出异常
		if(!StringUtils.equals(getOrgAdministrative().getCode(), createOrgCode)){
			throw new TfrBusinessException("单号制单部门与当前部门不一致，无法拉货！","");
		}
		//如果ID为空，则代表是录入拉货，否则是修改拉货
		if(StringUtils.isBlank(airUnshippedGoodsId)){
			//录入拉货信息
			airUnshippedGoodsId = UUIDUtils.getUUID();
			//ID
			airUnshippedGoodsEntity.setId(airUnshippedGoodsId);
			//配载部门名称
			airUnshippedGoodsEntity.setAirAssembleOrgName(getOrgAdministrative().getName());   
			//配载部门编码
			airUnshippedGoodsEntity.setAirAssembleOrgCode(getOrgAdministrative().getCode());
			//创建人名称
			airUnshippedGoodsEntity.setCreateUserName(FossUserContext.getCurrentUser().getEmployee().getEmpName()); 
			//创建人编码
			airUnshippedGoodsEntity.setCreateUserCode(FossUserContext.getCurrentUser().getEmployee().getEmpCode());   
			//创建时间
			airUnshippedGoodsEntity.setCreateTime(new Date());
			//修改人名称
			airUnshippedGoodsEntity.setUpdateUserName(FossUserContext.getCurrentUser().getEmployee().getEmpName());   
			//修改人编码
			airUnshippedGoodsEntity.setUpdateUserCode(FossUserContext.getCurrentUser().getEmployee().getEmpCode()); 
			//修改时间
			airUnshippedGoodsEntity.setModifyTime(new Date());  
			//存入ID到DTO，标记此条记录是录入过的，下次保存将执行更新
			airUnshippedGoodsDto.setId(airUnshippedGoodsId);  
			//流水号集合
			List<AirUnshippedSerialNoEntity> airUnshippedSerialNoList = new ArrayList<AirUnshippedSerialNoEntity>();
			//如果是正单拉货，则需要根据正单号获取运单号+流水号，并存入拉货流水号表中
			if(StringUtils.equals(airUnshippedGoodsDto.getBillNoType(), AirfreightConstants.AIR_WAY_BILL_NO)){
				//校验是否已经拉货
				Long resultCount = airUnshippedGoodsDao.queryIsEmptyUnshippedGoods(airUnshippedGoodsDto.getBillNo());
				//返回count>0则表示此正单已拉货
				if(resultCount.intValue() > 0){
					throw new TfrBusinessException("当前正单已经做过拉货操作，不能重复拉货！","");
				}
				//根据正单号获取运单号+流水号
				airUnshippedSerialNoList = airUnshippedGoodsDao.querySerialNoByAirWaybillNo(airUnshippedGoodsDto);
				//给拉货流水号赋值
				for(AirUnshippedSerialNoEntity airUnshippedSerialNoEntity : airUnshippedSerialNoList){
					validateSerialInf(airUnshippedSerialNoEntity);
					airUnshippedSerialNoEntity.setUnshippedGoodsId(airUnshippedGoodsId);
					airUnshippedSerialNoEntity.setId(UUIDUtils.getUUID());
				}
			}else{
				//如果是代单号，则在拉货明细中录入选择的流水号
				airUnshippedSerialNoList = airUnshippedGoodsDto.getAirUnshippedSerialNoList();
				//校验是否选择了流水号
				if(airUnshippedSerialNoList.size() < 1){
					throw new TfrBusinessException("必须选择至少一条流水号","");
				}
				//给拉货流水号赋值
				for(AirUnshippedSerialNoEntity airUnshippedSerialNoEntity : airUnshippedSerialNoList){
					//校验签收状态
					validateSerialInf(airUnshippedSerialNoEntity);
					//添加其他信息
					airUnshippedSerialNoEntity.setUnshippedGoodsId(airUnshippedGoodsId);
					airUnshippedSerialNoEntity.setWaybillNo(airUnshippedGoodsDto.getBillNo());
					airUnshippedSerialNoEntity.setId(UUIDUtils.getUUID());
				}
			}
			if(airUnshippedSerialNoList.size() == 0){
				throw new TfrBusinessException("当前正单下不存在运单或者运单下不存在流水号！","");
			}
			//自动通知拉货
			airUnshippedGoodsNoise(airUnshippedGoodsEntity);
			//录入拉货明细
			airUnshippedGoodsDao.addAirUnshippedSerialNo(airUnshippedSerialNoList);
			//录入拉货信息
			return airUnshippedGoodsDao.addAirUnshippedGoods(airUnshippedGoodsEntity);
		}else{
			//修改拉货信息
			airUnshippedGoodsEntity.setUpdateUserName(FossUserContext.getCurrentUser().getEmployee().getEmpName());
			airUnshippedGoodsEntity.setUpdateUserCode(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
			airUnshippedGoodsEntity.setModifyTime(new Date());
			//存入ID到DTO，标记此条记录是录入过的，下次保存将执行更新
			airUnshippedGoodsDto.setId(airUnshippedGoodsId);  
			if(StringUtils.equals(airUnshippedGoodsDto.getAirUnshippedGoodsEntity().getUnshippedType(), AirfreightConstants.WAY_BILL_NO)){
				//删除全部流水号
				airUnshippedGoodsDao.deleteAirUnshippedSerialNo(airUnshippedGoodsId);
				//获取流水号集合
				List<AirUnshippedSerialNoEntity> airUnshippedSerialNoList = airUnshippedGoodsDto.getAirUnshippedSerialNoList();
				//添加新勾选的流水号
				for(AirUnshippedSerialNoEntity airUnshippedSerialNoEntity : airUnshippedSerialNoList){
					//拉货ID
					airUnshippedSerialNoEntity.setUnshippedGoodsId(airUnshippedGoodsId);
					//运单号
					airUnshippedSerialNoEntity.setWaybillNo(airUnshippedGoodsDto.getAirUnshippedGoodsEntity().getBillNo());
					//ID
					airUnshippedSerialNoEntity.setId(UUIDUtils.getUUID());
				}
				//录入拉货明细
				airUnshippedGoodsDao.addAirUnshippedSerialNo(airUnshippedSerialNoList);
			}
			//修改拉货信息
			return airUnshippedGoodsDao.updateAirUnshippedGoods(airUnshippedGoodsEntity);
		}
	}
	
	/**
	 * sonar优化 wwb 311396 2016年12月24日10:34:00
	 * @param airUnshippedSerialNoEntity
	 */
	private void validateSerialInf(
			AirUnshippedSerialNoEntity airUnshippedSerialNoEntity) {
		AirUnshippedGoodsDto validateIsSignDto;
		validateIsSignDto = new AirUnshippedGoodsDto();
		validateIsSignDto.setBillNo(airUnshippedSerialNoEntity.getWaybillNo());
		boolean signStatus = validateIsSign(validateIsSignDto);
		//已签收则抛出异常
		if(signStatus){
			throw new TfrBusinessException("运单号"+airUnshippedSerialNoEntity.getWaybillNo()+"已经签收，不能拉货！","");
		}
	}
	
	/**
	 * 拉货入库操作方法
	 * @author foss-liuxue(for IBM)
	 * @date 2013-3-13 下午2:36:28
	 */
	public void unshippedGoods(InOutStockEntity inOutStockEntity,List<AirUnshippedSerialNoEntity> airUnshippedSerialNoList,Integer unshippedGoodsQty){
		AirUnshippedSerialNoEntity airUnshippedSerialNoEntity = null;
		//遍历运单和流水号集合
		for(int i = 0; i< airUnshippedSerialNoList.size();i++){
			airUnshippedSerialNoEntity = airUnshippedSerialNoList.get(i);
			inOutStockEntity = new InOutStockEntity();
			//运单号
			inOutStockEntity.setWaybillNO(airUnshippedSerialNoEntity.getWaybillNo());  
			//流水号
			inOutStockEntity.setSerialNO(airUnshippedSerialNoEntity.getSerialNo());   
			//部门编号
			inOutStockEntity.setOrgCode(getOrgAdministrative().getCode());  
			//操作人名称
			inOutStockEntity.setOperatorName(FossUserContext.getCurrentUser().getEmployee().getEmpName());  
			//操作人工号
			inOutStockEntity.setOperatorCode(FossUserContext.getCurrentUser().getEmployee().getEmpCode()); 
			//入库类型
			inOutStockEntity.setInOutStockType(StockConstants.AIR_UNSHIPPED_GOODS_IN_STOCK_TYPE); 
			//入库
			stockService.inStockPC(inOutStockEntity);
			//拉货成功后删除运单流水号中的数据，恢复为可合票状态 
			deleteAirWaybillSerialNo(airUnshippedSerialNoEntity);
		}
	}
	
	/**
	 * 拉货入库
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-14 下午3:14:46
	 */
	@Override
	@Transactional
	public int unshippedGoodsInStock(AirUnshippedGoodsDto airUnshippedGoodsDto){
		InOutStockEntity inOutStockEntity = new InOutStockEntity();
		//待拉货集合
		List<AirUnshippedSerialNoEntity> airUnshippedSerialNoList = null;
		//如果当前拉货是正单拉货，则根据单号查询所有流水号和运单号入库；如果是代单号，则入库选择的流水号
		if(StringUtils.equals(airUnshippedGoodsDto.getBillNoType(), AirfreightConstants.AIR_WAY_BILL_NO)){
			//正单拉货
			//获取正单下所有未配载的运单号和流水号
			airUnshippedSerialNoList = airUnshippedGoodsDao.querySerialNoByAirWaybillNo(airUnshippedGoodsDto);   
		}else{
			//代单拉货
			//获取当前运单下的流水号集合
			airUnshippedSerialNoList = airUnshippedGoodsDto.getAirUnshippedSerialNoList();   
			//为集合添加运单号
			for(AirUnshippedSerialNoEntity airUnshippedSerialNoEntity : airUnshippedSerialNoList){
				airUnshippedSerialNoEntity.setWaybillNo(airUnshippedGoodsDto.getBillNo());
			}
		}
		Integer unshippedGoodsQty = Integer.parseInt(airUnshippedGoodsDto.getUnshippedGoodsQty());
		//调用入库方法
		unshippedGoods(inOutStockEntity,airUnshippedSerialNoList,unshippedGoodsQty);
		return 0;
	}

	/**
	 * 删除拉货记录
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-17 下午3:09:25
	 */
	@Override
	@Transactional
	public int deleteAirUnshippedGoods(AirUnshippedGoodsDto airUnshippedGoodsDto) {
		//如果没有接收到ID，则抛出异常
		if(StringUtils.isBlank(airUnshippedGoodsDto.getId())){
			throw new TfrBusinessException("异常错误，请重新查询再操作！","");
		}
		//根据ID删除拉货信息
		return airUnshippedGoodsDao.deleteAirUnshippedGoods(airUnshippedGoodsDto);
	}

	/**
	 * 根据ID查找拉货信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-19 9:57:19
	 */
	@Override
	@Transactional(readOnly = true)
	public AirUnshippedGoodsEntity queryAirUnshippedGoodsById(
			AirUnshippedGoodsDto airUnshippedGoodsDto) {
		//根据ID获取拉货拉货信息
		List<AirUnshippedGoodsEntity> airUnshippedGoodsList = airUnshippedGoodsDao.queryAirUnshippedGoodsById(airUnshippedGoodsDto);
		if(airUnshippedGoodsList.size() < 1 ){
			throw new TfrBusinessException("没有找到这条记录，请重新查询再操作","");
		}
		//如果是代单则需要加载流水号
		if(!StringUtils.equals(airUnshippedGoodsDto.getAirAssembleType(), AirfreightConstants.AIR_WAY_BILL_NO)){
			//已拉货的流水号
			List<AirUnshippedSerialNoEntity> unshippedSerialNoEntities = airUnshippedGoodsDao.querySerialNoByUnshippedGoodsId(airUnshippedGoodsDto);
			//当前代单下所有的流水号
			List<AirWaybillSerialNoEntity> airWaybillSerialNoList = airUnshippedGoodsDao.querySerialNoByWaybillNo(airUnshippedGoodsDto);
			//临时存放流水号
			AirWaybillSerialNoEntity airWaybillSerialNo = null;
			//用于存储经过过滤的流水号的map
			Map<String,AirWaybillSerialNoEntity> serialNos = new HashMap<String,AirWaybillSerialNoEntity>();
			//遍历两个list，将已经拉货的流水号标记不可反选
			//将已拉货的流水号标记不可反选然后存入map，只用于比较
			for(AirUnshippedSerialNoEntity airUnshippedSerialNoEntity :unshippedSerialNoEntities){
				airWaybillSerialNo = new AirWaybillSerialNoEntity();
				//标记为不可反选
				airWaybillSerialNo.setUndeselect(true);
				//标记默认选中
				airWaybillSerialNo.setDefaultselect(true);
				//流水号
				airWaybillSerialNo.setSerialNo(airUnshippedSerialNoEntity.getSerialNo());
				//放入map
				serialNos.put(airUnshippedSerialNoEntity.getSerialNo(), airWaybillSerialNo);
			}
			//将代单下的流水号存入map
			for(AirWaybillSerialNoEntity airWaybillSerialNoEntity : airWaybillSerialNoList){
				//和已经存入map中已拉货的流水号比较，如果存在了，则将流水号集合中的流水号号标记不可反选然后存入map
				if(serialNos.containsKey(airWaybillSerialNoEntity.getSerialNo())){
					//标记为不可反选
					airWaybillSerialNoEntity.setUndeselect(false);
					//标记默认选中
					airWaybillSerialNoEntity.setDefaultselect(true);
					//放入map
					serialNos.put(airWaybillSerialNoEntity.getSerialNo(), airWaybillSerialNoEntity);
				}else{
					//否则只存入流水号
					serialNos.put(airWaybillSerialNoEntity.getSerialNo(), airWaybillSerialNoEntity);
				}
			}
			//处理后的流水号集合
			List<AirWaybillSerialNoEntity> result = new ArrayList<AirWaybillSerialNoEntity>();
			result.addAll(serialNos.values());
			//存入dto返回给页面
			airUnshippedGoodsDto.setAirWaybillSerialNoList(result);
		}
		return airUnshippedGoodsList.get(0);
	}
	
	/**
	 * 拉货通知
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-27 上午11:34:51
	 */
	@Transactional
	public void airUnshippedGoodsNoise(AirUnshippedGoodsEntity airUnshippedGoodsEntity){
		InstationJobMsgEntity instationJobMsgEntity = new InstationJobMsgEntity();
		//发送方编码
		instationJobMsgEntity.setSenderCode(FossUserContext.getCurrentUser().getEmployee().getEmpCode()); 
		//发送方姓名
		instationJobMsgEntity.setSenderName(FossUserContext.getCurrentUser().getEmployee().getEmpName());   
		//发送方部门编码
		instationJobMsgEntity.setSenderOrgCode(getOrgAdministrative().getCode());   
		//发送方部门名称
		instationJobMsgEntity.setSenderOrgName(getOrgAdministrative().getName()); 
		//接收方类型
		instationJobMsgEntity.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
		//消息类型
		instationJobMsgEntity.setMsgType(DictionaryValueConstants.MSG_TYPE__NORMAL); 
		//发送时间
		instationJobMsgEntity.setPostDate(new Date());  
		//站内消息状态
		instationJobMsgEntity.setStatus(MessageConstants.MSG__STATUS__PROCESSING);  
		//消息内容
		String message = ""; 
		//如果是正单号，则遍历下面所有的运单，通知所有开单部门
		if(AirfreightConstants.AIR_WAY_BILL_NO.equals(airUnshippedGoodsEntity.getUnshippedType())){
			//查询所有已拉货的运单号 
			List<AirUnshippedSerialNoEntity> airUnshippedSerialNoList = airUnshippedGoodsDao.queryWaybillNoByAirWaybillNo(airUnshippedGoodsEntity.getBillNo());
			//如果size小于1，则抛出异常
			if(airUnshippedSerialNoList.size() < 1){
				throw new TfrBusinessException("该正单下未找到运单!","");
			}
			//遍历运单号集合，通知开单部门拉货
			for(AirUnshippedSerialNoEntity airUnshippedSerialNoEntity : airUnshippedSerialNoList){
				//运单号
				String waybillNo = airUnshippedSerialNoEntity.getWaybillNo();
				//拉货时间
				Date unshippedTime = airUnshippedGoodsEntity.getUnshippedTime();
				//拉货件数
				Integer unshippedGoodsQty = airUnshippedGoodsEntity.getUnshippedGoodsQty();
				//拉货重量
				BigDecimal unshippedWeight = airUnshippedGoodsEntity.getUnshippedWeight();
				//备注
				String notes = airUnshippedGoodsEntity.getNotes();
				//改配航班
				String reassignFlightNo = airUnshippedGoodsEntity.getReassignFlightNo(); 
				//航班号
				String flightNo = airUnshippedGoodsEntity.getFlightNo();
				//预计起飞时间
				String planLeveaTime = "";
				//预计到达时间
				String planArriveTime = "";
				//开单部门
				String createOrgCode = ""; 
				//根据运单查询开单部门
				createOrgCode = airUnshippedGoodsDao.queryCreateOrgCode(waybillNo);  
				//接收方部门
				instationJobMsgEntity.setReceiveOrgCode(createOrgCode);   
				//设置ID
				instationJobMsgEntity.setId(UUIDUtils.getUUID());
				//如果改配航班不为空
				if(!StringUtils.isBlank(reassignFlightNo)){
					FlightEntity flightEntity = new FlightEntity();
					flightEntity.setFlightNo(reassignFlightNo);
					//根据改配航班查询预计起飞时间和预计达到时间
					FlightEntity returnEntity = flightService.queryFlightBySelective(flightEntity);
					//没找到记录则抛出异常
					if(returnEntity == null){
						throw new TfrBusinessException("根据当前改配航班号未查询到相对应的航班信息","");
					}
					//设置预计起飞时间和预计达到时间
					planLeveaTime = DateUtils.convert(returnEntity.getPlanLeaveTime(),"HH:mm:ss");
					planArriveTime = DateUtils.convert(returnEntity.getPlanArriveTime(),"HH:mm:ss");
				}
				//拼接消息
				SmsParamDto smsParamDto = new SmsParamDto();
				Map<String,String> map = new HashMap<String,String>();
				//运单号
				map.put("waybillNo", waybillNo);
				//拉货时间
				map.put("unshippedTime", DateUtils.convert(unshippedTime, "yyyy-MM-dd hh:mm:ss"));
				//原航班
				map.put("flightNo", flightNo);
				//拉货件数
				map.put("unshippedGoodsQty", unshippedGoodsQty.toString());
				//拉货重量
				map.put("unshippedWeight", unshippedWeight.toString());
				//拉货原因
				map.put("notes", notes);
				//改配航班
				map.put("reassignFlightNo", reassignFlightNo);
				//预飞时间
				map.put("planLeveaTime", planLeveaTime);
				//落地时间
				map.put("planArriveTime", planArriveTime);
				//给smsParamDto赋值
				smsParamDto.setMap(map);
				smsParamDto.setSmsCode(AirfreightConstants.TFR_UNSHIPPED_GOODS_NOTICE);
				//获取message
				message = sMSTempleteService.querySmsByParam(smsParamDto);
//				message = "【拉货】 运单号："+waybillNo+" 拉货时间："+DateUtils.convert(unshippedTime, "yyyy-MM-dd hh:mm:ss")+" 原航班："+flightNo+"， 拉货件数："+unshippedGoodsQty+
//						" 拉货重量："+unshippedWeight.toString()+"，拉货原因："+notes+"；改配航班："+reassignFlightNo+"，预飞时间："+planLeveaTime+"，落地时间："+planArriveTime+"。";
				instationJobMsgEntity.setContext(message);
				try{
					//调用通知接口发送通知
					messageService.createBatchInstationMsg(instationJobMsgEntity);
				}catch(BusinessException e){
					//打印异常日志
					LOGGER.error(e.getMessage());
					throw new TfrBusinessException(e.getErrorCode(),"");
				}
			}
		}else{
			//运单号
			String waybillNo = airUnshippedGoodsEntity.getBillNo();
			//拉货时间
			Date unshippedTime = airUnshippedGoodsEntity.getUnshippedTime();
			//拉货件数
			Integer unshippedGoodsQty = airUnshippedGoodsEntity.getUnshippedGoodsQty();
			//拉货重量
			BigDecimal unshippedWeight = airUnshippedGoodsEntity.getUnshippedWeight();
			//备注
			String notes = airUnshippedGoodsEntity.getNotes();
			//改配航班
			String reassignFlightNo = airUnshippedGoodsEntity.getReassignFlightNo();
			//航班号
			String flightNo = airUnshippedGoodsEntity.getFlightNo();
			//预计起飞时间
			String planLeveaTime = "";
			//预计到达时间
			String planArriveTime = "";
			//开单部门
			instationJobMsgEntity.setReceiveOrgCode(airUnshippedGoodsDao.queryCreateOrgCode(waybillNo));
			//设置ID
			instationJobMsgEntity.setId(UUIDUtils.getUUID());
			//如果改配航班不为空
			if(!StringUtils.isBlank(reassignFlightNo)){
				FlightEntity flightEntity = new FlightEntity();
				flightEntity.setFlightNo(reassignFlightNo);
				//根据改配航班查询预计起飞时间和预计达到时间
				FlightEntity returnEntity = flightService.queryFlightBySelective(flightEntity);
				if(returnEntity == null){
					//没找到记录则抛出异常
					throw new TfrBusinessException("根据当前改配航班号未查询到相对应的航班信息","");
				}
				//设置预计起飞时间和预计达到时间
				planLeveaTime = DateUtils.convert(returnEntity.getPlanLeaveTime(),"HH:mm:ss");
				planArriveTime = DateUtils.convert(returnEntity.getPlanArriveTime(),"HH:mm:ss");
			}
			//拼接消息
			SmsParamDto smsParamDto = new SmsParamDto();
			Map<String,String> map = new HashMap<String,String>();
			//运单号
			map.put("waybillNo", waybillNo);
			//拉货时间
			map.put("unshippedTime", DateUtils.convert(unshippedTime, "yyyy-MM-dd hh:mm:ss"));
			//原航班
			map.put("flightNo", flightNo);
			//拉货件数
			map.put("unshippedGoodsQty", unshippedGoodsQty.toString());
			//拉货重量
			map.put("unshippedWeight", unshippedWeight.toString());
			//拉货原因
			map.put("notes", notes);
			//改配航班
			map.put("reassignFlightNo", reassignFlightNo);
			//预飞时间
			map.put("planLeveaTime", planLeveaTime);
			//落地时间
			map.put("planArriveTime", planArriveTime);
			//给smsParamDto赋值
			smsParamDto.setMap(map);
			smsParamDto.setSmsCode(AirfreightConstants.TFR_UNSHIPPED_GOODS_NOTICE);
			//获取message
			message = sMSTempleteService.querySmsByParam(smsParamDto);
//			message = "【拉货】 运单号："+waybillNo+" 拉货时间："+DateUtils.convert(unshippedTime, "yyyy-MM-dd hh:mm:ss")+" 原航班："+flightNo+"， 拉货件数："+unshippedGoodsQty+
//					" 拉货重量："+unshippedWeight.toString()+"，拉货原因："+notes+"；改配航班："+reassignFlightNo+"，预飞时间："+planLeveaTime+"，落地时间："+planArriveTime+"。";
			instationJobMsgEntity.setContext(message);
			try{
				//调用通知接口发送通知
				messageService.createBatchInstationMsg(instationJobMsgEntity);
			}catch(BusinessException e){
				//打印异常日志
				LOGGER.error(e.getMessage());
				throw new TfrBusinessException(e.getErrorCode(),"");
			}
		}
		//增加或修改通知日志
		String unshippedGoodsId = airUnshippedGoodsEntity.getId();
		//根据unshippedGoodsId查询通知日志ID
		String id = airUnshippedGoodsDao.queryNoticId(unshippedGoodsId);
		AirUnshippedNoticeEntity airUnshippedNoticeEntity = new AirUnshippedNoticeEntity();
		//为通知日志实体赋值
		//通知人code
		airUnshippedNoticeEntity.setCreatedUserCode(FossUserContext.getCurrentUser().getEmployee().getEmpCode());  
		//通知人名称
		airUnshippedNoticeEntity.setCreatedUserName(FossUserContext.getCurrentUser().getEmployee().getEmpName()); 
		//通知部门code
		airUnshippedNoticeEntity.setCreateOrgCode(getOrgAdministrative().getCode());  
		//通知部门name
		airUnshippedNoticeEntity.setCreateOrgName(getOrgAdministrative().getName()); 
		//通知信息
		airUnshippedNoticeEntity.setMessage(message);
		//通知时间
		airUnshippedNoticeEntity.setNoticeTime(new Date());   
		//如果当前拉货信息已经发送过通知，则更新之前的通知，否则新增
		try{
			if(StringUtils.isBlank(id)){
				airUnshippedNoticeEntity.setId(UUIDUtils.getUUID());
				airUnshippedNoticeEntity.setUnshippedGoodsId(unshippedGoodsId);
				airUnshippedGoodsDao.addNotice(airUnshippedNoticeEntity);
			}else{
				airUnshippedNoticeEntity.setId(id);
				airUnshippedGoodsDao.updateNotice(airUnshippedNoticeEntity);
			}
		}catch(BusinessException e){
			LOGGER.error(e.getMessage());
			throw new TfrBusinessException("发送通知出错!","");
		}
	}

	/**
	 * 起草拉货通知
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-27 下午2:25:21
	 */
	@Override
	public void addUnshippedMessage(AirUnshippedGoodsDto airUnshippedGoodsDto) {
		//根据ID查找拉货信息
		AirUnshippedGoodsEntity airUnshippedGoodsEntity = queryAirUnshippedGoodsById(airUnshippedGoodsDto);
		//发送通知
		airUnshippedGoodsNoise(airUnshippedGoodsEntity);
	}
	
	/**
	 * 拉货成功后删除运单流水号中的数据，恢复为可合票状态 
	 * @author foss-liuxue(for IBM)
	 * @date 2013-3-13 下午2:31:46
	 */
	public int deleteAirWaybillSerialNo(AirUnshippedSerialNoEntity airUnshippedNoticeEntity){
		return airUnshippedGoodsDao.deleteAirWaybillSerialNo(airUnshippedNoticeEntity);
	}
	
	/**
	 * 获取上级空运总调组织
	 */
	public OrgAdministrativeInfoEntity getOrgAdministrative(){
		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=airDispatchUtilService.queryAirDispatchDept(deptCode);
		return orgAdministrativeInfoEntity;
	}
	
}