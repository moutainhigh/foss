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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/PdaScanToFossService.java
 * 
 * FILE NAME        	: PdaScanToFossService.java
 * 
 * AUTHOR			: 菜鸟项目--电子运单
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.server.service.impl
 * FILE    NAME: PdaScanToFossService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.message.IMessageBundle;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPdaScanDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IEWaybillProcessService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodPDAService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPdaScanService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.server.utils.StringHandlerUtil;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.EWaybillProcessEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.EcomWaybillRelateEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodPDAEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PdaScanEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaScanQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.PdaInterfaceException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillStoreException;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * PDA扫描信息处理相关服务接口实现
 * @author 200972  lanhuilan
 * @date 2015-01=30 下午8:58:38
 */
public class PdaScanService implements IPdaScanService {
	// 日志信息
	public static final Logger LOGGER = LoggerFactory.getLogger(PdaScanService.class);
	
	/**
	 * PDA盲扫DAO
	 */
	private IPdaScanDao pdaScanDao;
	
	/**
	 * 电子运单激活Dao
	 */
	private IEWaybillProcessService eWaybillProcessService;

	/**
	 * 快递车辆与营业部关系服务类
	 */
	private IExpressVehiclesService expressVehiclesService;
	
	/**
	 * 人员 Service
	 */
	private IEmployeeService employeeService;
	
	/**
	 * 快递点部营业部映射关系Service
	 */
	private IExpressPartSalesDeptService expressPartSalesDeptService;
	
	/**
	 * 库存服务类
	 */
	private IStockService stockService;
	
	private ILabeledGoodDao labeledGoodDao;

	/**
	 * 国际化信息
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-11-30 上午8:59:57
	 */
	private IMessageBundle messageBundle;
	
	/**
	 * PDA货签信息服务类
	 */
	private ILabeledGoodPDAService labeledGoodPDAService;
											
	private IWaybillRelateDetailEntityService waybillRelateDetailEntityService;
	
	/**
	 * 保存PDA扫描信息
	 * @author 200972  lanhuilan
	 * @date 2015-01=31 上午8:58:38
	 */
	@Override
	public void savePdaScanInfo(PdaScanQueryDto pdaScanInfo) {
		// TODO Auto-generated method stub
		LOGGER.info("-----------进入PDA扫描信息保存方法----------------");
		LOGGER.info("-----------对传入的参数进行校验----------------");
//		对传入的参数进行基本的校验
//		pdaScanInfo 不能为空
		if(pdaScanInfo == null){
			throw new PdaInterfaceException("pda扫描信息为空！");
		}
//		运单号不能为空
		if(StringUtils.isEmpty(pdaScanInfo.getWaybillNo())){
			throw new BusinessException("运单号为空");
		}
//		流水号不能为空
		if(StringUtils.isEmpty(pdaScanInfo.getSerialNo())){
			throw new BusinessException("流水号为空");
		}
//		司机（快递员）工号不能为空
		if(StringUtils.isEmpty(pdaScanInfo.getDriverCode())){
			throw new BusinessException("司机（快递员）工号为空");
		}
//		扫描时间不能为空
		if(pdaScanInfo.getScanTime() == null){
			throw new BusinessException("Pda扫描时间为空");
		}
//		扫描类型不能为空
		if(StringUtils.isEmpty(pdaScanInfo.getScanType())){
			throw new BusinessException("Pda扫描类型为空");
		}
//		扫描任务Id不能为空
		if(StringUtils.isEmpty(pdaScanInfo.getTaskId())){
			throw new BusinessException("Pda扫描任务ID为空");
		}
		LOGGER.info("-----------参数校验完毕----------------");
		
		/*在这里对传输过来的数据校验完毕，假设所有传过来的数据都是正常的*/
		LOGGER.info("-----------封装待保存的PDA信息实体----------------");
//		定义待保存的Pda扫描信息实体
		PdaScanEntity pdaScanEntity = new PdaScanEntity();
//		将传入的参数封装为Pda扫描信息实体
		pdaScanEntity.setId(UUIDUtils.getUUID()); 
//		设置运单号
		pdaScanEntity.setWaybillNo(pdaScanInfo.getWaybillNo());
//		设置订单号	
		pdaScanEntity.setOrderNo(pdaScanInfo.getOrderNo());
//		设置流水号	
		pdaScanEntity.setSerialNo(pdaScanInfo.getSerialNo());		
//		设置司机（快递员）工号
		pdaScanEntity.setDriverCode(pdaScanInfo.getDriverCode());
//		设置运单类型	
		if(StringUtils.isEmpty(pdaScanInfo.getWaybillType())){
			pdaScanEntity.setWaybillType(WaybillConstants.EWAYBILL);
		}
//		设置重量	
		pdaScanEntity.setWeight(pdaScanInfo.getGoodsWeight());
//		设置体积
		pdaScanEntity.setVolume(pdaScanInfo.getGoodsVolume());
//		设置扫描时间
		pdaScanEntity.setScanTime(pdaScanInfo.getScanTime());
//		设置是否有效
		pdaScanEntity.setActive(FossConstants.ACTIVE);
//		设置扫描类型
		pdaScanEntity.setScanType(pdaScanInfo.getScanType());
//		设置任务类型
		pdaScanEntity.setTaskType(pdaScanInfo.getTaskType());
//		设置任务Id
		pdaScanEntity.setTaskId(pdaScanInfo.getTaskId());
//		设置是否完成
		pdaScanEntity.setWhetherComplete(FossConstants.INACTIVE);
		//设置理货员
		pdaScanEntity.setClerkCode(pdaScanInfo.getClerkCode());
		//设置当前部门
		pdaScanEntity.setOperateOrgCode(pdaScanInfo.getOperateOrgCode());
		//开单部门
		//pdaScanEntity.setCreateOrgCode("174688");
		//收货部门
		pdaScanEntity.setReceiveOrgCode(pdaScanInfo.getReceiveOrgCode());
		LOGGER.info("-----------信息封装完毕----------------");
		
		LOGGER.info("-----------查询运单号为："+pdaScanEntity.getWaybillNo()+"流水号为："+pdaScanEntity.getSerialNo()+"，当前为有效状态的扫描信息----------------");
//		定义PDA扫描数据查询条件实体
		PdaScanQueryDto pdaScanDto = new  PdaScanQueryDto();
//		封装查询条件
//		是否有效状态为有效
		pdaScanDto.setActive(FossConstants.ACTIVE);
//		指定运单号为参数传入的运单号
		pdaScanDto.setWaybillNo(pdaScanInfo.getWaybillNo());
//		指定流水号为参数传入的流水号
		pdaScanDto.setSerialNo(pdaScanInfo.getSerialNo());
//		任务ID
		//pdaScanDto.setTaskId(pdaScanInfo.getTaskId());
//		任务类型，PICKUP为收货任务
		//pdaScanDto.setTaskType(pdaScanInfo.getTaskType());
//		快递员工号
//		pdaScanDto.setDriverCode(pdaScanInfo.getDriverCode());
//		扫描类型，SCAN为正扫，CANCEL为反扫
		//pdaScanDto.setScanType(pdaScanInfo.getScanType());
//		查找指定运单号、流水号的有效扫描数据
		List<PdaScanEntity> list = pdaScanDao.queryScanInforBySecondCondition(pdaScanDto);
		LOGGER.info("-----------查询结束----------------");
		
		LOGGER.info("-----------根据查询结果进行数据插入处理----------------");
		if(CollectionUtils.isEmpty(list)){
//			如果不存在指定运单号、流水号的有效信息，则插入一条新的扫描数据
			LOGGER.info("-----------不存在运单号为："+pdaScanEntity.getWaybillNo()+"流水号为："+pdaScanEntity.getSerialNo()+"的有效扫描数据，直接插入----------------");
			pdaScanDao.insertPdaScanInfo(pdaScanEntity);
			
		}else{
			List<PdaScanEntity> pdaScanEntityCANCELList = new ArrayList<PdaScanEntity>();
//			循环查询出的所有有效数据
			boolean isScan = false;
			boolean isCANCEL= false;
			for(PdaScanEntity it:list){
				//如果当前数据已经是完成接货状态，插入无效数据
				if(it.getScanType().equals(WaybillConstants.SCAN_TYPE_SACN)
						&&it.getActive().equals(FossConstants.ACTIVE)
						&&FossConstants.YES.equals(it.getWhetherComplete())){
					LOGGER.info("-----------已存在有效性为Y，扫描类型为正扫、是否完成接货为是的数据，插入无效数据----------------");
					pdaScanEntity.setActive(FossConstants.INACTIVE);
					pdaScanDao.insertPdaScanInfo(pdaScanEntity);
					//已完成任务，不插入新的有效数据
					isScan = false;
					break;
				}
				//把反扫数据添加到反扫数据集合
				if (WaybillConstants.SCAN_TYPE_CANCEL.equals(it.getScanType())) {
					pdaScanEntityCANCELList.add(it);
				}
				//判断是否是正扫，
				if(!isScan&&pdaScanEntity.getScanType().equals(WaybillConstants.SCAN_TYPE_SACN)){
						isScan = true;
				}
				if(!isScan){
					if (it.getScanType().equals(WaybillConstants.SCAN_TYPE_SACN)
							&&it.getTaskId().equals(pdaScanEntity.getTaskId())
							&&it.getScanTime().before(pdaScanEntity.getScanTime())) {
					//不是正扫，这里是反扫，就要找到正扫的那个任务id，任务id和反扫的任务id一致
						LOGGER.info("-----------反扫数据，失效当前有效数据，插入新的数据----------------");
						it.setActive(FossConstants.INACTIVE);
						pdaScanDao.updatePdaScanInfoById(it);
						pdaScanDao.insertPdaScanInfo(pdaScanEntity);
						//反扫，不插入数据
						isScan = false;
						isCANCEL = false;
						break;
					}
					isCANCEL = true;
				}
			}
			//反扫数据，并且没有找到正扫数据的任务号，先把反扫数据插入进数据库
			if (isCANCEL) {
				LOGGER.info("-----------反扫数据，插入反扫数据----------------");
				pdaScanDao.insertPdaScanInfo(pdaScanEntity);
			}
			if(isScan){
				boolean isNewPdaScanEntity = true;
				if (CollectionUtils.isNotEmpty(pdaScanEntityCANCELList)) {
					//循环所有反扫数据查询出是否有已经先存入的反扫数据
					for (PdaScanEntity pdaScanEntityCANCEL : pdaScanEntityCANCELList) {
						//如果查询到已经有反扫数据存在，把当前数据置为无效，插入无效数据
						if (pdaScanEntityCANCEL.getTaskId().equals(pdaScanEntity.getTaskId())
								&&pdaScanEntity.getScanTime().before(pdaScanEntityCANCEL.getScanTime())) {
							LOGGER.info("-----------已经存在反扫数据，把当前数据置为无效数据----------------");
							pdaScanEntity.setActive(FossConstants.INACTIVE);
							pdaScanDao.insertPdaScanInfo(pdaScanEntity);
							isNewPdaScanEntity = false;
							break;
						}
					}
				}
				//插入新的有效扫描数据
				if (isNewPdaScanEntity) {
					LOGGER.info("-----------正扫数据，插入一条有效数据----------------");
					pdaScanDao.insertPdaScanInfo(pdaScanEntity);
				}
			}
		}
		LOGGER.info("-----------数据插入操作结束----------------");
	}
	
	/**
	 * 完成接货并激活运单
	 * @author 200972 lanhuilan 
	 * @date 2015-02-04  下午10:02:53
	 */
	@Override
	public void overPickup(String taskId) {
		LOGGER.info("-----------参数校验----------------");
//		扫描任务Id不能为空
		if(StringUtils.isEmpty(taskId)){
			throw new BusinessException("Pda扫描任务ID为空");
		}
		LOGGER.info("-----------参数校验结束----------------");
		
		LOGGER.info("-----------进行完成接货状态修改----------------");
//		定义保存更新参数的Pda扫描信息实体
		PdaScanEntity pdaScanEntity = new PdaScanEntity();
		pdaScanEntity.setTaskId(taskId);
		pdaScanEntity.setActive(FossConstants.YES);
		pdaScanEntity.setWhetherComplete(FossConstants.YES);
		
		pdaScanDao.updatePdaScanInfoByTaskId(pdaScanEntity);
		LOGGER.info("-----------完成接货状态修改结束----------------");
		
//		循环调用激活方法
		LOGGER.info("-----------开始激活操作----------------");
//		定义PDA扫描数据查询条件实体
		LOGGER.info("-----------查询可激活数据，任务Id:"+taskId+"----------------");		
		PdaScanQueryDto pdaScanDto = new  PdaScanQueryDto();
//		封装查询条件
//		是否有效状态为有效
		pdaScanDto.setActive(FossConstants.ACTIVE);
//		指定扫描类型为正扫
		pdaScanDto.setScanType("SCAN");
//		指定任务号
		pdaScanDto.setTaskId(taskId);
//	         根据指定条件查询不重复的运单号
		List<PdaScanEntity> list =pdaScanDao.queryWaybillNoByCondition(pdaScanDto);
		LOGGER.info("---------------------------"+list);
		LOGGER.info("-----------遍历查询结果----------------");
		if(CollectionUtils.isEmpty(list)){
			return;
		}
		boolean status = false;
		for(PdaScanEntity it :list){
			//如果是子母件就不插入数据
			Map<String, Object> params=new java.util.HashMap<String, Object>();
			params.put("active", "Y");
			params.put("waybillNo", it.getWaybillNo());
			List<EcomWaybillRelateEntity> ecomWaybillRelateEntityList =waybillRelateDetailEntityService.queryAllEwaybillRelateByEcomWaybillRelateWayBillNo(params);
			if(ecomWaybillRelateEntityList.size()==0){
			EWaybillProcessEntity entity = new EWaybillProcessEntity();
			entity.setWaybillNo(it.getWaybillNo());
			eWaybillProcessService.insert(entity);
			}
			//是正扫并且是已完成接货
			if (it.getActive().equals(FossConstants.ACTIVE)&&
					it.getWhetherComplete().equals(WaybillConstants.YES)) {
				//根据运单号设置查询未完成接货的所有的正扫，有效数据
				pdaScanDto.setWhetherComplete(WaybillConstants.NO);
				pdaScanDto.setWaybillNo(it.getWaybillNo());
				pdaScanDto.setTaskId(null);
				status = true;
			}
			
			LOGGER.info("-----------遍历查询结果，运单号:"+it.getWaybillNo()+"----------------");
		}
		//当判断成立，已经有激活定单，
		if (status) {
			//查询出所有的没人完成任务，为正扫的，有效的，此运单号下的数据
			List<PdaScanEntity> whetherCompleteisN = pdaScanDao.queryScanInforBySecondCondition(pdaScanDto);
			if (CollectionUtils.isNotEmpty(whetherCompleteisN)) {
				//判断成立，循环把所有没有完成任务的正扫数据全部置为无效
				LOGGER.info("---------更新运单号为："+pdaScanDto.getWaybillNo()+"下所有未完成任务的有效正扫数据,更新成失效状态----------------");
				for (PdaScanEntity temp : whetherCompleteisN) {
					temp.setActive(FossConstants.INACTIVE);
					pdaScanDao.updatePdaScanInfoById(temp);
				}
				LOGGER.info("---------更新完毕----------------");
			}
		}
		LOGGER.info("-----------激活操作结束----------------");
	}
	
	/**
	 * 查询是否存在已经卸车的记录,如果存在，需要进行入库
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-7-12 10:22:58
	 * @param waybillNo
	 */
	@Override
	public void waybillInStockByWaybillNo(String waybillNo) {
		if(StringUtils.isEmpty(waybillNo)){
			return;
		}
		// 获取pda扫描信息
		PdaScanQueryDto pdaScanQueryDto = new PdaScanQueryDto();
		pdaScanQueryDto.setWaybillNo(waybillNo);
		pdaScanQueryDto.setScanType(WaybillConstants.STATUS_UNLOAD);
		List<PdaScanEntity> pdaScanList = pdaScanDao.queryScanInforBySecondCondition(pdaScanQueryDto);
		if(CollectionUtils.isEmpty(pdaScanList)){
			return;
		}
		for(PdaScanEntity pdaScanEntity : pdaScanList){
			try {
				// 添加操作日志，以便进行日志出入库记录跟踪
				StockEntity stockEntity = stockService.queryUniqueStock(pdaScanEntity.getWaybillNo(), pdaScanEntity.getSerialNo());
				// 根据是否异步操作来来添加库存信息
				if (stockEntity == null || (stockEntity != null && !stockEntity.getOrgCode().equals(pdaScanEntity.getOperateOrgCode()))) {
					InOutStockEntity inOutStockEntity = new InOutStockEntity();
					// 设置运载单号
					inOutStockEntity.setWaybillNO(waybillNo);
					// 设置当前部门
					inOutStockEntity.setOrgCode(pdaScanEntity.getOperateOrgCode());
					// 设置操作部门
					inOutStockEntity.setOperatorCode(pdaScanEntity.getClerkCode());
					// 设置操作部门名称
					EmployeeEntity employEntity = employeeService.queryEmployeeByEmpCode(pdaScanEntity.getClerkCode());
					if(employEntity != null){
						inOutStockEntity.setOperatorName(employEntity.getEmpName());
					}
					// 设置更改单类型
					inOutStockEntity.setInOutStockType(StockConstants.UNLOAD_GOODS_IN_STOCK_TYPE);
					// 入库
					stockService.inStockPC(inOutStockEntity);
				}
			} catch (BusinessException e) {
				// 添加异常日志信息
				LOGGER.error("Excepton", e);
				// 抛出异常信息至前台，并终止程序继续运行
				throw new WaybillStoreException(WaybillStoreException.WAYBILLSTOCKINFO_FAIL,
						new Object[] {messageBundle.getMessage(e.getErrorCode(), e.getErrorArguments())});
			}
		}
	}

	/**
	 * 随机获取一个收货部门
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-5-23 11:01:35
	 * @param expressPartOrgCode
	 * @return
	 */
	@Override
	public String getRandomReceiveOrgCodeByExpressPartOrgCode(String expressPartOrgCode){
		String receiveOrgCode = null;
		int randumInt = 0;
		//根据点部查询对应辐射的营业部
		ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto = new ExpressPartSalesDeptQueryDto();
		if(StringUtils.isNotEmpty(expressPartOrgCode)){
			expressPartSalesDeptQueryDto.setExpressPartCode(expressPartOrgCode);				
		}else{
			return null;
		}
		//根据快递点部查询对应所辐射的营业部集合
		List<ExpressPartSalesDeptResultDto> expressPartSalesDeptResultDtoList = 
				expressPartSalesDeptService.queryExpressPartSalesDeptByExpressPartCode(expressPartSalesDeptQueryDto);
		if(CollectionUtils.isEmpty(expressPartSalesDeptResultDtoList)){
			return null;
		}else{
			//随机抽取一个营业部编码出来,如果该营业部编码为空,批量进行数据对比，直到找到一条不为空的记录为止
			randumInt = (int) (Math.random() * expressPartSalesDeptResultDtoList.size());
			LOGGER.info("随机获取的数字为:"+randumInt);
			receiveOrgCode = expressPartSalesDeptResultDtoList.get(randumInt).getSalesDeptCode();
			if(StringUtils.isEmpty(receiveOrgCode)){
				for(ExpressPartSalesDeptResultDto resultDto : expressPartSalesDeptResultDtoList){
					if(StringUtils.isNotEmpty(resultDto.getSalesDeptCode())){
						receiveOrgCode = resultDto.getSalesDeptCode();
						break;
					}
				}
			}
		}
		return receiveOrgCode;
	}

	/**
	 * 根据扫描记录插入对应的货签信息数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-7-18 11:28:33
	 */
	@Override
	public int addLabelGoodsInfo(WaybillEntity waybillEntity, WaybillSystemDto systemDto) {
		if(null == waybillEntity || StringUtils.isEmpty(waybillEntity.getWaybillNo())
				|| null == waybillEntity.getGoodsQtyTotal() || waybillEntity.getGoodsQtyTotal() < 1){
			return 0;
		}
		//查询已经接货扫描的数据信息
		PdaScanQueryDto pdaScanQueryDto = new PdaScanQueryDto();			
		pdaScanQueryDto.setWaybillNo(waybillEntity.getWaybillNo());
		pdaScanQueryDto.setActive(WaybillConstants.YES);
		pdaScanQueryDto.setWhetherComplete(WaybillConstants.YES);
		List<PdaScanEntity> pdaScanList = pdaScanDao.queryScanInforBySecondCondition(pdaScanQueryDto);
		
		LabeledGoodEntity labeledGood = null;
		List<LabeledGoodEntity> labeledGoodList = new ArrayList<LabeledGoodEntity>();
		if(CollectionUtils.isNotEmpty(pdaScanList)){
			List<String> serialNoList = new ArrayList<String>();
			for(PdaScanEntity pdaScanEntity : pdaScanList){
				//判定数据是否包含在里面
				if(StringUtils.isNotEmpty(pdaScanEntity.getSerialNo())
						&& serialNoList.contains(pdaScanEntity.getSerialNo())){
					continue;
				}
				serialNoList.add(pdaScanEntity.getSerialNo());
				labeledGood = new LabeledGoodEntity();
				// id
				labeledGood.setId(UUIDUtils.getUUID());
				// 运单号
				labeledGood.setWaybillNo(pdaScanEntity.getWaybillNo());
				// 有效
				labeledGood.setActive(FossConstants.ACTIVE);
				// 流水号
				labeledGood.setSerialNo(pdaScanEntity.getSerialNo());
				// 创建时间
				labeledGood.setCreateTime(systemDto.getCreateTime() == null ? new Date() : systemDto.getCreateTime());
				// 修改时间
				labeledGood.setModifyTime(systemDto.getModifyTime() == null ? new Date() : systemDto.getModifyTime());
				// 开单时间
				labeledGood.setBillTime(systemDto.getBillTime() == null ? new Date() : systemDto.getBillTime());
				// 初始化
				labeledGood.setInitalVale(FossConstants.YES);
				labeledGoodList.add(labeledGood);
			}
		}else{
			for (int i = 1; i <= waybillEntity.getGoodsQtyTotal(); i++) {
				labeledGood = new LabeledGoodEntity();
				// id
				labeledGood.setId(UUIDUtils.getUUID());
				// 运单号
				labeledGood.setWaybillNo(waybillEntity.getWaybillNo());
				// 有效
				labeledGood.setActive(FossConstants.ACTIVE);
				// 流水号
				labeledGood.setSerialNo(StringHandlerUtil.lpad(String.valueOf(i), NumberConstants.NUMBER_4, String.valueOf(0)));
				// 创建时间
				labeledGood.setCreateTime(systemDto.getCreateTime() == null ? new Date() : systemDto.getCreateTime());
				// 修改时间
				labeledGood.setModifyTime(systemDto.getModifyTime() == null ? new Date() : systemDto.getModifyTime());
				// 开单时间
				labeledGood.setBillTime(systemDto.getBillTime() == null ? new Date() : systemDto.getBillTime());
				// 初始化
				labeledGood.setInitalVale(FossConstants.YES);
				labeledGoodList.add(labeledGood);
			}
		}
		return labeledGoodDao.insertBatchLabeledGoods(labeledGoodList);
	}

	@Override
	public int addLabelGoodPda(WaybillEntity waybillEntity) {
		if(null == waybillEntity || StringUtils.isEmpty(waybillEntity.getWaybillNo())
				|| null == waybillEntity.getGoodsQtyTotal() || waybillEntity.getGoodsQtyTotal() < 1){
			return 0;
		}
		//查询已经接货扫描的数据信息
		PdaScanQueryDto pdaScanQueryDto = new PdaScanQueryDto();			
		pdaScanQueryDto.setWaybillNo(waybillEntity.getWaybillNo());
		pdaScanQueryDto.setActive(WaybillConstants.YES);
		pdaScanQueryDto.setWhetherComplete(WaybillConstants.YES);
		List<PdaScanEntity> pdaScanList = pdaScanDao.queryScanInforBySecondCondition(pdaScanQueryDto);

		int count = 0;
		LabeledGoodPDAEntity labeledGood = null;
		List<LabeledGoodPDAEntity> labelGoodList = new ArrayList<LabeledGoodPDAEntity>();
		if(CollectionUtils.isNotEmpty(pdaScanList)){
			List<String> serialNoList = new ArrayList<String>();
			for(PdaScanEntity pdaScanEntity : pdaScanList){
				//判定数据是否包含在里面
				if(StringUtils.isNotEmpty(pdaScanEntity.getSerialNo())
						&& serialNoList.contains(pdaScanEntity.getSerialNo())){
					continue;
				}
				serialNoList.add(pdaScanEntity.getSerialNo());
				// new货签对象
				labeledGood = new LabeledGoodPDAEntity();
				// id
				labeledGood.setWaybillPDAId(waybillEntity.getId());
				// 运单号
				labeledGood.setWaybillNo(waybillEntity.getWaybillNo());
				// 流水号
				labeledGood.setSerialNo(pdaScanEntity.getSerialNo());
				// 开单时间
				labeledGood.setBillTime(waybillEntity.getCreateTime());
				// 有效
				labeledGood.setActive(FossConstants.ACTIVE);
				// 生效时间
				labeledGood.setCreateTime(waybillEntity.getCreateTime());
				// 失效时间
				labeledGood.setModifyTime(waybillEntity.getCreateTime());
				// 创建人编码
				labeledGood.setCreateUserCode(waybillEntity.getCreateUserCode());
				labelGoodList.add(labeledGood);
			}
		}else{
			// 循环添加货签信息
			for (int i = 1; i <= waybillEntity.getGoodsQtyTotal(); i++) {
				// new货签对象
				labeledGood = new LabeledGoodPDAEntity();
				// id
				labeledGood.setWaybillPDAId(waybillEntity.getId());
				// 运单号
				labeledGood.setWaybillNo(waybillEntity.getWaybillNo());
				// 流水号
				labeledGood.setSerialNo(StringHandlerUtil.lpad(String.valueOf(i), NumberConstants.NUMBER_4, String.valueOf(0)));
				// 开单时间
				labeledGood.setBillTime(waybillEntity.getCreateTime());
				// 有效
				labeledGood.setActive(FossConstants.ACTIVE);
				// 生效时间
				labeledGood.setCreateTime(waybillEntity.getCreateTime());
				// 失效时间
				labeledGood.setModifyTime(waybillEntity.getCreateTime());
				// 创建人编码
				labeledGood.setCreateUserCode(waybillEntity.getCreateUserCode());
				labelGoodList.add(labeledGood);
			}
		}
		if(CollectionUtils.isNotEmpty(labelGoodList)){
			for(LabeledGoodPDAEntity pdaEntity : labelGoodList){
				count = count + labeledGoodPDAService.insertSelective(pdaEntity);
			}
		}
		return count;
	}

	@Override
	public List<PdaScanEntity> queryScanInforBySecondCondition(PdaScanQueryDto pdaScanQueryDto) {
		return pdaScanDao.queryScanInforBySecondCondition(pdaScanQueryDto);
	}

	/**
	 * 查询数据是否数据在子母件数据里面
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-22 14:03:16
	 * @param pdaScanQueryDto
	 * @return
	 */
	@Override
	public List<PdaScanEntity> queryPdaScanInfoWaybillRelateByCondition(PdaScanQueryDto pdaScanQueryDto) {
		return pdaScanDao.queryPdaScanInfoWaybillRelateByCondition(pdaScanQueryDto);
	}

	@Override
	public String getRadomCreateOrgCodeByDriverCode(String driverCode) {
		if(StringUtils.isEmpty(driverCode)){
			return null;
		}
		ExpressVehiclesEntity entity = new ExpressVehiclesEntity();
		entity.setEmpCode(driverCode);
		entity.setActive(FossConstants.YES);
		List<ExpressVehiclesEntity> expressVehiclesList = expressVehiclesService.queryExpressVehiclesByEntity(entity);
		int randumInt = 0;
		String createOrgCode = null;
		if(CollectionUtils.isEmpty(expressVehiclesList)){
			return null;
		}else{
			randumInt = (int) (Math.random() * expressVehiclesList.size());
			createOrgCode = expressVehiclesList.get(randumInt).getOrgCode();
			if(StringUtils.isEmpty(createOrgCode)){
				for(ExpressVehiclesEntity vehicleEntity : expressVehiclesList){
					if(StringUtils.isNotEmpty(vehicleEntity.getOrgCode())){
						createOrgCode = vehicleEntity.getOrgCode();
						break;
					}
				}
			}
		}
		return createOrgCode;
	}

	public IPdaScanDao getPdaScanDao() {
		return pdaScanDao;
	}

	public void setPdaScanDao(IPdaScanDao pdaScanDao) {
		this.pdaScanDao = pdaScanDao;
	}
	public IEWaybillProcessService geteWaybillProcessService() {
		return eWaybillProcessService;
	}
	public void seteWaybillProcessService(IEWaybillProcessService eWaybillProcessService) {
		this.eWaybillProcessService = eWaybillProcessService;
	}
	public void setExpressVehiclesService(IExpressVehiclesService expressVehiclesService) {
		this.expressVehiclesService = expressVehiclesService;
	}
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	public void setExpressPartSalesDeptService(IExpressPartSalesDeptService expressPartSalesDeptService) {
		this.expressPartSalesDeptService = expressPartSalesDeptService;
	}
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	public void setMessageBundle(IMessageBundle messageBundle) {
		this.messageBundle = messageBundle;
	}

	public void setLabeledGoodDao(ILabeledGoodDao labeledGoodDao) {
		this.labeledGoodDao = labeledGoodDao;
	}

	public void setLabeledGoodPDAService(ILabeledGoodPDAService labeledGoodPDAService) {
		this.labeledGoodPDAService = labeledGoodPDAService;
	}
	
	/**
	 * @param waybillRelateDetailEntityService the waybillRelateDetailEntityService to set
	 */
	public void setWaybillRelateDetailEntityService(IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
		this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
	}
}