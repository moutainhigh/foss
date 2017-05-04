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
 *  PROJECT NAME  : tfr-stockchecking-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/server/dao/IStTaskDao.java
 *  
 *  FILE NAME          :IStTaskDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.lostwarning.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportClearless;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseDto;
import com.deppon.foss.module.transfer.load.api.server.service.IConnectionBillService;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.load.api.shared.domain.ConnectionBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillNoForUnloadTaskLackGoodsDto;
import com.deppon.foss.module.transfer.lostwarning.api.server.dao.IUnloadLackDiffReportDao;
import com.deppon.foss.module.transfer.lostwarning.api.server.define.LostWarningConstant;
import com.deppon.foss.module.transfer.lostwarning.api.server.service.IUnloadLackService;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadDiffReportDao;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.LackGoodsFoundEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ReportOaErrorLogEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadDiffReportDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadWaybillDetailDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/** 
 * @className: UnloadService
 * @author: ShiWei shiwei@outlook.com
 * @description: 卸车service
 * @date: 2013-6-4 下午8:55:21
 * 
 */
public class UnloadLackService implements IUnloadLackService{
	
	private IUnloadTaskService unloadTaskService;
	
	private IUnloadDiffReportDao unloadDiffReportDao;
	
	private IUnloadLackDiffReportDao unloadLackDiffReportDao;
	
	/**
	 * 记录日志
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	/**
	 * 配载单service
	 */
	private IVehicleAssembleBillService vehicleAssembleBillService;
	
	/** 
	 * 查询运单信息接口
	 */
	private IWaybillManagerService waybillManagerService;
	
	/** 
	 * 数据字典接口
	 */
	private IDataDictionaryValueService dataDictionaryValueService;
	
	/** 
	 * 产品接口
	 */
	private IProductService productService;
	
	/**
	 * 常量，当传给oa的数据字典缺失时，传该常量
	 */
	private static final String UNKNOWN_TYPE = "未知类型";
	
	/** 综合管理 组织信息 Service*/
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/** 
	 * 组织接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 库存service，用来少货找到后入库
	 */
	private IStockService stockService;
	
	/**
	 * 交接单service
	 */
	private IHandOverBillService handOverBillService;
	
	/**
	 * 卸车任务查询service
	 */
	private IUnloadTaskQueryService unloadTaskQueryService;
	
	/**
	 * 配置参数service
	 */
	private IConfigurationParamsService configurationParamsService;
	
	
	/**接驳交接单查询service**/
	private IConnectionBillService connectionBillService;

	private IOutfieldService outfieldService;
	
	private LostWarningAnalyDataForLack lostWarningAnalyDataForLack;
	
	
	
	public void setLostWarningAnalyDataForLack(
			LostWarningAnalyDataForLack lostWarningAnalyDataForLack) {
		this.lostWarningAnalyDataForLack = lostWarningAnalyDataForLack;
	}


	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}


	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}

	public void setConnectionBillService(
			IConnectionBillService connectionBillService) {
		this.connectionBillService = connectionBillService;
	}


	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}


	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setVehicleAssembleBillService(
			IVehicleAssembleBillService vehicleAssembleBillService) {
		this.vehicleAssembleBillService = vehicleAssembleBillService;
	}

	public void setUnloadDiffReportDao(IUnloadDiffReportDao unloadDiffReportDao) {
		this.unloadDiffReportDao = unloadDiffReportDao;
	}

	public void setUnloadLackDiffReportDao(IUnloadLackDiffReportDao unloadLackDiffReportDao) {
		this.unloadLackDiffReportDao = unloadLackDiffReportDao;
	}

	
	public void setUnloadTaskService(IUnloadTaskService unloadTaskService) {
		this.unloadTaskService = unloadTaskService;
	}
	
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
	/**   
	 * @param unloadTaskQueryService the unloadTaskQueryService to set
	 * Date:2013-7-29上午9:21:24
	 */
	public void setUnloadTaskQueryService(
			IUnloadTaskQueryService unloadTaskQueryService) {
		this.unloadTaskQueryService = unloadTaskQueryService;
	}



	/**
	 * 上报卸车多货、少货差错
	 * job
	 * @author 045923-foss-shiwei
	 * @date 2013-6-5 下午3:15:01
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadService#executeUnloadDiffReportTask(java.lang.String)
	 */
	@Override
	public int executeUnloadDiffReportTask(String reportId) {
		/**
		 * 更新会影响ogg的再次同步，故不再进行赋值更新，仅将处理完差异报告插入临时表
		 */
		Map<String, UnloadDiffReportDto> waybillNoMap = new HashMap<String, UnloadDiffReportDto>();
		//获取卸车部门code
		UnloadDiffReportEntity tempEntity = this.unloadDiffReportDao.queryUnloadDiffReportBasicInfoById(reportId);
		com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskDto unloadTaskDto = unloadTaskQueryService.getUnloadTaskByUnloadTaskNo(tempEntity.getUnloadTaskNo());
		String unloadOrgCode = tempEntity.getOrgCode();
		
		//如果为二程接驳卸车类型，则调用上一环节（装车部门）部门的外场codes
		if(StringUtils.equals(tempEntity.getUnloadType(), UnloadConstants.UNLOAD_TASK_TYPE_SCEXPRESS)){
			//获取上一环节部门，即装车部门
			OrgAdministrativeInfoEntity loadOrg  = null;
			try{
				loadOrg = this.queryPreviousOrgForUnloadDiff(tempEntity);
			}catch(BusinessException e){
				LOGGER.error("处理卸车差异报告时发生异常，差异报告ID：" + reportId + "异常信息：" + e.getMessage());
				return FossConstants.FAILURE;
			}
			 unloadOrgCode = loadOrg.getCode();
		}
		//获取差异报告明细
		List<UnloadDiffReportDetailEntity> reportDetailList = this.getReportDetailList(reportId, tempEntity.getUnloadType(), unloadOrgCode, tempEntity.getLackGoodsPieces());
		
		//初始化分组使用的运单号
		String tempWaybillNo = "";
		//根据运单号分组
		for(UnloadDiffReportDetailEntity reportDetail : reportDetailList){
			//ISSUE-3474
			//对于没有处理且未上报OA差错的卸车少货明细，需查看是否在生成此明细后是否存在入库记录，如果存在，则自动标记为已处理.
			//FOSS上报OA时会处理，且后面也会查询，在则不会加入list中
		/*	if(StringUtils.equals(UnloadConstants.UNLOAD_EXCEPTION_TYPE_LACKGOODS, reportDetail.getDiffType())) {
				this.handleDifferDetail(reportDetail,unloadTaskDto.getUnloadStartTime());
			}*/
			
			UnloadDiffReportDto unloadDiffReportDto = new UnloadDiffReportDto();
			if(!StringUtils.equals(tempWaybillNo, reportDetail.getWaybillNo())){
				tempWaybillNo = reportDetail.getWaybillNo();
				//校验运单号是否已经作废
				if(unloadLackDiffReportDao.isInvalidWaybillNo(tempWaybillNo)){
					continue;
				}
				waybillNoMap.put(tempWaybillNo, unloadDiffReportDto);
			} else {
				unloadDiffReportDto = waybillNoMap.get(reportDetail.getWaybillNo());
			}
			//判断是否进行卸车扫描、单票入库、清仓扫描
			if(unloadLackDiffReportDao.isSanAndSignleInStockLessGoods(reportDetail.getWaybillNo()))
			{
				continue;
			}
			//添加多货少货流水号
			if(StringUtils.equals(UnloadConstants.UNLOAD_EXCEPTION_TYPE_MOREGOODS, reportDetail.getDiffType())){
				//多货不处理
			}else if(StringUtils.equals(UnloadConstants.UNLOAD_EXCEPTION_TYPE_BYHANDGOODS, reportDetail.getDiffType())){
				//流水号是否无效
				if(unloadLackDiffReportDao.isInvalidSerialNo(tempWaybillNo, reportDetail.getSerialNo())){
					continue;
				}
				//流水号是否签收
				if(unloadLackDiffReportDao.isSignedWaybillNoSerialNo(tempWaybillNo, reportDetail.getSerialNo())){
					continue;
				}
				//是否装车扫描
				if(unloadLackDiffReportDao.isLoadDataWaybillNo(reportDetail)){
					continue;
				}
				//如果为手输差异不用判断是否入库，直接上报
				unloadDiffReportDto.getLessGoodsList().add(reportDetail);
			}else{
				//ISSUE-3474
				//查询生成明细之后是否有入库记录
				List<InOutStockEntity> inOutStocks = stockService.queryInStockInfo(tempWaybillNo, reportDetail.getSerialNo(), unloadOrgCode, unloadTaskDto.getUnloadStartTime());
				if(CollectionUtils.isEmpty(inOutStocks)) {
					//为空说明无入库记录
					//add by wwb for 相关校验，无效、作废、中止、卸车差异报告后有装车扫描、签收
					//流水号是否无效
					if(unloadLackDiffReportDao.isInvalidSerialNo(tempWaybillNo, reportDetail.getSerialNo())){
						continue;
					}
					//流水号是否签收
					if(unloadLackDiffReportDao.isSignedWaybillNoSerialNo(tempWaybillNo, reportDetail.getSerialNo())){
						continue;
					}
					//是否装车扫描
					if(unloadLackDiffReportDao.isLoadDataWaybillNo(reportDetail)){
						continue;
					}
					//累加少货明细
					unloadDiffReportDto.getLessGoodsList().add(reportDetail);
				}
			}
		}
		
		//遍历每个运单 上报QMS
		int count = 0;
		for(Map.Entry<String, UnloadDiffReportDto> entry : waybillNoMap.entrySet()){
			String waybillNo = entry.getKey();
			UnloadDiffReportDto unloadDiffReportDto = entry.getValue();
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
			//如果运单返回为空
			if(waybillEntity == null){
				LOGGER.error("运单【" + waybillNo + "】被删除，无法上报qms！");
				continue;
			}
			
//			LostWarningDataEntity bean = null;
//			List<LostWarningDataEntity> list = new ArrayList<LostWarningDataEntity>();
			
			/*
			 * 上报少货
			 */
			if(CollectionUtils.isNotEmpty(unloadDiffReportDto.getLessGoodsList())){
//				bean = new LostWarningDataEntity();
				//调用qms接口，上报qms少货差错单
				//获取少货的装车部门
				UnloadDiffReportDetailEntity detail = unloadDiffReportDto.getLessGoodsList().get(0);
				List<UnloadWaybillDetailDto> dtoList = unloadTaskService.queryLackGoodsBillNoByWaybillNoAndSerialNo(reportId, detail.getWaybillNo(), detail.getSerialNo());
				OrgAdministrativeInfoEntity pOrg = null;
				if(CollectionUtils.isNotEmpty(dtoList)){
					UnloadWaybillDetailDto dto = dtoList.get(0);
					//如果为交接单
					if(StringUtils.equals(UnloadConstants.BILL_TYPE_HANDOVER, dto.getBillType())){
						//调用交接单服务，获取出发部门
						HandOverBillEntity lHandOverBill = handOverBillService.queryHandOverBillByNo(dto.getBillNo());
						//出发部门code
						String departureCode = lHandOverBill.getDepartDeptCode();
						pOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(departureCode);
					}else if(StringUtils.equals(UnloadConstants.BILL_TYPE_VEHICLEASSEMBLE, dto.getBillType())){
						//调用配载单，获取出发部门
						List<VehicleAssembleBillEntity> lVehicleAssembleBillList = vehicleAssembleBillService.queryVehicleAssembleBillByNo(dto.getBillNo());
						//出发部门code
						String departureCode = lVehicleAssembleBillList.get(0).getOrigOrgCode();
						pOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(departureCode);
					}else if(StringUtils.equals(UnloadConstants.BILL_TYPE_PICKUP, dto.getBillType())) {
						//接送货交接单，接货单创建人所在部门
						String pickUperOrgCode = unloadDiffReportDao.queryPickUperOrgCode(dto.getBillNo());
						pOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(pickUperOrgCode);
					}else if(StringUtils.equals(UnloadConstants.BILL_TYPE_SCBILL, dto.getBillType())){
						//二程接驳交接单
						//出发部门code
						//调用交接单服务，获取出发部门
						ConnectionBillEntity connectionBill=connectionBillService.queryConnectionBillByNo(dto.getBillNo());						//出发部门code
						String departureCode = connectionBill.getDepartDeptCode();
						pOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(departureCode);
						
					}
					if(pOrg == null){
						LOGGER.error("获取出发部门出现错误，为空， 运单号：【{}】, 流水号：【{}】", detail.getWaybillNo(), detail.getSerialNo());
						continue;
					}
				}else{
					LOGGER.error("获取少货运单所在单据为空，差异编号：" +  reportId + "，运单号：" + detail.getWaybillNo() + "，流水号：" + detail.getSerialNo());
					continue;
				}
				
				ResponseDto responseDto = this.reportOAUnloadLessGoods(waybillEntity, unloadDiffReportDto,unloadDiffReportDto.getLessGoodsList(),tempEntity,pOrg,tempEntity.getUnloadType());
				LOGGER.error("上报qms卸车少货，报告ID：" + reportId + ",运单号：" + waybillNo + "qms返回信息：失败原因（" + responseDto.getFailureReason() + "），qms差错单号：（" + responseDto.getErrorsNo() + "）");
				if(StringUtils.isNotBlank(responseDto.getErrorsNo())){
					updateOAErrorNoAndAddLackGoodsFoundInfo(unloadDiffReportDto.getLessGoodsList(), tempEntity, responseDto.getErrorsNo(),new Date());
				} else {
					//失败统计下失败数
					count++;
					//保存上报失败原因
					this.saveLessFailureReason(responseDto, tempEntity.getDiffReportNo(), waybillNo, unloadDiffReportDto.getLessGoodsList(),TransferConstants.GOODS_STATUS_LACK);
				}
			}
		}
		//如果明细不为空，且并非全部报错,说明已经执行了，将卸车差异报告id存入临时表
		if(CollectionUtils.isNotEmpty(reportDetailList) && count != waybillNoMap.size()){
			//插入临时表数据
			unloadLackDiffReportDao.addUnloadDiffReportReportIdTemp(reportId);
		}
//		this.updateHandleState(reportId);
		return FossConstants.SUCCESS;
	}
	
	/**
	 * @author nly
	 * @date 2014年12月12日 上午11:00:41
	 * @function 获取需上报的差异明细list
	 * @param reportId
	 * @param unloadType
	 * @param unloadOrgCode
	 * @return
	 */
	private List<UnloadDiffReportDetailEntity> getReportDetailList(String reportId,String unloadType,String unloadOrgCode, int lackGoodsPieces){
		
		/*
		 * 读取配置参数中卸车少货上报QMS的时限
		 */
		ConfigurationParamsEntity entityDuration = configurationParamsService.queryConfigurationParamsByOrgCode(
																					DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
																					ConfigurationParamsConstants.TFR_PARM__UNLOAD_EXC_TIME_LIMIT_REPORT_OA_ERROR,
																					unloadOrgCode);
		BigDecimal timeLimit = BigDecimal.ZERO;//初始为0
		if (entityDuration != null && StringUtils.isNotEmpty(entityDuration.getConfValue())) {
			timeLimit = new BigDecimal(entityDuration.getConfValue());
		} 
		
		LOGGER.error("unloadType值为:【{}】",unloadType);
		//获取差异报告明细
		List<UnloadDiffReportDetailEntity> reportDetailList = new ArrayList<UnloadDiffReportDetailEntity>();
		//长短途卸车，二程接驳卸车
		if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE)
				|| StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_SHORT_DISTANCE)
				||StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_SCEXPRESS)
				) {
			//不再更新
			int unresolvedCount = unloadLackDiffReportDao.queryUnResolvedDiffReportDetailCount(reportId);
			if(unresolvedCount == 0) {
				/*unloadLackDiffReportDao.updateUnloadDiffReportReportOa(reportId);
				//插入已上报的id到临时表
				unloadLackDiffReportDao.addUnloadDiffReportReportIdTemp(reportId);*/
				return reportDetailList;
			}
			reportDetailList = unloadLackDiffReportDao.queryUnResolvedDiffReportDetail(reportId, timeLimit);
		}else if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_DELIVER)) {
			LOGGER.error("DELIVER类型的reportId值为:【{}】 , lackGoodsPieces值为:【{}】",reportId, lackGoodsPieces);
			//接送货卸车，只需上报OA少货，不上报多货；快递、整车单不上报
			//判断差异中是否存在少货差异，若无则不需要上报丢货，则更新NEED_REPORT_OA为L
			if(lackGoodsPieces == 0) {
			/*	unloadLackDiffReportDao.updateUnloadDiffReportReportOa(reportId);
				//插入已上报的id到临时表
				unloadLackDiffReportDao.addUnloadDiffReportReportIdTemp(reportId);*/
				return reportDetailList; 
			} else {
				//非快递、非整车单少货差异均被处理
				int unresolveCount = unloadLackDiffReportDao.queryUnResolvedLackDetailCount(reportId);
				if(unresolveCount == 0) {
				/*	unloadLackDiffReportDao.updateUnloadDiffReportReportOa(reportId);
					//插入已上报的id到临时表
					unloadLackDiffReportDao.addUnloadDiffReportReportIdTemp(reportId);*/
					return reportDetailList;
				}
			}
			reportDetailList = unloadLackDiffReportDao.queryUnResolvedLackDetail(reportId, timeLimit);
		}
		return reportDetailList;
	}
	/**
	 * @author nly
	 * @date 2014年12月11日 下午1:01:02
	 * @function 处理卸车差异明细
	 * @param reportDetail
	 * @param waybillNo
	 * @param serialNo
	 * @param unloadStartTime
	 */
	private void handleDifferDetail(UnloadDiffReportDetailEntity reportDetail,Date unloadStartTime) {
		//少货
		List<InOutStockEntity> inOutStocks = stockService.queryInStockInfo(reportDetail.getWaybillNo(), reportDetail.getSerialNo(), null, unloadStartTime);
		if(CollectionUtils.isNotEmpty(inOutStocks)) {
			//如果有入库记录则不算少货
			//更新差异报告明细为已处理
			InOutStockEntity inOutStock = inOutStocks.get(0);
			OrgAdministrativeInfoEntity superEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(inOutStock.getOrgCode());
			if(superEntity != null) {
				reportDetail.setHandleOrgCode(superEntity.getCode());
				reportDetail.setHandleOrgName(superEntity.getName());
			} else {
				reportDetail.setHandleOrgCode(inOutStock.getOrgCode());
				reportDetail.setHandleOrgName(inOutStock.getOrgCode());
			}
			reportDetail.setHandlerCode(inOutStock.getOperatorCode());
			reportDetail.setHandlerName(inOutStock.getOperatorName());
			reportDetail.setNote("此货已入库, FOSS自动处理!");
			reportDetail.setExceptionHandleTime(new Date());
			unloadDiffReportDao.updateUnloadDiffReportDetail(reportDetail);
		}
	}
	
	/**
	 * @author nly
	 * @date 2014年12月11日 下午12:43:10
	 * @function 保存卸车上报OA差错失败原因
	 * @param responseDto
	 * @param differReportNo
	 * @param waybillNo
	 * @param differGoodsList
	 */
	private void saveLessFailureReason(ResponseDto responseDto,String differReportNo,String waybillNo,List<UnloadDiffReportDetailEntity> differGoodsList,String type) {

		String msg = responseDto.getMessage();//失败原因
		String failure = responseDto.getFailureReason();
		//DB字段长度1000，oracle一个汉字长度为3，保证长度在300以内
		if(StringUtils.isNotEmpty(msg) && msg.length() > LostWarningConstant.SONAR_NUMBER_150) {
				msg = msg.substring(0,LostWarningConstant.SONAR_NUMBER_150);
			}
		
		if(StringUtils.isNotEmpty(failure) && failure.length() > LostWarningConstant.SONAR_NUMBER_150) {
			failure = failure.substring(0,LostWarningConstant.SONAR_NUMBER_150);
		}
		//保存上报失败日志
		this.addReportOaErrorLog(differReportNo,waybillNo,TransferConstants.REPORT_TYPE_UNLOAD,type,"Msg:"+msg+";FailureReason:"+failure);
		//更新备注信息，代码异常时不更新备注
		/*if(!responseDto.getIsException()  && StringUtils.isNotEmpty(msg)) {
			for(UnloadDiffReportDetailEntity goodsReportDetail : differGoodsList){
				//获取oa差错单编号
				goodsReportDetail.setNote(msg);
				//更新差错明细信息
				unloadDiffReportDao.updateUnloadDiffReportDetail(goodsReportDetail);
			}
		}*/
	
	}
	
	/**
	 * @author nly
	 * @date 2014年12月11日 下午12:22:50
	 * @function 更新卸车差异报告的处理状态
	 * @param reportId
	 */
	private void updateHandleState(String reportId) {
		//如果没有未处理的少货异常，则更新报告状态为已处理
		UnloadDiffReportDetailEntity queryCondition = new UnloadDiffReportDetailEntity();
		//差异报告ID
		queryCondition.setDiffReportId(reportId);
		List<UnloadDiffReportDetailEntity> unresolvedDetailEntityList = unloadDiffReportDao.queryUnresolvedLackGoodsException(queryCondition);
		if(CollectionUtils.isEmpty(unresolvedDetailEntityList)){
			//记录日志
			LOGGER.error(queryCondition.getDiffReportId() + "（卸车差异报告ID）多货差错上报完毕，更新差异报告处理状态为“已处理”！");
			//更新差异报告处理状态为已处理
			unloadDiffReportDao.updateUnloadDiffReportHandleState(reportId, UnloadConstants.UNLOAD_DIFF_HANDLE_STATE_RESOLVED);
		}		
	}
	
	/**
	 * 上报OA少货
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-27 上午11:33:25
	 */
	private ResponseDto reportOAUnloadLessGoods(WaybillEntity waybillEntity, UnloadDiffReportDto unloadDiffReportDto,List<UnloadDiffReportDetailEntity> lessGoodsList,UnloadDiffReportEntity diffEntity,OrgAdministrativeInfoEntity loadOrg,String unloadType){
		OaReportClearless oaReportClearless = new OaReportClearless();
		StringBuffer responsibleOrgCode = new StringBuffer();
		StringBuffer responsibleOrgName = new StringBuffer();
		StringBuffer finalSysCode = new StringBuffer();
		List<String> bizTypesList = new ArrayList<String>();
		//事业部类型
		bizTypesList.add(BizTypeConstants.ORG_DIVISION);
		//查询上级部门
		
		//装车部门
		if(loadOrg != null){
			//装车部门名称
			oaReportClearless.setPreviousclDept(loadOrg.getName());
			//责任部门
			responsibleOrgName.append(loadOrg.getName());
			responsibleOrgName.append(",");
			//装车部门标杆编码
			oaReportClearless.setPreviousclDeptId(loadOrg.getUnifiedCode());
			//责任部门编码
			responsibleOrgCode.append(loadOrg.getUnifiedCode());
			responsibleOrgCode.append(",");
			//责任事业部编码
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
					queryOrgAdministrativeInfoByCode(loadOrg.getCode(), bizTypesList);
			if(orgAdministrativeInfoEntity != null){
				finalSysCode.append(orgAdministrativeInfoEntity.getName());
				finalSysCode.append(",");
			}else{
				LOGGER.warn("上报OA少货：查询部门【" + loadOrg.getCode() + "】的上一级事业部失败！");
			}
			
		}
		//获取上报人（即卸车任务的创建人）工号、工号
		OrgAdministrativeInfoEntity unloadEntity = null;
		if(diffEntity != null){
			//卸车任务ID
			String unloadTaskId = diffEntity.getUnloadTaskId();
			//车牌号
			oaReportClearless.setCarNumber(diffEntity.getVehicleNo());
			//获取卸车任务创建人
			List<LoaderParticipationEntity> loaderEntityList = this.unloadTaskService.queryTaskCreatorLoaderByTaskId(unloadTaskId);
			if(CollectionUtils.isNotEmpty(loaderEntityList)){
				LoaderParticipationEntity loader = loaderEntityList.get(0);
				//上报人code
				oaReportClearless.setUserId(loader.getLoaderCode());
			}
			//卸车部门
			unloadEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(diffEntity.getOrgCode());
			if(unloadEntity != null){
				//卸车部门name
				oaReportClearless.setUnloadDept(unloadEntity.getName());
				//责任部门
				responsibleOrgName.append(unloadEntity.getName());
				//卸车部门标杆编码
				oaReportClearless.setUnloadDeptId(unloadEntity.getUnifiedCode());
				//责任部门编码
				responsibleOrgCode.append(unloadEntity.getUnifiedCode());
				//责任事业部编码
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
						queryOrgAdministrativeInfoByCode(unloadEntity.getCode(), bizTypesList);
				if(orgAdministrativeInfoEntity != null){
					finalSysCode.append(orgAdministrativeInfoEntity.getName());
				}else{
					LOGGER.warn("上报OA少货：查询部门【" + loadOrg.getCode() + "】的上一级事业部失败！");
				}
			}
		}
		//责任部门
		oaReportClearless.setResponsibleDeptId(responsibleOrgCode.toString());
		oaReportClearless.setResponsibleDept(responsibleOrgName.toString());
		oaReportClearless.setFinalSysCode(finalSysCode.toString());
		//运单号
		oaReportClearless.setWayBillId(waybillEntity.getWaybillNo());
		//上报时间
		oaReportClearless.setReportTime(new Date());
		/*
		 * 运输类型
		 */
		DataDictionaryValueEntity dictEntity1 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.BSE_TRANS_TYPE, waybillEntity.getTransportType());
		if(dictEntity1 == null){
			//运输类型为空时，根据运输性质查询对应的一级产品
			ProductEntity entity = productService.getProductLele(waybillEntity.getProductCode(),null,1);
			if(entity != null) {
				oaReportClearless.setTransportType(entity.getName());
			}
		}else{
			//运输类型
			oaReportClearless.setTransportType(dictEntity1.getValueName());
		}
		/*
		 * 返单类别
		 */
		DataDictionaryValueEntity dictEntity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.RETURN_BILL_TYPE, waybillEntity.getReturnBillType());
		if(dictEntity == null){
			//数据字段为空，则设置为常量
			oaReportClearless.setReturnBillType(UNKNOWN_TYPE);
		}else{
			oaReportClearless.setReturnBillType(dictEntity.getValueName());
		}
		//托运人
		oaReportClearless.setShipper(waybillEntity.getDeliveryCustomerContact());       
		/*
		 * 运输性质
		 */
		oaReportClearless.setTransportProduct(productService.getProductByCache(waybillEntity.getProductCode(), null).getName());	//产品类型
		/*
		 * 配载类型
		 */
		DataDictionaryValueEntity dictEntity2 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.ASSEMBLE_TYPE, waybillEntity.getLoadMethod());
		if(dictEntity2 == null){
			//数据字段为空，则设置为常量
			oaReportClearless.setStowageType(UNKNOWN_TYPE);
		}else{
			//配载类型
			oaReportClearless.setStowageType(dictEntity2.getValueName());
		}
		/*
		 * 收货人电话
		 */
		if(StringUtils.isBlank(waybillEntity.getReceiveCustomerPhone())){
			//若收货人电话为空，则传收货人手机
			oaReportClearless.setReceiverTel(waybillEntity.getReceiveCustomerMobilephone());   
		}else{
			//若收货人电话不为空，则传收货人电话
			oaReportClearless.setReceiverTel(waybillEntity.getReceiveCustomerPhone());
		}
		/*
		 * 提货方式
		 */
		DataDictionaryValueEntity dictEntity3 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.PICKUP_GOODS, waybillEntity.getReceiveMethod());
		if(dictEntity3 == null){
			//数据字段为空，则设置为常量
			oaReportClearless.setGroupSendFlag(UNKNOWN_TYPE);
		}else{
			//提货方式
			oaReportClearless.setGroupSendFlag(dictEntity3.getValueName());
		}
		//储运事项
		oaReportClearless.setRemark(waybillEntity.getTransportationRemark());        
		//重量
		if(null != waybillEntity.getGoodsWeightTotal()) {
			oaReportClearless.setWeight(waybillEntity.getGoodsWeightTotal().doubleValue());
		} else {
			oaReportClearless.setWeight(Double.valueOf(0));
		}
		//体积
		if(null != waybillEntity.getGoodsVolumeTotal()) {
			oaReportClearless.setVloume(waybillEntity.getGoodsVolumeTotal().doubleValue());
		} else {
			oaReportClearless.setVloume(Double.valueOf(0));
		}
		//货物名称
		oaReportClearless.setGoods(waybillEntity.getGoodsName());          
		//发货时间
		oaReportClearless.setSendTime(DateUtils.convert(waybillEntity.getBillTime(), DateUtils.DATE_TIME_FORMAT));
		/*
		 * 目的站
		 */
		OrgAdministrativeInfoEntity destEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCustomerPickupOrgCode());
		if(destEntity != null){
			oaReportClearless.setDestination(destEntity.getName());
		}
		//收货人
		oaReportClearless.setReceiver(waybillEntity.getReceiveCustomerContact());       
		//收货部门
		OrgAdministrativeInfoEntity recieveEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getReceiveOrgCode());
		if(recieveEntity != null){
			//收货部门标杆编码
			oaReportClearless.setReceivingDeptID(recieveEntity.getUnifiedCode());
			//收货部门name
			oaReportClearless.setReceivingDept(recieveEntity.getName());
		}
		/*
		 * 付款方式
		 */
		DataDictionaryValueEntity dictEntity4 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE, waybillEntity.getPaidMethod());
		if(dictEntity4 == null){
			//数据字段为空，则设置为常量
			oaReportClearless.setPayType(UNKNOWN_TYPE);
		}else{
			//付款方式
			oaReportClearless.setPayType(dictEntity4.getValueName());
		}
		//保险金额
		if(null != waybillEntity.getInsuranceFee()) {
			oaReportClearless.setInsuranceMoney(waybillEntity.getInsuranceFee());        
		} else {
			oaReportClearless.setInsuranceMoney(BigDecimal.valueOf(0));
		}
		//包装
		oaReportClearless.setGoodsPacking(waybillEntity.getGoodsPackage());          
		//运费总额
		oaReportClearless.setTotal(waybillEntity.getTotalFee());          
		//件数
		oaReportClearless.setGoodsCount(waybillEntity.getGoodsQtyTotal());          
		//少货件数
		oaReportClearless.setNogoodscount(lessGoodsList.size());
		StringBuffer serialNoSb = new StringBuffer();
		/*
		 * 事件经过
		 */
		//OA表设计中，事件经过最多放置650，故此处做处理
		String event="";
		if(StringUtils.equals(diffEntity.getUnloadType(), UnloadConstants.UNLOAD_TASK_TYPE_SCEXPRESS)){
			//如果为二程接驳卸车
			event="自动差错上报，二程接驳，司机卸车少货"+"；流水号：" + serialNoSb.toString();
		}else{ 
			event = UnloadConstants.UNLOAD_LESS_GOODS_REPORT_OA_EVENT + "；流水号：" + serialNoSb.toString();
		}
		if(event.length() > LostWarningConstant.SONAR_NUMBER_650){
			event = event.substring(0, LostWarningConstant.SONAR_NUMBER_650) + "....";
		}
		oaReportClearless.setEventReport(event);
		//少货类型
		oaReportClearless.setLostGoodsType(this.queryLessGoodsType(unloadType, loadOrg, unloadEntity));
		//开单部门
		OrgAdministrativeInfoEntity createEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCreateOrgCode());
		if(createEntity != null){
			//开单部门标杆编码
			oaReportClearless.setSheetDeptId(createEntity.getUnifiedCode());
			//开单部门名称
			oaReportClearless.setSheetDept(createEntity.getName());
		}
		//交接单号
		String handOverBillNos = this.queryHandOverBillNoForLackGoods(diffEntity, loadOrg, diffEntity.getOrgCode(), waybillEntity.getWaybillNo());
		oaReportClearless.setReplayBill(handOverBillNos);
		//发货客户编码
		oaReportClearless.setDeliveryCustomerCode(waybillEntity.getDeliveryCustomerCode());
		//收货客户编码
		oaReportClearless.setReceiveCustomerCode(waybillEntity.getReceiveCustomerCode());
		//差异流水号
		StringBuffer s = new StringBuffer(LostWarningConstant.SONAR_NUMBER_100);
		for(UnloadDiffReportDetailEntity entity: lessGoodsList){
			s.append(entity.getSerialNo()).append(",");
		}
		if(null != s && s.length() > 0){
			oaReportClearless.setSerialNoList(s.substring(0, s.length()-1));
		}
		//业务渠道
		oaReportClearless.setBusinessChannel(this.queryBusinessChannel(unloadType, loadOrg, unloadEntity));
		
		/*** 上报到丢货预警系统 ***/
		ResponseDto dto = lostWarningAnalyDataForLack.analyWarningData_unloadData(oaReportClearless, loadOrg, unloadEntity);
		return dto;
	}
	
	/**
	 * @author nly
	 * @date 2014年12月17日 上午10:58:00
	 * @function 获取丢货类别
	 * @param unloadType
	 * @param loadOrg
	 * @param unloadOrg
	 * @return
	 */
	private String queryLessGoodsType(String unloadType,OrgAdministrativeInfoEntity loadOrg,OrgAdministrativeInfoEntity unloadOrg) {
		if(StringUtils.equals(UnloadConstants.UNLOAD_TASK_TYPE_DELIVER, unloadType)) {
			return UnloadConstants.DELIVER_UNLOAD_LESS_GOODS;
		} else if (StringUtils.equals(UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE, unloadType)) {
			return UnloadConstants.TRANSFERCENTER_TRANSFERCENTER_UNLOAD_LESS_GOODS;
		} else if(StringUtils.equals(UnloadConstants.UNLOAD_TASK_TYPE_SHORT_DISTANCE, unloadType)) {
			if(null != loadOrg && null != unloadOrg) {
				if(StringUtils.equals("Y", loadOrg.getSalesDepartment())
						|| StringUtils.equals("Y", unloadOrg.getTransferCenter())) {
					return UnloadConstants.SALEDEPARTMENT_TRANSFERCENTER_UNLOAD_LESS_GOODS;
				}
				if(StringUtils.equals("Y", loadOrg.getTransferCenter())
						|| StringUtils.equals("Y", unloadOrg.getSalesDepartment())){
					return UnloadConstants.TRANSFERCENTER_SALEDEPARTMENT_UNLOAD_LESS_GOODS;
				}
			}
		}else if(StringUtils.equals(UnloadConstants.UNLOAD_TASK_TYPE_SCEXPRESS, unloadType)){
			
			return UnloadConstants.TRANSFERCENTER_SALEDEPARTMENT_UNLOAD_LESS_GOODS;
		}
		return "";
	}
	/**
	 * @author nly
	 * @date 2014年12月17日 上午10:16:11
	 * @function 获取业务渠道
	 * @param loadOrgCode
	 * @param unloadOrgCode
	 * @return
	 */
	private String queryBusinessChannel(String unloadType,OrgAdministrativeInfoEntity loadOrg,OrgAdministrativeInfoEntity unloadOrg) {
		StringBuffer businessChannel = new StringBuffer();
		if(null == loadOrg) {
			businessChannel.append("");
		} else if(StringUtils.equals("Y", loadOrg.getSalesDepartment())){
			businessChannel.append(UnloadConstants.SALEDEPARTMENT_BUSINESS_CHANNEL);
		}  else if(StringUtils.equals(UnloadConstants.UNLOAD_TASK_TYPE_DELIVER, unloadType)
				&& StringUtils.equals("Y", loadOrg.getTransDepartment())) {
			//集中接货装车部门，既是外场又是车队时，业务渠道为“车队”
			businessChannel.append(UnloadConstants.TRANSDEPARTMENT_BUSINESS_CHANNEL);	
		}else if(StringUtils.equals("Y", loadOrg.getTransferCenter())) {
			businessChannel.append(this.getTransferChannel(loadOrg.getCode()));
		}else {
			businessChannel.append("");
		}
		
		businessChannel.append(",");
		
		if(null == unloadOrg) {
			businessChannel.append("");
		} else if(StringUtils.equals("Y", unloadOrg.getSalesDepartment())){
			businessChannel.append(UnloadConstants.SALEDEPARTMENT_BUSINESS_CHANNEL);
		} else if(StringUtils.equals("Y", unloadOrg.getTransferCenter())) {
			businessChannel.append(this.getTransferChannel(unloadOrg.getCode()));
		} else {
			businessChannel.append("");
		}
		return businessChannel.toString();
	}
	
	/**
	 * @author nly
	 * @date 2015年1月4日 上午8:54:10
	 * @function 获取外场对应级别
	 * @param transferCode
	 * @return
	 */
	private String getTransferChannel(String transferCode) {
		String transferChannel = UnloadConstants.TRANSFERCENTER_BUSINESS_CHANNEL;
		OutfieldEntity outfieldEntity= outfieldService.queryOutfieldByOrgCode(transferCode);
		if(null != outfieldEntity && StringUtils.isNotEmpty(outfieldEntity.getTransferServiceChannel())) {
			DataDictionaryValueEntity dictEntity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.TRANSFER_SERVICE_CHANNEL, outfieldEntity.getTransferServiceChannel());
			if(null != dictEntity && StringUtils.isNotEmpty(dictEntity.getValueName())) {
				transferChannel = dictEntity.getValueName();
				return transferChannel;
			} else {
				return transferChannel;
			}
		} else {
			return transferChannel;
		}
	}
	
	/**
	 * 上报少货差错时，获取运单所在的交接单
	 * @author 045923-foss-shiwei
	 * @date 2013-2-28 下午7:21:40
	 */
	private String queryHandOverBillNoForLackGoods(UnloadDiffReportEntity tempEntity, OrgAdministrativeInfoEntity loadOrg,String unloadOrgCode,String waybillNo){
		/**
		 * 参数均不能为空
		 */
		if(tempEntity == null
				|| loadOrg == null
				|| StringUtils.isBlank(unloadOrgCode)
				|| StringUtils.isBlank(waybillNo)){
			return null;
		}
		StringBuffer noList = new StringBuffer();
		List<String> handOverBillNoList = new ArrayList<String>();
		//卸车任务id
		String unloadTaskId = tempEntity.getUnloadTaskId();
		//获取卸车任务实体
		UnloadTaskEntity unloadTask = this.unloadTaskService.queryUnloadTaskBaseInfoById(unloadTaskId);
		//任务类型（长短途）
		String unloadType = unloadTask.getUnloadType();
		//获取卸车任务下的单据列表
		List<UnloadBillDetailEntity> unloadBillList = this.unloadTaskService.queryUnloadTaskBillDetailListById(unloadTaskId);
		//如果为短途卸车或接送货卸车
		if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_SHORT_DISTANCE)
				|| StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_DELIVER)
				|| StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_SCEXPRESS)){
			for(UnloadBillDetailEntity unloadBillEntity : unloadBillList){
				handOverBillNoList.add(unloadBillEntity.getBillNo());
			}
		}else if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE)){
			//循环调用配载单服务，收集所有的交接单号
			for(UnloadBillDetailEntity unloadBillEntity : unloadBillList){
				String vehicleAssembleNo = unloadBillEntity.getBillNo();
				//获取配载单下的所有交接单
				List<QueryHandOverBillDto> handOverBillList = this.vehicleAssembleBillService.queryHandOverBillListByVNo(vehicleAssembleNo);
				for(QueryHandOverBillDto handOverBillDto : handOverBillList){
					handOverBillNoList.add(handOverBillDto.getHandOverBillNo());
				}
			}
		}else{
			LOGGER.error("卸车任务（" + tempEntity.getUnloadTaskNo() + "）类型未知！");
		}
		
		List<String> rightNoList = new ArrayList<String>();
		if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_DELIVER)) {
			rightNoList = unloadDiffReportDao.queryPickUpHandOverNo(waybillNo,handOverBillNoList);
		}else if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_SCEXPRESS)){
			/*
			 * 二程接驳卸车
			 * 调用交接单服务，获取符合条件的交接单号
			 */
			QueryHandOverBillNoForUnloadTaskLackGoodsDto queryDto = new QueryHandOverBillNoForUnloadTaskLackGoodsDto();
			//出发部门code
			queryDto.setDepartOrgCode(loadOrg.getCode());
			//卸车任务中交接单号list
			queryDto.setUnloadedHandOverBillNoList(handOverBillNoList);
			//运单号
			queryDto.setWaybillNo(waybillNo);
			rightNoList=this.connectionBillService.queryConnectionBillNoForUnloadTaskLackGoods(queryDto);
		} else {
			/*
			 * 调用交接单服务，获取符合条件的交接单号
			 */
			QueryHandOverBillNoForUnloadTaskLackGoodsDto queryDto = new QueryHandOverBillNoForUnloadTaskLackGoodsDto();
			//出发部门code
			queryDto.setDepartOrgCode(loadOrg.getCode());
			//到达部门code
			queryDto.setDestOrgCode(unloadOrgCode);
			//卸车任务中交接单号list
			queryDto.setUnloadedHandOverBillNoList(handOverBillNoList);
			//运单号
			queryDto.setWaybillNo(waybillNo);
			rightNoList = this.handOverBillService.queryHandOverBillNoForUnloadTaskLackGoods(queryDto);
		}
		if(CollectionUtils.isNotEmpty(rightNoList)){
			for(int i = 0;i < rightNoList.size();i++){
				String no = rightNoList.get(i);
				if(i == 0){
					noList.append(no);
				}else{
					noList.append("/");
					noList.append(no);
				}
			}
		}
		return noList.toString();
	}
	
	/**
	 * 上报多货、少货差错时调用该方法获取上一环节部门
	 * @author 045923-foss-shiwei
	 * @date 2013-2-28 下午6:44:46
	 */
		//卸车任务ID
	private OrgAdministrativeInfoEntity queryPreviousOrgForUnloadDiff(UnloadDiffReportEntity tempEntity){
		String unloadTaskId = tempEntity.getUnloadTaskId();
		//获取卸车任务实体
		UnloadTaskEntity unloadTask = this.unloadTaskService.queryUnloadTaskBaseInfoById(unloadTaskId);
		if(unloadTask != null){
			//卸车类型
			String unloadType = unloadTask.getUnloadType();
			//获取卸车任务下的单据
			List<UnloadBillDetailEntity> billList = unloadTaskService.queryUnloadTaskBillDetailListById(unloadTaskId);
			LOGGER.info("billList    :" + billList.size() + "unloadType " + unloadType);
			//如果为长途卸车
			if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE)){
				if(CollectionUtils.isNotEmpty(billList)){
					//获取卸车任务单据明细
					UnloadBillDetailEntity billEntity = billList.get(0);
					//配载车次号
					String vehicleAssembleNo = billEntity.getBillNo();
					//调用配载单服务，获取出发部门code
					List<VehicleAssembleBillEntity> vehicleAssembleBillList = this.vehicleAssembleBillService.queryVehicleAssembleBillByNo(vehicleAssembleNo);
					if(CollectionUtils.isNotEmpty(vehicleAssembleBillList)){
						//出发部门code
						String departOrgCode = vehicleAssembleBillList.get(0).getOrigOrgCode();
						//获取出发部门实体
						OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(departOrgCode);
						return orgEntity;
					}else{
						LOGGER.error("###################根据配载单号" + vehicleAssembleNo + "获取到的配载单为空！");
					}
				}else{
					LOGGER.error("根据卸车任务（编号：" + unloadTask.getUnloadTaskNo() + "）获取任务单据列表为空！");
				}//如果为短途卸车
			}else if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_SHORT_DISTANCE)){
				if(CollectionUtils.isNotEmpty(billList)){
					//获取卸车任务单据明细
					UnloadBillDetailEntity billEntity = billList.get(0);
					//获取交接单号
					String handOverBillNo = billEntity.getBillNo();
					LOGGER.info("handOverBillService的值为【{}】", handOverBillService);
					//调用交接单服务，获取出发部门code
					HandOverBillEntity handOverBill = this.handOverBillService.queryHandOverBillByNo(handOverBillNo);
					LOGGER.error("handOverBill的值为【{}】", handOverBill);
					//出发部门code
					String departOrgCode = handOverBill.getDepartDeptCode();
					//获取出发部门实体
					OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(departOrgCode);
					return orgEntity;
				}else{
					LOGGER.error("根据卸车任务（编号：" + unloadTask.getUnloadTaskNo() + "）获取任务单据列表为空！");
				}
			} else if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_SCEXPRESS)){//如果为二程接驳卸车
				if(CollectionUtils.isNotEmpty(billList)){
					//获取卸车任务单据明细
					UnloadBillDetailEntity billEntity = billList.get(0);
					//获取交接单号
					String connectionBillNo = billEntity.getBillNo();
					//调用交接单服务，获取出发部门code
					 ConnectionBillEntity  connectionBill = this.connectionBillService.queryConnectionBillByNo(connectionBillNo);
					//出发部门code
					 LOGGER.error("connectionBill的值为【{}】", connectionBill);
					 String departOrgCode = connectionBill.getDepartDeptCode();
					//获取出发部门实体
					OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(departOrgCode);
					return orgEntity;
				}else{
					LOGGER.error("根据卸车任务（编号：" + unloadTask.getUnloadTaskNo() + "）获取任务单据列表为空！");
				}
			}else{
				LOGGER.error("卸车任务（编号：" + unloadTask.getUnloadTaskNo() + unloadType + "）类型无法识别！");
			}
		}
		return null;
	}
	/**
	 * 更新少货差异报告中的少货报告，同时新增一条少货找到记录(待找到后更新)
	 * @author 045923-foss-shiwei
	 * @date 2013-7-2 下午3:00:57
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadService#updateOAErrorNoAndAddLackGoodsFoundInfo(java.util.List, com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportEntity, java.lang.String)
	 */
	@Override
	@Transactional
	public int updateOAErrorNoAndAddLackGoodsFoundInfo(List<UnloadDiffReportDetailEntity> lessGoodsList,UnloadDiffReportEntity diffEntity, String oaErrorNo,Date reportErrorTime) {
		for(UnloadDiffReportDetailEntity lessGoodsReportDetail : lessGoodsList){
			//获取oa差错单编号
			lessGoodsReportDetail.setOaMistakeBillNo(oaErrorNo);
			//更新差错单明细信息
//			unloadDiffReportDao.updateUnloadDiffReportDetail(lessGoodsReportDetail);
			//插入一条少货找到记录
			LackGoodsFoundEntity lgEntity = new LackGoodsFoundEntity();
			lgEntity.setId(UUIDUtils.getUUID());
			lgEntity.setWaybillNo(lessGoodsReportDetail.getWaybillNo());
			lgEntity.setSerialNo(lessGoodsReportDetail.getSerialNo());
			lgEntity.setOaErrorNo(oaErrorNo);
			lgEntity.setLackGoodsOrgCode(diffEntity.getOrgCode());
			lgEntity.setReportType(TransferConstants.REPORT_TYPE_UNLOAD);
			lgEntity.setReportId(diffEntity.getId());
			lgEntity.setReportOATime(reportErrorTime);
			this.addLackGoodsFoundInfo(lgEntity);
		}
		return FossConstants.SUCCESS;
	}
	
	
	/**
	 * @author niuly
	 * @date 2014-6-9上午9:13:00
	 * @function 保存上报OA差错失败日志
	 * @param reportCode
	 * @param waybillNo
	 * @param failureReason
	 * @param message
	 */
	@Override
	public void addReportOaErrorLog(String reportCode, String waybillNo, String reportType,String differType,String message) {
		ReportOaErrorLogEntity logEntity = new ReportOaErrorLogEntity(); 
		logEntity.setId(UUIDUtils.getUUID());
		logEntity.setReportCode(reportCode);
		logEntity.setWaybillNo(waybillNo);
		logEntity.setReportType(reportType);
		logEntity.setDifferType(differType);
		logEntity.setRemark(message);
		logEntity.setCreateDate(new Date());
		
		unloadLackDiffReportDao.addReportOaErrorLog(logEntity);
	}
	
	/**
	 * 新增一条卸车少货记录
	 * @author 045923-foss-shiwei
	 * @date 2013-7-2 下午3:00:38
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadService#addLackGoodsFoundInfo(com.deppon.foss.module.transfer.unload.api.shared.domain.LackGoodsFoundEntity)
	 */
	@Override
	public int addLackGoodsFoundInfo(LackGoodsFoundEntity entity) {
		return unloadDiffReportDao.addLackGoodsFoundInfo(entity);
	}
	
}
