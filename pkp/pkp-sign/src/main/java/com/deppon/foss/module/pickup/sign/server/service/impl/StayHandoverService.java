/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/service/impl/StayHandoverService.java
 * 
 * FILE NAME        	: StayHandoverService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverService;
import com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverserialService;
import com.deppon.foss.module.pickup.sign.api.shared.define.DeliverHandlerConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverserialEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.PdaDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StayHandoverDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/***
 * 交接service实现
 * @author foss-meiying
 * @date 2012-11-28 上午11:56:12
 * @since
 * @version
 */
public class StayHandoverService  implements IStayHandoverService {
	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(StayHandoverService.class);
	/**
	 * 交接dao
	 */
	private IStayHandoverDao stayHandoverDao;
	/**
	 * 交接明细service
	 */
	private IStayHandoverDetailService stayHandoverDetailService;
	/**
	 * 交接流水号service
	 */
	private IStayHandoverserialService stayHandoverserialService;
	
	/**
	 * 添加交接表
	 * @author foss-meiying
	 * @date 2012-12-24 上午9:32:42
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverService#addStayHandover(com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverEntity)
	 */
	@Override
	@Transactional
	public StayHandoverEntity addStayHandover(StayHandoverEntity entity) {
		return stayHandoverDao.addStayHandover(entity);
	}
	/**
	 * 删除交接信息
	 * @author foss-meiying
	 * @date 2012-12-24 上午9:32:34
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverService#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	@Transactional
	public int deleteByPrimaryKey(String id) {
		return stayHandoverDao.deleteByPrimaryKey(id);
	}
	
	/**
	 * 通过id查询交接信息
	 * @author foss-meiying
	 * @date 2012-12-24 上午9:32:23
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverService#queryByPrimaryKey(java.lang.String)
	 */
	@Override
	public StayHandoverEntity queryByPrimaryKey(String id) {
		return stayHandoverDao.queryByPrimaryKey(id);
	}
	
	/**
	 * 通过相应参数修改交接信息
	 * @author foss-meiying
	 * @date 2012-12-24 上午9:32:11
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverService#updateByPrimaryKeySelective(com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverEntity)
	 */
	@Override
	@Transactional
	public int updateByPrimaryKeySelective(StayHandoverEntity record) {
		return stayHandoverDao.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 
	 *  根据主键修改交接信息
	 * @author foss-meiying
	 * @date 2012-12-24 上午9:32:00
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverService#updateByPrimaryKey(com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverEntity)
	 */
	@Override
	@Transactional
	public int updateByPrimaryKey(StayHandoverEntity record) {
		return stayHandoverDao.updateByPrimaryKey(record);
	}

	
	/**
	 * 
	 * 得到接货信息
	 * @author foss-meiying
	 * @date 2012-12-24 上午9:31:49
	 * @return 
	 */
	@Override
	public StayHandoverEntity queryPickUpStayHandOverInfo(String driverCode) {
		LOGGER.info("根据司机工号查询运单待处理信息开始");
		//自动生成交接id
		String stayHandlerId = UUIDUtils.getUUID();
		//得到总件数，总重量，总体积
		StayHandoverEntity entity = stayHandoverDao.querySumGoodsInfoByParams(driverCode);
		if(entity == null){
			LOGGER.error("根据司机工号查询运单待处理信息为空");
			return null;
		}
		StayHandoverDto dto = new StayHandoverDto();
		dto.setDriverCode(driverCode);
		//运输性质为精准卡航
		dto.setProductCode(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);
		//未生成交接
		dto.setHandoverStatus(FossConstants.NO);
		//得到卡货票数
		Integer fastWaybillQty = stayHandoverDao.queryPickupFastWaybillQtyCount(dto);
		//卡货票数
		entity.setFastWaybillQty(fastWaybillQty);
		//交接类型为接货
		entity.setHandoverType(DeliverHandlerConstants.HANDOVER_TYPE_RECEIVE);
		//交接id
		entity.setId(stayHandlerId);
		return entity;
	}
	
	/**
	 * 
	 * 得到接货信息-零担完成接货任务，清空时间优化
	 * @author foss-231438 借鉴上一方法
	 * @date 2015-07-13 上午9:31:49
	 * @return 
	 */
	@Override
	public StayHandoverEntity queryPickUpStayHandOverInfo(String driverCode,boolean isDriver) {
		LOGGER.info("根据司机工号查询运单待处理信息开始");
		//自动生成交接id
		String stayHandlerId = UUIDUtils.getUUID();
		StayHandoverDto param = new StayHandoverDto();
			param.setDriverCode(driverCode);//司机工号
			param.setHandoverStatus(FossConstants.NO); //未生成交接单
			Double result = getStartTime();
			if(isDriver){ //零担的运单判断是否在0-2点提交接货任务
				if(result>=0 && result<=2){ //0~2点之间提交,接货任务清零时间优化
					param.setIsExpandTime("Y");
				}
			}
			//得到总件数，总重量，总体积
			StayHandoverEntity entity = stayHandoverDao.querySumGoodsInfoByParams(param);
		if(entity == null){
			LOGGER.error("根据司机工号查询运单待处理信息为空");
			return null;
		}
		StayHandoverDto dto = new StayHandoverDto();
		dto.setDriverCode(driverCode);
		//运输性质为精准卡航
		dto.setProductCode(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);
		//未生成交接
		dto.setHandoverStatus(FossConstants.NO);
		//得到卡货票数
		Integer fastWaybillQty = stayHandoverDao.queryPickupFastWaybillQtyCount(dto);
		//卡货票数
		entity.setFastWaybillQty(fastWaybillQty);
		//交接类型为接货
		entity.setHandoverType(DeliverHandlerConstants.HANDOVER_TYPE_RECEIVE);
		//交接id
		entity.setId(stayHandlerId);
		return entity; 
	}
	
	/**
	 * 根据车号得到接货信息-零担完成接货任务，清空时间优化
	 * add by 329757
	 * 2016-6-17
	 */
	@Override
	public StayHandoverEntity queryPickUpStayHandOverInfoByVehicleNo(
			String vehicleNo, boolean isDriver) {
		LOGGER.info("根据车牌号查询运单待处理信息开始");
		//自动生成交接id
		String stayHandlerId = UUIDUtils.getUUID();
		StayHandoverDto param = new StayHandoverDto();
			param.setVehicleNo(vehicleNo);//车牌号
			param.setHandoverStatus(FossConstants.NO); //未生成交接单
			Double result = getStartTime();
			if(isDriver){ //零担的运单判断是否在0-2点提交接货任务
				if(result>=0 && result<=2){ //0~2点之间提交,接货任务清零时间优化
					param.setIsExpandTime("Y");
				}
			}
			//得到总件数，总重量，总体积
			StayHandoverEntity entity = stayHandoverDao.querySumGoodsInfoByVo(param);
		if(entity == null){
			LOGGER.error("根据车牌号查询运单待处理信息为空");
			return null;
		}
		StayHandoverDto dto = new StayHandoverDto();
		dto.setVehicleNo(vehicleNo);
		//运输性质为精准卡航
		dto.setProductCode(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);
		//未生成交接
		dto.setHandoverStatus(FossConstants.NO);
		//得到卡货票数
		Integer fastWaybillQty = stayHandoverDao.queryPickupFastWaybillQtyCountByVo(dto);
		//卡货票数
		entity.setFastWaybillQty(fastWaybillQty);
		//交接类型为接货
		entity.setHandoverType(DeliverHandlerConstants.HANDOVER_TYPE_RECEIVE);
		//交接id
		entity.setId(stayHandlerId);
		return entity;
	}
	
	
	/**
	 * @author 231438
	 * @return Long
	 * 获取当前操作时间与当天的0点0分0秒之间的差值
	 */
	private Double getStartTime(){  
        Calendar todayStart = Calendar.getInstance();  
        todayStart.set(Calendar.HOUR_OF_DAY, 0);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);  
        Long date = new Date().getTime(); //当前时间，转化为Long值
        //todayStart.getTime().getTime()当天的0点0分0秒的Long值
        return (date-todayStart.getTime().getTime())/1000/3600.00;  
    }  
	
	/**
	 * 在补录PDA运单保存/提交时，若减少了件数，则执行以下操作：
    *  2.2 将删除的PDA货件从中转卸车交接单货件明细表(T_SRV_STAY_HANDOVERSERIAL)中删除。
   	*  2.3 更新中转卸车交接单明细(T_SRV_STAY_HANDOVERDETAIL)的开单件数(GODDS_QTY),减去件数。
   	*  2.4 更新中转卸车交接单表(T_SRV_STAY_HANDOVER)的件数(GOODS_QTY_TOTAL)，减去件数。
	 * @author foss-meiying
	 * @date 2013-3-17 下午4:18:23
	 * @param dto 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverService
	 * #makeupPdaReduceGoodsQty(com.deppon.foss.module.pickup.sign.api.shared.dto.PdaDto)
	 */
	@Override
	public void makeupPdaReduceGoodsQty(PdaDto dto) {
		LOGGER.info("--补录PDA运单，件数减少操作开始" +ReflectionToStringBuilder.toString(dto));
		if(dto == null){//如果传入的对象为空
			throw new SignException(SignException.OBJECT_IS_NULL);
		}
		StayHandoverDetailEntity entity =stayHandoverDetailService.queryByWaybillNo(dto.getWaybillNo());
		if(entity != null){//如果查询交接明细不为空
			dto.setStayHandoverDetailId(entity.getId());//交接明细id
			stayHandoverserialService.deleteByStayHandoverIdAndSerialNos(dto);//删除的PDA货件
			LOGGER.info("--删除交接流水号表中的当前传入的流水号完成");
			entity.setGoodsQty(entity.getGoodsQty()-dto.getGoodsQty());//减去件数
			if(StringUtil.isNotBlank(dto.getNewWaybillNo())){
				entity.setWaybillNo(dto.getNewWaybillNo());//新的运单号
			}
			stayHandoverDetailService.updateByPrimaryKeySelective(entity);
			LOGGER.info("--修改交接明细完成");
			StayHandoverEntity handoverEntity = new StayHandoverEntity();
			handoverEntity.setId(entity.gettSrvStayHandoverId());//交接id
			handoverEntity.setGoodsQtyTotal(dto.getGoodsQty());//传入的件数
			stayHandoverDao.updateGoodsQtyTotalReduceById(handoverEntity);//执行修改操作
			LOGGER.info("--修改交接表的货物总件数完成");
		}else {//交接明细为空
			throw new SignException(SignException.QUERY_STAYHANDOVER_DETAIL_IS_NULL);//抛出异常  根据运单号查询交接明细为空
		}
		LOGGER.info("--补录PDA运单，件数减少操作完成");
	}
	/**
	 * 在补录PDA运单保存/提交时，若增加了件数，则执行以下操作
	 *   1.2 将新增的PDA货件插入中转卸车交接单货件明细表(T_SRV_STAY_HANDOVERSERIAL)中。
   	 *	 1.3 更新中转卸车交接单明细(T_SRV_STAY_HANDOVERDETAIL)的开单件数(GODDS_QTY),累加上增加的件数。
   	 *	 1.4 更新中转卸车交接单表(T_SRV_STAY_HANDOVER)的件数(GOODS_QTY_TOTAL)，累加上增加的件数
	 * @author foss-meiying
	 * @date 2013-3-17 下午4:18:09
	 * @param dto 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverService
	 * #makeupPdaAddgoodsQty(com.deppon.foss.module.pickup.sign.api.shared.dto.PdaDto)
	 */
	@Override
	public void makeupPdaAddgoodsQty(PdaDto dto) {
		LOGGER.info("--补录PDA运单，件数增加操作开始"+ReflectionToStringBuilder.toString(dto));
		if(dto == null){//如果传入的对象为空
			throw new SignException(SignException.OBJECT_IS_NULL);
		}
		StayHandoverDetailEntity entity =stayHandoverDetailService.queryByWaybillNo(dto.getWaybillNo());
		if(entity != null){//如果查询交接明细不为空
			for(String serialNo : dto.getSerialNos()){
				StayHandoverserialEntity serialEntity = new StayHandoverserialEntity();
				serialEntity.setSerailno(serialNo);//流水号
				serialEntity.settSrvStayHandoverdetailId(entity.getId());//交接明细id
				stayHandoverserialService.addSelective(serialEntity);//添加交接流水号集合
			}
			LOGGER.info("--批量添加交接流水号集合");
			entity.setGoodsQty(entity.getGoodsQty()+dto.getGoodsQty());//累加上增加的件数
			if(StringUtil.isNotBlank(dto.getNewWaybillNo())){
				entity.setWaybillNo(dto.getNewWaybillNo());//新的运单号
			}
			stayHandoverDetailService.updateByPrimaryKeySelective(entity);
			StayHandoverEntity handoverEntity = new StayHandoverEntity();
			handoverEntity.setId(entity.gettSrvStayHandoverId());//交接id
			handoverEntity.setGoodsQtyTotal(0 - dto.getGoodsQty());//使传入的件数为负数
			stayHandoverDao.updateGoodsQtyTotalReduceById(handoverEntity);//执行修改操作
			LOGGER.info("--修改交接表的货物总件数完成");
		}else {//交接明细为空
			throw new SignException(SignException.QUERY_STAYHANDOVER_DETAIL_IS_NULL);//抛出异常  根据运单号查询交接明细为空
		}
		LOGGER.info("--补录PDA运单，件数增加操作开始");
	}
	/**
	 * Sets the 交接明细service.
	 *
	 * @param stayHandoverDetailService the new 交接明细service
	 */
	public void setStayHandoverDetailService(IStayHandoverDetailService stayHandoverDetailService) {
		this.stayHandoverDetailService = stayHandoverDetailService;
	}
	
	/**
	 * Sets the 交接流水号service.
	 *
	 * @param stayHandoverserialService the new 交接流水号service
	 */
	public void setStayHandoverserialService(IStayHandoverserialService stayHandoverserialService) {
		this.stayHandoverserialService = stayHandoverserialService;
	}
	/**
	 * Gets the 交接dao.
	 *
	 * @return the 交接dao
	 */
	public IStayHandoverDao getStayHandoverDao() {
		return stayHandoverDao;
	}
	
	/**
	 * Sets the 交接dao.
	 *
	 * @param stayHandoverDao the new 交接dao
	 */
	public void setStayHandoverDao(IStayHandoverDao stayHandoverDao) {
		this.stayHandoverDao = stayHandoverDao;
	}
	
}