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
 *  PROJECT NAME  : tfr-stock
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/server/service/impl/PDAStockService.java
 *  
 *  FILE NAME          :PDAStockService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stock.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.define.ExceptionGoodsConstants;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.NoLabelGoodsEntity;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPackOutService;
import com.deppon.foss.module.transfer.packaging.api.shared.define.PackagingConstants;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.WaybillNoLogingDateDto;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAStockService;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockPositionNumberEntity;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.util.define.FossConstants;
/**
 * 实现了PDA各种单票出入库方法
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 上午11:53:15
 */
public class PDAStockService implements IPDAStockService{
	
	/**
	* @fields LOGGER
	*/
	private static final Logger LOGGER = LogManager.getLogger(PDAStockService.class);
	
	/**
	* 库存管理Service
	*/
	private IStockService stockService;
	/**
	* 业务互斥锁服务
	*/
	private IBusinessLockService businessLockService;
	/**
	* 无标签多货的业务操作方法 Service
	*/
	private INoLabelGoodsService noLabelGoodsService;
	/** 综合管理 组织信息 Service*/
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/** 包装*/
	private IPackOutService packOutService;
	
	/**
	* @param packOutService
	* @description  设置包装
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-26 下午6:16:53
	*/
	public void setPackOutService(IPackOutService packOutService) {
		this.packOutService = packOutService;
	}

	/**
	* @param stockService
	* @description 设置库存管理Service
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-8-5 上午9:32:52
	*/
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	
	/**
	* @param businessLockService
	* @description 设置业务互斥锁服务
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-8-5 上午9:34:45
	*/
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	
	/**
	* @param noLabelGoodsService
	* @description 设置无标签多货的业务操作方法 Service
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-8-5 上午9:35:09
	*/
	public void setNoLabelGoodsService(INoLabelGoodsService noLabelGoodsService) {
		this.noLabelGoodsService = noLabelGoodsService;
	}
	
	/**
	* @param orgAdministrativeInfoService
	* @description 综合管理 组织信息 Service
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-8-5 上午9:35:29
	*/
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * PDA入库代包装货区
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号
	 * @param inOutStockEntity.scanTime 扫描时间
	 * @param inOutStockEntity.pdaDeviceNO PDA设备号
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-8 下午4:10:26
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#inStockPackageAreaPDA(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Override
	public int inStockPackageAreaPDA(InOutStockEntity inOutStockEntity) {
		inOutStockEntity.setDeviceType(StockConstants.PDA_DEVICE_TYPE);
		inOutStockEntity.setInOutStockType(StockConstants.PACKAGE_AREA_IN_STOCK_TYPE);
		
		//业务锁
		MutexElement mutex = new MutexElement(inOutStockEntity.getWaybillNO(),"IN_OUT_STOCK",MutexElementType.IN_OUT_STOCK);
		businessLockService.lock(mutex, StockConstants.IN_OUT_TIMEOUT);
		LOGGER.info("入库包装货区加锁：" + mutex.getBusinessNo());
		
		stockService.inStock(inOutStockEntity, StockConstants.CONFIRM, StockConstants.CONFIRM, false);
		
		//解业务锁
		businessLockService.unlock(mutex);
		LOGGER.info("入库包装货区完成解锁：" + mutex.getBusinessNo());
		
		return FossConstants.SUCCESS;
	}
	/**
	 * 
	 * PDA出库代包装货区
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号       
	 * @param inOutStockEntity.scanTime 扫描时间
	 * @param inOutStockEntity.pdaDeviceNO PDA设备号   
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-8 下午4:10:34
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#outStockPackageAreaPDA(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Override
	public int outStockPackageAreaPDA(InOutStockEntity inOutStockEntity) {
		inOutStockEntity.setDeviceType(StockConstants.PDA_DEVICE_TYPE);
		//inOutStockEntity.setInOutStockType(StockConstants.NORMAL_AREA_FROM_SPECIAL_IN_STOCK_TYPE);
		inOutStockEntity.setInOutStockType(StockConstants.PACKAGE_AREA_IN_STOCK_TYPE_NORMAL);
		
		MutexElement mutex = new MutexElement(inOutStockEntity.getWaybillNO(),"IN_OUT_STOCK",MutexElementType.IN_OUT_STOCK);
		businessLockService.lock(mutex, StockConstants.IN_OUT_TIMEOUT);
		LOGGER.info("出库包装货区加锁：" + mutex.getBusinessNo());
		
		int result = stockService.inStock(inOutStockEntity, StockConstants.CONFIRM, StockConstants.CONFIRM, false);
		//int result = stockService.outStock(inOutStockEntity);
		
		/**入库类型是 入库包装货区时 需要调用下 包装的接口  begin*/
		if(StringUtils.equals(StockConstants.PACKAGE_AREA_IN_STOCK_TYPE_NORMAL, inOutStockEntity.getInOutStockType())){
			WaybillNoLogingDateDto waybillNoLogingDateDto = new WaybillNoLogingDateDto();
			waybillNoLogingDateDto.setWaybillNo(inOutStockEntity.getWaybillNO());
			waybillNoLogingDateDto.setLogingDate(new Date());
			waybillNoLogingDateDto.setType(PackagingConstants.PACKAGING_LOGING_OUT);
			packOutService.insertWaybillNoLogingDate(waybillNoLogingDateDto);
		}
		/**入库类型是 入库包装货区时 需要调用下 包装的接口  end*/
		businessLockService.unlock(mutex);
		LOGGER.info("出库包装货区完成解锁：" + mutex.getBusinessNo());
		
		return result;
	}
	/**
	 * PDA入库贵重物品货区
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号       
	 * @param inOutStockEntity.scanTime 扫描时间
	 * @param inOutStockEntity.pdaDeviceNO PDA设备号  
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-8 下午4:10:40
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#inStockValuableAreaPDA(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Override
	public int inStockValuableAreaPDA(InOutStockEntity inOutStockEntity) {
		inOutStockEntity.setDeviceType(StockConstants.PDA_DEVICE_TYPE);
		inOutStockEntity.setInOutStockType(StockConstants.VALUABLE_AREA_IN_STOCK_TYPE);
		
		//业务锁
		MutexElement mutex = new MutexElement(inOutStockEntity.getWaybillNO(),"IN_OUT_STOCK",MutexElementType.IN_OUT_STOCK);
		businessLockService.lock(mutex, StockConstants.IN_OUT_TIMEOUT);
		LOGGER.info("入库贵重物品货区加锁：" + mutex.getBusinessNo());
		
		stockService.inStock(inOutStockEntity, StockConstants.CONFIRM, StockConstants.CONFIRM, false);
		
		//解业务锁
		businessLockService.unlock(mutex);
		LOGGER.info("入库贵重物品货区完成解锁：" + mutex.getBusinessNo());
		
		return FossConstants.SUCCESS;
	}
	/**
	 * PDA出库贵重物品货区
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号       
	 * @param inOutStockEntity.scanTime 扫描时间
	 * @param inOutStockEntity.pdaDeviceNO PDA设备号   
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-8 下午4:10:46
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#outStockValuableAreaPDA(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Override
	public int outStockValuableAreaPDA(InOutStockEntity inOutStockEntity) {
		inOutStockEntity.setDeviceType(StockConstants.PDA_DEVICE_TYPE);
		//inOutStockEntity.setInOutStockType(StockConstants.NORMAL_AREA_FROM_SPECIAL_IN_STOCK_TYPE);
		inOutStockEntity.setInOutStockType(StockConstants.VALUABLE_AREA_IN_STOCK_TYPE_NORMAL);
		
		MutexElement mutex = new MutexElement(inOutStockEntity.getWaybillNO(),"IN_OUT_STOCK",MutexElementType.IN_OUT_STOCK);
		businessLockService.lock(mutex, StockConstants.IN_OUT_TIMEOUT);
		LOGGER.info("出库贵重物品货区加锁：" + mutex.getBusinessNo());
		
		int result = stockService.inStock(inOutStockEntity, StockConstants.CONFIRM, StockConstants.CONFIRM, false);
		//int result = stockService.outStock(inOutStockEntity);
		
		businessLockService.unlock(mutex);
		LOGGER.info("出库贵重物品货区完成解锁：" + mutex.getBusinessNo());
		return result;
	}
	/**
	 *PDA入库异常货区
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号       
	 * @param inOutStockEntity.scanTime 扫描时间
	 * @param inOutStockEntity.pdaDeviceNO PDA设备号   
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-8 下午4:10:53
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#inStockExceptionAreaPDA(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Override
	public int inStockExceptionAreaPDA(InOutStockEntity inOutStockEntity) {
		inOutStockEntity.setDeviceType(StockConstants.PDA_DEVICE_TYPE);
		
	    //判断是否是无标签货物
		if(StringUtils.startsWithIgnoreCase(inOutStockEntity.getWaybillNO(), "w")){
			inOutStockEntity.setInOutStockType(StockConstants.NO_LABEL_GOODS_IN_STOCK_TYPE);
			//更新库存状态
			NoLabelGoodsEntity noLabelGoods = new NoLabelGoodsEntity();
			noLabelGoods.setNoLabelBillNo(inOutStockEntity.getWaybillNO());
			noLabelGoods.setNoLabelSerialNo(inOutStockEntity.getSerialNO());
			noLabelGoods.setStockStatus(ExceptionGoodsConstants.STOCK_STATUS_IN);
			noLabelGoodsService.updateNoLabelGoods(noLabelGoods);
		}else{
			inOutStockEntity.setInOutStockType(StockConstants.EXCEPTION_AREA_IN_STOCK_TYPE);
		}
		
		//业务锁
		MutexElement mutex = new MutexElement(inOutStockEntity.getWaybillNO(),"IN_OUT_STOCK",MutexElementType.IN_OUT_STOCK);
		businessLockService.lock(mutex, StockConstants.IN_OUT_TIMEOUT);
		LOGGER.info("入库异常货区加锁：" + mutex.getBusinessNo());
		
		stockService.inStock(inOutStockEntity, StockConstants.CONFIRM, StockConstants.CONFIRM, false);
		
		//解业务锁
		businessLockService.unlock(mutex);
		LOGGER.info("入库异常货区完成解锁：" + mutex.getBusinessNo());
		
		return FossConstants.SUCCESS;
	}
	/**
	 * PDA出库异常货区
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号       
	 * @param inOutStockEntity.scanTime 扫描时间
	 * @param inOutStockEntity.pdaDeviceNO PDA设备号  
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-8 下午4:11:57
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#outStockExceptionAreaPDA(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Override
	public int outStockExceptionAreaPDA(InOutStockEntity inOutStockEntity) {
		inOutStockEntity.setDeviceType(StockConstants.PDA_DEVICE_TYPE);
		
		MutexElement mutex = new MutexElement(inOutStockEntity.getWaybillNO(),"IN_OUT_STOCK",MutexElementType.IN_OUT_STOCK);
		businessLockService.lock(mutex, StockConstants.IN_OUT_TIMEOUT);
		LOGGER.info("出库异常货区加锁：" + mutex.getBusinessNo());
		int result=0;
		
		 //判断是否是无标签货物
		if(StringUtils.startsWithIgnoreCase(inOutStockEntity.getWaybillNO(), "w")){
			inOutStockEntity.setInOutStockType(StockConstants.NO_LABEL_ORIGINAL_GOODS_IN_STOCK_TYPE);
			NoLabelGoodsEntity noLabelGoodsEntity = noLabelGoodsService.queryUniqNoLabelGoods(inOutStockEntity.getWaybillNO(), inOutStockEntity.getSerialNO());
			if(noLabelGoodsEntity != null && StringUtils.isNotBlank(noLabelGoodsEntity.getOriginalWaybillNo()) 
					&& StringUtils.isNotBlank(noLabelGoodsEntity.getOriginalSerialNo())){
				//出库
				stockService.outStock(inOutStockEntity);
				//封装原货件入库正常货区参数
				inOutStockEntity.setWaybillNO(noLabelGoodsEntity.getOriginalWaybillNo());
				inOutStockEntity.setSerialNO(noLabelGoodsEntity.getOriginalSerialNo());
				stockService.inStock(inOutStockEntity, StockConstants.CONFIRM, StockConstants.CONFIRM, false);
				//更新无标签货物库存状态
				noLabelGoodsEntity.setStockStatus(ExceptionGoodsConstants.STOCK_STATUS_OUT);
				noLabelGoodsService.updateNoLabelGoods(noLabelGoodsEntity);
			}else{
				LOGGER.error("无标签货物没被认领，不可出库.运单号 ：" + inOutStockEntity.getWaybillNO() + " 流水号：" + inOutStockEntity.getSerialNO());
				throw new StockException("无标签货物没被认领，不可出库","");
			}
		}else{
			//inOutStockEntity.setInOutStockType(StockConstants.NORMAL_AREA_FROM_SPECIAL_IN_STOCK_TYPE);
			inOutStockEntity.setInOutStockType(StockConstants.EXCEPTION_AREA_IN_STOCK_TYPE_NORMAL);
			result = stockService.inStock(inOutStockEntity, StockConstants.CONFIRM, StockConstants.CONFIRM, false);
			NoLabelGoodsEntity noLabelGoods = new NoLabelGoodsEntity();
			noLabelGoods.setOriginalWaybillNo(inOutStockEntity.getWaybillNO());
			noLabelGoods.setOriginalSerialNo(inOutStockEntity.getSerialNO());
			noLabelGoods.setStockStatus(ExceptionGoodsConstants.STOCK_STATUS_OUT);
			noLabelGoodsService.updateNoLabelGoods(noLabelGoods);
		}
		
		businessLockService.unlock(mutex);
		LOGGER.info("出库异常货区完成解锁：" + mutex.getBusinessNo());
		return result;
	}
	/**
	 * PDA单件入库
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.inOutStockType 入库类型  参见常量类 com.deppon.foss.module.transfer.stock.api.define.StockConstants
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号       
	 * @param inOutStockEntity.scanTime 扫描时间
	 * @param inOutStockEntity.pdaDeviceNO PDA设备号   
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-8 下午4:12:07
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#singleInStockPDA(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Override
	public int singleInStockPDA(InOutStockEntity inOutStockEntity) {
		inOutStockEntity.setDeviceType(StockConstants.PDA_DEVICE_TYPE);
		
		//业务锁
		MutexElement mutex = new MutexElement(inOutStockEntity.getWaybillNO(),"IN_OUT_STOCK",MutexElementType.IN_OUT_STOCK);
		businessLockService.lock(mutex, StockConstants.IN_OUT_TIMEOUT);
		LOGGER.info("单票入库加锁：" + mutex.getBusinessNo());
		
		stockService.inStock(inOutStockEntity, StockConstants.CONFIRM, StockConstants.CONFIRM, false);
		
		//解业务锁
		businessLockService.unlock(mutex);
		LOGGER.info("单票入库完成解锁：" + mutex.getBusinessNo());
		
		return FossConstants.SUCCESS;
	}
	
	/**
	* @description PDA 对已在库存的货物进行库位确认
	* @param inOutStockEntity.waybillNO 运单号
	* @param inOutStockEntity.serialNO 流水号 
	* @param inOutStockEntity.position 库位
	* @param inOutStockEntity.orgCode 部门编号
	* @param inOutStockEntity.operatorCode 操作人工号
	* @param inOutStockEntity.operatorName 操作人名称
	* @param inOutStockEntity.scanTime 扫描时间
	* @param inOutStockEntity.pdaDeviceNO PDA设备号   
	* @return
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-17 下午4:15:53
	*/
	@Override
	public int updateStockStockPosition(InOutStockEntity inOutStockEntity) {
		inOutStockEntity.setDeviceType(StockConstants.PDA_DEVICE_TYPE);
		MutexElement mutex = new MutexElement(inOutStockEntity.getWaybillNO(),"IN_OUT_STOCK",MutexElementType.IN_OUT_STOCK);
		businessLockService.lock(mutex, StockConstants.IN_OUT_TIMEOUT);
		LOGGER.info("单票入库加锁：" + mutex.getBusinessNo());
		Integer backFlag = FossConstants.SUCCESS;
		List<InOutStockEntity> inStockList = new ArrayList<InOutStockEntity>();
		inStockList.add(inOutStockEntity);
		try{
			stockService.updateStockStockPosition(inStockList, inOutStockEntity.getPosition(), inOutStockEntity.getOrgCode(), inOutStockEntity.getOperatorCode(), inOutStockEntity.getOperatorName());
		}catch (Exception e) {
			backFlag = FossConstants.FAILURE;
			LOGGER.error("PDA库位更新失败",e);
		}
		//解业务锁
		businessLockService.unlock(mutex);
		LOGGER.info("单票入库完成解锁：" + mutex.getBusinessNo());
		
		return backFlag;
	}
	
	/**
	* @param orgCode 当前部门的编号
	* @return
	* @description 对应外场的驻地派送部货区
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-17 下午5:19:11
	*/
	@Override
	public List<BaseDataDictDto> areaByOrgcodeList(String orgCode) {
		List<BaseDataDictDto> backList = new ArrayList<BaseDataDictDto>();
		try{
			OrgAdministrativeInfoEntity oaiPojo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
			String transferCode = stockService.transferCenterCodeByBigOrg(oaiPojo);
			backList = stockService.queryGoodsAreaListByOrganizationCode(transferCode);
		}catch (Exception e) {
			LOGGER.error("PDA对应外场的驻地派送部货区异常",e);
		}
		
		return backList;
	}
	
	/**
	* @param orgCode 当前部门的编号
	* @param goodsAreaCode 驻地派送部货区的code
	* @return
	* @description 
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-17 下午5:21:46
	*/
	@Override
	public List<BaseDataDictDto> queryPositionList(String orgCode,
			String goodsAreaCode) {
		List<BaseDataDictDto> backList = new ArrayList<BaseDataDictDto>();
		try{
			OrgAdministrativeInfoEntity oaiPojo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
			String transferCode = stockService.transferCenterCodeByBigOrg(oaiPojo);
			backList = stockService.queryStorageListByGoodsAreaFrom(transferCode, goodsAreaCode);
		}catch (Exception e) {
			LOGGER.error("PDA对应外场的驻地派送部货区异常",e);
		}
		return backList;
	}

	/**
	* @param waybillNo 运单号
	* @param orgCode 部门编号
	* @param stockPositionNumber  定位编号
	* @return 根据运单号、部门编号来存储定位编号到库存表中
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-18 下午5:21:46
	*/
	public List<StockPositionNumberEntity> queryStockPositionNumber(String waybillNo,
			String orgCode) {
		List<StockPositionNumberEntity> stockPositionNumberEntity = new ArrayList<StockPositionNumberEntity>();
		stockPositionNumberEntity = stockService.queryStockPositionNumber(
					waybillNo, orgCode);
		return stockPositionNumberEntity;
	}

	/**
	 * @param waybillNo 运单号
	 * @param serialNo  流水号
	 * @param orgCode 部门编号
	 * @param stockPositionNumber  定位编号
	 * @return 根据运单号、流水号、部门编号来存储定位编号到库存表中
	 * @description 
	 * @version 1.0
	 * @author 200968-foss-zwd
	 * @update 2014-12-19 上午8:21:46
	 */
	public void saveStockPositionNumber(List<StockPositionNumberEntity> stockPositionNumberEntityList) {
	  stockService.saveStockPositionNumber(stockPositionNumberEntityList);
	}

}