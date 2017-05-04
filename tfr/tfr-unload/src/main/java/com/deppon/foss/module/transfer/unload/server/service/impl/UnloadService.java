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
package com.deppon.foss.module.transfer.unload.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToOAService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrToQmsErrorService;
import com.deppon.foss.module.transfer.common.api.shared.define.QmsErrorConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.QmsErrRequestParam;
import com.deppon.foss.module.transfer.common.api.shared.domain.QmsErrResponseParam;
import com.deppon.foss.module.transfer.common.api.shared.domain.QmsErrorMainEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.QmsKDErrSubHasWaybillEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.QmsLDErrSubHasWaybillEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportClearMore;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportClearless;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseDto;
import com.deppon.foss.module.transfer.departure.api.server.dao.ISharedDao;
import com.deppon.foss.module.transfer.departure.api.shared.dto.AutoTaskDTO;
import com.deppon.foss.module.transfer.load.api.server.service.IConnectionBillService;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadTaskQueryService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.load.api.shared.domain.ConnectionBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillNoForUnloadTaskLackGoodsDto;
import com.deppon.foss.module.transfer.lostwarning.server.service.impl.LostWarningAnalyData;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao;
import com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadDiffReportDao;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadService;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.LackGoodsFoundEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ReportOaErrorLogEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ReportQMSDataEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadDiffReportDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadSerialDetaiDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadWaybillDetailDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

import javax.annotation.Resource;

import static com.deppon.foss.module.pickup.waybill.server.service.impl.GisQueryService.LOGGER;

/** 
 * @className: UnloadService
 * @author: ShiWei shiwei@outlook.com
 * @description: 卸车service
 * @date: 2013-6-4 下午8:55:21
 * 
 */
public class UnloadService implements IUnloadService{
	
	private IUnloadTaskService unloadTaskService;
	
	private IUnloadDiffReportDao unloadDiffReportDao;
	
	
	private IEmployeeService employeeService;
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
	@Resource
	private IProductService productService4Dubbo;
	/**
	 * 上报差错接口
	 */
	private ITfrToQmsErrorService tfrToQmsErrorService;
	
	/**
	 * 常量，当传给oa的数据字典缺失时，传该常量
	 */
	private static final String UNKNOWN_TYPE = "未知类型";
	
	/** 综合管理 组织信息 Service*/
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 配置参数service
	 */
	private IConfigurationParamsService configurationParamsService;
	
	/** 
	 * 组织接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 库存service，用来少货找到后入库
	 */
	private IStockService stockService;
	
	/** 
	 * 上报接口
	 */
	private IFOSSToOAService fossToOAService;
	
	/**
	 * 交接单service
	 */
	private IHandOverBillService handOverBillService;
	
	/**
	 * 卸车任务查询service
	 */
	private IUnloadTaskQueryService unloadTaskQueryService;
	
	/**
	 * 定义常量字符串，拼接卸车差异报告编号
	 */
	private static final String REPORTNO_POST_FIX = "_c";
	
	/**接驳交接单查询service**/
	private IConnectionBillService connectionBillService;
	
	private IPDAUnloadTaskDao pdaUnloadTaskDao;
	
	private ISharedDao sharedDao;

	private IOutfieldService outfieldService;
	
	private ILoadTaskQueryService loadTaskQueryService;
	
	/**
	 * 丢货预警上报service
	 */
	private LostWarningAnalyData lostWarningAnalyData;
	
	public void setLostWarningAnalyData(LostWarningAnalyData lostWarningAnalyData) {
		this.lostWarningAnalyData = lostWarningAnalyData;
	}
	
	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}

	public void setSharedDao(ISharedDao sharedDao) {
		this.sharedDao = sharedDao;
	}

	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}

	public void setConnectionBillService(
			IConnectionBillService connectionBillService) {
		this.connectionBillService = connectionBillService;
	}

	public void setFossToOAService(IFOSSToOAService fossToOAService) {
		this.fossToOAService = fossToOAService;
	}

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

//	public void setProductService(IProductService productService) {
//		this.productService = productService;
//	}

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

	public void setUnloadTaskService(IUnloadTaskService unloadTaskService) {
		this.unloadTaskService = unloadTaskService;
	}
	
	public IEmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
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
	 * @param pdaUnloadTaskDao the pdaUnloadTaskDao to set
	 * Date:2013-7-29上午11:16:07
	 */
	public void setPdaUnloadTaskDao(IPDAUnloadTaskDao pdaUnloadTaskDao) {
		this.pdaUnloadTaskDao = pdaUnloadTaskDao;
	}

	public void setTfrToQmsErrorService(ITfrToQmsErrorService tfrToQmsErrorService) {
		this.tfrToQmsErrorService = tfrToQmsErrorService;
	}

	/**
	 * 保存卸车差异报告
	 * @author 045923-foss-shiwei
	 * @date 2013-6-4 下午8:56:07
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadService#addUnloadDiffReportBasicInfoAndDetail(com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportEntity, java.util.List)
	 */
	@Override
	@Transactional
	public int addUnloadDiffReportBasicInfoAndDetail(
			UnloadDiffReportEntity baseEntity,
			List<UnloadDiffReportDetailEntity> serialNoList) {
		//生成卸车差异报告编号
		String unloadTaskNo = baseEntity.getUnloadTaskNo();
		//生成卸车差异报告编号
		String unloadTaskDiffReportNo = unloadTaskNo.concat(REPORTNO_POST_FIX);
		//差异报告编号
		baseEntity.setDiffReportNo(unloadTaskDiffReportNo);
		
		//更新卸车任务的“是否生成卸车差异报告”字段为‘Y’
		UnloadTaskEntity task = new UnloadTaskEntity();
		task.setId(baseEntity.getUnloadTaskId());
		task.setBeCreatedGapRep(FossConstants.YES);
		unloadTaskService.updateUnloadTaskBasicInfo(task);
		
		//插入卸车差异报告基本信息
		unloadDiffReportDao.addUnloadDiffReport(baseEntity);
		//循环插入流水号明细
		for(UnloadDiffReportDetailEntity serialNo : serialNoList){
			unloadDiffReportDao.addUnloadDiffReportDetail(serialNo);
		}
		
		//往pkp临时表中插入一条记录
		try{
			AutoTaskDTO dto =  new AutoTaskDTO();
			dto.setId(UUIDUtils.getUUID());
			dto.setTaskDetailId(baseEntity.getId());
			dto.setTaskDetailType("G");
			dto.setStockOrgCode("N/A");
			sharedDao.insertTempForPKP(dto);
		}catch(Exception e){
			LOGGER.info("调用PKP接口sharedDao.insertTempForPKP(dto)发生异常，卸车差异报告ID：" + baseEntity.getId() + "异常信息：" + e.getMessage());
		}
		
		return FossConstants.SUCCESS;
	}

	/**
	 * 上报卸车多货、少货差错(old)
	 * job
	 * @author 045923-foss-shiwei
	 * @date 2013-6-5 下午3:15:01
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadService#executeUnloadDiffReportTask(java.lang.String)
	 */
	@Override
	public int executeUnloadDiffReportTask(String reportId) {
		Map<String, UnloadDiffReportDto> waybillNoMap = new HashMap<String, UnloadDiffReportDto>();
		//获取卸车部门code
		UnloadDiffReportEntity tempEntity = this.unloadDiffReportDao.queryUnloadDiffReportBasicInfoById(reportId);
		com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskDto unloadTaskDto = unloadTaskQueryService.getUnloadTaskByUnloadTaskNo(tempEntity.getUnloadTaskNo());
		String unloadOrgCode = tempEntity.getOrgCode();
		//获取上一环节部门，即装车部门
		OrgAdministrativeInfoEntity loadOrg  = null;
		try{
			loadOrg = this.queryPreviousOrgForUnloadDiff(tempEntity);
		}catch(BusinessException e){
			LOGGER.error("处理卸车差异报告时发生异常，差异报告ID：" + reportId + "异常信息：" + e.getMessage());
			return FossConstants.FAILURE;
		}
		//如果为二程接驳卸车类型，则调用上一环节（装车部门）部门的外场codes
		if(StringUtils.equals(tempEntity.getUnloadType(), UnloadConstants.UNLOAD_TASK_TYPE_SCEXPRESS)){
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
			//更改于2016/06/01，此处不再需要少货
			if(StringUtils.equals(UnloadConstants.UNLOAD_EXCEPTION_TYPE_LACKGOODS, reportDetail.getDiffType())) {
				this.handleDifferDetail(reportDetail,unloadTaskDto.getUnloadStartTime());
			}
			
			UnloadDiffReportDto unloadDiffReportDto = new UnloadDiffReportDto();
			if(!StringUtils.equals(tempWaybillNo, reportDetail.getWaybillNo())){
				tempWaybillNo = reportDetail.getWaybillNo();
				//多货
				waybillNoMap.put(tempWaybillNo, unloadDiffReportDto);
			} else {
				//少货
				//多货
				unloadDiffReportDto = waybillNoMap.get(reportDetail.getWaybillNo());
			}
			//添加多货少货流水号
			if(StringUtils.equals(UnloadConstants.UNLOAD_EXCEPTION_TYPE_MOREGOODS, reportDetail.getDiffType())){
				//累加多货明细
				unloadDiffReportDto.getMoreGoodsList().add(reportDetail);
			}else if(StringUtils.equals(UnloadConstants.UNLOAD_EXCEPTION_TYPE_BYHANDGOODS, reportDetail.getDiffType())){
				//如果为手输差异不用判断是否入库，直接上报
				unloadDiffReportDto.getLessGoodsList().add(reportDetail);
			}else{
				//ISSUE-3474
				//对于没有处理且未上报OA差错的卸车少货明细，需查看是否在生成此明细后是否存在入库记录，如果存在，则自动标记为已处理.
				List<InOutStockEntity> inOutStocks = stockService.queryInStockInfo(tempWaybillNo, reportDetail.getSerialNo(), unloadOrgCode, unloadTaskDto.getUnloadStartTime());
				if(CollectionUtils.isEmpty(inOutStocks)) {
					//为空说明无入库记录
					//累加少货明细
					unloadDiffReportDto.getLessGoodsList().add(reportDetail);
				}
			}
		}
		
		//遍历每个运单 上报qms
		String waybillNo = null;
		for(Map.Entry<String, UnloadDiffReportDto> entry : waybillNoMap.entrySet()){
			waybillNo = entry.getKey();
			UnloadDiffReportDto unloadDiffReportDto = entry.getValue();
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
			//如果运单返回为空
			if(waybillEntity == null){
				LOGGER.error("运单【" + waybillNo + "】被删除，无法上报QMS差错！");
				continue;
			}
			/*
			 * 上报多货
			 */
			try{
				List<UnloadDiffReportDetailEntity> moreGoodsList = unloadDiffReportDto.getMoreGoodsList();
				if(CollectionUtils.isNotEmpty(moreGoodsList)){
					QmsErrResponseParam qmsErrResponseParam = this.reportQMSUnloadMoreGoods(waybillEntity, unloadDiffReportDto,tempEntity,moreGoodsList,loadOrg);
					//因上报接口会返回null
					if(qmsErrResponseParam == null){
						qmsErrResponseParam = new QmsErrResponseParam();
						qmsErrResponseParam.setHandleCode("S000099");
						qmsErrResponseParam.setMessage("系统异常");
						this.saveMoreFailureReason(qmsErrResponseParam, tempEntity.getDiffReportNo(), waybillNo, moreGoodsList,TransferConstants.GOODS_STATUS_SURPLUS);
					}else{
						LOGGER.error("上报QMS卸车多货，报告ID：" + reportId + ",运单号：" + waybillNo + "QMS返回信息：失败原因（" + qmsErrResponseParam.getMessage() + "），QMS差错单号：（" + qmsErrResponseParam.getErrorID() + "）");
						//更新差错明细  qms差错编号
						if(StringUtils.isNotBlank(qmsErrResponseParam.getErrorID())){
							for(UnloadDiffReportDetailEntity moreGoodsReportDetail : moreGoodsList){
								//获取qms差错单编号
								moreGoodsReportDetail.setOaMistakeBillNo(qmsErrResponseParam.getErrorID());
								//更新差错明细信息
								unloadDiffReportDao.updateUnloadDiffReportDetail(moreGoodsReportDetail);
							}
						} else {
							//保存上报失败原因
							this.saveMoreFailureReason(qmsErrResponseParam, tempEntity.getDiffReportNo(), waybillNo, moreGoodsList,TransferConstants.GOODS_STATUS_SURPLUS);
						}
					}
				}
			}catch(Exception e){
				//待优化，将出错信息写入错误日志表
				e.printStackTrace();
			}
			/*
			 * 上报少货
			 */
			//业务变更去掉卸车差异上报OA少货
	/*		if(CollectionUtils.isNotEmpty(unloadDiffReportDto.getLessGoodsList())){
				//调用oa接口，上报oa少货差错单
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
				}else{
					LOGGER.error("获取少货运单所在单据为空，差异编号：" +  reportId + "，运单号：" + detail.getWaybillNo() + "，流水号：" + detail.getSerialNo());
					continue;
				}
				try{
					ResponseDto responseDto = this.reportOAUnloadLessGoods(waybillEntity, unloadDiffReportDto,unloadDiffReportDto.getLessGoodsList(),tempEntity,pOrg,tempEntity.getUnloadType());
					LOGGER.error("上报oa卸车少货，报告ID：" + reportId + ",运单号：" + waybillNo + "oa返回信息：失败原因（" + responseDto.getFailureReason() + "），oa差错单号：（" + responseDto.getErrorsNo() + "）");
					if(StringUtils.isNotBlank(responseDto.getErrorsNo())){
						updateOAErrorNoAndAddLackGoodsFoundInfo(unloadDiffReportDto.getLessGoodsList(), tempEntity, responseDto.getErrorsNo(),new Date());
					} else {
						//保存上报失败原因
						this.saveLessFailureReason(responseDto, tempEntity.getDiffReportNo(), waybillNo, unloadDiffReportDto.getLessGoodsList(),TransferConstants.GOODS_STATUS_LACK);
					}
				}catch(StockException e){
					e.printStackTrace();
					//表示无需上报
				}
			}*/
		}
		//更新差异报告处理状态
		this.updateHandleState(reportId);
		return FossConstants.SUCCESS;
	}
	
	
	
	/**
	 * @param waybillEntity
	 * @param unloadDiffReportDto
	 * @param tempEntity
	 * @param moreGoodsList
	 * @param loadOrg
	 * @author 257220
	 * @date 2015-6-9上午10:04:05
	 */
	private QmsErrResponseParam reportQMSUnloadMoreGoods(WaybillEntity waybillEntity,
			UnloadDiffReportDto unloadDiffReportDto,
			UnloadDiffReportEntity tempEntity,
			List<UnloadDiffReportDetailEntity> moreGoodsList,
			OrgAdministrativeInfoEntity loadOrg) {
		QmsErrRequestParam request = buildMoreGoodsReportInfo(waybillEntity, unloadDiffReportDto, tempEntity, moreGoodsList, loadOrg);
		QmsErrResponseParam qmsErrResponseParam = tfrToQmsErrorService.reportQmsError(request,TransferConstants.QMS_ESB_CODE_UNLOADMOREGOODS);
		return qmsErrResponseParam;
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
		 * 读取配置参数中卸车少货上报OA的时限
		 */
		ConfigurationParamsEntity entityDuration = configurationParamsService.queryConfigurationParamsByOrgCode(
																					DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
																					ConfigurationParamsConstants.TFR_PARM__UNLOAD_EXC_TIME_LIMIT_REPORT_OA_ERROR,
																					unloadOrgCode);
		BigDecimal timeLimit = BigDecimal.ZERO;//初始为0
		if (entityDuration != null && StringUtils.isNotEmpty(entityDuration.getConfValue())) {
			timeLimit = new BigDecimal(entityDuration.getConfValue());
		} 
		//获取差异报告明细
		List<UnloadDiffReportDetailEntity> reportDetailList = new ArrayList<UnloadDiffReportDetailEntity>();
		//长短途卸车，二程接驳卸车
		if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE)
				|| StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_SHORT_DISTANCE)
				||StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_SCEXPRESS)
				) {
			int unresolvedCount = unloadDiffReportDao.queryUnResolvedDiffReportDetailCount(reportId);
			if(unresolvedCount == 0) {
				unloadDiffReportDao.updateUnloadDiffReportReportOa(reportId);
				return reportDetailList;
			}
			reportDetailList = unloadDiffReportDao.queryUnResolvedDiffReportDetail(reportId,timeLimit);
		}else if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_DELIVER)) {
			//接送货卸车，只需上报OA少货，不上报多货；快递、整车单不上报
			//判断差异中是否存在少货差异，若无则不需要上报丢货，则更新NEED_REPORT_OA为N
			if(lackGoodsPieces == 0) {
				unloadDiffReportDao.updateUnloadDiffReportReportOa(reportId);
				return reportDetailList; 
			} else {
				//非快递、非整车单少货差异均被处理
				int unresolveCount = unloadDiffReportDao.queryUnResolvedLackDetailCount(reportId);
				if(unresolveCount == 0) {
					unloadDiffReportDao.updateUnloadDiffReportReportOa(reportId);
					return reportDetailList;
				}
			}
			reportDetailList = unloadDiffReportDao.queryUnResolvedLackDetail(reportId,timeLimit);
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
	 * 上报多货出错
	 * @param responseDto
	 * @param differReportNo
	 * @param waybillNo
	 * @param differGoodsList
	 * @param type
	 * @author 257220
	 * @date 2015-6-9上午10:19:23
	 */
	private void saveMoreFailureReason(QmsErrResponseParam responseDto,String differReportNo,String waybillNo,List<UnloadDiffReportDetailEntity> differGoodsList,String type) {
		String msg = responseDto.getMessage();//错误信息
		String failure = responseDto.getHandleCode();//错误编码
		//DB字段长度1000，oracle一个汉字长度为3，保证长度在300以内
		if(StringUtils.isNotEmpty(msg) && msg.length() > ConstantsNumberSonar.SONAR_NUMBER_150) {
				msg = msg.substring(0,ConstantsNumberSonar.SONAR_NUMBER_150);
			}
		
		if(StringUtils.isNotEmpty(failure) && failure.length() > ConstantsNumberSonar.SONAR_NUMBER_150) {
			failure = failure.substring(0,ConstantsNumberSonar.SONAR_NUMBER_150);
		}
		//保存上报失败日志
		this.addReportOaErrorLog(differReportNo,waybillNo,TransferConstants.REPORT_TYPE_UNLOAD,type,"Msg:"+msg+";FailureReason:"+failure);
		//更新备注信息，代码异常时不更新备注
		if(StringUtils.isNotEmpty(msg)) {
			for(UnloadDiffReportDetailEntity goodsReportDetail : differGoodsList){
				//获取oa差错单编号
				goodsReportDetail.setNote(msg);
				//更新差错明细信息
				unloadDiffReportDao.updateUnloadDiffReportDetail(goodsReportDetail);
			}
		}
	}
	/**
	
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
		if(StringUtils.isNotEmpty(msg) && msg.length() > ConstantsNumberSonar.SONAR_NUMBER_150) {
				msg = msg.substring(0,ConstantsNumberSonar.SONAR_NUMBER_150);
			}
		
		if(StringUtils.isNotEmpty(failure) && failure.length() > ConstantsNumberSonar.SONAR_NUMBER_150) {
			failure = failure.substring(0,ConstantsNumberSonar.SONAR_NUMBER_150);
		}
		//保存上报失败日志
		this.addReportOaErrorLog(differReportNo,waybillNo,TransferConstants.REPORT_TYPE_UNLOAD,type,"Msg:"+msg+";FailureReason:"+failure);
		//更新备注信息，代码异常时不更新备注
		if(!responseDto.getIsException()  && StringUtils.isNotEmpty(msg)) {
			for(UnloadDiffReportDetailEntity goodsReportDetail : differGoodsList){
				//获取oa差错单编号
				goodsReportDetail.setNote(msg);
				//更新差错明细信息
				unloadDiffReportDao.updateUnloadDiffReportDetail(goodsReportDetail);
			}
		}
	
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
	 * 上报OA少货（old）
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
			ProductEntity entity = productService4Dubbo.getProductLele(waybillEntity.getProductCode(),null,1);
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
		oaReportClearless.setTransportProduct(productService4Dubbo.getProductByCache(waybillEntity.getProductCode(), null).getName());	//产品类型
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
		//记录日志
		LOGGER.error("###############上报OA少货的同时，将该货物入库到“特殊组织”----------开始################");
		/*
		 * 入“特殊组织”库存
		 */
		//定义少货流水号字符串
		StringBuffer serialNoSb = new StringBuffer();
		for(int i = 0;i < lessGoodsList.size();i++){
			UnloadDiffReportDetailEntity serialNo = lessGoodsList.get(i);
			LoaderParticipationEntity loaderParticipation = pdaUnloadTaskDao.getLoaderParticipationByTaskId(serialNo.getUnloadTaskId());
			//构造入库对象
			InOutStockEntity stockEntity = new InOutStockEntity();
			//运单号
			stockEntity.setWaybillNO(serialNo.getWaybillNo());
			//流水号
			stockEntity.setSerialNO(serialNo.getSerialNo());
			//操作人code，job名称
			stockEntity.setOperatorCode(loaderParticipation.getLoaderCode());
			//操作人name，job名称
			stockEntity.setOperatorName(loaderParticipation.getLoaderName());
			//入库类别，卸车少货处理超时入库
			stockEntity.setInOutStockType(StockConstants.LOSE_GOODS_IN_STOCK_TYPE);
			//部门code
			if(StringUtils.equals(diffEntity.getUnloadType(), UnloadConstants.UNLOAD_TASK_TYPE_SCEXPRESS)){
				//如果为二程接驳卸车，入库到装车外场 ，这3个参数必须传
				stockEntity.setOrgCode(loadOrg.getCode());
				stockEntity.setUnloadSCOrgCode(diffEntity.getOrgCode());//卸车部门
				stockEntity.setUnloadSCType(UnloadConstants.UNLOAD_TASK_TYPE_SCEXPRESS);//卸车类型
			}else{
				stockEntity.setOrgCode(diffEntity.getOrgCode());
			}//车牌号
			stockEntity.setInOutStockBillNO(serialNo.getVehicleNo());
			//去掉卸车少货入库到特殊组织
			/*stockService.inStockPC(stockEntity);*/
			if(i + 1 == lessGoodsList.size()){
				serialNoSb.append(serialNo.getSerialNo());
				serialNoSb.append("。");
			}else{
				serialNoSb.append(serialNo.getSerialNo());
				serialNoSb.append("、");
			}
			
			LOGGER.error("####入库特殊组织，运单号：" + serialNo.getWaybillNo() + "，流水号：" + serialNo.getSerialNo() + "################");
		}
		LOGGER.error("###############上报OA少货的同时，将该货物入库到“特殊组织”----------结束################");
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
		if(event.length() > ConstantsNumberSonar.SONAR_NUMBER_650){
			event = event.substring(0, ConstantsNumberSonar.SONAR_NUMBER_650) + "....";
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
		StringBuffer s = new StringBuffer(ConstantsNumberSonar.SONAR_NUMBER_100);
		for(UnloadDiffReportDetailEntity entity: lessGoodsList){
			s.append(entity.getSerialNo()).append(",");
		}
		if(null != s && s.length() > 0){
			oaReportClearless.setSerialNoList(s.substring(0, s.length()-1));
		}
		//业务渠道
		oaReportClearless.setBusinessChannel(this.queryBusinessChannel(unloadType, loadOrg, unloadEntity));
		
		//上报QMS已经迁移
		/*try{
			lostWarningAnalyData.analyWarningData_unloadData(oaReportClearless, loadOrg, unloadEntity);
		}catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("上报丢货预警系 异常:"+e.getMessage());
		} */
		//上报OA少货差错
		ResponseDto responseDto=fossToOAService.reportLessGoods(oaReportClearless);
		try{
			if(StringUtils.isNotBlank(responseDto.getErrorsNo())){
				String oaReportClearlessmsg = null;
				if(oaReportClearless!=null){
					oaReportClearlessmsg= JSONObject.fromObject(oaReportClearless).toString();
					//try TODO 
					/*将需要上报的丢货的运单号及流水号单独入库**/
					/*String wno=oaReportClearless.getWayBillId();
					String sserialNoList=oaReportClearless.getSerialNoList();
					//流水号（String）转list并去重
					String[] arr = sserialNoList.split(",");
				    List<String> list = java.util.Arrays.asList(arr);
				    List<String> tempList=new ArrayList<String>();
				    for(String seriaNO:list){
				    	 if(!tempList.contains(seriaNO)){
				    		 //入库
				    		 tempList.add(seriaNO);
				    		 LOGGER.error("运单流水号############"+seriaNO+"###########去重##########");
				    		 unloadDiffReportDao.insertintoreportFoundDataBacks(wno,seriaNO);
				    	 }
				    }*/
				}
				String loadOrgmsg = null;
				if(loadOrg!=null){
					 loadOrgmsg = JSONObject.fromObject(loadOrg).toString();}
				String unloadEntitymsg = null;	 
				if(unloadEntity!=null){
					unloadEntitymsg = JSONObject.fromObject(unloadEntity).toString();}
				unloadDiffReportDao.insertUnloadtoOABackupsForQMS(oaReportClearlessmsg,loadOrgmsg,unloadEntitymsg);
			}
	   }catch(Exception e){
		   LOGGER.error("运单号############"+oaReportClearless.getWayBillId()+"###########写入上报QMS数据备份表失败####mark######"+e);
		   LOGGER.error(e.getMessage());
		   LOGGER.error("运单号############"+oaReportClearless.getWayBillId()+"###########写入上报QMS数据备份表失败##########");
	   }
		return responseDto;	
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
	 * 构造快递多货上报信息
	 * @param waybillEntity
	 * @param unloadDiffReportDto
	 * @param tempEntity
	 * @param moreGoodsList
	 * @param loadOrg
	 * @author 257220
	 * @date 2015-6-8上午11:26:14
	 */
	private QmsErrRequestParam buildMoreGoodsReportInfo(WaybillEntity waybillEntity,UnloadDiffReportDto unloadDiffReportDto,
			UnloadDiffReportEntity tempEntity,List<UnloadDiffReportDetailEntity> moreGoodsList,OrgAdministrativeInfoEntity loadOrg) {
		QmsErrRequestParam qmsErrRequestParam = new QmsErrRequestParam();
		// 业务编码 K：快递差错 L：零担差错
		String errCategory = getErrCategory(waybillEntity);
		String findWay = findWay(tempEntity.getUnloadTaskId(),waybillEntity.getWaybillNo());
		// 差错上报主信息
		QmsErrorMainEntity qmsErrorMainEntity = buildErrorMainEntity(waybillEntity, unloadDiffReportDto, tempEntity, moreGoodsList,loadOrg);
		if(QmsErrorConstants.QMS_L.equals(errCategory)){
			qmsErrRequestParam.setErrCategoty(QmsErrorConstants.QMS_L);
			//差错类型编号 L有货无单
			qmsErrRequestParam.setErrorTypeId(QmsErrorConstants.MORE_GOODS_CODE);
			// 构建零担信息
			QmsLDErrSubHasWaybillEntity ldErrSubHasWaybillEntity = buildLDErrSubHasWaybillEntity(waybillEntity, unloadDiffReportDto, tempEntity, moreGoodsList, loadOrg);
			qmsErrRequestParam.setLdsubHasInfo(ldErrSubHasWaybillEntity);
			//设置文件标准
			if(StringUtils.equals(findWay, QmsErrorConstants.FIND_WAY_HANDING)){//经手部门发现
				qmsErrorMainEntity.setDocStandarId(QmsErrorConstants.FILE_STANDARD_ID_L_UNLOADING_MORE_HANDING);
				qmsErrorMainEntity.setDocStandarName(QmsErrorConstants.FILE_STANDARD_NAME_L_UNLOADING_MORE_HANDING);
			}else{//非经手部门发现
				qmsErrorMainEntity.setDocStandarId(QmsErrorConstants.FILE_STANDARD_ID_L_UNLOADING_MORE_NOHANDING);
				qmsErrorMainEntity.setDocStandarName(QmsErrorConstants.FILE_STANDARD_NAME_L_UNLOADING_MORE_NOHANDING);
			}
		}else if(QmsErrorConstants.QMS_K.equals(errCategory)){
			qmsErrRequestParam.setErrCategoty(QmsErrorConstants.QMS_K);
			// 差错类型编号 K货单不符（多货）
			qmsErrRequestParam.setErrorTypeId(QmsErrorConstants.EXPRESS_MORE_GOODS_CODE);
			// 构建快递有单信息
			QmsKDErrSubHasWaybillEntity kdsubHasInfo = buildKDErrSubHasWaybillEntity(waybillEntity, unloadDiffReportDto, tempEntity, moreGoodsList,loadOrg);
			qmsErrRequestParam.setKdsubHasInfo(kdsubHasInfo);
			//设置文件标准
			if(StringUtils.equals(findWay, QmsErrorConstants.FIND_WAY_HANDING)){//经手部门发现
				qmsErrorMainEntity.setDocStandarId(QmsErrorConstants.FILE_STANDARD_ID_UNLOADING_MORE_HANDING);
				qmsErrorMainEntity.setDocStandarName(QmsErrorConstants.FILE_STANDARD_NAME_UNLOADING_MORE_HANDING);
			}else{//非经手部门发现
				qmsErrorMainEntity.setDocStandarId(QmsErrorConstants.FILE_STANDARD_ID_UNLOADING_MORE_NOHANDING);
				qmsErrorMainEntity.setDocStandarName(QmsErrorConstants.FILE_STANDARD_NAME_UNLOADING_MORE_NOHANDING);
			}
		}
			qmsErrRequestParam.setMainInfo(qmsErrorMainEntity);
		
		// 是否立即返回信息 为否
		qmsErrRequestParam.setReturnResult(false);
		return qmsErrRequestParam;
	}
	
	/**
	 * @return
	 * @author 257220
	 * @date 2015-6-8下午1:45:34
	 */
	private QmsKDErrSubHasWaybillEntity buildKDErrSubHasWaybillEntity(WaybillEntity waybillEntity, UnloadDiffReportDto unloadDiffReportDto,UnloadDiffReportEntity tempEntity,List<UnloadDiffReportDetailEntity> moreGoodsList,OrgAdministrativeInfoEntity loadOrg) {
		QmsKDErrSubHasWaybillEntity qmsKDErrSubHasWaybillEntity =  new QmsKDErrSubHasWaybillEntity();
		/*
		 * 事件经过
		 */
		//拼接多货流水号,英文逗号隔开
		StringBuffer serialNoSb = new StringBuffer();
		for(int i = 0;i < moreGoodsList.size();i++){
			UnloadDiffReportDetailEntity serialNo = moreGoodsList.get(i);
			if(i + 1 == moreGoodsList.size()){
				serialNoSb.append(serialNo.getSerialNo());
			}else{
				serialNoSb.append(serialNo.getSerialNo());
				serialNoSb.append(QmsErrorConstants.REPORT_SEPARATOR);
			}
		}
		//事件经过
		String event = QmsErrorConstants.EVENT_UNLOAD_MORE + ";" + serialNoSb.toString();
		qmsKDErrSubHasWaybillEntity.setIncident(event);
		//上一环节部门
		if(loadOrg != null){
			//责任人：责任部门负责人；若找不到负责人，就往上找至大区为止
			String responsibDepart = obtainResponsibDepart(tempEntity);
			if(responsibDepart == null){
				responsibDepart = loadOrg.getCode();
			}
			Map<String,String> principalMap = getPrincipal(responsibDepart);
			if(principalMap != null){
				//责任人工号 respEmpCode
				qmsKDErrSubHasWaybillEntity.setRespEmpCode(principalMap.get("userCode"));
				//责任人姓名 respEmpName
				qmsKDErrSubHasWaybillEntity.setRespEmpName(principalMap.get("userName"));
			}
			//责任部门标杆编码
			qmsKDErrSubHasWaybillEntity.setRespDeptCode(loadOrg.getUnifiedCode());
			//责任部门名称
			qmsKDErrSubHasWaybillEntity.setRespDeptName(loadOrg.getName());
			//上一环节部门标杆编码
			qmsKDErrSubHasWaybillEntity.setLastHandedDeptCode(loadOrg.getUnifiedCode());
			//上一环节部门名称
			qmsKDErrSubHasWaybillEntity.setLastHandedDeptName(loadOrg.getName());
			//责任部门事业部
			//设置查询参数
			List<String> bizTypesList = new ArrayList<String>();
			//事业部类型
			bizTypesList.add(BizTypeConstants.ORG_DIVISION);
			//查询上级部门
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
					queryOrgAdministrativeInfoByCode(loadOrg.getCode(), bizTypesList);
			if(orgAdministrativeInfoEntity != null){
				//责任事业部门标杆代码
				qmsKDErrSubHasWaybillEntity.setDivisionCode(orgAdministrativeInfoEntity.getUnifiedCode());
				qmsKDErrSubHasWaybillEntity.setDivisionName(orgAdministrativeInfoEntity.getName());//责任事业部编号
			}else{
				LOGGER.warn("卸车多货上报OA：查询部门【" + loadOrg.getCode() +"】上一级事业部失败！");
			}
		}
		//开单部门
		OrgAdministrativeInfoEntity createEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCreateOrgCode());
		if(createEntity != null){
			//开单部门标杆编码
			qmsKDErrSubHasWaybillEntity.setBillingDeptCode(createEntity.getUnifiedCode());
			//开单部门名称
			qmsKDErrSubHasWaybillEntity.setBillingDeptName(createEntity.getName());
		}
		//发货客户编码
		qmsKDErrSubHasWaybillEntity.setDeliveryCustomerCode(waybillEntity.getDeliveryCustomerCode());
		//收货客户编码
		qmsKDErrSubHasWaybillEntity.setReceivingCustomerCode(waybillEntity.getReceiveCustomerCode());
		//托运人
		qmsKDErrSubHasWaybillEntity.setConsignor(waybillEntity.getDeliveryCustomerContact());
		//运输性质
		String productCode = waybillEntity.getProductCode();
		qmsKDErrSubHasWaybillEntity.setTransportProperties(productService4Dubbo.getProductByCache(waybillEntity.getProductCode(), null).getName());
		/*
		 * 运输类型
		 */
		DataDictionaryValueEntity dictEntity1 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.BSE_TRANS_TYPE, waybillEntity.getTransportType());
		if(dictEntity1 == null){
			//运输类型为空时，根据运输性质查询对应的一级产品
			ProductEntity entity = productService4Dubbo.getProductLele(productCode,null,1);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("UnloadService调用计价Dubbo接口成功，entity = {} " , entity);
			}
			if(entity != null) {
				qmsKDErrSubHasWaybillEntity.setTransportType(entity.getName());
			}
		}else{
			//运输类型
			qmsKDErrSubHasWaybillEntity.setTransportType(dictEntity1.getValueName());
		}
		//到达部门
		String customerPickupOrgCode = waybillEntity.getCustomerPickupOrgCode();
		OrgAdministrativeInfoEntity customerPickupOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(customerPickupOrgCode);
		if(customerPickupOrgEntity != null){
			//到达部门标杆编码
			qmsKDErrSubHasWaybillEntity.setArriveDeptCode(customerPickupOrgEntity.getUnifiedCode());
		}
		qmsKDErrSubHasWaybillEntity.setArriveDeptName(waybillEntity.getCustomerPickupOrgName());
		//收货人
		qmsKDErrSubHasWaybillEntity.setConsignee(waybillEntity.getReceiveCustomerName());
		//储运事项
		qmsKDErrSubHasWaybillEntity.setStorageItems(waybillEntity.getTransportationRemark());
		//收货人电话
		if(StringUtils.isBlank(waybillEntity.getReceiveCustomerPhone())){
			//若收货人电话为空，则传入收货人手机
			qmsKDErrSubHasWaybillEntity.setReceiverPhone(waybillEntity.getReceiveCustomerMobilephone());   
		}else{
			//若收货人电话不为空，则传入收货人电话
			qmsKDErrSubHasWaybillEntity.setReceiverPhone(waybillEntity.getReceiveCustomerPhone());
		}
		/*
		 * 提货方式
		 */
		DataDictionaryValueEntity dictEntity3 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.PICKUP_GOODS, waybillEntity.getReceiveMethod());
		if(dictEntity3 == null){
			//若数据字典为空，则传入常量
			qmsKDErrSubHasWaybillEntity.setDeliveryMethods(UNKNOWN_TYPE);
		}else{
			//提货方式
			qmsKDErrSubHasWaybillEntity.setDeliveryMethods(dictEntity3.getValueName());
		}
		//重量
		qmsKDErrSubHasWaybillEntity.setWeight(waybillEntity.getGoodsWeightTotal() == null?"":waybillEntity.getGoodsWeightTotal().toString());
		//体积
		qmsKDErrSubHasWaybillEntity.setVolume(waybillEntity.getGoodsVolumeTotal() == null?"":waybillEntity.getGoodsVolumeTotal().toString());
		//件数
		qmsKDErrSubHasWaybillEntity.setFnumber(String.valueOf(waybillEntity.getGoodsQtyTotal()));
		//货物名称
		qmsKDErrSubHasWaybillEntity.setGoodsName(waybillEntity.getGoodsName());
		//发货时间
		qmsKDErrSubHasWaybillEntity.setDeliveryTime(DateUtils.convert(waybillEntity.getBillTime(), DateUtils.DATE_TIME_FORMAT));
		//目的站
		qmsKDErrSubHasWaybillEntity.setTargetStation(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(waybillEntity.getTargetOrgCode()));
		/*
		 * 付款方式
		 */
		DataDictionaryValueEntity dictEntity4 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE, waybillEntity.getPaidMethod());
		if(dictEntity4 == null){
			//若数据字典为空，则传入常量
			qmsKDErrSubHasWaybillEntity.setPaymentMethod(UNKNOWN_TYPE);
		}else{
			//付款方式
			qmsKDErrSubHasWaybillEntity.setPaymentMethod(dictEntity4.getValueName());
		}
		//保险金额
		if(null != waybillEntity.getInsuranceFee()) {
			qmsKDErrSubHasWaybillEntity.setInsuranceAmount(waybillEntity.getInsuranceFee().toString());        
		} else {
			qmsKDErrSubHasWaybillEntity.setInsuranceAmount("");
		}
		//货物包装
		qmsKDErrSubHasWaybillEntity.setGoodsPackage(waybillEntity.getGoodsPackage());
		//运费总额
		qmsKDErrSubHasWaybillEntity.setTotalFreight(waybillEntity.getTotalFee() == null?"":waybillEntity.getTotalFee().toString());   
		
		if(tempEntity != null){
			//是否整票 判断规则 比对“多货件数”与“总件数”是否相同
			if(moreGoodsList.size() == waybillEntity.getGoodsQtyTotal()){
				qmsKDErrSubHasWaybillEntity.setEntireTicket(UnloadConstants.UNLOAD_IS_ENTIRETICKET_YES);
			}else{
				qmsKDErrSubHasWaybillEntity.setEntireTicket(UnloadConstants.UNLOAD_IS_ENTIRETICKET_NO);
			}
			//卸车任务编号
			qmsKDErrSubHasWaybillEntity.setUnloadingTaskNumber(tempEntity.getUnloadTaskNo());
			//车牌号
			qmsKDErrSubHasWaybillEntity.setLicensePlateNumber(tempEntity.getVehicleNo());
			//多货件数
			qmsKDErrSubHasWaybillEntity.setMoreShipments(moreGoodsList.size()+"");
			/*
			 * 有货无交接的类型(经手部门发现、非经手部门发现)，默认为经手部门发现
			 */
			//218427 sonar 优化 将此行代码提至 if判断中
			qmsKDErrSubHasWaybillEntity.setNotransferGoods(findWay(tempEntity.getUnloadTaskId(),waybillEntity.getWaybillNo()));
		}
		
		//多货类型 
		qmsKDErrSubHasWaybillEntity.setMoreCargoType(QmsErrorConstants.MORECARGOTYPE_UNLOAD_MORE);
		//多货流水号
		qmsKDErrSubHasWaybillEntity.setMoregoodsSerialNum(serialNoSb.toString());
		//上报部门标杆编码
		//上报部门名称
		//上报事业部标杆编码
		//上报事业部名称
		return qmsKDErrSubHasWaybillEntity;
	}
	/**
	 * 构建零担实体
	 * @param waybillEntity
	 * @param unloadDiffReportDto
	 * @param tempEntity
	 * @param moreGoodsList
	 * @param loadOrg
	 * @return
	 * @author 257220
	 * @date 2015-6-13下午1:45:48
	 */
	private QmsLDErrSubHasWaybillEntity buildLDErrSubHasWaybillEntity(WaybillEntity waybillEntity, UnloadDiffReportDto unloadDiffReportDto,UnloadDiffReportEntity tempEntity,List<UnloadDiffReportDetailEntity> moreGoodsList,OrgAdministrativeInfoEntity loadOrg) {
		QmsLDErrSubHasWaybillEntity qmsLDErrSubHasWaybillEntity = new QmsLDErrSubHasWaybillEntity();
		//事件经过：拼接多货流水号,英文逗号隔开
		StringBuffer serialNoSb = new StringBuffer();
		for(int i = 0;i < moreGoodsList.size();i++){
			UnloadDiffReportDetailEntity serialNo = moreGoodsList.get(i);
			if(i + 1 == moreGoodsList.size()){
				serialNoSb.append(serialNo.getSerialNo());
			}else{
				serialNoSb.append(serialNo.getSerialNo());
				serialNoSb.append(QmsErrorConstants.REPORT_SEPARATOR);
			}
		}
		//事件经过
		String event = QmsErrorConstants.EVENT_UNLOAD_MORE + ";" + serialNoSb.toString();
		qmsLDErrSubHasWaybillEntity.setIncident(event);
		//短信通知对象工号
		//短信通知对象名称
		//上一环节部门
		if(loadOrg != null){
			//责任人：责任部门负责人；若找不到负责人，就往上找至大区为止
			String responsibDepart = obtainResponsibDepart(tempEntity);
			if(responsibDepart == null){
				responsibDepart = loadOrg.getCode();
			}
			Map<String,String> principalMap = getPrincipal(responsibDepart);
			if(principalMap != null){
				//责任人工号 respEmpCode
				qmsLDErrSubHasWaybillEntity.setRespEmpCode(principalMap.get("userCode"));
				//责任人姓名 respEmpName
				qmsLDErrSubHasWaybillEntity.setRespEmpName(principalMap.get("userName"));
			}
			//责任部门标杆编码
			qmsLDErrSubHasWaybillEntity.setRespDeptCode(loadOrg.getUnifiedCode());
			//责任部门名称
			qmsLDErrSubHasWaybillEntity.setRespDeptName(loadOrg.getName());
			//上一环节部门标杆编码
			qmsLDErrSubHasWaybillEntity.setLastLinkDeptCode(loadOrg.getUnifiedCode());
			//上一环节部门名称
			qmsLDErrSubHasWaybillEntity.setLastLinkDeptName(loadOrg.getName());
			//责任部门事业部
			//设置查询参数
			List<String> bizTypesList = new ArrayList<String>();
			//事业部类型
			bizTypesList.add(BizTypeConstants.ORG_DIVISION);
			//查询上级部门
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
					queryOrgAdministrativeInfoByCode(loadOrg.getCode(), bizTypesList);
			if(orgAdministrativeInfoEntity != null){
				//责任事业部门标杆代码
				qmsLDErrSubHasWaybillEntity.setDivisionCode(orgAdministrativeInfoEntity.getUnifiedCode());
				qmsLDErrSubHasWaybillEntity.setDivisionName(orgAdministrativeInfoEntity.getName());//责任事业部编号
			}else{
				LOGGER.warn("卸车多货上报QMS：查询部门【" + loadOrg.getCode() +"】上一级事业部失败！");
			}
		}
		//开单部门
		OrgAdministrativeInfoEntity createEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCreateOrgCode());
		if(createEntity != null){
			//开单部门标杆编码
			qmsLDErrSubHasWaybillEntity.setBillingDeptCode(createEntity.getUnifiedCode());
			//开单部门名称
			qmsLDErrSubHasWaybillEntity.setBillingDeptName(createEntity.getName());
		}
		//发货客户编码
		qmsLDErrSubHasWaybillEntity.setSendClientCode(waybillEntity.getDeliveryCustomerCode());
		//收货客户编码 
		qmsLDErrSubHasWaybillEntity.setTakeOverClientCode(waybillEntity.getReceiveCustomerCode());
		//托运人姓名
		qmsLDErrSubHasWaybillEntity.setShipper(waybillEntity.getDeliveryCustomerContact());
		
		String productCode = waybillEntity.getProductCode();
		//运输类型
		DataDictionaryValueEntity dictEntity1 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.BSE_TRANS_TYPE, waybillEntity.getTransportType());
		if(dictEntity1 == null){
			//运输类型为空时，根据运输性质查询对应的一级产品
			ProductEntity entity = productService4Dubbo.getProductLele(productCode,null,1);
			if(entity != null) {
				qmsLDErrSubHasWaybillEntity.setTransType(entity.getName());
			}
		}else{
			//运输类型
			qmsLDErrSubHasWaybillEntity.setTransType(dictEntity1.getValueName());
		}
		//运输性质
		qmsLDErrSubHasWaybillEntity.setTransNature(productService4Dubbo.getProductByCache(waybillEntity.getProductCode(), null).getName());
		//收货人
		qmsLDErrSubHasWaybillEntity.setReceiverName(waybillEntity.getReceiveCustomerContact());
		//储运事项
		qmsLDErrSubHasWaybillEntity.setStorageTransport(waybillEntity.getTransportationRemark());
		//收货人电话
		if(StringUtils.isBlank(waybillEntity.getReceiveCustomerPhone())){
			//若收货人电话为空，则传入收货人手机
			qmsLDErrSubHasWaybillEntity.setReceiverPhone(waybillEntity.getReceiveCustomerMobilephone());   
		}else{
			//若收货人电话不为空，则传入收货人电话
			qmsLDErrSubHasWaybillEntity.setReceiverPhone(waybillEntity.getReceiveCustomerPhone());
		}
		/*
		 * 提货方式
		 */
		DataDictionaryValueEntity dictEntity3 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.PICKUP_GOODS, waybillEntity.getReceiveMethod());
		if(dictEntity3 == null){
			//若数据字典为空，则传入常量
			qmsLDErrSubHasWaybillEntity.setPickUpType(UNKNOWN_TYPE);
		}else{
			//提货方式
			qmsLDErrSubHasWaybillEntity.setPickUpType(dictEntity3.getValueName());
		}
		//重量
		qmsLDErrSubHasWaybillEntity.setWeight(waybillEntity.getGoodsWeightTotal() == null?"":waybillEntity.getGoodsWeightTotal().toString());
		//体积
		qmsLDErrSubHasWaybillEntity.setVolume(waybillEntity.getGoodsVolumeTotal() == null?"":waybillEntity.getGoodsVolumeTotal().toString());
		//件数
		qmsLDErrSubHasWaybillEntity.setNum(waybillEntity.getGoodsQtyTotal() == null?"":waybillEntity.getGoodsQtyTotal().toString());
		//货物名称
		qmsLDErrSubHasWaybillEntity.setGoodsName(waybillEntity.getGoodsName());
		//发货时间
		qmsLDErrSubHasWaybillEntity.setSendGoodsTime(DateUtils.convert(waybillEntity.getBillTime(), DateUtils.DATE_TIME_FORMAT));
		//目的站
	//	qmsLDErrSubHasWaybillEntity.setTargetStation(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(waybillEntity.getTargetOrgCode()));
		//到达部门
		String customerPickupOrgCode = waybillEntity.getCustomerPickupOrgCode();
		OrgAdministrativeInfoEntity customerPickupOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(customerPickupOrgCode);
		if(customerPickupOrgEntity != null){
			//到达部门标杆编码
			qmsLDErrSubHasWaybillEntity.setArriveDeptCode(customerPickupOrgEntity.getUnifiedCode());
		}
		qmsLDErrSubHasWaybillEntity.setArriveDeptName(waybillEntity.getCustomerPickupOrgName());
		/*
		 * 付款方式
		 */
		DataDictionaryValueEntity dictEntity4 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE, waybillEntity.getPaidMethod());
		if(dictEntity4 == null){
			//若数据字典为空，则传入常量
			qmsLDErrSubHasWaybillEntity.setPayType(UNKNOWN_TYPE);
		}else{
			//付款方式
			qmsLDErrSubHasWaybillEntity.setPayType(dictEntity4.getValueName());
		}
		//保险金额
		if(null != waybillEntity.getInsuranceFee()) {
			qmsLDErrSubHasWaybillEntity.setSafeMoney(waybillEntity.getInsuranceFee().toString());        
		} else {
			qmsLDErrSubHasWaybillEntity.setSafeMoney("");
		}
		//货物包装
		qmsLDErrSubHasWaybillEntity.setGoodsPackage(waybillEntity.getGoodsPackage());
		//预付金额
		qmsLDErrSubHasWaybillEntity.setPrepayMoney(waybillEntity.getPrePayAmount() == null?"":waybillEntity.getPrePayAmount().toString());
		//到付金额
		qmsLDErrSubHasWaybillEntity.setTopatMoney(waybillEntity.getToPayAmount() == null ? "" : waybillEntity.getToPayAmount().toString());
		//运费总额
		qmsLDErrSubHasWaybillEntity.setFreightSumFee(waybillEntity.getTotalFee() == null?"":waybillEntity.getTotalFee().toString()); 
		
		if(tempEntity != null){
			//是否整票 判断规则 比对“多货件数”与“总件数”是否相同
			if(moreGoodsList.size() == waybillEntity.getGoodsQtyTotal()){
				qmsLDErrSubHasWaybillEntity.setIsWholeTicket(UnloadConstants.UNLOAD_IS_ENTIRETICKET_YES);
			}else{
				qmsLDErrSubHasWaybillEntity.setIsWholeTicket(UnloadConstants.UNLOAD_IS_ENTIRETICKET_NO);
			}
			//卸车任务编号
			qmsLDErrSubHasWaybillEntity.setUnloadBusiCode(tempEntity.getUnloadTaskNo());
			//车牌号
			qmsLDErrSubHasWaybillEntity.setCarCode(tempEntity.getVehicleNo());
		}
		/*
		 * 有货无交接的类型(经手部门发现、非经手部门发现)，默认为经手部门发现
		 */
		qmsLDErrSubHasWaybillEntity.setGoodsNoTransfer(findWay(tempEntity == null ? "" : tempEntity.getUnloadTaskId(),waybillEntity.getWaybillNo()));
		//多货类型
		qmsLDErrSubHasWaybillEntity.setMoreGoodsType(QmsErrorConstants.MORECARGOTYPE_UNLOAD_MORE);
		//多货流水号
		qmsLDErrSubHasWaybillEntity.setMoreGoodsFlowcode(serialNoSb.toString());
		//上报部门标杆编码
		//上报部门名称
		//多货件数
		qmsLDErrSubHasWaybillEntity.setMoreGoodsNum(Integer.toString(moreGoodsList.size()));
		return qmsLDErrSubHasWaybillEntity;
	}
	/**
	 * 获取业务类型 
	 * @author 257220
	 * @date 2015-6-11下午4:47:25
	 */
	private String getErrCategory(WaybillEntity waybillEntity){
		// 业务编码 K：快递差错 L：零担差错
		String productCode = waybillEntity.getProductCode();
		Boolean isExpress = productService4Dubbo.onlineDetermineIsExpressByProductCode(productCode,new Date());
		return isExpress ? QmsErrorConstants.QMS_K : QmsErrorConstants.QMS_L;
	}
	/**
	 * @author nly
	 * @date 2015年5月5日 上午10:39:21
	 * @function 根据部门code查询部门负责人，向上查找，最高至大区
	 * @param deptCode
	 * @return
	 */
	private Map<String, String> getPrincipal(String deptCode) {
		Map<String,String> map = new HashMap<String,String>();
		//是否已到大区
		String bigRegion = "N";
		for(int i =0;i < ConstantsNumberSonar.SONAR_NUMBER_6;i++) {
			//最高判断至大区
			if(StringUtils.equals("Y", bigRegion)) {
				return null;
			}
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode);
			if(null == org) {
				return null;
			}
			bigRegion = org.getBigRegion();
			//负责人工号
			String userCode = org.getPrincipalNo();
			if(StringUtils.isNotEmpty(userCode)) {
				
				EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(userCode);
				if(null != employee) {
					String userName = employee.getEmpName();
					map.put("userCode", userCode);
					map.put("userName", userName);
					return map;
				} else {
					//查找上一部门负责人
					deptCode = org.getParentOrgCode();
				}
			} else {
				//查找上一部门负责人
				deptCode = org.getParentOrgCode();
			}	
		}
		
		return null;
	}
	/**
	 * 构建上报差错信息主体
	 * @return
	 * @author 257220
	 * @date 2015-6-8下午1:45:30
	 */
	private QmsErrorMainEntity buildErrorMainEntity(WaybillEntity waybillEntity, UnloadDiffReportDto unloadDiffReportDto,UnloadDiffReportEntity tempEntity,List<UnloadDiffReportDetailEntity> moreGoodsList,OrgAdministrativeInfoEntity loadOrg) {
		QmsErrorMainEntity qmsErrorMainEntity = new QmsErrorMainEntity();
		//获取上报人（即卸车任务的创建人）
		if(tempEntity != null){
			//卸车任务ID
			String unloadTaskId = tempEntity.getUnloadTaskId();
			//卸车任务创建人
			List<LoaderParticipationEntity> loaderEntityList = this.unloadTaskService.queryTaskCreatorLoaderByTaskId(unloadTaskId);
			if(CollectionUtils.isNotEmpty(loaderEntityList)){
				LoaderParticipationEntity loader = loaderEntityList.get(0);
				//上报人工号
				String loaderCode = loader.getLoaderCode();
				qmsErrorMainEntity.setRepEmpcode(loaderCode);
				//上报人姓名
				qmsErrorMainEntity.setRepEmpName(loader.getLoaderName());
				EmployeeEntity  employee = employeeService.queryEmployeeByEmpCode(loaderCode);
				//上报人职位
				if(employee != null){
					qmsErrorMainEntity.setRepEmpJob(employee.getTitleName());
					String orgCode = employee.getOrgCode();
					//上报人事业部
					//设置查询参数
					List<String> bizTypesList = new ArrayList<String>();
					//事业部类型
					bizTypesList.add(BizTypeConstants.ORG_DIVISION);
					//查询上级部门
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
							queryOrgAdministrativeInfoByCode(orgCode, bizTypesList);
					if(orgAdministrativeInfoEntity != null){
						//上报人事业部标杆编码
						qmsErrorMainEntity.setRepDivisionCode(orgAdministrativeInfoEntity.getUnifiedCode());
						//上报人事业部
						qmsErrorMainEntity.setRepDivisionName(orgAdministrativeInfoEntity.getName());
					}else{
						LOGGER.warn("卸车多货上报QMS：查询部门【" + orgCode +"】上一级事业部失败！");
					}
				}
			}
		}
		//运单号
		String wayBillNum = waybillEntity.getWaybillNo();
		qmsErrorMainEntity.setWayBillNum(wayBillNum);
		//收货部门
		OrgAdministrativeInfoEntity recieveEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getReceiveOrgCode());
		if(recieveEntity != null){
			//收货部门标杆编码
			qmsErrorMainEntity.setReceiveDeptCode(recieveEntity.getUnifiedCode());
			//收货部门名称
			qmsErrorMainEntity.setReceiveDeptName(recieveEntity.getName());
		}
		return qmsErrorMainEntity;
	}
	/**
	 * 获取发现方式
	 * 经手部门发现/非经手部门发现：默认为经手部门发现。若同一个单多件，
	 * 若同时存在经手部门发现和非经手部门发现时，为经手部门发现。
	 * 判断方式：MORE_ENTRAINED
	 * @return
	 * @author 257220
	 * @date 2015-7-20下午2:20:12
	 */
	private String findWay(String taskId,String wayBillNo){
		String findWay = QmsErrorConstants.FIND_WAY_HANDING;
		int count = unloadDiffReportDao.queryEntrainedNum(taskId,wayBillNo);
		if(count <= 0){
			findWay = QmsErrorConstants.FIND_WAY_NOHANDING;
		}
		return findWay;
	}
	
	/**
	 * 上报OA多货
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-27 上午11:33:45
	 */
	private ResponseDto reportOAUnloadMoreGoods(WaybillEntity waybillEntity, UnloadDiffReportDto unloadDiffReportDto,UnloadDiffReportEntity tempEntity,List<UnloadDiffReportDetailEntity> moreGoodsList,OrgAdministrativeInfoEntity loadOrg){
		//构造多货差错单对象
		OaReportClearMore oaReportClearMore = new OaReportClearMore();
		//卸车任务编号
		oaReportClearMore.setDisburdenTaskNumber(tempEntity.getUnloadTaskNo());
		//上一环节部门
		if(loadOrg != null){
			//上一部门name
			oaReportClearMore.setPreviousDept(loadOrg.getName());
			//上一部门code
			oaReportClearMore.setPreviousDeptId(loadOrg.getUnifiedCode());
			//责任部门标杆编码
			oaReportClearMore.setResponsibleDeptId(loadOrg.getUnifiedCode());
			//责任部门名称
			oaReportClearMore.setResponsibleDept(loadOrg.getName());
			//设置责任事业部
			//设置查询参数
			List<String> bizTypesList = new ArrayList<String>();
			//事业部类型
			bizTypesList.add(BizTypeConstants.ORG_DIVISION);
			//查询上级部门
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
					queryOrgAdministrativeInfoByCode(loadOrg.getCode(), bizTypesList);
			if(orgAdministrativeInfoEntity != null){
				oaReportClearMore.setFinalSysCode(orgAdministrativeInfoEntity.getName());
				oaReportClearMore.setRespDivisionOrgid(orgAdministrativeInfoEntity.getUnifiedCode());
			}else{
				LOGGER.warn("卸车多货上报OA：查询部门【" + loadOrg.getCode() +"】上一级事业部失败！");
			}
			
		}else{
			LOGGER.warn("卸车多货上报OA：查询货物【" + waybillEntity.getWaybillNo() +"】上一部门失败！");
		}
		//获取上报人（即卸车任务的创建人）工号、工号
		if(tempEntity != null){
			//卸车任务ID
			String unloadTaskId = tempEntity.getUnloadTaskId();
			//车牌号
			oaReportClearMore.setCarNumber(tempEntity.getVehicleNo());
			//卸车任务创建人
			List<LoaderParticipationEntity> loaderEntityList = this.unloadTaskService.queryTaskCreatorLoaderByTaskId(unloadTaskId);
			if(CollectionUtils.isNotEmpty(loaderEntityList)){
				LoaderParticipationEntity loader = loaderEntityList.get(0);
				//上报人工号
				oaReportClearMore.setUserId(loader.getLoaderCode());
			}
		}
		//运单号
		oaReportClearMore.setWayBillId(waybillEntity.getWaybillNo());
		//上报时间
		oaReportClearMore.setReportTime(new Date());
		/*
		 * 运输类型
		 */
		DataDictionaryValueEntity dictEntity1 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.BSE_TRANS_TYPE, waybillEntity.getTransportType());
		if(dictEntity1 == null){
			//运输类型为空时，根据运输性质查询对应的一级产品
			ProductEntity entity = productService4Dubbo.getProductLele(waybillEntity.getProductCode(),null,1);
			if(entity != null) {
				oaReportClearMore.setTransportType(entity.getName());
			}
		}else{
			//运输类型
			oaReportClearMore.setTransportType(dictEntity1.getValueName());
		}
		/*
		 * 返单类别
		 */
		DataDictionaryValueEntity dictEntity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.RETURN_BILL_TYPE, waybillEntity.getReturnBillType());
		if(dictEntity == null){
			//若数据字典为空，则传入常量
			oaReportClearMore.setReturnBillType(UNKNOWN_TYPE);
		}else{
			//返单类别
			oaReportClearMore.setReturnBillType(dictEntity.getValueName());
		}
		//托运人
		oaReportClearMore.setShipper(waybillEntity.getDeliveryCustomerContact());
		//产品类型
		oaReportClearMore.setTransportProduct(productService4Dubbo.getProductByCache(waybillEntity.getProductCode(), null).getName());	//产品类型
		/*
		 * 配载类型
		 */
		DataDictionaryValueEntity dictEntity2 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.ASSEMBLE_TYPE, waybillEntity.getLoadMethod());
		if(dictEntity2 == null){
			//若数据字典为空，则传入常量
			oaReportClearMore.setStowageType(UNKNOWN_TYPE);
		}else{
			//配载类型
			oaReportClearMore.setStowageType(dictEntity2.getValueName());
		}
		//收货人电话
		if(StringUtils.isBlank(waybillEntity.getReceiveCustomerPhone())){
			//若收货人电话为空，则传入收货人手机
			oaReportClearMore.setReceiverTel(waybillEntity.getReceiveCustomerMobilephone());   
		}else{
			//若收货人电话不为空，则传入收货人电话
			oaReportClearMore.setReceiverTel(waybillEntity.getReceiveCustomerPhone());
		}
		/*
		 * 提货方式
		 */
		DataDictionaryValueEntity dictEntity3 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.PICKUP_GOODS, waybillEntity.getReceiveMethod());
		if(dictEntity3 == null){
			//若数据字典为空，则传入常量
			oaReportClearMore.setGroupSendFlag(UNKNOWN_TYPE);
		}else{
			//提货方式
			oaReportClearMore.setGroupSendFlag(dictEntity3.getValueName());
		}
		//运单备注
		oaReportClearMore.setRemark(waybillEntity.getTransportationRemark());
		//重量
		if(null != waybillEntity.getGoodsWeightTotal()) {
			oaReportClearMore.setWeight(waybillEntity.getGoodsWeightTotal().doubleValue());
		} else {
			oaReportClearMore.setWeight(Double.valueOf(0));
		}
		//体积
		if(null != waybillEntity.getGoodsVolumeTotal()) {
			oaReportClearMore.setVloume(waybillEntity.getGoodsVolumeTotal().doubleValue());
		} else {
			oaReportClearMore.setVloume(Double.valueOf(0));
		}
		//货物名称
		oaReportClearMore.setGoods(waybillEntity.getGoodsName());
		//发货时间
		oaReportClearMore.setSendTime(DateUtils.convert(waybillEntity.getBillTime(), DateUtils.DATE_TIME_FORMAT));
		//目的站
		oaReportClearMore.setDestination(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(waybillEntity.getTargetOrgCode()));
		//收货人
		oaReportClearMore.setReceiver(waybillEntity.getReceiveCustomerContact());
		//目的站
		OrgAdministrativeInfoEntity destEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCustomerPickupOrgCode());
		if(destEntity != null){
			oaReportClearMore.setDestination(destEntity.getName());
		}
		//收货部门
		OrgAdministrativeInfoEntity recieveEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getReceiveOrgCode());
		if(recieveEntity != null){
			//收货部门标杆编码
			oaReportClearMore.setReceivingDeptID(recieveEntity.getUnifiedCode());
			//收货部门名称
			oaReportClearMore.setReceivingDept(recieveEntity.getName());
		}
		/*
		 * 付款方式
		 */
		DataDictionaryValueEntity dictEntity4 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE, waybillEntity.getPaidMethod());
		if(dictEntity4 == null){
			//若数据字典为空，则传入常量
			oaReportClearMore.setPayType(UNKNOWN_TYPE);
		}else{
			//付款方式
			oaReportClearMore.setPayType(dictEntity4.getValueName());
		}
		//保险价值，运单中该字段的值允许为空
		if(null != waybillEntity.getInsuranceFee()) {
			oaReportClearMore.setInsuranceMoney(waybillEntity.getInsuranceFee().doubleValue());
		} else {
			oaReportClearMore.setInsuranceMoney(Double.valueOf(0));
		}
		//包装
		oaReportClearMore.setGoodsPacking(waybillEntity.getGoodsPackage());
		//运费总额
		oaReportClearMore.setTotal(waybillEntity.getTotalFee().doubleValue());
		/*
		 * 事件经过
		 */
		//拼接多货流水号
		StringBuffer serialNoSb = new StringBuffer();
		for(int i = 0;i < moreGoodsList.size();i++){
			UnloadDiffReportDetailEntity serialNo = moreGoodsList.get(i);
			if(i + 1 == moreGoodsList.size()){
				serialNoSb.append(serialNo.getSerialNo());
				serialNoSb.append("。");
			}else{
				serialNoSb.append(serialNo.getSerialNo());
				serialNoSb.append("、");
			}
		}
		//oa事件经过长度2000/3
		String event="";
		if(StringUtils.equals(tempEntity.getUnloadType(), UnloadConstants.UNLOAD_TASK_TYPE_SCEXPRESS)){
			event="自动差错上报，二程接驳，司机卸车多货"+"；流水号：" + serialNoSb.toString();
		}else{ 
			event = UnloadConstants.UNLOAD_MORE_GOODS_REPORT_OA_EVENT + "；流水号：" + serialNoSb.toString();
		}
		
		if(event.length() > ConstantsNumberSonar.SONAR_NUMBER_650){
			event = event.substring(0, ConstantsNumberSonar.SONAR_NUMBER_650) + "....";
		}
		
		
		oaReportClearMore.setEventReport(event);
		//件数
		oaReportClearMore.setGoodsCount(waybillEntity.getGoodsQtyTotal());
		//多货件数
		oaReportClearMore.setMoreGoodsCount(moreGoodsList.size());
		//多货类型
		oaReportClearMore.setMoreGoodsType(UnloadConstants.UNLOAD_MORE_GOODS_REPORT_OA_DIFF_TYPE);
		//开单部门
		OrgAdministrativeInfoEntity createEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCreateOrgCode());
		if(createEntity != null){
			//开单部门标杆编码
			oaReportClearMore.setSheetDeptId(createEntity.getUnifiedCode());
			//开单部门名称
			oaReportClearMore.setSheetDept(createEntity.getName());
		}
		/*
		 * 有货无交接的类型(经手部门发现、非经手部门发现)，默认为经手部门发现
		 */
		oaReportClearMore.setHaveGoodsNoReplay(UnloadConstants.GOODS_SHOULD_BE_HERE);
		//发货客户编码
		oaReportClearMore.setDeliveryCustomerCode(waybillEntity.getDeliveryCustomerCode());
		//收货客户编码
		oaReportClearMore.setReceiveCustomerCode(waybillEntity.getReceiveCustomerCode());
		//差异流水号,以逗号分割，用于解析
		StringBuffer s = new StringBuffer(ConstantsNumberSonar.SONAR_NUMBER_100);
		for(UnloadDiffReportDetailEntity entity: moreGoodsList){
			s.append(entity.getSerialNo()).append(",");
		}
		if(null != s && s.length() > 0){
			oaReportClearMore.setSerialNoList(s.substring(0, s.length()-1));
		}
		
		//上报oa多货差错单
		return fossToOAService.reportMoreGoods(oaReportClearMore);
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
	private OrgAdministrativeInfoEntity queryPreviousOrgForUnloadDiff(UnloadDiffReportEntity tempEntity){
		//卸车任务ID
		String unloadTaskId = tempEntity.getUnloadTaskId();
		//获取卸车任务实体
		UnloadTaskEntity unloadTask = this.unloadTaskService.queryUnloadTaskBaseInfoById(unloadTaskId);
		if(unloadTask != null){
			//卸车类型
			String unloadType = unloadTask.getUnloadType();
			//获取卸车任务下的单据
			List<UnloadBillDetailEntity> billList = unloadTaskService.queryUnloadTaskBillDetailListById(unloadTaskId);
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
					//调用交接单服务，获取出发部门code
					HandOverBillEntity handOverBill = this.handOverBillService.queryHandOverBillByNo(handOverBillNo);
					if(handOverBill != null){
						//出发部门code
						String departOrgCode = handOverBill.getDepartDeptCode();
						//获取出发部门实体
						OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(departOrgCode);
						return orgEntity;
					}
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
					 if(connectionBill != null){
						 //出发部门code
						String departOrgCode = connectionBill.getDepartDeptCode();
						//获取出发部门实体
						OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(departOrgCode);
						return orgEntity;
					 }
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
	 *@param tempEntity 卸车差异报告实体
	 *@return 装车任务编号
	 *@date  2015-11-20上午10:51:50
	 *@author 257220
	 */
	private String queryLoadTaskNo(UnloadDiffReportEntity tempEntity){
		if(tempEntity == null){
			return null;
		}
		//卸车任务id
		String unloadTaskId = tempEntity.getUnloadTaskId();
		//获取卸车任务实体
		UnloadTaskEntity unloadTask = this.unloadTaskService.queryUnloadTaskBaseInfoById(unloadTaskId);
		//任务类型（长短途）
		String unloadType = unloadTask.getUnloadType();
		String billNo = "";//单据编号
		UnloadBillDetailEntity billEntity = null;
		//获取卸车任务下的单据列表
		List<UnloadBillDetailEntity> billList = this.unloadTaskService.queryUnloadTaskBillDetailListById(unloadTaskId);
		if(CollectionUtils.isNotEmpty(billList)){
			billEntity  = billList.get(0);
		}
		if(billEntity == null){
			return null;
		}
		billNo = billEntity.getBillNo();
		//如果为短途卸车或接送货卸车
		if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE)){
			//调取配载单服务 获取交接单编号
			List<QueryHandOverBillDto> handOverBillDtos =  vehicleAssembleBillService.queryHandOverBillListByVNo(billNo);
			if(CollectionUtils.isNotEmpty(handOverBillDtos)){
				String handOverBillNo = handOverBillDtos.get(0).getHandOverBillNo();
				//获取交接单实体
				HandOverBillEntity handOverBill = this.handOverBillService.queryHandOverBillByNo(handOverBillNo);
				if(handOverBill != null){
					return handOverBill.getLoadTaskNo();
				}
			}
		}else if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_SHORT_DISTANCE)){
			//获取交接单实体
			HandOverBillEntity handOverBill = this.handOverBillService.queryHandOverBillByNo(billNo);
			if(handOverBill != null){
				return handOverBill.getLoadTaskNo();
			}
		}else if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_SCEXPRESS)){//二程接驳
			 ConnectionBillEntity  connectionBill = this.connectionBillService.queryConnectionBillByNo(billNo);
			 if(connectionBill != null){
				 return connectionBill.getLoadTaskNo();
			 }
		}
		return null;
	}
	/**
	 *@param 
	 *@return 装车人所在部门
	 *@date  2015-11-20上午10:51:50
	 *@author 257220
	 */
	private String obtainResponsibDepart(UnloadDiffReportEntity tempEntity){
		String loadTaskNo = this.queryLoadTaskNo(tempEntity);
		if(loadTaskNo == null){
			return null;
		}
		//获取装车任务id
		String loadTaskId = this.loadTaskQueryService.getLoadTaskByTaskNo(loadTaskNo).getId();
		//查询装车人
		List<LoaderParticipationEntity> loadersEntitys = this.loadTaskQueryService.queryLoaderByTaskId(loadTaskId);
		if(CollectionUtils.isNotEmpty(loadersEntitys)){
			for (LoaderParticipationEntity loaderParticipationEntity : loadersEntitys) {
				if(StringUtils.equals(loaderParticipationEntity.getBeCreator(),"Y")){
					String loader = loaderParticipationEntity.getLoaderCode();
						if(StringUtils.isNotEmpty(loader)){
							EmployeeEntity emp = this.employeeService.queryEmployeeByEmpCode(loader);
							if(emp !=null){
								return emp.getOrgCode();
							}
						}
				}
			}
		}
		return null;
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
			unloadDiffReportDao.updateUnloadDiffReportDetail(lessGoodsReportDetail);
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
	 * 获取少货未找到的，某类型(清仓、卸车)少货差错单编号
	 * @author 045923-foss-shiwei
	 * @date 2013-7-3 下午3:11:46
	 */
	@Override
	public List<String> queryNoFoundLackGoodsOAErrorNo(String reportType,Date reportOATime){
		return unloadDiffReportDao.queryNoFoundLackGoodsOAErrorNo(reportType, reportOATime);
	}

	/**
	 * 查询某少货差错下的未找到的流水号
	 * @author 045923-foss-shiwei
	 * @date 2013-7-6 上午10:54:50
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadService#queryNoFoundLackGoodsDetailByOAErrorNo(java.lang.String)
	 */
	@Override
	public List<LackGoodsFoundEntity> queryNoFoundLackGoodsDetailByOAErrorNo(
			String oaErrorNo) {
		return unloadDiffReportDao.queryNoFoundLackGoodsDetailByOAErrorNo(oaErrorNo);
	}

	/**
	 * 少货找到后，更新少货信息
	 * @author 045923-foss-shiwei
	 * @date 2013-7-6 上午10:55:12
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadService#updateUnloadLackGoodsInfo(com.deppon.foss.module.transfer.unload.api.shared.domain.LackGoodsFoundEntity)
	 */
	@Override
	public int updateUnloadLackGoodsInfo(LackGoodsFoundEntity entity) {
		unloadDiffReportDao.updateUnloadLackGoodsInfo(entity);
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
		
		unloadDiffReportDao.addReportOaErrorLog(logEntity);
	}
	

	/** 
	 * <p>通过oa差错编号查询存在于少货找到上报记录表中的少货差错详情</p> 
	 * @author 148246-foss-sunjianbo
	 * @date 2014-10-21 下午7:58:08
	 * @param oaErrorNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadService#queryLackGoodsDetailByOAErrorNo(java.lang.String)
	 */
	@Override
	public List<LackGoodsFoundEntity> queryLackGoodsDetailByOAErrorNo(
			String oaErrorNo) {
		return unloadDiffReportDao.queryLackGoodsDetailByOAErrorNo(oaErrorNo);
	}

	public void setLoadTaskQueryService(ILoadTaskQueryService loadTaskQueryService) {
		this.loadTaskQueryService = loadTaskQueryService;
	}
	
	 /**通过运单号查询所有卸车流水信息(流水、时间)*/
	@Override
	public List<UnloadSerialDetaiDto> queryUnloadSerialDetailByWaybillNo(String waybillNo){
		 return pdaUnloadTaskDao.queryUnloadSerialDetailByWaybillNo(waybillNo);
	 }
	
/**
 * @author 283244
 * 上报QMS实体方法（第二版）
 * **/
	@Override
	public int executeUnloadDiffReportToQMS(
			ReportQMSDataEntity reportQMSDataEntity) {
		if(reportQMSDataEntity==null){
			return 0;
		}
		OaReportClearless oclmsg;
		String  reportlesss=reportQMSDataEntity.getOaReportClearlessmsg();
		if(reportlesss!=null){
		JSONObject jsonObject = JSONObject.fromObject(reportlesss);
		oclmsg = (OaReportClearless) JSONObject.toBean(jsonObject, OaReportClearless.class);
		}else{
		oclmsg=null;
		}
		
		
		OrgAdministrativeInfoEntity ldormsg;
		String  loadorgg=reportQMSDataEntity.getLoadOrgmsg();
		if(loadorgg!=null){
		JSONObject jsonObject = JSONObject.fromObject(loadorgg);
	    ldormsg=(OrgAdministrativeInfoEntity) JSONObject.toBean(jsonObject, OrgAdministrativeInfoEntity.class);
		}else{
		ldormsg=null;
		}
		
		OrgAdministrativeInfoEntity  ldenmsg;
		String unloadEntity=reportQMSDataEntity.getUnloadEntitymsg();
		if(unloadEntity!=null){
			JSONObject jsonObject = JSONObject.fromObject(unloadEntity);
			ldenmsg=(OrgAdministrativeInfoEntity) JSONObject.toBean(jsonObject, OrgAdministrativeInfoEntity.class);
		}else{
			ldenmsg=null;
		}
		
	//try TODO
		try{
			lostWarningAnalyData.analyWarningData_unloadData(oclmsg, ldormsg, ldenmsg);
		}catch (Exception e) {
			LOGGER.error("上报QMS丢货数据出现异常:"+e.getMessage());
	}
		return 1;
}
}
