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

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToOAService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadService;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.lostwarning.api.server.dao.IFindLostGoodsDao;
import com.deppon.foss.module.transfer.lostwarning.api.server.define.LostWarningConstant;
import com.deppon.foss.module.transfer.lostwarning.api.server.domain.FindLostGoodsEntity;
import com.deppon.foss.module.transfer.lostwarning.api.server.domain.FindLostGoodsSerialEntity;
import com.deppon.foss.module.transfer.lostwarning.server.service.impl.FossToMcewService;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadDiffReportDao;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadDiffReportService;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadService;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.LackGoodsFoundEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ReportQMSDataEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ConfirmUnloadTaskBillsDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadDiffReportConditionDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/** 
 * @className: UnloadDiffReportService
 * @author: ShiWei shiwei@outlook.com
 * @description: 卸车差异报告service类
 * @date: 2012-12-10 上午10:13:47
 * 
 */
public class UnloadDiffReportService implements IUnloadDiffReportService {
	
	/**
	 * 记录日志
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/**
	 * 本模块dao
	 */
	private IUnloadDiffReportDao unloadDiffReportDao;
	
	/**
	 * 序列号生产
	 * 
	 */
	private IFindLostGoodsDao   findLostGoodsDao;
	
	
	/**
	 * 处理方式生产
	 * 
	 * */
	private LostWarningConstant LostWarningConstant;
	
	/**
	 * 
	 * 卸车少货找到处理方式
	 * 
	 * */
	//未使用-sonar-352203
//	private FindLostGoodsService findLostGoodsService;
	

	/**
	 * 用于获取上级组织
	 */
	private ILoadService loadService;
	
	/**
	 * 卸车任务接口，用来更新卸车任务“是否生成卸车差异报告”字段
	 */
	private IUnloadTaskService unloadTaskService;
	
	/**
	 * 交接单service
	 */
	private IHandOverBillService handOverBillService;
	
	/**
	 * 库存service，用来少货找到后入库
	 */
	private IStockService stockService;
	
	/**
	 * 配置参数service
	 */
	private IConfigurationParamsService configurationParamsService;
	
	/** 
	 * 组织接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	private IUnloadTaskQueryService unloadTaskQueryService;
	
	/** 
	 * 上报接口
	 */
	private IFOSSToOAService fossToOAService;
	
	private IUnloadService unloadService;
	
	private ITfrCommonService tfrCommonService;
	
	private ISignDetailService signDetailService;
	
	private IWaybillRfcService waybillRfcService;
	
	
	
	public void setLostWarningConstant(LostWarningConstant lostWarningConstant) {
		LostWarningConstant = lostWarningConstant;
	}

	
	/**
	 * 
	 * 
	 */
	
	public void setFindLostGoodsDao(IFindLostGoodsDao findLostGoodsDao) {
		this.findLostGoodsDao = findLostGoodsDao;
	}
	
	
	/*
	 * 卸车少货找到服务
	 * **/
/*	public void setFindLostGoodsService(FindLostGoodsService findLostGoodsService) {
		this.findLostGoodsService = findLostGoodsService;
	}*/

	/**
	 * @param waybillRfcService the waybillRfcService to set
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	/**
	 * @param signDetailService the signDetailService to set
	 */
	public void setSignDetailService(ISignDetailService signDetailService) {
		this.signDetailService = signDetailService;
	}

	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	public void setUnloadService(IUnloadService unloadService) {
		this.unloadService = unloadService;
	}

	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
	}
	
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	public void setUnloadDiffReportDao(IUnloadDiffReportDao unloadDiffReportDao) {
		this.unloadDiffReportDao = unloadDiffReportDao;
	}
	public void setUnloadTaskService(IUnloadTaskService unloadTaskService) {
		this.unloadTaskService = unloadTaskService;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	public void setFossToOAService(IFOSSToOAService fossToOAService) {
		this.fossToOAService = fossToOAService;
	}
	
	/**   
	 * @param unloadTaskQueryService the unloadTaskQueryService to set
	 * Date:2013-7-29上午9:37:07
	 */
	public void setUnloadTaskQueryService(
			IUnloadTaskQueryService unloadTaskQueryService) {
		this.unloadTaskQueryService = unloadTaskQueryService;
	}

	/**
	 * 查询卸车差异报告
	 * @author 045923-foss-shiwei
	 * @date 2012-12-10 上午11:26:05
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadDiffReportService#queryUnloadDiffReportList(com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadDiffReportConditionDto, int, int)
	 */
	@Override
	public List<UnloadDiffReportEntity> queryUnloadDiffReportList(QueryUnloadDiffReportConditionDto queryCondition, int limit,int start) {
//		createUnloadDiffReport();
//		executeUnloadDiffReportTask(0,1);
//		reportUnloadLackGoodsFoundToOA();
		
		//获取当前部门编码
		String orgCode = this.querySuperiorOrgCode();
		//设置查询条件
		queryCondition.setOrgCode(orgCode);
		//判断是否选择查询本部门差异
		//判断是否选择查询本部门差异
		if(queryCondition.getDepartStatus().equals("true")){
			// 获取当前登录用户所在部门
			String orgLogCode = getCurrentOrgCode();
			queryCondition.setOrgLogCode(orgLogCode);
		}
		//返回查询结果
		return unloadDiffReportDao.queryUnloadDiffReportList(queryCondition, limit, start);
	}
	/**
	 * 
	 *
	 * @return 
	 * @Description: 获得当前部门值
	 * 
	 * Company:IBM
	 * @author FOSSDP-sz
	 * @date 2012-12-25 下午2:50:57
	 * @version V1.0
	 */
	private String getCurrentOrgCode() {
		OrgAdministrativeInfoEntity currentDept = FossUserContext.getCurrentDept();
		return currentDept.getCode();
	}
    
	/**
	 * 获取差异报告总条数
	 * @author 045923-foss-shiwei
	 * @date 2012-12-10 上午11:26:55
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadDiffReportService#queryUnloadDiffReportCount(com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadDiffReportConditionDto)
	 */
	@Override
	public Long queryUnloadDiffReportCount(QueryUnloadDiffReportConditionDto queryCondition) {
		//返回查询结果
		return unloadDiffReportDao.queryUnloadDiffReportCount(queryCondition);
	}

	/**
	 * 根据卸车差异报告编号获取报告基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-11 上午10:44:43
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadDiffReportService#queryUnloadDiffReportBasicInfo(java.lang.String)
	 */
	@Override
	public UnloadDiffReportEntity queryUnloadDiffReportBasicInfo(String diffReportNo) {
		//返回查询结果
		return unloadDiffReportDao.queryUnloadDiffReportBasicInfo(diffReportNo);
	}

	/**
	 * @Title: queryUnloadDiffReportById 
	 * @Description: 根据id获取差异报告信息 
	 * @param id
	 * @return    
	 * @return UnloadDiffReportEntity    返回类型 
	 * queryUnloadDiffReportById
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-7-24上午10:49:25
	 */
	public UnloadDiffReportEntity queryUnloadDiffReportById(String id) {
		//返回查询结果
		return unloadDiffReportDao.queryUnloadDiffReportById(id);
	}
	
	/**
	 * 根据卸车差异报告ID，获取差异报告详情
	 * @author 045923-foss-shiwei
	 * @date 2012-12-11 上午11:06:34
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadDiffReportService#queryUnloadDiffReportDetail(java.lang.String)
	 */
	@Override
	public List<UnloadDiffReportDetailEntity> queryUnloadDiffReportDetail(String diffReportId) {
		
		return unloadDiffReportDao.queryUnloadDiffReportDetail(diffReportId);
	}
	
	/**
	 * @Title: queryInStockNumForLack 
	 * @Description: 发现卸车少货，查询入库记录表，从建立卸车任务开始的时间，到当前时间为止，有没有入库动作，如果存在，该流水号不算少货
	 * @param taskTime
	 * @param deptCode
	 * @param waybillNo
	 * @param serialNo
	 * @return    
	 * @return int    返回类型 
	 * queryInStockNumForLack
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-7-23下午4:22:59
	 */
	private Long queryInStockNumForLack(Date taskTime, String deptCode, String waybillNo, String serialNo) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taskTime", taskTime);
		map.put("deptCode", deptCode);
		map.put("waybillNo", waybillNo);
		map.put("serialNo", serialNo);
		//多货，查询入库记录表，从建立卸车任务开始的时间，到当前时间为止，有没有入库动作，如果存在，该流水号不算多货
		return unloadDiffReportDao.queryInStockNumForLack(map);
	}

	/**
	 * 生成卸车差异报告，先查询出卸车流水号信息，根据异常的卸车流水号明细
	 * @author 045923-foss-shiwei
	 * @date 2012-12-22 下午5:03:03
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadDiffReportService#createUnloadDiffReport(java.util.List)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int createUnloadDiffReport(Date bizJobStartTime) {
		LOGGER.error("createUnloadDiffReport");
		LOGGER.info("---卸车差异报告，生成开始---");
		//获取所有未生成卸车差异报告的、已经卸车结束的、货物状态不正常的卸车任务流水号明细list
		LOGGER.info("卸车差异报告，查询卸车流水号明细开始");
		//默认查询三天内的数据,4320分钟
		//int day = 3;
//		int minutes = 4320;
		int minutes = ConstantsNumberSonar.SONAR_NUMBER_4320;
		try{
			ConfigurationParamsEntity defaultBizHourSlice = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , ConfigurationParamsConstants.TFR_PARM_UNLOAD_CREATE_REPORT_DAY, FossConstants.ROOT_ORG_CODE);
			minutes = Integer.valueOf(defaultBizHourSlice.getConfValue()).intValue();
		}catch(Exception e){
			LOGGER.error("获取配置参数失败", ExceptionUtils.getFullStackTrace(e));
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.UNLOAD_DIFFER_REPORT.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.UNLOAD_DIFFER_REPORT.getBizCode());
			jobProcessLogEntity.setRemark("生成卸车差异报告时获取配置参数失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		}
		List<UnloadDiffReportDetailEntity> serialNoList = unloadDiffReportDao.queryUnresolvedUnloadSerialNoList(bizJobStartTime,minutes);
		LOGGER.info("卸车差异报告，查询卸车流水号明细结束");
		//如果获取的结果集为空，则直接返回
		if(serialNoList == null || serialNoList.size() == 0){
			return FossConstants.SUCCESS;
		}
		//定义map，key为卸车任务编号，value为该卸车任务的差异报告基本信息
		Map<String,UnloadDiffReportEntity> baseEntityMap = new HashMap<String,UnloadDiffReportEntity>();
		//定义待保存的差异报告流水号明细Map，key为卸车任务编号，value为非正常的流水号list
		Map<String,List<UnloadDiffReportDetailEntity>> unsavedSerialNoMap = new HashMap<String,List<UnloadDiffReportDetailEntity>>();
		//遍历查询出的不正常的流水号明细
		LOGGER.info("卸车差异报告，遍历查询不正常流水号开始");
		for(UnloadDiffReportDetailEntity detailEntity : serialNoList){
			LOGGER.error("查询卸车任务："+ detailEntity.getUnloadTaskNo() + "开始");
			UnloadTaskDto unloadTaskDto = unloadTaskQueryService.getUnloadTaskByUnloadTaskNo(detailEntity.getUnloadTaskNo());
			LOGGER.error("查询卸车任务："+ detailEntity.getUnloadTaskNo() + "结束");
			LOGGER.error(unloadTaskDto.getUnloadStartTime() + "");
			LOGGER.error(unloadTaskDto.getUnloadOrgCode());
			LOGGER.error("差错类型" + detailEntity.getDiffType());
			LOGGER.error("运单号" + detailEntity.getWaybillNo());
			LOGGER.error("流水号" + detailEntity.getSerialNo());
			//ISSUE-3474
			//发现卸车少货，查询入库记录表，从建立卸车任务开始的时间，到当前时间为止，有没有入库动作，如果存在，该流水号不算少货
			if(StringUtils.equals(detailEntity.getDiffType(), UnloadConstants.UNLOAD_GOODS_STATE_LACK)){//如果为少货
				
				//少货时判断此件货是否已签收，已签收的不再生成差异
				try{
					String checkSerialNoIsSign = signDetailService.querySerialNoIsSign(detailEntity.getWaybillNo(), detailEntity.getSerialNo());
					if(StringUtils.equals(FossConstants.YES, checkSerialNoIsSign)) {
						LOGGER.info("卸车任务：" + detailEntity.getUnloadTaskNo()+"运单号:"+detailEntity.getWaybillNo()+"流水号:"+detailEntity.getSerialNo()+"已签收！");
						continue;
					}
				} catch(Exception e) {
					LOGGER.error("卸车任务：" + detailEntity.getUnloadTaskNo()+"运单号:"+detailEntity.getWaybillNo()+"流水号:"+detailEntity.getSerialNo()+"调用是否签收接口异常！");
					e.printStackTrace();
					//发生异常时，仅该单不再生成差异，后续单继续生成
					continue;
				}
				
				LOGGER.info("卸车任务：" + detailEntity.getUnloadTaskNo()+"运单号:"+detailEntity.getWaybillNo()+"流水号:"+detailEntity.getSerialNo()+"查询是否入库开始");
				Long result = queryInStockNumForLack(unloadTaskDto.getUnloadStartTime(), detailEntity.getUnloadOrgCode(), detailEntity.getWaybillNo(), detailEntity.getSerialNo());
				LOGGER.error("入库记录" + result);
				LOGGER.info("卸车任务：" + detailEntity.getUnloadTaskNo()+"运单号:"+detailEntity.getWaybillNo()+"流水号:"+detailEntity.getSerialNo()+"查询是否入库开始");
				if(result == null) {
					result = 0L;
				}
				if(result.intValue() > 0) {
					LOGGER.error("continue");
					//如果有入库记录则不算少货
					continue;
				}
			}
			
			//获取卸车任务编号
			String unloadTaskNo = detailEntity.getUnloadTaskNo();
			//获取卸车任务ID
			String unloadTaskId = detailEntity.getUnloadTaskId();
			LOGGER.info("卸车差异：" + unloadTaskNo + "处理明细开始");
			//如果未构造差异报告基本信息
			UnloadDiffReportEntity baseEntity = null;
			if(baseEntityMap.get(unloadTaskNo) == null){
				baseEntity = new UnloadDiffReportEntity();
				baseEntity.setId(UUIDUtils.getUUID());
				//卸车任务ID
				baseEntity.setUnloadTaskId(detailEntity.getUnloadTaskId());
				//卸车任务编号
				baseEntity.setUnloadTaskNo(detailEntity.getUnloadTaskNo());
				//卸车差异报告处理状态为：处理中
				baseEntity.setHandleStatus(UnloadConstants.UNLOAD_DIFF_HANDLE_STATE_RESOLVING);
				//报告生成时间
				baseEntity.setReportCreateTime(new Date());
				//建立任务部门编码
				baseEntity.setOrgCode(detailEntity.getUnloadOrgCode());
				
				//如果多货
				if(StringUtils.equals(detailEntity.getDiffType(), UnloadConstants.UNLOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED)
						|| StringUtils.equals(detailEntity.getDiffType(), UnloadConstants.UNLOAD_GOODS_STATE_MORE_ENTRAINED)){
					//多货
					baseEntity.setMoreGoodsPieces(1);
				}else if(StringUtils.equals(detailEntity.getDiffType(), UnloadConstants.UNLOAD_GOODS_STATE_LACK)){//如果为少货
					//少货
					baseEntity.setLackGoodsPieces(1);
					//卸车时间留空
					detailEntity.setUnloadTime(null);
				}
				//chigo 如果扫描状态为手输,并且差异类型不为多货
				else if(StringUtils.equals(detailEntity.getScanState(), UnloadConstants.UNLOAD_TASK_SCAN_STATUS_INPUT)){
					baseEntity.setByhandGoodsPieces(1);
				}
			}else{//如果该卸车任务差异报告基本信息已经构造，则更新多货、少货件数
				baseEntity = baseEntityMap.get(unloadTaskNo);
				//如果多货
				if(StringUtils.equals(detailEntity.getDiffType(), UnloadConstants.UNLOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED)
						|| StringUtils.equals(detailEntity.getDiffType(), UnloadConstants.UNLOAD_GOODS_STATE_MORE_ENTRAINED)){
					baseEntity.setMoreGoodsPieces(baseEntity.getMoreGoodsPieces() + 1);
				}else if(StringUtils.equals(detailEntity.getDiffType(), UnloadConstants.UNLOAD_GOODS_STATE_LACK)){//如果为少货
					baseEntity.setLackGoodsPieces(baseEntity.getLackGoodsPieces() + 1);
					detailEntity.setUnloadTime(null);
				}
				//chigo 如果为手输情况
				else if(StringUtils.equals(detailEntity.getScanState(), UnloadConstants.UNLOAD_TASK_SCAN_STATUS_INPUT)){
					baseEntity.setByhandGoodsPieces(baseEntity.getByhandGoodsPieces()+1);

				}
			}
			//将构造完毕的基本信息放置于map中
			baseEntityMap.put(unloadTaskNo, baseEntity);
			
			//构造待保存的差异报告流水号明细
			detailEntity.setId(UUIDUtils.getUUID());
			detailEntity.setDiffReportId(baseEntity.getId());
			detailEntity.setReportCreatedTime(baseEntity.getReportCreateTime());
			
			/**差异类型**/
			//如果多货
			if(StringUtils.equals(detailEntity.getDiffType(), UnloadConstants.UNLOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED)
					|| StringUtils.equals(detailEntity.getDiffType(), UnloadConstants.UNLOAD_GOODS_STATE_MORE_ENTRAINED)){
				detailEntity.setDiffType(UnloadConstants.UNLOAD_EXCEPTION_TYPE_MOREGOODS);
				//如果为多货，则需查询扫描流水号明细表，获取上一环节是否扫描
				ConfirmUnloadTaskBillsDto dto = new ConfirmUnloadTaskBillsDto();
				dto.setUnloadTaskId(unloadTaskId);
				dto.setWaybillNo(detailEntity.getWaybillNo());
				dto.setSerialNo(detailEntity.getSerialNo());
				//若为长途卸车
				if(StringUtils.equals(detailEntity.getUnloadTaskType(), UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE)){
					List<String> tempList = unloadTaskService.queryLongDistanceLoadTaskIsScaned(dto);
					if(tempList != null && 0 != tempList.size()){
						//上一环节扫描
						detailEntity.setIsScanedLastTime(FossConstants.YES);
					}else{
						//上一环节未扫描
						detailEntity.setIsScanedLastTime(FossConstants.NO);
					}
				}else if(StringUtils.equals(detailEntity.getUnloadTaskType(), UnloadConstants.UNLOAD_TASK_TYPE_SHORT_DISTANCE)){
					List<String> tempList = unloadTaskService.queryShortDistanceLoadTaskIsScaned(dto);
					if(tempList != null && 0 != tempList.size()){
						//上一环节扫描
						detailEntity.setIsScanedLastTime(FossConstants.YES);
					}else{
						//上一环节未扫描
						detailEntity.setIsScanedLastTime(FossConstants.NO);
					}
				}else{
					//除长途、短途卸车外，其他卸车差异“上一环节是否扫描”留空
					detailEntity.setIsScanedLastTime(null);
				}
			}else if(StringUtils.equals(detailEntity.getDiffType(), UnloadConstants.UNLOAD_GOODS_STATE_LACK)){//如果为少货
				//差异类型：少货
				detailEntity.setDiffType(UnloadConstants.UNLOAD_EXCEPTION_TYPE_LACKGOODS);
			}
			else if(StringUtils.equals(detailEntity.getScanState(), UnloadConstants.UNLOAD_TASK_SCAN_STATUS_INPUT)){
				detailEntity.setDiffType(UnloadConstants.UNLOAD_EXCEPTION_TYPE_BYHANDGOODS);
			}
			//将detailEntity放置于待保存的流水号Map中
			if(unsavedSerialNoMap.get(unloadTaskNo) == null){
				List<UnloadDiffReportDetailEntity> tempSerialNoList = new ArrayList<UnloadDiffReportDetailEntity>();
				tempSerialNoList.add(detailEntity);
				unsavedSerialNoMap.put(unloadTaskNo, tempSerialNoList);
			}else{
				List<UnloadDiffReportDetailEntity> tempSerialNoList = unsavedSerialNoMap.get(unloadTaskNo);
				tempSerialNoList.add(detailEntity);
			}
			LOGGER.info("卸车差异：" + unloadTaskNo + "处理明细结束");
		}
		LOGGER.info("卸车差异报告，遍历查询不正常流水号结束");
		//循环插入卸车差异报告，每一份差异报告单独提交一次
		Set entrySet = baseEntityMap.entrySet();
		Iterator it = entrySet.iterator();
		LOGGER.info("卸车差异报告，批量插入数据开始");
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry)it.next();
			UnloadDiffReportEntity entity = (UnloadDiffReportEntity)entry.getValue();
			String tempUnloadTaskNo = entity.getUnloadTaskNo();
			List<UnloadDiffReportDetailEntity> tempSerialNoList = unsavedSerialNoMap.get(tempUnloadTaskNo);
			//插入一份差异报告
			try{
				unloadService.addUnloadDiffReportBasicInfoAndDetail(entity, tempSerialNoList);
			}catch(Exception e){
				LOGGER.error("生成卸车差异报告发生异常，卸车任务编号：" + tempUnloadTaskNo);
				continue;
			}
			
		}
		LOGGER.info("卸车差异报告，批量插入数据结束");
		LOGGER.info("---卸车差异报告，生成结束---");
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 手动处理卸货差异报告中的少货差异
	 * 1、如果少货，未上报OA，则直接本部门入库该货物，更新差异报告中的处理时间、人、部门等字段
	 * 2、如果少货，已上报OA，则直接本部门入库该货物，更新差异报告中的处理时间、人、部门等字段，若该运单号所有少货记录
	 * 		都已处理，则需调用接口向OA上报少货已找到差错；
	 * @author 045923-foss-shiwei
	 * @date 2012-12-26 下午3:30:12
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadDiffReportService#handleUnloadLackGoodsException(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	public int handleUnloadLackGoodsException(String diffReportId,
			String waybillNo, String serialNo,String note) {
		
		/**入库该件货物**/
		//获取当前操作人、部门等
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//操作人code
		String optManCode = currentInfo.getEmpCode();
		//操作人name
		String optManName = currentInfo.getEmpName();
		//当前部门编码
		String optDeptCode = this.querySuperiorOrgCode();
		//当前部门名称
		String optDeptName = this.querySuperiorOrgName();
		
		
		/*
		 * 入库
		 */
		InOutStockEntity inEntity = new InOutStockEntity();
		//运单号
		inEntity.setWaybillNO(waybillNo);
		//流水号
		inEntity.setSerialNO(serialNo);
		//操作人code
		inEntity.setOperatorCode(optManCode);
		//操作人name
		inEntity.setOperatorName(optManName);
		//入库时间
		inEntity.setInOutStockTime(new Date());
		//部门code
		inEntity.setOrgCode(optDeptCode);
		//备注
		inEntity.setNotes(note);
		//入库类型：卸车少货找到入库
		inEntity.setInOutStockType(StockConstants.UNLOAD_LOSE_GOODS_FOUND_IN_STOCK_TYPE);
		
		UnloadDiffReportEntity unloadDiffReport = queryUnloadDiffReportById(diffReportId);
		UnloadTaskDto unloadTaskDto = unloadTaskQueryService.getUnloadTaskByUnloadTaskNo(unloadDiffReport.getUnloadTaskNo());
		//车牌号, 综合查询模块需要用到
		inEntity.setInOutStockBillNO(unloadTaskDto.getVehicleNo());
		
		int result = 0;
		
		//BUG-47399
		List<InOutStockEntity> inOutStocks = stockService.queryInStockInfo(waybillNo, serialNo, null, unloadTaskDto.getUnloadStartTime());
		if(CollectionUtils.isNotEmpty(inOutStocks)) {
			//如果差异报告创建时间之后本部门有入库记录则不用继续入库, 直接更新卸车差异状态
			result = -2;
		} else {
			//如果成功, 该接口里会调用处理卸车差异的方法
			//如果正常签收则返回-2
			result = stockService.inStockPC(inEntity);
		}
		//BUG-47399end
		
		//返回-2说明该票货已经正常签收, 直接更新卸车差异状态
		//返回其他则 stockService中自己调用处理差错的接口
		if(result == -2) {
			UnloadDiffReportDetailEntity detailEntity = new UnloadDiffReportDetailEntity();
			//部门
			detailEntity.setUnloadOrgCode(optDeptCode);
			//运单号
			detailEntity.setWaybillNo(waybillNo);
			//流水号
			detailEntity.setSerialNo(serialNo);
			
			//查询少货差异明细
			List<UnloadDiffReportDetailEntity> diffReportDetailList = queryUnresolvedLackGoodsException(detailEntity);
			if(!CollectionUtils.isNotEmpty(diffReportDetailList)){
				//差异明细里没有该货件、抛出异常
				throw new StockException(StockException.NO_UNLODA_DIFF, "");
			} else {
				//处理卸车少货差异
				UnloadDiffReportDetailEntity diffReportDetail = diffReportDetailList.get(0);
				//处理时间
				diffReportDetail.setExceptionHandleTime(new Date());
				//部门编号
				diffReportDetail.setHandleOrgCode(optDeptCode);
				//部门名称
				diffReportDetail.setHandleOrgName(optDeptName);
				//处理人工号
				diffReportDetail.setHandlerCode(optManCode);
				//处理人名称
				diffReportDetail.setHandlerName(optManName);
				//备注
				diffReportDetail.setNote(note);
				//处理差错
				handleUnloadLackDiffReport(diffReportDetail, optManCode, optManName, optDeptCode);
			}
		}
		
		//调用接送货接口，更新待办事项
		try{
			waybillRfcService.resetTodoWhenGoodFound(waybillNo, serialNo);
		}catch(Exception e) {
			LOGGER.error("手工处理卸车少货差异时，更新待办异常：" + e.getMessage());
		}
		
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
	
	
	/**
	 * 获取未处理的少货异常，使用场景：
	 * 1、卸车差异报告，手动处理少货界面，首先调用该方法，传入报告ID，运单号、流水号，看该差异是否已被处理
	 * 			处理后再次调用该方法，传入报告ID，看该报告下是否有未处理的少货差异；
	 * 			如果刚处理过的少货差异已经上报OA，则再次传入报告ID、运单号，调用该方法；
	 * 2、单票入库，少货找到时，需传入卸车部门code、运单号、流水号
	 * @author 045923-foss-shiwei
	 * @date 2012-12-27 上午10:40:22
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadDiffReportService#queryUnresolvedLackGoodsException(com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportDetailEntity)
	 */
	@Override
	public List<UnloadDiffReportDetailEntity> queryUnresolvedLackGoodsException(UnloadDiffReportDetailEntity detailEntity) {
		//返回查询结果
		return unloadDiffReportDao.queryUnresolvedLackGoodsException(detailEntity);
	}
	
	/**
	 * 更新卸货差异明细信息，可能更新的字段包括：
	 * 异常处理时间、备注、处理人编号、处理人名称、处理部门编号、处理部门名称
	 * @author 045923-foss-shiwei
	 * @date 2012-12-27 上午10:53:32
	 */
	@Override
	public int updateUnloadDiffReportDetail(UnloadDiffReportDetailEntity detailEntity) {
		//更新卸车差异明细
		unloadDiffReportDao.updateUnloadDiffReportDetail(detailEntity);
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 将卸车差异报告的处理状态更新为“已处理”
	 * @author 045923-foss-shiwei
	 * @date 2012-12-26 下午3:43:58
	 */
	@Override
	public int updateUnloadDiffReportHandleState(String diffReportId,String handleState) {
		//更新卸车差异报告处理状态：已处理
		unloadDiffReportDao.updateUnloadDiffReportHandleState(diffReportId, handleState);
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 处理卸车差异报告
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-25 下午2:54:17
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadDiffReportService#executeUnloadDiffReportTask()
	 */
	@Override
	public void executeUnloadDiffReportTask(int threadNo, int threadCount) {
		//查询查理状态为 未处理 UnloadConstants.UNLOAD_DIFF_HANDLE_STATE_UNRESOLVED 的差异报告
		//遍历差异报告下明细 
		List<String> reportIdList = unloadDiffReportDao.queryUnResolvedDiffReport(threadNo, threadCount, null, null/*,bizDate,deliverBizDate*/);
		if(CollectionUtils.isNotEmpty(reportIdList)){
			for(String reportId : reportIdList){
				try{
					unloadService.executeUnloadDiffReportTask(reportId);
				}catch(Exception e){
					LOGGER.error("上报卸车差错出现异常，差异报告ID：" + reportId + "，异常信息：" + e.getMessage());
					TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
					jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.UNLOAD_DIFF_REPORT_OA.getBizName());
					jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.UNLOAD_DIFF_REPORT_OA.getBizCode());
					jobProcessLogEntity.setExecBizId(reportId);
					jobProcessLogEntity.setExecTableName("卸车差异报告");
					jobProcessLogEntity.setRemark("上报卸车差错时出现异常");
					jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
					jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
					tfrCommonService.addJobProcessLog(jobProcessLogEntity);
					continue;
				}
			}
		}else{
			//没有未处理完毕的差异报告
		}
		
	}
	//-------------------------添加UnloadDiffReporToQMSJob的实现方法begin-----------------------------------------
	/**
	 * 处理卸车差异报告(上报QMS)
	 * @author 283244
	 */
	@Override
	public void executeUnloadDiffReportToQMSTask() {
		//遍历差异报告下明细 
		List<ReportQMSDataEntity> reportQMSDataEntitys=unloadDiffReportDao.queryOABackupsForQMSData();
		if(CollectionUtils.isNotEmpty(reportQMSDataEntitys)){
			for(ReportQMSDataEntity reportQMSDataEntity : reportQMSDataEntitys){
				String id=null;
				//加入id为空判断 sonar 218427
				if(reportQMSDataEntity!=null&&StringUtils.isNotEmpty(reportQMSDataEntity.getId())){
				id=reportQMSDataEntity.getLostWarningId().toString();
				}
				try{
					int a=unloadService.executeUnloadDiffReportToQMS(reportQMSDataEntity);
					if(a==1){
							unloadDiffReportDao.updateUnloadToOAbackups(reportQMSDataEntity.getLostWarningId().toString());
					}
				}catch(Exception e){
					//LOGGER.error("上报卸车差错出现异常(TOQMS)，差异报告ID：" + reportId + "，异常信息：" + e.getMessage());
					TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
					jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.UNLOAD_DIFF_REPORT_QMS.getBizName());
					jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.UNLOAD_DIFF_REPORT_QMS.getBizCode());
					jobProcessLogEntity.setExecBizId(id);
					jobProcessLogEntity.setExecTableName("卸车差异报告(TOQMS)");
					jobProcessLogEntity.setRemark("上报卸车差错时出现异常(TOQMS)");
					jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
					jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
					tfrCommonService.addJobProcessLog(jobProcessLogEntity);
					continue;
				}
			}
		}else{
			//没有未处理完毕的差异报告
		}
		
	}
	
	
	
	/**
	 * 处理少货差异，供stock模块调用
	 * @author 045923-foss-shiwei
	 * @date 2012-12-27 下午9:24:27
	 */
	@Override
	public int handleUnloadLackDiffReport(UnloadDiffReportDetailEntity detailEntity,String userCode,String userName,String currentOrgCode) {
		//构造查询条件
		UnloadDiffReportDetailEntity queryCondition = new UnloadDiffReportDetailEntity();
		//差异报告ID
		queryCondition.setDiffReportId(detailEntity.getDiffReportId());
		//运单号
		queryCondition.setWaybillNo(detailEntity.getWaybillNo());
		//流水号
		queryCondition.setSerialNo(detailEntity.getSerialNo());
		//更新卸车少货信息
		this.updateUnloadDiffReportDetail(detailEntity);
		
		/*
		 * 若该少货异常已上报OA差错，则看该运单下的少货异常是否全部处理，若已全部处理，则调用OA接口，上报少货已找到
		 * ISSUE-3190，如果临时表中没有差错单编号，则此处需要上报少货找到
		 */
			if(StringUtils.isNotBlank(detailEntity.getOaMistakeBillNo())){
				List<LackGoodsFoundEntity> tempList = unloadDiffReportDao.queryLackGoodsDetailByOAErrorNo(detailEntity.getOaMistakeBillNo());
				if(CollectionUtils.isEmpty(tempList)){
				//查询条件流水号清空
				queryCondition.setSerialNo(null);
				//查询该运单下未处理的少货明细
				List<UnloadDiffReportDetailEntity> unresolvedWaybillDetailEntityList = unloadDiffReportDao.queryUnresolvedLackGoodsException(queryCondition);
				if(unresolvedWaybillDetailEntityList == null
						|| 0 == unresolvedWaybillDetailEntityList.size()){
					/*调用交接单service，获取上一环节部门code*/
					//构造查询条件
					HandOverBillSerialNoDetailEntity queryEntity = new HandOverBillSerialNoDetailEntity();
					//到达部门code
					queryEntity.setDestOrgCode(currentOrgCode);
					//运单号
					queryEntity.setWaybillNo(detailEntity.getWaybillNo());
					//流水号
					queryEntity.setSerialNo(detailEntity.getSerialNo());
					//获取上一环节部门code
					List<String> lastDeptCodeList = handOverBillService.querydepartDeptCodeByDestOrgCodeAndWaybillNoAndSerialNo(queryEntity);
					//定义上一环节部门标杆编码
					String lastOrgUnifiedCode = null;
					if(lastDeptCodeList != null && lastDeptCodeList.size() != 0){
						String orgCode = lastDeptCodeList.get(0);
						OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
						if(orgEntity != null){
							lastOrgUnifiedCode = orgEntity.getUnifiedCode();
						}
					}
					/*
					 * 上报少货找到
					 */
					ResponseDto responseDto = fossToOAService.reportLessGoodsFound(detailEntity.getOaMistakeBillNo(), userCode, userName, lastOrgUnifiedCode);
					//如果oa没处理成功，则回滚事务
					if(!responseDto.getIsSuccess()){
						//记录日志
						LOGGER.error("######################上报OA少货已找到差错单失败，失败信息：" + responseDto.getFailureReason());
						//抛出业务异常
						throw new TfrBusinessException("上报OA“少货已找到差错单”失败！");
					}
				}
			}
		}

		/*
		 * 查询当前卸车差异报告下是否还有未处理的少货差异，若没有，则更新差异报告处理状态为“已处理”
		 */
		//清空查询条件中的运单号、流水号，只留差异报告id
		queryCondition.setSerialNo(null);
		queryCondition.setWaybillNo(null);
		//查询当前卸车差异报告下是否还有未处理的少货差异
		List<UnloadDiffReportDetailEntity> unresolvedDetailEntityList = unloadDiffReportDao.queryUnresolvedLackGoodsException(queryCondition);
		if(null == unresolvedDetailEntityList
				|| 0 == unresolvedDetailEntityList.size()){
			//记录日志
			LOGGER.error(queryCondition.getDiffReportId() + "（卸车差异报告ID）少货差异处理完毕，更新差异报告处理状态为“已处理”！");
			//更新差异报告处理状态为已处理
			this.updateUnloadDiffReportHandleState(detailEntity.getDiffReportId(), UnloadConstants.UNLOAD_DIFF_HANDLE_STATE_RESOLVED);
		}
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 获取当前部门的上级部门code
	 * @author 045923-foss-shiwei
	 * @date 2013-4-2 下午4:48:53
	 */
	private String querySuperiorOrgCode(){
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		OrgAdministrativeInfoEntity superEntity = loadService.querySuperiorOrgByOrgCode(orgCode);
		if(superEntity == null || StringUtils.isBlank(superEntity.getCode())){
			LOGGER.error("###################根据部门（code：" + orgCode + "）获取上级营业部、派送部、总调、外场、结果为空！");
			throw new TfrBusinessException("获取本部门的上级组织失败(包括营业部、派送部、总调、外场)！");
		}else{
			return superEntity.getCode();
		}
	}

	/**
	 * 获取当前部门的上级部门name
	 * @author 045923-foss-shiwei
	 * @date 2013-4-2 下午4:48:53
	 */
	private String querySuperiorOrgName(){
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		OrgAdministrativeInfoEntity superEntity = loadService.querySuperiorOrgByOrgCode(orgCode);
		if(superEntity == null || StringUtils.isBlank(superEntity.getCode())){
			LOGGER.error("###################根据部门（code：" + orgCode + "）获取上级营业部、派送部、总调、外场、结果为空！");
			throw new TfrBusinessException("获取本部门的上级组织失败(包括营业部、派送部、总调、外场)！");
		}else{
			return superEntity.getName();
		}
	}

	/**
	 * 上报oa少货已找到差错，供job调用
	 * @author 045923-foss-shiwei
	 * @date 2013-7-3 下午4:25:43
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadDiffReportService#reportUnloadLackGoodsFoundToOA()
	 */
	@Override
	public int reportUnloadLackGoodsFoundToOA() {
		//获取配置参数
		ConfigurationParamsEntity defaultBizHourSlice = configurationParamsService.queryConfigurationParamsByOrgCode(
				DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , 
				ConfigurationParamsConstants.TFR_PARM_REPORT_UNLOAD_LACK_GOODS_FOUND_LATEST_REPORT_TIME, 
				FossConstants.ROOT_ORG_CODE);
		Date bizDate = null;
		if (defaultBizHourSlice == null
				|| StringUtils.isEmpty(defaultBizHourSlice.getConfValue())) {
			LOGGER.error("###########################读取配置参数中的oa少货差错上报最晚时间为空！##########################");
			throw new TfrBusinessException("读取配置参数中的oa少货差错上报最晚时间为空！");
		} else {
			bizDate = DateUtils.strToDate(defaultBizHourSlice.getConfValue());
		}
		//查询出所有未少货找到的oa差错单编号
		List<String> errorNoList = unloadDiffReportDao.queryNoFoundLackGoodsOAErrorNo(TransferConstants.REPORT_TYPE_UNLOAD,bizDate);
		String unloadOrgCode = null;
		String tempSerialNo = null;
		String tempWaybillNo = null;
		//遍历这些少货差错
		for(String errorNo : errorNoList){
			//获取差错单下所有未找到的流水号
			List<LackGoodsFoundEntity> lackSerialNoList = unloadDiffReportDao.queryNoFoundLackGoodsDetailByOAErrorNo(errorNo);
			//取最后一次上报的流水号
			unloadOrgCode = lackSerialNoList.get(lackSerialNoList.size() - 1).getLackGoodsOrgCode();
			tempSerialNo = lackSerialNoList.get(lackSerialNoList.size() - 1).getSerialNo();
			tempWaybillNo = lackSerialNoList.get(lackSerialNoList.size() - 1).getWaybillNo();
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
					unloadDiffReportDao.updateUnloadLackGoodsInfo(lackSerialNo);
					
					//有入库记录时才更新待办事项异常状态为N
					try{
						waybillRfcService.resetTodoWhenGoodFound(waybillNo, serialNo);
					}catch(Exception e) {
						LOGGER.error("自动上报卸车少货找到时，更新待办异常："+e.getMessage());
					}
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
				//若最后一件的入库类型为卸车入库，则责任部门为最后交接单的部门
				//定义上一环节部门标杆编码
				String lastOrgUnifiedCode = null;
				String tempOrgCode = "";
				if(StringUtils.equals(StockConstants.UNLOAD_GOODS_IN_STOCK_TYPE, discoverer == null ? "" : discoverer.getInOutStockType())) {
					
					//构造查询条件
					HandOverBillSerialNoDetailEntity queryEntity = new HandOverBillSerialNoDetailEntity();
					//到达部门code
					queryEntity.setDestOrgCode(unloadOrgCode);
					//运单号
					queryEntity.setWaybillNo(tempWaybillNo);
					//流水号
					queryEntity.setSerialNo(tempSerialNo);
					//获取上一环节部门code
					List<String> lastDeptCodeList = handOverBillService.querydepartDeptCodeByDestOrgCodeAndWaybillNoAndSerialNo(queryEntity);
					
					if(lastDeptCodeList != null && lastDeptCodeList.size() != 0){
						tempOrgCode = lastDeptCodeList.get(0);
					}
				} else {
					//218427 sonar优化 部门为空判断
					if(StringUtils.isEmpty(discoverer.getOrgCode())){
						throw new TfrBusinessException("discoverer.getOrgCode()部门为空");
					}
					//责任部门为入库部门
					tempOrgCode = discoverer.getOrgCode();
				}
				OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(tempOrgCode);
				if(orgEntity != null){
					lastOrgUnifiedCode = orgEntity.getUnifiedCode();
				}
				
				//--------------------------------卸车丢货找到上报QMS-------------------------------
				
				final SimpleDateFormat sdfHmsQMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				List<FindLostGoodsEntity> liist=new ArrayList<FindLostGoodsEntity>();
				FindLostGoodsEntity reportToQMSList=new FindLostGoodsEntity();
				//设置运单号
				reportToQMSList.setWaybillNum(lackSerialNoList.get(0).getWaybillNo());
				reportToQMSList.setDealTime(sdfHmsQMS.format(new Date()));
				reportToQMSList.setSysAutoTrackId(findLostGoodsDao.getSeqNextVal()+"");
				reportToQMSList.setDealContent(LostWarningConstant.dealFindContentStr);
				reportToQMSList.setLoseStatus("2");
				reportToQMSList.setFindScene("4");
				List<FindLostGoodsSerialEntity> serianoList=new ArrayList<FindLostGoodsSerialEntity>();
				for(LackGoodsFoundEntity lgfe : lackSerialNoList){
					FindLostGoodsSerialEntity fgse=new FindLostGoodsSerialEntity();
					fgse.setFindStatus("2");
					fgse.setIsEffective("2");
					fgse.setFlowCode(lgfe.getSerialNo());
					serianoList.add(fgse);
					}
				reportToQMSList.setFlowcodeList(serianoList);
				liist.add(reportToQMSList);
				
				//上报QMS处理
				try{
				String requestStr = JSONArray.fromObject(liist).toString();
				//上报QMS，并解析返回的报文信息
				/*String respStrr = */FossToMcewService.getInstatce().reportFindGoodsData(requestStr);
				
				}catch(Exception e){
					
					LOGGER.info("卸车找到丢货上报QMS失败");
				}
				//-------------------------------卸车丢货找到上报QMS---------------------------------
				
				/*
				 * 上报少货找到
				 */
				//218427 sonar  discoverer.getOperatorCode()不为空判断
				if(StringUtils.isNotEmpty(discoverer.getOperatorCode())){
				ResponseDto responseDto = fossToOAService.reportLessGoodsFound(errorNo, discoverer.getOperatorCode(), discoverer.getOperatorName(), lastOrgUnifiedCode);
				//如果oa没处理成功，则回滚事务
				
				if(!responseDto.getIsSuccess()){
					//记录日志
					LOGGER.error("######################上报OA少货已找到差错单失败，失败信息：" + responseDto.getFailureReason());
					//查询下一个少货差错单
					continue;
				}
				}
			}
		}
		/*try{
			findLostGoodsService.findLostGoods();
			}catch(Exception e){
				LOGGER.info("丢货找到数据上报QMS失败");
			}*/
		
		return FossConstants.SUCCESS;
	}

	/** 
	 * @Title: exportUnloadDifferencesReportDetail 
	 * @Description: 导出
	 * @param diffReportNo
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadDiffReportService#exportUnloadDifferencesReportDetail(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-7-8下午5:40:37
	 */ 
	@Override
	public InputStream exportUnloadDifferencesReportDetail(String diffReportNo) {
		InputStream excelStream = null;
		UnloadDiffReportEntity baseEntity = queryUnloadDiffReportBasicInfo(diffReportNo);
		//获取卸车差异报告ID
		String diffReportId = baseEntity.getId();
		//根据id获取差异报告详情列表
		List<UnloadDiffReportDetailEntity> unloadDiffReportDetailEntityList = queryUnloadDiffReportDetail(diffReportId);
		//行List
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(UnloadDiffReportDetailEntity unloadDiffReportDetail : unloadDiffReportDetailEntityList){
			//每行的列List
			List<String> columnList = new ArrayList<String>();
			
			//运单号
			columnList.add(unloadDiffReportDetail.getWaybillNo());

			//流水号
			columnList.add(unloadDiffReportDetail.getSerialNo());
			
			//OA差错编号
			columnList.add(unloadDiffReportDetail.getOaMistakeBillNo());
			
			String diffType = "";
			
			List<BaseDataDictDto> dicts = tfrCommonService.loadDictDataComboxNoDefault(DictionaryConstants.UNLOAD_EXCEPTION_TYPE);
			if(dicts == null) {
				dicts = new ArrayList<BaseDataDictDto>();
			}
			for(BaseDataDictDto dict : dicts) {
				String valueCode = dict.getValueCode();
				if(StringUtils.equals(unloadDiffReportDetail.getDiffType(), valueCode)) {
					diffType = dict.getValueName();
					break;
				}
			}
			//差异类型
			columnList.add(diffType);
			
			//运输性质
			columnList.add(unloadDiffReportDetail.getTransProperty());

			//上一环节是否扫描
			String isScanedLastTime = unloadDiffReportDetail.getIsScanedLastTime();
			columnList.add(StringUtils.equals(isScanedLastTime, "Y") ? "是" : "否");
			
			//卸货时间
			Date unloadTime = unloadDiffReportDetail.getUnloadTime();
			String unloadTimeStr = "";
			if(unloadTime != null) {
				unloadTimeStr = DateUtils.convert(unloadTime);
			}
			columnList.add(unloadTimeStr);
			
			//异常处理时间
			Date exceptionHandleTime = unloadDiffReportDetail.getExceptionHandleTime();
			String exceptionHandleTimeStr = "";
			if(exceptionHandleTimeStr != null) {
				exceptionHandleTimeStr = DateUtils.convert(exceptionHandleTime);
			}
			columnList.add(exceptionHandleTimeStr);
			
			//目的站
			String targetOrg = unloadDiffReportDetail.getTargetOrg();
			columnList.add(targetOrg);
			
			//备注
			String note = unloadDiffReportDetail.getNote();
			columnList.add(note);
			
			rowList.add(columnList);
		}
		String[] rowHeads = {"运单号","流水号","OA差错编号","差异类型", "运输性质", "上一环节是否扫描", "卸货时间","异常处理时间","目的站","备注"};//定义表头
		
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("卸车差异报告明细");
		exportSetting.setSize(ConstantsNumberSonar.SONAR_NUMBER_5000);
		ExportResource exportResource  = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);
        return excelStream;
	}

	/**
	 * @author niuly
	 * @date 2014-6-17上午10:39:43
	 * @function 判断运单是否少货且成功上报过OA少货差错
	 * @param waybillNo
	 * @return
	 */
	@Override
	public boolean hasLackError(String waybillNo) {
		long count = unloadDiffReportDao.queryLackCountByWaybillNo(waybillNo);
		return count > 0;
	}
	
	/**
	 * 处理少货差异，二程接驳卸车使用
	 * @author 205109-foss-zenghaibin
	 * @date 2015-05-26 上午8:41:27
	 */
	@Override
	public int handleSCUnloadLackDiffReport(UnloadDiffReportDetailEntity detailEntity,String userCode,String userName,String currentOrgCode,String outFieldCode) {
		//构造查询条件
		UnloadDiffReportDetailEntity queryCondition = new UnloadDiffReportDetailEntity();
		//差异报告ID
		queryCondition.setDiffReportId(detailEntity.getDiffReportId());
		//运单号
		queryCondition.setWaybillNo(detailEntity.getWaybillNo());
		//流水号
		queryCondition.setSerialNo(detailEntity.getSerialNo());
		//更新卸车少货信息
		this.updateUnloadDiffReportDetail(detailEntity);
		
		/*
		 * 若该少货异常已上报OA差错，则看该运单下的少货异常是否全部处理，若已全部处理，则调用OA接口，上报少货已找到
		 * ISSUE-3190，如果临时表中没有差错单编号，则此处需要上报少货找到
		 */
			if(StringUtils.isNotBlank(detailEntity.getOaMistakeBillNo())){
				List<LackGoodsFoundEntity> tempList = unloadDiffReportDao.queryLackGoodsDetailByOAErrorNo(detailEntity.getOaMistakeBillNo());
				if(CollectionUtils.isEmpty(tempList)){
				//查询条件流水号清空
				queryCondition.setSerialNo(null);
				//查询该运单下未处理的少货明细
				List<UnloadDiffReportDetailEntity> unresolvedWaybillDetailEntityList = unloadDiffReportDao.queryUnresolvedLackGoodsException(queryCondition);
				if(unresolvedWaybillDetailEntityList == null
						|| 0 == unresolvedWaybillDetailEntityList.size()){
					
					/*
					 * 上报少货找到
					 */
					ResponseDto responseDto = fossToOAService.reportLessGoodsFound(detailEntity.getOaMistakeBillNo(), userCode, userName, outFieldCode);
					//如果oa没处理成功，则回滚事务
					if(!responseDto.getIsSuccess()){
						//记录日志
						LOGGER.error("######################上报OA少货已找到差错单失败，失败信息：" + responseDto.getFailureReason());
						//抛出业务异常
						throw new TfrBusinessException("上报OA“少货已找到差错单”失败！");
					}
				}
			}
		}

		/*
		 * 查询当前卸车差异报告下是否还有未处理的少货差异，若没有，则更新差异报告处理状态为“已处理”
		 */
		//清空查询条件中的运单号、流水号，只留差异报告id
		queryCondition.setSerialNo(null);
		queryCondition.setWaybillNo(null);
		//查询当前卸车差异报告下是否还有未处理的少货差异
		List<UnloadDiffReportDetailEntity> unresolvedDetailEntityList = unloadDiffReportDao.queryUnresolvedLackGoodsException(queryCondition);
		if(null == unresolvedDetailEntityList
				|| 0 == unresolvedDetailEntityList.size()){
			//记录日志
			LOGGER.error(queryCondition.getDiffReportId() + "（卸车差异报告ID）少货差异处理完毕，更新差异报告处理状态为“已处理”！");
			//更新差异报告处理状态为已处理
			this.updateUnloadDiffReportHandleState(detailEntity.getDiffReportId(), UnloadConstants.UNLOAD_DIFF_HANDLE_STATE_RESOLVED);
		}
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
	
	
	
	/**
	 * 生成卸车差异报告，先查询出卸车流水号信息，根据异常的卸车流水号明细
	 * @author 045923-foss-shiwei
	 * @date 2012-12-22 下午5:03:03
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadDiffReportService#createUnloadDiffReport(java.util.List)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int createUnloadDiffReportOne(Date startTime,Date endTime) {
		LOGGER.error("createUnloadDiffReport");
		LOGGER.info("---卸车差异报告，生成开始---");
		//获取所有未生成卸车差异报告的、已经卸车结束的、货物状态不正常的卸车任务流水号明细list
		LOGGER.info("卸车差异报告，查询卸车流水号明细开始");
		//默认查询三天内的数据,4320分钟
		//int day = 3;
/*		int minutes = 4320;
		try{
			ConfigurationParamsEntity defaultBizHourSlice = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , ConfigurationParamsConstants.TFR_PARM_UNLOAD_CREATE_REPORT_DAY, FossConstants.ROOT_ORG_CODE);
			minutes = Integer.valueOf(defaultBizHourSlice.getConfValue()).intValue();
		}catch(Exception e){
			LOGGER.error("获取配置参数失败", ExceptionUtils.getFullStackTrace(e));
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.UNLOAD_DIFFER_REPORT.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.UNLOAD_DIFFER_REPORT.getBizCode());
			jobProcessLogEntity.setRemark("生成卸车差异报告时获取配置参数失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		}*/
		List<UnloadDiffReportDetailEntity> serialNoList = unloadDiffReportDao.queryUnresolvedUnloadSerialNoListByTime(startTime,endTime);
		LOGGER.info("卸车差异报告，查询卸车流水号明细结束");
		//如果获取的结果集为空，则直接返回
		if(serialNoList == null || serialNoList.size() == 0){
			return FossConstants.SUCCESS;
		}
		//定义map，key为卸车任务编号，value为该卸车任务的差异报告基本信息
		Map<String,UnloadDiffReportEntity> baseEntityMap = new HashMap<String,UnloadDiffReportEntity>();
		//定义待保存的差异报告流水号明细Map，key为卸车任务编号，value为非正常的流水号list
		Map<String,List<UnloadDiffReportDetailEntity>> unsavedSerialNoMap = new HashMap<String,List<UnloadDiffReportDetailEntity>>();
		//遍历查询出的不正常的流水号明细
		LOGGER.info("卸车差异报告，遍历查询不正常流水号开始");
		for(UnloadDiffReportDetailEntity detailEntity : serialNoList){
			LOGGER.error("查询卸车任务："+ detailEntity.getUnloadTaskNo() + "开始");
			UnloadTaskDto unloadTaskDto = unloadTaskQueryService.getUnloadTaskByUnloadTaskNo(detailEntity.getUnloadTaskNo());
			LOGGER.error("查询卸车任务："+ detailEntity.getUnloadTaskNo() + "结束");
			LOGGER.error(unloadTaskDto.getUnloadStartTime() + "");
			LOGGER.error(unloadTaskDto.getUnloadOrgCode());
			LOGGER.error("差错类型" + detailEntity.getDiffType());
			LOGGER.error("运单号" + detailEntity.getWaybillNo());
			LOGGER.error("流水号" + detailEntity.getSerialNo());
			//ISSUE-3474
			//发现卸车少货，查询入库记录表，从建立卸车任务开始的时间，到当前时间为止，有没有入库动作，如果存在，该流水号不算少货
			if(StringUtils.equals(detailEntity.getDiffType(), UnloadConstants.UNLOAD_GOODS_STATE_LACK)){//如果为少货
				
				//少货时判断此件货是否已签收，已签收的不再生成差异
				try{
					String checkSerialNoIsSign = signDetailService.querySerialNoIsSign(detailEntity.getWaybillNo(), detailEntity.getSerialNo());
					if(StringUtils.equals(FossConstants.YES, checkSerialNoIsSign)) {
						LOGGER.info("卸车任务：" + detailEntity.getUnloadTaskNo()+"运单号:"+detailEntity.getWaybillNo()+"流水号:"+detailEntity.getSerialNo()+"已签收！");
						continue;
					}
				} catch(Exception e) {
					LOGGER.error("卸车任务：" + detailEntity.getUnloadTaskNo()+"运单号:"+detailEntity.getWaybillNo()+"流水号:"+detailEntity.getSerialNo()+"调用是否签收接口异常！");
					e.printStackTrace();
					//发生异常时，仅该单不再生成差异，后续单继续生成
					continue;
				}
				
				LOGGER.info("卸车任务：" + detailEntity.getUnloadTaskNo()+"运单号:"+detailEntity.getWaybillNo()+"流水号:"+detailEntity.getSerialNo()+"查询是否入库开始");
				Long result = queryInStockNumForLack(unloadTaskDto.getUnloadStartTime(), detailEntity.getUnloadOrgCode(), detailEntity.getWaybillNo(), detailEntity.getSerialNo());
				LOGGER.error("入库记录" + result);
				LOGGER.info("卸车任务：" + detailEntity.getUnloadTaskNo()+"运单号:"+detailEntity.getWaybillNo()+"流水号:"+detailEntity.getSerialNo()+"查询是否入库开始");
				if(result == null) {
					result = 0L;
				}
				if(result.intValue() > 0) {
					LOGGER.error("continue");
					//如果有入库记录则不算少货
					continue;
				}
			}
			
			//获取卸车任务编号
			String unloadTaskNo = detailEntity.getUnloadTaskNo();
			//获取卸车任务ID
			String unloadTaskId = detailEntity.getUnloadTaskId();
			LOGGER.info("卸车差异：" + unloadTaskNo + "处理明细开始");
			//如果未构造差异报告基本信息
			UnloadDiffReportEntity baseEntity = null;
			if(baseEntityMap.get(unloadTaskNo) == null){
				baseEntity = new UnloadDiffReportEntity();
				baseEntity.setId(UUIDUtils.getUUID());
				//卸车任务ID
				baseEntity.setUnloadTaskId(detailEntity.getUnloadTaskId());
				//卸车任务编号
				baseEntity.setUnloadTaskNo(detailEntity.getUnloadTaskNo());
				//卸车差异报告处理状态为：处理中
				baseEntity.setHandleStatus(UnloadConstants.UNLOAD_DIFF_HANDLE_STATE_RESOLVING);
				//报告生成时间
				baseEntity.setReportCreateTime(new Date());
				//建立任务部门编码
				baseEntity.setOrgCode(detailEntity.getUnloadOrgCode());
				
				//如果多货
				if(StringUtils.equals(detailEntity.getDiffType(), UnloadConstants.UNLOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED)
						|| StringUtils.equals(detailEntity.getDiffType(), UnloadConstants.UNLOAD_GOODS_STATE_MORE_ENTRAINED)){
					//多货
					baseEntity.setMoreGoodsPieces(1);
				}else if(StringUtils.equals(detailEntity.getDiffType(), UnloadConstants.UNLOAD_GOODS_STATE_LACK)){//如果为少货
					//少货
					baseEntity.setLackGoodsPieces(1);
					//卸车时间留空
					detailEntity.setUnloadTime(null);
				}
				//chigo 如果扫描状态为手输,并且差异类型不为多货
				else if(StringUtils.equals(detailEntity.getScanState(), UnloadConstants.UNLOAD_TASK_SCAN_STATUS_INPUT)){
					baseEntity.setByhandGoodsPieces(1);
				}
			}else{//如果该卸车任务差异报告基本信息已经构造，则更新多货、少货件数
				baseEntity = baseEntityMap.get(unloadTaskNo);
				//如果多货
				if(StringUtils.equals(detailEntity.getDiffType(), UnloadConstants.UNLOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED)
						|| StringUtils.equals(detailEntity.getDiffType(), UnloadConstants.UNLOAD_GOODS_STATE_MORE_ENTRAINED)){
					baseEntity.setMoreGoodsPieces(baseEntity.getMoreGoodsPieces() + 1);
				}else if(StringUtils.equals(detailEntity.getDiffType(), UnloadConstants.UNLOAD_GOODS_STATE_LACK)){//如果为少货
					baseEntity.setLackGoodsPieces(baseEntity.getLackGoodsPieces() + 1);
					detailEntity.setUnloadTime(null);
				}
				//chigo 如果为手输情况
				else if(StringUtils.equals(detailEntity.getScanState(), UnloadConstants.UNLOAD_TASK_SCAN_STATUS_INPUT)){
					baseEntity.setByhandGoodsPieces(baseEntity.getByhandGoodsPieces()+1);

				}
			}
			//将构造完毕的基本信息放置于map中
			baseEntityMap.put(unloadTaskNo, baseEntity);
			
			//构造待保存的差异报告流水号明细
			detailEntity.setId(UUIDUtils.getUUID());
			detailEntity.setDiffReportId(baseEntity.getId());
			detailEntity.setReportCreatedTime(baseEntity.getReportCreateTime());
			
			/**差异类型**/
			//如果多货
			if(StringUtils.equals(detailEntity.getDiffType(), UnloadConstants.UNLOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED)
					|| StringUtils.equals(detailEntity.getDiffType(), UnloadConstants.UNLOAD_GOODS_STATE_MORE_ENTRAINED)){
				detailEntity.setDiffType(UnloadConstants.UNLOAD_EXCEPTION_TYPE_MOREGOODS);
				//如果为多货，则需查询扫描流水号明细表，获取上一环节是否扫描
				ConfirmUnloadTaskBillsDto dto = new ConfirmUnloadTaskBillsDto();
				dto.setUnloadTaskId(unloadTaskId);
				dto.setWaybillNo(detailEntity.getWaybillNo());
				dto.setSerialNo(detailEntity.getSerialNo());
				//若为长途卸车
				if(StringUtils.equals(detailEntity.getUnloadTaskType(), UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE)){
					List<String> tempList = unloadTaskService.queryLongDistanceLoadTaskIsScaned(dto);
					if(tempList != null && 0 != tempList.size()){
						//上一环节扫描
						detailEntity.setIsScanedLastTime(FossConstants.YES);
					}else{
						//上一环节未扫描
						detailEntity.setIsScanedLastTime(FossConstants.NO);
					}
				}else if(StringUtils.equals(detailEntity.getUnloadTaskType(), UnloadConstants.UNLOAD_TASK_TYPE_SHORT_DISTANCE)){
					List<String> tempList = unloadTaskService.queryShortDistanceLoadTaskIsScaned(dto);
					if(tempList != null && 0 != tempList.size()){
						//上一环节扫描
						detailEntity.setIsScanedLastTime(FossConstants.YES);
					}else{
						//上一环节未扫描
						detailEntity.setIsScanedLastTime(FossConstants.NO);
					}
				}else{
					//除长途、短途卸车外，其他卸车差异“上一环节是否扫描”留空
					detailEntity.setIsScanedLastTime(null);
				}
			}else if(StringUtils.equals(detailEntity.getDiffType(), UnloadConstants.UNLOAD_GOODS_STATE_LACK)){//如果为少货
				//差异类型：少货
				detailEntity.setDiffType(UnloadConstants.UNLOAD_EXCEPTION_TYPE_LACKGOODS);
			}
			else if(StringUtils.equals(detailEntity.getScanState(), UnloadConstants.UNLOAD_TASK_SCAN_STATUS_INPUT)){
				detailEntity.setDiffType(UnloadConstants.UNLOAD_EXCEPTION_TYPE_BYHANDGOODS);
			}
			//将detailEntity放置于待保存的流水号Map中
			if(unsavedSerialNoMap.get(unloadTaskNo) == null){
				List<UnloadDiffReportDetailEntity> tempSerialNoList = new ArrayList<UnloadDiffReportDetailEntity>();
				tempSerialNoList.add(detailEntity);
				unsavedSerialNoMap.put(unloadTaskNo, tempSerialNoList);
			}else{
				List<UnloadDiffReportDetailEntity> tempSerialNoList = unsavedSerialNoMap.get(unloadTaskNo);
				tempSerialNoList.add(detailEntity);
			}
			LOGGER.info("卸车差异：" + unloadTaskNo + "处理明细结束");
		}
		LOGGER.info("卸车差异报告，遍历查询不正常流水号结束");
		//循环插入卸车差异报告，每一份差异报告单独提交一次
		Set entrySet = baseEntityMap.entrySet();
		Iterator it = entrySet.iterator();
		LOGGER.info("卸车差异报告，批量插入数据开始");
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry)it.next();
			UnloadDiffReportEntity entity = (UnloadDiffReportEntity)entry.getValue();
			String tempUnloadTaskNo = entity.getUnloadTaskNo();
			List<UnloadDiffReportDetailEntity> tempSerialNoList = unsavedSerialNoMap.get(tempUnloadTaskNo);
			//插入一份差异报告
			try{
					unloadService.addUnloadDiffReportBasicInfoAndDetail(entity, tempSerialNoList);
			}catch(Exception e){
				LOGGER.error("生成卸车差异报告发生异常，卸车任务编号：" + tempUnloadTaskNo);
				continue;
			}
			
		}
		LOGGER.info("卸车差异报告，批量插入数据结束");
		LOGGER.info("---卸车差异报告，生成结束---");
		//返回处理成功
		return FossConstants.SUCCESS;
	}


	
		


}