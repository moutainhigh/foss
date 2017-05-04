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
 *  PROJECT NAME  : tfr-exceptiongoods
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/exceptiongoods/server/service/impl/ContrabandGoodsService.java
 *  
 *  FILE NAME          :ContrabandGoodsService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.transfer.exceptiongoods.api.define.ExceptionGoodsConstants;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IContrabandGoodsDao;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IContrabandGoodsService;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.ContrabandGoodsEntity;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.exception.ContrabandGoodsException;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity;
import com.deppon.foss.util.define.FossConstants;
/**
 * 实现违禁品的业务操作
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 下午12:18:13
 */
public class ContrabandGoodsService implements IContrabandGoodsService{
	
	/**
	* @fields LOGGER
	*/
	private static final Logger LOGGER = LogManager.getLogger(ContrabandGoodsService.class);
	/** 违禁品DAO*/
	private IContrabandGoodsDao contrabandGoodsDao;
	/** 库存Service*/
	private IStockService stockService;
	/** 营业部派送部Service*/
	private ISaleDepartmentService saleDepartmentService;
	/** 货区管理service*/
	private IGoodsAreaService goodsAreaService;
	/** 综合管理 组织信息 Service*/
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	* @param contrabandGoodsDao
	* @description 设置违禁品DAO
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:43:47
	*/
	public void setContrabandGoodsDao(IContrabandGoodsDao contrabandGoodsDao) {
		this.contrabandGoodsDao = contrabandGoodsDao;
	}
	
	/**
	* @param saleDepartmentService
	* @description 设置 营业部派送部Service
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:44:24
	*/
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	
	/**
	* @param stockService
	* @description 设置库存Service
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:44:07
	*/
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	
	/**
	* @param goodsAreaService
	* @description 设置货区管理service
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:44:42
	*/
	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}
	
	/**
	* @param orgAdministrativeInfoComplexService
	* @description 设置综合管理 组织信息 Service
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:45:44
	*/
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * 分页查询违禁品
	 * @param contrabandGoods.waybillNo 运单号
	 * @param contrabandGoods.findOrgCode 报告部门编号
	 * @param contrabandGoods.handoverOrgCode 移交部门编号
	 * @param contrabandGoods.createBillOrgCode 开单部门编号
	 * @param contrabandGoods.beginTime 开始时间
	 * @param contrabandGoods.endTime 结束时间
	 * @param contrabandGoods.processResult 处理结果
	 * @param contrabandGoods.handoverStatus 移交状态
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-5 下午4:29:48
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IContrabandGoodsService#queryContrabandGoods(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.ContrabandGoodsEntity, int, int)
	 */
	@Override
	public List<ContrabandGoodsEntity> queryContrabandGoods(
			ContrabandGoodsEntity contrabandGoods, String currentOrgCode, int limit, int start) {
		return contrabandGoodsDao.queryContrabandGoods(contrabandGoods, limit, start);
	}
	
	/**
	 * 查询违禁品总数
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-7 下午3:51:59
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IContrabandGoodsService#queryContrabandGoodsCount(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.ContrabandGoodsEntity, java.lang.String)
	 */
	@Override
	public Long queryContrabandGoodsCount(ContrabandGoodsEntity contrabandGoods, String currentOrgCode) {
		return contrabandGoodsDao.queryContrabandGoodsCount(contrabandGoods);
	}
	
	/**
	 * 在OA中上报违禁品时调用此接口将违禁品同步到FOSS
	 * @param waybillNo 运单号
	 * @param oaErrorNo 差错编号
	 * @param findOrgCode  发现部门
	 * @param findTime  发现时间
	 * @param isContrabandGoods  是否是违禁品
	 * @paramo perateType  操作类型：  1 --上报, 2 -- 处理结果
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-14 上午9:00:02
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IContrabandGoodsService#oaToFossContrabandGoods(java.lang.String, java.lang.String, java.lang.String, java.util.Date, boolean, int)
	 */
	@Transactional
	@Override
	public int oaToFossContrabandGoods(String waybillNo, String oaErrorNo, String findOrgCode, Date findTime, Boolean isContrabandGoods, int operateType) {
		if(ExceptionGoodsConstants.MARK_CONTRABAND == operateType){
			LOGGER.debug("OA同步违禁品到FOSS，差错编号：" + oaErrorNo);
			ContrabandGoodsEntity contrabandGoodsEntity = new ContrabandGoodsEntity();
			//运单号
			contrabandGoodsEntity.setWaybillNo(waybillNo);
			//发现时间
			contrabandGoodsEntity.setFindTime(findTime);
			//OA差错编号
			contrabandGoodsEntity.setOaErrorNo(oaErrorNo);
			//发现部门编号
			contrabandGoodsEntity.setFindOrgCode(findOrgCode);
			//未交接
			contrabandGoodsEntity.setHandoverStatus(ExceptionGoodsConstants.NO_HANDOVER_STATUS);
			//疑似违禁品
			contrabandGoodsEntity.setProcessResult(ExceptionGoodsConstants.SUSPICION_CONTRABAND_PROCESS_RESULT);
			//保存
			contrabandGoodsDao.addContrabandGoods(contrabandGoodsEntity);
		}else{//更新处理结果
			LOGGER.debug("OA更新违禁品处理状态到FOSS，差错编号：" + oaErrorNo);
			ContrabandGoodsEntity contrabandGoodsEntity = new ContrabandGoodsEntity();
			//OA差错编号
			contrabandGoodsEntity.setOaErrorNo(oaErrorNo);
			if(isContrabandGoods){
				//违禁品
				contrabandGoodsEntity.setProcessResult(ExceptionGoodsConstants.CONTRABAND_PROCESS_RESULT);
			}else{
				//非违禁品
				contrabandGoodsEntity.setProcessResult(ExceptionGoodsConstants.NO_CONTRABAND_PROCESS_RESULT);
			}
			contrabandGoodsDao.updateContrabandGoods(contrabandGoodsEntity);
		}
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 移交违禁品到驻地派送部
	 * 出库异常货区
	 * 入库派送部货区
	 * 修改走货路径 驻地派送部为最终到达部门
	 * 更新移交状态
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-17 下午3:01:21
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IContrabandGoodsService#handoverContrabandGoods(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.ContrabandGoodsEntity, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	public int handoverContrabandGoods(List<ContrabandGoodsEntity> contrabandGoodsList, String operatorCode, String operatorName) {
		
		for(ContrabandGoodsEntity contrabandGoods : contrabandGoodsList){
			//设置查询外场异常货区货件库存参数： 运单、部门、货区
			WaybillStockEntity waybillStockEntity = new WaybillStockEntity();
			waybillStockEntity.setWaybillNO(contrabandGoods.getWaybillNo());
			waybillStockEntity.setOrgCode(contrabandGoods.getFindOrgCode());
			//获取异常货区编号
			List<GoodsAreaEntity> goodsAreaList = goodsAreaService.queryGoodsAreaListByType(contrabandGoods.getFindOrgCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
			String exceptionGoodsArea = null;
			if(CollectionUtils.isNotEmpty(goodsAreaList)){
				exceptionGoodsArea = goodsAreaList.get(0).getGoodsAreaCode();
			}else{
				//根据部门查询货区失败
				LOGGER.error("查询组织：" + contrabandGoods.getFindOrgCode() + "的异常货区失败");
				throw new ContrabandGoodsException(ContrabandGoodsException.QUERY_EXCEPTION_GOODSAREA_ERROR_CODE,"");
			}
			waybillStockEntity.setGoodsAreaCode(exceptionGoodsArea);
			//根据运单号、部门查询异常货区库存
			List<StockEntity> stockList = stockService.queryStock(waybillStockEntity);
			if(CollectionUtils.isNotEmpty(stockList)){
				InOutStockEntity inOutStockEntity = new InOutStockEntity();
				//设置出入库参数
				inOutStockEntity.setWaybillNO(contrabandGoods.getWaybillNo());
				inOutStockEntity.setOrgCode(contrabandGoods.getFindOrgCode());
				inOutStockEntity.setOperatorCode(operatorCode);
				inOutStockEntity.setOperatorName(operatorName);
				//遍历库存记录
				for(StockEntity stock : stockList){
					//设置流水号
					inOutStockEntity.setSerialNO(stock.getSerialNO());
					//出库异常货区    入库派送部货区     修改走货路径 驻地派送部为最终到达部门
					stockService.handoverContrabandToStationStock(inOutStockEntity);
				}
			}else{
				//异常货区库存中不存在该票货物
				LOGGER.error("异常货区库存中不存在该票货物");
				throw new ContrabandGoodsException(ContrabandGoodsException.NOT_EXIST_EXCEPTION_GOODSAREA_STOCK_ERROR_CODE,"");
			}
			//更新移交状态
			contrabandGoods.setHandoverStatus(ExceptionGoodsConstants.HANDOVER_STATUS);
			contrabandGoodsDao.updateContrabandGoods(contrabandGoods);
		}
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 判断部门类型是否是营业部
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-18 上午11:18:16
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IContrabandGoodsService#isSaleOrg(java.lang.String)
	 */
	public String queryCurrentOrgType(OrgAdministrativeInfoEntity currentOrg){
		
		//营业部
		if(StringUtils.endsWith(FossConstants.YES, currentOrg.getSalesDepartment())){
			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(currentOrg.getCode());
			//驻地 派送部
			if(StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getStation()) && 
					StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getDelivery())){
				return ExceptionGoodsConstants.STATION_ORG;
			}
			return ExceptionGoodsConstants.SALE_ORG;
		}
		//外场
		return ExceptionGoodsConstants.TRANSFER_CENTER_ORG;
	}
	/**
	 * 获取当前用户所属外场编号
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-18 下午12:16:02
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IContrabandGoodsService#queryTransferCenterCode(com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity)
	 */
	public OrgAdministrativeInfoEntity queryTransferCenterCode(OrgAdministrativeInfoEntity currentOrg){
		//外场
		if(StringUtils.endsWith(FossConstants.YES, currentOrg.getTransferCenter())){
			return currentOrg;
		}
		List<String> bizTypesList = new ArrayList<String>();
		//外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoByCode(currentOrg.getCode(), bizTypesList);
		
		if(orgAdministrativeInfoEntity != null){
			return orgAdministrativeInfoEntity;
		}else{
			//查询用户所属外场部门失败
			LOGGER.error("查询用户所属外场部门失败");
			throw new ContrabandGoodsException(ContrabandGoodsException.QUERY_USER_TRANSFER_CENTER_ERROR_CODE,"");
		}
	}
    
	/**
	 * 查询运单是否已上报违禁品
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-30 下午4:44:06
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IContrabandGoodsService#queryContrabandGoodsStatus(java.lang.String)
	 */
	@Override
	public String queryContrabandGoodsStatus(String waybillNo) {
		ContrabandGoodsEntity contrabandGoods= contrabandGoodsDao.queryContrabandGoods(waybillNo);
		//初始值 未上报过违禁品
		String processResult = ExceptionGoodsConstants.NO_REPORT_CONTRABAND;
		if(contrabandGoods != null && contrabandGoods.getExceptionResult() == 1){
			processResult = contrabandGoods.getProcessResult();
		}
		return processResult;
	}

	/**
	 * 增加从QMS推送的违禁品
	 * @author 316759-foss-ruipengwang
	 * @date 2016-05-19 下午16:38:04
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IContrabandGoodsService#qmsToFossContrabandGoods(java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Transactional
	@Override
	public int qmsToFossContrabandGoods(String waybillNo, String qmsErrorNo,
			String goodsName, int exceptionStatus, int exceptionResult) throws Exception {
		LOGGER.info("查询违禁品是否已存在");
		ContrabandGoodsEntity contrabandGoodsEntity = contrabandGoodsDao.queryContrabandGoods(waybillNo);
		if (contrabandGoodsEntity != null) {
			LOGGER.debug("QMS修改违禁品信息，差错编号：" + qmsErrorNo);
			// 运单号
			contrabandGoodsEntity.setWaybillNo(waybillNo);
			// 差错编号
			contrabandGoodsEntity.setOaErrorNo(qmsErrorNo);
			// 差错状态
			contrabandGoodsEntity.setExceptionStatus(exceptionStatus);
			// 差错结果
			contrabandGoodsEntity.setExceptionResult(exceptionResult);
			// 货物名称
			contrabandGoodsEntity.setGoodsName(goodsName);
			// 保存
			contrabandGoodsDao.updateContrabandGoodsFromQms(contrabandGoodsEntity);
		} else {
			LOGGER.debug("QMS同步违禁品到FOSS，差错编号：" + qmsErrorNo);
			ContrabandGoodsEntity goodsEntity = new ContrabandGoodsEntity();
			// 运单号
			goodsEntity.setWaybillNo(waybillNo);
			// 差错编号
			goodsEntity.setOaErrorNo(qmsErrorNo);
			// 差错状态
			goodsEntity.setExceptionStatus(exceptionStatus);
			// 差错结果
			goodsEntity.setExceptionResult(exceptionResult);
			// 货物名称
			goodsEntity.setGoodsName(goodsName);
			// 保存
			contrabandGoodsDao.addContrabandGoodsFromQms(goodsEntity);
		}
		return FossConstants.SUCCESS;
	}
	
}