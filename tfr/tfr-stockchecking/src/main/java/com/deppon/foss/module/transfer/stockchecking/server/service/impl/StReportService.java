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
 *  PROJECT NAME  : tfr-stockchecking
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/server/service/impl/StReportService.java
 *  
 *  FILE NAME          :StReportService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * ISSUE-3248
 * 
 * 
 * 
 * 
 * 
 * 1、绝对不支持同时处理多个运单号的操作，否则后台处理量过大。
 *2、增加运单号查询，支持autocomplete操作，不需要点击查询按钮，输入时自动过滤出运单号
 *3、做成2级Grid
 *3.1、点击一个运单号可以全选此运单下的所有流水号
 *3.2、可以对一个运单号下所勾选的“未处理”状态的流水号进行批量备注
 *3.3、同时保留针对单一流水号进行备注的功能
 */
package com.deppon.foss.module.transfer.stockchecking.server.service.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToOAService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportClearMore;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportClearless;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.FeedbackDto;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferDetailDao;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferReportDao;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStOperatorDao;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStResultListDao;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao;
import com.deppon.foss.module.transfer.stockchecking.api.server.service.IStReportService;
import com.deppon.foss.module.transfer.stockchecking.api.server.service.ITaskReportService;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferDetailEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferReportEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StOperatorEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StResultListEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StDifferDetailDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StReportDetailDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StReportDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StWaybillDetailDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.vo.StReportVO;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadDiffReportService;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadService;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.LackGoodsFoundEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 处理清仓差异报告业务
 * @author foss-wuyingjie
 * @date 2012-12-25 上午10:51:44
 */
public class StReportService implements IStReportService {
	private static final Logger LOGGER = LoggerFactory.getLogger(StReportService.class);
	
	private IStTaskDao stTaskDao;
	private IStDifferReportDao stDifferReportDao;
	private IStDifferDetailDao stDifferDetailDao;
	private IStOperatorDao stOperatorDao;
	private IStResultListDao stResultListDao;
	
	private ITfrCommonService tfrCommonService;
	private IStockService stockService;
	private ICalculateTransportPathService calculateTransportPathService;
	private IFOSSToOAService fossToOAService;
	private IWaybillManagerService waybillManagerService;
	@Resource
	private IProductService productService4Dubbo;
	private IProductService productService;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private IConfigurationParamsService configurationParamsService;
	
	private ITaskReportService taskReportService;
	/** 综合管理 组织信息 Service*/
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	//签收接口
	private ISignDetailService signDetailService;
	//调用卸车接口，用于其他部门少货找到处理
	private IUnloadService unloadService;
	
	private IOutfieldService outfieldService;
	
	private IUnloadDiffReportService unloadDiffReportService;
	
	private IDataDictionaryValueService dataDictionaryValueService;
	
	
	/**
	 * 获取快递员车辆绑定的开单营业部 
	 */
    private IExpressVehiclesService expressVehiclesService;
	public void setExpressVehiclesService(
			IExpressVehiclesService expressVehiclesService) {
		this.expressVehiclesService = expressVehiclesService;
	}
	/**
	 * 
	 * 和判断登录的是不是快递员
	 */
	private ICommonExpressEmployeeService commonExpressEmployeeService;
	public void setCommonExpressEmployeeService(
			ICommonExpressEmployeeService commonExpressEmployeeService) {
		this.commonExpressEmployeeService = commonExpressEmployeeService;
	}
	/**
	 * @param unloadDiffReportService the unloadDiffReportService to set
	 */
	public void setUnloadDiffReportService(
			IUnloadDiffReportService unloadDiffReportService) {
		this.unloadDiffReportService = unloadDiffReportService;
	}
	public void setStResultListDao(IStResultListDao stResultListDao) {
		this.stResultListDao = stResultListDao;
	}
	
	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}
	public void setUnloadService(IUnloadService unloadService) {
		this.unloadService = unloadService;
	}

	public void setSignDetailService(ISignDetailService signDetailService) {
		this.signDetailService = signDetailService;
	}

	public void setStTaskDao(IStTaskDao stTaskDao) {
		this.stTaskDao = stTaskDao;
	}

	public void setStDifferReportDao(IStDifferReportDao stDifferReportDao) {
		this.stDifferReportDao = stDifferReportDao;
	}

	public void setStDifferDetailDao(IStDifferDetailDao stDifferDetailDao) {
		this.stDifferDetailDao = stDifferDetailDao;
	}
	
	public void setStOperatorDao(IStOperatorDao stOperatorDao) {
		this.stOperatorDao = stOperatorDao;
	}
	
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}
	
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	
	public void setCalculateTransportPathService(ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}
	
	public void setFossToOAService(IFOSSToOAService fossToOAService) {
		this.fossToOAService = fossToOAService;
	}
	
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	
//	public void setProductService(IProductService productService) {
//		this.productService = productService;
//	}
	
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	public void setTaskReportService(ITaskReportService taskReportService) {
		this.taskReportService = taskReportService;
	}
	
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}
	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:52:06
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStReportService#queryStReportDtoList(com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StReportDto, int, int)
	 */
	@Override
	public List<StReportDto> queryStReportDtoList(StReportDto stReportDto, int start, int limit) {
		OrgAdministrativeInfoEntity orgEntity = null;
		CurrentInfo user = FossUserContext.getCurrentInfo();//获取当前登录的信息
		if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null){//判断是不是快递员
			String code = expressVehiclesService.queryExpressVehiclesByCode(user.getEmpCode());//获取快递员绑定营业部的编号
			OrgAdministrativeInfoEntity expressOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(code);
			orgEntity=this.getBigOrg(expressOrgEntity);
			stReportDto.setOperator(user.getEmpCode());
		}else{
			orgEntity=this.getBigOrg(FossUserContext.getCurrentDept());
		}
		stReportDto.setCurrentDeptCode(orgEntity.getCode());
		List<StReportDto> list = stDifferReportDao.queryStReportDtoList(stReportDto, start, limit);
		
		for(StReportDto dto: list){
//			获取清仓人信息
			List<StOperatorEntity> operatorsList = stOperatorDao.queryOperatorsByStTaskId(dto.getTaskId());
			StringBuffer operators = new StringBuffer();
			for(StOperatorEntity o: operatorsList){
				operators.append(o.getEmpName()).append(",");
			}
			String opers = operators.toString();
			if(StringUtils.endsWith(opers, ",")){
				opers = StringUtils.left(opers, opers.length() - 1);
			}
			dto.setOperator(opers);
			//设置行政区
			String receiveMethod = dto.getReceiveMethod();//获取提货方式
			String districtName = dto.getDistrictName();//获取分区名称
			if("PICKUP".equals(receiveMethod)){
				dto.setAdministrativeRegion("自提区");
			}else if("DELIVER".equals(receiveMethod)){
				if("".equals(districtName) || districtName==null){
					dto.setAdministrativeRegion("—");
				}else{
					dto.setAdministrativeRegion(districtName);
				}
			}else{
				dto.setAdministrativeRegion("—");
			}
			//设置件区
			int startQty = dto.getStartQty();//起始件数
			int entQty = dto.getEndQty();//结束件数
			if(startQty != 0 && entQty != 0){
				dto.setPieceRegion(startQty+"—"+entQty);
			}else{
				dto.setPieceRegion("—");
			}
		}
		
		return list;
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:52:11
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStReportService#queryStReportDtoListCount(com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StReportDto)
	 */
	@Override
	public Long queryStReportDtoListCount(StReportDto stReportDto) {
		OrgAdministrativeInfoEntity orgEntity = null;
		CurrentInfo user = FossUserContext.getCurrentInfo();//获取当前登录的信息
		if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null){//判断是不是快递员
			String code = expressVehiclesService.queryExpressVehiclesByCode(user.getEmpCode());//获取快递员绑定营业部的编号
			OrgAdministrativeInfoEntity expressOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(code);
			orgEntity=this.getBigOrg(expressOrgEntity);
			stReportDto.setOperator(user.getEmpCode());
		}else{
			orgEntity=this.getBigOrg(FossUserContext.getCurrentDept());
		}
		stReportDto.setCurrentDeptCode(orgEntity.getCode());
		return stDifferReportDao.queryStReportDtoListCount(stReportDto);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:52:16
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStReportService#queryStReportDetailDtoListById(java.lang.String)
	 */
	@Override
	public List<StReportDetailDto> queryStReportDetailDtoListById(String stReportId) {
	
		return stDifferDetailDao.queryStReportDetailDtoListById(stReportId);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:52:21
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStReportService#queryStReportRelativeDetailDtoListById(java.lang.String)
	 */
	@Override
	public List<StReportDetailDto> queryStReportRelativeDetailDtoListById(String stReportId) {
		Date limitedDay = DateUtils.addDays(Calendar.getInstance().getTime(), -LoadConstants.SONAR_NUMBER_30);
		
		return stDifferDetailDao.queryStReportRelativeDetailDtoList(stReportId, limitedDay, TransferConstants.GOODS_STATUS_LACK, TransferConstants.GOODS_STATUS_SURPLUS_ERROR_GOODSAREA);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:52:27
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStReportService#updateReportDetail(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferDetailEntity, java.lang.String, java.lang.String)
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void updateReportDetail(StDifferDetailEntity stDifferDetail, String userName, String userCode) {
/*
 *      判断清仓差异报告结果规则
 *      1、清仓结果状态为"正常"的，并且在快照表中存在的为正常的清仓结果,不作处理
 *      2、在清仓结果中不存在于快照表中件，为"少货"情况
 *      3、清仓结果状态为"多货"的
 *      3.1、查询本部门非本货区是否存在此件
 *      3.1.1、如果存在，差异结果为"放错货区"
 *      3.1.2、如果不存在，在本部门入库此件，("并同时从上一部门出库，并通知上一部门;3.1.2.2.1此动作在入库服务中实现);查询此件是否在当前对应的走货路径上
 *       3.1.2.1、如果在走货路径上，清仓差异结果为"多货-夹带" 
 *       3.1.2.2、如果不在走货路径上，清仓差异结果为"多货-异地夹带"
 *        3.1.2.2.1、更新此件对应的走货路径
 */
		String isInStock = stDifferDetail.getIsInStock();
		StDifferReportEntity reportEntity = stDifferReportDao.queryStReportEntityById(stDifferDetail.getStDifferReportId());
		StTaskEntity taskEntity = stTaskDao.queryStTaskByReportId(stDifferDetail.getStDifferReportId());
		String remark = stDifferDetail.getRemark();
		String differenceReason=stDifferDetail.getDifferenceReason();
		stDifferDetail = stDifferDetailDao.queryStDifferDetailEntityById(stDifferDetail.getId());
		//是否已签收
		String checkSerialNoIsSign = signDetailService.querySerialNoIsSign(stDifferDetail.getWaybillNo(), stDifferDetail.getSerialNo());
//		如果为"夹带"以及"多货夹带"的，重新判断一次当前货件的当前状态,并作后续处理
		if(StringUtils.equals(TransferConstants.GOODS_STATUS_SURPLUS_CARRY, stDifferDetail.getDifferenceType()) ||
		   StringUtils.equals(TransferConstants.GOODS_STATUS_SURPLUS_CARRY_OTHERS, stDifferDetail.getDifferenceType())){
//			    3.1 查询本部门非本货区是否存在此件
			boolean isExistOtherGoodsArea = stockService.isExistOtherGoodsAreaStock(reportEntity.getDeptcode(), stDifferDetail.getWaybillNo(), stDifferDetail.getSerialNo(), reportEntity.getGoodsAreaCode());
//				3.1.1 如果存在，差异结果为"放错货区" 
			if(!isExistOtherGoodsArea){
//				3.1.2、如果不存在，在本部门入库此件，("并同时从上一部门出库，并通知上一部门;3.1.2.2.1此动作在入库服务中实现);查询此件是否在当前对应的走货路径上
				//界面上选择了入库
				if(StringUtils.equals(FossConstants.YES, isInStock)) {
					InOutStockEntity stockEntity = new InOutStockEntity();
					stockEntity.setWaybillNO(stDifferDetail.getWaybillNo());
					stockEntity.setSerialNO(stDifferDetail.getSerialNo());
					stockEntity.setOrgCode(reportEntity.getDeptcode());
					stockEntity.setOperatorName(taskEntity.getCreatorName());
					stockEntity.setOperatorCode(taskEntity.getCreatorCode());
					stockEntity.setInOutStockType(StockConstants.STOCKCHECKING_MORE_GOODS_IN_STOCK_TYPE);
					
					stockService.inStockPC(stockEntity);
					
					//更新库位信息
					StResultListEntity reslutEntity = stResultListDao.queryStResultEntity(taskEntity.getId(),stockEntity.getWaybillNO(),stockEntity.getSerialNO());
					//PDA清仓任务且库位号不为空时更新库位信息
					try{
						if(StringUtils.equals("Y", taskEntity.getIspda()) && StringUtils.isNotEmpty(reslutEntity.getPositionNo())) {
							List<InOutStockEntity> inStockList = new ArrayList<InOutStockEntity>();
							inStockList.add(stockEntity);
							//处理人
							if(FossUserContext.getCurrentUser() != null) {
								userCode = FossUserContext.getCurrentInfo().getEmpCode();
								userName = FossUserContext.getCurrentInfo().getEmpName();
							}
							stockService.updateStockStockPosition(inStockList, reslutEntity.getPositionNo(), reportEntity.getDeptcode(), userCode, userName);
						}
					} catch(Exception e) {
						e.printStackTrace();
						LOGGER.info("处理清仓差异时，更新库位失败！差异编号：D" + taskEntity.getTaskNo()+",运单号：" + stockEntity.getWaybillNO() + ",流水号：" + stockEntity.getSerialNO() +",库位号：" + reslutEntity.getPositionNo());
					}
				}
//					3.1.2.2、如果不在走货路径上，清仓差异结果为"多货-异地夹带"
				FeedbackDto feedbackDto = calculateTransportPathService.getNextOrgAndTime(stDifferDetail.getWaybillNo(), stDifferDetail.getSerialNo(), reportEntity.getDeptcode());
				
				if(TransportPathConstants.STATUS_WRONG == feedbackDto.getResult()){
					stDifferDetail.setDifferenceType(TransferConstants.GOODS_STATUS_SURPLUS_CARRY_OTHERS);
				}else{
//					3.1.2.1、如果在走货路径上，清仓差异结果为"多货-夹带"
					stDifferDetail.setDifferenceType(TransferConstants.GOODS_STATUS_SURPLUS_CARRY);
				}
			}
		}else if(StringUtils.equals(TransferConstants.GOODS_STATUS_LACK, stDifferDetail.getDifferenceType())
				&& StringUtils.equals(FossConstants.NO, checkSerialNoIsSign)
				&& StringUtils.equals(FossConstants.YES, isInStock)){
			//未签收出库进行入库操作
			InOutStockEntity stockEntity = new InOutStockEntity();
			stockEntity.setWaybillNO(stDifferDetail.getWaybillNo());
			stockEntity.setSerialNO(stDifferDetail.getSerialNo());
			stockEntity.setOrgCode(reportEntity.getDeptcode());
			stockEntity.setOperatorName(taskEntity.getCreatorName());
			stockEntity.setOperatorCode(taskEntity.getCreatorCode());
			stockEntity.setInOutStockType(StockConstants.STOCKTAKING_LOSE_GOODS_FOUND_IN_STOCK_TYPE);
			
			stockService.inStockPC(stockEntity);
		}
		//上报OA上限
		int oaReportHourRule = 0;
		//清仓差异报告部门
		String deptCode = reportEntity.getDeptcode();
		try{
			ConfigurationParamsEntity defaultBizHourSlice = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , ConfigurationParamsConstants.TFR_PARM__ST_REPORT_OA_ERROR, deptCode);
			oaReportHourRule = Integer.valueOf(defaultBizHourSlice.getConfValue()).intValue();
		}catch(Exception e){
			LOGGER.error("获取配置参数失败", ExceptionUtils.getFullStackTrace(e));
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizCode());
			jobProcessLogEntity.setRemark("更新差异报告明细时获取配置参数失败失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		}
		//处理时间 
		Date currentDate = new Date();
		//差异报告创建时间
		Date createDate = reportEntity.getCreateTime();
		Long differ = com.deppon.foss.util.DateUtils.getMinuteDiff(createDate,currentDate);
		boolean isRightTime = differ - oaReportHourRule*LoadConstants.SONAR_NUMBER_60 > 0 ? true : false;
		
//		少货状态的情况，需查看本次是否为当前清仓差异报告中最后一条待处理的"少货"差异明细记录，若是，尝试进行调用"OA少货找到接口"
		if(StringUtils.equals(TransferConstants.GOODS_STATUS_LACK, stDifferDetail.getDifferenceType())
				&& isRightTime){
//				查看本次是否为当前清仓差异报告中最后一条待处理的"少货"差异明细记录
			//List<StDifferDetailEntity> detailList = stDifferDetailDao.queryStReportDetail(stDifferDetail.getStDifferReportId(), TransferConstants.GOODS_STATUS_LACK, TransferConstants.STOCK_CHECKING_REPORT_DOING);
			//if(CollectionUtils.isNotEmpty(detailList) && StringUtils.equals(stDifferDetail.getId(), detailList.get(0).getId())){
			//处理一票单的最后一件少货时上报OA少货找到
			if(isLastLackOfWaybill(stDifferDetail.getStDifferReportId(),stDifferDetail.getWaybillNo())) {
				try{
					if(FossUserContext.getCurrentUser() != null) {
						userCode = FossUserContext.getCurrentInfo().getEmpCode();
						userName = FossUserContext.getCurrentInfo().getEmpName();
					}
				} catch(Exception e) {
					e.printStackTrace();
					LOGGER.info("处理清仓差异少货获取当前登录人异常");
				}
				
				//OA标杆编码
				String oaDeptCode = "";
				OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(reportEntity.getDeptcode());
				if(orgEntity != null){
					oaDeptCode = orgEntity.getUnifiedCode();
				}
				
				List<LackGoodsFoundEntity> list = new ArrayList<LackGoodsFoundEntity>();
				
				//是否在中间表中，若不在则上报OA少货找到
				if(StringUtils.isNotBlank(stDifferDetail.getOaErrorNo()))
				{
					/*modify by 148246-foss-sunjianbo 2014-10-21*/
					//list = unloadService.queryNoFoundLackGoodsOAErrorNo(stDifferDetail.getOaErrorNo());
					list = unloadService.queryLackGoodsDetailByOAErrorNo(stDifferDetail.getOaErrorNo());
				}
				if((list != null && list.size() == 0) || list == null)
				{
					fossToOAService.reportLessGoodsFound(stDifferDetail.getOaErrorNo(), userCode, userName, oaDeptCode);
					LOGGER.info("上报OA少货找到成功！单号：" + stDifferDetail.getWaybillNo());
				}
			}
		}
		
//		更新清仓差异明细的备注信息、处理时间、处理状态
		StDifferDetailEntity stDifferDetailEntity = new StDifferDetailEntity();
		stDifferDetailEntity.setId(stDifferDetail.getId());
		stDifferDetailEntity.setRemark(remark);
		stDifferDetailEntity.setDifferenceReason(differenceReason);
		stDifferDetailEntity.setHandleStatus(TransferConstants.STOCK_CHECKING_REPORT_DONE);
		stDifferDetailEntity.setHandleTime(Calendar.getInstance().getTime());
		stDifferDetailEntity.setUserCode(userCode);
		
		stDifferDetailDao.updateByPrimaryKeySelective(stDifferDetailEntity);
		
//		判断此条清仓差异明细数据是否为此清仓差异报告的最后一条"未处理"的数据
		long count = stDifferDetailDao.queryStReportDetailCountByReportId(stDifferDetail.getId(), stDifferDetail.getStDifferReportId(), TransferConstants.STOCK_CHECKING_REPORT_DOING);
//		若是，则更新此清仓差异报告的处理状态
		if(count == 0){
			StDifferReportEntity stDifferReportEntity = new StDifferReportEntity();
			stDifferReportEntity.setId(stDifferDetail.getStDifferReportId());
			stDifferReportEntity.setHandleStatus(TransferConstants.STOCK_CHECKING_REPORT_DONE);
			
			stDifferReportDao.updateByPrimaryKeySelective(stDifferReportEntity);
		}
	}
	/**
	 * @author niuly
	 * @date 2013-6-28
	 * @param map
	 * @function 根据任务id，运单号，差异明细处理DOING状态和少货判断是否为最后一件
	 * @return
	 */
	private boolean isLastLackOfWaybill(String differReportId, String waybillNo) {
		int lackNum = stDifferDetailDao.queryCountLastLackOfWaybill(differReportId, waybillNo);
		return lackNum == 1 ? true : false;
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:52:36
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStReportService#executeStReportTask(java.util.Date, java.util.Date, int, int)
	 */
	@Override
	public void executeStReportTask(Date bizJobStartTime, Date bizJobEndTime, int threadNo, int threadCount) {
//		查询待处理的清仓任务
		List<StTaskEntity> stTaskList = stTaskDao.queryStTaskByBatch(bizJobStartTime, bizJobEndTime, threadNo, threadCount);
//  	循环清仓任务，并获取对应的清仓快照列表和比对结果列表
/*
 *      判断清仓差异报告结果规则
 *      1、清仓结果状态为"正常"的，并且在快照表中存在的为正常的清仓结果,不作处理
 *      2、在清仓结果中不存在于快照表中件，为"少货"情况
 *      3、清仓结果状态为"多货"的
 *      3.1、查询本部门非本货区是否存在此件
 *      3.1.1、如果存在，差异结果为"放错货区"
 *      3.1.2、如果不存在，在本部门入库此件，("并同时从上一部门出库，并通知上一部门;3.1.2.2.1此动作在入库服务中实现);查询此件是否在当前对应的走货路径上
 *       3.1.2.1、如果在走货路径上，清仓差异结果为"多货-夹带" 
 *       3.1.2.2、如果不在走货路径上，清仓差异结果为"多货-异地夹带"
 *        3.1.2.2.1、更新此件对应的走货路径
 */
		for(StTaskEntity stTask: stTaskList){
			try{
//				生成清仓差异报告和对应的差异报告明细，如果都成功的处理，则此报告的状态标记为“已完成”TransferConstants.STOCK_CHECKING_REPORT_DONE
				taskReportService.addStTaskReport(stTask);
			}catch(Exception e){
				LOGGER.error(ExceptionUtils.getFullStackTrace(e));
				
				TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
				jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.ST_REPORT.getBizName());
				jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.ST_REPORT.getBizCode());
				jobProcessLogEntity.setExecBizId(stTask.getId());
				jobProcessLogEntity.setExecTableName("TFR.T_OPT_ST_TASK");
				jobProcessLogEntity.setRemark("保存差异报告及明细时出错");
				jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
				jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
				
				tfrCommonService.addJobProcessLog(jobProcessLogEntity);
			}
		}
	}
	
	@Override
	public void executeStOAErrorTask(Date bizJobEndTime, int threadNo, int threadCount) {
		this.stOALackErrorTask(bizJobEndTime, threadNo, threadCount);
		//this.stOAMoreErrorTask(bizJobEndTime, threadNo, threadCount);
	}
	public void stOAMoreErrorTask(Date bizJobEndTime, int threadNo, int threadCount) {
		Date beginReportOATime = null;
		try{
			ConfigurationParamsEntity configurationParamsEntity = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , ConfigurationParamsConstants.TFR_PARM__ST_REPORT_OA_BEGINE_TIME, FossConstants.ROOT_ORG_CODE);
			beginReportOATime = com.deppon.foss.util.DateUtils.convert(configurationParamsEntity.getConfValue(), "yyyy-MM-dd HH:mm:ss");
		}catch(Exception e){
			LOGGER.error("获取配置参数失败", ExceptionUtils.getFullStackTrace(e));
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizCode());
			jobProcessLogEntity.setRemark("清仓多货上报OA获取配置参数失败失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		}
		
		//查询存在未上报OA多货差错单的清仓差异报告
		List<StDifferReportEntity> stDifferReportMoreList = stDifferReportDao.queryMoreStReportForOAError(beginReportOATime, bizJobEndTime, threadNo, threadCount);
		for(StDifferReportEntity report: stDifferReportMoreList){
			Map<String, StWaybillDetailDto> waybillNoMap = new HashMap<String, StWaybillDetailDto>();
			//查询待处理的清仓差异记录，找出需上报OA差错单的数据
			List<StDifferDetailDto> stDifferDetailList = stDifferDetailDao.queryStOAErrorByBatch(report.getId());
			//通过清仓差异报告ID获取清仓任务信息
			StTaskEntity stTaskEntity = stTaskDao.queryStTaskByReportId(report.getId());
		
			String tempWaybillNo = "";	//临时保存运单号
			//此处按单号分组处理，供下面上报OA差错单使用
			for(StDifferDetailDto detail: stDifferDetailList){
				StWaybillDetailDto stWaybillDetailDto = new StWaybillDetailDto();
				if(!StringUtils.equals(tempWaybillNo, detail.getWaybillNo())){
					tempWaybillNo = detail.getWaybillNo();
					
					stWaybillDetailDto.setDeptcode(detail.getDeptcode());
					waybillNoMap.put(tempWaybillNo, stWaybillDetailDto);
				}else{
					stWaybillDetailDto = waybillNoMap.get(detail.getWaybillNo());
				}
				
				//多货
				if(StringUtils.equals(TransferConstants.GOODS_STATUS_SURPLUS_CARRY, detail.getDifferenceType()) ||
						 StringUtils.equals(TransferConstants.GOODS_STATUS_SURPLUS_CARRY_OTHERS, detail.getDifferenceType())){
					
					stWaybillDetailDto.getSurplusList().add(detail);
				}
			}
			
			for(Map.Entry<String, StWaybillDetailDto> entry : waybillNoMap.entrySet()){
				StWaybillDetailDto detail = entry.getValue();
				//获取运单信息
				WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(entry.getKey());

				//sonar-352203
				if(waybillEntity == null || detail == null || CollectionUtils.isEmpty(detail.getSurplusList())){
					continue;
				}
					//遍历多货
					for(StDifferDetailEntity surplusEntity: detail.getSurplusList()){
						//上报多货
						try{
							ResponseDto responseDto = this.reportOAMoreGoods(waybillEntity, detail, surplusEntity,stTaskEntity);
							//成功获取多货差错单后，需更新这些清仓任务明细的状态
							if(StringUtils.isNotBlank(responseDto.getErrorsNo())){
								StDifferDetailEntity newStDifferDetailEntity = new StDifferDetailEntity();
								newStDifferDetailEntity.setId(surplusEntity.getId());
								newStDifferDetailEntity.setOaErrorNo(responseDto.getErrorsNo());
								stDifferDetailDao.updateByPrimaryKeySelective(newStDifferDetailEntity);
								continue;
							}/* else {*/
								//若上报失败，更新异常信息为备注信息，且记录日志
								String message = responseDto.getMessage();
								//代码异常不更新备注
								if(responseDto.getIsException() == true || StringUtils.isEmpty(message)) {
									continue;
								}
									if(message.length() > LoadConstants.SONAR_NUMBER_300) {
										message = message.substring(0, LoadConstants.SONAR_NUMBER_300);
									}
									
									StDifferDetailEntity newStDifferDetailEntity = new StDifferDetailEntity();
									newStDifferDetailEntity.setId(surplusEntity.getId());
									newStDifferDetailEntity.setRemark(message);
									stDifferDetailDao.updateByPrimaryKeySelective(newStDifferDetailEntity);
//								}
								//记录上报失败日志。上报OA多货以及记录日志均是以运单号为单位，但上报代码却以流水号为单位循环，单位不匹配，此代码流程需要梳理。
								//因清仓上报OA多货接口已关闭，若后续放开，需重新梳理上报OA差错的逻辑
								//unloadService.addReportOaErrorLog(report.getReportCode(),waybillNo,TransferConstants.REPORT_TYPE_ST,TransferConstants.GOODS_STATUS_SURPLUS,message);
//							}
						}catch(Exception e){
							LOGGER.error(ExceptionUtils.getFullStackTrace(e));
							TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
							jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizName());
							jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizCode());
							jobProcessLogEntity.setRemark("清仓多货上报OA失败！deptCode: " + detail.getDeptcode());
							jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
							jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
							tfrCommonService.addJobProcessLog(jobProcessLogEntity);
						}
					}
					
//				}
			}
		}
		
		
	}
	public void stOALackErrorTask(Date bizJobEndTime, int threadNo, int threadCount) {
		LOGGER.info("---调用清仓stOALackErrorTask()方法开始---");
		int oaReportHourRule = LoadConstants.SONAR_NUMBER_24;
		Date beginReportOATime = null;
		int bundleNum = 0;
		try{
			ConfigurationParamsEntity defaultBizHourSlice = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , ConfigurationParamsConstants.TFR_PARM__ST_REPORT_OA_ERROR, FossConstants.ROOT_ORG_CODE);
			oaReportHourRule = Integer.valueOf(defaultBizHourSlice.getConfValue()).intValue();
			
			ConfigurationParamsEntity configurationParamsEntity = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , ConfigurationParamsConstants.TFR_PARM__ST_REPORT_OA_BEGINE_TIME, FossConstants.ROOT_ORG_CODE);
			beginReportOATime = com.deppon.foss.util.DateUtils.convert(configurationParamsEntity.getConfValue(), "yyyy-MM-dd HH:mm:ss");
			
			ConfigurationParamsEntity bundleNumParamsEntity = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , ConfigurationParamsConstants.TFR_PARM__ST_REPORT_OA_BUNDLE_NUM, FossConstants.ROOT_ORG_CODE);
			bundleNum = Integer.valueOf(bundleNumParamsEntity.getConfValue()).intValue();
			
		}catch(Exception e){
			LOGGER.error("获取配置参数失败", ExceptionUtils.getFullStackTrace(e));
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizCode());
			jobProcessLogEntity.setRemark("清仓少货上报OA获取配置参数失败失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		}
		
		//查询存在未上报OA少货差错单的清仓差异报告
		List<StDifferReportEntity> stDifferReportList = stDifferReportDao.queryLackStReportForOAError(beginReportOATime, bizJobEndTime, threadNo, threadCount, oaReportHourRule,bundleNum);
		
		for(StDifferReportEntity report: stDifferReportList){
			StDifferReportEntity entity = stDifferReportDao.queryStReportEntityById(report.getId());
			
			int oaReportHourRuleCc = LoadConstants.SONAR_NUMBER_24;
			try{
				ConfigurationParamsEntity defaultBizHourSlice1 = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , ConfigurationParamsConstants.TFR_PARM__ST_REPORT_OA_ERROR, entity.getDeptcode());
				oaReportHourRuleCc = Integer.valueOf(defaultBizHourSlice1.getConfValue()).intValue();
			}catch(Exception e){
				LOGGER.error("获取配置参数失败", ExceptionUtils.getFullStackTrace(e));
				TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
				jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizName());
				jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizCode());
				jobProcessLogEntity.setRemark("清仓少货上报OA获取配置参数失败失败！");
				jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
				jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
				tfrCommonService.addJobProcessLog(jobProcessLogEntity);
			}
			Date createDate = entity.getCreateTime();
			Date currentDate = new Date();
			Long differ = com.deppon.foss.util.DateUtils.getMinuteDiff(createDate,currentDate);
			//小于上报时间时不上报
			/*boolean isNotToOALack = differ - oaReportHourRuleCc*60 < 0 ? true : false;
			if(isNotToOALack) {
					continue;
			}*/
			
			List<String> lackWaybillNoList = new ArrayList<String>(); 
			Map<String,String> oaErrorCodes = new HashMap<String,String>();
			Map<String, StWaybillDetailDto> waybillNoMap = new HashMap<String, StWaybillDetailDto>();
			//查询待处理的清仓差异记录，找出需上报OA差错单的数据
			List<StDifferDetailDto> stDifferDetailList = stDifferDetailDao.queryStOAErrorByBatch(report.getId());
			//通过清仓差异报告ID获取清仓任务信息
			StTaskEntity stTaskEntity = stTaskDao.queryStTaskByReportId(report.getId());
			String tempWaybillNo = "";	//临时保存运单号
			//此处按单号分组处理，供下面上报OA差错单使用
			for(StDifferDetailDto detail: stDifferDetailList){
				StWaybillDetailDto stWaybillDetailDto = new StWaybillDetailDto();
				if(!StringUtils.equals(tempWaybillNo, detail.getWaybillNo())){
					tempWaybillNo = detail.getWaybillNo();					
					stWaybillDetailDto.setDeptcode(detail.getDeptcode());
					waybillNoMap.put(tempWaybillNo, stWaybillDetailDto);
				}else{
					stWaybillDetailDto = waybillNoMap.get(detail.getWaybillNo());
				}				
				//少货情况
				if(StringUtils.equals(TransferConstants.GOODS_STATUS_LACK, detail.getDifferenceType())){
					stWaybillDetailDto.getLackList().add(detail);
				}
			}
			LOGGER.info("---调用清仓上报OA少货差错接口开始，差异报告ID为： " + report.getId() + "---");
			String waybillNo = null;
			for(Map.Entry<String, StWaybillDetailDto> entry : waybillNoMap.entrySet()){
				waybillNo = entry.getKey();
				StWaybillDetailDto detail = entry.getValue();
				//获取运单信息
				WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
				//sonar-352203
				if(waybillEntity == null || detail.getLackList().size() <= 0){
					continue;
				}
					//上报少货
					try{
						LOGGER.info("---清仓上报OA少货差错开始，单号：" + waybillNo + "---");
						ResponseDto responseDto = this.reportOALessGoods(waybillEntity, detail, stTaskEntity);
						LOGGER.info("---清仓上报OA少货差错成功，单号：" + waybillNo + ",OA差错编号为：" + responseDto.getErrorsNo() +"---");
						//成功获取少货差错单后，需更新这些清仓任务明细的状态
						if(StringUtils.isNotBlank(responseDto.getErrorsNo())){
							for(StDifferDetailEntity lackEntity: detail.getLackList()){
								StDifferDetailEntity newStDifferDetailEntity = new StDifferDetailEntity();
								newStDifferDetailEntity.setId(lackEntity.getId());
								newStDifferDetailEntity.setOaErrorNo(responseDto.getErrorsNo());
								stDifferDetailDao.updateByPrimaryKeySelective(newStDifferDetailEntity);
								LOGGER.info("---更新清仓少货明细（OA差错编号）成功，差异报告ID为：" + report.getId() +",单号为："+ waybillNo + ",流水号为："+ lackEntity.getSerialNo() +",OA差错编号为：" + responseDto.getErrorsNo());
							}
							LOGGER.info("---差异报告ID为：" + report.getId() +" 单号为"+ waybillNo + "OA差错编号为："+ responseDto.getErrorsNo()+ "更新全部完成---");
							lackWaybillNoList.add(waybillNo);
							oaErrorCodes.put(waybillNo, responseDto.getErrorsNo());
						} else {
							//若上报失败，更新异常信息为备注信息，且记录日志
							String message = responseDto.getMessage();
							if(StringUtils.isNotEmpty(message) && message.length() > LoadConstants.SONAR_NUMBER_300) {
								message = message.substring(0, LoadConstants.SONAR_NUMBER_300);
							}
							//记录上报失败日志
							unloadService.addReportOaErrorLog(entity.getReportCode(),waybillNo,TransferConstants.REPORT_TYPE_ST,TransferConstants.GOODS_STATUS_LACK,message);
							//代码异常时不更新备注
							//sonar-352203
							if(responseDto.getIsException() == true || StringUtils.isEmpty(message)) {
								continue;
							}
								for(StDifferDetailEntity lackEntity: detail.getLackList()){
									StDifferDetailEntity newStDifferDetailEntity = new StDifferDetailEntity();
									newStDifferDetailEntity.setId(lackEntity.getId());
									newStDifferDetailEntity.setRemark(message);
									stDifferDetailDao.updateByPrimaryKeySelective(newStDifferDetailEntity);
								}
//							}
						}
					}catch(Exception e){
						LOGGER.info("---清仓上报OA少货差错时，单号：" + waybillNo + "抛出异常---");
						LOGGER.error(ExceptionUtils.getFullStackTrace(e));
						TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
						jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizName());
						jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizCode());
						jobProcessLogEntity.setRemark("清仓少货上报OA失败！deptCode: " + detail.getDeptcode());
						jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
						jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
						tfrCommonService.addJobProcessLog(jobProcessLogEntity);
					}
//				}
			}	
			LOGGER.info("---调用清仓上报OA少货差错接口结束，差异报告ID为： " + report.getId() + "---");
			//对于已成功上报的OA差错单对应的货件，"少货"性质的需要逐件进行入特殊库处理
			//去掉清仓少货入库到特殊组织
			/*for(String reportWaybillNo: lackWaybillNoList){
				StWaybillDetailDto stWaybillDetailDto = waybillNoMap.get(reportWaybillNo);
				for(StDifferDetailEntity lackList: stWaybillDetailDto.getLackList()){
					try{
						InOutStockEntity stockEntity = new InOutStockEntity();
						stockEntity.setWaybillNO(reportWaybillNo);
						stockEntity.setSerialNO(lackList.getSerialNo());
						stockEntity.setOrgCode(stWaybillDetailDto.getDeptcode());
						stockEntity.setOperatorName(stTaskEntity.getCreatorName());
						stockEntity.setOperatorCode(stTaskEntity.getCreatorCode());
						stockEntity.setInOutStockType(StockConstants.LOSE_GOODS_IN_STOCK_TYPE);
						stockEntity.setInOutStockBillNO(stTaskEntity.getTaskNo());
						stockService.inStockRequiresNewTransactional(stockEntity);
					}catch(Exception e){
						LOGGER.error(ExceptionUtils.getFullStackTrace(e));
						TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
						jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizName());
						jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizCode());
						jobProcessLogEntity.setRemark("少货上报OA差异后入库出错！deptCode: " + stWaybillDetailDto.getDeptcode() + "waybillNo:" + reportWaybillNo + ", serialNo:" + lackList.getSerialNo());
						jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
						jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
						tfrCommonService.addJobProcessLog(jobProcessLogEntity);
					}
				}
			}*/
			//插入数据到临时表，用于其他部门少货找到
			for(String reportWaybillNo: lackWaybillNoList) {
				StWaybillDetailDto stWaybillDetailDto = waybillNoMap.get(reportWaybillNo);
				for(StDifferDetailEntity lackList: stWaybillDetailDto.getLackList()){
					try{
						//上报OA少货差错后，新增数据到临时表
						LackGoodsFoundEntity foundEntity = new LackGoodsFoundEntity();
						foundEntity.setId(UUIDUtils.getUUID());
						foundEntity.setWaybillNo(reportWaybillNo);
						foundEntity.setSerialNo(lackList.getSerialNo());
						foundEntity.setOaErrorNo(oaErrorCodes.get(reportWaybillNo));
						foundEntity.setLackGoodsOrgCode(entity.getDeptcode());
						foundEntity.setReportType(TransferConstants.REPORT_TYPE_ST);
						foundEntity.setReportId(entity.getId());
						foundEntity.setReportOATime(new Date());
						unloadService.addLackGoodsFoundInfo(foundEntity);
						LOGGER.info("---清仓少货新增临时表数据成功，差异报告ID为：" + report.getId() +",单号为："+ lackList.getWaybillNo() + ",流水号为："+ lackList.getSerialNo() +",OA差错编号为：" + oaErrorCodes.get(lackList.getWaybillNo()));
					}catch(Exception e){
						LOGGER.error(ExceptionUtils.getFullStackTrace(e));
						TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
						jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizName());
						jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizCode());
						jobProcessLogEntity.setRemark("少货上报OA差异后新增临时表出错！deptCode: " + entity.getDeptcode() + "waybillNo:" + reportWaybillNo + ", serialNo:" + lackList.getSerialNo());
						jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
						jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
						tfrCommonService.addJobProcessLog(jobProcessLogEntity);
					}
				}
			}
			
		}
		LOGGER.info("---调用清仓stOALackErrorTask()方法结束---");
		
		
	}
	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:52:50
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStReportService#executeStOAErrorTask(java.util.Date, int, int)
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void executeStOAErrorTask1(Date bizJobEndTime, int threadNo, int threadCount) {
		int oaReportHourRule = LoadConstants.SONAR_NUMBER_24;
		Date beginReportOATime = null;
		try{
			ConfigurationParamsEntity defaultBizHourSlice = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , ConfigurationParamsConstants.TFR_PARM__ST_REPORT_OA_ERROR, FossConstants.ROOT_ORG_CODE);
			oaReportHourRule = Integer.valueOf(defaultBizHourSlice.getConfValue()).intValue();
			
			ConfigurationParamsEntity configurationParamsEntity = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , ConfigurationParamsConstants.TFR_PARM__ST_REPORT_OA_BEGINE_TIME, FossConstants.ROOT_ORG_CODE);
			beginReportOATime = com.deppon.foss.util.DateUtils.convert(configurationParamsEntity.getConfValue(), "yyyy-MM-dd HH:mm:ss");
			
		}catch(Exception e){
			LOGGER.error("获取配置参数失败", ExceptionUtils.getFullStackTrace(e));
		}
		
		//查询存在未上报OA差错单的清仓差异报告
		List<StDifferReportEntity> stDifferReportList = stDifferReportDao.queryStReportForOAError(beginReportOATime,bizJobEndTime, threadNo, threadCount, oaReportHourRule);
		
		for(StDifferReportEntity report: stDifferReportList){
			List<String> lackWaybillNoList = new ArrayList<String>(); 
			Map<String, StWaybillDetailDto> waybillNoMap = new HashMap<String, StWaybillDetailDto>();
			//查询待处理的清仓差异记录，找出需上报OA差错单的数据
			List<StDifferDetailDto> stDifferDetailList = stDifferDetailDao.queryStOAErrorByBatch(report.getId());
			//通过清仓差异报告ID获取清仓任务信息
			StTaskEntity stTaskEntity = stTaskDao.queryStTaskByReportId(report.getId());
		
			String tempWaybillNo = "";	//临时保存运单号
			//此处按单号分组处理，供下面上报OA差错单使用
			for(StDifferDetailDto detail: stDifferDetailList){
				StWaybillDetailDto stWaybillDetailDto = new StWaybillDetailDto();
				if(!StringUtils.equals(tempWaybillNo, detail.getWaybillNo())){
					tempWaybillNo = detail.getWaybillNo();
					
					stWaybillDetailDto.setDeptcode(detail.getDeptcode());
					waybillNoMap.put(tempWaybillNo, stWaybillDetailDto);
				}else{
					stWaybillDetailDto = waybillNoMap.get(detail.getWaybillNo());
				}
				
				//处理少货情况及多货情况
				if(StringUtils.equals(TransferConstants.GOODS_STATUS_LACK, detail.getDifferenceType())){
					stWaybillDetailDto.getLackList().add(detail);
				}else if(StringUtils.equals(TransferConstants.GOODS_STATUS_SURPLUS_CARRY, detail.getDifferenceType()) ||
						 StringUtils.equals(TransferConstants.GOODS_STATUS_SURPLUS_CARRY_OTHERS, detail.getDifferenceType())){
					
					stWaybillDetailDto.getSurplusList().add(detail);
				}
			}
			
			String waybillNo = null;
			for(Map.Entry<String, StWaybillDetailDto> entry : waybillNoMap.entrySet()){
				waybillNo = entry.getKey();
				StWaybillDetailDto detail = entry.getValue();
				try{
					//获取运单信息
					WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
					if(waybillEntity != null && detail.getLackList().size() > 0){
						//上报少货
						ResponseDto responseDto = this.reportOALessGoods(waybillEntity, detail, stTaskEntity);
						//成功获取少货差错单后，需更新这些清仓任务明细的状态
						if(StringUtils.isNotBlank(responseDto.getErrorsNo())){
							for(StDifferDetailEntity lackEntity: detail.getLackList()){
								StDifferDetailEntity newStDifferDetailEntity = new StDifferDetailEntity();
								newStDifferDetailEntity.setId(lackEntity.getId());
								newStDifferDetailEntity.setOaErrorNo(responseDto.getErrorsNo());
								stDifferDetailDao.updateByPrimaryKeySelective(newStDifferDetailEntity);
							}
							
						lackWaybillNoList.add(waybillNo);
						}
					}

					if(waybillEntity != null && detail != null && CollectionUtils.isNotEmpty(detail.getSurplusList())){
						//遍历多货
						for(StDifferDetailEntity surplusEntity: detail.getSurplusList()){
							//上报多货
							ResponseDto responseDto = this.reportOAMoreGoods(waybillEntity, detail, surplusEntity,stTaskEntity);
							//成功获取多货差错单后，需更新这些清仓任务明细的状态
							if(StringUtils.isNotBlank(responseDto.getErrorsNo())){
								StDifferDetailEntity newStDifferDetailEntity = new StDifferDetailEntity();
								newStDifferDetailEntity.setId(surplusEntity.getId());
								newStDifferDetailEntity.setOaErrorNo(responseDto.getErrorsNo());
								stDifferDetailDao.updateByPrimaryKeySelective(newStDifferDetailEntity);
							}
						}
						
					}
					
				}catch(Exception e){
					LOGGER.error(ExceptionUtils.getFullStackTrace(e));
					
					TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
					jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizName());
					jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizCode());
					jobProcessLogEntity.setRemark("调用OA接口出错！,运单编号为：" + waybillNo);
					jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
					jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
					
					tfrCommonService.addJobProcessLog(jobProcessLogEntity);
				}

				
				//对于已成功上报的OA差错单对应的货件，"少货"性质的需要逐件进行入特殊库处理
				for(String reportWaybillNo: lackWaybillNoList){
					StWaybillDetailDto stWaybillDetailDto = waybillNoMap.get(reportWaybillNo);
					for(StDifferDetailEntity lackList: stWaybillDetailDto.getLackList()){
						try{
							InOutStockEntity stockEntity = new InOutStockEntity();
							stockEntity.setWaybillNO(reportWaybillNo);
							stockEntity.setSerialNO(lackList.getSerialNo());
							stockEntity.setOrgCode(stWaybillDetailDto.getDeptcode());
							stockEntity.setOperatorName(stTaskEntity.getCreatorName());
							stockEntity.setOperatorCode(stTaskEntity.getCreatorCode());
							stockEntity.setInOutStockType(StockConstants.LOSE_GOODS_IN_STOCK_TYPE);
							stockEntity.setInOutStockBillNO(stTaskEntity.getTaskNo());
							
							stockService.inStockRequiresNewTransactional(stockEntity);
						}catch(Exception e){
							LOGGER.error(ExceptionUtils.getFullStackTrace(e));
							
							TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
							jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.ST_REPORT.getBizName());
							jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.ST_REPORT.getBizCode());
							jobProcessLogEntity.setRemark("少货上报OA差异后入库出错！deptCode: " + stWaybillDetailDto.getDeptcode() + "waybillNo:" + reportWaybillNo + ", serialNo:" + lackList.getSerialNo());
							jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
							jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
							
							tfrCommonService.addJobProcessLog(jobProcessLogEntity);
						}
					}
				}
				
			}
		}
		
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:52:59
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStReportService#createFileName(java.lang.String)
	 */
	@Override
	public String createFileName(String fileName) {
		try {
			return URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			
			throw new TfrBusinessException(TfrBusinessException.EXPORT_FILE_ERROR_CODE, "");
		}
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:53:03
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStReportService#exportStDifferDetailById(java.lang.String)
	 */
	@Override
	public InputStream exportStDifferDetailById(String stReportId) {
		InputStream excelStream = null;
		
//		定义导出表头
		String[] rowHeads = {"报告编号", "包号", "运单号", "流水号", "货区", "行政区", "件区", "OA差错编号", "差异类型","差异原因", "清仓员", "报告生成时间", "异常处理时间", "备注", "目的站"};
//		行List
		List<List<String>> rowList = new ArrayList<List<String>>();
//		获取导出数据
		List<StReportDetailDto> stReportDetailDtoList = stDifferDetailDao.queryStReportDetailDtoListById(stReportId);
//		将需导出的数据载入至行
		for(StReportDetailDto exportDto: stReportDetailDtoList){
			
			//设置行政区
			String receiveMethod = exportDto.getReceiveMethod();//获取提货方式
			String districtName = exportDto.getDistrictName();//获取分区名称
			
			if("PICKUP".equals(receiveMethod)){
				exportDto.setAdministrativeRegion("自提区");
			}else if("DELIVER".equals(receiveMethod)){
				if("".equals(districtName) || null==districtName){
					exportDto.setAdministrativeRegion("—");
				}else{
					exportDto.setAdministrativeRegion(districtName);
				}
			}else{
				exportDto.setAdministrativeRegion("—");
			}
			//设置件区
			int startQty = exportDto.getStartQty();//起始件数
			int entQty = exportDto.getEndQty();//结束件数
			if(startQty != 0 && entQty != 0){
				exportDto.setPieceRegion(startQty+"—"+entQty);
			}else{
				exportDto.setPieceRegion("—");
			}
			//装入数据
			List<String> columnList = new ArrayList<String>();
			columnList.add(exportDto.getReportCode());			//报告编号
			columnList.add(exportDto.getPackageNo());			//包号
			columnList.add(exportDto.getWaybillNo());			//运单号
			columnList.add(exportDto.getSerialNo());			//流水号
			columnList.add(exportDto.getGoodsAreaName());		//货区
			columnList.add(exportDto.getAdministrativeRegion());//行政区
			columnList.add(exportDto.getPieceRegion());		    //件区
			columnList.add(exportDto.getOaErrorNo());			//OA差错编号
			columnList.add(exportDto.getDiffInfo());	        //差异类型
			columnList.add(exportDto.getDifferenceReason());    //差异原因
			columnList.add(exportDto.getOperator());			//清仓员
			columnList.add(com.deppon.foss.util.DateUtils.convert(exportDto.getReportCreateTime(), "yyyy-MM-dd HH:mm:ss"));	//报告生成时间
			columnList.add(com.deppon.foss.util.DateUtils.convert(exportDto.getHandleTime(), "yyyy-MM-dd HH:mm:ss"));			//异常处理时间
			columnList.add(exportDto.getRemark());				//备注
			columnList.add(exportDto.getBillTargetOrgName());
			
			rowList.add(columnList);
		}
		
		SheetData sheetData = new SheetData();
		sheetData.setRowHeads(rowHeads);
		sheetData.setRowList(rowList);
		
		ExcelExport excelExportUtil = new ExcelExport();
		excelStream = excelExportUtil.inputToClient(excelExportUtil.exportExcel(sheetData, "清仓任务明细", TransferConstants.EXPORT_FILE_MAX_ROW));
		
        return excelStream;
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:53:10
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStReportService#queryStDifferDetailEntityList(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<StDifferDetailEntity> queryStDifferDetailEntityList(String waybillNo, String serialNo, String deptCode) {
		
		return stDifferDetailDao.queryStDifferDetailEntityList(waybillNo, serialNo, deptCode);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:53:16
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStReportService#updateReportDetailList(java.util.List)
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@Override  
	public void updateReportDetailList(StDifferDetailEntity differDetailEntity) {
//		更新清仓差异明细的备注信息、处理时间、处理状态
		stDifferDetailDao.updateByPrimaryKeySelective(differDetailEntity);
		
//		判断此条清仓差异明细数据是否为此清仓差异报告的最后一条"未处理"的数据
		long count = stDifferDetailDao.queryStReportDetailCountByReportId(differDetailEntity.getId(), differDetailEntity.getStDifferReportId(), TransferConstants.STOCK_CHECKING_REPORT_DOING);
//		若是，则更新此清仓差异报告的处理状态
		if(count == 0){
			StDifferReportEntity stDifferReportEntity = new StDifferReportEntity();
			stDifferReportEntity.setId(differDetailEntity.getStDifferReportId());
			stDifferReportEntity.setHandleStatus(TransferConstants.STOCK_CHECKING_REPORT_DONE);
			
			stDifferReportDao.updateByPrimaryKeySelective(stDifferReportEntity);
		}
	}
	
	@Override
	public List<StDifferDetailEntity> queryStDifferDetailListByWaybillNo(String waybillNo) {
		List<StDifferDetailEntity> list = stDifferDetailDao.queryStDifferDetailListByWaybillNo(waybillNo, TransferConstants.GOODS_STATUS_LACK, TransferConstants.STOCK_CHECKING_REPORT_DOING);
		
		if(CollectionUtils.isEmpty(list)){
			list = new ArrayList<StDifferDetailEntity>();
		}
		return list;
	}
	/**
	 * 上报OA清仓少货
	 * @date 2013-5-21 下午3:43:05
	 * @modifier sunjianbo
	 * @mtime  2014-5-22 上午11:11:57
	 */
	private ResponseDto reportOALessGoods(WaybillEntity waybillEntity, StWaybillDetailDto detail, StTaskEntity stTaskEntity){
			OaReportClearless lessDto = new OaReportClearless();
			lessDto.setWayBillId(waybillEntity.getWaybillNo());
			lessDto.setReportTime(Calendar.getInstance().getTime());
			//运单transport_type可为空
			if(StringUtils.isEmpty(waybillEntity.getTransportType())) {
				//运输类型为空时，根据运输性质查询对应的一级产品
				ProductEntity entity = productService4Dubbo.getProductLele(waybillEntity.getProductCode(),null,1);
				if(entity != null) {
					lessDto.setTransportType(entity.getName());
				}
			}else {
				lessDto.setTransportType(productService4Dubbo.getProductByCache(waybillEntity.getTransportType(), null).getName());		//运输类型
			}
			lessDto.setReturnBillType(tfrCommonService.queryDictNameByCode(DictionaryConstants.RETURN_BILL_TYPE, waybillEntity.getReturnBillType()));
			lessDto.setShipper(waybillEntity.getDeliveryCustomerContact());
			lessDto.setTransportProduct(productService4Dubbo.getProductByCache(waybillEntity.getProductCode(), null).getName());	//产品类型
			lessDto.setStowageType("");			//配载类型
			if(StringUtils.isNotEmpty(waybillEntity.getReceiveCustomerPhone())){
				lessDto.setReceiverTel(waybillEntity.getReceiveCustomerPhone());
			}else{
				lessDto.setReceiverTel(waybillEntity.getReceiveCustomerMobilephone());
			}
			lessDto.setGroupSendFlag(tfrCommonService.queryDictNameByCode(DictionaryConstants.PICKUP_GOODS, waybillEntity.getReceiveMethod()));		//提货方式
			lessDto.setRemark(waybillEntity.getTransportationRemark());
			if(null != waybillEntity.getGoodsWeightTotal()) {
				lessDto.setWeight(waybillEntity.getGoodsWeightTotal().doubleValue());
			} else {
				lessDto.setWeight(Double.valueOf(0));
			}
			if(null != waybillEntity.getGoodsVolumeTotal()) {
				lessDto.setVloume(waybillEntity.getGoodsVolumeTotal().doubleValue());
			} else {
				lessDto.setVloume(Double.valueOf(0));
			}
			lessDto.setGoods(waybillEntity.getGoodsName());
			//lessDto.setSendTime(com.deppon.foss.util.DateUtils.convert(waybillEntity.getPreDepartureTime(), "yyyy-MM-dd HH:mm:ss"));
			//发货时间取运单的开单时间
			lessDto.setSendTime(com.deppon.foss.util.DateUtils.convert(waybillEntity.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			lessDto.setDestination(waybillEntity.getTargetOrgCode());
			lessDto.setReceiver(waybillEntity.getReceiveCustomerName());
			OrgAdministrativeInfoEntity recieveEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getReceiveOrgCode());
			if(recieveEntity != null){
				lessDto.setReceivingDeptID(recieveEntity.getUnifiedCode());
				lessDto.setReceivingDept(recieveEntity.getName());
			}
			lessDto.setPayType(tfrCommonService.queryDictNameByCode(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE, waybillEntity.getPaidMethod()));				//付款方式
			if(waybillEntity.getInsuranceFee() == null){
				lessDto.setInsuranceMoney(new BigDecimal(0));
			}else{
				lessDto.setInsuranceMoney(waybillEntity.getInsuranceFee());
			}
			lessDto.setGoodsPacking(waybillEntity.getGoodsPackage());
			lessDto.setTotal(waybillEntity.getTotalFee());
			lessDto.setNogoodscount(detail.getLackList().size());
			//OA表设计中长度2000，事件经过最多放置650，故此处做处理
			String event = "清仓手工上报,包含件：" + detail.getLackListString();
			if(event.length() > LoadConstants.SONAR_NUMBER_650) {
				event = event.substring(0,LoadConstants.SONAR_NUMBER_650) + "....";
			}
			lessDto.setEventReport(event);
			lessDto.setGoodsCount(waybillEntity.getGoodsQtyTotal());
			lessDto.setLostGoodsType("清仓丢货");
			//责任部门
			lessDto.setResponsibleDept(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(detail.getDeptcode()));	//当前外场部门
			OrgAdministrativeInfoEntity currentOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(detail.getDeptcode());
			lessDto.setResponsibleDeptId(currentOrg.getUnifiedCode());//当前外场部门编号
			//责任事业部标杆编码
			//设置查询参数
			List<String> bizTypesList = new ArrayList<String>();
			//事业部类型
			bizTypesList.add(BizTypeConstants.ORG_DIVISION);
			//查询上级事业部门
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
					queryOrgAdministrativeInfoByCode(detail.getDeptcode(), bizTypesList);
			if(orgAdministrativeInfoEntity != null){
				lessDto.setFinalSysCode(orgAdministrativeInfoEntity.getName());
			}else{
				lessDto.setFinalSysCode("");
				LOGGER.warn("清仓少货上报OA：查询责任部门【" + detail.getDeptcode() + "】的责任事业部失败！");
			}
			
			OrgAdministrativeInfoEntity createEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCreateOrgCode());
			if(createEntity != null){
				lessDto.setSheetDeptId(createEntity.getUnifiedCode());
				lessDto.setSheetDept(createEntity.getName());
			}
			lessDto.setReceiver(waybillEntity.getReceiveCustomerContact()); //收货人
			lessDto.setUserId(stTaskEntity.getCreatorCode());
			lessDto.setReplayBill(stTaskEntity.getTaskNo());
			//发货客户编码
			lessDto.setDeliveryCustomerCode(waybillEntity.getDeliveryCustomerCode());
			//收货客户编码
			lessDto.setReceiveCustomerCode(waybillEntity.getReceiveCustomerCode());
			//差异流水号
			String lackStr = detail.getLackListString();
			if(StringUtils.isNotEmpty(lackStr)) {
				lessDto.setSerialNoList(lackStr.substring(0,lackStr.length() - 1 ));
			}
			//业务渠道
			OutfieldEntity  outfieldEntity=outfieldService.queryOutfieldByOrgCodeNoCache(stTaskEntity.getDeptcode());
			if(outfieldEntity!=null){
				DataDictionaryValueEntity dictEntity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.TRANSFER_SERVICE_CHANNEL, outfieldEntity.getTransferServiceChannel());
				if(null != dictEntity && StringUtils.isNotEmpty(dictEntity.getValueName())) {
					lessDto.setBusinessChannel(dictEntity.getValueName());
				}
			}else{
				lessDto.setBusinessChannel("营业部");
			}
			
			ResponseDto responseDto = fossToOAService.reportLessGoods(lessDto);
			
			return responseDto;
	}
	
	/**
	 * 上报OA清仓多货
	 * @date 2013-4-17 上午11:24:16
	 * @modifier sunjianbo
	 * @mtime  2014-5-22 上午11:11:57
	 */
	private ResponseDto reportOAMoreGoods(WaybillEntity waybillEntity, StWaybillDetailDto detail, StDifferDetailEntity surplusEntity, StTaskEntity stTaskEntity){
		
				OaReportClearMore surplusDto = new OaReportClearMore();
				surplusDto.setWayBillId(waybillEntity.getWaybillNo());
				surplusDto.setReportTime(Calendar.getInstance().getTime());
				//运单transport_type可为空
				if(StringUtils.isEmpty(waybillEntity.getTransportType())) {
					//运输类型为空时，根据运输性质查询对应的一级产品
					ProductEntity entity = productService4Dubbo.getProductLele(waybillEntity.getProductCode(),null,1);
					if(entity != null) {
						surplusDto.setTransportType(entity.getName());
					}
				} else {
					surplusDto.setTransportType(productService4Dubbo.getProductByCache(waybillEntity.getTransportType(), null).getName());		//运输类型
				}
				surplusDto.setReturnBillType(tfrCommonService.queryDictNameByCode(DictionaryConstants.RETURN_BILL_TYPE, waybillEntity.getReturnBillType()));
				surplusDto.setShipper(waybillEntity.getDeliveryCustomerContact());
				surplusDto.setTransportProduct(productService4Dubbo.getProductByCache(waybillEntity.getProductCode(), null).getName());	//产品类型
				surplusDto.setStowageType("");			//配载类型
				if(StringUtils.isNotEmpty(waybillEntity.getReceiveCustomerPhone())){
					surplusDto.setReceiverTel(waybillEntity.getReceiveCustomerPhone());
				}else{
					surplusDto.setReceiverTel(waybillEntity.getReceiveCustomerMobilephone());
				}
				surplusDto.setGroupSendFlag(tfrCommonService.queryDictNameByCode(DictionaryConstants.PICKUP_GOODS, waybillEntity.getReceiveMethod()));		//提货方式
				surplusDto.setRemark(waybillEntity.getTransportationRemark());
				if(null != waybillEntity.getGoodsWeightTotal() ) {
					surplusDto.setWeight(waybillEntity.getGoodsWeightTotal().doubleValue());
				} else {
					surplusDto.setWeight(Double.valueOf(0));
				}
				if(null != waybillEntity.getGoodsVolumeTotal()) {
					surplusDto.setVloume(waybillEntity.getGoodsVolumeTotal().doubleValue());
				} else {
					surplusDto.setVloume(Double.valueOf(0));
				}
				surplusDto.setGoods(waybillEntity.getGoodsName());
				surplusDto.setSendTime(com.deppon.foss.util.DateUtils.convert(waybillEntity.getPreDepartureTime(), "yyyy-MM-dd HH:mm:ss"));
				surplusDto.setDestination(waybillEntity.getTargetOrgCode());
				surplusDto.setReceiver(waybillEntity.getReceiveCustomerName());
				OrgAdministrativeInfoEntity recieveEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getReceiveOrgCode());
				if(recieveEntity != null){
					surplusDto.setReceivingDeptID(recieveEntity.getUnifiedCode());
					surplusDto.setReceivingDept(recieveEntity.getName());
				}
				surplusDto.setPayType(tfrCommonService.queryDictNameByCode(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE, waybillEntity.getPaidMethod()));				//付款方式
				if(waybillEntity.getInsuranceFee() == null){
					surplusDto.setInsuranceMoney(0);
				}else{
					surplusDto.setInsuranceMoney(waybillEntity.getInsuranceFee().doubleValue());
				}
				surplusDto.setGoodsPacking(waybillEntity.getGoodsPackage());
				surplusDto.setTotal(waybillEntity.getTotalFee().doubleValue());
				String event = "清仓手工上报,包含件：" + detail.getSurplusListString();
				if(event.length() > LoadConstants.SONAR_NUMBER_650) {
					event = event.substring(0,LoadConstants.SONAR_NUMBER_650) + "....";
				}
				surplusDto.setEventReport(event);
				surplusDto.setGoodsCount(waybillEntity.getGoodsQtyTotal());
				surplusDto.setMoreGoodsType("清仓多货");
				//surplusDto.setResponsibleDept(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(detail.getDeptcode()));	//当前外场部门
				//surplusDto.setResponsibleDeptId(detail.getDeptcode());//当前外场部门编号
				OrgAdministrativeInfoEntity createEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCreateOrgCode());
				if(createEntity != null){
					surplusDto.setSheetDeptId(createEntity.getUnifiedCode());
					surplusDto.setSheetDept(createEntity.getName());
				}
				surplusDto.setReceiver(waybillEntity.getReceiveCustomerContact()); //收货人
				surplusDto.setUserId(stTaskEntity.getCreatorCode());
				surplusDto.setDisburdenTaskNumber(stTaskEntity.getTaskNo());
				//多货件数
				surplusDto.setMoreGoodsCount(detail.getSurplusList().size());
				if(StringUtils.equals(TransferConstants.GOODS_STATUS_SURPLUS_CARRY, surplusEntity.getDifferenceType())){
					//经手
					surplusDto.setHaveGoodsNoReplay(UnloadConstants.GOODS_SHOULD_BE_HERE);
				}else{
					//非经手
					surplusDto.setHaveGoodsNoReplay(UnloadConstants.GOODS_SHOULD_NOT_BE_HERE);
				}
				//发货客户编码
				surplusDto.setDeliveryCustomerCode(waybillEntity.getDeliveryCustomerCode());
				//收货客户编码
				surplusDto.setReceiveCustomerCode(waybillEntity.getReceiveCustomerCode());
				//差异流水号
				String moreStr = detail.getSurplusListString();
				if(StringUtils.isNotEmpty(moreStr)) {
					surplusDto.setSerialNoList(moreStr.substring(0, moreStr.length() - 1));
				}

                //上报OA
				ResponseDto responseDto = fossToOAService.reportMoreGoods(surplusDto);
				
				return responseDto;
	}
	/**
	 * 获取当前部门的上级外场、空运总调大部门
	 * @author 097457-foss-wangqiang
	 * @date 2013-5-14 下午3:50:19
	 */
	private OrgAdministrativeInfoEntity getBigOrg(OrgAdministrativeInfoEntity currentOrg){
		if(StringUtils.equals(FossConstants.YES, currentOrg.getSalesDepartment()) || 
				StringUtils.equals(FossConstants.YES, currentOrg.getAirDispatch()) ||
				StringUtils.equals(FossConstants.YES, currentOrg.getTransferCenter())){
			return currentOrg;
		}else{
			//设置查询参数
			List<String> bizTypesList = new ArrayList<String>();
			//外场类型
			bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			//空运总调类型
			bizTypesList.add(BizTypeConstants.ORG_AIR_DISPATCH);
			
			OrgAdministrativeInfoEntity bigOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(currentOrg.getCode(), bizTypesList);
			if(bigOrg == null){
				LOGGER.error("查询部门：" + currentOrg.getCode() + " 所属外场或空运总调大部门失败");
				throw new TfrBusinessException("查询当前部门所属外场或空运总调失败","");
			}
			return bigOrg;
		}
	}

	/**
	 * @author niuly
	 * @function 根据差异id查询差异明细
	 * @date 2013-06-24 13:55:23
	 */
	@Override
	public List<StReportDetailDto> queryStReportDetailDtoListsById(String stReportId, int start, int limit) {
		List<StReportDetailDto> reportDetailList = stDifferDetailDao.queryStReportDetailDtoListsById(stReportId,start,limit);

		for(StReportDetailDto dto: reportDetailList){
			//设置行政区
			String receiveMethod = dto.getReceiveMethod();//获取提货方式
			String districtName = dto.getDistrictName();//获取分区名称
			
			if("PICKUP".equals(receiveMethod)){
				dto.setAdministrativeRegion("自提区");
			}else if("DELIVER".equals(receiveMethod)){
				if("".equals(districtName) || null==districtName){
					dto.setAdministrativeRegion("—");
				}else{
					dto.setAdministrativeRegion(districtName);
				}
			}else{
				dto.setAdministrativeRegion("—");
			}
			//设置件区
			int startQty = dto.getStartQty();//起始件数
			int entQty = dto.getEndQty();//结束件数
			if(startQty != 0 && entQty != 0){
				dto.setPieceRegion(startQty+"—"+entQty);
			}else{
				dto.setPieceRegion("—");
			}
		}
		this.doStReportDetailDto(reportDetailList);
		return reportDetailList;
	}

	/**
	 * @author niuly
	 * @function 根据差异id查询差异明细总数，用于分页
	 */
	@Override
	public Long getStReportDetailDtoCount(String stReportId) {
		return stDifferReportDao.getStReportDetailDtoCount(stReportId);
	}

	/**
	 * @author niuly
	 * @function 查询一个差异明细中一个单、一个流水号两天内的关联差异报告
	 * @param id
	 * @return
	 */
	@Override
	public List<StReportDetailDto> queryReportRelativeDetail(String stReportId,	String waybillNo, List<String> serailNosList) {
		Date limitedDay = DateUtils.addDays(Calendar.getInstance().getTime(), -2);
		List<StReportDetailDto> relativeList = stDifferDetailDao.queryReportRelativeDetail(stReportId, limitedDay, TransferConstants.GOODS_STATUS_LACK, TransferConstants.GOODS_STATUS_SURPLUS_ERROR_GOODSAREA,waybillNo,serailNosList);
		if(CollectionUtils.isNotEmpty(relativeList)) {
			for(StReportDetailDto dto:relativeList) {
				//设置行政区
				String receiveMethod = dto.getReceiveMethod();//获取提货方式
				String districtName = dto.getDistrictName();//获取分区名称
				if("PICKUP".equals(receiveMethod)){
					dto.setAdministrativeRegion("自提区");
				}else if("DELIVER".equals(receiveMethod)){
					if("".equals(districtName) || districtName==null){
						dto.setAdministrativeRegion("—");
					}else{
						dto.setAdministrativeRegion(districtName);
					}
				}else{
					dto.setAdministrativeRegion("—");
				}
				//设置件区
				int startQty = dto.getStartQty();//起始件数
				int entQty = dto.getEndQty();//结束件数
				if(startQty != 0 && entQty != 0){
					dto.setPieceRegion(startQty+"—"+entQty);
				}else{
					dto.setPieceRegion("—");
				}
				//设置运输性质
				WaybillEntity entity = queryWaybillBasicByNo(dto.getWaybillNo());
				if(null != entity) {
					dto.setTransProperty(this.queryProductNameByCode(entity.getProductCode()));
				}
			}
		}
		return relativeList;
	}

	@Override
	public void executeStReportGapRepair(Date bizEndTime, Date scheduledFireTime, int threadNo, int threadCount, int timeInterval) {
//		获取此JOB开关时间
		Date beginTime = null;
		Date startTime = Calendar.getInstance().getTime();
		Date planEndTime = DateUtils.addMinutes(startTime, timeInterval);
		try{
			ConfigurationParamsEntity configurationParamsEntity = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , ConfigurationParamsConstants.TFR_PARM__ST_REPORT_GAP_REPAIR_BEGINE_TIME, FossConstants.ROOT_ORG_CODE);
			beginTime = com.deppon.foss.util.DateUtils.convert(configurationParamsEntity.getConfValue(), "yyyy-MM-dd HH:mm:ss");
			
		}catch(Exception e){
			LOGGER.error("获取配置参数失败", ExceptionUtils.getFullStackTrace(e));
			throw new BusinessException("获取配置参数失败", e);
		}
//		查询此时间后的所有未处理状态、且未上报OA差错的的清仓差异报告明细数据
		List<StDifferDetailEntity> stDifferDetailList = stDifferDetailDao.queryDetailForGapRepairList(beginTime, scheduledFireTime, threadNo, threadCount);
		
		for(StDifferDetailEntity differEntity: stDifferDetailList){
//			超过设定的执行时间，结束JOB
			if(Calendar.getInstance().getTime().after(planEndTime)){
				break;
			}
			try{
				taskReportService.stReportGapRepair(differEntity);
			}catch(Exception e){
				LOGGER.error(ExceptionUtils.getFullStackTrace(e));
				
				TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
				jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.ST_REPORT_GAP_REPAIR.getBizName());
				jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.ST_REPORT_GAP_REPAIR.getBizCode());
				jobProcessLogEntity.setExecBizId(differEntity.getId());
				jobProcessLogEntity.setExecTableName("TFR.T_OPT_ST_DIFFER_DETAIL");
				jobProcessLogEntity.setRemark("更新清仓差异报告明细状态出错");
				jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
				jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
				
				tfrCommonService.addJobProcessLog(jobProcessLogEntity);
			}
		}
	}
	
	/**
	 * 自动上报清仓少货找到差错至oa，供job调用
	 * @author 045923-foss-shiwei
	 * @date 2013-7-6 上午10:46:25
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.IStReportService#reportStLackGoodsFoundToOA()
	 */
	@Override
	public int reportStLackGoodsFoundToOA(){
		//获取配置参数
		ConfigurationParamsEntity defaultBizHourSlice = configurationParamsService.queryConfigurationParamsByOrgCode(
				DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , 
				ConfigurationParamsConstants.TFR_PARM_REPORT_ST_LACK_GOODS_FOUND_LATEST_REPORT_TIME, 
				FossConstants.ROOT_ORG_CODE);
		Date bizDate = null;
		if (defaultBizHourSlice == null
				|| StringUtils.isEmpty(defaultBizHourSlice.getConfValue())) {
			LOGGER.error("###########################读取配置参数中的oa清仓少货差错上报最晚时间为空！##########################");
			throw new TfrBusinessException("读取配置参数中的oa清仓少货差错上报最晚时间为空！");
		} else {
			bizDate = com.deppon.foss.util.DateUtils.strToDate(defaultBizHourSlice.getConfValue());
		}
		//查询出所有未少货找到的oa差错单编号
		List<String> errorNoList = unloadService.queryNoFoundLackGoodsOAErrorNo(TransferConstants.REPORT_TYPE_ST,bizDate);
		String lackOrgCode = null;
		//遍历这些少货差错
		for(String errorNo : errorNoList){
			//获取差错单下所有未找到的流水号
			List<LackGoodsFoundEntity> lackSerialNoList = unloadService.queryNoFoundLackGoodsDetailByOAErrorNo(errorNo);
			lackOrgCode = lackSerialNoList.get(0).getLackGoodsOrgCode();
			//bool变量，用来标记差错单下流水号是否已全部找到
			boolean beFoundAll = true;
			InOutStockEntity discoverer = null;
			//循环流水号，查询入库记录
			for(int i = 0;i < lackSerialNoList.size();i++){
				LackGoodsFoundEntity lackSerialNo = lackSerialNoList.get(i);
				String waybillNo = lackSerialNo.getWaybillNo(),
						serialNo = lackSerialNo.getSerialNo();
				Date reportTime = lackSerialNo.getReportOATime();
				List<InOutStockEntity> inStockList = stockService.queryInStockInfo(waybillNo, serialNo, null, reportTime);
				if(CollectionUtils.isEmpty(inStockList)){
					beFoundAll = false;
					continue;
				}else{
					//取出第一条入库记录
					InOutStockEntity firstInStock = inStockList.get(0);
					//更新少货差错单表
					lackSerialNo.setBeFound(FossConstants.YES);
					lackSerialNo.setDiscovererCode(firstInStock.getOperatorCode());
					lackSerialNo.setDiscovererName(firstInStock.getOperatorName());
					lackSerialNo.setDiscovererOrgCode(firstInStock.getOrgCode());
					lackSerialNo.setFoundTime(firstInStock.getInOutStockTime());
					lackSerialNo.setInStockId(firstInStock.getId());
					unloadService.updateUnloadLackGoodsInfo(lackSerialNo);
					
					//更新此条少货记录差错表的同时，将对应清仓任务差异报告明细状态标记为已处理状态，此处未做事务控制，今后可优化
					taskReportService.stReportGapRepairNoStock(lackSerialNo);
				}
				//取出最后一个流水号的入库记录
				if(beFoundAll){
					if(i + 1 == lackSerialNoList.size()){
						discoverer = inStockList.get(0);
					}
				}
			}
			//如果货物全部找到，则上报少货找到差错单
			if(beFoundAll){
				String lastOrgUnifiedCode = null;
				OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(lackOrgCode);
				if(orgEntity != null){
					lastOrgUnifiedCode = orgEntity.getUnifiedCode();
				}
				/*
				 * 上报少货找到
				 */
				//sonar-352203
				ResponseDto responseDto = null;
				if(discoverer != null){
				/*ResponseDto */responseDto = fossToOAService.reportLessGoodsFound(errorNo, discoverer.getOperatorCode(), discoverer.getOperatorName(), lastOrgUnifiedCode);
				}
				//如果oa没处理成功，则回滚事务
				if(responseDto != null && !responseDto.getIsSuccess()){
					//记录日志
					LOGGER.error("######################上报OA少货已找到差错单失败，失败信息：" + responseDto.getFailureReason());
					//查询下一个少货差错单
					continue;
				}
			}
		}
		return FossConstants.SUCCESS;
	}
	/**
	 * @author niuly
	 * @date 2014-6-1下午4:15:48
	 * @function 对差异明细进行封装
	 * @param stReportDetailDtoList
	 */
	private void doStReportDetailDto(List<StReportDetailDto> stReportDetailDtoList) {
		//差异报告明细中运单对应的流水号信息
		for(StReportDetailDto dto:stReportDetailDtoList) {
			List<StDifferDetailEntity> differDetailList = this.queryStDifferDetailsByStIdAndWaybillNo(dto.getStDifferReportId(),dto.getWaybillNo());
			//获取运单信息
			WaybillEntity waybillEntity = this.queryWaybillBasicByNo(dto.getWaybillNo());
			
			//运输性质
			String transProperty = "";
			if(null != waybillEntity){
				transProperty = this.queryProductNameByCode(waybillEntity.getProductCode());
			}
			//根据任务id、运单号和流水号获取操作时间
			for(StDifferDetailEntity entity:differDetailList) {
				Date operateTime = this.queryOperateTime(dto.getStDifferReportId(),entity.getWaybillNo(),entity.getSerialNo());
				entity.setOperateTime(operateTime);
				entity.setTransProperty(transProperty);
			}
			dto.setDifferDetailList(differDetailList);
			if(waybillEntity != null) {
				dto.setGoodsName(waybillEntity.getGoodsName());
				dto.setGoodsPackage(waybillEntity.getGoodsPackage());
				dto.setGoodsWeight(waybillEntity.getGoodsWeightTotal());
				dto.setGoodsVolume(waybillEntity.getGoodsVolumeTotal());
				dto.setBillTargetOrgName(waybillEntity.getTargetOrgCode());
				dto.setTransProperty(transProperty);
			}
			for(StDifferDetailEntity entity:differDetailList) {
				if(entity!= null && !StringUtils.equals("N/A", entity.getOaErrorNo())) {
					dto.setOaErrorNo(entity.getOaErrorNo());
					break;
				}
			}
			//设置差异类型，用于处理清仓少货差异德邦快递货物的处理按钮，若有一件是少货，那么批量处理的按钮不可用
			for(StDifferDetailEntity entity:differDetailList) {
				if(entity!= null && StringUtils.equals("少货", entity.getDifferenceType())) {
					dto.setDifferenceType("少货");
					break;
				}
			}
		}
	}
	/**
	 * @author niuly
	 * @date 2013-07-10
	 * @function 查询外场code
	 * @return String
	 */
	@Override
	public String queryTransferCode() {
		OrgAdministrativeInfoEntity orgEntity = null;
		CurrentInfo user = FossUserContext.getCurrentInfo();//获取当前登录的信息
		if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null) {//判断是不是快递员
			//根据快递员的工号查找到快递员车辆所在的开大营业部
			String code = expressVehiclesService.queryExpressVehiclesByCode(user.getEmpCode());
							//根据营业部编号获取组织架构
			OrgAdministrativeInfoEntity expressOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(code);
			orgEntity = this.getBigOrg(expressOrgEntity);
//			System.out.println("查询差异报告----快递员");
		}else{
			 orgEntity = this.getBigOrg(FossUserContext.getCurrentDept());
		}
		String currentDeptCode = "";
//		对于空运总调型的，需找到对应的外场所属的库区
		if(StringUtils.equals(FossConstants.YES, orgEntity.getAirDispatch())){
//			获取空运总调对应的外场编号
			currentDeptCode = outfieldService.queryTransferCenterByAirDispatchCode(orgEntity.getCode());
			
		}else{
			currentDeptCode = orgEntity.getCode();
		}
		return currentDeptCode;
	}
	/**
	 * @author zenghaibin
	 * @date 2014-10-29
	 * @function 查询是否是外场
	 * @return String
	 */
	@Override
	public String queryTransferCenter() {
		OrgAdministrativeInfoEntity orgEntity = null;
		CurrentInfo user = FossUserContext.getCurrentInfo();//获取当前登录的信息
		if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null) {//判断是不是快递员
			//根据快递员的工号查找到快递员车辆所在的开大营业部
			String code = expressVehiclesService.queryExpressVehiclesByCode(user.getEmpCode());
			//根据营业部编号获取组织架构
			OrgAdministrativeInfoEntity expressOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(code);
			orgEntity = expressOrgEntity;
			System.out.println("查询差异报告-----快递员");
		}else{
			 orgEntity = FossUserContext.getCurrentDept();
		}
		
		if(StringUtils.equals(FossConstants.YES, orgEntity.getSalesDepartment()) || 
				StringUtils.equals(FossConstants.YES, orgEntity.getAirDispatch()) ||
				StringUtils.equals(FossConstants.YES, orgEntity.getTransferCenter())){
			return orgEntity.getTransferCenter();
		}else{
			//设置查询参数
			List<String> bizTypesList = new ArrayList<String>();
			//外场类型
			bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			//空运总调类型
			bizTypesList.add(BizTypeConstants.ORG_AIR_DISPATCH);
			
			OrgAdministrativeInfoEntity bigOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(orgEntity.getCode(), bizTypesList);
			if(bigOrg == null){
				LOGGER.error("查询部门：" + orgEntity.getCode() + " 所属外场或空运总调大部门失败");
				throw new TfrBusinessException("查询当前部门所属外场或空运总调失败","");
			}
			return bigOrg.getTransferCenter();
		}
	}
	  /**
	   * @author niuly
	   * @date 2013-7-24 15:19:25
	   * @param stDifferReportId
	   * @param waybillNo
	   * @function 根据差异报告id和运单号查询差异明细信息
	   * @return
	   */
	  @Override
	  public List<StDifferDetailEntity> queryStDifferDetailsByStIdAndWaybillNo(String stDifferReportId, String waybillNo) {
	    return stDifferDetailDao.queryStDifferDetailsByStIdAndWaybillNo(stDifferReportId, waybillNo);
	  }
		/**
		 * @author niuly
		 * @date 2013-07-25 17:12:25
		 * @function 批量更新清仓差异报告明细
		 * @param stReportDetailDtoList
		 * @param empName
		 * @param empCode
		 */
	@Override
	public void updateBatchReportDetail(List<StDifferDetailEntity> stDifferDetailList, String empName,String empCode) {
		for(StDifferDetailEntity entity: stDifferDetailList) {
			this.updateReportDetail(entity, empName, empCode);
		}
	}
	/**
	 * @author niuly
	 * @date 2013-07-26 15:08:25
	 * @function 根据单号、差异报告id查询差异报告明细
	 * @param stReportId
	 * @param waybillNo
	 * @return
	 */
	@Override
	public List<StReportDetailDto> queryReportDetailByWaybillNo(String stReportId, String waybillNo) {
		List<StReportDetailDto> reportDetailList = stDifferDetailDao.queryReportDetailByWaybillNo(stReportId, waybillNo);
		this.doStReportDetailDto(reportDetailList);
		return reportDetailList;
	}
	/**
	 * @author niuly
	 * @date 2013-08-02 14:24:21
	 * @function 根据运单号查询运单基本信息
	 * @param waybillNo
	 * @return WaybillEntity
	 */
	@Override
	public WaybillEntity queryWaybillBasicByNo(String waybillNo) {
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		return waybillEntity;
	}
	/**
	 * @author niuly
	 * @date 2013-08-05 13:55:36
	 * @function 根据差异报告id查询差异报告信息
	 * @param id
	 * @return
	 */
	@Override
	public StDifferReportEntity queryStReportEntityById(String id) {
		return stDifferReportDao.queryStReportEntityById(id);
	}
	/**
	 * @author niuly
	 * @date 2013-08-05 14:19:48
	 * @function 查询扫描结果信息
	 * @param taskId
	 * @param waybillNo
	 * @param serialNo
	 * @return
	 */
	@Override
	public Date queryOperateTime(String reportId,String waybillNo, String serialNo) {
		return stDifferReportDao.queryOperateTime(reportId,waybillNo,serialNo);
	}

	/**
	 * @author niuly
	 * @date 2014-5-19下午2:56:33
	 * @function 根据产品编码查询产品名称
	 * @param code
	 * @return
	 */
	@Override
	public String queryProductNameByCode(String code) {
		ProductEntity entity = productService4Dubbo.getProductByCache(code,null);
		String productName = "";
		if(entity != null) {
			productName = entity.getName();
		}
		return productName;
	}	
	/**
	 * @author niuly
	 * @date 2014-6-17上午11:04:43
	 * @function 判断运单是否少货且成功上报OA差错
	 * @param waybillNo
	 * @return
	 */
	@Override
	public boolean hasLackError(String waybillNo) {
		 long count = stDifferDetailDao.queryLackCountByWaybillNo(waybillNo);
		 return count > 0;
	}
	/**
	 * @author niuly
	 * @date 2014-6-17上午11:40:38
	 * @function 卸车或清仓任意一个少货且成功上报OA少货差错的，返回true
	 * @param waybillNo
	 * @return
	 */
	@Override
	public boolean isLack(String waybillNo) {
		boolean isLack = false;
		if(!StringUtils.isEmpty(waybillNo)) {
			//因清仓差异数量很大，故先判断卸车少货差异，若不存在卸车少货时才判断清仓
			isLack = unloadDiffReportService.hasLackError(waybillNo.trim());
			if(!isLack) {
				//清仓
				isLack = this.hasLackError(waybillNo.trim()); 
			}
		}
		return isLack;
	}
	
	/**
	 * @author zenghaibin
	 * @date 2014-10-20上午11:40:38
	 * @function 查询转运场的清仓差异的少货多货件数
	 * @return
	 */
	@Override
public StReportVO goodsCountInfo(){
		
		OrgAdministrativeInfoEntity stReportOrg = this.getBigOrg(FossUserContext.getCurrentDept());
		
		if(StringUtils.equals(FossConstants.YES, stReportOrg.getTransferCenter())){
			//是外场
			return stDifferReportDao.goodsCountInfo(stReportOrg.getCode());
		}else{
			
			return null;
		}
	}
	
}